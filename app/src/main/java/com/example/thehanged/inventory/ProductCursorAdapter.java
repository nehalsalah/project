
package com.example.thehanged.inventory;

import android.content.ContentUris;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.thehanged.inventory.data.ProductContract.productEntry;

/**
 * {@link ProductCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of Product data in the {@link Cursor}.
 */
public class ProductCursorAdapter extends CursorAdapter {
    private Context mContext;

    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the Product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current Product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mContext = context;

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        ImageView ImageViewProductImage = (ImageView) view.findViewById(R.id.imageView);

        // Find the columns of Product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(productEntry.COLUMN_product_NAME);
        int priceColumnIndex = cursor.getColumnIndex(productEntry.COLUMN_product_price);
        int quantityColumnIndex = cursor.getColumnIndex(productEntry.COLUMN_product_Quantity);

        // Read the Product attributes from the Cursor for the current Product
        final String Name = cursor.getString(nameColumnIndex);
        final int price = cursor.getInt(priceColumnIndex);
        final int Quantity = cursor.getInt(quantityColumnIndex);
        final String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(productEntry.COLUMN_product_image));
        // Update the TextViews with the attributes for the current Product
        nameTextView.setText(Name);
        priceTextView.setText(Integer.toString(price));
        quantityTextView.setText(Integer.toString(Quantity));
        if (imagePath != null) {
            ImageViewProductImage.setVisibility(View.VISIBLE);
            ImageViewProductImage.setImageURI(Uri.parse(imagePath));
        } else {
            ImageViewProductImage.setVisibility(View.GONE);
        }


        Button sellButton = (Button) view.findViewById(R.id.saleButton);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null) {
                    Object obj = view.getTag();
                    String st = obj.toString();
                    ContentValues values = new ContentValues();
                    values.put(productEntry.COLUMN_product_NAME, Name);
                    values.put(productEntry.COLUMN_product_Quantity, Quantity >= 1 ? Quantity - 1 : 0);
                    values.put(productEntry.COLUMN_product_price, price);
                    values.put(productEntry.COLUMN_product_image, imagePath);
                    Uri currentPetUri = ContentUris.withAppendedId(productEntry.CONTENT_URI, Integer.parseInt(st));

                    int rowsAffected = mContext.getContentResolver().update(currentPetUri, values, null, null);
                    if (rowsAffected == 0 || Quantity == 0) {
                        Toast.makeText(mContext, mContext.getString(R.string.sell), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Object obj = cursor.getInt(cursor.getColumnIndex(productEntry._ID));
        sellButton.setTag(obj);
    }


}



