package recipes.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdResponse {
    private Long id;

    public IdResponse(Long id) {
        this.id = id;
    }
}
