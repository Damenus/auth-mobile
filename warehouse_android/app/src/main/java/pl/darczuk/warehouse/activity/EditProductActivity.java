package pl.darczuk.warehouse.activity;

import android.content.Intent;
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

public class EditProductActivity extends AppCompatActivity {

    Product product;
    EditText editTextModelName;
    EditText editTextManufactureName;
    EditText editTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

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
                HttpClient.saveProduct(product);

//                RestClient client=new RestClient(Properties.getInstance().WAREHOUSE_URL +
//                        Properties.getInstance().WAREHOUSE_API +
//                        Properties.getInstance().WAREHOUSE_PRODUCT);
//
//                try {
//                    JSONObject obj = new JSONObject();
//                    obj.put("modelName", product.getModelName());
//                    obj.put("manufacturerName", product.getManufacturerName());
//                    obj.put("price", product.getPrice().toString());
//
//                    //client.AddParam("", obj.toString());
//                    client.AddParam("modelName", product.getModelName());
//                    client.AddParam("manufacturerName", product.getManufacturerName());
//                    client.AddParam("price", product.getPrice().toString());
//
//                    client.Execute(RestClient.RequestMethod.POST);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//                String response=client.getResponse();

                finish();
            }
        });
    }
}
