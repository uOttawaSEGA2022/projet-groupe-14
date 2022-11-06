package ca.uottawa.engineering.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import ca.uottawa.engineering.mealer.classes.Admin;

public class AdminPage extends AppCompatActivity{
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Admin admin = new Admin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        listView = (ListView) findViewById(R.id.list);
        //initialiser le custombaseadaptor avec la list de complaint comme argument
        //listview.setAdapter(custombaseadaptor) pour configurer la list a afficher tous les complains dans l ordre
    }
}