package it.moondroid.zoomfragmentanimation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FragmentTransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_transition);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.main_container, new FirstFragment())
                    .commit();
        }
    }


    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }

    public static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        private OnItemClickListener mItemClickListener;

        List<Integer> items = new ArrayList<Integer>();
        public void setOnItemClickListener(OnItemClickListener listener) {
            this.mItemClickListener = listener;
        }


        public ImageAdapter() {
            items.add(1);
            items.add(1);
            items.add(1);
            items.add(1);
            items.add(1);
            items.add(1);
            items.add(1);
            items.add(1);
            items.add(1);
            items.add(1);
        }

        @Override
        public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            viewHolder.container.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, i);
                    }
                }
            });
            viewHolder.blue.setTransitionName("testBlue" + i);
            viewHolder.orange.setTransitionName("testOrange" + i);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View orange;
            public View blue;
            public View container;

            public ViewHolder(View itemView) {
                super(itemView);
                blue = itemView.findViewById(R.id.blue_bar);
                orange = itemView.findViewById(R.id.orange_bar);
                container = itemView.findViewById(R.id.container);
            }
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class FirstFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.first_fragment, container, false);

            RecyclerView list = (RecyclerView) rootView.findViewById(R.id.streams_list);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            ImageAdapter adapter = new ImageAdapter();
            adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // Set shared and scene transitions
                    setSharedElementReturnTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.trans_move));
                    setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));

                    View orange = view.findViewById(R.id.orange_bar);
                    View blue = view.findViewById(R.id.blue_bar);

                    SecondFragment secondFragment = new SecondFragment();
                    // Set shared and scene transitions on 2nd fragment
                    secondFragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.trans_move));
                    secondFragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));

                    // You need to make sure the transitionName is both unique to each instance of the view you
                    // want to animate as well as known to the 2nd fragment.  Since these views are inside
                    // a RecyclerView or ListView, they can have many instances.  In your adapter you need to
                    // set a transitionName dynamically (I use the position), then pass that unique transitionName
                    // to the 2nd fragment before you animate
                    secondFragment.setBlueId(blue.getTransitionName());
                    secondFragment.setOrangeId(orange.getTransitionName());
                    android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.replace(R.id.main_container, secondFragment);
                    trans.addToBackStack(null);
                    trans.addSharedElement(blue, blue.getTransitionName());
                    trans.addSharedElement(orange, orange.getTransitionName());
                    trans.commit();
                }
            });
            list.setAdapter(adapter);

            return rootView;
        }
    }

    public static class SecondFragment extends Fragment {
        private String mOrangeId;
        private String mBlueId;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.second_fragment, container, false);
            rootView.findViewById(R.id.blue_bar).setTransitionName(mBlueId);
            rootView.findViewById(R.id.orange_bar).setTransitionName(mOrangeId);
            return rootView;
        }

        public void setOrangeId(String id) {
            mOrangeId = id;
        }
        public void setBlueId(String id) {
            mBlueId = id;
        }
    }
}
