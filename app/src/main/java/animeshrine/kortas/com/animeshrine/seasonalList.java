package animeshrine.kortas.com.animeshrine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class seasonalList extends AppCompatActivity {
    ListView mListView;
    String[] animes;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasonal_list);

        mListView = (ListView) findViewById(R.id.lsSeason);

        int i=0;
        animes = new String[Global.seasonalAnime.size()] ;
        for (AnimeS a   : Global.seasonalAnime)
        {
            animes[i]= a.name ;
            i++;
        }

        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(seasonalList.this,
                android.R.layout.simple_list_item_1,animes);
        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Anime animeAux = new Anime();
                AnimeS a = Global.seasonalAnime.get(position);
                Toast.makeText(seasonalList.this,a.name,Toast.LENGTH_SHORT).show();
                animeAux.setDesc(a.desc);
                animeAux.setName(a.name);
                animeAux.setImg(a.img);
                animeAux.setGenres(a.genres);
                animeAux.setId(a.malID);
                animeAux.setImg("");
                  animeAux.setRank(a.rank);
                    Global.acAnime=animeAux;

                Intent i = new Intent(seasonalList.this, AnimeDetails.class);
                startActivity(i);
            }
        });
    }
}
