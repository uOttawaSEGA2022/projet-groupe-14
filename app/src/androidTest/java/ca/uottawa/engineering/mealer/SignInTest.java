package ca.uottawa.engineering.mealer;

import android.content.Context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

// TODO: test async, await, etc and wait for method to finish
public class SignInTest {

    @Test
    public void adminSignIn() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        String TAG = "ADMIN-SIGNIN";
        String email = "admin@admin.com";
        String password = "adminn";

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        assertEquals("vo6Y1cReIdR3I4YXTUf73HQL3dr1", user.getUid());
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

    @Test
    public void chefSignIn() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String TAG = "CHEF-SIGNIN";
        String email = "s.yead.zaman@gmail.com";
        String password = "mealer";

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        assertEquals("7xgqHmRxNQf5ns4UBkfvkc4VY7b2", user.getUid());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "signInWithEmail:failure");
                        fail("Could not sign in.");
                    }
                });
    }

    @Test
    public void clientSignIn() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String TAG = "CLIENT-SIGNIN";
        String email = "client@client.com";
        String password = "mealer";

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        assertEquals("yXbIAReuDFg03nGNLeLngrnM5Yv2", user.getUid());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "signInWithEmail:failure");
                        fail("Could not sign in.");
                    }
                });
    }

    @Test
    public void nonExistentSignIn() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String TAG = "NONEXISENT-SIGNIN";
        String email = "fake@fake.com";
        String password = "mealer";

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        fail("Should not have signed in.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "signInWithEmail:failure");
                        assertTrue(true);
                    }
                });
    }

}
