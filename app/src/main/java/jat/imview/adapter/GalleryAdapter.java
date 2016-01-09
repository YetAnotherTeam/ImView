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

import jat.imview.contentProvider.DB.Table.FeaturedTable;
import jat.imview.contentProvider.DB.Table.ImageTable;
import jat.imview.rest.restMethod.ConnectionParams;
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
        TouchImageView touchImageView = new TouchImageView(container.getContext());
        Context context = weakContext.get();
        if (cursor.moveToPosition(position)) {
            int imageId = cursor.getInt(cursor.getColumnIndex(FeaturedTable.IMAGE_ID));
            int columnIndex = cursor.getColumnIndex(ImageTable.NETPATH);
            String path = cursor.getString(columnIndex);
            String url = ConnectionParams.SCHEME + ConnectionParams.HOST + "/" + path;
            Log.d("MyImageURL", url);
            Picasso.with(context)
                    .load(url)
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

        }
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
