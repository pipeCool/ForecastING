package de.interhype.hackathon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import de.interhype.hackathon.domain.enumeration.Currency;

import de.interhype.hackathon.domain.enumeration.AccountType;

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

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "fixed_access_date")
    private ZonedDateTime fixedAccessDate;

    @Column(name = "access_delay")
    private Long accessDelay;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private AccountType type;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne
    private MainAccount account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public BankAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public ZonedDateTime getFixedAccessDate() {
        return fixedAccessDate;
    }

    public BankAccount fixedAccessDate(ZonedDateTime fixedAccessDate) {
        this.fixedAccessDate = fixedAccessDate;
        return this;
    }

    public void setFixedAccessDate(ZonedDateTime fixedAccessDate) {
        this.fixedAccessDate = fixedAccessDate;
    }

    public Long getAccessDelay() {
        return accessDelay;
    }

    public BankAccount accessDelay(Long accessDelay) {
        this.accessDelay = accessDelay;
        return this;
    }

    public void setAccessDelay(Long accessDelay) {
        this.accessDelay = accessDelay;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BankAccount currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public AccountType getType() {
        return type;
    }

    public BankAccount type(AccountType type) {
        this.type = type;
        return this;
    }

    public void setType(AccountType type) {
        this.type = type;
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
        transaction.setAccount(this);
        return this;
    }

    public BankAccount removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setAccount(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public MainAccount getAccount() {
        return account;
    }

    public BankAccount account(MainAccount mainAccount) {
        this.account = mainAccount;
        return this;
    }

    public void setAccount(MainAccount mainAccount) {
        this.account = mainAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankAccount bankAccount = (BankAccount) o;
        if (bankAccount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bankAccount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + id +
            ", accountName='" + accountName + "'" +
            ", fixedAccessDate='" + fixedAccessDate + "'" +
            ", accessDelay='" + accessDelay + "'" +
            ", currency='" + currency + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
