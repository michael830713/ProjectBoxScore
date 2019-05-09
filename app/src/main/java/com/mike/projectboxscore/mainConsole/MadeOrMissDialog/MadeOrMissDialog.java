package com.mike.projectboxscore.mainConsole.MadeOrMissDialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mike.projectboxscore.Constants;
import com.mike.projectboxscore.Data.PlayerStats;
import com.mike.projectboxscore.R;
import com.mike.projectboxscore.mainConsole.MainConsoleViewContract;
import com.mike.projectboxscore.mainConsole.substituteDialog.SubContract;
import com.mike.projectboxscore.mainConsole.substituteDialog.SubstituteAdapter;

import java.util.ArrayList;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class MadeOrMissDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "SubstituteDialog";

    private Button mMadeButton;
    private Button mMissButton;
    MadeOrMissCallback madeOrMissCallback;

    public void setMadeOrMissCallback(MadeOrMissCallback madeOrMissCallback) {
        this.madeOrMissCallback = madeOrMissCallback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_made_or_miss, container, false);
        mMadeButton = view.findViewById(R.id.buttonMade);
        mMissButton = view.findViewById(R.id.buttonMiss);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMadeButton.setOnClickListener(this);
        mMissButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonMade:
                madeOrMissCallback.madeOrMissCallBack(Constants.MADE);
                break;

            case R.id.buttonMiss:
                madeOrMissCallback.madeOrMissCallBack(Constants.MISS);

                break;
        }
    }
}
