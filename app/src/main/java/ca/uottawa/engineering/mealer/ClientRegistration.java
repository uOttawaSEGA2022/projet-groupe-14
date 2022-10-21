package ca.uottawa.engineering.mealer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import ca.uottawa.engineering.mealer.classes.Client;

public class ClientRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String role = "client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_registration);
    }

    public void registerclick(View view) {
        mAuth = FirebaseAuth.getInstance();
        EditText name = (EditText)findViewById(R.id.NicknameEditClient);
        EditText lname = (EditText)findViewById(R.id.NameEditClient);
        EditText email = (EditText)findViewById(R.id.EmailEditClient);
        EditText password = (EditText)findViewById(R.id.PasswordEditClient);
        EditText adress = (EditText)findViewById(R.id.AdressEditClient);
        EditText cardnum = (EditText)findViewById(R.id.CardNumberEdit);
        Client client = new Client(getstring(name),getstring(lname),getstring(email),getstring(adress),"Client",Integer.parseInt(getstring(cardnum)));
        db.collection("users").document(mAuth.getUid()).set(client);
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
    public String getstring(EditText e){
        return e.getText().toString();
    }
}