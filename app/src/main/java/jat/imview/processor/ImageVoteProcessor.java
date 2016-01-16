package jat.imview.processor;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

import jat.imview.contentProvider.db.table.FeaturedTable;
import jat.imview.contentProvider.db.table.ImageTable;
import jat.imview.model.Image;
import jat.imview.rest.http.HTTPMethod;
import jat.imview.rest.http.Response;
import jat.imview.rest.resource.ImageListResource;
import jat.imview.rest.resource.ImageVoteResource;
import jat.imview.rest.restMethod.ImageVoteRestMethod;
import jat.imview.rest.restMethod.base.RestMethod;
import jat.imview.rest.restMethod.base.RestMethodResult;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageVoteProcessor {
    private WeakReference<Context> weakContext;
    private int imageId;
    private boolean isUpVote;

    public ImageVoteProcessor(Context context, int imageId, boolean isUpVote) {
        weakContext = new WeakReference<>(context);
        this.imageId = imageId;
        this.isUpVote = isUpVote;
    }

    public void getImage(ProcessorCallback processorCallback) {
        RestMethod<ImageVoteResource> imageVoteRestMethod = new ImageVoteRestMethod(HTTPMethod.POST, imageId, isUpVote);
        RestMethodResult<ImageVoteResource> restMethodResult = imageVoteRestMethod.execute();
        if (restMethodResult.getStatusCode() == 200) {
            updateContentProvider(restMethodResult);
        }
        processorCallback.send(restMethodResult.getStatusCode());
    }

    private void updateContentProvider(RestMethodResult<ImageVoteResource> restMethodResult) {
        ImageVoteResource imageVoteResource = restMethodResult.getResource();
        List<Image> imageList = imageVoteResource.getImageList();
        ContentResolver contentResolver = weakContext.get().getContentResolver();
        ContentValues[] featuredValuesArray = new ContentValues[imageList.size()];
        for (int i = 0; i < imageList.size(); ++i) {
            Image image = imageList.get(i);
            ContentValues values = new ContentValues();
            values.put(ImageTable.ID, image.getId());
            values.put(ImageTable.NETPATH, image.getNetpath());
            values.put(ImageTable.RATING, image.getRating());
            values.put(ImageTable.PUBLISH_DATE, String.valueOf(image.getPublishDate()));
            values.put(ImageTable.COMMENTS_COUNT, String.valueOf(image.getCommentsCount()));
            contentResolver.insert(
                    ContentUris.withAppendedId(ImageTable.CONTENT_URI, image.getId()),
                    values
            );

            ContentValues featuredValues = new ContentValues();
            featuredValues.put(FeaturedTable.IMAGE_ID, image.getId());
            featuredValuesArray[i] = featuredValues;
        }
        contentResolver.delete(FeaturedTable.CONTENT_URI, null, null);
        contentResolver.bulkInsert(FeaturedTable.CONTENT_URI, featuredValuesArray);
    }
}