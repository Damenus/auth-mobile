package pl.darczuk.warehouse.activity.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public LiveData<Product> getProduct(int productId) {

        try {
            return new findAsyncTask(mProductDao).execute(productId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insert(Product product) {
        new insertAsyncTask(mProductDao).execute(product);
    }

    private class findAsyncTask extends AsyncTask<Integer, Void, LiveData<Product>> {

        private ProductDao mAsyncTaskDao;

        public findAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<Product> doInBackground(final Integer... productsId) {
            LiveData<Product> product = mAsyncTaskDao.findProductById(productsId[0]);
            return product;
        }

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

    public void delete(Product product) {
        new deleteAsyncTask(mProductDao).execute(product);
    }

    private class deleteAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao mAsyncTaskDao;

        deleteAsyncTask(ProductDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Product... products) {
            mAsyncTaskDao.delete(products[0]);
            return null;
        }
    }

    public void update(Product product) {
        new updateAsyncTask(mProductDao).execute(product);
    }

    private class updateAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao mAsyncTaskDao;

        public updateAsyncTask(ProductDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Product... products) {
            mAsyncTaskDao.update(products[0]);
            return null;
        }
    }

    public void synchronize() { new synchronizeAsyncTask(mProductDao).execute(); }

    private class synchronizeAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProductDao mAsyncTaskDao;

        public synchronizeAsyncTask(ProductDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Void... param) {

            return null;
        }
    }
}
