package com.example.appbanhang.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.example.appbanhang.app.adapter.CartItemAdapter;
import com.example.appbanhang.app.models.CartItem;
import com.example.appbanhang.service.ApiService;
import com.example.appbanhang.service.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private ListView listViewCartItems;
    private CartItemAdapter adapter;
    private List<CartItem> cartItemList;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCartItems = findViewById(R.id.list_cart_items);
        cartItemList = new ArrayList<>();
        adapter = new CartItemAdapter(CartActivity.this, R.layout.cart_item_layout, cartItemList);
        listViewCartItems.setAdapter(adapter);

        bottomNav = findViewById(R.id.bottom_nav);

        // Chọn mặc định mục Home khi mở ứng dụng
        bottomNav.setSelectedItemId(R.id.menu_cart);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Kiểm tra ID của MenuItem được chọn và thực thi hành động tương ứng
                if (item.getItemId() == R.id.menu_home) {
                    Intent homeIntent = new Intent(CartActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_search) {
                    Intent cartIntent = new Intent(CartActivity.this, SearchActivity.class);
                    startActivity(cartIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_cart) {
                    return true;
                } else if (item.getItemId() == R.id.menu_logout) {
                    Intent cartIntent = new Intent(CartActivity.this, LoginActivity.class);
                    startActivity(cartIntent);
                }
                return false;
            }
        });
        getCartItemsAndUpdateListView();

        // Xử lý sự kiện xóa mục khi người dùng giữ một mục trong danh sách
        listViewCartItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // Hiển thị cảnh báo hoặc xác nhận xóa mục tại vị trí position
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setMessage("Are you sure you want to remove this item from the cart?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Gọi phương thức để xóa mục từ giỏ hàng
                                removeCartItem(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();

                // Trả về true để không kích hoạt sự kiện click bình thường
                return true;
            }
        });
    }

    private void getCartItemsAndUpdateListView() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<CartItem>> call = apiService.getCartItems();
        call.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cartItemList.clear();
                    cartItemList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(CartActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức để xóa một mục từ giỏ hàng
    private void removeCartItem(final int position) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Lấy ra CartItem cần xóa từ danh sách cartItemList
        CartItem cartItemToRemove = cartItemList.get(position);

        // Gọi API để xóa mục từ giỏ hàng dựa trên id của mục
        Call<Void> call = apiService.removeCartItem(cartItemToRemove.getProductId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xóa mục thành công từ danh sách cartItemList
                    cartItemList.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(CartActivity.this, "Item removed from cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "Failed to remove item from cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
