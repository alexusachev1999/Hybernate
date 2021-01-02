package ru.levelup.junior.dao;

import ru.levelup.entities.Account;

import javax.persistence.EntityManager;

public class AccountsDAO {

    private final EntityManager manager;

    public AccountsDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void create(Account account){
        manager.persist(account);
    }

    public Account findByLogin(String login){

        return manager.createQuery(
                "from Account where login = :p", Account.class)
                .setParameter("p", login)
                .getSingleResult();
    }
    public Account findByLoginAndPassword(String login,String password){

        return manager.createQuery(
                "from Account where login = :login and password = : password", Account.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
    }

}
