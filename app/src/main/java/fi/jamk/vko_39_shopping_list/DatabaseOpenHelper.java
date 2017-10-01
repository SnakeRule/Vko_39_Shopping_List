package fi.jamk.vko_39_shopping_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jere on 1.10.2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ShopList_database";
    private final String DATABASE_TABLE = "products";
    private final String NAME = "name";
    private final String AMOUNT = "amount";
    private final String PRICE = "price";

    public DatabaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+DATABASE_TABLE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +NAME+ " TEXT, " +AMOUNT+" INTEGER, "+PRICE+" REAL);");

        // Create some sample data
        ContentValues values = new ContentValues();
        values.put(NAME, "Milk");
        values.put(AMOUNT, 2);
        values.put(PRICE, 0.9);

        // Insert data to db
        db.insert(DATABASE_TABLE, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
