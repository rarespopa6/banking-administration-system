package org.bank.controller;

import org.bank.model.Account;
import org.bank.model.Customer;
import org.bank.model.Loan;
import org.bank.model.User;
import org.bank.service.AccountService;
import org.bank.service.LoanService;
import org.bank.service.UserService;

import java.util.List;

/**
 * Controller class that acts as an intermediary between the user interface and the underlying business logic.
 * This class handles user-related and account-related operations.
 */
public class AppController {
    private final UserService userService = new UserService();
    private final AccountService accountService = new AccountService();
    private final LoanService loanService = new LoanService();

    /**
     * Creates a new customer and returns their unique identifier.
     *
     * @param firstName   the first name of the customer
     * @param lastName    the last name of the customer
     * @param email       the email address of the customer
     * @param phoneNumber the phone number of the customer
     * @param password    the password for the customer account
     * @return the unique identifier of the newly created customer
     */
    public int createCustomer(String firstName, String lastName, String email, String phoneNumber, String password) {
        return userService.createCustomer(firstName, lastName, email, phoneNumber, password);
    }

    /**
     * Retrieves a user based on their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the User object if found, null otherwise
     */
    public User readUser(int id) {
        return userService.readUser(id);
    }

    /**
     * Retrieves a user based on their email address.
     *
     * @param email the email address of the user
     * @return the User object if found, null otherwise
     */
    public User readUser(String email) {
        return userService.getUserByEmail(email);
    }

    /**
     * Updates the details of an existing user.
     *
     * @param id          the unique identifier of the user to update
     * @param firstName   the new first name of the user
     * @param lastName    the new last name of the user
     * @param email       the new email address of the user
     * @param phoneNumber the new phone number of the user
     * @param password    the new password for the user account
     */
    public void updateUser(int id, String firstName, String lastName, String email, String phoneNumber, String password) {
        userService.updateUser(id, firstName, lastName, email, phoneNumber, password);
    }

    /**
     * Deletes a user based on their unique identifier.
     *
     * @param id the unique identifier of the user to delete
     */
    public void deleteUser(int id) {
        userService.deleteUser(id);
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * @return a list of User objects
     */
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Retrieves a list of accounts associated with a specific customer.
     *
     * @param customerId the unique identifier of the customer
     * @return a list of Account objects associated with the customer
     */
    public List<Account> getAccountsForCustomer(int customerId) {
        return accountService.getAccountsForCustomer(customerId);
    }

    /**
     * Creates a checking account for a specific customer.
     *
     * @param customerId     the unique identifier of the customer
     * @param initialDeposit the initial deposit for the checking account
     * @return the newly created CheckingAccount object
     */
    public Account createCheckingAccount(int customerId, double initialDeposit) {
        return accountService.createCheckingAccount(List.of(readUser(customerId)), initialDeposit);
    }

    /**
     * Creates a savings account for a specific customer.
     *
     * @param customerId     the unique identifier of the customer
     * @param initialDeposit the initial deposit for the savings account
     * @return the newly created SavingsAccount object
     */
    public Account createSavingsAccount(int customerId, double initialDeposit) {
        return accountService.createSavingsAccount(List.of(readUser(customerId)), initialDeposit);
    }

    /**
     * Closes an account for a specific customer.
     *
     * @param customerId the unique identifier of the customer
     * @param accountId  the unique identifier of the account to close
     */
    public void closeAccount(int customerId, int accountId) {
        accountService.closeAccountForCustomer(customerId, accountId);
    }

    /**
     * Makes a new loan for a customer and credits the loan amount to the specified account.
     *
     * @param borrower the customer who is taking out the loan
     * @param account the account to which the loan amount will be credited
     * @param amount the amount of the loan
     * @param termMonths the term of the loan in months
     */
    public void getLoan(Customer borrower, Account account, double amount, int termMonths) {
        this.loanService.getNewLoan(borrower, amount, termMonths);
        this.accountService.addBalance(account, amount);
    }

    /**
     * Pays off a loan for a customer and debits the payment amount from the specified account.
     *
     * @param loanId the id of the loan to be paid
     * @param borrower the customer who is paying off the loan
     * @param account the account from which the payment will be deducted
     * @param paymentAmount the amount to be paid off
     */
    public void payLoan(int loanId, Customer borrower, Account account, double paymentAmount) {
        Loan loan = this.loanService.getLoans(borrower).stream()
                .filter(l -> l.getId() == loanId)
                .findFirst()
                .orElse(null);

        if (loan != null) {
            this.loanService.payLoan(borrower, loan, paymentAmount);

            try {
                this.accountService.subtractBalance(account, paymentAmount);
            } catch (Exception e) {
                throw e;
            }
        }
        else System.out.println("Loan not found.");
    }

    /**
     * Retrieves all loans associated with a specified customer.
     *
     * @param borrower the customer whose loans are to be retrieved
     * @return a list of loans associated with the specified customer
     */
    public List<Loan> viewLoansStatus(Customer borrower) {
        return this.loanService.getLoans(borrower);
    }
}
