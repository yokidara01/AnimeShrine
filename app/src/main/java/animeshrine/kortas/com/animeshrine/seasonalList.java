package animeshrine.kortas.com.animeshrine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class seasonalList extends AppCompatActivity {
    ListView mListView;
    String[] animes;

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
    }
}
