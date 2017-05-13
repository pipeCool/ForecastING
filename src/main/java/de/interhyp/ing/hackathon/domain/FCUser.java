package de.interhyp.ing.hackathon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FCUser.
 */
@Entity
@Table(name = "fc_user")
public class FCUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<FCBankAccount> bankaccounts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FCUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public FCUser login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<FCBankAccount> getBankaccounts() {
        return bankaccounts;
    }

    public FCUser bankaccounts(Set<FCBankAccount> fCBankAccounts) {
        this.bankaccounts = fCBankAccounts;
        return this;
    }

    public FCUser addBankaccount(FCBankAccount fCBankAccount) {
        this.bankaccounts.add(fCBankAccount);
        fCBankAccount.setUser(this);
        return this;
    }

    public FCUser removeBankaccount(FCBankAccount fCBankAccount) {
        this.bankaccounts.remove(fCBankAccount);
        fCBankAccount.setUser(null);
        return this;
    }

    public void setBankaccounts(Set<FCBankAccount> fCBankAccounts) {
        this.bankaccounts = fCBankAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FCUser fCUser = (FCUser) o;
        if (fCUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fCUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FCUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", login='" + getLogin() + "'" +
            "}";
    }
}
