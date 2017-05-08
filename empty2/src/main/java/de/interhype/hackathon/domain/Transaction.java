package de.interhype.hackathon.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import de.interhype.hackathon.domain.enumeration.Currency;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "trx_date")
    private ZonedDateTime trxDate;

    @Column(name = "booking_date")
    private ZonedDateTime bookingDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "original_amount")
    private Double originalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "original_currency")
    private Currency originalCurrency;

    @Column(name = "account_name")
    private String accountName;

    @ManyToOne
    private BankAccount account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTrxDate() {
        return trxDate;
    }

    public Transaction trxDate(ZonedDateTime trxDate) {
        this.trxDate = trxDate;
        return this;
    }

    public void setTrxDate(ZonedDateTime trxDate) {
        this.trxDate = trxDate;
    }

    public ZonedDateTime getBookingDate() {
        return bookingDate;
    }

    public Transaction bookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public void setBookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Double getAmount() {
        return amount;
    }

    public Transaction amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getOriginalAmount() {
        return originalAmount;
    }

    public Transaction originalAmount(Double originalAmount) {
        this.originalAmount = originalAmount;
        return this;
    }

    public void setOriginalAmount(Double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Currency getOriginalCurrency() {
        return originalCurrency;
    }

    public Transaction originalCurrency(Currency originalCurrency) {
        this.originalCurrency = originalCurrency;
        return this;
    }

    public void setOriginalCurrency(Currency originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public String getAccountName() {
        return accountName;
    }

    public Transaction accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BankAccount getAccount() {
        return account;
    }

    public Transaction account(BankAccount bankAccount) {
        this.account = bankAccount;
        return this;
    }

    public void setAccount(BankAccount bankAccount) {
        this.account = bankAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transaction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + id +
            ", trxDate='" + trxDate + "'" +
            ", bookingDate='" + bookingDate + "'" +
            ", amount='" + amount + "'" +
            ", originalAmount='" + originalAmount + "'" +
            ", originalCurrency='" + originalCurrency + "'" +
            ", accountName='" + accountName + "'" +
            '}';
    }
}
