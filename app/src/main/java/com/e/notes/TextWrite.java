package com.e.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class TextWrite extends AppCompatActivity {
    EditText  editText ;
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_write);
        editText = (EditText)  findViewById(R.id.write);
      /*  SharedPreferences sharedPreferences = this.getSharedPreferences("com.e.notes", Context.MODE_PRIVATE);
       sharedPreferences.edit().putString("name",editText.getText().toString()).apply();
        String s = sharedPreferences.getString("name","");
     */
        Intent intent = getIntent();
       page =intent.getIntExtra("value",-1);
        if(page != -1){
            editText.setText(MainActivity.names.get(page));
        }else{
            MainActivity.names.add("");
            page = MainActivity.names.size() -1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.names.set(page,String.valueOf(s));
                MainActivity.adapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.e.notes",Context.MODE_PRIVATE);
                HashSet <String> set= new HashSet< >(MainActivity.names);
                sharedPreferences.edit().putStringSet("notes",set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





    }
}
