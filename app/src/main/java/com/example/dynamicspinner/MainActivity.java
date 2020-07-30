package com.example.dynamicspinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etItemName;
    Button btnAddItem, btnRemItem;
    Spinner spinner;

    ArrayList<String> itemNames = new ArrayList<>();
    int selectedItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding
        etItemName = findViewById(R.id.editTextItemName);
        btnAddItem = findViewById(R.id.buttonAddItem);
        btnRemItem = findViewById(R.id.buttonRemoveItem);
        spinner = findViewById(R.id.spinner);

        //add label item (disable select)
        itemNames.add("Select Item Name");

        //Array Adapter
        //android.R.layout.simple_spinner_dropdown_item
        final ArrayAdapter<String> spinnerAd = new ArrayAdapter<String>(this,
                R.layout.spinner_item, itemNames) {
            //set which item to disable select
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                return true;
            }
            //set item color
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0)
                    tv.setTextColor(Color.parseColor("#D3D3D3"));
                else
                    tv.setTextColor(Color.parseColor("#000000"));
                return view;
            }
        };

        //bridging
        spinner.setAdapter(spinnerAd);

        //click item on spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                selectedItem = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //click add item
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = etItemName.getText().toString();
                itemNames.add(itemName);
                etItemName.getText().clear();
                spinnerAd.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_SHORT).show();
            }
        });

        //click remove item
        btnRemItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemNames.size() > 1) {
                    itemNames.remove(selectedItem);
                    spinnerAd.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "No more items to remove", Toast.LENGTH_SHORT).show();
            }
        });


    }


}