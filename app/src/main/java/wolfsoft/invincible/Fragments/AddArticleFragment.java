package wolfsoft.invincible.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import customfonts.MyEditText;
import wolfsoft.invincible.R;
import wolfsoft.invincible.minime.CMD_Iso18k6cTagAccess;
import wolfsoft.invincible.minime.CMD_PwrMgt;
import wolfsoft.invincible.minime.MtiCmd;
import wolfsoft.invincible.minime.UsbCommunication;
import wolfsoft.invincible.model.Action;
import wolfsoft.invincible.model.Category;
import wolfsoft.invincible.model.Field;
import wolfsoft.invincible.model.FieldList;
import wolfsoft.invincible.model.Product;
import wolfsoft.invincible.model.Zone;
import wolfsoft.invincible.utils.AndroidUtility;
import wolfsoft.invincible.utils.preferences.RfidAppPreferences;
import wolfsoft.invincible.utils.storage.FileManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddArticleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddArticleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CAMERA_REQUEST = 1888;
    private static final int RESULT_OK = -1 ;


    RfidAppPreferences sharedPreferences;
    Timer timer;
    public static final int REQUEST_CODE = 1;
    public static final int PERIOD = 400;
    ArrayList<String> scannedTagsList = new ArrayList<>();
    private UsbCommunication mUsbCommunication = UsbCommunication.newInstance();
    private MtiCmd mMtiCmd;



    TextView iSearching;
    customfonts.MyEditText scannedTag;
    ImageButton chargerPhoto;
    ImageButton scanTag;
    ImageButton btnSave;
    customfonts.MyEditText productName;
    Uri imageUri;
    customfonts.MyEditText description;
    customfonts.MyEditText point;
    ImageView image_taken;
    TextView label_searching;
    MyEditText article_field;
    ImageButton btn_add_field;


    Spinner listZone;
    Spinner category;
    Spinner spinner_field;

    Bitmap bitmap = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddArticleFragment newInstance(String param1, String param2) {
        AddArticleFragment fragment = new AddArticleFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_article, container, false);

        chargerPhoto = (ImageButton) v.findViewById(R.id.btn_photo);
        scanTag = (ImageButton) v.findViewById(R.id.btn_scan_tag);
        btnSave  =(ImageButton) v.findViewById(R.id.btn_save);
        productName = (customfonts.MyEditText) v.findViewById(R.id.article_nom);
        listZone = (Spinner) v.findViewById(R.id.spinner_zone);
        category = (Spinner) v.findViewById(R.id.spinner_category);
        description = (customfonts.MyEditText) v.findViewById(R.id.article_description);
        point = ( customfonts.MyEditText ) v.findViewById(R.id.article_point);
        iSearching = (TextView) v.findViewById(R.id.iSearching);
        image_taken = (ImageView) v.findViewById(R.id.image_taken);
        scannedTag = (MyEditText) v.findViewById(R.id.article_tagcode) ;
        label_searching = (TextView) v.findViewById(R.id.label_searching) ;
        spinner_field = (Spinner) v.findViewById(R.id.spinner_field);
        article_field = (MyEditText) v.findViewById(R.id.article_field);
        Spinner spinner_mesure = (Spinner)v.findViewById(R.id.spinner_unite_mesure);
        ArrayAdapter<CharSequence> adapter_mesure = ArrayAdapter.createFromResource(getContext(),
                R.array.mesure_array, android.R.layout.simple_spinner_item);
        adapter_mesure.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mesure.setAdapter(adapter_mesure);

        btn_add_field  =(ImageButton) v.findViewById(R.id.btn_add_field);
        btn_add_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (article_field.getText().toString() == "") {
                        AndroidUtility.alert(getContext(), "Vérifier la valeur insérée!");
                        return;
                    }
                    if (scannedTag.getText().toString().isEmpty()) {
                        AndroidUtility.alert(getContext(), "Scanner une tag d'abord!");
                        return;
                    }

                    String value = article_field.getText().toString();
                    String nField = spinner_field.getSelectedItem().toString();

                    Field f = new Field();
                    f.setFieldName(nField);
                    f.setProduct_id(scannedTag.getText().toString());
                    AndroidUtility.alert(getContext(),scannedTag.getText().toString());
                    f.setFieldValue(value);
                    f.save();
                    AndroidUtility.alert(getContext(),"L'attribut: "+nField+" est enregistré avec succée, pour l'article ");

                    spinner_field.setSelection(0);
                    article_field.setText("");
                }catch (Exception c){
                    AndroidUtility.alert(getContext(),"Erreur "+c.getMessage());
                    spinner_field.setSelection(0);
                    article_field.setText("");
                    return;
                }
            }
        });


        Category c = new Category();
        List<String> spinnerArray_cat =  new ArrayList<String>();
        for (Category cat : c.listAll(Category.class)){
            spinnerArray_cat.add(cat.getNam());
        }
        Spinner spinner_category = (Spinner)v.findViewById(R.id.spinner_category);

        ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray_cat);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(adapter_category);


        List<String> spinnerArray_field =  new ArrayList<String>();
        for(FieldList f: FieldList.listAll(FieldList.class)){
            spinnerArray_field.add(f.getFieldName());
        }
        ArrayAdapter<String> adapter_fields = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,spinnerArray_field);
        adapter_fields.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_fields.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_field.setAdapter(adapter_fields);


        chargerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File photo;
                try {
                    // place where to store camera taken picture
                    photo = createTemporaryFile("rfid", ".jpeg");
                    //photo.delete();
                    imageUri = Uri.fromFile(photo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                // start camera intent
                startActivityForResult(intent, REQUEST_CODE);


            }
        });


        scanTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanATag();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Product.find(Product.class, "name = ?", new String[]{productName.getText().toString()}).size() > 0) {
                    AndroidUtility.alert(getActivity(), "un produit avec le nom : " + productName.getText().toString() + " existe deja");
                } else if(Product.find(Product.class, "tag = ?", new String[]{scannedTag.getText().toString()}).size() > 0) {
                    AndroidUtility.alert(getActivity(), "un produit avec le tag : " + scannedTag.getText().toString() + " existe deja");
                } else {
                    try {
                        String path = savePhotosToSdCard(productName.getText().toString());
                        List<Zone> zones = null;
                        if(listZone.getSelectedItem() != null){
                            zones = Zone.find(Zone.class, "name = ?", new String[]{listZone.getSelectedItem().toString()});
                        }
                        if (zones != null && zones.size() > 0) {
                            Product product = new Product(scannedTag.getText().toString(),
                                    path, productName.getText().toString(),
                                    category.getSelectedItem().toString()
                                    , description.getText().toString(),
                                    point.getText().toString(),
                                    zones.get(0));
                            product.save();

                            if (product.getTagId().equals("")) {
                                List<Product> products = Product.find(Product.class, "name = ?", product.getName());
                                Action action = new Action(Action.IN, products.get(0).getId(), zones.get(0).getId());
                                action.save();
                            } else {
                                List<Product> products = Product.find(Product.class, "tag = ?", product.getTagId());
                                Action action = new Action(Action.IN, products.get(0).getId(), zones.get(0).getId());
                                action.save();
                            }
                        } else {
                            Product product = new Product(scannedTag.getText().toString(),
                                    path, productName.getText().toString(),
                                    category.getSelectedItem().toString()
                                    , description.getText().toString(),
                                    point.getText().toString());
                            product.save();
                            if (product.getTagId().equals("")) {
                                List<Product> products = Product.find(Product.class, "name = ?", product.getName());
                                Action action = new Action(Action.IN, products.get(0).getId(), new Long(0));
                                action.save();
                            } else {
                                List<Product> products = Product.find(Product.class, "tag = ?", product.getTagId());
                                Action action = new Action(Action.IN, products.get(0).getId(), new Long(0));
                                action.save();
                            }
                        }
                        AndroidUtility.alert(getActivity(), "le produit " + productName.getText().toString() + " enregister");

                    } catch(Exception e) {
                        AndroidUtility.alert(getActivity(), "exception error AP : " + e.getMessage());
                    }
                }
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, getZoneList());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listZone.setAdapter(dataAdapter);
        sharedPreferences = RfidAppPreferences.getInstance(getActivity().getApplicationContext());

            return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            // We need to recyle unused bitmaps
            if (bitmap != null) {
                bitmap.recycle();
            }
            grabImage();
        }
    }

    public void grabImage() {
//        getActivity().getContentResolver().notifyChange(imageUri, null);
        ContentResolver cr = getActivity().getContentResolver();
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

            image_taken.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getZoneList() {
        ArrayList<String> list = new ArrayList<>();
        List<Zone> listZone = Zone.listAll(Zone.class);
        for (int i = 0; i < listZone.size(); i++) {
            list.add(listZone.get(i).getName());
        }
        return list;
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


    /**
     *  IMPLEMENTED METHODS START HERE
     */
    private boolean getUsbState() {
        return sharedPreferences.isDeviceConnected();
    }

    public String savePhotosToSdCard(String productName) {
        String path = FileManager.getInstance().saveImageFile(bitmap, productName);
        return path;
    }

    public void scanATag() {
       // try {
            final Handler handler = new Handler();
            scannedTag.setText("");
            if (getUsbState()) {
                label_searching.setText("Searching ...");
                // circulaireProgressBarStart();
                new Thread() {
                    boolean invetory = true;
                    ArrayList<String> tags = new ArrayList<>();

                    ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

                    public void run() {
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (tags.size() > 0) {
                                    handler.post(updateResult);
                                }
                            }

                        }, 0, PERIOD);

                        while (invetory) {
                            mMtiCmd = new CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory(mUsbCommunication);
                            CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory finalCmd = (CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory) mMtiCmd;

                            if (finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.StartInventory)) {
                                if (finalCmd.getTagNumber() > 0 && !tags.contains(finalCmd.getTagId())) {
                                    tags.add(finalCmd.getTagId());
                                    tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                                }
                            }
                        }
                        setPowerState();
                    }

                    final Runnable updateResult = new Runnable() {
                        @Override
                        public void run() {
                            invetory = false;
                            timer.cancel();
                            if (tags.size() == 1) {
                                scannedTag.setText(tags.get(0));
                                scannedTagsList.add(tags.get(0));
                            } else if (tags.size() > 1) {
                                showMoreThanOneTagScanned();
                            }
                            stopInventory();
                        }
                    };
                }.start();
            } else {
                AndroidUtility.alert(getActivity(), "The Reader is not connected");
            }
       // } catch (Exception e) {
       //     AndroidUtility.alert(getActivity(), "exception error AP : " + e.getMessage());
       // }
    }

        private void setPowerState() {
            MtiCmd mMtiCmd = new CMD_PwrMgt.RFID_PowerEnterPowerState(mUsbCommunication);
            CMD_PwrMgt.RFID_PowerEnterPowerState finalCmd = (CMD_PwrMgt.RFID_PowerEnterPowerState) mMtiCmd;

            finalCmd.setCmd(CMD_PwrMgt.PowerState.Sleep);
            sleep(200);
        }

    private void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
        }
    }
    public void stopInventory() {
        iSearching.setText("");
        //  circulaireProgressBarStop();
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

    public File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    }










