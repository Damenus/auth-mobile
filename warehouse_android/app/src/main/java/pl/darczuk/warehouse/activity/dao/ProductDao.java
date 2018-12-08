package pl.darczuk.warehouse.activity.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pl.darczuk.warehouse.activity.model.Product;

@Dao
public interface ProductDao {

    @Update
    LiveData<Product> update(Product product);

    @Insert
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM product")
    void deleteAll();

    @Query("SELECT * FROM product WHERE id = :productId LIMIT 1")
    LiveData<Product> findProductById(int productId);

    @Query("SELECT * FROM product ORDER BY id ASC")
    LiveData<List<Product>> getAllProducts();

}
