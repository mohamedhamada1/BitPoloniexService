package sa.bitoasis.bitoasis.views.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;


import sa.bitoasis.bitoasis.BR;
import sa.bitoasis.bitoasis.R;
import sa.bitoasis.bitoasis.databinding.ActivityLoginBinding;
import sa.bitoasis.bitoasis.views.base.BaseActivity;
import sa.bitoasis.bitoasis.views.main.TickerActivity;
import sa.bitoasis.bitoasis.views.signup.SignupActivity;
import sa.waqood.networkmodule.enums.Status;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginCallBack {

    private LoginViewModel loginViewModel;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    ActivityLoginBinding mActivityLoginBinding;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mActivityLoginBinding.setCallback(this);
        mActivityLoginBinding.setViewModel(loginViewModel);
    }

    @Override
    public LoginViewModel getViewModel() {
        loginViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel.class);
        return loginViewModel;
    }

    public int getBindingVariable() {
        return BR.viewModel;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void clickLogin() {
        hideKeyboard();
        switch (loginViewModel.validateInput()) {
            case pass:
                mActivityLoginBinding.TIPassword.setErrorEnabled(false);
                mActivityLoginBinding.TIEmail.setErrorEnabled(false);
                loginViewModel.login().observe(this, userResource -> {
                    mActivityLoginBinding.setResource(userResource);
                    if (userResource.status == Status.SUCCESS) {
                        if (userResource.data.optBoolean("success")) {
                            startActivity(TickerActivity.getStartIntent(LoginActivity.this));
                            finish();
                        } else {
                            String errorMsg = userResource.data.optString("error");
                            if (errorMsg == null)
                                errorMsg = getString(R.string.Ops_something_went_wrong);
                            showSnackMessage(errorMsg);
                        }

                    } else if (userResource.status == Status.ERROR) {
                        String errorMsg = userResource.message;
                        if (errorMsg == null) {
                            errorMsg = getString(R.string.Ops_something_went_wrong);
                        }
                        showSnackMessage(errorMsg);
                    }

                });
                break;
            case empty:
                showSnackMessage(getString(R.string.Field_mandatory));
                break;
            case invalid_password:
                mActivityLoginBinding.TIPassword.setErrorEnabled(true);
                mActivityLoginBinding.TIPassword.setError(getString(R.string.pass_validation));
                break;
            case invalid_email:
                mActivityLoginBinding.TIEmail.setErrorEnabled(true);
                mActivityLoginBinding.TIEmail.setError(getString(R.string.email_validation));
                break;
        }

    }

    @Override
    public void clickSignup() {
        startActivity(SignupActivity.getStartIntent(LoginActivity.this));
        finish();
    }

    @Override
    public void skip() {
        startActivity(TickerActivity.getStartIntent(LoginActivity.this));
    }
}
