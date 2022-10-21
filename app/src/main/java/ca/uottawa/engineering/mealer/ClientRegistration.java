package ca.uottawa.engineering.mealer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

import ca.uottawa.engineering.mealer.classes.Client;

public class ClientRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_registration);
    }

    public void registerclick(View view) throws InterruptedException {
        EditText nickT = (EditText)findViewById(R.id.NicknameEditClient);
        EditText nameT = (EditText)findViewById(R.id.NameEditClient);
        EditText emailT = (EditText)findViewById(R.id.EmailEditClient);
        EditText passT = (EditText)findViewById(R.id.PasswordEditClient);
        EditText addrT = (EditText)findViewById(R.id.AdressEditClient);
        EditText cardNumT = (EditText)findViewById(R.id.CardNumberEdit);
        EditText cardExpT = (EditText)findViewById(R.id.DateExp);
        EditText cardCVVT = (EditText)findViewById(R.id.CVVnum);

        String nickname = nickT.getText().toString();
        String name = nameT.getText().toString();
        String email = emailT.getText().toString();
        String password = passT.getText().toString();
        String address = addrT.getText().toString();
        String cardNum = cardNumT.getText().toString();
        String cardExp = cardExpT.getText().toString();
        String cardCVV = cardCVVT.getText().toString();

        Log.d("FDB", email);
        Log.d("FDB", password);

        mAuth.createUserWithEmailAndPassword(email, password) // Create new user
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mAuth = FirebaseAuth.getInstance();
                            String userID = mAuth.getUid();
                            Log.d("FDB", userID);

                            Client client = new Client(nickname, name, email, address, cardNum, cardExp, cardCVV);
                            db.collection("users").document(userID).set(client); // Add to database
                            Log.d("FDB", "client created and added.");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("FDB", "client not created.");
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

    public String[] validateAndReturnInput() {
        return null;
    }

    public void createUser(String[] input) {

    }
}