-- Create database
CREATE DATABASE IF NOT EXISTS investment_portfolio;
USE investment_portfolio;

-- Create stocks table
CREATE TABLE stocks (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL UNIQUE,
    company_name VARCHAR(100) NOT NULL,
    sector VARCHAR(50),
    current_price DECIMAL(10,2),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_symbol CHECK (symbol REGEXP '^[A-Z]{1,5}$')
);

-- Create portfolios table
CREATE TABLE portfolios (
    portfolio_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    CONSTRAINT chk_portfolio_name CHECK (LENGTH(name) >= 3)
);

-- Create positions table
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

-- Create transactions table
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

-- Create indexes for better query performance
CREATE INDEX idx_stock_symbol ON stocks(symbol);
CREATE INDEX idx_portfolio_name ON portfolios(name);
CREATE INDEX idx_position_portfolio ON positions(portfolio_id);
CREATE INDEX idx_position_stock ON positions(stock_id);
CREATE INDEX idx_transaction_portfolio ON transactions(portfolio_id);
CREATE INDEX idx_transaction_stock ON transactions(stock_id);
CREATE INDEX idx_transaction_date ON transactions(transaction_date);