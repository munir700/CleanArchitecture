package android.assignment.ui;

import android.app.Dialog;
import android.assignment.R;
import android.assignment.adapter.SortDialogAdapter;
import android.assignment.models.SortModel;
import android.assignment.preferences.PreferenceHandler;
import android.assignment.utils.AppConstants;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import java.util.List;

import static android.assignment.utils.AppConstants.INDEX_NOW_PLAYING;
import static android.assignment.utils.AppConstants.INDEX_TOP_RATED;
import static android.assignment.utils.AppConstants.INDEX_POPULARITY;
import static android.assignment.utils.AppConstants.INDEX_UP_COMING;

public class SortDialog extends Dialog {
    public static final int INITIAL_POSITION = -1;
    private Context context;
    private PreferenceHandler preferenceHandler;

    private List<SortModel> SortModelList;
    private int selectedPosition = INITIAL_POSITION;

    public SortDialog(@NonNull Context context, List<SortModel> SortModelList, PreferenceHandler preferenceHandler) {
        super(context);
        this.context = context;
        this.SortModelList = SortModelList;
        this.preferenceHandler = preferenceHandler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_sort);

        initUI();
    }

    private void initUI() {

        findViewById(R.id.close_ib).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortDialog.this.dismiss();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.sorting_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        SortDialogAdapter sortDialogAdapter = new SortDialogAdapter(context, SortModelList, preferenceHandler.getSortDialogSelection(), new SortDialogAdapter.OnClickListener() {
            @Override
            public void onClick(int position, SortModel propSortingModel) {
                int selectedId = propSortingModel.getId();
                preferenceHandler.setSortDialogSelection(selectedId);
                selectedPosition = selectedId;
                setIndexSort(selectedId);
                SortDialog.this.dismiss();
            }
        });

        recyclerView.setAdapter(sortDialogAdapter);

    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    private void setIndexSort(int position) {

        switch (position) {
            case INDEX_POPULARITY:
                preferenceHandler.setLastSelectedSort(AppConstants.POPULARITY);
                preferenceHandler.setLastSelectedSortTitle(context.getString(R.string.STR_POPULAR));
                break;
            case INDEX_NOW_PLAYING:
                preferenceHandler.setLastSelectedSort(AppConstants.NOWPLAYING);
                preferenceHandler.setLastSelectedSortTitle(context.getString(R.string.STR_NOW_PLAYING));
                break;
            case INDEX_UP_COMING:
                preferenceHandler.setLastSelectedSort(AppConstants.UPCOMING);
                preferenceHandler.setLastSelectedSortTitle(context.getString(R.string.STR_UPCOMING));
                break;
            case INDEX_TOP_RATED:
                preferenceHandler.setLastSelectedSort(AppConstants.TOPRATED);
                preferenceHandler.setLastSelectedSortTitle(context.getString(R.string.STR_TOP_RATED));
                break;
            default:
                preferenceHandler.setLastSelectedSort("");
                break;
        }

    }

}
