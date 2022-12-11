package ca.uottawa.engineering.mealer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import ca.uottawa.engineering.mealer.classes.Chef;
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
    private Chef chef;
    private Meal meal;
    private String username;
    private final ClientHandler clientHandler = ClientSingleton.getInstance();

    TextView chefReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal);

        Bundle extras = getIntent().getExtras();
        meal = (Meal) extras.get("meal");
        mealHandler = new MealHandler(meal.getName());
        username = clientHandler.getClient().getName();

        getChefInfo();

        TextView chefName = (TextView) findViewById(R.id.chefName);
        chefName.setText(meal.getChefName());
        chefReviews = (TextView) findViewById(R.id.chefReviews);
        TextView clientMealName = (TextView) findViewById(R.id.clientMealName);
        clientMealName.setText(meal.getName());
        TextView mealDescription = (TextView) findViewById(R.id.mealDescription);
        mealDescription.setText(meal.getDesc());
        TextView mealIngredients = (TextView) findViewById(R.id.mealIngredients);
        mealIngredients.setText(meal.getIngredients());
        TextView mealType = (TextView) findViewById(R.id.mealType);
        mealType.setText(meal.getCuisineType());
        TextView mealAllergies = (TextView) findViewById(R.id.mealAllergies);
        mealAllergies.setText(meal.getAllergies());
        TextView mealPrice = (TextView) findViewById(R.id.mealPrice);
        mealPrice.setText(meal.getCost());
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

    public void getChefInfo() {
        db.collection("users")
                .whereEqualTo("name", meal.getChefName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            Log.d(TAG, document.getQuery().toString());
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d(TAG, documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                chef = documentSnapshot.toObject(Chef.class);

                                String rating;
                                double ratingNumber = chef.getReview();
                                if (ratingNumber == 0) {
                                    rating = "No Reviews";
                                } else {
                                    rating = String.format("%.1f out of 5 stars", ratingNumber);
                                }

                                chefReviews.setText(rating);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void onClickProfile(View view) {
        Intent switchActivityIntent = new Intent(this, chefProfileinfo.class);
        switchActivityIntent.putExtra("chefname", meal.getChefName());
        startActivity(switchActivityIntent);
    }
}
