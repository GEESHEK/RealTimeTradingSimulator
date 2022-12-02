package com.example.CapstoneProjectFile.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "UserAccount")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private double balance;
    @OneToMany(
            mappedBy = "userAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties("userAccount")
    private List<BoughtStocks> boughtStocks = new ArrayList<>();

    @OneToMany(
            mappedBy = "userAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties("userAccount")
    private List<StockHistory> stockHistory = new ArrayList<>();

    public UserAccount() {
    }

    public UserAccount(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<BoughtStocks> getBoughtStocks() {
        return boughtStocks;
    }

    public void setBoughtStocks(List<BoughtStocks> boughtStocks) {
        this.boughtStocks.addAll(boughtStocks);
    }

    public List<StockHistory> getStockHistory() {
        return stockHistory;
    }

    public void setStockHistory(List<StockHistory> stockHistory) {
        this.stockHistory.addAll(stockHistory);
    }
}
