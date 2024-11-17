package wide_commerce.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wide_commerce.rest.dto.ProductTypeRequest;
import wide_commerce.rest.dto.ProductTypeResponse;
import wide_commerce.rest.entity.ProductType;
import wide_commerce.rest.repository.ProductTypeRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    private ProductTypeResponse toResponse(ProductType productType){
        return ProductTypeResponse.builder()
                .id(productType.getId())
                .name(productType.getName())
                .build();
    }

    public ProductTypeResponse create(ProductTypeRequest request){
        ProductType productType = new ProductType();
        productType.setId(UUID.randomUUID().toString());
        productType.setName(request.getName());

        productType = productTypeRepository.save(productType);

        return toResponse(productType);
    }

    public ProductTypeResponse findByID(String id) {
        ProductType productType = productTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type Not Found"));

        return toResponse(productType);
    }

    public List<ProductTypeResponse> findAll(){
        var productTypes =  productTypeRepository.findAll();
        return productTypes
                .stream()
                .map(
                        productType ->
                                new ProductTypeResponse(productType.getId(), productType.getName()))
                .toList();
    }

    public void deleteByID(String id) {
        productTypeRepository.deleteById(id);
    }

    public void updateByID(String id, ProductTypeRequest request){
        var productType= productTypeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Type Not Found"));

        if (request.getName() != null) {
            productType.setName(request.getName());
        }

        productTypeRepository.save(productType);

    }
}
