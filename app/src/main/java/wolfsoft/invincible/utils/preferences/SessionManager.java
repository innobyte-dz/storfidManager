package wolfsoft.invincible.utils.preferences;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;

import wolfsoft.invincible.LoginActivity;
import wolfsoft.invincible.model.UserContext;


/**
 * Created by lamine on 5/1/2015.
 */

public class SessionManager {

    public static final String LOGOUT_ACTION = "logOut";
    public static final String KEY_ID = "userId";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ACTION = "action";
    public static final String KEY_SESSION_ID = "sessionID";
    public static final String KEY_REFRESH_TOKEN = "refreshToken";
    public static final String KEY_MOBILE_PERMISSIONS = "mobilePermissions";
    public static final String KEY_SERVER_TYPE = "serverType";
    // Shared preferences file name
    private static final String PREFERENCES_NAME = "clearPreferences";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "isLoggedIn";
    // Current Activity
    public static Context context;
    private static SessionManager sessionManager;
    // Shared Preferences
    SecurePreferences sharedPreferences;
    // Editor for Shared preferences
    Editor editor;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    private SessionManager(Context context) {
        SessionManager.context = context;
        sharedPreferences = new SecurePreferences(context, PREFERENCES_NAME);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    public static SessionManager getInstance(Context context) {
        if (sessionManager == null)
            sessionManager = new SessionManager(context);
        return sessionManager;
    }

    // create session
    public void createLoginSession(UserContext userContext) {
        // Storing values in prefs
        editor.putString(IS_LOGIN, "true");
        editor.putString(KEY_ID, "" + userContext.getUserId());
        editor.putString(KEY_FIRST_NAME, userContext.getFirstName());
        editor.putString(KEY_LAST_NAME, userContext.getLastName());
        editor.putString(KEY_EMAIL, userContext.getEmail());
        editor.putString(KEY_PHONE, userContext.getPhone());
        editor.putString(KEY_ACTION, userContext.getAction());
        editor.putString(KEY_SESSION_ID, userContext.getSessionId());
        editor.putString(KEY_REFRESH_TOKEN, userContext.getRefreshToken());

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status If false it will redirect
     * user to login page Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            logoutUser();
        }
    }

    /**
     * Get stored session data
     */
    public UserContext getUserDetails() {
        UserContext userContext = new UserContext(Integer.parseInt(sharedPreferences.getString(KEY_ID, "0")),
                sharedPreferences.getString(KEY_FIRST_NAME, null), sharedPreferences.getString(
                KEY_LAST_NAME, null), sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null), sharedPreferences.getString(KEY_SESSION_ID,
                null), sharedPreferences.getString(KEY_REFRESH_TOKEN, null));

        userContext.setAction(sharedPreferences.getString(KEY_ACTION, null));
        userContext.setUserPermissions(sharedPreferences.getString(KEY_MOBILE_PERMISSIONS, null));

        return userContext;
    }

    /**
     * Set user Actions permissions
     */
    public void setUserPermissions(String userPermissions) {
        editor.putString(KEY_MOBILE_PERMISSIONS, userPermissions);
        // commit changes
        editor.commit();
    }

    /**
     * Set user name
     */
    public void setUserName(String userName) {
        editor.putString(KEY_FIRST_NAME, userName);
        // commit changes
        editor.commit();
    }


    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(LOGOUT_ACTION);
        context.sendBroadcast(broadcastIntent);
        // After logout redirect user to Loing Activity
        Intent i = new Intent(SessionManager.context, LoginActivity.class);

        // Add new Flag to start new Activity
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    /**
     * Quick check for login
     * *
     */
    // Get Login State
    public boolean isLoggedIn() {
        return Boolean.parseBoolean(sharedPreferences.getString(IS_LOGIN, "false"));
    }

    // temp methode
    public Boolean checkUser(String user, String pass) {
        if (sharedPreferences.getString(user, null) != null) {
            if (sharedPreferences.getString(user, null).equals(pass)) {
                return true;
            }
        }
        return false;
    }

}