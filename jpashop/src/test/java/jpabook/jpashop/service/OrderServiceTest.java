package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

//import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired EntityManager em;

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Test
    public void 상품_주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);
        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertEquals( OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상품 상태는 ORDER");
        Assertions.assertEquals( 1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 같아야한다.");
        Assertions.assertEquals( 10000 * orderCount,  getOrder.getTotalPrice(), "주문 가격은 가격*수량이다.");
        Assertions.assertEquals( 8,  book.getStockQuantity(), "주문 수량만큼 재고가 줄어야한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);


        //when
        int orderCount = 11;
        //then
        Assertions.assertThrows(NotEnoughStockException.class, ()->{
            orderService.order(member.getId(), item.getId(), orderCount);
        });


    }
    @Test
    public void 주문_취소() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("Hello JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(getOrder.getStatus(), OrderStatus.CANCEL, "주문 취소 시 상태는 CANCEL.");
        Assertions.assertEquals(10, item.getStockQuantity(), "취소 후 재고가 같아야한다.");

    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울", "역삼로", "12-12"));
        em.persist(member);
        return member;
    }
}