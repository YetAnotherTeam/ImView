package jat.imview;

import android.app.Application;

import jat.imview.network.ServiceHelper;

/**
 * Created by bulat on 07.12.15.
 */
public class ImViewApplication extends Application {
    public static volatile ServiceHelper serviceHelperInstance;
}
