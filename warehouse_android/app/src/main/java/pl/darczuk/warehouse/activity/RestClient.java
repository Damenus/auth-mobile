package pl.darczuk.warehouse.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import pl.darczuk.warehouse.activity.dao.ProductDao;
import pl.darczuk.warehouse.activity.model.Product;
import pl.darczuk.warehouse.activity.model.ProductDTO;
import pl.darczuk.warehouse.activity.util.Properties;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class RestClient {

    private String token;
    private String role;
    private String uuidApp;
    private OkHttpClient client;
    private String url;


    public RestClient(@NonNull Application application) {
        token = application
                .getSharedPreferences("warehouse", Context.MODE_PRIVATE)
                .getString("Token", "");
        role = application
                .getSharedPreferences("warehouse", Context.MODE_PRIVATE)
                .getString("Role", "");
        client = new OkHttpClient();
        url = Properties.getInstance().WAREHOUSE_URL + Properties.getInstance().WAREHOUSE_API;
        uuidApp = application
                .getSharedPreferences("warehouse", Context.MODE_PRIVATE)
                .getString("UUIDApp", "");
    }

    //<?xml version="1.0" encoding="UTF-8"?>
    //<sync>
    //<uuidApp>ddarczuk+ojbho12u3oob</uuidApp>
    //<productList>
    //  <product modelName="" ...>1</product>
    //</productList>
    //</sync>

    private List<Product> parseXMLtoProducts(String xml) {
        List<Product> products = new ArrayList<>();
//        try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            factory.setNamespaceAware(true);
//
//            XmlPullParser xpp = factory.newPullParser();
//
//            xpp.setInput(new StringReader(xml));
//
//
//            int eventType = xpp.getEventType();
//            Product currentProduct = null;
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                String eltName = null;
//
//                switch (eventType) {
//                    case XmlPullParser.START_TAG:
//                        eltName = xpp.getName();
//
//                        if ("player".equals(eltName)) {
//                            currentProduct = new Product();
//                            products.add(currentProduct);
//                            xpp.getAttributeValue()
//                        } else if (currentProduct != null) {
//                            if ("name".equals(eltName)) {
//                                currentProduct.name = xpp.nextText();
//                            } else if ("age".equals(eltName)) {
//                                currentProduct.age = xpp.nextText();
//                            } else if ("position".equals(eltName)) {
//                                currentProduct.position = xpp.nextText();
//                            }
//                        }
//                        break;
//                }
//
//                eventType = xpp.next();
//            }
//
//
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return  products;
    }

    private String parseProductstoXML(List<Product> products, String uuidApp) {
        String xml = "";

        xml += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml += "<sync>";
        xml += "<uuidApp>" + uuidApp + "</uuidApp>";
        xml += "<productList>";
        for (Product product:products) {
            xml += "<product modelName=\"" + product.getModelName()
                    + "\" manufacturerName=\"" + product.getManufacturerName()
                    + "\" price=\"" + product.getPrice()
                    + "\" LocalDeltaChangeQuantity=\"" + product.getLocalDeltaChangeQuantity()
                    + "\" >" + product.getId() + "</product>";
        }
        xml += "</productList>";
        xml += "</sync>";

        return xml;
    }

    private Product productFromJson(String json) {

        Product product = null;
        JSONObject productJson = null;
        try {
            productJson = new JSONObject(json);
            product = new Product(
                    Long.decode(productJson.getString("id")),
                    productJson.getString("modelName"),
                    productJson.getString("manufacturerName"),
                    productJson.getDouble("price"),
                    productJson.getInt("quantity")
            );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return product;
    }

    public List<Product> getAllProducts() {

        String stringAllProducts = "";
        try {
            stringAllProducts = new RestClientAsyncTask(client,"/product", this.token, RequestMethodEnum.GET)
                    .execute()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JSONArray jsonProducts = null;
        try {
            jsonProducts = new JSONArray(stringAllProducts);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Product> products= new ArrayList<Product>();
        if (jsonProducts != null) {
            for (int i=0;i<jsonProducts.length();i++){
                try {
                    products.add(
                            new Product(
                                    Long.decode(jsonProducts.getJSONObject(i).getString("id")),
                                    jsonProducts.getJSONObject(i).getString("modelName"),
                                    jsonProducts.getJSONObject(i).getString("manufacturerName"),
                                    jsonProducts.getJSONObject(i).getDouble("price"),
                                    jsonProducts.getJSONObject(i).getInt("quantity")
                            )
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return products;
    }

    String buildBodyInsert(String manufacturerName, String modelName, String price) {

        String json = "";

        try {
            json = new JSONObject()
                    .put("modelName", modelName)
                    .put("manufacturerName", manufacturerName)
                    .put("price", price)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public void saveProduct(Product product) {

        String stringAllProducts = "";
        try {
            stringAllProducts = new RestClientAsyncTask(client,"/product", this.token, RequestMethodEnum.POST)
                    .execute(buildBodyInsert(product.getManufacturerName(), product.getModelName(), String.valueOf(product.getPrice())))
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Product product) {

        String stringAllProducts = "";
        try {
            stringAllProducts = new RestClientAsyncTask(client,"/product/"+product.getId(), this.token, RequestMethodEnum.DELETE)
                    .execute()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public Product increaseProduct(Product product, String quantity) {

        String response = "";
        try {
            response = new RestClientAsyncTask(
                    client,
                    "/product/" + product.getId() + "/increase/" + quantity,
                    this.token,
                    RequestMethodEnum.GET
            )
                    .execute()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return productFromJson(response);
    }

    public Product decreaseProduct(Product product, String quantity) {

        String response = "";
        try {
            response = new RestClientAsyncTask(
                    client,
                    "/product/" + product.getId() + "/decrease/" + quantity,
                    this.token,
                    RequestMethodEnum.GET
            )
                    .execute()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return productFromJson(response);

    }

    String buildBodySync(List<Product> products, String id) {
// {
//  "user+uuid": user+sgaasd123asdg,
//  "products": ["id"="1", "localDeltaChangeQuantity"=123],...
//
//  }

        JSONObject json = new JSONObject();
        JSONArray list = new JSONArray();

        if(products != null) {
            for (Product product : products) {
                try {
                    list.put(
                            new JSONObject()
                                    .put("id", product.getId())
                                    .put("localDeltaChangeQuantity", product.getLocalDeltaChangeQuantity())
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            json.put("user+uuid", id);
            json.put("products", list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    String buildListDTOSync(List<Product> products) {
// {
//
//  ["id"="1", "localDeltaChangeQuantity"=123],...
//
//  }

        JSONArray list = new JSONArray();

        if(products != null) {
            for (Product product : products) {
                try {
                    list.put(
                            new JSONObject()
                                    .put("id", product.getId())
                                    .put("modelName", product.getModelName())
                                    .put("manufacturerName", product.getManufacturerName())
                                    .put("price", product.getPrice())
                                    .put("quantity", product.getLocalDeltaChangeQuantity())
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



        return list.toString();
    }

    public List<ProductDTO> sync(List<Product> products) {

        String response = "";
        try {
            response = new RestClientAsyncTask(
                    client,
                    "/sync",
                    this.token,
                    RequestMethodEnum.POST
            )
                    .execute(buildListDTOSync(products))
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //json -> dto
        JSONArray jsonProducts = null;
        try {
            jsonProducts = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<ProductDTO> syncProducts = new ArrayList<>();

        if (jsonProducts != null) {
            for (int i=0;i<jsonProducts.length();i++){
                try {
                    syncProducts.add(
                            new ProductDTO(
                                    Long.decode(jsonProducts.getJSONObject(i).getString("id")),
                                    jsonProducts.getJSONObject(i).getString("modelName"),
                                    jsonProducts.getJSONObject(i).getString("manufacturerName"),
                                    jsonProducts.getJSONObject(i).getDouble("price"),
                                    jsonProducts.getJSONObject(i).getInt("quantity")
                            )
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //dto -> products


        return syncProducts;

    }

    enum RequestMethodEnum {
        GET, POST, DELETE
    }

    public static class RestClientAsyncTask extends AsyncTask<String, Void, String> {

        static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client;
        String url;
        String token;
        RequestMethodEnum requestMethod;

        public RestClientAsyncTask(OkHttpClient client, String url, String token, RequestMethodEnum requestMethod) {
            this.client = client;
            this.url = Properties.getInstance().WAREHOUSE_URL + Properties.getInstance().WAREHOUSE_API + url;
            this.token = token;
            this.requestMethod = requestMethod;
        }


        @Override
        protected String doInBackground(String... params) {

            Request request;
            Request.Builder requestBuilder = new Request.Builder();

            requestBuilder.url(url);
            requestBuilder.addHeader("Authorization", this.token);

            if(requestMethod == RequestMethodEnum.POST) {
                requestBuilder.post(RequestBody.create(JSON, params[0]));
            }
            if(requestMethod == RequestMethodEnum.DELETE) {
                requestBuilder.delete();
            }

            request = requestBuilder.build();


            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String result = "";
            try {
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {

        }
    }
}
