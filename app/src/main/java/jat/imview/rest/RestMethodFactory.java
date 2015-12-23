package jat.imview.rest;

import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;

import java.util.List;
import java.util.Map;

import jat.imview.сontentProvider.ImageConstants;

public class RestMethodFactory {
    private UriMatcher uriMatcher;
    private Context mContext;
    private static final int IMAGE = 1;
    private static RestMethodFactory restMethodFactoryInstance;

    public static RestMethodFactory getInstance(Context context) {
        RestMethodFactory localInstance = restMethodFactoryInstance;
        if (localInstance == null) {
            synchronized (RestMethodFactory.class) {
                localInstance = restMethodFactoryInstance;
                if (localInstance == null) {
                    restMethodFactoryInstance = localInstance = new RestMethodFactory(context);
                }
            }
        }
        return localInstance;
    }

    private RestMethodFactory(Context context) {
        mContext = context.getApplicationContext();
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ImageConstants.AUTHORITY, ImageConstants.TABLE_NAME, IMAGE);
    }

    public RestMethod getRestMethod(Uri resourceUri, HTTPMethod method, Map<String, List<String>> headers, byte[] body) {
        switch (uriMatcher.match(resourceUri)) {
            case IMAGE:
                if (method == HTTPMethod.GET) {
                    return new GetImageRestMethod(mContext);
                }
                break;
        }
        return null;
    }
}
