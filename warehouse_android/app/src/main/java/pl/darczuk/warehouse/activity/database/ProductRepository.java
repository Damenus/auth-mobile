package pl.darczuk.warehouse.activity.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import pl.darczuk.warehouse.activity.dao.ProductDao;
import pl.darczuk.warehouse.activity.model.Product;
import pl.darczuk.warehouse.activity.model.ProductDTO;

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

    public List<Product> getAllProductsAsync() {

        try {
            return new getAllAsyncTask(mProductDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class getAllAsyncTask extends AsyncTask<Void, Void, List<Product>> {

        private ProductDao mAsyncTaskDao;

        public getAllAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Product> doInBackground(final Void... params) {
            List<Product> products = mAsyncTaskDao.getAllProductsAsync();
            return products;
        }

    }

    public Product getProduct(Long productId) {

        try {
            return new findAsyncTask(mProductDao).execute(productId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class findAsyncTask extends AsyncTask<Long, Void, Product> {

        private ProductDao mAsyncTaskDao;

        public findAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Product doInBackground(final Long... productsId) {
            Product product = mAsyncTaskDao.findProductById(productsId[0]);
            return product;
        }

    }

    public void insertProducts(List<Product> products) {
        new insertProductsAsyncTask(mProductDao).execute(products);
    }

    private static class insertProductsAsyncTask extends AsyncTask<List<Product>, Void, Void> {

        private ProductDao mAsyncTaskDao;

        insertProductsAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Product>... products) {
            mAsyncTaskDao.insertProducts(products[0]);
            return null;
        }
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

    public void nuke() {
        new nukeAsyncTask(mProductDao).execute();
    }

    private class nukeAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProductDao mAsyncTaskDao;

        nukeAsyncTask(ProductDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.nuke();
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

    public void updateFromDto(ProductDTO product) {
        new updateFromDtoAsyncTask(mProductDao).execute(product);
    }

    private class updateFromDtoAsyncTask extends AsyncTask<ProductDTO, Void, Void> {

        private ProductDao mAsyncTaskDao;

        public updateFromDtoAsyncTask(ProductDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final ProductDTO... products) {
            mAsyncTaskDao.updateFromDto(products[0].getId(), products[0].getQuantity());
            return null;
        }
    }

    public void synchronize() { new synchronizeAsyncTask(mProductDao).execute(); }

    private class synchronizeAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProductDao mAsyncTaskDao;

        public synchronizeAsyncTask(ProductDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Void... param) {

            // wysyłam całą swoją bazę produktów (idProduktu, localDeltaChangeQuantity) a mam (idProduktu, ilość od servera ,ilość jaką jaką dodał/odjął), wyświetlam (idProduktu, ilość od servera + ilość jaką jaką dodał/odjął)
            // Server modyfikuję słownik(użtykownik+UUIDurządzenia, ilość jaką jaką dodał/odjął)
            // odbieram całą listę produktów z aktualnymi na serwerze ilościa, ale serwer wysyła AllQuantity -

            return null;
        }
    }
}
