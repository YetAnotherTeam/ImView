package jat.imview.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jat.imview.R;

public class ImageFragment extends Fragment {
    private static final String ARGUMENT_POSITION = "argument_position";
    private int position;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        //rootView.findViewById(R.id.)
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARGUMENT_POSITION);

    }

    public static ImageFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_POSITION, position);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
