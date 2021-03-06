/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mike.projectboxscore.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mike.projectboxscore.datas.Team;
import com.mike.projectboxscore.base.BasePresenter;
import com.mike.projectboxscore.base.BaseView;

import java.util.ArrayList;

/**
 * Created by Wayne Chen on 2018/4/16.
 * <p>
 * This specifies the contract between the view and the presenter.
 */
public interface LoginPageContract {

    interface View extends BaseView<Presenter> {


        void setupGoogleSignInUi();

        void firebaseAuthWithGoogleUi(GoogleSignInAccount account);

        void demoMainPageUi();

        void googleSignInUi();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        ArrayList<Team> getTeams();


        void setupGoogleSignIn();

        void firebaseAuthWithGoogle(GoogleSignInAccount account);

        void demoMainPage();

        void googleSignIn();

    }
}
