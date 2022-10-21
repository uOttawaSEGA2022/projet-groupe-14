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

import ca.uottawa.engineering.mealer.classes.Chef;

public class ChefRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_registration);
    }

    public void register(View view) throws InterruptedException {
        EditText nickT = (EditText)findViewById(R.id.NicknameEdit);
        EditText nameT = (EditText)findViewById(R.id.NameEdit);
        EditText emailT = (EditText)findViewById(R.id.EmailEdit);
        EditText passT = (EditText)findViewById(R.id.chefPasswordEdit);
        EditText addrT = (EditText)findViewById(R.id.AdressEdit);
        EditText chequeT = (EditText)findViewById(R.id.informationchequeEdit);
        EditText descT = (EditText)findViewById(R.id.DescriptionEdit);

        String nickname = nickT.getText().toString();
        String name = nameT.getText().toString();
        String email = emailT.getText().toString();
        String password = passT.getText().toString();
        String address = addrT.getText().toString();
        int cheque = Integer.parseInt(chequeT.getText().toString());
        String description = descT.getText().toString();

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

                            Chef chef = new Chef(nickname, name, email, address, cheque, description);
                            db.collection("users").document(userID).set(chef); // Add to database
                            Log.d("FDB", "Chef created and added.");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("FDB", "Chef not created.");
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


}
