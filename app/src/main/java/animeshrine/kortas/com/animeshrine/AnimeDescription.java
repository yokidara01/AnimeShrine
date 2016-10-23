package animeshrine.kortas.com.animeshrine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimeDescription extends AppCompatActivity {

    public TextView name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_description);
            name= (TextView) findViewById(R.id.name) ;


        List<String> ls1 = new ArrayList<>();
        List<String> ls2 = new ArrayList<>();

        ls1.add("1");ls1.add("2");ls1.add("3");ls1.add("4");ls1.add("5");
        ls2=ls1;
        Collections.shuffle(ls2);
        System.out.print(ls1.get(1));System.out.print(ls1.get(2));System.out.print(ls1.get(3));
        name.setText(this.getIntent().getStringExtra("name")) ;

    }
}
