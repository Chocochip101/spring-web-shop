package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDTO> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDTO> result = orders.stream()
                .map(o -> new SimpleOrderDTO(o))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDTO> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDTO> result = orders.stream().map(o -> new SimpleOrderDTO(o))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDTO> ordersV4(){
        return orderRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDTO{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;

        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDTO(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
