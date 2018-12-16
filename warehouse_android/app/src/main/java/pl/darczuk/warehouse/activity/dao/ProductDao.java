package pl.darczuk.warehouse.activity.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pl.darczuk.warehouse.activity.model.Product;
import pl.darczuk.warehouse.activity.model.ProductDTO;

@Dao
public interface ProductDao {

    @Update
    int update(Product product); // return number updated rows

    @Query("UPDATE product SET serverQuantity = :quantity WHERE product.id = :id")
    int updateFromDto(Long id, int quantity); // return number updated rows

    @Insert
    void insert(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProducts(List<Product> products);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM product")
    void nuke();

    @Query("SELECT * FROM product WHERE id = :productId LIMIT 1")
    Product findProductById(Long productId);

    @Query("SELECT * FROM product ORDER BY id ASC")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM product ORDER BY id ASC")
    List<Product> getAllProductsAsync();

}
