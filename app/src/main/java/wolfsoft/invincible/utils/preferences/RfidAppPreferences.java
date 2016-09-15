package wolfsoft.invincible.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by lamine on 4/27/2015.
 */
public class RfidAppPreferences {
    // Shared preferences file name
    private static final String PREFERENCES_NAME = "rfidPreferences";
    // All Shared Preferences Keys
    private static final String IS_CONNECTED = "isConnected";
    // Current Activity
    public static Context context;
    private static RfidAppPreferences rfidAppPreferences;
    // Shared Preferences
    SecurePreferences sharedPreferences;
    // Editor for Shared preferences
    Editor editor;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    private RfidAppPreferences(Context context) {
        RfidAppPreferences.context = context;
        sharedPreferences = new SecurePreferences(context, PREFERENCES_NAME);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    public static RfidAppPreferences getInstance(Context context) {
        if (rfidAppPreferences == null)
            rfidAppPreferences = new RfidAppPreferences(context);
        return rfidAppPreferences;
    }

    public boolean isDeviceConnected() {
        boolean state = sharedPreferences.getBoolean(IS_CONNECTED, false);
        return state;
    }

    public void setDeviceConnected(boolean state) {
        editor.putBoolean(IS_CONNECTED, state);

        // commit changes
        editor.commit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);

        // commit changes
        editor.commit();
    }
}
