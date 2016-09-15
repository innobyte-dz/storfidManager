package wolfsoft.invincible.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.orm.query.Select;

import customfonts.MyEditText;
import wolfsoft.invincible.R;
import wolfsoft.invincible.minime.CMD_Iso18k6cTagAccess;
import wolfsoft.invincible.minime.CMD_PwrMgt;
import wolfsoft.invincible.minime.MtiCmd;
import wolfsoft.invincible.minime.UsbCommunication;
import wolfsoft.invincible.model.Field;
import wolfsoft.invincible.model.Product;
import wolfsoft.invincible.utils.AndroidUtility;
import wolfsoft.invincible.utils.preferences.RfidAppPreferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListProductFragment extends Fragment {



    ImageButton btn_scan_tag;
    MyEditText field_duree;
    MyEditText article_nom;
    List<Product> result_products = new ArrayList<>();

    Boolean isMultiple = true;
    Boolean isKeepHistory = false;

    CheckBox checkBox_multiple;
    CheckBox checkBox_keep;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RfidAppPreferences sharedPreferences;

    public static final int REQUEST_CODE = 1;
    public static final int PERIOD = 400;
    ArrayList<String> scannedTagsList = new ArrayList<>();
    private UsbCommunication mUsbCommunication = UsbCommunication.newInstance();
    private MtiCmd mMtiCmd;
    RecyclerView recyclerView;
    List<Product> result_filtred_article = new ArrayList<>();
    private int scantimes;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListProductFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListProductFragment newInstance(int columnCount) {
        ListProductFragment fragment = new ListProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = RfidAppPreferences.getInstance(getActivity().getApplicationContext());
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);



            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            Product p = new Product();
            recyclerView.setAdapter(new MyProductRecyclerViewAdapter(p.listAll(Product.class), mListener));

        checkBox_keep = (CheckBox) view.findViewById(R.id.checkBox_keep);
        checkBox_multiple = (CheckBox) view.findViewById(R.id.checkBox_multiple);

        checkBox_keep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               isKeepHistory = checkBox_keep.isChecked();
            }
        });

        checkBox_multiple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMultiple = checkBox_multiple.isChecked();
            }
        });




        field_duree = (MyEditText) view.findViewById(R.id.field_duree);
        btn_scan_tag = (ImageButton) view.findViewById(R.id.btn_scan_tag);
        btn_scan_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanATag();
            }
        });
        article_nom = (MyEditText) view.findViewById(R.id.article_nom);

        result_filtred_article.clear();
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // s could be name,or value, or category
                result_filtred_article.clear();
                try {
                    for (Product p : Product.listAll(Product.class)) {
                        if (p.getName().toLowerCase().contains(s.toString()) || p.getCategory().toLowerCase().contains(s.toString())) {
                            if (!result_filtred_article.contains(p)) {
                                result_filtred_article.add(p);
                            }
                        }
                    }
                    //result_filtred_article.clear();
                    recyclerView.setAdapter(new MyProductRecyclerViewAdapter(result_filtred_article, mListener));
                    AndroidUtility.alert(getContext(), Field.listAll(Field.class).size() + "");
                }catch (Exception ss){
                    AndroidUtility.alert(getActivity(),"Erreur result_filtred_article");
                    return;
                }
            }
        };
        article_nom.addTextChangedListener(watcher);


        return view;
    }

    private boolean getUsbState() {
        return sharedPreferences.isDeviceConnected();
    }


    private void showMoreThanOneTagScanned() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Plusieurs tags scanner");
        alert.setMessage("Vous avez scanner plusieurs tags, merci de separer les tags et refaire le scan");
        alert.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert.show();
    }
    ///////////////////////////////////////////////////////////////////////////////////*

    /**
     *  SCAn TAGS
     */

    public static ArrayList<String> tagList = new ArrayList<String>();

    public void scanATag() {

        if( Integer.parseInt(field_duree.getText().toString()) == 0 || field_duree.getText().toString().isEmpty() || field_duree.getText().toString() == "" ){
            AndroidUtility.alert(getContext(),"Vérifier la durée de scan!");
            return;
        }



        final Handler handler = new Handler();
        scantimes = 20;
        try {
            scantimes = Integer.parseInt(field_duree.getText().toString()); // For tests only
        }catch (Exception c){
            AndroidUtility.alert(getContext(),"Vérifier la durée de scan! valeur numérique");
            scantimes = 20;
        }
        if(getUsbState()) {
            final ProgressDialog mProgDlg = ProgressDialog.show(getActivity(), "Inventory", "Searching ...", true);
            final int finalScantimes = scantimes;

            new Thread() {
                int numTags;
                String tagId;
                ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

                public void run() {
                    tagList.clear();

                    outerloop:
                    for(int i = 0; i < finalScantimes; i++) {
                        mMtiCmd = new CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory(mUsbCommunication);
                        CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory finalCmd = (CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory) mMtiCmd;

                        if(finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.StartInventory)) {
                            tagId = finalCmd.getTagId();
                            if(finalCmd.getTagNumber() > 0) {
                                tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                                if(!tagList.contains(tagId)){
                                    tagList.add(tagId);
                                }
//								finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.GetAllTags);
                            }

                            for(numTags = finalCmd.getTagNumber(); numTags > 1; numTags--) {
                                if(finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.NextTag)) {
                                    tagId = finalCmd.getTagId();
                                }
                            }
                            Collections.sort(tagList);
                        } else {
                            // #### process error ####
                        }
                    }
                    mProgDlg.dismiss();
                    setPowerState();
                    handler.post(updateResult);

                }

                final Runnable updateResult = new Runnable() {
                    @Override
                    public void run() {
                        if (tagList.size() > 0) {
                        try {
                                result_products.clear();
                            if(!isMultiple && tagList.size() > 1){
                                mProgDlg.dismiss();
                                showMoreThanOneTagScanned();
                            }else {
                                for (String tag : tagList) {

                                    List<Product> resultat = Product.find(Product.class, "tag= ?", new String[]{tag});
                                    try {
                                        if (!result_products.contains(resultat.get(0)) && resultat.size() > 0) {
                                            result_products.add(resultat.get(0));
                                        }
                                    } catch (Exception c) {
                                        // AndroidUtility.alert(getContext(),"resultat SQL egal 0");
                                    }
                                }
                            }
                                // AndroidUtility.alert(getContext(), "result_products SIZE:" + result_products.size());
                                recyclerView.setAdapter(new MyProductRecyclerViewAdapter(result_products, mListener));

                            }catch(Exception ss){
                               // AndroidUtility.alert(getContext(), ss.getMessage());
                            }finally{
                                // currentThread().interrupt();
                            }
                        }else{
                            AndroidUtility.alert(getContext(),"tagList Size = 0");
                        }
                    }
                };
            }.start();
        } else
            Toast.makeText(getActivity(), "The Reader is not connected", Toast.LENGTH_SHORT).show();

    }
    private void killThread(){

    }

    public void stopInventory() {

        try {
            recyclerView.setAdapter(new MyProductRecyclerViewAdapter(result_filtred_article, mListener));
        }catch (Exception ss){
            AndroidUtility.alert(getActivity(),"Erreur! stopInventory "+ss.getMessage());
        }
    }


    private void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
        }
    }
    private void setPowerState() {
        try {
            MtiCmd mMtiCmd = new CMD_PwrMgt.RFID_PowerEnterPowerState(mUsbCommunication);
            CMD_PwrMgt.RFID_PowerEnterPowerState finalCmd = (CMD_PwrMgt.RFID_PowerEnterPowerState) mMtiCmd;
            finalCmd.setCmd(CMD_PwrMgt.PowerState.Sleep);
            sleep(200);
        }catch (Exception ss){
            AndroidUtility.alert(getActivity(),"Vérifier le lecteur: "+ ss.getMessage());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Product mItem);
        // TODO: Update argument type and name


    }



}