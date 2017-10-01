package fi.jamk.vko_39_shopping_list;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.IntegerRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Jere on 1.10.2017.
 */

public class ShoppingListAdapter extends CursorAdapter {

    public ShoppingListAdapter(Context context, Cursor c) {
        super(context, c, 0);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.shopping_list_adapter, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView amount = (TextView) view.findViewById(R.id.amount);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView totalPrice = (TextView) view.findViewById(R.id.totalPrice);

        String resultName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        name.setText(resultName);

        int resultAmount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"));
        amount.setText(String.valueOf(resultAmount));

        float resultPrice = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
        price.setText(String.valueOf(resultPrice) + " €");

        totalPrice.setText(String.valueOf(resultPrice*resultAmount) + " €");
    }

}
