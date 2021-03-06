package sa.bitoasis.bitoasis.views.signup;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import sa.bitoasis.bitoasis.di.ViewModelProviderFactory;
import sa.bitoasis.bitoasis.user.User;

/**
 * Created by Mohamed.Shaaban on 3/11/2018.
 */
@Module
public class SignupActivityModule {
    @Provides
    ViewModelProvider.Factory provideSignupViewModelFactory(SignupViewModel signupViewModel) {
        return new ViewModelProviderFactory<>(signupViewModel);
    }
    @Provides
    User provideUser(){
        return  new User();
    }
}
