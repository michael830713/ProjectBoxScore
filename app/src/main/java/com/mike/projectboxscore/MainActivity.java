package com.mike.projectboxscore;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mike.projectboxscore.LoginUi.LoginPageFragment;
import com.mike.projectboxscore.LoginUi.LoginPagePresenter;
import com.mike.projectboxscore.MainPage.MainPageFragment;
import com.mike.projectboxscore.MainPage.MainPagePresenter;
import com.mike.projectboxscore.MyTeam.MyTeamFragment;
import com.mike.projectboxscore.NewTeam.NewTeamFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LoginPagePresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demoLoginView();
    }

    private void demoLoginView() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        LoginPageFragment fragment = LoginPageFragment.newInstance();
        mLoginPresenter = new LoginPagePresenter(fragment);
        fragmentTransaction.replace(R.id.container, fragment, "LoginFragment");
        fragmentTransaction.commit();

    }

    private void demoSurfaceView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        try {
            MainPageFragment fragment = MainPageFragment.newInstance();
            fragmentTransaction.replace(R.id.container, fragment, "Surface");
            fragmentTransaction.commit();

        } catch (Throwable t) {
            Log.d(TAG, "demoSurfaceView: " + t);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                Log.i(TAG, "backstack entry before pop: " + getSupportFragmentManager().getBackStackEntryCount());
                getSupportFragmentManager().popBackStackImmediate();
                Log.i(TAG, "backstack entry: " + getSupportFragmentManager().getBackStackEntryCount());
            }
        }
    }

    public static boolean isFragmentInBackstack(final FragmentManager fragmentManager, final String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return true;
            }
        }
        return false;
    }

}
