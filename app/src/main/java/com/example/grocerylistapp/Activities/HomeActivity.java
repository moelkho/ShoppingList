package com.example.grocerylistapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grocerylistapp.DataBase.Grocery;
import com.example.grocerylistapp.DataBase.GroceryDBHelper;
import com.example.grocerylistapp.DataBase.ListGroceryAdapter;
import com.example.grocerylistapp.R;
public class HomeActivity extends AppCompatActivity {

private EditText editTextName;
private TextView textAmount;
private EditText editTextNotes;
private Button buttonIncrease;
private Button buttonDecrease;
private Button buttonAdd;
private int amount = 0 ;
private SQLiteDatabase sqLiteDatabase;
private ListGroceryAdapter listGroceryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    setWidgets();

    setListeners();

    }



    private void setWidgets() {
        GroceryDBHelper groceryDBHelper = new GroceryDBHelper(getApplicationContext());
        sqLiteDatabase = groceryDBHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listGroceryAdapter = new ListGroceryAdapter(this,getAllItems());
        recyclerView.setAdapter(listGroceryAdapter);

         new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
             @Override
             public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                 return false;
             }

             @Override
             public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
             }
         }).attachToRecyclerView(recyclerView);


        editTextName =findViewById(R.id.name_editText);
        textAmount = findViewById(R.id.amount);
        editTextNotes = findViewById(R.id.notes_editText);
        buttonIncrease = findViewById(R.id.btn_increase);
        buttonDecrease = findViewById(R.id.btn_decrease);
        buttonAdd = findViewById(R.id.btn_add);

    }

    private void removeItem(long tag) {
        sqLiteDatabase.delete(Grocery.GroceryItem.TABLE_NAME, Grocery.GroceryItem._ID + "=" + tag,null);
        listGroceryAdapter.swapCursor(getAllItems());
    }

    private void setListeners() {

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }

            
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGrocercyItem();
                
            }
        });
    }

    private void addGrocercyItem() {

        if (editTextName.getText().toString().trim().equals("") || amount ==0)
        {
            return;
        }

        String name = editTextName.getText().toString();
        String notes = editTextNotes.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Grocery.GroceryItem.COLUMN_NAME, name);
        contentValues.put(Grocery.GroceryItem.COLUMN_AMOUNT,  amount);
        contentValues.put(Grocery.GroceryItem.COLUMN_NOTES, notes);


        sqLiteDatabase.insert(Grocery.GroceryItem.TABLE_NAME,null,contentValues);
        listGroceryAdapter.swapCursor(getAllItems());
        clearFields();






    }

    private void clearFields() {

        editTextName.getText().clear();
        editTextNotes.getText().clear();



    }

    private void decrease() {

       if(amount>0){ amount--;
        textAmount.setText(String.valueOf(amount));}
    }

    private void increase() {
        amount++;
        textAmount.setText(String.valueOf(amount));
    }

    private Cursor getAllItems() {

        return sqLiteDatabase.query(Grocery.GroceryItem.TABLE_NAME,null,null,
                null,null,null,
                Grocery.GroceryItem.COLUMN_TIMESTAMP+ " DESC");
    }


}