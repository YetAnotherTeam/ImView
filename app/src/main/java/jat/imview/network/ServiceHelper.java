package jat.imview.network;

/**
 * Created by bulat on 07.12.15.
 */
public class ServiceHelper {
    public static volatile ServiceHelper serviceHelperInstance;
    public static ServiceHelper getInstance() {
        ServiceHelper localInstance = serviceHelperInstance;
        if (localInstance == null) {
            synchronized (ServiceHelper.class) {
                localInstance = serviceHelperInstance;
                if (localInstance == null) {
                    serviceHelperInstance = localInstance = new ServiceHelper();
                }
            }
        }
        return localInstance;
    }
}
