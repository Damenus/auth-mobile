package pl.darczuk.warehouse.controller;

import pl.darczuk.warehouse.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WarehouseCommandLineRunner implements CommandLineRunner {

    private final WarehouseRepository warehouseRepository;

    public WarehouseCommandLineRunner(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        warehouseRepository.save(
                new Product("Galaxy S9", "Samsung", 3000.0, 40));
        warehouseRepository.save(
                new Product("Galaxy S8", "Samsung", 2800.0, 40));
        warehouseRepository.save(
                new Product("6", "OnePlus", 1800.0, 30));
        warehouseRepository.save(
                new Product("6T", "OnePlus", 2200.0, 30));
        warehouseRepository.save(
                new Product("X", "Apple", 4999.99, 50));
        warehouseRepository.save(
                new Product("XS", "Apple", 5230.50, 100));
        warehouseRepository.save(
                new Product("Pixel 2 XL", "Google", 4400.0, 20));

        warehouseRepository.findAll().forEach(System.out::println);
    }
}
