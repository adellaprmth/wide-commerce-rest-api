package wide_commerce.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wide_commerce.rest.dto.ProductRequest;
import wide_commerce.rest.dto.ProductResponse;
import wide_commerce.rest.dto.ProductTypeResponse;
import wide_commerce.rest.entity.Product;
import wide_commerce.rest.repository.ProductRepository;
import wide_commerce.rest.repository.ProductTypeRepository;
import wide_commerce.rest.utils.RandomStringGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    private ProductResponse toResponse (Product product){
        var productType = product.getProductType();
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .code(product.getCode())
                .productType(
                        ProductTypeResponse
                                .builder()
                                .id(productType.getId())
                                .name(productType.getName())
                                .build()
                )
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public ProductResponse create(ProductRequest request){
        var productType = productTypeRepository
                .findById(request.getType())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Invalid Product Type Id"
                        )
                );

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(request.getName());
        product.setCode(RandomStringGenerator.generate(10));
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setProductType(productType);

        productRepository.save(product);

        return toResponse(product);
    }

    public ProductResponse findByID(String id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        return toResponse(product);
    }

    public List<ProductResponse> findAll(){
        var products =  productRepository.findAll();

        List<ProductResponse> productResponses = new ArrayList<>();
        for (var p : products) {
            productResponses.add(toResponse(p));
        }
        return productResponses;
    }

    public Page<ProductResponse> findPage(PageRequest pageRequest) {
        Page<Product> productResponses =  productRepository.findAll(pageRequest);
        return productResponses.map(p -> toResponse(p));
    }

    public void deleteByID(String id) {
        productRepository.deleteById(id);
    }

    public void updateByID(String id, ProductRequest request){
        var product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));
        if (Objects.nonNull(request.getName())){
            product.setName(request.getName());
        }

        if (Objects.nonNull(request.getType())){
            var productType = productTypeRepository
                    .findById(request.getType())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Product Type Id"));
            product.setProductType(productType);
        }

        if (Objects.nonNull(request.getPrice())){
            product.setPrice(request.getPrice());
        }

        if (Objects.nonNull(request.getStock())){
            product.setStock(request.getStock());
        }

        productRepository.save(product);

    }
}
