package fi.jamk.vko_39_shopping_list;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements AddProductDialogFragment.ProductDialogListener {

    ListView shopListView;
    SQLiteDatabase db;
    private final int DELETE_ID = 0;
    Cursor cursor;
    private String DATABASE_TABLE = "products";
    TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalPrice = (TextView) findViewById(R.id.totalPrice);

        shopListView = (ListView) findViewById(R.id.productListView);

        registerForContextMenu(shopListView);

        db = (new DatabaseOpenHelper(this)).getWritableDatabase();

        queryData();
    }

    public void queryData() {
        String[] resultColumns = new String[]{"_id, name, amount, price"};
        cursor = db.query(DATABASE_TABLE, resultColumns, null, null, null, null, "price DESC", null);
        float total = 0;

        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(this, cursor);

        shopListView.setAdapter(shoppingListAdapter);

        cursor = db.rawQuery("SELECT amount, price FROM products", null);

        if (cursor.moveToFirst()) {
            do {
                total += cursor.getFloat(0) * cursor.getFloat(1);
            } while (cursor.moveToNext());
        }


        totalPrice.setText("Total price of all products: " + String.valueOf(total) + " €");
    }

    public void addProduct(View view){
        AddProductDialogFragment addProductDialogFragment = new AddProductDialogFragment();

        addProductDialogFragment.show(getFragmentManager(), "Add");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String Name, int amount, float price) {
        ContentValues values = new ContentValues(2);

        values.put("name", Name);
        values.put("amount", amount);
        values.put("price", price);

        db.insert(DATABASE_TABLE, null, values);

        queryData();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                String[] args = {String.valueOf(info.id)};
                db.delete("products", "_id=?", args);
                queryData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // close cursor and db connection
        cursor.close();
        db.close();
    }
}
