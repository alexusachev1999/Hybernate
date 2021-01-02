package ru.levelup.junior.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.levelup.entities.Account;
import ru.levelup.entities.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class TransactionDAOTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private TransactionDAO dao;

    @Before
    public void setup(){
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
        dao = new TransactionDAO(manager);
    }

    @After
    public void cleanup(){
        if (manager != null){
            manager.close();
        }
        if (factory != null){
            factory.close();
        }
    }


    @Test
    public void create() {
        Account origin = new Account("test1","123");
        Account receiver = new Account("test2","234");

        Transaction tx = new Transaction(new Date(),1,origin,receiver);

        manager.getTransaction().begin();
        try {
            manager.persist(origin);
            manager.persist(receiver);

            dao.create(tx);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Assert.assertNotNull(manager.find(Transaction.class, tx.getId()));

    }

    @Test
    public void findByOriginAccount() {
        Account origin = new Account("test1","123");
        Account receiver = new Account("test2","234");

        Transaction tx = new Transaction(new Date(),1,origin,receiver);

        manager.getTransaction().begin();
        try {
            manager.persist(origin);
            manager.persist(receiver);

            dao.create(tx);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Assert.assertEquals(tx.getId(), dao.findByOriginAccount(origin).get(0).getId());
        Assert.assertEquals(0, dao.findByOriginAccount(receiver).size());
    }

    @Test
    public void findByReceiverAccount() {
        Account origin = new Account("test1","123");
        Account receiver = new Account("test2","234");

        Transaction tx = new Transaction(new Date(),1,origin,receiver);

        manager.getTransaction().begin();
        try {
            manager.persist(origin);
            manager.persist(receiver);

            dao.create(tx);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Assert.assertEquals(tx.getId(), dao.findByReceiverAccount(receiver).get(0).getId());
        Assert.assertEquals(0, dao.findByReceiverAccount(origin).size());
    }

    @Test
    public void findByAccount() {
        Account origin = new Account("test1","123");
        Account receiver = new Account("test2","234");

        Transaction tx = new Transaction(new Date(),1,origin,receiver);

        manager.getTransaction().begin();
        try {
            manager.persist(origin);
            manager.persist(receiver);

            dao.create(tx);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Assert.assertEquals(tx.getId(), dao.findByAccount(origin).get(0).getId());
        Assert.assertEquals(tx.getId(), dao.findByAccount(receiver).get(0).getId());
    }
}