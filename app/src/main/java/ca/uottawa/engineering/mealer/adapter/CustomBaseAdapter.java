package ca.uottawa.engineering.mealer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import ca.uottawa.engineering.mealer.R;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    public CustomBaseAdapter(Context ctx){
        this.context = ctx;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return 5;
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
        return view;
    }
}
