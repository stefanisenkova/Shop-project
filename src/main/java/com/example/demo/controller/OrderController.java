package com.example.demo.controller;

import com.example.demo.Status;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductRepository productRepository;
    List<Order> orders = new ArrayList<>();

    @GetMapping("/orders")
    public String orders(Order order, Model model) {
        orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/order-details/{id}")
    public String orderDetails(@PathVariable int id, Model model) {
        List<Purchase> chosePurchases = purchaseRepository.findByOrderId(id);
        model.addAttribute("chosePurchases", chosePurchases);
        return "order-details";
    }

    @PostMapping("/order-status-update/{id}")
    public String orderUpdate(@PathVariable int id, Order order) {
        order.setId(id);
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == order.getId()) {
                orders.get(i).setStatus(order.getStatus());
            }
            orderRepository.save(orders.get(i));
        }
        return "redirect:/orders";
    }

    @GetMapping("/filter-orders-by-status-new")
    public String filterOrdersByStatusNew(Order order, Model model) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < allOrders.size(); i++) {
            if (allOrders.get(i).getStatus() == Status.NEW) {
                orders.add(allOrders.get(i));
            }
        }
        model.addAttribute("orders", orders);
        return "orders";
    }


    @GetMapping("/filter-orders-by-status-processed")
    public String filterOrdersByStatusProcessed(Order order, Model model) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < allOrders.size(); i++) {
            if (allOrders.get(i).getStatus() == Status.PROCESSED) {
                orders.add(allOrders.get(i));
            }
        }
        model.addAttribute("orders", orders);
        return "orders";
    }


    @GetMapping("/filter-orders-by-status-sent-by-courier")
    public String filterOrdersByStatusSentByCourier(Order order, Model model) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < allOrders.size(); i++) {
            if (allOrders.get(i).getStatus() == Status.SENT_BY_COURIER) {
                orders.add(allOrders.get(i));
            }
        }
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/filter-orders-by-status-completed")
    public String filterOrdersByStatusCompleted(Order order, Model model) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < allOrders.size(); i++) {
            if (allOrders.get(i).getStatus() == Status.COMPLETED) {
                orders.add(allOrders.get(i));
            }
        }
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/buy-something-form")
    public String buySomething(Product product) {
        return "buy-something-form";
    }
    List<Purchase> purchases = new ArrayList<>();
    List<Double> quantity = new ArrayList<>();
    double finalPrice = 0;
    Purchase purchase;
    Product product;

    @PostMapping("/purchase/{id}/{quantity}")
    public String purchase(@RequestParam int id, @RequestParam double quantity, Model model) {
        product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        if (product.getQuantity() < quantity) {
            return "wrong-quantity";
        }

        this.quantity.add(quantity);
        double price = quantity * product.getPrice();
        purchase = new Purchase(product.getName(), product.getPrice(), quantity, price);
        purchase.setProduct(product);
        finalPrice += price;

        purchases.add(purchase);
        model.addAttribute("purchase", purchase);
        return "purchase-success";
    }

    @GetMapping("/stop-shopping")
    public String stopShopping(Order order, Model model) {
        if (purchases.isEmpty()) {
            return "bad-customer";
        }
        for (int i = 0; i < quantity.size(); i++) {
            product.setQuantity(product.getQuantity() - quantity.get(i));
        }
        productRepository.save(product);

        order.setPrice(finalPrice);
        order.setStatus(Status.NEW);
        orderRepository.save(order);
        for (int i = 0; i < purchases.size(); i++) {
            purchases.get(i).setOrder(order);
            purchaseRepository.save(purchases.get(i));
        }
        model.addAttribute("purchases", purchases);
        return "purchases";
    }
}
