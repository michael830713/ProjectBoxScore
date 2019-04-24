package com.mike.projectboxscore.LoginUi;

import com.google.firebase.firestore.DocumentSnapshot;

public interface FirebaseDataCallback {
    void firebaseDataCallBack(DocumentSnapshot snapshot);
}
