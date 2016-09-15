package wolfsoft.invincible.utils;

import android.content.Context;
import android.graphics.Color;

import com.github.johnpersano.supertoasts.SuperToast;

/**
 * Created by Ibrahim on 26/12/2015.
 */
public class AndroidUtility {

    public static void alert(Context context, String message) {
        SuperToast superToast = new SuperToast(context);
        superToast.setText(message);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setBackground(SuperToast.Background.GREEN);
        superToast.setTextColor(Color.WHITE);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
        superToast.show();
    }
}
