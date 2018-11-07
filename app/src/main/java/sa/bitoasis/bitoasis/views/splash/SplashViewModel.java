package sa.bitoasis.bitoasis.views.splash;

import android.arch.lifecycle.LiveData;

import javax.inject.Inject;

import sa.bitoasis.bitoasis.repository.local.db.dao.UserDao;
import sa.bitoasis.bitoasis.user.User;
import sa.bitoasis.bitoasis.views.base.BaseViewModel;


public class SplashViewModel extends BaseViewModel {

    UserDao userDao;
    @Inject
    public SplashViewModel(UserDao userDao) {
        this.userDao = userDao;
    }
    public LiveData<User> getUser(){
       return userDao.get();
    }
}
