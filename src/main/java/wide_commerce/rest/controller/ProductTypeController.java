package wide_commerce.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wide_commerce.rest.dto.ProductResponse;
import wide_commerce.rest.dto.ProductTypeRequest;
import wide_commerce.rest.dto.ProductTypeResponse;
import wide_commerce.rest.dto.WebResponse;
import wide_commerce.rest.service.ProductTypeService;

import java.util.List;

@RestController
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @PostMapping(
            path = "api/product_types",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductTypeResponse> create(@RequestBody ProductTypeRequest request){
        ProductTypeResponse productTypeResponse = productTypeService.create(request);
        return WebResponse.<ProductTypeResponse>builder().data(productTypeResponse).build();
    }

    @GetMapping(
            path = "api/product_types",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ProductTypeResponse>> getAll(){
        List<ProductTypeResponse> productTypeResponse = productTypeService.findAll();
        return WebResponse.<List<ProductTypeResponse>>builder().data(productTypeResponse).build();
    }

    @GetMapping(
            path = "api/product_types/{productTypesId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductTypeResponse> getById(@PathVariable("productTypesId") String productsTypesId){
        ProductTypeResponse productTypeResponse = productTypeService.findByID(productsTypesId);
        return WebResponse.<ProductTypeResponse>builder().data(productTypeResponse).build();
    }

    @PatchMapping(
            path = "api/product_types/{productTypesId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductTypeResponse> update(@PathVariable("productTypesId") String productsTypesId, @RequestBody ProductTypeRequest request){
        productTypeService.updateByID(productsTypesId, request);
        ProductTypeResponse byID = productTypeService.findByID(productsTypesId);
        return WebResponse.<ProductTypeResponse>builder().data(byID).build();
    }

    @DeleteMapping(
            path = "api/product_types/{productTypesId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("productTypesId") String productTypesId){
        productTypeService.deleteByID(productTypesId);
        return WebResponse.<String>builder().data("Successfully Removed").build();
    }
}
