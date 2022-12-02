package com.example.CapstoneProjectFile.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "BoughtStocks")
public class BoughtStocks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ticker;
    private int unitsOfStock;
    private BigDecimal price;
    private LocalDateTime timeOfPurchased;

    @ManyToOne
    @JoinColumn(name = "userAccountId")
    @JsonIgnoreProperties("broughtStocks")
    private UserAccount userAccount;

    public BoughtStocks() {
    }

    public BoughtStocks(String ticker, int unitsOfStock, BigDecimal price, LocalDateTime timeOfPurchased, UserAccount userAccount) {
        this.ticker = ticker;
        this.unitsOfStock = unitsOfStock;
        this.price = price;
        this.timeOfPurchased = timeOfPurchased;
        this.userAccount = userAccount;
    }

    public Long getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getUnitsOfStock() {
        return unitsOfStock;
    }

    public void setUnitsOfStock(int unitsOfStock) {
        this.unitsOfStock = unitsOfStock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTimeOfPurchased() {
        return timeOfPurchased;
    }

    public void setTimeOfPurchased(LocalDateTime timeOfPurchased) {
        this.timeOfPurchased = timeOfPurchased;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
