package ca.uottawa.engineering.mealer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.uottawa.engineering.mealer.classes.Meal;

public class MealTests {

    @Test
    public void mealEqualsTrueTest() {
        Meal meal1 = new Meal("Name", "Cuisine Type", "Ingredients", "Allergies", "Cost", "Description", "Chef Name");
        Meal meal2 = new Meal("Name", "Cuisine Type", "Ingredients", "Allergies", "Cost", "Description", "Chef Name");

        assertEquals(true, meal1.equals(meal2));
    }

    @Test
    public void mealEqualsFalseTest() {
        Meal meal1 = new Meal("Name", "Cuisine Type", "Ingredients", "Allergies", "Cost", "Description", "Chef Name");
        Meal meal2 = new Meal("NAME2", "Cuisine Type", "Ingredients", "Allergies", "Cost", "Description", "Chef Name");

        assertEquals(false, meal1.equals(meal2));
    }

}
