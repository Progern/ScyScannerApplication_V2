package progernapplications.scyscannerapplication_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.eyalbira.loadingdots.LoadingDots;

public class SplashScreen extends Activity {

    private Handler mTimeHandler;
    private Intent startMainActivity;
    private LoadingDots mLoadingDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startMainActivity = new Intent(this, MainActivity.class);
        mLoadingDots = (LoadingDots) findViewById(R.id.splash_dots);

        // Start Main Activity after some time

        mTimeHandler = new Handler();
        mTimeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingDots.stopAnimation();
                startActivity(startMainActivity);
            }
        }, 4200);
    }
}
