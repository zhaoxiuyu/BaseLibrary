package com.base.app.module.test;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 测试
 *
 * @author Administrator
 */
public class Test {

    public static void main(final String[] args) {

        ArrayList<Order> orders = getOrderList();

        ArrayList<Order> list = new ArrayList<Order>();

        for (Order order : orders) {
            if (list.size() == 0) {
                list.add(order);
            } else {
                boolean boo = true;
                for (Order newOrder : list) {
                    if (newOrder.getRoomId() == order.getRoomId()) {
                        if (newOrder.getEnd().equals(order.getStart())) {
                            newOrder.setEnd(order.getEnd());
                            newOrder.setDay(newOrder.getDay() + order.getDay());
                            boo = false;
                        } else if (newOrder.getStart().equals(order.getEnd())) {
                            newOrder.setStart(order.getStart());
                            newOrder.setDay(newOrder.getDay() + order.getDay());
                            boo = false;
                        }
                    }
                }
                if (boo) {
                    list.add(order);
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }

    }


    private static ArrayList<Order> getOrderList() {
        ArrayList<Order> orders = new ArrayList<>();

        Order order1 = new Order("2020-08-19", "2020-08-20", 1, "1000");
        Order order2 = new Order("2020-08-20", "2020-08-21", 1, "1000");
        Order order3 = new Order("2020-08-10", "2020-08-11", 1, "2000");
        Order order4 = new Order("2020-08-11", "2020-08-12", 1, "2000");
        Order order5 = new Order("2020-08-25", "2020-08-26", 1, "3000");
        Order order6 = new Order("2020-08-26", "2020-08-27", 1, "3000");
        Order order7 = new Order("2020-08-18", "2020-08-19", 1, "1000");
        Order order8 = new Order("2020-08-05", "2020-08-06", 1, "1000");

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);
        orders.add(order6);
        orders.add(order7);
        orders.add(order8);

        return orders;
    }

}
