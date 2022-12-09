package ca.uottawa.engineering.mealer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import ca.uottawa.engineering.mealer.R;
import ca.uottawa.engineering.mealer.classes.Meal;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Meal> listmeal;
    ArrayList<Meal> searchlist;
    boolean isdoable=true;

    public CustomBaseAdapter(Context ctx, ArrayList<Meal> mealList) {
        this.listmeal = mealList;
        this.searchlist = new ArrayList<Meal>();
        this.context = ctx;
        inflater = LayoutInflater.from(ctx);
        Log.d("Clientpage", String.valueOf(searchlist.size()));
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
        view = inflater.inflate(R.layout.activity_meal_list_ui, null);
        TextView mealname = (TextView) view.findViewById(R.id.mealName);
        mealname.setText(listmeal.get(i).getName());
        return view;
    }

    public void filter(String text) {
        if (isdoable==true){
            searchlist.addAll(listmeal);
            isdoable = false;
        }
        listmeal.clear();
        if (text.equals("")) {
            listmeal.addAll(searchlist);
        }
        else {
            for (Meal meal : searchlist) {
                if (meal.getName().equals(text)) {
                    listmeal.clear();
                    listmeal.add(meal);
                }
            }

        }
        notifyDataSetChanged();
    }
}