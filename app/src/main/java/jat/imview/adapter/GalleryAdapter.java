package jat.imview.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import jat.imview.ui.view.TouchImageView;

/**
 * Created by bulat on 07.12.15.
 */
public class GalleryAdapter extends PagerAdapter {
    private Cursor cursor;
    private WeakReference<Context> weakContext;

    public GalleryAdapter(Context context, Cursor cursor) {
        weakContext = new WeakReference<>(context);
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return 100;
        /*
        if (cursor == null) {
            return 0;
        } else {
            return cursor.getCount();
        }*/
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        TouchImageView touchImageView = new TouchImageView(container.getContext());

        //cursor.moveToPosition(position);
        Picasso.with(weakContext.get())
                .load("https://www.google.ru/images/nav_logo242_hr.png")
                .into(touchImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("123", "success");
                            }

                            @Override
                            public void onError() {
                                Log.d("123", "error");
                            }
                        }
                );
        // touchImageView.setImage(cursor.getString(cursor.getColumnIndex(FeaturedTable.IMAGE_ID)));
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

}
