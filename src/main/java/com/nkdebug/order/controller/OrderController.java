package com.nkdebug.order.controller;


import com.nkdebug.order.common.Payment;
import com.nkdebug.order.common.TransactionRequest;
import com.nkdebug.order.common.TransactionResponse;
import com.nkdebug.order.entity.Order;
import com.nkdebug.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest transactionRequest) {
        return orderService.saveOrder(transactionRequest);

    }
}
