package pl.darczuk.warehouse.controller;

import pl.darczuk.warehouse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WarehouseRepository extends JpaRepository<Product, Long> {
}
