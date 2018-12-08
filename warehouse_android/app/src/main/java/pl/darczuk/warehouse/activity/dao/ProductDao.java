package pl.darczuk.warehouse.activity.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import pl.darczuk.warehouse.activity.model.Product;

@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM product")
    void deleteAll();

    @Query("SELECT * FROM product ORDER BY id ASC")
    LiveData<List<Product>> getAllProducts();
}
