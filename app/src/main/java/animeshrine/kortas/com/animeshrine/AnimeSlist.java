package animeshrine.kortas.com.animeshrine;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

public class AnimeSlist extends AppCompatActivity {


    public List<Anime> animesSafe;




    public ListView customListView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_slist);
        customListView = (ListView) findViewById(R.id.custom_ListView);

    }
}