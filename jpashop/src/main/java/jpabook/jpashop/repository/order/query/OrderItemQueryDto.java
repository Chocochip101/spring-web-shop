package jpabook.jpashop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderItemQueryDto {
    @JsonIgnore
    private Long orderId;
    private String itemName;
    private int price;
    private int count;

    public OrderItemQueryDto(Long orderId, String itemName, int price, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
    }
}
