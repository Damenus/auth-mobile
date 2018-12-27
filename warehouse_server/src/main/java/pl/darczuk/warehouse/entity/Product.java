package pl.darczuk.warehouse.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String modelName;
    private String manufacturerName;
    private Double price;
    private Double size;
    @ElementCollection
    private Map<String, Integer> quantity;
    @ManyToMany
    List<Product> products;
    private Long lastTimeUpdate;

    public void setId(Long id) {
        this.id = id;
    }

    public Product() {}

    public Product(String modelName, String manufacturerName, Double price, Double size) {
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.size = size;
        this.quantity = new HashMap<>();
        //this.quantity = 0;
        this.lastTimeUpdate = System.currentTimeMillis();
    }

    public Product(String modelName, String manufacturerName, Double price, Double size, int quantity, Long lastTimeUpdate) {
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.size = size;
        this.quantity = new HashMap<>();
        this.quantity.put("admin", quantity);
        //this.quantity = quantity;
        this.lastTimeUpdate = lastTimeUpdate;
    }

    public Product(String modelName, String manufacturerName, Double price, Double size, int quantity, Long lastTimeUpdate, List<Product> listProducts) {
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.size = size;
        this.quantity = new HashMap<>();
        this.quantity.put("admin", quantity);
        this.products = listProducts;
        this.lastTimeUpdate = lastTimeUpdate;
    }

    public Product(ProductDto productDTO, String uuidDevice) {
        this.id = productDTO.getId();
        this.modelName = productDTO.getModelName();
        this.manufacturerName = productDTO.getManufacturerName();
        this.price = productDTO.getPrice();
        this.size =  productDTO.getSize();
        this.quantity = new HashMap<>();
        this.quantity.put(uuidDevice, productDTO.getQuantity());
        this.lastTimeUpdate = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Long getLastTimeUpdate() {
        return lastTimeUpdate;
    }

    public void setLastTimeUpdate(Long lastTimeUpdate) {
        this.lastTimeUpdate = lastTimeUpdate;
    }

    public int getQuantity(String id) {
        int dd = quantity.getOrDefault(id, 0);
        return dd;
    }

    public int getQuantity() {
        AtomicInteger sum = new AtomicInteger();
        quantity.forEach((k,v)-> sum.addAndGet(v));
        return sum.intValue();
    }

    public void setQuantity(int quantity, String id) {
        if (this.quantity.containsKey(id))
            this.quantity.replace(id, quantity);
        else
            this.quantity.put(id, quantity);
    }

    public void increaseQuantity(int quantity, String id) {
        this.quantity.merge(id, quantity, Integer::sum);
    }

    public void decreaseQuantity(int quantity, String id) {
        this.quantity.merge(id, quantity, (integer, integer2) -> integer - integer2);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getProductsBundleId() {
        String productsIds = "";
        List<Long> productsLongIds = new ArrayList<>();
        for (Product product: products) {
            productsLongIds.add(product.getId());
        }

        productsIds = productsLongIds.toString();

        return productsIds;

    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity &&
                Objects.equals(id, product.id) &&
                Objects.equals(modelName, product.modelName) &&
                Objects.equals(manufacturerName, product.manufacturerName) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, modelName, manufacturerName, price, quantity);
    }
}
