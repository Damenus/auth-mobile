package pl.darczuk.warehouse.activity.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import pl.darczuk.warehouse.activity.dao.ProductDao;
import pl.darczuk.warehouse.activity.model.Product;

public class ProductRepository {
    private ProductDao mProductDao;
    private LiveData<List<Product>> mAllProduct;

    public ProductRepository(Application application) {
        ProductRoomDatabase db = ProductRoomDatabase.getDatabase(application);
        mProductDao = db.productDao();
        mAllProduct = mProductDao.getAllProducts();
    }

    public LiveData<List<Product>> getAllProduct() {
        return mAllProduct;
    }

    public void insert(Product product) {
        new insertAsyncTask(mProductDao).execute(product);
    }

    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao mAsyncTaskDao;

        insertAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Product... products) {
            mAsyncTaskDao.insert(products[0]);
            return null;
        }
    }
}
