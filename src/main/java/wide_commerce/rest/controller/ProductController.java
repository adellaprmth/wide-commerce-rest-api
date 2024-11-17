package wide_commerce.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wide_commerce.rest.dto.PageResponse;
import wide_commerce.rest.dto.ProductRequest;
import wide_commerce.rest.dto.ProductResponse;
import wide_commerce.rest.dto.WebResponse;
import wide_commerce.rest.service.ProductService;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(
            path = "api/products",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> create(@RequestBody ProductRequest request){
        ProductResponse productResponse = productService.create(request);
        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }

    @GetMapping(
            path = "api/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PageResponse<ProductResponse> getAll(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "20") Integer pageSize
            ){
        Page<ProductResponse> productResponse = productService.findPage(PageRequest.of(pageNumber - 1 , pageSize));
        return PageResponse.fromPage(productResponse);
    }

    @GetMapping(
            path = "api/products/{productsId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> getByID(@PathVariable("productsId") String productsId){
        ProductResponse productResponse = productService.findByID(productsId);
        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }

    @PatchMapping(
            path = "api/products/{productsId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> update(@PathVariable("productsId") String productsId, @RequestBody ProductRequest request){
        productService.updateByID(productsId, request);
        ProductResponse byID = productService.findByID(productsId);
        return WebResponse.<ProductResponse>builder().data(byID).build();
    }

    @DeleteMapping(
            path = "api/products/{productsId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("productsId") String productsId){
        productService.deleteByID(productsId);
        return WebResponse.<String>builder().data("Successfully Removed").build();
    }

}
