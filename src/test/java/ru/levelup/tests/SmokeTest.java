package ru.levelup.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.levelup.entities.Account;
import ru.levelup.entities.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class SmokeTest {
    private EntityManagerFactory factory;
    private EntityManager manager;

    @Before
    public void setup(){
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
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
    public void testCreateAccount(){
        Account account = new Account();
        account.setLogin("test");
        account.setPassword("123");

        manager.getTransaction().begin();

        try {
            manager.persist(account);
            manager.getTransaction().commit();
        } catch (Exception e){
            manager.getTransaction().rollback();
        }

        Assert.assertNotNull(manager.find(Account.class, account.getId()));
    }

    @Test
    public void queryAccount(){
        Account account = new Account();
        account.setLogin("test");
        account.setPassword("123");

        manager.getTransaction().begin();

        try {
            manager.persist(account);
            manager.getTransaction().commit();
        } catch (Exception e){
            manager.getTransaction().rollback();
        }

        Account found = manager.createQuery("from Account where login = :p", Account.class).setParameter("p","test")
                .getSingleResult();

        Assert.assertEquals(account.getId(),found.getId());
        Assert.assertEquals("123",found.getPassword());
    }

    @Test
    public void queryTransaction(){
        Account account = new Account();
        account.setLogin("test");
        account.setPassword("123");

        Account receiver = new Account();
        receiver.setLogin("another");
        receiver.setPassword("456");


        Transaction tx = new Transaction();
        tx.setAmount(10);
        tx.setOrigin(account);
        tx.setReceiver(receiver);
        tx.setTime(new Date());

        manager.getTransaction().begin();

        try {
            manager.persist(account);
            manager.persist(receiver);
            manager.persist(tx);

            manager.getTransaction().commit();
        } catch (Exception e){
            manager.getTransaction().rollback();
        }

        List<Transaction> found = manager.createQuery("from Transaction t where t.origin.login =:p", Transaction.class)
                .setParameter("p","test")
                .getResultList();

        Assert.assertEquals(1,found.size());
    }

    @Test
    public void criteriaBuilder(){
        Account account = new Account();
        account.setLogin("test");
        account.setPassword("123");

        manager.getTransaction().begin();

        try {
            manager.persist(account);
            manager.getTransaction().commit();
        } catch (Exception e){
            manager.getTransaction().rollback();
        }

        CriteriaBuilder builder = factory.getCriteriaBuilder();
        CriteriaQuery <Account> query = builder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.where(builder.equal(root.get("login"),"test"));

        Account found = manager.createQuery(query).getSingleResult();
        Assert.assertEquals(account.getId(),found.getId());
    }
}
