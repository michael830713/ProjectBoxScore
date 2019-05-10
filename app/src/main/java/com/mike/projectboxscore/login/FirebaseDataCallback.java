package com.mike.projectboxscore.login;

import com.google.firebase.firestore.DocumentSnapshot;

public interface FirebaseDataCallback {
    void firebaseDataCallBack(DocumentSnapshot snapshot);
}
