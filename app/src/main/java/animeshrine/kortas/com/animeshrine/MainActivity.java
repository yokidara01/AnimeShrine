package animeshrine.kortas.com.animeshrine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;


import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
        private Button fButton ;
    private SwipeMenuListView mListView;
    private String[] arrData = {
            "Harshal Benake 0",
            "Harshal Benake 1",
            "Harshal Benake 2",
            "Harshal Benake 3",
            "Harshal Benake 4",
            "Harshal Benake 5",
            "Harshal Benake 6",
            "Harshal Benake 7",
            "Harshal Benake 8",
            "Harshal Benake 9",
            "Harshal Benake 10"
    };
    private String[] myListName;
    private String[] myListUserStatu;
    List<Anime> animelist;
    private int listcount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MyDBHandler myDBHandler = new MyDBHandler(this, null, null, 1);
       // myDBHandler.updateNotFirstUse();


        /****************************************************/





    //TextView tv = (TextView) findViewById(R.id.textView);
   //     tv.setText(AnimeS.listAll(AnimeS.class).size());


        Button sbButton = (Button) findViewById(R.id.simplesearchviewByn);
        sbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SimpleSearch.class);
                startActivity(intent);

            }
        });


        fButton= (Button) findViewById(R.id.loadSeasonAnimebtn);
        try {


        if(Global.seasonalAnime.size()>0){
        fButton.setText(fButton.getText()+" "+ Global.seasonalAnime.size()+"already exist . ovewrite?");}
        }catch (Exception e){e.printStackTrace(); fButton.setText("Seasonal database empty");}

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoadSeasonAnime.class);
                startActivity(intent);

            }
        });






        /*********************************************************/

      /*  MobileAds.initialize(getApplicationContext(),"ca-app-pub-8576924018474704/4754853076");

        AdRequest adRequest = new AdRequest.Builder().build();

        AdView adView = (AdView) findViewById(R.id.adView);
        adView.loadAd(adRequest);*/


      //  UserInformation userInformation = new UserInformation(MainActivity.this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent = new Intent(this, SimpleSearch.class);



            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            /*Intent intent = new Intent(this, AnimeDetails.class);
             startActivity(intent);*/
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this,MyList.class);
             startActivity(intent);


        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this,RandomAnime.class);
            startActivity(intent);


        } else if (id == R.id.nav_seasonal) {

            Intent intent = new Intent(this,seasonalList.class);
            startActivity(intent);

        }/* else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }















    class AppAdapter extends BaseSwipListAdapter {

        @Override
        public int getCount() {
            return animelist.size();
        }

        @Override
        public Anime getItem(int position) {
            return animelist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_list_app_mylist, null);
                new MainActivity.AppAdapter.ViewHolder(convertView);
            }
            MyList.AppAdapter.ViewHolder holder = (MyList.AppAdapter.ViewHolder) convertView.getTag();
            Anime anime = getItem(position);
            holder.iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.nrt04calendarcover));
            holder.tv_name.setText(anime.getName());
            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "iv_icon_click", Toast.LENGTH_SHORT).show();
                }
            });
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "iv_icon_click", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }

        @Override
        public boolean getSwipEnableByPosition(int position) {
            if (position % 2 == 0) {
                return false;
            }
            return true;
        }
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
