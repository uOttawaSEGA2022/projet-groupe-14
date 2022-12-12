package ca.uottawa.engineering.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import ca.uottawa.engineering.mealer.classes.Chef;
import ca.uottawa.engineering.mealer.classes.Meal;
import ca.uottawa.engineering.mealer.classes.Order;

public class ChefMealStatus extends AppCompatActivity {
    String orderName;
    Order order;
    DocumentReference ref;
    TextView meal_name;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "chef-meal-status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_meal_status);
        Bundle extras = getIntent().getExtras();
        orderName = (String) extras.get("orderName");
        meal_name = (TextView) findViewById(R.id.MealName);
        getOrder();



    }

    public void OnState(View view){
        order.setState("ready");
        ref.set(order);
    }

    public void getOrder(){
        db.collection("orderedMeals")
                .whereEqualTo("mealName", orderName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            Log.d(TAG, document.getQuery().toString());
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d(TAG, documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                order = documentSnapshot.toObject(Order.class);
                                ref = documentSnapshot.getReference();
                                meal_name.setText(order.getMealName());


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}