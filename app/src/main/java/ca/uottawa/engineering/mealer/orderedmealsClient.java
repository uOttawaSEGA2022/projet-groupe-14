package ca.uottawa.engineering.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ca.uottawa.engineering.mealer.classes.Order;
import ca.uottawa.engineering.mealer.helpers.ChefHandler;
import ca.uottawa.engineering.mealer.helpers.ChefSingleton;
import ca.uottawa.engineering.mealer.helpers.ClientHandler;
import ca.uottawa.engineering.mealer.helpers.ClientSingleton;

public class orderedmealsClient extends AppCompatActivity {
    private final String TAG = "client_ordered";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Order> orders = new ArrayList<>();

    ArrayAdapter<Order> adapter;
    ListView listView;
    private  ClientHandler clientHandler = ClientSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderedmeals_client);
        Log.d(TAG, clientHandler.getClient().getName());
        retrieveOrderedMeals();
        listView = (ListView) findViewById(R.id.orderedlist);

        adapter = new ArrayAdapter<Order>
                (this, android.R.layout.simple_list_item_1, orders);

        listView.setAdapter(adapter);
        listView.setClickable(true);

    }
    @Override
    public void onRestart() {
        super.onRestart();
        this.recreate();
    }
    private void retrieveOrderedMeals() {
        db.collection("orderedMeals")
                .whereEqualTo("chefName", clientHandler.getClient().getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                // get meal from reference
                                DocumentReference orderRef = (DocumentReference) document.get("mealRef");
                                orderRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Order order = documentSnapshot.toObject(Order.class);
                                        order.setMealName(document.get("mealName").toString());
                                        order.setClientName(document.get("clientName").toString());
                                        order.setState(document.get("state").toString());
                                        orders.add(order);
                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}