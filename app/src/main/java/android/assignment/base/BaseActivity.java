package android.assignment.base;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.assignment.R;
import android.assignment.enums.ViewModelEventsEnum;
import android.assignment.interfaces.ViewModelCallBackObserver;
import android.assignment.ui.ActionDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.LinearLayout;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * @author Munir Ahmad.
 */

public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity implements ViewModelCallBackObserver {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private LinearLayout progressBar;
    public DB binding;
    public VM viewModel;

    public abstract Class<VM> getViewModel();

    @LayoutRes
    public abstract int getLayoutRes();

    @Override
    public void onObserve(ViewModelEventsEnum event, Object eventMessage) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
        viewModel.addObserver(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        if (binding != null) {
            //TODO use layout in xml.It is recommended for approact.
            //otherwise the binding object is going to be null and non of the views are accessable.
            progressBar = binding.getRoot().findViewById(R.id.progressBar);
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public void onProgressClick(View v) {
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.appManager.mustDie(this);
    }

    protected void doStartActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }


    public void showProgress() {
        if (progressBar != null) {
            if (!progressBar.isShown()) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hideProgress() {
        if (progressBar != null) {
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * on Server Request Failed.
     */
    public void onApiRequestFailed(String message) {
        new ActionDialog(this, message, null, true).show();
    }


}
