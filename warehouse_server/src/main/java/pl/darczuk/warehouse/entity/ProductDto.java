package pl.darczuk.warehouse.entity;

import java.util.List;

public class ProductDto {

    private Long id;
    private String modelName;
    private String manufacturerName;
    private Double price;
    private Double size;
    private int quantity;
    List<Product> products;
    private Long lastTimeUpdate;

    public ProductDto(){}

    public ProductDto(Long id, String modelName, String manufacturerName, Double price, Double size, int quantity, List<Product> products, Long lastTimeUpdate) {
        this.id = id;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
        this.products = products;
        this.lastTimeUpdate = lastTimeUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
            this.quantity = quantity;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getLastTimeUpdate() {
        return lastTimeUpdate;
    }

    public void setLastTimeUpdate(Long lastTimeUpdate) {
        this.lastTimeUpdate = lastTimeUpdate;
    }
}
