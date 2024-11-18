package wide_commerce.rest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wide_commerce.rest.dto.CartResponse;
import wide_commerce.rest.dto.ProductCartResponse;
import wide_commerce.rest.entity.Cart;
import wide_commerce.rest.entity.ProductCart;
import wide_commerce.rest.entity.User;
import wide_commerce.rest.repository.CartRepository;
import wide_commerce.rest.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    private CartResponse toResponse(Cart cart) {

        List<ProductCartResponse> products = new ArrayList<>();

        if (cart.getProductCarts() != null) {
            for (var c : cart.getProductCarts()){
                var product = c.getProduct();
                products.add(new ProductCartResponse(product.getId(), product.getName(), product.getPrice(), c.getQuantity() ));
            }
        }
        return CartResponse
                .builder()
                .id(cart.getId())
                .status(cart.getStatus())
                .products(products)
                .build();
    }

    public CartResponse getActive(User user){
        log.info(user.getId(), user.getName());
        Cart cart = cartRepository.getActive(user.getId())
                .orElseGet(() -> {
                    var c = new Cart();
                    c.setId(UUID.randomUUID().toString());
                    c.setStatus("active");
                    c.setUser(user);

                    cartRepository.save(c);
                    return c;
                });

        return toResponse(cart);
    }

    public CartResponse addProductToCart(User user, String productId, int quantity ) {
        Cart cart = cartRepository.getActive(user.getId())
                .orElseGet(() -> {
                    var c = new Cart();
                    c.setId(UUID.randomUUID().toString());
                    c.setStatus("active");
                    c.setUser(user);

                    cartRepository.save(c);
                    return c;
                });

        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid product id"));

        if (product.getStock() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity is greater than product stock");
        }
        var currentProductCarts = cart.getProductCarts();

        if (currentProductCarts == null) {
            currentProductCarts = new ArrayList<>();
        }


        boolean foundExisting = false;
        for (var p : currentProductCarts) {
            if (!p.getProduct().getId().equals(productId)) {
                continue;
            }

            int newQuantity = p.getQuantity() + quantity;
            if (newQuantity > p.getProduct().getStock()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity is greater than product stock");
            }

            p.setQuantity(newQuantity);
            foundExisting = true;
        }

        if (!foundExisting) currentProductCarts.add(new ProductCart(cart, product, quantity));

        cart.setProductCarts(currentProductCarts);

        cartRepository.save(cart);

        return toResponse(cart);
    }

    public CartResponse removeProductFromCart(User user, String productId) {
        Cart cart = cartRepository.getActive(user.getId())
                .orElseGet(() -> {
                    var c = new Cart();
                    c.setId(UUID.randomUUID().toString());
                    c.setStatus("active");
                    c.setUser(user);

                    cartRepository.save(c);
                    return c;
                });

        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid product id"));
        var currentProductCarts = cart.getProductCarts();


        if (currentProductCarts != null) {
            currentProductCarts.removeIf(productCart -> productCart.getProduct().getId().equals(productId));
        }
        cart.setProductCarts(currentProductCarts);

        cartRepository.save(cart);

        return toResponse(cart);
    }

    public CartResponse updateProductQuantityFromCart(User user, String productId, int quantity ) {
        Cart cart = cartRepository.getActive(user.getId())
                .orElseGet(() -> {
                    var c = new Cart();
                    c.setId(UUID.randomUUID().toString());
                    c.setStatus("active");
                    c.setUser(user);

                    cartRepository.save(c);
                    return c;
                });

        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid product id"));

        if (product.getStock() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity is greater than product stock");
        }
        var currentProductCarts = cart.getProductCarts();

        boolean foundProducts = false;
        if (currentProductCarts != null) {
            for (var c : currentProductCarts) {
                if (!c.getProduct().getId().equals(productId)) {
                    continue;
                }

                foundProducts = true;

                c.setQuantity(quantity);
            }
        }

        if (!foundProducts) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product not found in cart");

        cart.setProductCarts(currentProductCarts);

        cartRepository.save(cart);

        return toResponse(cart);
    }

    public void checkout(User user) {
        var cart = cartRepository.getActive(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Active Cart found"));
        cart.setStatus("completed");

        if (cart.getProductCarts() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty.");

        for (var p: cart.getProductCarts()) {
            var prod = p.getProduct();
            if (prod.getStock() < p.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "out of stock");
            }
            prod.setStock(prod.getStock() - p.getQuantity());
            productRepository.save(prod);
        }

        cartRepository.save(cart);

        // New active cart
        var c = new Cart();
        c.setId(UUID.randomUUID().toString());
        c.setStatus("active");
        c.setUser(user);

        cartRepository.save(c);
    }


}
