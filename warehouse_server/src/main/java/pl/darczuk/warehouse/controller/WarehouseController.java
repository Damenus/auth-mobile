package pl.darczuk.warehouse.controller;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
import pl.darczuk.warehouse.entity.Product;
import org.springframework.web.bind.annotation.*;
import pl.darczuk.warehouse.entity.Role;
import pl.darczuk.warehouse.entity.User;
import pl.darczuk.warehouse.security.TokenGenerator;

import java.security.Principal;
import java.util.*;

@RestController
//@EnableOAuth2Sso
public class WarehouseController { //extends WebSecurityConfigurerAdapter {

    private WarehouseRepository repository;
    private UserRepository userRepository;
    //private TokenRepository tokenRepository;
    private ArrayList<String> tokens = new ArrayList<>() ;

    private TokenGenerator tokenGenerator = new TokenGenerator();

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password) {
        User user = userRepository.findByLogin(login);
        if (user.getPassword().equals(password)) {
            String token = tokenGenerator.createToken(user);
            tokens.add(token);
            return token;
        }
        else
            return "";
    }


    @PostMapping("/registration")
    public User registration(@RequestParam String login, @RequestParam String password, @RequestParam String role) {

        Role uRole;

        if (role == "MENAGER") {
            uRole = Role.MENAGER;
        } else  {
            uRole = Role.EMPLOYEE;
        }

        User newUser = new User(login, password, uRole);
        return userRepository.save(newUser);
    }
//
//    @PostMapping("/registration")
//    public User registration(@RequestBody User user) {
//
//        return userRepository.save(user);
//    }

    public WarehouseController(WarehouseRepository repository, UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
    }

    private boolean checkIfTokenExist(String token) {

        return tokens.contains(token);
    }

    @GetMapping("/api/v1/product")
    @ResponseBody
    public List<Product> getAllProducts(@RequestHeader(value="Authorization") String token) {

        if (checkIfTokenExist(token))
            return repository.findAll();
        else
            return null;
    }

    @GetMapping("/api/v1/product/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/api/v1/product")
    public Product addProduct(@RequestParam String modelName, @RequestParam String manufacturerName, @RequestParam String price) {
        Product newProduct = new Product(modelName, manufacturerName, Double.valueOf(price));
        return repository.save(newProduct);
    }

//    @PostMapping("/api/v1/product")
//    public Product addProduct2(@RequestBody Product newProduct) {
//
//        return repository.save(newProduct);
//    }

    @DeleteMapping("/api/v1/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/api/v1/product/{id}/increase/{quantity}")
    public Product increaseQuantityProduct(@PathVariable Long id, @PathVariable Integer quantity) {

        Product updatedProduct = repository.findById(id).get();
        updatedProduct.increaseQuantity(quantity);
        return repository.save(updatedProduct);
    }

    @GetMapping("/api/v1/product/{id}/decrease/{quantity}")
    public Product decreaseQuantityProduct(@PathVariable Long id, @PathVariable Integer quantity) {

        Product updatedProduct = repository.findById(id).get();
        updatedProduct.decreaseQuantity(quantity);
        return repository.save(updatedProduct);
    }

}
