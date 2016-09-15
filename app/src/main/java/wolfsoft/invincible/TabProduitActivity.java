package wolfsoft.invincible;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import wolfsoft.invincible.Fragments.AddArticleFragment;
import wolfsoft.invincible.Fragments.AddCategoryFragment;
import wolfsoft.invincible.Fragments.AddFieldFragment;
import wolfsoft.invincible.Fragments.ListProductFragment;
import wolfsoft.invincible.Fragments.ProductPopup;
import wolfsoft.invincible.model.Product;
import wolfsoft.invincible.utils.AndroidUtility;

public class TabProduitActivity extends BaseActivity implements ListProductFragment.OnListFragmentInteractionListener, AddArticleFragment.OnFragmentInteractionListener
        , AddCategoryFragment.OnFragmentInteractionListener,AddFieldFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_produit);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Produits");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            // Add icon to tab here
            //   tabLayout.getTabAt(i).setIcon(R.drawable.ic_symbol);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListProductFragment(), "Liste Produits");
        adapter.addFragment(new AddArticleFragment(), "Ajouter produit");
        adapter.addFragment(new AddCategoryFragment(), "Ajouter catégorie");
        adapter.addFragment(new AddFieldFragment(),"Ajouter attribut");

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {



    }

    @Override
    public void onListFragmentInteraction(Product mItem) {
        Intent tent = new Intent(getApplication(), ProductPopup.class);
        Bundle  b = new Bundle ();
        b.putString("tag",mItem.getTagId());
        tent.putExtras(b);
        startActivity(tent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TabProduit Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://wolfsoft.invincible/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TabProduit Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://wolfsoft.invincible/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public Bitmap grabImage(Uri imageUri) {
            getContentResolver().notifyChange(imageUri, null);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;
            try {
                bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr,
                        imageUri);

                int width = 600;
                int height = 600;

                Matrix matrix = new Matrix();

                // rotate the Bitmap
                matrix.postRotate(90);

                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                        true);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
          return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
