package android.assignment.di.modules;


import android.assignment.SplashActivity;
import android.assignment.views.activities.ImageSliderActivity;
import android.assignment.views.activities.MovieDetailActivity;
import android.assignment.views.activities.MoviesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MoviesActivity mortgageActivity();

    @ContributesAndroidInjector
    abstract MovieDetailActivity movieDetailActivity();

    @ContributesAndroidInjector
    abstract ImageSliderActivity imageSliderActivity();
}
