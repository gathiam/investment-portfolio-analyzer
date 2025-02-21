package com.portfolio.repository;

import com.portfolio.model.Portfolio;
import com.portfolio.model.Position;
import com.portfolio.model.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class DatabaseService {
    // Get database connection using config
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                com.portfolio.util.DatabaseConfig.getUrl(),
                com.portfolio.util.DatabaseConfig.getUser(),
                com.portfolio.util.DatabaseConfig.getPassword()
        );
    }

    // Stock operations
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

    // Portfolio operations
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

    // Update stock price
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