package ca.uottawa.engineering.mealer.handlers;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.uottawa.engineering.mealer.classes.Client;

public class ClientHandler {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = "Chef-Handler";

    private DocumentReference clientRef;
    private Client client;

    /**
     * Creates ClientHandler but sets nothing.
     */
    public ClientHandler() {
    }

    /**
     * Creates ClientHandler and automatically retrieves Client and Client ref from firebase.
     *
     * @param mAuth
     */
    public ClientHandler(FirebaseAuth mAuth) {
        clientRef = db.collection("users").document(mAuth.getUid());

        clientRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                client = documentSnapshot.toObject(Client.class);
            }
        });
    }

    /**
     * Updates firebase client with current client object values.
     */
    public void updateClient() {
        clientRef.set(client);
    }

    public Client getClient() {
        return client;
    }

    public DocumentReference getClientRef() {
        return clientRef;
    }
}
