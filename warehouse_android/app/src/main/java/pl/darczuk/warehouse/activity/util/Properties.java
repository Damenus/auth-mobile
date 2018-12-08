package pl.darczuk.warehouse.activity.util;

public class Properties {

    public static final String WAREHOUSE_URL = "http://192.168.0.150:8080";
    public static final String WAREHOUSE_API = "/api/v1";
    public static final String WAREHOUSE_PRODUCT = "/product";
    private static Properties mInstance = null;

    protected Properties() {}

    public static synchronized Properties getInstance() {
        if (null == mInstance) {
            mInstance = new Properties();
        }
        return mInstance;
    }
}
