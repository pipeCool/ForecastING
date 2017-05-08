package de.interhype.hackathon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MainAccount.
 */
@Entity
@Table(name = "main_account")
public class MainAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "account_name")
    private String accountName;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private Set<BankAccount> ktos = new HashSet<>();

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private Set<AccountHolder> holders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public MainAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Set<BankAccount> getKtos() {
        return ktos;
    }

    public MainAccount ktos(Set<BankAccount> bankAccounts) {
        this.ktos = bankAccounts;
        return this;
    }

    public MainAccount addKto(BankAccount bankAccount) {
        this.ktos.add(bankAccount);
        bankAccount.setAccount(this);
        return this;
    }

    public MainAccount removeKto(BankAccount bankAccount) {
        this.ktos.remove(bankAccount);
        bankAccount.setAccount(null);
        return this;
    }

    public void setKtos(Set<BankAccount> bankAccounts) {
        this.ktos = bankAccounts;
    }

    public Set<AccountHolder> getHolders() {
        return holders;
    }

    public MainAccount holders(Set<AccountHolder> accountHolders) {
        this.holders = accountHolders;
        return this;
    }

    public MainAccount addHolder(AccountHolder accountHolder) {
        this.holders.add(accountHolder);
        accountHolder.setAccount(this);
        return this;
    }

    public MainAccount removeHolder(AccountHolder accountHolder) {
        this.holders.remove(accountHolder);
        accountHolder.setAccount(null);
        return this;
    }

    public void setHolders(Set<AccountHolder> accountHolders) {
        this.holders = accountHolders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MainAccount mainAccount = (MainAccount) o;
        if (mainAccount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mainAccount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MainAccount{" +
            "id=" + id +
            ", accountName='" + accountName + "'" +
            '}';
    }
}
