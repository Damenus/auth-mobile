package pl.darczuk.warehouse.activity.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.List;

import pl.darczuk.warehouse.activity.RestClient;
import pl.darczuk.warehouse.activity.dao.ProductDao;
import pl.darczuk.warehouse.activity.database.ProductRepository;
import pl.darczuk.warehouse.activity.model.Product;
import pl.darczuk.warehouse.activity.model.ProductDTO;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private LiveData<List<Product>> mAllProducts;
    private LiveData<Product> mProduct;

    private RestClient restClient;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllProducts = mRepository.getAllProduct();
        restClient = new RestClient(application);
    }

    public LiveData<List<Product>> getAllProducts() { return mAllProducts; }

    public Product getProductById(Long productId) { return mRepository.getProduct(productId); }

    public void insert(Product product) { mRepository.insert(product); }

    public void insertProducts(List<Product> products) { mRepository.insertProducts(products); }

    public void delete(Product product) { mRepository.delete(product); }

    public void increasing(Product product, int quantity) {
        product.setQuantity(product.getLocalDeltaChangeQuantity() + quantity);
        mRepository.update(product);
    }

    public void decreasing(Product product, int quantity) {
        int newQuantity = product.getLocalDeltaChangeQuantity() - quantity;

        //if (newQuantity < 0)
           // return;

        product.setQuantity(newQuantity);
        mRepository.update(product);
    }

    public int sync(){

        List<ProductDTO> productsFromServer;
        List<Product> productsFromApp = mRepository.getAllProductsAsync();
        if(productsFromApp != null) {

            productsFromServer = restClient.sync(productsFromApp);

            // porównanie list by sprawdzić czy nie ma nowych produktów bo insert a nie update
            Iterator<ProductDTO> iterProductServer = productsFromServer.iterator();
            Iterator<Product> iterProductApp = productsFromApp.iterator();

            while (iterProductServer.hasNext()) {
                ProductDTO productServer = iterProductServer.next();
                Long idProduct = productServer.getId();
                while (iterProductApp.hasNext()) {
                    Product productApp = iterProductApp.next();
                    //update
                    if(productApp.getId() == idProduct) {
                        mRepository.updateFromDto(productServer);
                        iterProductServer.remove();
                        iterProductApp.remove();
                        break;
                    }
                }
            }

//            for (ProductDTO productServer: productsFromServer) {
//                Long idProduct = productServer.getId();
//                for (Product productApp: productsFromApp) {
//                    //update
//                    if(productApp.getId() == idProduct) {
//                        mRepository.updateFromDto(productServer);
//                        productsFromServer.remove(productServer);
//                        productsFromApp.remove(productApp);
//                        break;
//                    }
//                }
//            }

            //add
            for (ProductDTO productServer: productsFromServer) {
                mRepository.insert(new Product(productServer));
            }

            //remove
            for (Product productApp: productsFromApp) {
                mRepository.delete(productApp);
            }

            // update te same
//            for (ProductDTO productDto: productsFromServer){
//                mRepository.updateFromDto(productDto);
//
//            }


            return productsFromServer.size();
        }
        return 0;
    }

    public void nuke(){
        mRepository.nuke();
    }

}
