package pl.darczuk.warehouse.activity.model;

import java.util.List;

public class ProductDTO {

    private Long id;
    private String modelName;
    private String manufacturerName;
    private Double price;
    private Double size;
    private int quantity;
    private Long lastTimeUpdate;
    private String productsBundle;

    public ProductDTO(){}

    public ProductDTO(Long id, String modelName, String manufacturerName, Double price, Double size, int quantity, Long lastTimeUpdate, String productsBundle) {
        this.id = id;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
        this.lastTimeUpdate = lastTimeUpdate;
        this.productsBundle = productsBundle;
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

    public Long getLastTimeUpdate() {
        return lastTimeUpdate;
    }

    public void setLastTimeUpdate(Long lastTimeUpdate) {
        this.lastTimeUpdate = lastTimeUpdate;
    }

    public String getProductsBundle() {
        return productsBundle;
    }

    public void setProductsBundle(String productsBundle) {
        this.productsBundle = productsBundle;
    }
}
