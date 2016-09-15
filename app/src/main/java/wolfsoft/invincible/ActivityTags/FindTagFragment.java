package wolfsoft.invincible.ActivityTags;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import customfonts.MyEditText;
import wolfsoft.invincible.Fragments.MyProductRecyclerViewAdapter;
import wolfsoft.invincible.R;
import wolfsoft.invincible.minime.CMD_Iso18k6cTagAccess;
import wolfsoft.invincible.minime.CMD_PwrMgt;
import wolfsoft.invincible.minime.MtiCmd;
import wolfsoft.invincible.minime.UsbCommunication;
import wolfsoft.invincible.model.Category;
import wolfsoft.invincible.model.Field;
import wolfsoft.invincible.model.Product;
import wolfsoft.invincible.utils.AndroidUtility;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindTagFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindTagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindTagFragment extends Fragment {

    MyEditText txt_product_name;
    List<Product> result_filtred_article = new ArrayList<>();
    ArrayAdapter<String> adapter_product;
    Spinner spinner_product;
    Switch btn_scan;
    Thread threadScan;
    String selectedProduct;
    ImageView imageView2;
    TextView textView_result;
    TextView article_tagcode;
    ScanThread scanThread;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FindTagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindTagFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindTagFragment newInstance(String param1, String param2) {
        FindTagFragment fragment = new FindTagFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        threadScan = new Thread();

    }

    private void setupDropboxProducts(List<Product> products, Spinner spinner_product){

        List<String> spinnerArray_prod =  new ArrayList<String>();
        for (Product cat : products){
            spinnerArray_prod.add(cat.getName());
        }

        adapter_product = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray_prod);
        adapter_product.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_product.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_product.setAdapter(adapter_product);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_find_tag, container, false);

        spinner_product = (Spinner)v.findViewById(R.id.spinner_product);
        setupDropboxProducts(Product.listAll(Product.class),spinner_product);
        spinner_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selectedProduct = Product.find(Product.class, "name = ?", new String[]{spinner_product.getSelectedItem().toString()}).get(0).getTagId();
                }catch (Exception c){
                    AndroidUtility.alert(getContext(),c.getMessage());
                }
                article_tagcode.setText(selectedProduct);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                article_tagcode.setText("...");
            }
        });

        txt_product_name = (MyEditText) v.findViewById(R.id.txt_product_name);
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
                    setupDropboxProducts(result_filtred_article,spinner_product);
                }catch (Exception ss){
                    AndroidUtility.alert(getActivity(),"Erreur result_filtred_article");
                    return;
                }
            }
        };
        txt_product_name.addTextChangedListener(watcher);
        article_tagcode = (TextView) v.findViewById(R.id.article_tagcode);

        btn_scan = (Switch)v.findViewById(R.id.btn_scan);
        btn_scan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(btn_scan.isChecked()){
                    if(selectedProduct=="" || selectedProduct.isEmpty()){
                        AndroidUtility.alert(getContext(),"Séléctionner un produit d'abord");
                    }else {

                        AndroidUtility.alert(getContext(),"selectedProduct "+selectedProduct);
                        if(scanThread == null){
                            scanThread = new ScanThread(selectedProduct);
                        }
                        scanThread.start();
                    }
                }else if(!btn_scan.isChecked()){
                   // imageView2.setImageResource(R.drawable.ic_scan_result);
                  //  textView_result.setText("...");
                  //  AndroidUtility.alert(getContext(),"STOP "+selectedProduct);
                  //  scanThread.interrupt();
                  //  scanThread = null;
                }
            }
        });
        imageView2 = (ImageView) v.findViewById(R.id.imageView2);
        textView_result  =(TextView) v.findViewById(R.id.textView_result);
        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /***
     *
     */

    private MtiCmd mMtiCmd;
    private UsbCommunication mUsbCommunication = UsbCommunication.newInstance();
    final Handler handler = new Handler();


    class ScanThread extends Thread{
        private Boolean stillWorking = true;
        private String tag;
        public String getTag() {
            return tag;
        }
        public void setTag(String tag) {
            this.tag = tag;
        }
        public ScanThread(String _tag) {
                this.tag=_tag;
        }

        int numTags;
        String tagId;
        ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        @Override
        public void interrupt() {
            super.interrupt();
            stillWorking = false;
        }

        @Override
        public void run() {
            super.run();
            try{
                while(stillWorking) {
                    if (interrupted()) {
                        throw new InterruptedException();
                    }
                    mMtiCmd = new CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory(mUsbCommunication);
                    CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory finalCmd = (CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory) mMtiCmd;

                    if(finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.StartInventory)) {
                        tagId = finalCmd.getTagId();
                        if(finalCmd.getTagNumber() > 0) {

                            if(tagId.equals(selectedProduct)){
                                tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                                imageView2.setImageResource(R.drawable.ic_scan_result_ok);
                                textView_result.setText(selectedProduct);
                            }else{
                            imageView2.setImageResource(R.drawable.ic_scan_result);
                            textView_result.setText("...");
                            }
//								finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.GetAllTags);
                        }
                        for(numTags = finalCmd.getTagNumber(); numTags > 1; numTags--) {
                            if(finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.NextTag)) {
                                tagId = finalCmd.getTagId();
                            }
                        }

                    } else {
                        // #### process error ####
                    }
                }
                setPowerState();

            }catch (InterruptedException c){
                AndroidUtility.alert(getContext(),c.getMessage());
             //   interrupt();
            }
        }


        public void setPowerState() {
            try {
                MtiCmd mMtiCmd = new CMD_PwrMgt.RFID_PowerEnterPowerState(mUsbCommunication);
                CMD_PwrMgt.RFID_PowerEnterPowerState finalCmd = (CMD_PwrMgt.RFID_PowerEnterPowerState) mMtiCmd;
                finalCmd.setCmd(CMD_PwrMgt.PowerState.Sleep);
                sleep(200);
            }catch (Exception ss){
                AndroidUtility.alert(getActivity(),"Vérifier le lecteur: "+ ss.getMessage());
            }
        }
    }
}

