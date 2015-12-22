package jat.imview.network;

import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import jat.imview.network.ServiceHelperObserver.Observable;
import jat.imview.network.ServiceHelperObserver.Observer;

/**
 * Created by bulat on 07.12.15.
 */
public class ServiceHelper implements Observable{
    private final List<Observer> observers = new ArrayList<>();
    private WeakReference<Context> weakContext;
    private final Map<RequestType, Integer> requests = new HashMap<>();
    private AtomicInteger requestCounter;
    enum RequestType {LOGIN, SIGNUP, IMAGE_NEW, IMAGE_GET, IMAGE_LIST, COMMENT_NEW, COMMENT_LIST}
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

    public void setContext(Context context) {
        weakContext = new WeakReference<Context>(context);
    }

    public void startService(Intent intent) {
        Context context = weakContext.get();
        if (context != null) {
            context.startService(intent);
        } else {
            throw new RuntimeException("You must set context before use startService");
        }
    }

    @Override
    public void registerObserver(Observer o) {
        synchronized (this) {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        }
    }

    @Override
    public void removeObserver(Observer o) {
        synchronized (this) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObservers(String response) {
        synchronized (this) {
            for (Observer observer : observers) {
                observer.handleResponse(response);
            }
        }
    }

    private boolean isRequestPending(RequestType requestType) {
        return requests.containsKey(requestType);
    }

    public int getImageList() {
        int requestId;
        if (isRequestPending(RequestType.IMAGE_LIST)) {
            requestId = requestCounter.getAndIncrement();
            requests.put(RequestType.IMAGE_LIST, requestId);
            Intent intent = new Intent();
            startService(intent);
        } else {
            requestId = requests.get(RequestType.IMAGE_LIST);
        }
        return requestId;
    }
}
