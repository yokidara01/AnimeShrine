package animeshrine.kortas.com.animeshrine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SimpleSearch extends AppCompatActivity implements AsyncResponse {
    private cstmAdptr adpt;
   public List<Anime> animesSafe;
    public List<Anime> animes;
    public FloatingActionButton resetBtn;

    public EditText searchtxt  ;
public  ListView customListView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_search);
       customListView = (ListView) findViewById(R.id.custom_ListView);
        adpt = new cstmAdptr(animesSafe, this);

        customListView.setAdapter(adpt);
        AsyncListViewLoader asyncTask =new AsyncListViewLoader();
        asyncTask.delegate = this;
        asyncTask.execute();
        searchtxt = (EditText) findViewById(R.id.inputSearch) ;
        resetBtn =(FloatingActionButton) findViewById(R.id.fab);
        animes=adpt.getItemList();
        searchtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

               int textlength = s.length();
                ArrayList<Anime> tempArrayList = new ArrayList<Anime>();
                for(Anime a: adpt.getItemList()){
                    if (textlength <= a.getName().length()) {
                        if (a.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            tempArrayList.add(a);
                        }
                    }
                }

                adpt.setItemList(tempArrayList);
                customListView.setAdapter(adpt);


               // adpt.getFilter().filter(s.toString());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animesSafe= Global.MyAnimeList;
                Toast.makeText(SimpleSearch.this,animesSafe.size()+"",Toast.LENGTH_SHORT).show();
                searchtxt.setText(null);
                adpt.setItemList(animesSafe);
                adpt.notifyDataSetChanged();
                customListView.setAdapter(adpt);
                adpt.notifyDataSetChanged();
            }
        });

     //   (new AsyncListViewLoader()).execute();


        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SimpleSearch.this,adpt.getItem(position).getId()+"",Toast.LENGTH_SHORT).show();
                Global.acAnime=adpt.getItem(position);
                Intent intent = new Intent(SimpleSearch.this ,AnimeDetails.class);
                intent.putExtra("name",adpt.getItem(position).getName()) ;
                startActivity(intent);


            }
        });


        searchtxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    animesSafe= Global.MyAnimeList;
                    ArrayList<Anime> tempArrayList = new ArrayList<Anime>();
                    for(Anime a: adpt.getItemList()){
                        if (searchtxt.getText().length() <= a.getName().length()) {
                            if (a.getName().toLowerCase().contains(searchtxt.getText().toString().toLowerCase())) {
                                tempArrayList.add(a);
                            }
                        }
                    }

                    adpt.setItemList(tempArrayList);
                    customListView.setAdapter(adpt);


                }
                return false;
            }
        });

    }

    @Override
    public void processFinish(List<Anime> output) {

    }


    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Anime>> {
        public AsyncResponse delegate = null;
        private final ProgressDialog dialog = new ProgressDialog(SimpleSearch.this);

        @Override
        protected void onPostExecute(List<Anime> result) {
            super.onPostExecute(result);
            delegate.processFinish(result);
           // result.clear();
          //  Anime a = new Anime() ;
            //a.setName("qsdkjlmkq");

          //  result.add(a);

            adpt.setItemList(result);
            adpt.notifyDataSetChanged(); dialog.dismiss();
            Global.MyAnimeList=result;

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

            try {
               // XMLPullParserHandler parser = new XMLPullParserHandler();
              //  result = parser.parse(getAssets().open("animelist.xml"));
                result= Global.AllAnime;
                Log.d("resultSize",result.size()+"") ;

            }catch (Exception e){e.printStackTrace();}

            return result;
           // return null;
        }



    }
}