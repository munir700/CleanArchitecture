package android.assignment.managers;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

public class AppManager {

    private Context appContext;

    @Inject
    public AppManager(Application application) {
        appContext = application.getApplicationContext();
    }

    public Context getContext() {
        return appContext;
    }

    public void setContext(Context appContext) {
        this.appContext = appContext;
    }

    public String getResourceString(int resourceName) {
        return getContext().getString(resourceName);
    }
}
