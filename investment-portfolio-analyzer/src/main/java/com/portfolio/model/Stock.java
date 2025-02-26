package com.portfolio.model;

import java.time.LocalDateTime;

/**
 * This class represents a stock in an investment portfolio.
 * @author Gaoussou Thiam
 * @date 02/17/2026
 * @see com.portfolio.model.Portfolio
 * @see com.portfolio.model.Position
 * @see com.portfolio.model.Transaction
 */
public class Stock {


    /**
     * Unique identifier for the stock.
     */
    private int stockId;

    /**
     * Ticker symbol of the stock.
     */
    private String symbol;

    /**
     * Name of the company associated with the stock.
     */
    private String companyName;

    /**
     * Industry sector of the company.
     */
    private String sector;

    /**
     * Current price of the stock.
     */
    private double currentPrice;

    /**
     * Timestamp of the last price update.
     */
    private LocalDateTime lastUpdated;


    /**
     * Constructs a new Stock instance with the specified details.
     *
     * @param symbol       The ticker symbol of the stock.
     * @param companyName  The name of the company.
     * @param sector       The industry sector of the company.
     * @param currentPrice The current price of the stock.
     */
    public Stock(String symbol, String companyName, String sector, double currentPrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.sector = sector;
        this.currentPrice = currentPrice;
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters and setters
    /**
     * Gets the stock ID.
     *
     * @return The unique identifier of the stock.
     */
    public int getStockId() { return stockId; }

    /**
     * Sets the stock ID.
     *
     * @param stockId The unique identifier to set.
     */
    public void setStockId(int stockId) { this.stockId = stockId; }

    /**
     * Gets the stock's ticker symbol.
     *
     * @return The ticker symbol.
     */
    public String getSymbol() { return symbol; }

    /**
     * Sets the stock's ticker symbol.
     *
     * @param symbol The new ticker symbol.
     */
    public void setSymbol(String symbol) { this.symbol = symbol; }

    /**
     * Gets the company's name.
     *
     * @return The name of the company.
     */
    public String getCompanyName() { return companyName; }

    /**
     * Sets the company's name.
     *
     * @param companyName The new name of the company.
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    /**
     * Gets the industry sector of the company.
     *
     * @return The industry sector.
     */
    public String getSector() { return sector; }

    /**
     * Sets the industry sector of the company.
     *
     * @param sector The new industry sector.
     */
    public void setSector(String sector) { this.sector = sector; }

    /**
     * Gets the current stock price.
     *
     * @return The current price of the stock.
     */
    public double getCurrentPrice() { return currentPrice; }

    /**
     * Sets the current stock price and updates the last updated timestamp.
     *
     * @param currentPrice The new price of the stock.
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
        this.lastUpdated = LocalDateTime.now();
    }

    /**
     * Gets the timestamp of the last price update.
     *
     * @return The last updated timestamp.
     */
    public LocalDateTime getLastUpdated() { return lastUpdated; }


    /**
     * Returns a string representation of the stock.
     *
     * @return A formatted string containing stock details.
     */
    @Override
    public String toString() {
        return String.format("Stock[symbol=%s, company=%s, price=%.2f]",
                symbol, companyName, currentPrice);
    }
}
