package com.portfolio.service;

import com.portfolio.model.Portfolio;
import com.portfolio.model.Position;
import com.portfolio.model.Stock;
import com.portfolio.repository.DatabaseService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * This class manages investment portfolios and provides business logic for portfolio operations.
 * It acts as a service layer between the UI and the database repository.
 * @author Gaoussou Thiam
 * @date 02/19/2026
 * @see com.portfolio.model.Portfolio
 * @see com.portfolio.model.Stock
 * @see com.portfolio.model.Position
 * @see com.portfolio.repository.DatabaseService
 */
public class PortfolioManager {


    /** Database service for data persistence operations. */
    private final DatabaseService databaseService;

    /**
     * Constructs a PortfolioManager with a new DatabaseService.
     */
    public PortfolioManager() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Creates a new portfolio and saves it to the database.
     *
     * @param name The name of the portfolio.
     * @param description The description of the portfolio.
     * @return The created portfolio with its ID set.
     * @throws SQLException If a database access error occurs.
     */
    public Portfolio createPortfolio(String name, String description) throws SQLException {
        Portfolio portfolio = new Portfolio(name, description);
        databaseService.savePortfolio(portfolio);
        return portfolio;
    }

    /**
     * Adds a stock to the database or updates its price if it already exists.
     *
     * @param symbol The stock symbol.
     * @param companyName The company name.
     * @param sector The industry sector.
     * @param currentPrice The current stock price.
     * @return The stock object with ID set.
     * @throws SQLException If a database access error occurs.
     */
    public Stock addStock(String symbol, String companyName, String sector, double currentPrice) throws SQLException {
        // Check if stock already exists
        Stock existingStock = databaseService.getStockBySymbol(symbol);
        if (existingStock != null) {
            // Update the price if it's different
            if (existingStock.getCurrentPrice() != currentPrice) {
                databaseService.updateStockPrice(symbol, currentPrice);
                existingStock.setCurrentPrice(currentPrice);
            }
            return existingStock;
        }

        // Create new stock
        Stock newStock = new Stock(symbol, companyName, sector, currentPrice);
        databaseService.saveStock(newStock);
        return newStock;
    }

    /**
     * Adds a position to a portfolio.
     *
     * @param portfolioId The ID of the portfolio.
     * @param stock The stock to add.
     * @param quantity The quantity of shares.
     * @param purchasePrice The price per share.
     * @throws SQLException If a database access error occurs.
     */
    public void addPosition(int portfolioId, Stock stock, double quantity, double purchasePrice) throws SQLException {
        Position position = new Position(stock, quantity, purchasePrice);
        databaseService.savePosition(portfolioId, position);
    }

    /**
     * Gets a portfolio with all its positions.
     *
     * @param portfolioId The ID of the portfolio.
     * @return The portfolio with all its positions, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    public Portfolio getPortfolio(int portfolioId) throws SQLException {
        return databaseService.getPortfolio(portfolioId);
    }

    /**
     * Gets all portfolios without detailed positions.
     *
     * @return A list of all portfolios.
     * @throws SQLException If a database access error occurs.
     */
    public List<Portfolio> getAllPortfolios() throws SQLException {
        return databaseService.getAllPortfolios();
    }

    /**
     * Calculates portfolio statistics including total value, cost, profit/loss,
     * return percentage, and sector allocation.
     *
     * @param portfolio The portfolio to analyze.
     * @return A map of statistic names to their values.
     */
    public Map<String, Object> getPortfolioStats(Portfolio portfolio) {
        Map<String, Object> stats = new HashMap<>();

        // Total value
        double totalValue = portfolio.getTotalValue();
        stats.put("totalValue", totalValue);

        // Total cost
        double totalCost = portfolio.getPositions().stream()
                .mapToDouble(p -> p.getQuantity() * p.getPurchasePrice())
                .sum();
        stats.put("totalCost", totalCost);

        // Total profit/loss
        double totalPnL = portfolio.getPositions().stream()
                .mapToDouble(Position::getUnrealizedPnL)
                .sum();
        stats.put("totalPnL", totalPnL);

        // Return percentage
        double returnPercentage = (totalValue - totalCost) / totalCost * 100;
        stats.put("returnPercentage", returnPercentage);

        // Sector allocation
        Map<String, Double> sectorAllocation = portfolio.getPositions().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getStock().getSector(),
                        Collectors.summingDouble(p -> (p.getCurrentValue() / totalValue) * 100)
                ));
        stats.put("sectorAllocation", sectorAllocation);

        return stats;
    }

    /**
     * Updates the price of a stock.
     *
     * @param symbol The symbol of the stock to update.
     * @param newPrice The new price of the stock.
     * @throws SQLException If a database access error occurs.
     */
    public void updateStockPrice(String symbol, double newPrice) throws SQLException {
        databaseService.updateStockPrice(symbol, newPrice);
    }
}