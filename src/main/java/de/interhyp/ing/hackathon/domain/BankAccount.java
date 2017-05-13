package de.interhyp.ing.hackathon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BankAccount.
 */
@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bank")
    private String bank;

    @Column(name = "current_amount")
    private Double currentAmount;

    @OneToMany(mappedBy = "bankaccount")
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public BankAccount iban(String iban) {
        this.iban = iban;
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBank() {
        return bank;
    }

    public BankAccount bank(String bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public BankAccount currentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
        return this;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public BankAccount transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public BankAccount addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setBankaccount(this);
        return this;
    }

    public BankAccount removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setBankaccount(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public User getUser() {
        return user;
    }

    public BankAccount user(User user) {
        this.user = user;
        return this;
    }

    public void setUser( User fCUser) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankAccount fCBankAccount = (BankAccount) o;
        if (fCBankAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fCBankAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + getId() +
            ", iban='" + getIban() + "'" +
            ", bank='" + getBank() + "'" +
            ", currentAmount='" + getCurrentAmount() + "'" +
            "}";
    }
}
