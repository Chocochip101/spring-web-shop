package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * XtoOne(ManytoOne, OnetoOne)
 * Order
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleController {
    private final OrderRepository orderRepository;
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
//        Loop
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }
}
