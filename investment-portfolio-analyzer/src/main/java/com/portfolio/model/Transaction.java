package com.portfolio.model;

import java.time.LocalDateTime;

/**
 * This class represents a financial transaction involving a stock within a portfolio.
 * @author Gaoussou Thiam
 * @date 02/17/2026
 * @see com.portfolio.model.Stock
 * @see com.portfolio.model.Portfolio
 * @see com.portfolio.model.Position
 */
public class Transaction {


    /**
     * Enum representing the type of transaction - either BUY or SELL.
     */
    public enum TransactionType {
        BUY, SELL
    }

    /** Unique identifier for the transaction. */
    private int transactionId;

    /** Identifier of the portfolio associated with the transaction. */
    private int portfolioId;

    /** The stock involved in the transaction. */
    private Stock stock;

    /** The type of transaction (BUY or SELL). */
    private TransactionType type;

    /** The quantity of stock bought or sold. */
    private double quantity;

    /** The price per unit of the stock at the time of transaction. */
    private double price;

    /** Timestamp indicating when the transaction took place. */
    private LocalDateTime transactionDate;


    /**
     * Constructs a new Transaction with the specified details.
     *
     * @param portfolioId The ID of the portfolio related to the transaction.
     * @param stock       The stock being transacted.
     * @param type        The type of transaction (BUY or SELL).
     * @param quantity    The quantity of stock transacted.
     * @param price       The price per unit of stock.
     */
    public Transaction(int portfolioId, Stock stock, TransactionType type, double quantity, double price) {
        this.portfolioId = portfolioId;
        this.stock = stock;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = LocalDateTime.now();
    }

    /**
     * Calculates the total amount of the transaction.
     *
     * @return The total transaction amount (quantity * price).
     */
    public double getAmount() {
        return quantity * price;
    }

    /**
     * Gets the transaction ID.
     *
     * @return The transaction ID.
     */
    public int getTransactionId() { return transactionId; }

    /**
     * Sets the transaction ID.
     *
     * @param transactionId The transaction ID to set.
     */
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    /**
     * Gets the portfolio ID associated with this transaction.
     *
     * @return The portfolio ID.
     */
    public int getPortfolioId() { return portfolioId; }

    /**
     * Gets the stock involved in the transaction.
     *
     * @return The stock object.
     */
    public Stock getStock() { return stock; }

    /**
     * Gets the type of transaction (BUY or SELL).
     *
     * @return The transaction type.
     */
    public TransactionType getType() { return type; }

    /**
     * Gets the quantity of stock involved in the transaction.
     *
     * @return The quantity of stock.
     */
    public double getQuantity() { return quantity; }

    /**
     * Gets the price per unit of the stock at the time of the transaction.
     *
     * @return The stock price per unit.
     */
    public double getPrice() { return price; }

    /**
     * Gets the date and time of the transaction.
     *
     * @return The transaction date and time.
     */
    public LocalDateTime getTransactionDate() { return transactionDate; }


    /**
     * Returns a string representation of the transaction.
     *
     * @return A formatted string containing transaction details.
     */
    @Override
    public String toString() {
        return String.format("Transaction[id=%d, type=%s, stock=%s, quantity=%.2f, price=%.2f]",
                transactionId, type, stock.getSymbol(), quantity, price);
    }
}
