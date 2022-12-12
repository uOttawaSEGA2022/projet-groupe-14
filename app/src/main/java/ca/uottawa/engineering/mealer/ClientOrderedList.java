package ca.uottawa.engineering.mealer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import ca.uottawa.engineering.mealer.helpers.ClientHandler;
import ca.uottawa.engineering.mealer.helpers.ClientSingleton;

public class ClientOrderedList extends AppCompatActivity {
    private final String TAG = "client-ordered-list";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ClientHandler clientHandler = ClientSingleton.getInstance();

    private ArrayList<Order> orders = new ArrayList<>();

    ArrayAdapter<Order> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_ordered_meals);
        Log.d(TAG, clientHandler.getClient().getName());
        retrieveOrderedMeals();
        listView = (ListView) findViewById(R.id.clientOrderedList);

        adapter = new ArrayAdapter<Order>
                (this, android.R.layout.simple_list_item_1, orders);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Order order = orders.get(i);
                Log.d(TAG, order.getMealName());

                switch_page(order);
            }
        });

    }
    @Override
    public void onRestart() {
        super.onRestart();
        this.recreate();
    }

    private void retrieveOrderedMeals() {
        db.collection("orderedMeals")
                .whereEqualTo("clientName", clientHandler.getClient().getName())
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
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void switch_page(Order order) {
        // TODO: currently switching to wrong activity
        Intent switchActivityIntent = new Intent(this, ClientMealStatusActivity.class);
        switchActivityIntent.putExtra("orderName", order.getMealName());

        startActivity(switchActivityIntent);
    }
}