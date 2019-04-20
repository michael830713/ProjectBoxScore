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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LoginPagePresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        demoSurfaceView();
        demoLoginView();
    }

    private void demoLoginView() {

        LoginPageFragment fragment = LoginPageFragment.newInstance();
        mLoginPresenter = new LoginPagePresenter(fragment);
        setFragmentToContainer(fragment, false);

    }

    private void demoSurfaceView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        try {
            MainPageFragment fragment = MainPageFragment.newInstance();
//            mLoginPresenter = new MainPagePresenter(fragment);
            fragmentTransaction.replace(R.id.container, fragment, "Surface");
            fragmentTransaction.commit();

        } catch (Throwable t) {
            Log.d(TAG, "demoSurfaceView: " + t);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        fragment.getClass().getName();
        Log.d(TAG, "fragment: " + fragment.getClass().getName());
        if (fragment.getClass() == MyTeamFragment.class) {
            MainPageFragment mainPageFragment = MainPageFragment.newInstance();
            setFragmentToContainer(mainPageFragment, false);
            MainPagePresenter mainPagePresenter = new MainPagePresenter(mainPageFragment);
        } else if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void setFragmentToContainer(Fragment fragment, boolean adddToBackStack) {
        final String tag = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();

        if (isFragmentInBackstack(manager, tag)) {
            // Fragment exists, go back to that fragment
            manager.popBackStackImmediate(tag, 0);

        } else {
            // Fragment doesn't exist

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            if (adddToBackStack) transaction.addToBackStack(tag);
            transaction.commit();
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
