package org.bank.service;

import org.bank.config.DBConfig;
import org.bank.model.Customer;
import org.bank.model.Loan;
import org.bank.model.exception.ValidationException;
import org.bank.repository.DBRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that manages loans by providing functionality to create new loans, pay off loans, and retrieve loans
 */
public class LoanService {
    private final DBRepository<Loan> loanRepository = new DBRepository<>(Loan.class, DBConfig.LOANS_TABLE);

    public LoanService() {
    }

    /**
     * Makes a new loan for a customer and adds it to the customer's loan list
     *
     * @param borrower the customer who is taking out the loan
     * @param loanAmount the amount of the loan
     * @param termMonths the term of the loan in months
     */
    public void getNewLoan(Customer borrower, double loanAmount, int termMonths) {
        if (termMonths < 6 || termMonths > 120) {
            throw new ValidationException("Invalid term Months (6-120).");
        }
        Loan loan = new Loan(borrower, loanAmount, termMonths);
        int id = loanRepository.create(loan);
        loan.setId(id);
        borrower.addLoan(loan);
    }

    /**
     * Used to pay off a loan. If the payment exceeds the loan amount,
     * the loan is paid off and removed from the customer's loan list.
     *
     * @param borrower the customer who is paying off the loan
     * @param loan the loan to be paid off
     * @param payment the amount to be paid off
     */
    public double payLoan(Customer borrower, Loan loan, double payment) {
        double paymentToBeProcessed = Math.min(payment, loan.getLoanAmount());

        double remainingAmount = loan.getLoanAmount() - paymentToBeProcessed;
        loan.setLoanAmount(remainingAmount);
        loanRepository.update(loan);

        if (remainingAmount <= 0) {
            borrower.removeLoan(loan);
            loanRepository.delete(loan.getId());
            System.out.println("Loan fully paid off!");
        } else {
            System.out.println("Payed successfully " + paymentToBeProcessed + ". Remaining: " + remainingAmount);
        }

        return paymentToBeProcessed;
    }


    /**
     * Retrieves all loans associated with a specified customer
     *
     * @param borrower the customer whose loans are to be retrieved
     * @return a list of loans associated with the specified customer
     */
    public List<Loan> getLoans(Customer borrower) {
        borrower.setLoanList(loanRepository.findAll().stream()
                .filter(loan -> loan.getBorrower().getId() == borrower.getId())
                .collect(Collectors.toList()));
        return borrower.getLoanList();
    }

    /**
     * @param loanId the id of the loan to be retrieved
     * @param borrower the customer who owns the loan
     * @return the loan with the specified id, or null if no such loan exists
     */
    public Loan getLoanById(int loanId, Customer borrower) {
        return getLoans(borrower).stream()
                .filter(l -> l.getId() == loanId)
                .findFirst()
                .orElse(null);
    }


    /**
     * Retrieves all loans associated with a specified customer, sorted by loan amount
     *
     * @param borrower the customer whose loans are to be retrieved
     * @return a list of loans associated with the specified customer, sorted by loan amount
     */
    public List<Loan> getLoansSortedByAmount(Customer borrower) {
        List<Loan> loans = getLoans(borrower);
        loans.sort((l1, l2) -> Double.compare(l1.getLoanAmount(), l2.getLoanAmount()));
        return loans;
    }
}
