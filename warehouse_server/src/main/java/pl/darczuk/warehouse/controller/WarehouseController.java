package pl.darczuk.warehouse.controller;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.darczuk.warehouse.entity.*;
import org.springframework.web.bind.annotation.*;
import pl.darczuk.warehouse.security.TokenGenerator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

@RestController
//@EnableOAuth2Sso
public class WarehouseController { //extends WebSecurityConfigurerAdapter {

    private WarehouseRepository repository;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private ArrayList<String> tokens = new ArrayList<>() ;

    private TokenGenerator tokenGenerator = new TokenGenerator();

    private Map<String,String> getMapFromGoogleTokenString(String idTokenString) {
        //String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + tokenID;

        BufferedReader in = null;
        try {
            // get information from token by contacting the google_token_verify_tool url :
            in = new BufferedReader(new InputStreamReader(
                    ((HttpURLConnection) (new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + idTokenString.trim()))
                            .openConnection()).getInputStream(), Charset.forName("UTF-8")));

            // read information into a string buffer :
            StringBuffer b = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                b.append(inputLine + "\n");
            }

            // transforming json string into Map<String,String> :
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(b.toString(), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class));

            // exception handling :
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            System.out.println("\n\n\tFailed to transform json to string\n");
            e.printStackTrace();
        } finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, @RequestParam String uuidDevice) {
        User user = userRepository.findByLogin(login);
        if (user.getPassword().equals(password)) {
            String token = tokenGenerator.createToken(user);
            Token t = tokenRepository.save(new Token(user, uuidDevice));
            tokens.add(token);
            //return token;
            return t.getHash()+":"+user.getRole();
        }
        else
            return "";
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader(value="Authorization") String token) {
        tokens.remove(token);
    }

    @PostMapping("/testToken")
    public String testToken(@RequestHeader(value="Authorization") String token) {
        if (checkIfTokenExist(token)) {
            return "OK";
        } else {
            return "404";
        }
    }

    @PostMapping("/tokensignin")
    public String tokenSignIn(@RequestParam String idTokenString) {

//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
//                // Specify the CLIENT_ID of the app that accesses the backend:
//                //.setAudience(Collections.singletonList(CLIENT_ID))
//                .setAudience(Collections.singletonList("BE:01:A3:AC:59:4E:92:DC:B4:57:D6:D5:87:2C:FB:0B:AD:75:D3:84"))
//                // Or, if multiple clients access the backend:
//                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
//                .build();
//
//// (Receive idTokenString by HTTPS POST)
//
//        GoogleIdToken idToken = null;
//        try {
//            idToken = verifier.verify(idTokenString);
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (idToken != null) {
//            Payload payload = idToken.getPayload();
//
//            // Print user identifier
//            String userId = payload.getSubject();
//            System.out.println("User ID: " + userId);
//
//            // Get profile information from payload
//            String email = payload.getEmail();
//            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//            String name = (String) payload.get("name");
//            String pictureUrl = (String) payload.get("picture");
//            String locale = (String) payload.get("locale");
//            String familyName = (String) payload.get("family_name");
//            String givenName = (String) payload.get("given_name");
//
//            User user = userRepository.findByLogin(email);
//            String token = tokenGenerator.createToken(user);
//            tokens.add(token);
//            return token;
//        }

        Map<String,String> mapa = getMapFromGoogleTokenString(idTokenString);
        if (mapa != null) {
            String email = mapa.get("email");
            User user = userRepository.findByLogin(email);
            if (user != null) {
                String token = tokenGenerator.createToken(user);
                tokens.add(token);
                return token;
            } else
                return "";
        }

        return "";
    }

    @PostMapping("/tokenregistration")
    public String tokenRegistration(@RequestParam String idTokenString, @RequestParam String role) {
//
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
//                .setAudience(Collections.singletonList("BE:01:A3:AC:59:4E:92:DC:B4:57:D6:D5:87:2C:FB:0B:AD:75:D3:84"))
//                .build();
//
//        GoogleIdToken idToken = null;
//        try {
//            idToken = verifier.verify(idTokenString);
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String email="";
//        String userId = "";
//
//        // if User user = userRepository.findByLogin(email);
//
//        if (idToken != null) {
//            Payload payload = idToken.getPayload();
//
//            // Print user identifier
//            userId = payload.getSubject();
//            System.out.println("User ID: " + userId);
//
//            // Get profile information from payload
//            email = payload.getEmail();
//            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//            String name = (String) payload.get("name");
//            String pictureUrl = (String) payload.get("picture");
//            String locale = (String) payload.get("locale");
//            String familyName = (String) payload.get("family_name");
//            String givenName = (String) payload.get("given_name");

            Role uRole;

            if (role.equals("MENAGER")) {
                uRole = Role.MENAGER;
            } else  {
                uRole = Role.EMPLOYEE;
            }

        Map<String,String> mapa = getMapFromGoogleTokenString(idTokenString);
        if (mapa != null) {
            String email = mapa.get("email");
            User newUser = new User(email, "", uRole);
            User user = userRepository.findByLogin(email);
            if (user == null) {
                userRepository.save(newUser);
                String token = tokenGenerator.createToken(newUser);
                tokens.add(token);
                return token;
            }
            return "";
        }


       // }

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

    public WarehouseController(WarehouseRepository repository, UserRepository userRepository, TokenRepository tokenRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    private boolean checkIfTokenExist(String token) {

        return tokenRepository.existsById(token);//tokens.contains(token);
    }

    @GetMapping("/api/v1/product")
    @ResponseBody
    public List<Product> getAllProducts(@RequestHeader(value="Authorization") String token) {

        if (checkIfTokenExist(token)) {
            List<Product> dd = repository.findAll();
            return dd;
        }
        else
            return null;
    }

    @GetMapping("/api/v1/product/{id}")
    @ResponseBody
    public Product getProduct(@RequestHeader(value="Authorization") String token, @PathVariable Long id) {

        if (checkIfTokenExist(token)) {
            return repository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
        } else
            return null;
    }


    @RequestMapping(value="/api/v1/product", method=RequestMethod.POST,consumes="application/json",produces="application/json")
    @ResponseBody
    public Product addProduct(@RequestHeader(value="Authorization") String token, @RequestBody Product newProduct) {

        if (checkIfTokenExist(token)) {
            return repository.save(newProduct);
        } else
            return null;
    }


    @DeleteMapping("/api/v1/product/{id}")
    public void deleteProduct(@RequestHeader(value="Authorization") String token, @PathVariable Long id) {

        if (checkIfTokenExist(token)) {
            repository.deleteById(id);
        }
    }

    @GetMapping("/api/v1/product/{id}/increase/{quantity}")
    public Product increaseQuantityProduct(@RequestHeader(value="Authorization") String token, @PathVariable Long id, @PathVariable Integer quantity) {

        if (checkIfTokenExist(token)) {
            Product updatedProduct = repository.findById(id).get();
            //updatedProduct.increaseQuantity(quantity);
            return repository.save(updatedProduct);
        } else
            return null;
    }

    @GetMapping("/api/v1/product/{id}/decrease/{quantity}")
    public Product decreaseQuantityProduct(@RequestHeader(value="Authorization") String token, @PathVariable Long id, @PathVariable Integer quantity) {

        if (checkIfTokenExist(token)) {
            Product updatedProduct = repository.findById(id).get();
            //updatedProduct.decreaseQuantity(quantity);
            return repository.save(updatedProduct);
        } else
            return null;
    }


    @RequestMapping(value="/api/v1/sync", method=RequestMethod.POST,consumes="application/json",produces="application/json")
    @ResponseBody
    public List<ProductDto> syncAllProducts(@RequestHeader(value="Authorization") String token, @RequestBody List<ProductDto> productsFromDevice) {

        if (checkIfTokenExist(token)) {
            String uuidApp = tokenRepository.getOne(token).getDevice();
            List<Product> productsFromServer = repository.findAll();
            List<ProductDto> syncedProducts = new ArrayList<>();
            Product productFromRepo;

            Iterator<ProductDto> iterProductApp = productsFromDevice.iterator();
            Iterator<Product> iterProductServer = productsFromServer.iterator();

            while (iterProductApp.hasNext()) {
                ProductDto productApp = iterProductApp.next();
                Long idProduct = productApp.getId();
                while (iterProductServer.hasNext()) {
                    Product productServer = iterProductServer.next();
                    //update
                    if(productApp.getId() == idProduct) {
//////////////
                        //get prduct
                        productFromRepo = repository.findById(idProduct).get();
                        // update quantiti for the device
                        productFromRepo.setQuantity(productApp.getQuantity(), uuidApp);
                        int quantityWithoutDevice = productFromRepo.getQuantity() - productFromRepo.getQuantity(uuidApp);
                        // prepare DTO
                        syncedProducts.add(new ProductDto(productFromRepo.getId(), productFromRepo.getModelName(),  productFromRepo.getManufacturerName(), productFromRepo.getPrice(), quantityWithoutDevice));
//////////////

                        iterProductServer.remove();
                        iterProductApp.remove();
                        break;
                    }
                }
            }


            //remove
            for (Product productServer: productsFromServer) {
                repository.delete(repository.findById(productServer.getId()).get());
            }

            // add
            for (ProductDto productApp: productsFromDevice) {
                repository.save(new Product(productApp, uuidApp));
            }

//            for (ProductDto productFromRequest: productsDevice) {
//                //get prduct
//                productFromRepo = repository.findById(productFromRequest.getId()).get();
//                // update quantiti for the device
//                productFromRepo.setQuantity(productFromRequest.getQuantity(), uuidApp);
//                int quantityWithoutDevice = productFromRepo.getQuantity() - productFromRepo.getQuantity(uuidApp);
//                // prepare DTO
//                syncedProducts.add(new ProductDto(productFromRepo.getId(), productFromRepo.getModelName(),  productFromRepo.getManufacturerName(), productFromRepo.getPrice(), quantityWithoutDevice));
//            }


            return syncedProducts;

        } else
            return null;
    }

}
