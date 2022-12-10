package ca.uottawa.engineering.mealer.classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Order implements Parcelable {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String chefName;
    private String clientName;
    private DocumentReference mealRef;
    private String mealName;
    private String state;

    public Order() {
    }

    public Order(String chefName, String clientName, DocumentReference mealRef, String mealName, String state) {
        this.chefName = chefName;
        this.clientName = clientName;
        this.mealName = mealName;
        this.state = state;
    }

    public String toString() {
        return String.format("%s: by %s for %s - %s", mealName, chefName, clientName, state);
    }

    // Parcelable Things

    protected Order(Parcel in) {
        this.chefName = in.readString();
        this.clientName = in.readString();
        this.mealRef = db.document(in.readString());
        this.mealName = in.readString();
        this.state = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(chefName);
        parcel.writeString(clientName);
        parcel.writeString(mealRef.getPath());
        parcel.writeString(mealName);
        parcel.writeString(state);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
