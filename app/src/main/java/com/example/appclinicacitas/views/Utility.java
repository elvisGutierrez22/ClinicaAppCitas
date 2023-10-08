package com.example.appclinicacitas.views;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {
    static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReferenceForAppointment(){//quitar el Context context, String message
        //quitar todo esto
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            return FirebaseFirestore.getInstance().collection("Citas")
                    .document(currentUser.getUid()).collection("Mis_citas");
        } else {
            return null;
        }


        //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //return FirebaseFirestore.getInstance().collection("Citas")
                //.document(currentUser.getUid()).collection("Mis_citas");
    }
}
