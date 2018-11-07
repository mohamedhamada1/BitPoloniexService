package sa.bitoasis.bitoasis.utils;


import sa.bitoasis.bitoasis.BuildConfig;

/**
 * Created by Mohamed.Shaaban on 3/11/2018.
 */

public interface ApiEndPoint {

    String LOGIN_API = BuildConfig.BASE_URL + "Users/signIn";
    String SIGN_UP_API = BuildConfig.BASE_URL + "Users/signUp";


}
