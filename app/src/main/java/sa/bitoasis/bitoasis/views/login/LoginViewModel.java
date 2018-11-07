package sa.bitoasis.bitoasis.views.login;

import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;

import org.json.JSONObject;

import javax.inject.Inject;


import sa.bitoasis.bitoasis.enums.Validation;
import sa.bitoasis.bitoasis.repository.remote.LoginDataModel;
import sa.bitoasis.bitoasis.user.User;
import sa.bitoasis.bitoasis.utils.CommonUtils;
import sa.bitoasis.bitoasis.views.base.BaseViewModel;
import sa.waqood.networkmodule.Resource;


/**
 * Created by Mohamed.Shaaban on 3/11/2018.
 */

public class LoginViewModel extends BaseViewModel {

    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    private LoginDataModel loginDataModel;

    @Inject
    User user;

    @Inject
    public LoginViewModel(LoginDataModel loginDataModel) {
        this.loginDataModel = loginDataModel;
    }

    public Validation validateInput() {
        if (userName.get() == null || userName.get().isEmpty() || password.get() == null || password.get().isEmpty()) {
            return Validation.empty;
        } else if (!CommonUtils.isEmailValid(userName.get())) {
            return Validation.invalid_email;
        } else if (password.get() == null || password.get().length() < 3) {
            return Validation.invalid_password;
        }
        user.setPassword(password.get());
        user.setEmail(userName.get());
        return Validation.pass;
    }

    public LiveData<Resource<JSONObject>> login() {
        return loginDataModel.login(user);
    }

}
