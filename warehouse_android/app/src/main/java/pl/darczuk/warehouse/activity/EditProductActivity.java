package pl.darczuk.warehouse.activity;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import pl.darczuk.warehouse.R;
import pl.darczuk.warehouse.activity.model.Product;
import pl.darczuk.warehouse.activity.view.ProductViewModel;

public class EditProductActivity extends AppCompatActivity {

    Product product;
    EditText editTextModelName;
    EditText editTextManufactureName;
    EditText editTextPrice;

    RestClient restClient;
    ProductViewModel mProductViewModel;

    public Activity getActivity(){
        return this;
    }

    private String getToken() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("warehouse", Context.MODE_PRIVATE);
        String defaultValue = "";
        String token = sharedPref.getString("Token", defaultValue);
        return token;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        restClient = new RestClient(this.getApplication());
        mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        product = getIntent().getParcelableExtra("Product");

        editTextModelName = findViewById(R.id.editTextModelName);
        editTextManufactureName = findViewById(R.id.editTextManufactureName);
        editTextPrice = findViewById(R.id.editTextPrice);

        if (product != null) {

            editTextModelName.setText(product.getModelName());
            editTextManufactureName.setText(product.getManufacturerName());
            editTextPrice.setText(product.getPrice().toString());
        }

        Button buttonEdit = findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = new Product();
                product.setModelName(editTextModelName.getText().toString());
                product.setManufacturerName(editTextManufactureName.getText().toString());
                product.setPrice(Double.valueOf(editTextPrice.getText().toString()));
                //HttpClient.saveProduct(product, getToken());
                //restClient.saveProduct(product);
                mProductViewModel.insert(product);

                finish();
            }
        });
    }
}
