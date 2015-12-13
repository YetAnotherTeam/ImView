package jat.imview.network;

import jat.imview.ImViewApplication;

/**
 * Created by bulat on 07.12.15.
 */
public class ServiceHelper {
    public static ServiceHelper getInstance() {
        ServiceHelper localInstance = ImViewApplication.serviceHelperInstance;
        if (localInstance == null) {
            synchronized (ServiceHelper.class) {
                localInstance = ImViewApplication.serviceHelperInstance;
                if (localInstance == null) {
                    ImViewApplication.serviceHelperInstance = localInstance = new ServiceHelper();
                }
            }
        }
        return localInstance;
    }
}
