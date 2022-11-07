package ca.uottawa.engineering.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class ChefPage extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "chefpage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_page);

        String userId = mAuth.getUid(); // Get user ID

        // TODO: put the retrieval process behind a reusable method/class?
        DocumentReference docRef = db.collection("users").document(userId); // Retrieve user information by user ID
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        handleChefData(document);
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

    public void handleChefData(DocumentSnapshot document) {
        TextView hText = (TextView) findViewById(R.id.chefPageText);
        String role = document.get("role").toString();
        String name = document.get("name").toString();
        String suspension;

        Timestamp timestamp = (Timestamp) document.get("suspension");
        Date date = timestamp.toDate();
        long time = date.getTime();
        Log.i("CHEFPAGE", String.valueOf(time));

        if (time == 0L) {
            suspension = "not suspended";
        } else if (time < 1000000) {
            suspension = "permanently suspended";
        } else {
            long days = date.getTime() / (24 * 60 * 60 * 1000);
            suspension = String.format("suspended for %s days", days);
        }


        hText.setText(String.format("Hello %s!\nYou are signed in as %s. You are %s.", name, role, suspension));
    }

    // Logout button press
    public void logoutUser(View view) {
        mAuth.signOut();
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}