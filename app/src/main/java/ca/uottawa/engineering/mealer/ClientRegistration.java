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

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import ca.uottawa.engineering.mealer.classes.Client;

public class ClientRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final String TAG = "clientReg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_registration);
    }

    public void registerclick(View view) throws InterruptedException {

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
        String cardNum = inputs[5];
        String cardExp = inputs[6];
        String cardCVV = inputs[7];

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

                            Client client = new Client(nickname, name, email, address, cardNum, cardExp, cardCVV);
                            db.collection("users").document(userID).set(client); // Add to database
                            Log.d(TAG, "client created and added.");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "client not created.");
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
        TextView errormsg = (TextView) findViewById(R.id.errormsg);
        errormsg.setText(message);
    }

    public boolean validateInput(String[] inputs) {

        String regex = "[0-9]+";

        for (String input : inputs) {
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
            errorMessage("Password must be 6 characters or longer.");
            return false;
        }
        if (!inputs[5].matches(regex)) {
            errorMessage("Please use only numbers for card information.");
            return false;
        }
        if (!inputs[6].contains("/") || inputs[6].length() != 5) {
            if (inputs[6].charAt(2) != '/')
                errorMessage("Please enter expiration date in form: 'XX/XX'.");
            return false;
        }
        if (!inputs[7].matches(regex) || inputs[7].length() != 3) {
            errorMessage("Please use only numbers for card CVV");
            return false;
        }

        return true;
    }

    public String[] getInput() {
        String[] inputs = new String[8];

        EditText nickT = (EditText) findViewById(R.id.NicknameEditClient);
        EditText nameT = (EditText) findViewById(R.id.NameEditClient);
        EditText emailT = (EditText) findViewById(R.id.EmailEditClient);
        EditText passT = (EditText) findViewById(R.id.PasswordEditClient);
        EditText addrT = (EditText) findViewById(R.id.AdressEditClient);
        EditText cardNumT = (EditText) findViewById(R.id.CardNumberEdit);
        EditText cardExpT = (EditText) findViewById(R.id.DateExp);
        EditText cardCVVT = (EditText) findViewById(R.id.CVVnum);

        inputs[0] = nickT.getText().toString();
        inputs[1] = nameT.getText().toString();
        inputs[2] = emailT.getText().toString();
        inputs[3] = passT.getText().toString();
        inputs[4] = addrT.getText().toString();
        inputs[5] = cardNumT.getText().toString();
        inputs[6] = cardExpT.getText().toString();
        inputs[7] = cardCVVT.getText().toString();

        return inputs;
    }

}