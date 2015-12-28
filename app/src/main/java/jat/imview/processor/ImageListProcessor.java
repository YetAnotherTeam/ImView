package jat.imview.processor;

import android.content.Context;

import java.lang.ref.WeakReference;

import jat.imview.rest.GetImageListRestMethod;
import jat.imview.rest.HTTPMethod;
import jat.imview.rest.RestMethodFactory;
import jat.imview.rest.RestMethodResult;
import jat.imview.сontentProvider.ImageTable;

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
        GetImageListRestMethod getImageListMethod = (GetImageListRestMethod) RestMethodFactory
                .getInstance(weakContext.get())
                .getRestMethod(ImageTable.CONTENT_URI, HTTPMethod.GET);
        getImageListMethod.setIsFeatured(isFeatured);
        RestMethodResult result = getImageListMethod.execute();
        //updateContentProvider();
        processorCallback.send(result.getStatusCode());
    }

    private void updateContentProvider() {
        
    }
}
