package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.domain.common.jpa.BaseRepository;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatisticRepository extends BaseRepository<OrderEntity, UUID> {

    @Query(
            value =
                    "select count(id) from BOOK_ORDER where month(order_time) =1 and customer_id= :customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =2 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =3 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =4 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =5 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =6 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =7 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =8 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =9 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =10 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =11 and customer_id=:customerId\n" +
                            "union all\n" +
                            "select count(id) from BOOK_ORDER where month(order_time) =12 and customer_id=:customerId",
            nativeQuery = true)
    List<Integer> getMonthlyTotalOrderCount(@Param("customerId") String customerId);


    @Query(
            value = "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =1 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =2 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =3 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =4 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =5 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =6 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =7 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =8 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =9 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =10 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =11 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =12 and customer_id=:customerId)",
            nativeQuery = true)
    List<Integer> getMonthlyTotalOrderedBookCount(@Param("customerId") String customerId);


    @Query(
            value = "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =1 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =2 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =3 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =4 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =5 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =6 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =7 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =8 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =9 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =10 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =11 and customer_id=:customerId)\n" +
                    "union all\n" +
                    "select COALESCE(SUM(sale_price*quantity), 0) from BOOK_ORDER_ITEMS where book_order_id in (select id from BOOK_ORDER where month(order_time) =12 and customer_id=:customerId)\n",
            nativeQuery = true)
    List<Integer> getMonthlyTotalPurchasedAmount(@Param("customerId") String customerId);

}
