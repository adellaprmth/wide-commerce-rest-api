package wide_commerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String id;

    private String name;

    private String code;

    private Long price;

    private Integer stock;

    private ProductTypeResponse productType;
}
