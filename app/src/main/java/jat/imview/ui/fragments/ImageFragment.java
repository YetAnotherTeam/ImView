package jat.imview.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jat.imview.R;

public class ImageFragment extends Fragment {
    private static final String IMAGE_PATH_PARAMETER= "Image path";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        return rootView;
    }

    public static ImageFragment newInstance(int position) {
        ImageFragment fragment = new ImageFragment();

        Bundle args = new Bundle();
        args.putString(IMAGE_PATH_PARAMETER, "");
        fragment.setArguments(args);
        return fragment;
    }
}
