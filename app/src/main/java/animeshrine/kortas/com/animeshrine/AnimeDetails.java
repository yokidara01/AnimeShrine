package animeshrine.kortas.com.animeshrine;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.io.InputStream;

public class AnimeDetails extends AppCompatActivity {

    ImageView img ;
    TextView name ,rank;
   Button mallink ;
    String NameFromIntent;
    Button b1,b2,b3 ;
    MyDBHandler dbHandler;
    Anime a  ;
    ListView genres ;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    JustifiedTextView desc ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.e("animedesc","test");
        dbHandler = new MyDBHandler(this, null, null, 1);



// custom dialog
        final Dialog dialog = new Dialog(AnimeDetails.this);
        dialog.setContentView(R.layout.dialog_for_add_to_list);
        dialog.setTitle("Add Anime to my list");


        b1=(Button) dialog.findViewById(R.id.button1) ;
        b2=(Button) dialog.findViewById(R.id.button2) ;
        b3=(Button) dialog.findViewById(R.id.button3) ;

        // set the custom dialog components - text, image and button






        // if button is clicked, close the custom dialog

        a = Global.acAnime;




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a.setUserStatus("Watching");
                        dbHandler.addAnime(a);
                        dialog.dismiss();

                    }
                });

                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a.setUserStatus("To Watch");
                        dbHandler.addAnime(a);
                        dialog.dismiss();

                    }
                });

                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a.setUserStatus("Watched");
                        dbHandler.addAnime(a);
                        dialog.dismiss();

                    }
                });


                Log.e("DBDump", dbHandler.databaseToString());

      /*    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




     /*   */


        genres = (ListView) findViewById(R.id.genreslv)  ;

        Log.e("accAnime", a.toString());

       rank =(TextView) findViewById(R.id.rank) ;
        desc =(JustifiedTextView) findViewById(R.id.descjustified) ;
        rank.setText(a.getRank()+"");
        mallink = (Button) findViewById(R.id.loadSeasonAnimebtn);
        mallink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/anime/"+a.getId()));
                startActivity(browserIntent);
            }
        });
       int i=0;

        String[] values = new String[a.getGenres().size()];
        for( String gr :a.getGenres())
        {
           values[i]=gr;
            i++;

        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        genres.setAdapter(adapter);

        collapsingToolbarLayout =(CollapsingToolbarLayout) findViewById(R.id.toolbar_layout) ;

       // name.setText("dsfds");
        collapsingToolbarLayout.setTitle(a.getName());
      //  description.setText("qshdqklmshdjklqshdklqjshdjkl");
        desc.setText(a.getDesc());
try {


        new DownloadImageTask((ImageView) findViewById(R.id.img))
                .execute(a.getImg());
}catch (Exception e){e.printStackTrace();
    Toast.makeText(AnimeDetails.this,e.getMessage(),Toast.LENGTH_LONG).show();
}






    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(this, SimpleSearch.class);
        startActivity(intent);
    }


    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
              //  Log.e("Error", e.getMessage());

                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
