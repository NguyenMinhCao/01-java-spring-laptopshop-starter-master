package vn.hoidanit.laptopshop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailUpdateRequest {
    private Long cartDetailId;
    private Integer quantity;
}
