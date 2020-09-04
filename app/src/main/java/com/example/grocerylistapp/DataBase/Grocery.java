package com.example.grocerylistapp.DataBase;

import android.provider.BaseColumns;

public class Grocery {

    public static final class GroceryItem implements BaseColumns {

        private GroceryItem(){}

        public static final String TABLE_NAME = "groceryList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AMOUNT ="amount";
        public static final String COLUMN_NOTES ="notes";
        public static final String COLUMN_TIMESTAMP ="timestamp";

    }
}
