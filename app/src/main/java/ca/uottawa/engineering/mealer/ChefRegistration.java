package ca.uottawa.engineering.mealer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

import ca.uottawa.engineering.mealer.classes.Chef;

public class ChefRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final String TAG = "chefReg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_registration);
    }

    // Register button press
    public void register(View view) throws InterruptedException {

        errorMessage("");
        String[] inputs = getInput();
        boolean valid = validateInput(inputs);

        if (!valid) {
            return;
        }

        String nickname = inputs[0];
        String name = inputs[1];
        String email = inputs[2];
        String password = inputs[3];
        String address = inputs[4];
        String cheque = inputs[5];
        String description = inputs[6];

        Log.d(TAG, email);
        Log.d(TAG, password);

        mAuth.createUserWithEmailAndPassword(email, password) // Create new user
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mAuth = FirebaseAuth.getInstance();
                            String userID = mAuth.getUid();
                            Log.d(TAG, userID);

                            Chef chef = new Chef(nickname, name, email, address, cheque, description);
                            db.collection("users").document(userID).set(chef); // Add to database
                            Log.d(TAG, "Chef created and added.");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "Chef not created.");
                            // TODO: add message to user
                        }
                    }
                });

        TimeUnit.SECONDS.sleep(1); // Sometimes it goes too fast and the database doesn't update fast enough
        if (mAuth.getUid() != null) {
            Intent switchActivityIntent = new Intent(this, Homepage.class);
            startActivity(switchActivityIntent);
        }
    }

    public void errorMessage(String message) {
        TextView errormsg = (TextView) findViewById(R.id.errormsgchef);
        errormsg.setText(message);
    }

    public boolean validateInput(String[] inputs) {

        for (String input: inputs) {
            input = input.trim();
            if (input.isEmpty()) {
                errorMessage("Please make sure fields aren't empty.");
                return false;
            }
        }

        if (!inputs[2].contains("@")) {
            errorMessage("Please enter a valid email address.");
            return false;
        }
        if (inputs[3].length() < 6) {
            errorMessage("Please have a password that is at least 6 characters long.");
            return false;
        }

        return true;
    }


    public String[] getInput() {
        String[] inputs = new String[7];

        EditText nickT = (EditText) findViewById(R.id.NicknameEdit);
        EditText nameT = (EditText) findViewById(R.id.NameEdit);
        EditText emailT = (EditText) findViewById(R.id.EmailEdit);
        EditText passT = (EditText) findViewById(R.id.chefPasswordEdit);
        EditText addrT = (EditText) findViewById(R.id.AdressEdit);
        EditText chequeT = (EditText) findViewById(R.id.informationchequeEdit);
        EditText descT = (EditText) findViewById(R.id.DescriptionEdit);

        inputs[0] = nickT.getText().toString();
        inputs[1] = nameT.getText().toString();
        inputs[2] = emailT.getText().toString();
        inputs[3] = passT.getText().toString();
        inputs[4] = addrT.getText().toString();
        inputs[5] = chequeT.getText().toString();
        inputs[6] = descT.getText().toString();

        return inputs;
    }

}
