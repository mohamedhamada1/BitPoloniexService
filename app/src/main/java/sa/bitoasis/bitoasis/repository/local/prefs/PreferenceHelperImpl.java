package sa.bitoasis.bitoasis.repository.local.prefs;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by Mohamed.Shaaban on 3/5/2018.
 */


public class PreferenceHelperImpl implements IPreferenceHelper {
    public static final String PREF_KEY_CURRENT_LANGUAGE = "PREF_KEY_CURRENT_LANGUAGE";
    public static final String PREF_KEY_APP_OPENED_BEFORE = "PREF_KEY_APP_OPENED_BEFORE";
    public static final String PREF_KEY_TKN = "PREF_KEY_TKN";
    private final SharedPreferences mPrefs;

    @Inject
    public PreferenceHelperImpl(SharedPreferences mPrefs) {
        this.mPrefs = mPrefs;
    }


    @Override
    public boolean appOpenedBefore() {
        return mPrefs.getBoolean(PREF_KEY_APP_OPENED_BEFORE, false);
    }

    @Override
    public void setIsOpenedBefore(boolean isOpenedBefore) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(PREF_KEY_APP_OPENED_BEFORE, isOpenedBefore);
        editor.apply();
    }

    @Override
    public void setAppTkn(String token) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_KEY_TKN, token);
        editor.apply();
    }

    @Override
    public String getAppTkn() {
        return mPrefs.getString(PREF_KEY_TKN, "");
    }


}
