package com.portfolio.model;

import java.time.LocalDateTime;

public class Position {
    private int positionId;
    private Stock stock;
    private double quantity;
    private double purchasePrice;
    private LocalDateTime purchaseDate;

    public Position(Stock stock, double quantity, double purchasePrice) {
        this.stock = stock;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = LocalDateTime.now();
    }

    // Calculate current value of position
    public double getCurrentValue() {
        return quantity * stock.getCurrentPrice();
    }

    // Calculate unrealized profit/loss
    public double getUnrealizedPnL() {
        return getCurrentValue() - (quantity * purchasePrice);
    }

    // Calculate percentage return
    public double getReturnPercentage() {
        return ((stock.getCurrentPrice() - purchasePrice) / purchasePrice) * 100;
    }

    // Getters and setters
    public int getPositionId() { return positionId; }
    public void setPositionId(int positionId) { this.positionId = positionId; }
    public Stock getStock() { return stock; }
    public void setStock(Stock stock) { this.stock = stock; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public double getPurchasePrice() { return purchasePrice; }
    public LocalDateTime getPurchaseDate() { return purchaseDate; }

    @Override
    public String toString() {
        return String.format("Position[stock=%s, quantity=%.2f, value=%.2f, pnl=%.2f]",
                stock.getSymbol(), quantity, getCurrentValue(), getUnrealizedPnL());
    }
}