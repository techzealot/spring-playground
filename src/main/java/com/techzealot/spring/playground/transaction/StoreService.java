package com.techzealot.spring.playground.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class StoreService {

    /**
     * TransactionDefinition的一种实现
     */
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PlatformTransactionManager txManager;


    public Storage findFromMaster() {
        return transactionTemplate.execute((status) -> {
            //do business
            return new Storage(11L);
        });
    }

    public void decreaseStore() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = txManager.getTransaction(definition);
        try {
            //do business
        } catch (Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
        txManager.commit(status);
    }

    public void increaseStore() {
        TransactionStatus status = txManager.getTransaction(transactionTemplate);
        try {
            //do business
        } catch (Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
        txManager.commit(status);
    }

    public record Storage(Long id) {

    }
}
