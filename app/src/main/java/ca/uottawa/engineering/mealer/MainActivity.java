package ca.uottawa.engineering.mealer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.EditText;
import android.widget.TextView;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     * Will get role of user then call switch_page() to get the right homepage.
     */
    private void login() {
        String TAG = "role-login";

        DocumentReference docRef = db.collection("users").document(mAuth.getUid().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                final String role;
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        role = (String) document.get("role");
                        switch_page(role);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * Switches to the right homepage based on user role.
     * @param role
     */
    private void switch_page(String role) {
        switch (role) {
            case "admin":
                Intent switchActivityIntent = new Intent(this, AdminPage.class);
                startActivity(switchActivityIntent);
                break;
        }
    }

    // Login button press
    public void loginUser(View view) {

        errorMessage("");
        String[] inputs = getInput();
        boolean valid = validateInput(inputs);

        if (!valid) {
            return;
        }

        String username = inputs[0];
        String password = inputs[1];

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        login();
                    } else {
                        // Do something with error;
                        errorMessage("Could not login.");
                    }
                });


    }

    // Register button press
    public void register(View view) {
        Intent switchActivityIntent = new Intent(this, RegistrationPage.class);
        startActivity(switchActivityIntent);
    }

    public void errorMessage(String message) {
        TextView errormsg = (TextView) findViewById(R.id.errormsgmain);
        errormsg.setText(message);
    }

    public boolean validateInput(String[] inputs) {

        for (String input : inputs) {
            input = input.trim();
            if (input.isEmpty()) {
                errorMessage("Please make sure fields aren't empty.");
                return false;
            }
        }

        if (!inputs[0].contains("@")) {
            errorMessage("That is not a valid email.");
            return false;
        }
        if (inputs[1].length() < 6) {
            errorMessage("Password should be 6 characters or more.");
            return false;
        }

        return true;
    }

    public String[] getInput() {

        String[] inputs = new String[2];

        EditText uText = (EditText) findViewById(R.id.usernameTextEdit);
        EditText pText = (EditText) findViewById(R.id.passwordTextEdit);

        inputs[0] = uText.getText().toString();
        inputs[1] = pText.getText().toString();

        return inputs;
    }

}
