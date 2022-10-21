package ca.uottawa.engineering.mealer;

import static android.content.ContentValues.TAG;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

//    // Check if user is signed in already
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
////            reload(); // Do something
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void login() {
        Intent switchActivityIntent = new Intent(this, Homepage.class);
        startActivity(switchActivityIntent);
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
