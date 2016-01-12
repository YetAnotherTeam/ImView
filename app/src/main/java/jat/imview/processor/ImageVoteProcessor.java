package jat.imview.processor;

import android.content.Context;

import java.lang.ref.WeakReference;

import jat.imview.rest.http.Response;

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

    public void getImageVote(ProcessorCallback processorCallback) {
        /*ImageVoteRestMethod imageVoteRestMethod = ImageVoteRestMethod.getRestMethod(ImageListResource.CONTENT_URI, HTTPMethod.POST);
        imageVoteRestMethod.setImageId(imageId);
        imageVoteRestMethod.setIsUpVote(isUpVote);
        Response response = imageVoteRestMethod.execute();

        if (response.status == 200) {
            updateContentProvider(response);
        }
        processorCallback.send(response.status);*/
    }

    private void updateContentProvider(Response response) {
        /*ImageListResource imageListResponse = new ImageListResource(response);
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
            values.put(ImageTable.COMMENTS_COUNT, String.valueOf(image.getCommentsCount()));
            contentResolver.insert(
                    ContentUris.withAppendedId(ImageListResource.CONTENT_URI, image.getId()),
                    values
            );

            ContentValues featuredValues = new ContentValues();
            featuredValues.put(FeaturedTable.IMAGE_ID, image.getId());
            featuredValuesArray[i] = featuredValues;
        }
        contentResolver.delete(FeaturedTable.CONTENT_URI, null, null);
        contentResolver.bulkInsert(FeaturedTable.CONTENT_URI, featuredValuesArray);
        */
    }
}