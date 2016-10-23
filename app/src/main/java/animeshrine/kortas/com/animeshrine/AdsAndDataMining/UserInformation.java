package animeshrine.kortas.com.animeshrine.AdsAndDataMining;

/**
 * Created by Aladinne on 21/10/2016.
 */

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;

public class UserInformation {
    // this class allows to get device information. It's done in two steps:
    // 1) get synchronization account email
    // 2) get contact data, associated with this email
    // by https://github.com/jehy


    //WARNING! You need to have permissions
    //
    //<uses-permission android:name="android.permission.READ_CONTACTS" />
    //<uses-permission android:name="android.permission.GET_ACCOUNTS" />
    //
    // in your AndroidManifest.xml for this code.

    public String id = null;
    public String email = null;
    public String phone = null;
    public String accountName = null;
    public String name = null;

    public UserInformation(Activity MainActivity) {




        final AccountManager manager = AccountManager.get(MainActivity);
        if (ActivityCompat.checkSelfPermission(MainActivity, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        final Account[] accounts = manager.getAccountsByType("com.google");
            if (accounts[0].name != null) {
                accountName = accounts[0].name;
                String where=ContactsContract.CommonDataKinds.Email.DATA + " = ?";
                ArrayList<String> what = new ArrayList<String>();
                what.add(accountName);
                Log.v("Got account", "Account " + accountName);
                for (int i=1;i<accounts.length;i++)
                {
                    where+=" or "+ContactsContract.CommonDataKinds.Email.DATA + " = ?";
                    what.add(accounts[i].name);
                    Log.v("Got account", "Account " + accounts[i].name);

                }
                String[] whatarr=(String[]) what.toArray(new String[what.size()]);
                ContentResolver cr = MainActivity.getContentResolver();
                Cursor emailCur = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        where,
                        whatarr, null);
                while (emailCur.moveToNext()) {
                    id = emailCur
                            .getString(emailCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));
                    email = emailCur
                            .getString(emailCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    String newName = emailCur
                            .getString(emailCur
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (name == null || newName.length() > name.length())
                        name = newName;

                    Log.v("Got contacts", "ID " + id + " Email : " + email
                            + " Name : " + name);
                }

                emailCur.close();
                if (id != null) {

                    // get the phone number
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[] { id }, null);
                    while (pCur.moveToNext()) {
                        phone = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.v("Got contacts", "phone" + phone);
                    }
                    pCur.close();
                }
            }
        }
    }