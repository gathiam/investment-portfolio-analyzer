package com.portfolio.model;

import java.time.LocalDateTime;

public class Stock {
    private int stockId;
    private String symbol;
    private String companyName;
    private String sector;
    private double currentPrice;
    private LocalDateTime lastUpdated;

    // Constructor
    public Stock(String symbol, String companyName, String sector, double currentPrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.sector = sector;
        this.currentPrice = currentPrice;
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters and setters
    public int getStockId() { return stockId; }
    public void setStockId(int stockId) { this.stockId = stockId; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
        this.lastUpdated = LocalDateTime.now();
    }
    public LocalDateTime getLastUpdated() { return lastUpdated; }

    @Override
    public String toString() {
        return String.format("Stock[symbol=%s, company=%s, price=%.2f]",
                symbol, companyName, currentPrice);
    }
}
