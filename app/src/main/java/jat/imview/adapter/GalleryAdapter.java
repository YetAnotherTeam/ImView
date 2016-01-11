package jat.imview.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.ref.WeakReference;

import jat.imview.model.Image;
import jat.imview.ui.fragment.ImageFragment;


/**
 * Created by bulat on 07.12.15.
 */
public class GalleryAdapter extends FragmentStatePagerAdapter {
    private Cursor cursor;
    private WeakReference<Context> weakContext;

    public GalleryAdapter(Context context, FragmentManager fm) {
        super(fm);
        weakContext = new WeakReference<>(context);
    }

    @Override
    public int getCount() {
        if (cursor != null) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public Fragment getItem(int position) {
        Image image = null;
        if (cursor.moveToPosition(position)) {
            image = Image.getByCursor(cursor);
        }
        return ImageFragment.newInstance(image);
    }

    public void changeCursor(Cursor newCursor) {
        if (cursor == newCursor)
            return;
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        this.cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}
