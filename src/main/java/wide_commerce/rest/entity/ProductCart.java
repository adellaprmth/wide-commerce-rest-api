package wide_commerce.rest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product_carts")
public class ProductCart {

    @EmbeddedId
    private ProductCartId id;

    public ProductCart(Cart cart,  Product product, int quantity){
        this.id = new ProductCartId(cart.getId(), product.getId());
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "products_id", referencedColumnName = "id")
    private Product product;

    private int quantity;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class ProductCartId implements Serializable {

    private String cartId;
    private String productId;

    // Equals and hashCode (required for composite keys)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCartId that = (ProductCartId) o;
        return Objects.equals(cartId, that.cartId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }
}
