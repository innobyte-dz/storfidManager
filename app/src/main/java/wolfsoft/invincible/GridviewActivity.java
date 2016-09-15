package wolfsoft.invincible;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class GridviewActivity extends BaseActivity {


    private LinearLayout profile;
    private LinearLayout album;
    private LinearLayout contact;

    ExpandableHeightGridView gridview;
    ExpandableHeightGridView gridview2;
    ExpandableHeightGridView gridview3;

    // Put your images from draweble folder

    // Put Images at here for Gridview1
    private int[] Image = {R.drawable.tom,R.drawable.d,R.drawable.white,R.drawable.e,R.drawable.b,R.drawable.g};

    // Put Images at here for Gridview2
    private int[] Image2 = {R.drawable.b,R.drawable.a,R.drawable.white,R.drawable.e,R.drawable.b,R.drawable.g};

    // Put Images at here for Gridview3
    private int[] Image1 = {R.drawable.g,R.drawable.e,R.drawable.tom,R.drawable.d,R.drawable.b,R.drawable.white};


    // For Gridview1
    private ArrayList<BeanclassForGridView> beans;
    private GridviewAdapter gridviewAdapter;


    // For Gridview2
    private ArrayList<BeanclassForGridView> beans1;
    private GridviewFirstAdapter gridviewFirstAdapter;


    // For Gridview3
    private GridviewSecondAdapter gridviewSecondAdapter;
    private ArrayList<BeanclassForGridView> beans2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album);
        Context context;
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.white);


        profile = (LinearLayout) findViewById(R.id.profile);
        contact = (LinearLayout) findViewById(R.id.contact);

        ImageView imgv = (ImageView) findViewById(R.id.banar1);

        //  Bitmap bitmap = StringToBitMap(imgv);
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

  // Find all Gridviews Id
        gridview = (ExpandableHeightGridView) findViewById(R.id.gridview);
        gridview2 = (ExpandableHeightGridView) findViewById(R.id.gridview2);
        gridview3 = (ExpandableHeightGridView) findViewById(R.id.gridview3);
        beans = new ArrayList<BeanclassForGridView>();


        // Put Images in ArrayList For Gridview1
        for (int i = 0; i < Image.length; i++) {

            BeanclassForGridView beanclass = new BeanclassForGridView(Image[i]);
            beans.add(beanclass);

        }
        gridviewAdapter = new GridviewAdapter(GridviewActivity.this, beans);
        gridview.setExpanded(true);
        gridview.setAdapter(gridviewAdapter);

        // Put Images in ArrayList For Gridview2
        beans1 = new ArrayList<BeanclassForGridView>();

        for (int i = 0; i < Image1.length; i++) {

            BeanclassForGridView beanclass = new BeanclassForGridView(Image1[i]);
            beans1.add(beanclass);

        }
        gridviewFirstAdapter = new GridviewFirstAdapter(GridviewActivity.this, beans1);
        gridview2.setExpanded(true);
        gridview2.setAdapter(gridviewFirstAdapter);


        // Put Images in ArrayList For Gridview3
        beans2 = new ArrayList<BeanclassForGridView>();

        for (int i = 0; i < Image2.length; i++) {

            BeanclassForGridView beanclass = new BeanclassForGridView(Image2[i]);
            beans2.add(beanclass);

        }
        gridviewSecondAdapter = new GridviewSecondAdapter(GridviewActivity.this, beans2);
        gridview3.setExpanded(true);
        gridview3.setAdapter(gridviewSecondAdapter);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(GridviewActivity.this, MainActivity.class);
                startActivity(it);
            }
        });


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(GridviewActivity.this, ListviewActivity.class);
                startActivity(it);
            }
        });




    }


}
