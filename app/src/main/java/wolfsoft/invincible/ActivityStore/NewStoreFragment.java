package wolfsoft.invincible.ActivityStore;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import customfonts.MyEditText;
import wolfsoft.invincible.R;
import wolfsoft.invincible.model.Store;
import wolfsoft.invincible.utils.AndroidUtility;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewStoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewStoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    MyEditText store_nom;
    EditText store_adresse;
    EditText store_telephone;
    EditText store_service;
    EditText store_email;
    Button btn_save;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewStoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewStoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewStoreFragment newInstance(String param1, String param2) {
        NewStoreFragment fragment = new NewStoreFragment();
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
        View v = inflater.inflate(R.layout.fragment_new_store, container, false);

        store_adresse = (EditText) v.findViewById(R.id.store_adresse);
        store_nom = (MyEditText) v.findViewById(R.id.store_nom);
        store_email = (EditText) v.findViewById(R.id.store_email);
        store_telephone = (EditText) v.findViewById(R.id.store_telephone);
        store_service = (EditText) v.findViewById(R.id.store_service);

        btn_save = (Button) v.findViewById(R.id.btn_save_store);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if(store_nom.getText().toString().isEmpty()){
                        AndroidUtility.alert(getActivity(),"Le nom du magasin est obligatoire.");
                        return;
                    }else {

                        // Enrgistrement du magasin
                    try {
                        Store _store = new Store();

                        _store.setNom(store_nom.getText().toString());
                        _store.setAdresse(store_adresse.getText().toString());
                        _store.setEmail(store_email.getText().toString());
                        _store.setService(store_service.getText().toString());
                        _store.setTel(store_telephone.getText().toString());
                        Store.deleteAll(Store.class);
                        _store.save();

                        AndroidUtility.alert(getActivity(),"Infomations enregistrées...");
                        return;

                    }catch (Exception ex){
                        AndroidUtility.alert(getActivity(),"Erreur: "+ex.getMessage());
                        return;
                        }
                    }

                }catch (Exception cc){
                    AndroidUtility.alert(getActivity(),"Erreur: "+cc.getMessage());
                }
            }
        });

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
