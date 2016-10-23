
package animeshrine.kortas.com.animeshrine;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SplashScreen extends Activity implements AsyncResponse {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        tv = (TextView) findViewById(R.id.tvsplash);

        MyDBHandler myDBHandler = new MyDBHandler(SplashScreen.this, null, null, 1);
     int dbsize  = myDBHandler.getAllAnimeSize();
         if(dbsize==0) {
             PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
             PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
             wl.acquire();

             AsyncListViewLoader asyncTask = new AsyncListViewLoader();
             asyncTask.delegate = this;
             asyncTask.execute();

             wl.release();
           //  FirebaseCrash.report(new Throwable(new Exception("new user")));
             try {


             Global.seasonalAnime=AnimeS.listAll(AnimeS.class) ;
             }catch (Exception e){ e.printStackTrace();}
         }else
         {
             Global.AllAnime=myDBHandler.getAllAnimeFromAllAnime();
             try {
             Global.seasonalAnime=AnimeS.listAll(AnimeS.class) ;
             }catch (Exception e){ e.printStackTrace();}
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

            Global.AllAnime=result;

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

            result=la;

            myDBHandler.addToAllAnime(la);

            Log.d("animelist",la.size()+"");

           // SeasonAnimeStealer seasonAnimeStealer = new SeasonAnimeStealer() ;
         //Global.seasonalAnime =seasonAnimeStealer.stealSeasonJsoup();







            return result;
            // return null;
        }



    }

}