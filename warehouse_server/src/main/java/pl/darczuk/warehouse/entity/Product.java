package pl.darczuk.warehouse.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String modelName;
    private String manufacturerName;
    private Double price;
    private HashMap<String, Integer> quantity;
    //private int quantity;

    public void setId(Long id) {
        this.id = id;
    }

    public Product() {}

    public Product(String modelName, String manufacturerName, Double price) {
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.quantity = new HashMap<>();
        //this.quantity = 0;
    }

    public Product(String modelName, String manufacturerName, Double price, int quantity) {
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.quantity = new HashMap<>();
        this.quantity.put("admin", quantity);
        //this.quantity = quantity;
    }

    public Product(ProductDto productDTO, String uuidDevice) {
        this.id = productDTO.getId();
        this.modelName = productDTO.getModelName();
        this.manufacturerName = productDTO.getManufacturerName();
        this.price = productDTO.getPrice();
        this.quantity = new HashMap<>();
        this.quantity.put(uuidDevice, productDTO.getQuantity());
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
        this.quantity.replace(id, quantity);
    }

    public void increaseQuantity(int quantity, String id) {
        this.quantity.merge(id, quantity, Integer::sum);
    }

    public void decreaseQuantity(int quantity, String id) {
        this.quantity.merge(id, quantity, (integer, integer2) -> integer - integer2);
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
