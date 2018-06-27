package ada.osc.movielist.utils;

import android.content.Context;
import android.content.SharedPreferences;

import ada.osc.movielist.Consts;

/**
 * Created by avukelic on 27-Jun-18.
 */
public class SharedPrefUtil {

    public static void saveToSharedPref(Context context, String sharedPrefsName, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getStringFromSharedPref(Context context, String sharedPrefsName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void clearLoginPrefs(Context context, String sharedPrefsName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(Consts.EMAIL_PREF).apply();
        sharedPreferences.edit().remove(Consts.USERNAME_PREF).apply();
    }
}
