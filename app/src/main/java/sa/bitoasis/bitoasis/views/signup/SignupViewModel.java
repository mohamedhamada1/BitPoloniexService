package sa.bitoasis.bitoasis.views.signup;

import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;

import org.json.JSONObject;

import javax.inject.Inject;

import sa.bitoasis.bitoasis.enums.Validation;
import sa.bitoasis.bitoasis.repository.remote.SignupDataModel;
import sa.bitoasis.bitoasis.user.User;
import sa.bitoasis.bitoasis.utils.CommonUtils;
import sa.bitoasis.bitoasis.views.base.BaseViewModel;
import sa.waqood.networkmodule.Resource;


/**
 * Created by Mohamed.Shaaban on 3/13/2018.
 */

public class SignupViewModel extends BaseViewModel {

    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> confirmPassword = new ObservableField<>();
    public ObservableField<String> mobile = new ObservableField<>();

    private SignupDataModel signupDataModel;

    @Inject
    User user;

    @Inject
    public SignupViewModel(SignupDataModel signupDataModel) {
        this.signupDataModel = signupDataModel;
    }

    public Validation validateInput() {
        if (password.get() == null || password.get().isEmpty() || confirmPassword.get() == null || confirmPassword.get().isEmpty() || email.get() == null || email.get().isEmpty() || mobile.get() == null || mobile.get().isEmpty()) {
            return Validation.empty;
        } else if (!CommonUtils.isEmailValid(email.get())) {
            return Validation.invalid_email;
        } else if (!CommonUtils.isValidPassword(password.get())) {
            return Validation.invalid_password;
        } else if (!password.get().contentEquals(confirmPassword.get())) {
            return Validation.not_same_password;
        }
        user.setPassword(password.get());
        user.setEmail(email.get());
        user.setDeviceType("android");
        user.setPushToken("");
        user.setMobile(mobile.get());
        return Validation.pass;
    }


    public LiveData<Resource<JSONObject>> doSignup() {

        return signupDataModel.doSignup(user);
    }

    public User getUser() {
        return user;
    }
}
