package wolfsoft.invincible.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import wolfsoft.invincible.ActivityPos.PosActivity;
import wolfsoft.invincible.ActivityStore.StoreActivity;
import wolfsoft.invincible.ActivityTags.TagsActivity;
import wolfsoft.invincible.ActivityZone.ZonesActivity;
import wolfsoft.invincible.R;
import wolfsoft.invincible.TabProduitActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentManager fragmentManager;
    Fragment fragment;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private Button bProduit;
    private OnFragmentInteractionListener mListener;
    private Button bTags;
    private Button bVente;
    private Button bMagasin;
    private Button bZone;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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

        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        getActivity().setTitle("Accueil");

        bProduit = (Button) view.findViewById(R.id.btn_produit);
        bProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_list = new Intent(getActivity(), TabProduitActivity.class);
                startActivity(intent_list);
            }
        });

        bTags = (Button) view.findViewById(R.id.btn_tags);
        bTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent_list = new Intent(getActivity(), TagsActivity.class);
                startActivity(intent_list);
            }
        });

        bVente = (Button) view.findViewById(R.id.btn_vente);
        bVente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_list = new Intent(getActivity(), PosActivity.class);
                startActivity(intent_list);
            }
        });

        bMagasin = (Button) view.findViewById(R.id.btn_magasin);
        bMagasin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_store = new Intent(getActivity(), StoreActivity.class);
                startActivity(intent_store);
            }
        });

        bZone = (Button) view.findViewById(R.id.btn_zone);
        bZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_zone = new Intent(getActivity(), ZonesActivity.class);
                startActivity(intent_zone);
            }
        });

        return  view;
    }












    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
