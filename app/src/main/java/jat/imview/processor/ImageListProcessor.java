package jat.imview.processor;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageListProcessor {
    private WeakReference<Context> weakContext;
    public ImageListProcessor(Context context) {
        weakContext = new WeakReference<>(context);
    }

    public void getImageList(ProcessorCallback processorCallback) {
        //updateContentProvider();
        //processorCallback.send();
    }

    private void updateContentProvider() {
        
    }
}
