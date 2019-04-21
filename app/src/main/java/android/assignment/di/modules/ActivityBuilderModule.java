package android.assignment.di.modules;


import android.assignment.SplashActivity;
import android.assignment.views.activities.MoviesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MoviesActivity mortgageActivity();
}
