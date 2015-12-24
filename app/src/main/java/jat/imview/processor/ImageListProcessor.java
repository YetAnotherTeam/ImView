package jat.imview.processor;

import android.content.Context;

import java.lang.ref.WeakReference;

import jat.imview.model.ImageList;
import jat.imview.rest.HTTPMethod;
import jat.imview.rest.RestMethod;
import jat.imview.rest.RestMethodFactory;
import jat.imview.rest.RestMethodResult;
import jat.imview.сontentProvider.ImageConstants;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageListProcessor {
    private WeakReference<Context> weakContext;
    public ImageListProcessor(Context context) {
        weakContext = new WeakReference<>(context);
    }

    public void getImageList(ProcessorCallback processorCallback) {
        RestMethod<ImageList> getImageListMethod = RestMethodFactory.getInstance(weakContext.get())
                .getRestMethod(ImageConstants.CONTENT_URI, HTTPMethod.GET, null, null);
        RestMethodResult<ImageList> result = getImageListMethod.execute();
        //updateContentProvider();
        processorCallback.send(result.getStatusCode());
    }

    private void updateContentProvider() {
        
    }
}
