package wolfsoft.invincible.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import wolfsoft.invincible.R;
import wolfsoft.invincible.model.Product;
import wolfsoft.invincible.utils.AndroidUtility;

/**
 * Created by kimbooX on 08/07/2016.
 */
public class ProductPopup extends Activity {


    private Product product;
    private TextView txt_tag;
    private LinearLayout field_layout;
    private TextView field_name;
    private TextView field_value;


    private ImageButton btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_popup);

        /***
         *  RETIEVE PRODUCT DATA
         */

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            try {
                String tag = bundle.getString("tag");
                product = Product.find(Product.class, "tag = ?", new String[]{tag}).get(0);
            }catch (Exception ss){
                AndroidUtility.alert(getApplicationContext(),ss.getMessage());
            }
        }
        /***
         *
         */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int hight = dm.heightPixels;
        getWindow().setLayout((int)(width*0.95),(int)(hight*0.95));


        /***
         * DELETE BTN
         */

        btn_delete  = (ImageButton) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });



        /***
         *
         */





        txt_tag = (TextView) findViewById(R.id.txt_tag);
            txt_tag.setText(product.getName());
            field_layout = (LinearLayout) findViewById(R.id.field_layout);

        LinearLayout layout_row = (LinearLayout) findViewById(R.id.layout_row);
        layout_row.setOrientation(LinearLayout.HORIZONTAL);

        try{
            for(int index=0; index<product.getFields().size(); index++) {

                LinearLayout  row = new LinearLayout(this);
                row.setLayoutParams(layout_row.getLayoutParams());

            field_name = new TextView(this);
            field_value = new TextView(this);

                field_name.setTextSize(25);
                field_value.setTextSize(25);

                field_value.setText(product.getFields().get(index).getFieldValue());
                field_name.setText(product.getFields().get(index).getFieldName()+": ");
                field_name.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                field_value.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                row.addView(field_name);
                row.addView(field_value);

                field_layout.addView(row);
            }
            }catch (Exception s){ AndroidUtility.alert(getApplicationContext(),s.getMessage()); }

    }

    private void deleteProduct(){

        try {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            try{
                                product.delete();
                                AndroidUtility.alert(getBaseContext(),"Términé!");
                                finish();
                            }catch (Exception c){
                                AndroidUtility.alert(getApplicationContext(),c.getMessage());
                            }
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }
                }
            };

            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
                alert.setMessage("Supprimer le produit "+product.getName()+" ?").setPositiveButton("Oui",dialogClickListener).setNegativeButton("Non",dialogClickListener).show();


        }catch (Exception c)
        {
            AndroidUtility.alert(getApplicationContext(),c.getMessage());
        }

    }


}
