package pl.darczuk.warehouse.controller;

import pl.darczuk.warehouse.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.darczuk.warehouse.entity.Role;
import pl.darczuk.warehouse.entity.User;

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

        userRepository.save(
                new User("manager", "admin", Role.MENAGER));
        userRepository.save(
                new User("employee1", "admin", Role.EMPLOYEE));
        userRepository.save(
                new User("employee2", "admin", Role.EMPLOYEE));
        userRepository.save(
                new User("dd", "dd", Role.MENAGER));


        userRepository.findAll().forEach(System.out::println);
    }
}
