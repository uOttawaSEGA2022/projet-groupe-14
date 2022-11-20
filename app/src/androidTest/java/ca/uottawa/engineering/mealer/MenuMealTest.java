package ca.uottawa.engineering.mealer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;

import ca.uottawa.engineering.mealer.classes.Meal;

// ignore, work in progress
public class MenuMealTest {

    @Test
    public void addMealToFirebase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth.signOut();

        String TAG = "CREATE-MEAL";
        String email = "chef@chef.com";
        String password = "mealer";

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, user.getUid());

                        String name = "Sphagetti";
                        String type = "Italian";
                        String ingredient = "Sphagetti, Meatballs";
                        String allergies = "No Allergies";
                        String description = "Classic Italian Meal";
                        String price = "30$";
                        Meal meal= new Meal(name,type,ingredient,allergies,description,price, "don't work");
                        String path = String.format("users/%s/menu/", mAuth.getUid());
                        Log.d(TAG, path);
                        db.collection(path).document().set(meal);
                        Log.d(TAG, "Added meal to collection");
                        assertEquals("7Oc9YfTiaobMJXL7KFEk7yQCrc22", user.getUid());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "signInWithEmail:failure");
                        fail("Could not sign in");
                    }
                });
    }
}
