package com.portfolio.model;

import java.time.LocalDateTime;

public class Transaction {
    public enum TransactionType {
        BUY, SELL
    }

    private int transactionId;
    private int portfolioId;
    private Stock stock;
    private TransactionType type;
    private double quantity;
    private double price;
    private LocalDateTime transactionDate;

    public Transaction(int portfolioId, Stock stock, TransactionType type, double quantity, double price) {
        this.portfolioId = portfolioId;
        this.stock = stock;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = LocalDateTime.now();
    }

    // Calculate transaction amount
    public double getAmount() {
        return quantity * price;
    }

    // Getters and setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public int getPortfolioId() { return portfolioId; }
    public Stock getStock() { return stock; }
    public TransactionType getType() { return type; }
    public double getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public LocalDateTime getTransactionDate() { return transactionDate; }

    @Override
    public String toString() {
        return String.format("Transaction[id=%d, type=%s, stock=%s, quantity=%.2f, price=%.2f]",
                transactionId, type, stock.getSymbol(), quantity, price);
    }
}