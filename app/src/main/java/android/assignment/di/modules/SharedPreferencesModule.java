package android.assignment.di.modules;

import android.app.Application;
import android.assignment.preferences.PreferenceHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    @Provides
    @Singleton
    PreferenceHandler provideSharedPreferences(Application application) {
        PreferenceHandler preferenceHandler = new PreferenceHandler(application);
        return preferenceHandler;
    }
}