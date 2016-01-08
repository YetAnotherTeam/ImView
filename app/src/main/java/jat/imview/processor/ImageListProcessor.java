package jat.imview.processor;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

import jat.imview.contentProvider.DB.Table.FeaturedTable;
import jat.imview.model.Image;
import jat.imview.rest.HTTPMethod;
import jat.imview.rest.Response;
import jat.imview.rest.restMethod.RestMethodFactory;
import jat.imview.processor.responseParser.ImageListParser;
import jat.imview.rest.restMethod.ImageListRestMethod;
import jat.imview.contentProvider.DB.Table.ImageTable;

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
        ImageListRestMethod imageListMethod = (ImageListRestMethod) RestMethodFactory
                .getInstance(weakContext.get())
                .getRestMethod(ImageTable.CONTENT_URI, HTTPMethod.GET);
        imageListMethod.setIsFeatured(isFeatured);
        Response response = imageListMethod.execute();

        if (response.status == 200) {
            updateContentProvider(response);
        }
        processorCallback.send(response.status);
    }

    private void updateContentProvider(Response response) {
        ImageListParser imageListResponse = new ImageListParser(response);
        List<Image> imageList = imageListResponse.getImageList();
        ContentResolver contentResolver = weakContext.get().getContentResolver();
        ContentValues[] featuredValuesArray = new ContentValues[imageList.size()];
        for (int i = 0; i < imageList.size(); ++i) {
            Image image = imageList.get(i);
            ContentValues values = new ContentValues();
            values.put(ImageTable.ID, image.getId());
            values.put(ImageTable.NETPATH, image.getNetpath());
            values.put(ImageTable.RATING, image.getRating());
            values.put(ImageTable.PUBLISH_DATE, String.valueOf(image.getPublishDate()));
            contentResolver.update(
                    ContentUris.withAppendedId(ImageTable.CONTENT_URI, image.getId()),
                    values,
                    null,
                    null
            );

            ContentValues featuredValues = new ContentValues();
            featuredValues.put(FeaturedTable.IMAGE_ID, image.getId());
            featuredValuesArray[i] = featuredValues;
        }
        contentResolver.delete(FeaturedTable.CONTENT_URI, null, null);
        contentResolver.bulkInsert(FeaturedTable.CONTENT_URI, featuredValuesArray);
    }
}