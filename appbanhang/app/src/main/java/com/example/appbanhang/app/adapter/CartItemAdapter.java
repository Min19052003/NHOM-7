package com.example.appbanhang.app.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appbanhang.app.models.CartItem;
import com.example.appbanhang.R;

import java.util.List;

public class CartItemAdapter extends ArrayAdapter<CartItem> {

    private Context mContext;
    private int mResource;
    private SparseArray<Integer> productIdQuantityMap;

    public CartItemAdapter(@NonNull Context context, int resource, @NonNull List<CartItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        productIdQuantityMap = new SparseArray<>();
        mergeItems(objects);
    }

    private void mergeItems(List<CartItem> items) {
        for (CartItem item : items) {
            int productId = item.getProductId();
            int quantity = item.getQuantity();
            if (productIdQuantityMap.get(productId) != null) {
                quantity += productIdQuantityMap.get(productId);
            }
            productIdQuantityMap.put(productId, quantity);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, parent, false);
        }

        int productId = productIdQuantityMap.keyAt(position);
        int quantity = productIdQuantityMap.valueAt(position);

        TextView textViewProductName = view.findViewById(R.id.textViewProductName);
        TextView textViewQuantity = view.findViewById(R.id.textViewQuantity);

        textViewProductName.setText(String.valueOf(productId));
        textViewQuantity.setText(String.valueOf(quantity));

        return view;
    }
}
