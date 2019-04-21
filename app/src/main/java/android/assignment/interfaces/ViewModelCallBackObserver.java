package android.assignment.interfaces;

import android.assignment.enums.ViewModelEventsEnum;


public interface ViewModelCallBackObserver<T> {

    void onObserve(ViewModelEventsEnum event, T eventMessage);

}
