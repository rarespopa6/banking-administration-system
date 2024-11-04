package org.bank.model;

import java.util.List;

/**
 * Represents a checking account within the banking system.
 * A checking account allows for deposits and withdrawals with a transaction fee applied to each transaction.
 */
public class CheckingAccount extends Account {
    private double transactionFee;

    /**
     * Constructs a CheckingAccount instance with specified owners, balance, and transaction fee.
     *
     * @param owners         the list of users who own the account
     * @param balance        the initial balance of the account
     * @param transactionFee the fee charged for each transaction made from the account
     */
    public CheckingAccount(List<User> owners, double balance, double transactionFee) {
        super(owners, balance);
        this.transactionFee = transactionFee;
    }

    /**
     * Retrieves the transaction fee for this checking account.
     *
     * @return the transaction fee
     */
    public double getTransactionFee() {
        return transactionFee;
    }

    /**
     * Sets the transaction fee for this checking account.
     *
     * @param transactionFee the transaction fee to set
     */
    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    /**
     * Returns a string representation of the CheckingAccount object, including details about the account and transaction fee.
     *
     * @return a string representation of the checking account
     */
    @Override
    public String toString() {
        return "CheckingAccount{" +
                super.toString() +
                " ,transactionFee=" + transactionFee +
                '}';
    }
}