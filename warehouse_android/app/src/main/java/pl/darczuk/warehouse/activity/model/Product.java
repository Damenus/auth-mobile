package pl.darczuk.warehouse.activity.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

//import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
  //  @JsonProperty("id")
    private Long id;
  //  @JsonProperty("modelName")
    private String modelName;
 //   @JsonProperty("manufacturerName")
    private String manufacturerName;
  //  @JsonProperty("price")
    private Double price;
    private Double size;
    private int serverQuantity;
  //  @JsonProperty("localDeltaChangeQuantity")
    private int localDeltaChangeQuantity;
    private Long lastTimeUpdate;
    private String productsBundle;

    public Product() {}

    public Product(Long id, String modelName, String manufacturerName, Double price, Double size, int serverQuantity, Long lastTimeUpdate, String productsBundle) {
        this.id = id;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.size = size;
        this.serverQuantity = serverQuantity;
        this.localDeltaChangeQuantity = 0;
        this.lastTimeUpdate = lastTimeUpdate;
        this.productsBundle = productsBundle;
    }

    public Product(Long id, String modelName, String manufacturerName, Double price, Double size, int serverQuantity, int localDeltaChangeQuantity, Long lastTimeUpdate, String productsBundle) {
        this.id = id;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.size = size;
        this.serverQuantity = serverQuantity;
        this.localDeltaChangeQuantity = localDeltaChangeQuantity;
        this.lastTimeUpdate = lastTimeUpdate;
        this.productsBundle = productsBundle;
    }

    public Product(ProductDTO productDTO) {
        this.id = productDTO.getId();
        this.modelName = productDTO.getModelName();
        this.manufacturerName = productDTO.getManufacturerName();
        this.price = productDTO.getPrice();
        this.size = productDTO.getSize();
        this.serverQuantity = productDTO.getQuantity();
        this.localDeltaChangeQuantity = 0;
        this.lastTimeUpdate = productDTO.getLastTimeUpdate();
        this.productsBundle = productDTO.getProductsBundle();
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getServerQuantity() {
        return serverQuantity;
    }

    public void setServerQuantity(int serverQuantity) {
        this.serverQuantity = serverQuantity;
    }

    public int getLocalDeltaChangeQuantity() {
        return localDeltaChangeQuantity;
    }

    public void setLocalDeltaChangeQuantity(int localDeltaChangeQuantity) {
        this.localDeltaChangeQuantity = localDeltaChangeQuantity;
    }

    public int getQuantity() {
        return serverQuantity + localDeltaChangeQuantity;
    }

    public void setQuantity(int quantity) {
        this.localDeltaChangeQuantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        this.localDeltaChangeQuantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.localDeltaChangeQuantity -= quantity;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", price=" + price +
                ", serverQuantity=" + serverQuantity +
                ", localDeltaChangeQuantity=" + localDeltaChangeQuantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return serverQuantity == product.serverQuantity &&
                localDeltaChangeQuantity == product.localDeltaChangeQuantity &&
                id == product.id &&
                modelName == product.modelName &&
                manufacturerName == product.manufacturerName &&
                price == product.price;
    }

    @Override
    public int hashCode() {

        return id.hashCode() + modelName.hashCode() + manufacturerName.hashCode();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        id = in.readLong();
        modelName = in.readString();
        manufacturerName = in.readString();
        price = in.readDouble();
        serverQuantity = in.readInt();
        localDeltaChangeQuantity = in.readInt();
    }

}
