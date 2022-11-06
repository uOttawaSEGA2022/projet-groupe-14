package ca.uottawa.engineering.mealer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ca.uottawa.engineering.mealer.classes.Complaint;

public class CustomBaseAdaptor extends BaseAdapter {
    LayoutInflater listview;
    Context context;
    Complaint[] listcomplaints;
    public CustomBaseAdaptor(Context ctx,Complaint[] list){ //creer une list de view
        this.context = ctx;
        this.listcomplaints = list;
        listview = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return 0; //should return the number of Complaints
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
        view = listview.inflate(R.layout.activity_complaint_ui,null); // assigne view a le layout du complaint
        TextView chefname = (TextView) view.findViewById(R.id.NomDuChef); //retourne le texte view qui doit afficher le nom du chef
        return view;
    }
}
