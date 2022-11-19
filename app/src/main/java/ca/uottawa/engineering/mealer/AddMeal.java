package ca.uottawa.engineering.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        String description = inputs[5]; // I think the order got reversed
        String price = inputs[4];
        Meal meal= new Meal(name,type,ingredient,allergies,description,price, chefName);
        //push the meal to the data base
        db.collection("users/" + mAuth.getUid() + "/menu/").document().set(meal);
    }

    public String[] getInput() { //Get user input
        String[] inputs = new String[7];

        EditText name = (EditText) findViewById(R.id.TypeOfMealPlainText);
        EditText type = (EditText) findViewById(R.id.TypeOfCuisinePlainText);
        EditText ingredient = (EditText) findViewById(R.id.IngredientsPlainText);
        EditText allergies = (EditText) findViewById(R.id.AllergiesPlainText);
        EditText description = (EditText) findViewById(R.id.DescriptionPlainText);
        EditText price = (EditText) findViewById(R.id.price);

        inputs[0] = name.getText().toString();
        inputs[1] = type.getText().toString();
        inputs[2] = ingredient.getText().toString();
        inputs[3] = allergies.getText().toString();
        inputs[4] = description.getText().toString();
        inputs[5] = price.getText().toString();

        return inputs;
    }
    public boolean validateInput(String[] inputs) {

        for (String input: inputs) {
            input = input.trim();
            if (input.isEmpty()) {
                errorMessage("Please make sure fields aren't empty.");
                return false;
            }
        }



        return true;
    }
    public void errorMessage(String message) {
        TextView errormsg = (TextView) findViewById(R.id.ErrormsgAddMeal);
        errormsg.setText(message);
    }

}