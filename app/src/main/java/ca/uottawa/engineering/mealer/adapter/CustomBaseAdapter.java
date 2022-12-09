package ca.uottawa.engineering.mealer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.uottawa.engineering.mealer.R;
import ca.uottawa.engineering.mealer.classes.Meal;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Meal> listmeal;
    public CustomBaseAdapter(Context ctx, ArrayList<Meal> mealList){
        this.listmeal = mealList;
        this.context = ctx;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return listmeal.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_meal_list_ui,null);
        TextView mealname = (TextView) view.findViewById(R.id.mealName);
        mealname.setText(listmeal.get(i).getName());
        return view;
    }
}
