package sa.bitoasis.bitoasis.views.signup;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;


import sa.bitoasis.bitoasis.BR;
import sa.bitoasis.bitoasis.R;
import sa.bitoasis.bitoasis.databinding.ActivitySignupBinding;
import sa.bitoasis.bitoasis.utils.CommonUtils;
import sa.bitoasis.bitoasis.views.base.BaseActivity;
import sa.bitoasis.bitoasis.views.login.LoginActivity;
import sa.bitoasis.bitoasis.views.main.TickerActivity;
import sa.waqood.networkmodule.Resource;
import sa.waqood.networkmodule.enums.Status;


/**
 * Created by Mohamed.Shaaban on 3/13/2018.
 */

public class SignupActivity extends BaseActivity<ActivitySignupBinding, SignupViewModel> implements SignupCallBack {


    private SignupViewModel signupViewModel;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private ActivitySignupBinding mActivitySignupBinding;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignupBinding = getViewDataBinding();
        mActivitySignupBinding.setCallback(this);
        mActivitySignupBinding.setViewModel(signupViewModel);
    }

    @Override
    public SignupViewModel getViewModel() {
        signupViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SignupViewModel.class);
        return signupViewModel;
    }

    public int getBindingVariable() {
        return BR.viewModel;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_signup;
    }


    @Override
    public void clickSignup() {
        switch (signupViewModel.validateInput()) {
            case pass:
                mActivitySignupBinding.TIEmail.setErrorEnabled(false);
                mActivitySignupBinding.TIPassword.setErrorEnabled(false);
                mActivitySignupBinding.TIConfirmPassword.setErrorEnabled(false);
                signUp();
                break;
            case empty:
                showSnackMessage(getString(R.string.Field_mandatory));
                break;
            case invalid_password:
                mActivitySignupBinding.TIPassword.setErrorEnabled(true);
                mActivitySignupBinding.TIPassword.setError(getString(R.string.pass_validation));

                break;
            case invalid_email:
                mActivitySignupBinding.TIEmail.setErrorEnabled(true);
                mActivitySignupBinding.TIEmail.setError(getString(R.string.email_validation));

                break;
            case not_same_password:
                mActivitySignupBinding.TIConfirmPassword.setError(getString(R.string.password_is_not_match));
                mActivitySignupBinding.TIConfirmPassword.setErrorEnabled(true);
                break;

        }
    }

    @Override
    public void clickLogin() {
        startActivity(LoginActivity.getStartIntent(SignupActivity.this));
    }


    private void signUp() {
        signupViewModel.doSignup().observe(this, new Observer<Resource<JSONObject>>() {
            @Override
            public void onChanged(@Nullable Resource<JSONObject> jsonObjectResource) {
                mActivitySignupBinding.setResource(jsonObjectResource);
                if (jsonObjectResource.status == Status.SUCCESS) {

                    if (jsonObjectResource.data.optBoolean("success")) {
                        // success signup
                        createAlertMsg(getString(R.string.Your_account_has_been_created_successfully)).setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                startActivity(TickerActivity.getStartIntent(SignupActivity.this));
                            }
                        }).show();
                    } else {

                        try {
                            JSONObject jsonErrObject = jsonObjectResource.data.optJSONObject("error");
                            HashMap<String, String> errors = CommonUtils.toMap(jsonErrObject);
                            String errorMsg = null;
                            if (errors.get("email") != null && errors.get("email").contentEquals("This Email is Already Registered")) {
                                errorMsg = getString(R.string.Email_already_registered);
                            }
                            if (errors.get("mobile") != null && errors.get("mobile").contentEquals("This Mobile is Already Registered")) {
                                errorMsg = getString(R.string.This_Mobile_Already_Registered);
                            }
                            if (errorMsg == null) {
                                errorMsg = getString(R.string.Ops_something_went_wrong);
                            }
                            createAlertMsg(errorMsg).setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                        } catch (Exception e) {
                            // sometimes he send error as string
                            String error = jsonObjectResource.data.optString("error");
                            showSnackMessage(error);
                        }
                    }
                } else if (jsonObjectResource.status == Status.ERROR) {
                    String errorMsg;
                    if (jsonObjectResource.data == null) {
                        errorMsg = jsonObjectResource.message;
                    } else {
                        errorMsg = jsonObjectResource.data.optString("message");
                    }
                    if (errorMsg == null) {
                        errorMsg = getString(R.string.Something_went_wrong);
                    }
                    showSnackMessage(errorMsg);
                }

            }
        });
    }

}
