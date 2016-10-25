package animeshrine.kortas.com.animeshrine;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import animeshrine.kortas.com.animeshrine.AdsAndDataMining.User;


public class SplashScreen extends Activity implements AsyncResponse {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        tv = (TextView) findViewById(R.id.tvsplash);

        MyDBHandler myDBHandler = new MyDBHandler(SplashScreen.this, null, null, 1);
        int dbsize = myDBHandler.getAllAnimeSize();
        if (dbsize == 0) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
            wl.acquire();

            AsyncListViewLoader asyncTask = new AsyncListViewLoader();
            asyncTask.delegate = this;
            asyncTask.execute();

            wl.release();
            //FirebaseCrash.report(new Throwable(new Exception("new user")));


            try {


                Global.seasonalAnime = AnimeS.listAll(AnimeS.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (Global.firstUse ) {
                //   FirebaseCrash.report(new Throwable(new Exception("new user")));
     //           DatabaseReference database = FirebaseDatabase.getInstance().getReference();
       //         database.getRoot().child("users").setValue("test");
            /*    User user = new User();
                    String name = this.getUsername();
                    if(name==null)
                    {
                        user.setEmail("null");
                    }
                user.setEmail(name);user.setLocation("null");user.setName("null");
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                Log.e("firebase",database.getRoot().getKey()+"");
                  DatabaseReference myRef = database.getRef();
                myRef.child("user").setValue(user);*/

                // Write a message to the database
                User user = new User();
                String name = this.getUsername();
                if(name==null)
                {
                    user.setEmail("null");
                }
                user.setEmail(name);
               user.setOs( System.getProperty("os.version")); // OS version
               user.setApi( android.os.Build.VERSION.SDK );     // API Level
               user.setDevice( android.os.Build.DEVICE );          // Device
                user.setModel(android.os.Build.MODEL );           // Model
                user.setProduct(android.os.Build.PRODUCT);          // Product
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://animeshrine-65129.firebaseio.com/");
                 databaseReference.setValue(user) ;




            }

            Global.AllAnime = myDBHandler.getAllAnimeFromAllAnime();
            try {
                Global.seasonalAnime = AnimeS.listAll(AnimeS.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
        }


    }

    @Override
    public void processFinish(List<Anime> output) {
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);

    }

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Anime>> {
        public AsyncResponse delegate = null;
        private final ProgressDialog dialog = new ProgressDialog(SplashScreen.this);

        @Override
        protected void onPostExecute(List<Anime> result) {
            super.onPostExecute(result);
            delegate.processFinish(result);
            // result.clear();
            //  Anime a = new Anime() ;
            //a.setName("qsdkjlmkq");

            //  result.add(a);

            //  adpt.setItemList(result);
            //   adpt.notifyDataSetChanged(); dialog.dismiss();
            //    Log.e("all season anime",Global.seasonalAnime.size()+" "+Global.seasonalAnime.get(1).name );

            Global.AllAnime = result;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading Anime ");
            dialog.show();

        }

        @Override
        protected List<Anime> doInBackground(String... params) {
            List<Anime> result = new ArrayList<>();

            XMLPullParserHandler parser = new XMLPullParserHandler();

            List<Anime> la = null;
            try {
                la = parser.parse(getAssets().open("animelist.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            MyDBHandler myDBHandler = new MyDBHandler(SplashScreen.this, null, null, 1);

            result = la;

            myDBHandler.addToAllAnime(la);

            Log.d("animelist", la.size() + "");

            // SeasonAnimeStealer seasonAnimeStealer = new SeasonAnimeStealer() ;
            //Global.seasonalAnime =seasonAnimeStealer.stealSeasonJsoup();


            return result;
            // return null;
        }


    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public String getUsername() {
        AccountManager manager = AccountManager.get(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                //return parts[0];
            return  possibleEmails.get(0) ;
        }
        return null;
    }
}