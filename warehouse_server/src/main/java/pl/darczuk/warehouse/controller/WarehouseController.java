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

    @GetMapping("/api/v1/product")
    @ResponseBody
    public List<Product> getAllProducts() {

        return repository.findAll();
    }

    @GetMapping("/api/v1/product/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/api/v1/product")
    public Product addProduct(@RequestBody Product newProduct) {

        return repository.save(newProduct);
    }

    @DeleteMapping("/api/v1/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/api/v1/product/{id}/increase/{quantity}")
    public void increaseQuantityProduct(@PathVariable Long id, @PathVariable Integer quantity) {

        repository.findById(id).get().increaseQuantity(quantity);
    }

    @GetMapping("/api/v1/product/{id}/decrease/{quantity}")
    public void decreaseQuantityProduct(@PathVariable Long id, @PathVariable Integer quantity) {

        repository.findById(id).get().decreaseQuantity(quantity);
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
