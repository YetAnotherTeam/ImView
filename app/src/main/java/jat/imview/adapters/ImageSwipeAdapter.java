package jat.imview.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import jat.imview.ui.ImageFragment;

/**
 * Created by bulat on 07.12.15.
 */
public class ImageSwipeAdapter extends FragmentStatePagerAdapter {
    private final List<String> imageList;

    public ImageSwipeAdapter(FragmentManager fm) {
        super(fm);
        imageList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }
}
