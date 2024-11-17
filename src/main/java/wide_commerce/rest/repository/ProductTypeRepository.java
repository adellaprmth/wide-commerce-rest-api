package wide_commerce.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wide_commerce.rest.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, String> {
}
