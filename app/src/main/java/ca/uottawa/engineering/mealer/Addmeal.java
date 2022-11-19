package ca.uottawa.engineering.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

import ca.uottawa.engineering.mealer.classes.Chef;
import ca.uottawa.engineering.mealer.classes.Meal;

public class Addmeal extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final String TAG = "chefReg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_choice_page);
    }
    public void addmeal(View view) throws InterruptedException {

        String[] inputs = getInput();

        String name = inputs[0];
        String type = inputs[1];
        String ingredient = inputs[2];
        String allergies = inputs[3];
        String description = inputs[4];
        String price = inputs[5];
        Meal meal= new Meal(name,type,ingredient,allergies,description,price,"");
        //push the meal to the data base
        db.collection("meals").document("mealid").set(meal);


    }
    public String[] getInput() {
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
}