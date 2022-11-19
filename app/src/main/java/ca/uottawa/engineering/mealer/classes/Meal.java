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
    private String chefName;

    public Meal() {}

    public Meal(String name, String cuisineType, String ingredients, String allergies, String cost, String desc, String chefname) {
        this.name = name;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.ingredients = ingredients;
        this.allergies = allergies;
        this.cost = cost;
        this.desc = desc;
        this.chefName= chefname;
    }

    public String toString() {
        return String.format("%s: %s", name, chefName);
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
        this.chefName = in.readString();
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
        parcel.writeString(chefName);
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Meal meal) {
        if (this.name.equals(meal.getName()) && this.chefName.equals(meal.getChefName()) && this.ingredients.equals(meal.getIngredients())){
            return true;
        } else {
            return false;
        }
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

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }
}
