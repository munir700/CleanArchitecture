package android.assignment;

import android.assignment.views.activities.MoviesActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashActivity extends AppCompatActivity {

    public static final String TAG = SplashActivity.class.getSimpleName();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {

                startActivity(MoviesActivity.newIntent(SplashActivity.this));
                SplashActivity.this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView logoImage = findViewById(R.id.splash);
        Animation mFadeInAnimation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in_splash);
        logoImage.startAnimation(mFadeInAnimation);


        mHandler = new Handler();
        mHandler.postDelayed(runnable, 2000);

    }


    @Override
    public void onBackPressed() {
        try {
            mHandler.removeCallbacks(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }
}