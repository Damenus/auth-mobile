package pl.darczuk.warehouse.activity;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import pl.darczuk.warehouse.R;
import pl.darczuk.warehouse.activity.model.Product;
import pl.darczuk.warehouse.activity.view.ProductViewModel;

public class ProductDetailsActivity extends AppCompatActivity {

    Product product;
    EditText editTextNumber;
    TextView textView19;

    RestClient restClient;
    ProductViewModel productViewModel;


    public Activity getActivity(){
        return this;
    }

    private String getRole() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("warehouse", Context.MODE_PRIVATE);
        String defaultValue = "";
        String role = sharedPref.getString("Role", defaultValue);
        return role;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        restClient = new RestClient(this.getApplication());//ViewModelProviders.of(this).get(RestClient.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (getRole().equals("MENAGER")) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Deleted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //HttpClient.deleteProduct(product, getToken());
                    //restClient.deleteProduct(product);
                    productViewModel.delete(product);
                    finish();
                }
            });
        } else {
            fab.setVisibility(View.GONE);
        }



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Product product2 = (Product) bundle.getSerializable("Product");
        product = productViewModel.getProductById(product2.getId());

        TextView textView10 = findViewById(R.id.textView10);
        textView10.setText("ID");
        TextView textView11 = findViewById(R.id.textView11);
        textView11.setText(product.getId().toString());

        TextView textView12 = findViewById(R.id.textView12);
        textView12.setText("modelName");
        TextView textView13 = findViewById(R.id.textView13);
        textView13.setText(product.getModelName());

        TextView textView14 = findViewById(R.id.textView14);
        textView14.setText("manufacturerName");
        TextView textView15 = findViewById(R.id.textView15);
        textView15.setText(product.getManufacturerName());

        TextView textView16 = findViewById(R.id.textView16);
        textView16.setText("price");
        TextView textView17 = findViewById(R.id.textView17);
        textView17.setText(product.getPrice().toString());

        TextView textView18 = findViewById(R.id.textView18);
        textView18.setText("quantity");
        textView19 = findViewById(R.id.textView19);
        if(product.getProductsBundle().equals("")) {
            textView19.setText(String.valueOf(product.getQuantity()));
        } else {
            int minimum_quantity = Integer.MAX_VALUE;

            String list = product.getProductsBundle();
            List<String> idProductsOfBundle = Arrays.asList(
                    list
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" ", "")
                            .split(","));

            Product productFromBundle;
            for(int i =0; i < idProductsOfBundle.size(); i++) {
                productFromBundle = productViewModel.getProductById(Long.decode(idProductsOfBundle.get(i)));

                if(minimum_quantity>productFromBundle.getQuantity()) {
                    minimum_quantity = productFromBundle.getQuantity();
                }
            }

            textView19.setText(String.valueOf(minimum_quantity));
        }

        TextView textView20 = findViewById(R.id.textView20);
        textView20.setText("size");
        TextView textView21 = findViewById(R.id.textView21);
        textView21.setText(String.valueOf(product.getSize()));

        editTextNumber = findViewById(R.id.editTextNumber);

        Button buttonDecrease = findViewById(R.id.buttonDecrease);
        Button buttonIncrease = findViewById(R.id.buttonIncrease);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);


        if(!product.getProductsBundle().equals("")) {
            LinearLayout bundleLayout = findViewById(R.id.bundleLayout);
            bundleLayout.setOrientation(LinearLayout.VERTICAL);

            TextView textView = new TextView(this);
            textView.setText("List of products from bundle:");
            bundleLayout.addView(textView);

            String list = product.getProductsBundle();
            List<String> idProductsOfBundle = Arrays.asList(
                    list
                            .replace("[", "")
                            .replace("]", "")
                            .replace(" ", "")
                            .split(","));

            Product productFromBundle;
            for(int i =0; i < idProductsOfBundle.size(); i++) {
                productFromBundle = productViewModel.getProductById(Long.decode(idProductsOfBundle.get(i)));
                TextView descrptionOfProductFromBundle = new TextView(this);
                descrptionOfProductFromBundle.setText(
                        productFromBundle.getId() + " " +
                        productFromBundle.getManufacturerName() + " " +
                        productFromBundle.getModelName() + " "
                );
                bundleLayout.addView(descrptionOfProductFromBundle);
            }


        }

        buttonMinus.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String old_val = editTextNumber.getText().toString();
                int int_val = Integer.parseInt(old_val);
                if (int_val > 0)
                    int_val = int_val - 1;
                editTextNumber.setText(String.valueOf(int_val));
            }
        });

        buttonPlus.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String old_val = editTextNumber.getText().toString();
                int int_val = Integer.parseInt(old_val);
                int_val = int_val + 1;
                editTextNumber.setText(String.valueOf(int_val));
            }
        });

        buttonIncrease.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //product = restClient.increaseProduct(product, editTextNumber.getText().toString());

                textView19 = findViewById(R.id.textView19);

                if(product.getProductsBundle().equals("")) {
                    productViewModel.increasing(product, Integer.decode(editTextNumber.getText().toString()));
                    product = productViewModel.getProductById(product.getId());
                    textView19.setText(String.valueOf(product.getQuantity()));
                } else {
                    productViewModel.increasing(product, Integer.decode(editTextNumber.getText().toString()));
                    product = productViewModel.getProductById(product.getId());
                    int minimum_quantity = Integer.MAX_VALUE;

                    String list = product.getProductsBundle();
                    List<String> idProductsOfBundle = Arrays.asList(
                            list
                                    .replace("[", "")
                                    .replace("]", "")
                                    .replace(" ", "")
                                    .split(","));

                    Product productFromBundle;
                    for(int i =0; i < idProductsOfBundle.size(); i++) {
                        productFromBundle = productViewModel.getProductById(Long.decode(idProductsOfBundle.get(i)));
                        productViewModel.increasing(productFromBundle, Integer.decode(editTextNumber.getText().toString()));

                        if(minimum_quantity>productFromBundle.getQuantity()) {
                            minimum_quantity = productFromBundle.getQuantity();
                        }
                    }

                    textView19.setText(String.valueOf(minimum_quantity));
                }

                editTextNumber.setText(String.valueOf(0));

            }
        });

        buttonDecrease.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                textView19 = findViewById(R.id.textView19);

                if(product.getProductsBundle().equals("")) {
                    productViewModel.decreasing(product, Integer.decode(editTextNumber.getText().toString()));
                    product = productViewModel.getProductById(product.getId());
                    textView19.setText(String.valueOf(product.getQuantity()));
                } else {
                    productViewModel.decreasing(product, Integer.decode(editTextNumber.getText().toString()));
                    product = productViewModel.getProductById(product.getId());
                    int minimum_quantity = Integer.MAX_VALUE;

                    String list = product.getProductsBundle();
                    List<String> idProductsOfBundle = Arrays.asList(
                            list
                                    .replace("[", "")
                                    .replace("]", "")
                                    .replace(" ", "")
                                    .split(","));

                    Product productFromBundle;
                    for(int i =0; i < idProductsOfBundle.size(); i++) {
                        productFromBundle = productViewModel.getProductById(Long.decode(idProductsOfBundle.get(i)));
                        productViewModel.decreasing(productFromBundle, Integer.decode(editTextNumber.getText().toString()));

                        if(minimum_quantity>productFromBundle.getQuantity()) {
                            minimum_quantity = productFromBundle.getQuantity();
                        }
                    }

                    textView19.setText(String.valueOf(minimum_quantity));
                }

                editTextNumber.setText(String.valueOf(0));

            }
        });

    }

}
