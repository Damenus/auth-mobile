package pl.darczuk.warehouse.controller;

import pl.darczuk.warehouse.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {

    private WarehouseRepository repository;

    public WarehouseController(WarehouseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Product> allProducts() {
        return repository.findAll();
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
