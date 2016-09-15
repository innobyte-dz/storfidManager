package wolfsoft.invincible;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;



public class ListviewActivity extends BaseActivity {

    private ListView listview;
    private LinearLayout album;
    private LinearLayout profile;

    int number = 1;


    private int image;
    private String title;
    private String description;
    private String price;


    public int[] IMAGE = {R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white, R.drawable.white};
    public String[] TITLE = {"Andrew Simons","Andrew Simons","Andrew Simons","Andrew Simons","Andrew Simons","Andrew Simons","Andrew Simons","Andrew Simons"};
    public String[] DESCRIPTION = {"CEO", "CEO", "CEO", "CEO", "CEO", "CEO","CEO","CEO"};


    private ArrayList<BeanClassForListView> beanClassArrayList;
    private listViewAdapter listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_listview);



        profile = (LinearLayout)findViewById(R.id.profile);
        album = (LinearLayout)findViewById(R.id.album);

        listview = (ListView) findViewById(R.id.listview);
        beanClassArrayList = new ArrayList<BeanClassForListView>();


        for (int i = 0; i < TITLE.length; i++) {

            BeanClassForListView beanClass = new BeanClassForListView(IMAGE[i], TITLE[i], DESCRIPTION[i]);
            beanClassArrayList.add(beanClass);

        }
        listViewAdapter = new listViewAdapter(ListviewActivity.this, beanClassArrayList);
        listview.setAdapter(listViewAdapter);




        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(ListviewActivity.this, MainActivity.class);
                startActivity(it);
            }
        });





        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(ListviewActivity.this, GridviewActivity.class);
                startActivity(it);
            }
        });



    }
}



