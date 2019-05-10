package com.mike.projectboxscore.callback;

import com.google.firebase.firestore.DocumentSnapshot;

public interface FirebaseDataCallback {
    void firebaseDataCallBack(DocumentSnapshot snapshot);
}
