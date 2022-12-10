package ca.uottawa.engineering.mealer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import ca.uottawa.engineering.mealer.classes.Meal;
import ca.uottawa.engineering.mealer.classes.Order;
import ca.uottawa.engineering.mealer.helpers.ClientHandler;
import ca.uottawa.engineering.mealer.helpers.ClientSingleton;
import ca.uottawa.engineering.mealer.helpers.MealHandler;

public class ClientMeal extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final String TAG = "Clientmeal";
    private MealHandler mealHandler;
    private Meal meal;
    private String username;
    DocumentReference mealRef;
    private ArrayList<Meal> orderedmeals = new ArrayList<>();
    private final ClientHandler clientHandler = ClientSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal);

        Bundle extras = getIntent().getExtras();
        meal = (Meal) extras.get("meal");
        mealHandler = new MealHandler(meal.getName());
        username = clientHandler.getClient().getName();

        TextView chefname = (TextView) findViewById(R.id.chefName);

        chefname.setText(meal.getChefName());
    }

    public void addToOrderedMeals(View view) throws InterruptedException {
        Order order = new Order(meal.getChefName(), username, mealHandler.getMealRef(), meal.getName(), "incomplete");

        db.collection("orderedMeals").document()
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Context context = getApplicationContext();
                        CharSequence text = "Added meal to ordered meals!";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        onBackPressed();
    }
    }
