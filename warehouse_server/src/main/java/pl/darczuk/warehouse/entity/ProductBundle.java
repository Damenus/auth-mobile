package pl.darczuk.warehouse.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
public class ProductBundle implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String modelName;
    private String manufacturerName;
    private Double price;
    @ElementCollection
    private Map<String, Integer> quantity;
    @ElementCollection
    List<Product> products;
    private Long lastTimeUpdate;
}
