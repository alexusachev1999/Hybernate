package ru.levelup.junior.dao;

import ru.levelup.entities.Account;
import ru.levelup.entities.Transaction;

import javax.persistence.EntityManager;
import java.util.List;

public class TransactionDAO {
    private final EntityManager manager;

    public TransactionDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void create(Transaction transaction){
        if (transaction.getOrigin()==transaction.getReceiver()) {
            throw new IllegalArgumentException("Transaction origin and receiver the same");
        }
        if (transaction.getAmount()<=0){
            throw new IllegalArgumentException("Transaction amount is negative");
        }
        manager.persist(transaction);
    }

    public List <Transaction> findByOriginAccount (Account origin){
        return manager.createQuery("from Transaction where origin.id = :p", Transaction.class)
                .setParameter("p",origin.getId())
                .getResultList();
    }
    public List <Transaction> findByReceiverAccount (Account receiver){
        return manager.createQuery("from Transaction where receiver.id = :p", ru.levelup.entities.Transaction.class)
                .setParameter("p",receiver.getId())
                .getResultList();
    }
    public List <Transaction> findByAccount (Account account){
        return manager.createQuery("from Transaction where origin.id = :p or receiver.id = :p", ru.levelup.entities.Transaction.class)
                .setParameter("p",account.getId())
                .getResultList();
    }


}
