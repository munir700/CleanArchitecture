package android.assignment.di.modules;

import android.app.Application;
import android.assignment.managers.AppManager;
import android.assignment.preferences.PreferenceHandler;
import android.assignment.utils.NetworkUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    AppManager providesAppManager(Application application, PreferenceHandler preferenceHandler) {
        AppManager appManager = new AppManager(application, preferenceHandler);
        return appManager;
    }


    @Provides
    @Singleton
    NetworkUtils providesNetworkUtils(Application application) {
        NetworkUtils networkUtils = new NetworkUtils(application);
        return networkUtils;
    }

}
