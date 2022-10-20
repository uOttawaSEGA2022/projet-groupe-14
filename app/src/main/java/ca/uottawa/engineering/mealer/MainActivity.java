package ca.uottawa.engineering.mealer;

import static android.content.ContentValues.TAG;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import android.widget.Button;
import android.widget.EditText;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button loginButton;

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

        mAuth = FirebaseAuth.getInstance();

            }

    private void login() {
        Intent switchActivityIntent = new Intent(this, Homepage.class);
        startActivity(switchActivityIntent);
        }

    public void loginUser(View view) {
            EditText uText = (EditText) findViewById(R.id.usernameTextEdit);
            String username = uText.getText().toString();

            EditText pText = (EditText) findViewById(R.id.passwordTextEdit);
            String password = pText.getText().toString();

            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            login();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
    //                        Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
    //                                Toast.LENGTH_SHORT).show();
                            login();
                        }
                    });
        }

    }
