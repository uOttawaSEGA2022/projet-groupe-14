package ca.uottawa.engineering.mealer.classes;

public class Meal {

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
