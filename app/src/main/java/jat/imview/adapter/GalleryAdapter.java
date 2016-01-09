package jat.imview.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import jat.imview.model.Image;
import jat.imview.ui.view.TouchImageView;

/**
 * Created by bulat on 07.12.15.
 */
public class GalleryAdapter extends PagerAdapter {
    private Cursor cursor;
    private WeakReference<Context> weakContext;

    public GalleryAdapter(Context context) {
        weakContext = new WeakReference<>(context);
    }
/*
    public GalleryAdapter(Context context, FragmentManager fm) {
        super(fm);
        weakContext = new WeakReference<>(context);
    }
*/

    @Override
    public int getCount() {
        if (cursor == null) {
            return 0;
        } else {
            return cursor.getCount();
        }
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final Context context = weakContext.get();

        TouchImageView touchImageView = new TouchImageView(container.getContext());
        if (cursor.moveToPosition(position)) {
            String url = Image.getByCursor(cursor).getFullNetpath();
            Log.d("MyImageURL", url);
            Picasso.with(context)
                    .load(url)
                    .into(touchImageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d("MyImageLoading", "Success");
                                }

                                @Override
                                public void onError() {
                                    Log.d("MyImageLoading", "Error");
                                }
                            }
                    );

        }
        container.addView(touchImageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return touchImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
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
