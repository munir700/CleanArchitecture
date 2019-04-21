package android.assignment.di;


import android.app.Application;
import android.assignment.AppApplication;
import android.assignment.di.modules.ActivityBuilderModule;
import android.assignment.di.modules.AndroidWorkerInjectionModule;
import android.assignment.di.modules.ApiClientModule;
import android.assignment.di.modules.AppModule;
import android.assignment.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiClientModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        AndroidSupportInjectionModule.class,
        AndroidWorkerInjectionModule.class,
        ViewModelModule.class

})
public interface NetComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        NetComponent build();
    }

    void inject(AppApplication app);
}