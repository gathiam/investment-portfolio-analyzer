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

public class PortfolioManager {
    private final DatabaseService databaseService;

    public PortfolioManager() {
        this.databaseService = new DatabaseService();
    }

    /**
     * Creates a new portfolio
     */
    public Portfolio createPortfolio(String name, String description) throws SQLException {
        Portfolio portfolio = new Portfolio(name, description);
        databaseService.savePortfolio(portfolio);
        return portfolio;
    }

    /**
     * Adds a stock to the database if it doesn't exist
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
     * Adds a position to a portfolio
     */
    public void addPosition(int portfolioId, Stock stock, double quantity, double purchasePrice) throws SQLException {
        Position position = new Position(stock, quantity, purchasePrice);
        databaseService.savePosition(portfolioId, position);
    }

    /**
     * Gets a portfolio with all its positions
     */
    public Portfolio getPortfolio(int portfolioId) throws SQLException {
        return databaseService.getPortfolio(portfolioId);
    }

    /**
     * Gets all portfolios
     */
    public List<Portfolio> getAllPortfolios() throws SQLException {
        return databaseService.getAllPortfolios();
    }

    /**
     * Calculates portfolio statistics
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
     * Updates stock prices in the database
     */
    public void updateStockPrice(String symbol, double newPrice) throws SQLException {
        databaseService.updateStockPrice(symbol, newPrice);
    }
}