package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ca.uottawa.engineering.mealer.classes.Meal;

public class MealUi extends AppCompatActivity {

    Meal meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_ui);

        Bundle extras = getIntent().getExtras();
        meal = (Meal) extras.get("meal");

        TextView mealName = (TextView) findViewById(R.id.nomDuMeal);
        mealName.setText(meal.getName());
    }
}