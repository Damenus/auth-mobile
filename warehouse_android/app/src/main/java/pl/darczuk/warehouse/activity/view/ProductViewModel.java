package pl.darczuk.warehouse.activity.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import pl.darczuk.warehouse.activity.database.ProductRepository;
import pl.darczuk.warehouse.activity.model.Product;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private LiveData<List<Product>> mAllProducts;
    private LiveData<Product> mProduct;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllProducts = mRepository.getAllProduct();
    }

    public LiveData<List<Product>> getAllProducts() { return mAllProducts; }

    public LiveData<Product> getProductById(int productId) { return mRepository.getProduct(productId); }

    public void insert(Product product) { mRepository.insert(product); }

    public void delete(Product product) { mRepository.delete(product); }

    public void increasing(Product product, int quantity) {
        product.setQuantity(product.getQuantity() + quantity);
        mRepository.update(product);
    }

    public void decreasing(Product product, int quantity) {
        int newQuantity = product.getQuantity() - quantity;

        if (newQuantity < 0)
            return;

        product.setQuantity(newQuantity);
        mRepository.update(product);
    }

}
