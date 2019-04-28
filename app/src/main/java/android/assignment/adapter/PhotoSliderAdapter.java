package android.assignment.adapter;

import android.app.Activity;
import android.assignment.BuildConfig;
import android.assignment.R;
import android.assignment.databinding.RowItemPhotosSliderBinding;
import android.assignment.models.ProductionCompanies;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


public class PhotoSliderAdapter extends PagerAdapter {

    public interface OnClickListener {
        void onItemClick(int position, View view);
    }

    private Activity context;
    private ProductionCompanies[] images;

    private RequestOptions imageOptions;

    private OnClickListener onClickListener;
    private PhotoSliderCallBack listener;

    public void setPhotoSliderCallBackListener(PhotoSliderCallBack listener) {
        this.listener = listener;
    }

    public void setPhotos(ProductionCompanies[] images) {
        this.images = images;
        this.notifyDataSetChanged();
    }


    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public PhotoSliderAdapter(Activity context,
                              ProductionCompanies[] images, OnClickListener onClickListener) {
        this.context = context;
        this.images = images;
        this.onClickListener = onClickListener;
        imageOptions = new RequestOptions()
                .placeholder(R.drawable.img_loading_pics)
                .error(R.drawable.no_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup parent, final int position) {


        RowItemPhotosSliderBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.row_item_photos_slider,
                parent, false);

        binding.getRoot().setTag("" + position);
        initializeView(parent, binding, position);


        return binding.getRoot();
    }


    private void initializeView(final ViewGroup container, final RowItemPhotosSliderBinding binding, final int position) {


        ProductionCompanies productionCompany = images[position];

        binding.ivPlaceholder.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);

        binding.ivPlaceholder.setScaleType(ImageView.ScaleType.CENTER_CROP);

        String imgUrl = BuildConfig.IMG_BASE_URL + productionCompany.getLogoPath();

        Glide.with(context).applyDefaultRequestOptions(imageOptions)
                .load(imgUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (PhotoSliderAdapter.this.listener != null)
                            PhotoSliderAdapter.this.listener.readyForTransition();

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (PhotoSliderAdapter.this.listener != null)
                            PhotoSliderAdapter.this.listener.readyForTransition();

                        return false;
                    }
                })
                .into(binding.ivPlaceholder);

        container.addView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.ivPlaceholder.setTransitionName("PROP_DETAILS_TRANSITION" + position);
        }


        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onItemClick(position, view);
                }
            }
        });

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    public interface PhotoSliderCallBack {
        void readyForTransition();
    }


}