package com.portfolio.repository;

import com.portfolio.model.Portfolio;
import com.portfolio.model.Position;
import com.portfolio.model.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * This class handles all database operations for the investment portfolio application.
 * It provides methods to save, retrieve, and update data in the MySQL database.
 * @author Gaoussou Thiam
 * @date 02/19/2026
 * @see com.portfolio.model.Stock
 * @see com.portfolio.model.Portfolio
 * @see com.portfolio.model.Position
 */
public class DatabaseService {


    /**
     * Gets a database connection using the configuration from DatabaseConfig.
     *
     * @return A connection to the database.
     * @throws SQLException If a database access error occurs.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                com.portfolio.util.DatabaseConfig.getUrl(),
                com.portfolio.util.DatabaseConfig.getUser(),
                com.portfolio.util.DatabaseConfig.getPassword()
        );
    }

    /**
     * Saves a stock to the database.
     *
     * @param stock The stock to save.
     * @throws SQLException If a database access error occurs.
     */
    public void saveStock(Stock stock) throws SQLException {
        String sql = "INSERT INTO stocks (symbol, company_name, sector, current_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, stock.getSymbol());
            pstmt.setString(2, stock.getCompanyName());
            pstmt.setString(3, stock.getSector());
            pstmt.setDouble(4, stock.getCurrentPrice());

            pstmt.executeUpdate();

            // Get the generated ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    stock.setStockId(generatedKeys.getInt(1));
                }
            }
        }
    }

    /**
     * Retrieves a stock from the database by its symbol.
     *
     * @param symbol The symbol of the stock to retrieve.
     * @return The stock with the given symbol, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    public Stock getStockBySymbol(String symbol) throws SQLException {
        String sql = "SELECT * FROM stocks WHERE symbol = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, symbol);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Stock stock = new Stock(
                            rs.getString("symbol"),
                            rs.getString("company_name"),
                            rs.getString("sector"),
                            rs.getDouble("current_price")
                    );
                    stock.setStockId(rs.getInt("stock_id"));
                    return stock;
                }
                return null;
            }
        }
    }

    /**
     * Saves a portfolio to the database.
     *
     * @param portfolio The portfolio to save.
     * @throws SQLException If a database access error occurs.
     */
    public void savePortfolio(Portfolio portfolio) throws SQLException {
        String sql = "INSERT INTO portfolios (name, description) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, portfolio.getName());
            pstmt.setString(2, portfolio.getDescription());

            pstmt.executeUpdate();

            // Get the generated ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    portfolio.setPortfolioId(generatedKeys.getInt(1));
                }
            }
        }
    }

    /**
     * Saves a position to the database and associates it with a portfolio.
     *
     * @param portfolioId The ID of the portfolio that contains this position.
     * @param position The position to save.
     * @throws SQLException If a database access error occurs.
     */
    public void savePosition(int portfolioId, Position position) throws SQLException {
        String sql = "INSERT INTO positions (portfolio_id, stock_id, quantity, purchase_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, portfolioId);
            pstmt.setInt(2, position.getStock().getStockId());
            pstmt.setDouble(3, position.getQuantity());
            pstmt.setDouble(4, position.getPurchasePrice());

            pstmt.executeUpdate();

            // Get the generated ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    position.setPositionId(generatedKeys.getInt(1));
                }
            }
        }
    }

    /**
     * Retrieves a portfolio with all its positions from the database.
     *
     * @param portfolioId The ID of the portfolio to retrieve.
     * @return The portfolio with all its positions, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    public Portfolio getPortfolio(int portfolioId) throws SQLException {
        String sql = "SELECT p.*, pos.*, s.* FROM portfolios p " +
                "LEFT JOIN positions pos ON p.portfolio_id = pos.portfolio_id " +
                "LEFT JOIN stocks s ON pos.stock_id = s.stock_id " +
                "WHERE p.portfolio_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, portfolioId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Portfolio portfolio = new Portfolio(
                            rs.getString("name"),
                            rs.getString("description")
                    );
                    portfolio.setPortfolioId(rs.getInt("portfolio_id"));

                    do {
                        if (rs.getInt("stock_id") != 0) {  // Check if there are positions
                            Stock stock = new Stock(
                                    rs.getString("symbol"),
                                    rs.getString("company_name"),
                                    rs.getString("sector"),
                                    rs.getDouble("current_price")
                            );
                            stock.setStockId(rs.getInt("stock_id"));

                            Position position = new Position(
                                    stock,
                                    rs.getDouble("quantity"),
                                    rs.getDouble("purchase_price")
                            );
                            position.setPositionId(rs.getInt("position_id"));

                            portfolio.addPosition(position);
                        }
                    } while (rs.next());

                    return portfolio;
                }
                return null;
            }
        }
    }

    /**
     * Retrieves all portfolios from the database without their positions.
     *
     * @return A list of all portfolios.
     * @throws SQLException If a database access error occurs.
     */
    public List<Portfolio> getAllPortfolios() throws SQLException {
        String sql = "SELECT portfolio_id, name, description, creation_date FROM portfolios";
        List<Portfolio> portfolios = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Portfolio portfolio = new Portfolio(
                        rs.getString("name"),
                        rs.getString("description")
                );
                portfolio.setPortfolioId(rs.getInt("portfolio_id"));
                portfolios.add(portfolio);
            }
        }

        return portfolios;
    }

    /**
     * Updates the current price of a stock.
     *
     * @param symbol The symbol of the stock to update.
     * @param newPrice The new price of the stock.
     * @throws SQLException If a database access error occurs.
     */
    public void updateStockPrice(String symbol, double newPrice) throws SQLException {
        String sql = "UPDATE stocks SET current_price = ? WHERE symbol = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newPrice);
            pstmt.setString(2, symbol);

            pstmt.executeUpdate();
        }
    }
}