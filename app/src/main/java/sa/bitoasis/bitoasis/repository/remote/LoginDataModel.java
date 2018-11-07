package sa.bitoasis.bitoasis.repository.remote;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
 * Created by Mohamed.Shaaban on 3/10/2018.
 */

public class LoginDataModel {

    private UserDao userDataModel;
    private final AppExecutors appExecutors;
    IPreferenceHelper preferenceHelper;

    @Inject
    public LoginDataModel(UserDao userDataModel, IPreferenceHelper preferenceHelper,AppExecutors appExecutors) {
        this.userDataModel = userDataModel;
        this.appExecutors = appExecutors;
        this.preferenceHelper=preferenceHelper;
    }

    public LiveData<Resource<JSONObject>> login(User user) {
        return new NetworkBoundResource<JSONObject, JSONObject>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull JSONObject jsonObject) {
                // save it on DB
                if (jsonObject.optBoolean("success")) {
                    user.setToken(jsonObject.optString("token"));
                    preferenceHelper.setAppTkn(user.getToken());
                    JSONObject userJsonObject = jsonObject.optJSONObject("user");
                    if (userJsonObject != null) {
                        user.setUserId(userJsonObject.optLong("id"));
                        user.setHashedId(userJsonObject.optString("hashed_id"));
                        user.setProfileImg(userJsonObject.optString("profile_img"));
                        user.setCountryCode(userJsonObject.optString("country"));
                        user.setPushToken(userJsonObject.optString("push_token"));
                        user.setDeviceType(userJsonObject.optString("device_type"));
                        int isMentor = userJsonObject.optInt("is_mentor");
                        if (isMentor > 0)
                            user.setMentor(true);
                        else user.setMentor(false);
                        userDataModel.insert(user);
                    }
                }
            }
            @Override
            protected boolean shouldFetch(@Nullable JSONObject data) {
                return true;
            }

            @Override
            protected LiveData<JSONObject> loadFromDb() {
                // load from DB , it should use this method if you want to have sync between local and server data
                return null;
            }

            @NonNull
            @Override
            protected LiveData<Resource<?>> createCall() {
                return RXRequestNetwork.post(ApiEndPoint.LOGIN_API).setContentType("application/json").addUrlEncodeFormBodyParameter("email", user.getEmail()).addUrlEncodeFormBodyParameter("password", user.getPassword())
                        .build().asJSONObjectObservable();
            }

            @NonNull
            @Override
            protected int getRequestId() {
                return 101;
            }
        }.asLiveData();
    }

}
