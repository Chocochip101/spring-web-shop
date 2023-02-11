package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

         public void dbInit1(){
             Member member = getMember("user1", "Seoul", "Gangnam", "11111");
             em.persist(member);

             Book book1 = getBook("JPA1 book",10000, 100);
             em.persist(book1);

             Book book2 = getBook("JPA2 book", 10000, 200);
             em.persist(book2);

             OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
             OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
             Delivery delivery = getDelivery(member);
             Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
             em.persist(order);

         }


        public void dbInit2(){
            Member member = getMember("user2", "Jinju", "Jinjuro", "22222");
            em.persist(member);

            Book book1 = getBook("Spring1 book",20000, 200);
            em.persist(book1);

            Book book2 = getBook("Spring2 book", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
            Delivery delivery =getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }
        public void dbInit3(){
            Member member = getMember("user3", "NewYork", "NewYork", "3333");
            em.persist(member);

            Book book1 = getBook("Spring1 book",20000, 200);
            em.persist(book1);

            Book book2 = getBook("Spring2 book", 40000, 300);
            em.persist(book2);

            Book book3 = getBook("Spring3 book", 40000, 300);
            em.persist(book3);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
            OrderItem orderItem3 = OrderItem.createOrderItem(book3, 40000, 4);
            Delivery delivery =getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2, orderItem3);
            em.persist(order);

        }
        private static Book getBook(String bookName, int price, int sq) {
            Book book1 = new Book();
            book1.setName(bookName);
            book1.setPrice(price);
            book1.setStockQuantity(sq);
            return book1;
        }

        private static Member getMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private static Delivery getDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}

