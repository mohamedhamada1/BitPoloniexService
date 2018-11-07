package sa.bitoasis.bitoasis.views.main;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import sa.bitoasis.bitoasis.di.ViewModelProviderFactory;
import sa.bitoasis.bitoasis.user.User;
import sa.bitoasis.bitoasis.views.login.LoginViewModel;



@Module
public class TickerActivityModule {

    @Provides
    ViewModelProvider.Factory provideTickerViewModelFactory(TickerViewModel tickerViewModel) {
        return new ViewModelProviderFactory<>(tickerViewModel);
    }
    @Provides
    TickerAdapter provideTickerAdapterAdapter(TickerActivity activity) {
        return  new TickerAdapter(activity);

    }

}
