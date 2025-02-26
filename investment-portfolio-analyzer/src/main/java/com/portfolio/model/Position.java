package com.portfolio.model;

import java.time.LocalDateTime;

/**
 * This class represents a position in a stock portfolio, tracking the stock,
 * quantity, purchase price, and performance metrics.
 * @author Gaoussou Thiam
 * @date 02/17/2026
 * @see com.portfolio.model.Stock
 * @see com.portfolio.model.Portfolio
 */
public class Position {


    /**
     * Unique identifier for the position.
     */
    private int positionId;

    /**
     * The stock associated with this position.
     */
    private Stock stock;

    /**
     * The quantity of shares held in this position.
     */
    private double quantity;

    /**
     * The purchase price per share at the time of acquisition.
     */
    private double purchasePrice;

    /**
     * The timestamp when the position was created.
     */
    private LocalDateTime purchaseDate;


    /**
     * Constructs a new Position instance.
     *
     * @param stock         The stock associated with this position.
     * @param quantity      The number of shares purchased.
     * @param purchasePrice The price per share at the time of purchase.
     */
    public Position(Stock stock, double quantity, double purchasePrice) {
        this.stock = stock;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = LocalDateTime.now();
    }


    /**
     * Calculates the current value of the position based on the stock's current price.
     *
     * @return The total current value of the position.
     */
    public double getCurrentValue() {
        return quantity * stock.getCurrentPrice();
    }

    /**
     * Calculates the unrealized profit or loss for this position.
     *
     * @return The unrealized profit or loss amount.
     */
    public double getUnrealizedPnL() {
        return getCurrentValue() - (quantity * purchasePrice);
    }

    /**
     * Calculates the percentage return on the position.
     *
     * @return The percentage return on the position.
     */
    public double getReturnPercentage() {
        return ((stock.getCurrentPrice() - purchasePrice) / purchasePrice) * 100;
    }

    /**
     * Gets the position ID.
     *
     * @return The unique identifier for the position.
     */
    public int getPositionId() { return positionId; }

    /**
     * Sets the position ID.
     *
     * @param positionId The unique identifier to set.
     */
    public void setPositionId(int positionId) { this.positionId = positionId; }

    /**
     * Gets the stock associated with this position.
     *
     * @return The stock in the position.
     */
    public Stock getStock() { return stock; }

    /**
     * Sets the stock associated with this position.
     *
     * @param stock The stock to associate with the position.
     */
    public void setStock(Stock stock) { this.stock = stock; }

    /**
     * Gets the quantity of shares in this position.
     *
     * @return The number of shares held.
     */
    public double getQuantity() { return quantity; }

    /**
     * Sets the quantity of shares in this position.
     *
     * @param quantity The new quantity of shares.
     */
    public void setQuantity(double quantity) { this.quantity = quantity; }

    /**
     * Gets the purchase price per share.
     *
     * @return The purchase price per share.
     */
    public double getPurchasePrice() { return purchasePrice; }

    /**
     * Gets the timestamp of when the position was created.
     *
     * @return The purchase date.
     */
    public LocalDateTime getPurchaseDate() { return purchaseDate; }


    /**
     * Returns a string representation of the position.
     *
     * @return A formatted string containing position details.
     */
    @Override
    public String toString() {
        return String.format("Position[stock=%s, quantity=%.2f, value=%.2f, pnl=%.2f]",
                stock.getSymbol(), quantity, getCurrentValue(), getUnrealizedPnL());
    }
}