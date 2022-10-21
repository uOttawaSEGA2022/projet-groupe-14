package ca.uottawa.engineering.mealer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Homepage extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "homepage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        String userId = mAuth.getUid(); // Get user ID

        TextView hText = (TextView) findViewById(R.id.homepageText);

        // TODO: put the retrieval process behind a reusable method/class?
        DocumentReference docRef = db.collection("users").document(userId); // Retrieve user information by user ID
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String role = document.get("role").toString();
                        String name = document.get("name").toString();
                        hText.setText(String.format("Hello %s!\nYou are signed in as %s.", name, role));

                    } else {
                        // TODO: handle when UID does not exist
                        Log.d(TAG, "No such document");
                    }
                } else {
                    // TODO: handle when task is unsuccessful (not retrieved from firebase)
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    // Logout button press
    public void logoutUser(View view) {
        mAuth.signOut();
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

}