package com.e.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView  list ;
    static ArrayList<String> names = new ArrayList<>();
    static ArrayAdapter adapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.e.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>)  sharedPreferences.getStringSet("notes",null);
        if (set == null){
            names.add("Add a note ");
        }else{
            names = new ArrayList(set);
        }
        adapter = new ArrayAdapter(this,android.R.layout.simple_selectable_list_item,names);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete entry")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                names.remove(position);
                                adapter.notifyDataSetChanged();
                                HashSet<String> set= new HashSet< >(MainActivity.names);
                                sharedPreferences.edit().putStringSet("notes",set).apply();

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            return true;}
        });
    }
    //  creating menu item

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar,menu);

         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.add:
                Intent intent= new Intent(this,TextWrite.class);
                //intent.putExtra("value",position);
                startActivity(intent);
                break;
        }
    return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent= new Intent(this,TextWrite.class);
        intent.putExtra("value",position);
        startActivity(intent);


    }
}
