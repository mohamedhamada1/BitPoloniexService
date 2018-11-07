package sa.bitoasis.bitoasis.views.splash;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import sa.bitoasis.bitoasis.BR;
import sa.bitoasis.bitoasis.R;
import sa.bitoasis.bitoasis.databinding.ActivitySplashBinding;
import sa.bitoasis.bitoasis.user.User;
import sa.bitoasis.bitoasis.views.base.BaseActivity;
import sa.bitoasis.bitoasis.views.login.LoginActivity;
import sa.bitoasis.bitoasis.views.main.TickerActivity;


public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {


    private boolean openLogin = false;

    ActivitySplashBinding viewBinding;

    SplashViewModel splashViewModel;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = getViewDataBinding();

        splashViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user == null || user.getEmail() == null || user.getEmail().length() < 1) {
                    openLogin = true;
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (openLogin) {
                    startActivity(LoginActivity.getStartIntent(SplashActivity.this));
                } else {
                   startActivity(TickerActivity.getStartIntent(SplashActivity.this));
                }
                finish();
            }
        }, 2000);


    }

    @Override
    public SplashViewModel getViewModel() {
        splashViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SplashViewModel.class);
        return splashViewModel;
    }

    public int getBindingVariable() {
        return BR.viewModel;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }


}
