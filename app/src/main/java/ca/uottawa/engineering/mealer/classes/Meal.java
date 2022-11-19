package ca.uottawa.engineering.mealer.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Meal implements Parcelable {

    private String name;
    private String mealType;
    private String cuisineType;
    private String ingredients;
    private String allergies;
    private String cost;
    private String desc;

    public Meal() {}

    public Meal(String name, String cuisineType, String ingredients, String allergies, String cost, String desc) {
        this.name = name;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.ingredients = ingredients;
        this.allergies = allergies;
        this.cost = cost;
        this.desc = desc;
    }

    public String toString() {
        return String.format("%s", name);
    }

    // Parcelable Things

    protected Meal(Parcel in) {
        this.name = in.readString();
        this.mealType = in.readString();
        this.cuisineType = in.readString();
        this.ingredients = in.readString();
        this.allergies = in.readString();
        this.cost = in.readString();
        this.desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(mealType);
        parcel.writeString(cuisineType);
        parcel.writeString(ingredients);
        parcel.writeString(allergies);
        parcel.writeString(cost);
        parcel.writeString(desc);
    }

    public static final Creator<Complaint> CREATOR = new Creator<Complaint>() {
        @Override
        public Complaint createFromParcel(Parcel in) {
            return new Complaint(in);
        }

        @Override
        public Complaint[] newArray(int size) {
            return new Complaint[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}