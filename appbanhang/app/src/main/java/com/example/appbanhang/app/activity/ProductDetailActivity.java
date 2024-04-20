package com.example.appbanhang.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.example.appbanhang.app.models.CartItem;
import com.example.appbanhang.app.models.Product;
import com.example.appbanhang.service.ApiService;
import com.example.appbanhang.service.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView tvProductName, tvProductDescription, tvProductPrice;
    private ImageView ivProductImage;
    private Spinner spinnerQuantity;
    private ImageButton btnBack;

    private Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Initialize views
        tvProductName = findViewById(R.id.txt_tensanpham);
        tvProductDescription = findViewById(R.id.txtmotachitiet);
        tvProductPrice = findViewById(R.id.txt_giasanpham);
        ivProductImage = findViewById(R.id.img_chitiet);
        spinnerQuantity = findViewById(R.id.spiner);
        btnBack = findViewById(R.id.btn_back);
        btnAddToCart = findViewById(R.id.btn_themgiohang);

        // Get product ID from intent
        int productId = getIntent().getIntExtra("productId", -1);

        // Set up spinner for selecting quantity
        setupQuantitySpinner();

        // Call API to get product details
        getProductDetails(productId);

        // Set up onClickListener for back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to HomeActivity
                Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                // Finish the current activity
                finish();
            }
        });

        // Set up onClickListener for add to cart button
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected quantity
                int quantity = Integer.parseInt(spinnerQuantity.getSelectedItem().toString());
                // Add the product to cart
                addToCart(productId, quantity);
            }
        });
    }

    private void setupQuantitySpinner() {
        // Initialize an array of quantity values
        String[] quantityArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        // Create an adapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuantity.setAdapter(adapter);
    }

    private void getProductDetails(int productId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Product> call = apiService.getProductById(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    if (product != null) {
                        displayProductDetails(product);
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Failed to fetch product details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProductDetails(Product product) {
        // Display product details on the UI
        tvProductName.setText(product.getName());
        tvProductDescription.setText(product.getDescription());
        tvProductPrice.setText("Giá: " + product.getPrice() + " VND");
        Picasso.get().load(product.getImage()).into(ivProductImage);
    }

    private void addToCart(int productId, int quantity) {


        CartItem cartItem = new CartItem(productId, quantity);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.addToCart(cartItem);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    System.out.println("Thêm thành công");
                    Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                    startActivity(intent);
                } else {
                    System.out.println("Lỗi");
                    Toast.makeText(ProductDetailActivity.this, "Lỗi khi thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Lỗi kết nối");
                Toast.makeText(ProductDetailActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}