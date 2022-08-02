package com.nkdebug.order.service;

import com.nkdebug.order.common.Payment;
import com.nkdebug.order.common.TransactionRequest;
import com.nkdebug.order.common.TransactionResponse;
import com.nkdebug.order.entity.Order;
import com.nkdebug.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public TransactionResponse saveOrder(TransactionRequest transactionRequest) {


        String response = "";
        Order order = transactionRequest.getOrder();

        Payment payment = transactionRequest.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        // do a rest call to payment microservice and pass order id

         Payment paymentResponse = restTemplate.
                postForObject("http://localhost:8082/payment/doPayment",payment,
                        Payment.class);
        response = (paymentResponse.getPaymentStatus().equals("success")?
                 "payment processing is successfull & order is placed": "there is a failure in payment api, order added to cart");

         orderRepository.save(order);

         return new TransactionResponse(order, paymentResponse.getAmount(),
                 paymentResponse.getTransactionId(), response);
    }
}
