-- ========================================================================
-- Investment Portfolio Analyzer Database Schema
-- Author: Gaoussou Thiam
-- Date: 02/17/2026
-- 
-- This script creates the database and all tables required for the
-- Investment Portfolio Analyzer application. It includes:
--   - stocks: Information about individual stocks/securities
--   - portfolios: User investment portfolios
--   - positions: Holdings within portfolios (stocks and quantities)
--   - transactions: History of buy/sell activities
-- ========================================================================

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS investment_portfolio;
USE investment_portfolio;

-- --------------------------------------------------------
-- Table: stocks
-- Purpose: Stores information about individual securities
-- --------------------------------------------------------
CREATE TABLE stocks (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL UNIQUE,
    company_name VARCHAR(100) NOT NULL,
    sector VARCHAR(50),
    current_price DECIMAL(10,2),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_symbol CHECK (symbol REGEXP '^[A-Z]{1,5}$')
);

-- --------------------------------------------------------
-- Table: portfolios
-- Purpose: Stores portfolio information
-- --------------------------------------------------------
CREATE TABLE portfolios (
    portfolio_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    CONSTRAINT chk_portfolio_name CHECK (LENGTH(name) >= 3)
);

-- --------------------------------------------------------
-- Table: positions
-- Purpose: Links stocks to portfolios (holdings)
-- --------------------------------------------------------
CREATE TABLE positions (
    position_id INT AUTO_INCREMENT PRIMARY KEY,
    portfolio_id INT NOT NULL,
    stock_id INT NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    purchase_price DECIMAL(10,2) NOT NULL,
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (portfolio_id) REFERENCES portfolios(portfolio_id),
    FOREIGN KEY (stock_id) REFERENCES stocks(stock_id),
    CONSTRAINT chk_quantity CHECK (quantity > 0),
    CONSTRAINT chk_purchase_price CHECK (purchase_price > 0)
);

-- --------------------------------------------------------
-- Table: transactions
-- Purpose: Records buy/sell activities
-- --------------------------------------------------------
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    portfolio_id INT NOT NULL,
    stock_id INT NOT NULL,
    type ENUM('BUY', 'SELL') NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (portfolio_id) REFERENCES portfolios(portfolio_id),
    FOREIGN KEY (stock_id) REFERENCES stocks(stock_id),
    CONSTRAINT chk_transaction_quantity CHECK (quantity > 0),
    CONSTRAINT chk_transaction_price CHECK (price > 0)
);

-- --------------------------------------------------------
-- Indexes for better query performance
-- --------------------------------------------------------

-- Index for stock lookups by symbol (frequently used)
CREATE INDEX idx_stock_symbol ON stocks(symbol);

-- Index for portfolio lookups by name
CREATE INDEX idx_portfolio_name ON portfolios(name);

-- Indexes for foreign key relationships to improve JOIN performance
CREATE INDEX idx_position_portfolio ON positions(portfolio_id);
CREATE INDEX idx_position_stock ON positions(stock_id);
CREATE INDEX idx_transaction_portfolio ON transactions(portfolio_id);
CREATE INDEX idx_transaction_stock ON transactions(stock_id);

-- Index for transaction date queries (for reporting by date range)
CREATE INDEX idx_transaction_date ON transactions(transaction_date);

-- --------------------------------------------------------
-- In case you guys need sample data for testing, here you go!
-- --------------------------------------------------------

-- Simply uncomment to insert sample data
/*
-- Insert sample stocks
INSERT INTO stocks (symbol, company_name, sector, current_price) VALUES
('AAPL', 'Apple Inc.', 'Technology', 175.34),
('MSFT', 'Microsoft Corporation', 'Technology', 325.67),
('GOOGL', 'Alphabet Inc.', 'Technology', 138.55),
('AMZN', 'Amazon.com Inc.', 'Consumer Discretionary', 156.98),
('JNJ', 'Johnson & Johnson', 'Healthcare', 152.23);

-- Insert sample portfolio
INSERT INTO portfolios (name, description) VALUES
('Tech Growth', 'High growth technology focused portfolio');

-- Get the portfolio ID
SET @portfolio_id = LAST_INSERT_ID();

-- Insert sample positions
INSERT INTO positions (portfolio_id, stock_id, quantity, purchase_price) VALUES
(@portfolio_id, 1, 10, 150.25),
(@portfolio_id, 2, 5, 300.50),
(@portfolio_id, 3, 8, 125.75);

-- Insert sample transactions
INSERT INTO transactions (portfolio_id, stock_id, type, quantity, price) VALUES
(@portfolio_id, 1, 'BUY', 10, 150.25),
(@portfolio_id, 2, 'BUY', 5, 300.50),
(@portfolio_id, 3, 'BUY', 8, 125.75);
*/