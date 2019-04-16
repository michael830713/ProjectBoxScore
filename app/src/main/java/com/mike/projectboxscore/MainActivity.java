package com.mike.projectboxscore;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mike.projectboxscore.loginUI.LoginUiFragment;
import com.mike.projectboxscore.loginUI.LoginUIPresenter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LoginUIPresenter mSurfaceViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demoSurfaceView();
    }

    private void demoSurfaceView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        try {
            LoginUiFragment fragment = LoginUiFragment.newInstance();
            fragmentTransaction.replace(R.id.container, fragment, "Surface");
            fragmentTransaction.commit();
            mSurfaceViewPresenter = new LoginUIPresenter(fragment);
        } catch (Throwable t) {
            Log.d(TAG, "demoSurfaceView: " + t);
        }

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
//        super.onBackPressed();
    }
}
