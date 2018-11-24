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

import java.security.Principal;
import java.util.List;

@RestController
//@EnableOAuth2Sso
public class WarehouseController { //extends WebSecurityConfigurerAdapter {

    private WarehouseRepository repository;
    private UserRepository userRepository;

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @PostMapping("/api/v1/login")
    public String login(@RequestParam String login, @RequestParam String password) {
        //userRepository.findOne()
        return "DUPA"+password;
    }

    @PostMapping("/api/v1/registration")
    public String registration(@RequestParam String login, @RequestParam String password, @RequestParam Role role) {
        userRepository.save(new User(login, password, role));
        return "DUPA"+password;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**", "/error**").permitAll().anyRequest()
//                .authenticated();
//    }

    public WarehouseController(WarehouseRepository repository, UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
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

    @PostMapping("/registration")
    public User registration(@RequestBody User user) {

        return userRepository.save(user);
    }

//    @PostMapping("/login")
//    public User login(@RequestBody User user) {
//
//        return userRepository.save(newProduct);
//    }

    //@RequestMapping("/")
    //public String index() {
      //  return "Greetings from Spring Boot!";
    //}
}
