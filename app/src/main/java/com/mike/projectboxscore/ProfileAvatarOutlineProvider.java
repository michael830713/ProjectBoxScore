package com.mike.projectboxscore;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class ProfileAvatarOutlineProvider extends ViewOutlineProvider {
    @Override
    public void getOutline(View view, Outline outline) {
        view.setClipToOutline(true);
        int radius = 6;
        outline.setOval(0, 0, radius, radius);
    }
}
