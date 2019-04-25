package android.assignment.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

public class AppConstant {
    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        try {
            imageView.setImageResource(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
