package sa.bitoasis.bitoasis.repository.remote;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import sa.bitoasis.bitoasis.repository.local.db.dao.UserDao;
import sa.bitoasis.bitoasis.repository.local.prefs.IPreferenceHelper;
import sa.bitoasis.bitoasis.user.User;
import sa.bitoasis.bitoasis.utils.ApiEndPoint;
import sa.waqood.networkmodule.AppExecutors;
import sa.waqood.networkmodule.NetworkBoundResource;
import sa.waqood.networkmodule.RXRequestNetwork;
import sa.waqood.networkmodule.Resource;

/**
 * Created by Mohamed.Shaaban on 3/13/2018.
 */

public class SignupDataModel {


    private UserDao userDataModel;
    private final AppExecutors appExecutors;
    private IPreferenceHelper preferenceHelper;

    @Inject
    public SignupDataModel(UserDao userDataModel, IPreferenceHelper preferenceHelper, AppExecutors appExecutors) {
        this.userDataModel = userDataModel;
        this.appExecutors = appExecutors;
        this.preferenceHelper = preferenceHelper;
    }

    public LiveData<Resource<JSONObject>> doSignup(User user) {

        return new NetworkBoundResource<JSONObject, JSONObject>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull JSONObject jsonObject) {
                // save it on DB
                try {
                    if (jsonObject.optBoolean("success")) {
                        user.setToken(jsonObject.getString("token"));
                        user.setHashedId(jsonObject.getString("hashed_id"));
                        userDataModel.insert(user);
                        preferenceHelper.setAppTkn(user.getToken());
                    }
                } catch (JSONException e) {
                }

            }

            @Override
            protected boolean shouldFetch(@Nullable JSONObject data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<JSONObject> loadFromDb() {
                // load from DB , it should use this method if you want to have sync between local and server data
                return null;
            }

            @NonNull
            @Override
            protected LiveData<Resource<?>> createCall() {
                user.setCountry("UAE");
                user.setCountryCode("UAE");
                return RXRequestNetwork.post(ApiEndPoint.SIGN_UP_API).setContentType("application/json").addUrlEncodeFormBodyParameter("email", user.getEmail()).addUrlEncodeFormBodyParameter("password", user.getPassword()).addUrlEncodeFormBodyParameter("mobile", user.getMobile()).addUrlEncodeFormBodyParameter("push_token", user.getPushToken()).addUrlEncodeFormBodyParameter("device_type", "android").addUrlEncodeFormBodyParameter("country", user.getCountryCode())
                        .build()
                        .asJSONObjectObservable();
            }

            @NonNull
            @Override
            protected int getRequestId() {
                return 101;
            }
        }.asLiveData();
    }


}
