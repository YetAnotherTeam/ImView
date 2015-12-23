package jat.imview.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import jat.imview.processor.ImageListProcessor;
import jat.imview.rest.HTTPMethod;

/**
 * Created by bulat on 07.12.15.
 */
public class SendService extends IntentService {
    public static final String METHOD_EXTRA = "jat.imview.service.METHOD_EXTRA";
    public static final String REQUEST_TYPE_EXTRA = "jat.imview.service.REQUEST_TYPE_EXTRA";
    public static final String SERVICE_CALLBACK_EXTRA = "jat.imview.service.SERVICE_CALLBACK_EXTRA";

    public static final String LOGIN_USERNAME_EXTRA = "jat.imview.service.LOGIN_USERNAME_EXTRA";
    public static final String LOGIN_PASSWORD_EXTRA = "jat.imview.service.LOGIN_PASSWORD_EXTRA";
    public static final String SIGNUP_USERNAME_EXTRA = "jat.imview.service.SIGNUP_USERNAME_EXTRA";
    public static final String SIGNUP_PASSWORD_EXTRA = "jat.imview.service.SIGNUP_PASSWORD_EXTRA";

    public static final String IMAGE_NEW_FILEPATH_EXTRA = "jat.imview.service.IMAGE_NEW_FILEPATH_EXTRA";
    public static final String IMAGE_GET_IMAGE_ID_EXTRA = "jat.imview.service.IMAGE_GET_IMAGE_ID_EXTRA";
    public static final String IMAGE_LIST_IS_FEATURED_EXTRA = "jat.imview.service.IMAGE_LIST_IS_FEATURED_EXTRA";

    public static final String COMMENT_NEW_IMAGE_ID_EXTRA = "jat.imview.service.COMMENT_NEW_IMAGE_ID_EXTRA";
    public static final String COMMENT_NEW_TEXT_EXTRA = "jat.imview.service.COMMENT_NEW_TEXT_EXTRA";
    public static final String COMMENT_LIST_IMAGE_ID_EXTRA = "jat.imview.service.COMMENT_LIST_IMAGE_ID_EXTRA";

    public static final String ORIGINAL_INTENT_EXTRA = "jat.imview.service.ORIGINAL_INTENT_EXTRA";

    private static final int INVALID_REQUEST = -1;

    private Intent mOriginalRequestIntent;
    private ResultReceiver mServiceCallback;

    public SendService() {
        super("SendService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mOriginalRequestIntent = intent;
        HTTPMethod httpMethod = (HTTPMethod) mOriginalRequestIntent.getSerializableExtra(METHOD_EXTRA);
        RequestType requestType = (RequestType) mOriginalRequestIntent.getSerializableExtra(REQUEST_TYPE_EXTRA);
        mServiceCallback = mOriginalRequestIntent.getParcelableExtra(SERVICE_CALLBACK_EXTRA);
        switch (requestType) {
            case LOGIN:
                if (httpMethod.equals(HTTPMethod.POST)) {

                } else {
                    sendInvalidRequestCode();
                }
                break;
            case SIGNUP:
                if (httpMethod.equals(HTTPMethod.POST)) {

                } else {
                    sendInvalidRequestCode();
                }
                break;
            case IMAGE_NEW:
                if (httpMethod.equals(HTTPMethod.POST)) {

                } else {
                    sendInvalidRequestCode();
                }
                break;
            case IMAGE_GET:
                if (httpMethod.equals(HTTPMethod.GET)) {

                } else {
                    sendInvalidRequestCode();
                }
                break;
            case IMAGE_LIST:
                if (httpMethod.equals(HTTPMethod.GET)) {
                    ImageListProcessor imageListProcessor = new ImageListProcessor(getApplicationContext());
                    //imageListProcessor.getImageList();
                } else {
                    sendInvalidRequestCode();
                }
                break;
            case COMMENT_NEW:
                if (httpMethod.equals(HTTPMethod.POST)) {

                } else {
                    sendInvalidRequestCode();
                }
                break;
            case COMMENT_LIST:
                if (httpMethod.equals(HTTPMethod.GET)) {

                } else {
                    sendInvalidRequestCode();
                }
                break;
            default:
                sendInvalidRequestCode();
                break;
        }
    }

    private void sendInvalidRequestCode() {
        mServiceCallback.send(INVALID_REQUEST, getOriginalIntentBundle());
    }

    private Bundle getOriginalIntentBundle() {
        Bundle originalRequest = new Bundle();
        originalRequest.putParcelable(ORIGINAL_INTENT_EXTRA, mOriginalRequestIntent);
        return originalRequest;
    }
}
