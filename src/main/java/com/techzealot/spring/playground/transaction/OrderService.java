package com.techzealot.spring.playground.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private StoreService storeService;

    @Transactional(rollbackFor = Exception.class)
    public void createOrder() {
        //create order
        //update store
        storeService.decreaseStore();
        //AOP失效导致事务失效
        transactionNotWork();
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.NEVER)
    public void transactionNotWork() {

    }
}
