package pl.darczuk.warehouse.controller;

import org.springframework.data.jpa.repository.Query;
import pl.darczuk.warehouse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface WarehouseRepository extends JpaRepository<Product, Long> {


    @Query("SELECT l FROM Product l")
    List<Product> findAllSever();

//    @Query("UPDATE product SET serverQuantity = :quantity  WHERE product.id = :id")
//    int updateFromDto(Long id, int quantity); // return number updated rows

}
