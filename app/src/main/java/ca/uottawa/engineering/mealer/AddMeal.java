package ca.uottawa.engineering.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.uottawa.engineering.mealer.classes.Meal;

public class AddMeal extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final String TAG = "ADD-MEAL";
    private String chefName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_choice_page);

        DocumentReference docRef = db.collection("users/").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        chefName = (String) document.get("name");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    public void addMealToMenu(View view) throws InterruptedException {
        String[] inputs = getInput();
        boolean valid = validateInput(inputs);

        if(!valid){
            return;
        }

        String name = inputs[0];
        String type = inputs[1];
        String ingredient = inputs[2];
        String allergies = inputs[3];
        String description = inputs[4];
        String price = inputs[5];
        Meal meal= new Meal(name,type,ingredient,allergies,description,price, chefName);
        //push the meal to the data base
        db.collection("users/" + mAuth.getUid() + "/menu/").document().set(meal);
        Context context = getApplicationContext();
        CharSequence text = "Added Meal to Menu!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        onBackPressed();
    }

    private boolean validateInput(String[] inputs) {

        for (String string: inputs) {
            if (string.isEmpty()) {
                errorMessage("Fields cannot be empty!");
                return false;
            }
        }

        if (!inputs[5].matches("[0-9]+")) {
            errorMessage("Please give a number for the cost.");
            return false;
        }

        return true;
    }

    public String[] getInput() { //Get user input
        String[] inputs = new String[6]; //change to 7 if we ever add meal type lol

        EditText name = (EditText) findViewById(R.id.TypeOfMealPlainText);
        EditText type = (EditText) findViewById(R.id.TypeOfCuisinePlainText);
        EditText ingredient = (EditText) findViewById(R.id.IngredientsPlainText);
        EditText allergies = (EditText) findViewById(R.id.AllergiesPlainText);
        EditText description = (EditText) findViewById(R.id.DescriptionPlainText);
        EditText price = (EditText) findViewById(R.id.price);

        inputs[0] = name.getText().toString().trim();
        inputs[1] = type.getText().toString().trim();
        inputs[2] = ingredient.getText().toString().trim();
        inputs[3] = allergies.getText().toString().trim();
        inputs[4] = description.getText().toString().trim();
        inputs[5] = price.getText().toString().trim();

        return inputs;
    }

    public void errorMessage(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}