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

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllProducts = mRepository.getAllProduct();
    }

    public LiveData<List<Product>> getAllProducts() { return mAllProducts; }

    public void insert(Product product) { mRepository.insert(product); }

}
