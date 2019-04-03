package com.mike.projectboxscore.mainConsole;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mike.projectboxscore.R;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MainConsoleFragment extends Fragment implements MainConsoleViewContract.View {

    MainConsoleViewContract.Presenter mPresenter;
    private OnCourtPlayerAdapter mOnCourtPlayerAdapter;

    public MainConsoleFragment() {
        // Requires empty public constructor
    }

    public static MainConsoleFragment newInstance() {
        return new MainConsoleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOnCourtPlayerAdapter = new OnCourtPlayerAdapter();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.console_fragment, container, false);
        RecyclerView playerRecyclerView = root.findViewById(R.id.recyclerview_onCourt_players);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        playerRecyclerView.setLayoutManager(linearLayoutManager);
        playerRecyclerView.setAdapter(mOnCourtPlayerAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void initView() {

    }

    @Override
    public void showFullscreenModeUi(int videoWidth, int videoHeight) {

    }

    @Override
    public void showNormalModeUi(int videoWidth, int videoHeight) {

    }

    @Override
    public void addSurfaceHolderCallback(SurfaceHolder.Callback callback) {

    }

    @Override
    public void requestLandscapeUi() {

    }

    @Override
    public void requestPortraitUi() {

    }

    @Override
    public void setPresenter(MainConsoleViewContract.Presenter loginUiPresenter) {
        mPresenter = checkNotNull(loginUiPresenter);
    }
}
