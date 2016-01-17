package jat.imview.rest.resource;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import jat.imview.model.UserProfile;
import jat.imview.rest.resource.base.Resource;

/**
 * Created by bulat on 23.12.15.
 */
public class SignupResource implements Resource {
    private static final String LOG_TAG = "MyResponse";
    private UserProfile userProfile;

    public SignupResource(byte[] responseBody) {
        try {
            String responseString = new String(responseBody);
            Log.d(LOG_TAG, responseString);
            JSONObject user = new JSONObject(responseString);
            userProfile = new UserProfile();
            userProfile.setId(user.getInt("id"));
            userProfile.setName(user.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
