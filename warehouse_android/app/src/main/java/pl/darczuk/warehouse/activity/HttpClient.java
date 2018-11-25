package pl.darczuk.warehouse.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.HttpsURLConnection;

import pl.darczuk.warehouse.R;
import pl.darczuk.warehouse.activity.model.Product;
import pl.darczuk.warehouse.activity.util.Util;

public class HttpClient  {

    static DefaultHttpClient instance;


    public static String getRequest(String parametr, String token) {
        try {
            HttpClient.HttpClientTask mAuthTask = new HttpClient.HttpClientTask();
            String response = mAuthTask.execute(parametr, token).get();
            return response;

        } catch (ExecutionException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }

    public static String saveProduct(Product product) {
        try {
            HttpClient.SaveProductTask mAuthTask = new HttpClient.SaveProductTask();
            String response = mAuthTask.execute(product).get();
            return response;

        } catch (ExecutionException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }

    public static String deleteProduct(Product product) {
        try {
            HttpClient.DeleteProductTask mAuthTask = new HttpClient.DeleteProductTask();
            String response = mAuthTask.execute(product.getId().toString()).get();
            return response;

        } catch (ExecutionException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }

    public static String saveEditedProduct(Product product) {
        try {
            HttpClient.SaveEditedProductTask mAuthTask = new HttpClient.SaveEditedProductTask();
            String response = mAuthTask.execute(product).get();
            return response;

        } catch (ExecutionException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }

    public static String increaseProduct(Product product, String quantity) {
        try {
            HttpClient.IncreaseProductTask mAuthTask = new HttpClient.IncreaseProductTask();
            String response = mAuthTask.execute(String.valueOf(product.getId()), quantity).get();
            return response;

        } catch (ExecutionException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }

    public static String decreaseProduct(Product product, String quantity) {
        try {
            HttpClient.DecreaseProductTask mAuthTask = new HttpClient.DecreaseProductTask();
            String response = mAuthTask.execute(String.valueOf(product.getId()), quantity).get();
            return response;

        } catch (ExecutionException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }

    public static class DecreaseProductTask extends AsyncTask<String, Void, String> {

        private String url;

        public DecreaseProductTask() {
            this.url = Properties.getInstance().WAREHOUSE_URL + Properties.getInstance().WAREHOUSE_API;
        }

        @Override
        protected String doInBackground(String... params) {

            String stringUrl = "/product/" + params[0] + "/decrease/" + params[1];
            String result = "";

            try {

                URL myUrl = new URL(url+stringUrl);

                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                //  connection.setRequestMethod(REQUEST_METHOD);
                //  connection.setReadTimeout(READ_TIMEOUT);
                //  connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();

                int responseCode = connection.getResponseCode();
                if(responseCode == 200) {
                    InputStreamReader streamReader = new
                            InputStreamReader(connection.getInputStream());

                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();

                    String inputLine = "";
                    while ((inputLine = reader.readLine()) != null) {
                        stringBuilder.append(inputLine);
                    }

                    reader.close();
                    streamReader.close();

                    result = stringBuilder.toString();
                }
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


    public static class IncreaseProductTask extends AsyncTask<String, Void, String> {

        private String url;

        public IncreaseProductTask() {
            this.url = Properties.getInstance().WAREHOUSE_URL + Properties.getInstance().WAREHOUSE_API;
        }

        @Override
        protected String doInBackground(String... params) {

            String stringUrl = "/product/" + params[0] + "/increase/" + params[1];
            String result = "";

            try {

                URL myUrl = new URL(url+stringUrl);

                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                //  connection.setRequestMethod(REQUEST_METHOD);
                //  connection.setReadTimeout(READ_TIMEOUT);
                //  connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {

                    InputStreamReader streamReader = new
                            InputStreamReader(connection.getInputStream());

                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();

                    String inputLine = "";
                    while ((inputLine = reader.readLine()) != null) {
                        stringBuilder.append(inputLine);
                    }

                    reader.close();
                    streamReader.close();

                    result = stringBuilder.toString();
                }
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

    public static class DeleteProductTask extends AsyncTask<String, Void, String> {

        private String url;

        public DeleteProductTask() {
            this.url = Properties.getInstance().WAREHOUSE_URL + Properties.getInstance().WAREHOUSE_API;
        }

        @Override
        protected String doInBackground(String... params) {

            String stringUrl = "/product/" + params[0];
            String result = "";

            try {

                URL myUrl = new URL(url+stringUrl);

                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                //  connection.setRequestMethod(REQUEST_METHOD);
                //  connection.setReadTimeout(READ_TIMEOUT);
                //  connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setRequestMethod("DELETE");

                connection.connect();
                int responseCode = connection.getResponseCode();
                result = String.valueOf(responseCode);

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

    public static class SaveProductTask extends AsyncTask<Product, Void, String> {

        private String url;

        public SaveProductTask() {
            this.url = Properties.getInstance().WAREHOUSE_URL +
                    Properties.getInstance().WAREHOUSE_API +
                    Properties.getInstance().WAREHOUSE_PRODUCT;
        }

        private byte[] getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params)
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }

            return result.toString().getBytes("UTF-8");
        }

        @Override
        protected String doInBackground(Product... params) {

            String result = "";
            Product product = params[0];

            try {

                URL myUrl = new URL(url);


                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                //Set methods and timeouts
                int READ_TIMEOUT = 10000;
                int CONNECTION_TIMEOUT = 15000;

                connection.setRequestMethod("POST");
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("modelName", product.getModelName())
                        .appendQueryParameter("manufacturerName",  product.getManufacturerName())
                        .appendQueryParameter("price",  product.getPrice().toString());
                String query = builder.build().getEncodedQuery();

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                connection.connect();

                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                String inputLine="";
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();

                result = stringBuilder.toString(); //if token zaloguj jak -1 to nie loguj


                return result;
            } catch (IOException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
//                List<NameValuePair> params2 = new ArrayList<NameValuePair>();
//                params2.add(new BasicNameValuePair("modelName", product.getModelName()));
//                params2.add(new BasicNameValuePair("manufacturerName",  product.getManufacturerName()));
//                params2.add(new BasicNameValuePair("price",  product.getPrice().toString()));
//
//                byte[] postDataBytes = getQuery(params2);
//
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("modelName", product.getModelName());
//                jsonObject.put("manufacturerName",  product.getManufacturerName());
//                jsonObject.put("price",  product.getPrice().toString());
//
//               // HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
//                //Set methods and timeouts
//            //    int READ_TIMEOUT = 10000;
//            //    int CONNECTION_TIMEOUT = 15000;
//
//                connection.setRequestMethod("POST");
////                connection.setReadTimeout(READ_TIMEOUT);
////                connection.setConnectTimeout(CONNECTION_TIMEOUT);
//                //connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                //connection.setRequestProperty("Accept", "application/json");
//
//                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
//                connection.setDoOutput(true);
//
//                connection.getOutputStream().write(postDataBytes);
//                Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//                StringBuilder data = new StringBuilder();
//                for (int c; (c = in.read()) >= 0;) {
//                    data.append((char)c);
//                }
//                String intentData = data.toString();

        //        connection.setDoInput(true);
          //      connection.setDoOutput(true);



//                ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
//                out.writeObject(jsonObject);
//                out.close();

//                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//                wr.writeBytes("PostData=" + jsonObject);
//                wr.flush();
//                wr.close();


   //             OutputStream os = connection.getOutputStream();
     //           BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //writer.write(Util.getPostDataString(jsonObject));
                //writer.write(getQuery(params2));

    //            writer.flush();
     //           writer.close();
      //          os.close();

      //          connection.connect();
//
//                InputStreamReader streamReader = new
//                        InputStreamReader(connection.getInputStream());
//
//                BufferedReader reader = new BufferedReader(streamReader);
//                StringBuilder stringBuilder = new StringBuilder();
//
//                String inputLine="";
//                while((inputLine = reader.readLine()) != null){
//                    stringBuilder.append(inputLine);
//                }
//
//                reader.close();
//                streamReader.close();
//
//                result = stringBuilder.toString(); //if token zaloguj jak -1 to nie loguj
//                int responseCode=connection.getResponseCode();
//
//                if (responseCode == HttpsURLConnection.HTTP_OK) {
//
//                    BufferedReader in=new BufferedReader(
//                            new InputStreamReader(
//                                    connection.getInputStream()));
//                    StringBuffer sb = new StringBuffer("");
//                    String line="";
//
//                    while((line = in.readLine()) != null) {
//
//                        sb.append(line);
//                        break;
//                    }
//
//                    in.close();
//                    return sb.toString();
//
//                }
//                else {
//                    return new String("false : "+responseCode);
//                }

//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                    e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {

        }
    }

    public static class SaveEditedProductTask extends AsyncTask<Product, Void, String> {

        private String url;

        public SaveEditedProductTask() {
            this.url = Properties.getInstance().WAREHOUSE_URL + Properties.getInstance().WAREHOUSE_API;
        }

        @Override
        protected String doInBackground(Product... params) {

            Product stringUrl = params[0];
            String result = "";

            try {

                URL myUrl = new URL(url+stringUrl);

                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                //  connection.setRequestMethod(REQUEST_METHOD);
                //  connection.setReadTimeout(READ_TIMEOUT);
                //  connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();

                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                String inputLine="";
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();

                result = stringBuilder.toString();
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

    public static class HttpClientTask extends AsyncTask<String, Void, String> {

        private String url;

    public HttpClientTask() {
        this.url = Properties.getInstance().WAREHOUSE_URL + Properties.getInstance().WAREHOUSE_API;
    }

    @Override
    protected String doInBackground(String... params) {

        String stringUrl = params[0];
        String token = params[1];
        String result = "";

        try {

            URL myUrl = new URL(url+stringUrl);

            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            //Set methods and timeouts
          //  connection.setRequestMethod(REQUEST_METHOD);
          //  connection.setReadTimeout(READ_TIMEOUT);
          //  connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty ("Authorization", token);
            connection.connect();

            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            String inputLine="";
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            result = stringBuilder.toString();
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
