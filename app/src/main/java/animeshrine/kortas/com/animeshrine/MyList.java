package animeshrine.kortas.com.animeshrine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;


import java.util.ArrayList;
import java.util.List;


public class MyList extends AppCompatActivity {


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

        setContentView(R.layout.activity_my_list);




        View parentLayout = findViewById(R.id.rootlayout);
        Snackbar.make(parentLayout, "Hint:", Snackbar.LENGTH_LONG)
                .setAction("Slide on Any anime to see options", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();




        SwipeMenuListView swipeMenuListView = (SwipeMenuListView) findViewById(R.id.lvmylist);

        MyDBHandler myDBHandler = new MyDBHandler(this, null, null, 1);
        listcount = myDBHandler.getAllAnime().size();
        animelist = myDBHandler.getAllAnime();
        myListName = new String[listcount];
        myListUserStatu = new String[listcount];
        int i = 0;
        for (Anime a : animelist) {
            myListName[i] = a.getName();
            myListUserStatu[i] = a.getUserStatus();
            i++;


        }


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "item1"
                SwipeMenuItem item1 = new SwipeMenuItem(MyList.this);
                // set item background
                item1.setBackground(new ColorDrawable(Color.parseColor("#FF9933")));
                // set item width
                item1.setWidth(100);

                // set item title
                item1.setTitle("Status");
                // set item title fontsize
                item1.setTitleSize(18);
                // set item title font color
                item1.setTitleColor(Color.WHITE);
                // set a icon
                //  item1.setIcon(R.drawable.ic_launcher);
                // add to menu
                menu.addMenuItem(item1);

                // create "item2"
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                // set item background
                item2.setBackground(new ColorDrawable(Color.WHITE));
                // set item width
                item2.setWidth(100);
                // set a icon
                item2.setIcon(R.drawable.ic_menu_share);
                // add to menu
                menu.addMenuItem(item2);

                // create "item3"
              //  SwipeMenuItem item3 = new SwipeMenuItem(getApplicationContext());
                // set item background
              //  item3.setBackground(new ColorDrawable(Color.parseColor("#138808")));
                // set item width
              //  item3.setWidth(100);
                // set a icon
                //item3.setIcon(R.drawable.ic_launcher);
                // set item title
               // item3.setTitle("Status");
                // set item title fontsize
               // item3.setTitleSize(18);
                // set item title font color
               // item3.setTitleColor(Color.WHITE);
                // add to menu
             //   menu.addMenuItem(item3);
            }
        };

        // set creator
        swipeMenuListView.setMenuCreator(creator);

        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(MyList.this, "Item 1 pressed", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MyList.this, "Item 2 pressed", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MyList.this, "Item 3 pressed", Toast.LENGTH_SHORT).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        // Right
        // swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

        // Left
        swipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


        swipeMenuListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
                System.out.println("setOnSwipeListener onSwipeStart");

            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
                System.out.println("setOnSwipeListener onSwipeEnd");

            }
        });

        ArrayList arrayList = new ArrayList<>();

        AppAdapter customAdapter = new AppAdapter();
        swipeMenuListView.setAdapter(customAdapter);

    }

    /**
     * Custom Adapter for listview.
     */

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
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            Anime anime = getItem(position);
            holder.iv_icon.setImageDrawable(getResources().getDrawable(R.drawable.nrt04calendarcover));
            holder.tv_name.setText(anime.getName());
            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyList.this, "iv_icon_click", Toast.LENGTH_SHORT).show();
                }
            });
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyList.this, "iv_icon_click", Toast.LENGTH_SHORT).show();
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









   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_swipe_lv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_left) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            return true;
        }
        if (id == R.id.action_right) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}