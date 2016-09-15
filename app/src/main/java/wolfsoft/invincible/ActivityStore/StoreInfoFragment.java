package wolfsoft.invincible.ActivityStore;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

import wolfsoft.invincible.R;
import wolfsoft.invincible.model.Store;
import wolfsoft.invincible.utils.AndroidUtility;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoreInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoreInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    TextView txt_nom;
    TextView txt_adresse;
    TextView txt_service;
    TextView txt_telephone;
    TextView txt_email;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StoreInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreInfoFragment newInstance(String param1, String param2) {
        StoreInfoFragment fragment = new StoreInfoFragment();
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
        View v = inflater.inflate(R.layout.fragment_store_info, container, false);

        txt_nom = (TextView) v.findViewById(R.id.txt_nom);
        txt_adresse = (TextView) v.findViewById(R.id.txt_adresse);
        txt_telephone = (TextView) v.findViewById(R.id.txt_telephone);
        txt_email = (TextView) v.findViewById(R.id.txt_email);
        txt_service = (TextView) v.findViewById(R.id.txt_service);
        try {
            Store s = new Store();
            s = Store.listAll(Store.class).get(0);
            txt_nom.setText(s.getNom());
            txt_email.setText(s.getEmail());
            txt_service.setText(s.getService());
            txt_adresse.setText(s.getAdresse());
            txt_telephone.setText(s.getTel());
        }catch (Exception ss){
            AndroidUtility.alert(getActivity(),"Can't load store infos - "+ss.getMessage());
        }
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
