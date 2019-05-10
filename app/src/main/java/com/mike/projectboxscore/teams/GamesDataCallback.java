package com.mike.projectboxscore.teams;

import com.mike.projectboxscore.datas.Game;

import java.util.ArrayList;

public interface GamesDataCallback {
    void loadGameCallBack(ArrayList<Game> games);
}
