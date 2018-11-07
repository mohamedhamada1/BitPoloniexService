package sa.bitoasis.bitoasis.views.login;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import sa.bitoasis.bitoasis.di.ViewModelProviderFactory;
import sa.bitoasis.bitoasis.user.User;


/**
 * Created by Mohamed.Shaaban on 3/11/2018.
 */
@Module
public class LoginActivityModule {

    @Provides
    ViewModelProvider.Factory provideLoginViewModelFactory(LoginViewModel loginViewModel) {
        return new ViewModelProviderFactory<>(loginViewModel);
    }

    @Provides
    User provideUser(){
        return  new User();
    }

}
