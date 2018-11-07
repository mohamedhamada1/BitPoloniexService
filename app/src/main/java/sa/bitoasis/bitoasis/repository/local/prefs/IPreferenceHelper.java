package sa.bitoasis.bitoasis.repository.local.prefs;

/**
 * Created by Mohamed.Shaaban on 3/5/2018.
 */

public interface IPreferenceHelper {

    boolean appOpenedBefore();

    void setIsOpenedBefore(boolean isOpenedBefore);
    void setAppTkn(String token);
    String getAppTkn();
}
