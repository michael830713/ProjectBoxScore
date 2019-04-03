package com.mike.projectboxscore;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mike.projectboxscore.loginUI.LoginUIFragment;
import com.mike.projectboxscore.loginUI.LoginUIPresenter;

public class MainActivity extends AppCompatActivity {

    private LoginUIPresenter mSurfaceViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demoSurfaceView();
    }

    private void demoSurfaceView() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        LoginUIFragment fragment = LoginUIFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, "Surface");
        fragmentTransaction.commit();
        mSurfaceViewPresenter = new LoginUIPresenter(fragment);
    }

}
