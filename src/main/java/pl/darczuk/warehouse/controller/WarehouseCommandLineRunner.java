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

        warehouseRepository.save(new Product("modelName", "manufacturerName", 2.49));
        warehouseRepository.save(new Product("modelName", "manufacturerName", 2.49));
        warehouseRepository.save(new Product("modelName", "manufacturerName", 2.49));
        warehouseRepository.save(new Product("modelName", "manufacturerName", 2.49));
        warehouseRepository.save(new Product("modelName", "manufacturerName", 2.49));
        warehouseRepository.save(new Product("modelName", "manufacturerName", 2.49));
        warehouseRepository.save(new Product("modelName", "manufacturerName", 2.49));

        warehouseRepository.findAll().forEach(System.out::println);
    }
}
