package fi.jamk.vko_39_shopping_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jere on 1.10.2017.
 */

public class AddProductDialogFragment extends DialogFragment {

    public interface ProductDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog, String Name, int amount, float price);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    ProductDialogListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{

            mListener = (ProductDialogListener) activity;

        } catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "Must implement ProductDialogListener!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.add_product_dialog, null);
        builder.setView(dialogView);

        builder.setTitle("Add new product");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText product = (EditText) dialogView.findViewById(R.id.editText_name);
                String productName = product.getText().toString();

                EditText edit_amount = (EditText) dialogView.findViewById(R.id.editText_amount);
                int amount = Integer.parseInt(edit_amount.getText().toString());

                EditText edit_price = (EditText) dialogView.findViewById(R.id.editText_price);
                float price = Float.parseFloat(edit_price.getText().toString());

                mListener.onDialogPositiveClick(AddProductDialogFragment.this, productName, amount, price);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
}
