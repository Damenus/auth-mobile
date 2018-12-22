package pl.darczuk.warehouse.controller;

import pl.darczuk.warehouse.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.darczuk.warehouse.entity.Role;
import pl.darczuk.warehouse.entity.User;

import java.util.ArrayList;

@Component
public class WarehouseCommandLineRunner implements CommandLineRunner {

    private final WarehouseRepository warehouseRepository;

    private final UserRepository userRepository;

    public WarehouseCommandLineRunner(WarehouseRepository warehouseRepository, UserRepository userRepository) {
        this.warehouseRepository = warehouseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        Product pixel2 = new Product("Pixel 2 XL", "Google", 4400.0, 5.0, 20, System.currentTimeMillis());
        Product pixel3 = new Product("Pixel 3", "Google", 5400.0, 5.0, 20, System.currentTimeMillis());

        ArrayList<Product> list1 = new ArrayList<>();
        list1.add(pixel2);
        list1.add(pixel3);
        Product bundle1 = new Product("Google bundle", "Google", 12400.0, 5.0,5, System.currentTimeMillis(), list1);

        warehouseRepository.save(
                new Product("Galaxy S9", "Samsung", 3000.0, 5.5, 40, System.currentTimeMillis()));
        warehouseRepository.save(
                new Product("Galaxy S8", "Samsung", 2800.0, 5.5, 40, System.currentTimeMillis()));
        warehouseRepository.save(
                new Product("6", "OnePlus", 1800.0, 5.8, 30,  System.currentTimeMillis()));
        warehouseRepository.save(
                new Product("6T", "OnePlus", 2200.0, 5.8, 30, System.currentTimeMillis()));
        warehouseRepository.save(
                new Product("X", "Apple", 4999.99, 5.5, 50, System.currentTimeMillis()));
        warehouseRepository.save(
                new Product("XS", "Apple", 5230.50, 4.25, 100, System.currentTimeMillis()));
        warehouseRepository.save(pixel2);
        warehouseRepository.save(pixel3);

        warehouseRepository.save(bundle1);

     //   warehouseRepository.findAll().forEach(System.out::println);

        userRepository.save(
                new User("manager", "admin", Role.MENAGER));
        userRepository.save(
                new User("employee1", "admin", Role.EMPLOYEE));
        userRepository.save(
                new User("employee2", "admin", Role.EMPLOYEE));
        userRepository.save(
                new User("admin", "admin", Role.MENAGER));


        userRepository.findAll().forEach(System.out::println);
    }
}
