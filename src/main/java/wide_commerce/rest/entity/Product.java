package wide_commerce.rest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String id;

    private String name;

    private String code;

    private Long price;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCart> productCarts;
}
