package ru.levelup.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private long id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column
    private double amount;

    @ManyToOne (optional = false)
    private Account origin;

    @ManyToOne(optional = false)
    private Account receiver;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Transaction(Date time, double amount, Account origin, Account receiver) {
        this.time = time;
        this.amount = amount;
        this.origin = origin;
        this.receiver = receiver;
    }
    public Transaction(){}

}
