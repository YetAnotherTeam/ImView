package jat.imview.processor;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

import jat.imview.model.Image;
import jat.imview.rest.HTTPMethod;
import jat.imview.rest.Response;
import jat.imview.rest.RestMethodFactory;
import jat.imview.processor.responseParsers.ImageListParser;
import jat.imview.rest.restMethods.ImageListRestMethod;
import jat.imview.contentProvider.ImageTable;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageListProcessor {
    private WeakReference<Context> weakContext;
    private boolean isFeatured;

    public ImageListProcessor(Context context, boolean isFeatured) {
        weakContext = new WeakReference<>(context);
        this.isFeatured = isFeatured;
    }

    public void getImageList(ProcessorCallback processorCallback) {
        ImageListRestMethod getImageListMethod = (ImageListRestMethod) RestMethodFactory
                .getInstance(weakContext.get())
                .getRestMethod(ImageTable.CONTENT_URI, HTTPMethod.GET);
        getImageListMethod.setIsFeatured(isFeatured);
        Response response = getImageListMethod.execute();

        if (response.status == 200) {
            updateContentProvider(response);
        }
        processorCallback.send(response.status);
    }

    private void updateContentProvider(Response response) {
        ImageListParser imageListResponse = new ImageListParser(response);
        List<Image> imageList = imageListResponse.getImageList();
        for (int i = 0; i < imageList.size(); ++i) {
            Image image = imageList.get(i);
            ContentValues values = new ContentValues();
            values.put(ImageTable.ID, image.getId());
            values.put(ImageTable.NETPATH, image.getNetpath());
            values.put(ImageTable.RATING, image.getRating());
            values.put(ImageTable.PUBLISH_DATE, String.valueOf(image.getPublishDate()));
            weakContext.get().getContentResolver().update(
                    ContentUris.withAppendedId(ImageTable.CONTENT_URI, image.getId()), values, null, null
            );
            // TODO Make bulk insert
        }
    }
}