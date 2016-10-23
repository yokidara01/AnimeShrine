package animeshrine.kortas.com.animeshrine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import it.michelelacorte.elasticprogressbar.ElasticDownloadView;


public class LoadSeasonAnime extends AppCompatActivity {

   public ElasticDownloadView mElasticDownloadView;

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_season_anime);

              mElasticDownloadView = (ElasticDownloadView) findViewById(R.id.progresLastik);

                AsyncListViewLoader asyncTask = new AsyncListViewLoader();
                mElasticDownloadView.startIntro();
                asyncTask.execute();






            }




    private class AsyncListViewLoader extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
                    float prog = (values[0]*100)/values[1] ;
            mElasticDownloadView.setProgress(prog);

                if (values[0]==values[1])
                {
                    mElasticDownloadView.setProgress(100);
                    mElasticDownloadView.success();
                    Toast.makeText(LoadSeasonAnime.this,+values[1]+" Anime of this season are added to your database",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoadSeasonAnime.this,MainActivity.class);
                    startActivity(intent);
                }
           // Toast.makeText(LoadSeasonAnime.this,values[1]+"",Toast.LENGTH_SHORT).show();
           //
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

        }

        @Override
        protected Integer doInBackground(String... params) {
          int prog = 1 ;
            SeasonAnimeStealer seasonAnimeStealer = new SeasonAnimeStealer();
            seasonAnimeStealer.getIdsSeason();

            List<AnimeS> animesfinal =new ArrayList<>();
            Document doc = null;
            AnimeS anime ;
            AnimeS.deleteAll(AnimeS.class);
            int  i=0;
            for(int id : seasonAnimeStealer.IDs) {
                try {

                    AnimeS animeS = new AnimeS();
                    animeS = seasonAnimeStealer.getAnime(id);

                    animeS.save();
                    animesfinal.add(animeS);
                    publishProgress(i,seasonAnimeStealer.IDs.size());

                    i++;


                } catch (Exception e) {
                    e.printStackTrace();

                }


            }

            Global.seasonalAnime=animesfinal ;



            return null;
        }
    }

}
