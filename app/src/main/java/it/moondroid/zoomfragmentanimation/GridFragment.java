package it.moondroid.zoomfragmentanimation;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnGridFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GridFragment extends Fragment {

    private OnGridFragmentInteractionListener mListener;

    public GridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_grid, container, false);
        rootView.findViewById(R.id.item1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed(view);
            }
        });
        rootView.findViewById(R.id.item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed(view);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onGridFragmentInteraction(view);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnGridFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            Log.w("GridFragment", activity.toString()
                    + " must implement OnGridFragmentInteractionListener");
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
    public interface OnGridFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onGridFragmentInteraction(View view);
    }

}
