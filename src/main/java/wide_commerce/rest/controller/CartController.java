package wide_commerce.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wide_commerce.rest.dto.CartRequest;
import wide_commerce.rest.dto.CartResponse;
import wide_commerce.rest.dto.WebResponse;
import wide_commerce.rest.entity.User;
import wide_commerce.rest.service.CartService;

@RestController
@Slf4j
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping(
            path = "/api/cart",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CartResponse> getUserCart(User user){
        CartResponse cartResponse = cartService.getActive(user);
        return WebResponse.<CartResponse>builder().data(cartResponse).build();
    }

    @PostMapping(
            path = "api/cart",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> addProduct(User user, @RequestBody CartRequest request){
        log.info("Product id {}", request);
        cartService.addProductToCart(user, request.getProductId(), request.getQuantity());
        return WebResponse.<String>builder().data("Successfully Add Product to Cart").build();
    }

    @PatchMapping(
            path = "api/cart/product/{productId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updateQuantity(User user, @PathVariable("productId") String productId, @RequestBody CartRequest request){
        cartService.updateProductQuantityFromCart(user, productId, request.getQuantity());
        return WebResponse.<String>builder().data("Successfully Update Quantity Product").build();
    }

    @DeleteMapping(
            path = "api/cart/product/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteProduct(User user, @PathVariable("productId") String productId){
        cartService.removeProductFromCart(user,productId);
        return WebResponse.<String>builder().data("Successfully Delete Product").build();
    }

    @GetMapping(
            path = "api/cart/checkout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> checkout(User user){
        cartService.checkout(user);
        return WebResponse.<String>builder().data("Successfully Checkout").build();
    }
}
