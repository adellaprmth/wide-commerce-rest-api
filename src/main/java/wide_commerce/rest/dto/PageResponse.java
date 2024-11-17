package wide_commerce.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    public static <T> PageResponse<T> fromPage(Page<T> response) {
        return PageResponse
                .<T>builder()
                .pageNumber(response.getNumber() + 1)
                .pageSize(response.getSize())
                .totalPages(response.getTotalPages())
                .data(response.getContent())
                .build();
    }
    private List<T> data;

    private int pageNumber;
    private int pageSize;
    private int totalPages;
}
