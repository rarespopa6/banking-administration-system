package org.bank.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a bank account which can be owned by one or more customers.
 * This is an abstract class that serves as a base for specific types of accounts.
 */
public abstract class Account implements Identifiable {
    private int id;
    private List<User> customers;
    private double balance;
    private LocalDateTime creationTime;

    /**
     * Constructs an Account instance with specified customers and balance.
     *
     * @param customers the list of users who own the account
     * @param balance   the initial balance of the account
     */
    public Account(List<User> customers, double balance) {
        this.customers = customers;
        this.balance = balance;
        this.creationTime = LocalDateTime.now();
    }

    /**
     * Retrieves the unique identifier of the account.
     *
     * @return the account ID
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the account.
     *
     * @param id the account ID to set
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the list of customers who own the account.
     *
     * @return the list of customers
     */
    public List<User> getCustomers() {
        return customers;
    }

    /**
     * Sets the owners of the account.
     *
     * @param customers the list of users to set as owners
     */
    public void setOwner(List<User> customers) {
        this.customers = customers;
    }

    /**
     * Retrieves the current balance of the account.
     *
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the account.
     *
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Retrieves the creation time of the account.
     *
     * @return the creation time
     */
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the creation time of the account.
     *
     * @param creationTime the creation time to set
     */
    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Returns a string representation of the Account object, including the account ID,
     * list of customers, balance, and creation time.
     *
     * @return a string representation of the account
     */
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + id +
                ", customers=" + customers.toString() +
                ", balance=" + balance +
                ", creationTime=" + creationTime +
                '}';
    }
}