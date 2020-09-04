package com.example.grocerylistapp.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerylistapp.R;

public class ListGroceryAdapter extends RecyclerView.Adapter<ListGroceryAdapter.GroceryViewHolder> {

    private Context context;
    private Cursor cursor;
    public ListGroceryAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView notesText;
        public TextView amountText;
        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name_item_textview);
            notesText = itemView.findViewById(R.id.notes_item_textview);
            amountText = itemView.findViewById(R.id.amout_item_textview);

        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item,parent,false);
    return new GroceryViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(Grocery.GroceryItem.COLUMN_NAME));
        String notes = cursor.getString(cursor.getColumnIndex(Grocery.GroceryItem.COLUMN_NOTES));
        int amount = cursor.getInt(cursor.getColumnIndex(Grocery.GroceryItem.COLUMN_AMOUNT));
        long id = cursor.getLong(cursor.getColumnIndex(Grocery.GroceryItem._ID));

        holder.nameText.setText(name);
        holder.notesText.setText(notes);
        holder.amountText.setText(String.valueOf(amount));
        holder.itemView.setTag(id);

    }



    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor cursor)
    {
        if(this.cursor != null)
        {this.cursor.close();}
        this.cursor = cursor;

        if(cursor != null)
        {notifyDataSetChanged();}
    }
}
