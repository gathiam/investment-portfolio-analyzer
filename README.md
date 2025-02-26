# 📊 Investment Portfolio Analyzer

## Overview
This is a comprehensive Java-based investment portfolio management system designed to track, manage, and analyze investment portfolios with professional-grade metrics—a must-have tool for investors seeking data-driven insights into their financial positions.

![Investment Portfolio Analysis](https://img.shields.io/badge/Finance-Portfolio%20Analysis-blue)
![Java](https://img.shields.io/badge/Java-21-orange)
![MySQL](https://img.shields.io/badge/Database-MySQL-green)
![In Development](https://img.shields.io/badge/Status-In%20Development-yellow)

## ✨ Key Features

### Portfolio Management
- Create and manage multiple investment portfolios simultaneously
- Track positions across different stocks
- Record detailed transaction history with buy/sell operations
- Update stock prices for current portfolio valuation

### Financial Analysis
- Calculate essential performance metrics:
  - Total return (percentage and absolute)
  - Unrealized profit/loss per position
  - Sector allocation breakdown
- Track portfolio performance over time

### Technical Implementation
- Multi-layered architecture following best practices
- Persistent data storage with MySQL
- Clean, maintainable codebase using OOP principles
- Comprehensive Javadoc documentation

## 🛠️ Technology Stack

### Core Application
- Java 21 for robust application framework
- Maven for dependency management
- JDBC for efficient database connectivity
- Layered architecture for clean separation of concerns

### Data Persistence
- MySQL database for reliable storage
- Normalized schema design
- Transaction management
- Efficient querying

## 📂 Project Structure
```
src/
├── main/
│   ├── java/com/portfolio/
│   │   ├── model/            # Data entities (Stock, Portfolio, Position)
│   │   ├── repository/       # Database operations
│   │   ├── service/          # Business logic
│   │   └── util/             # Helper utilities
│   └── resources/            # Configuration files
│       ├── database.properties # Database connection settings
│       └── schema.sql        # Database schema creation script
└── test/                     # Unit and integration tests
    └── java/com/portfolio/
        └── model/            # Tests for model classes
```

## 📈 Future Enhancements
- **Python Analytics Integration:**
  - Risk metrics calculation (volatility, beta, Sharpe ratio)
  - Advanced portfolio statistics using NumPy & Pandas
  - Data visualization with Matplotlib & Seaborn
  - Correlation analysis between positions
  - Monte Carlo simulations for future performance projections
- Real-time market data integration via financial APIs
- Web interface with Spring Boot and React
- Mobile application for on-the-go portfolio monitoring

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Installation
1. Clone this repository
   ```bash
   git clone https://github.com/gathiam/investment-portfolio-analyzer.git
   ```

2. Set up the MySQL database
   ```bash
   mysql -u root -p < src/main/resources/schema.sql
   ```

3. Configure database connection in `src/main/resources/database.properties`

4. Build the project
   ```bash
   mvn clean install
   ```

5. Run the application
   ```bash
   java -jar target/investment-portfolio-analyzer-1.0-SNAPSHOT.jar
   ```

### Generating Documentation
The project includes comprehensive Javadoc documentation. To generate HTML documentation:
```bash
mvn javadoc:javadoc
```
Then, open `target/site/apidocs/index.html` in your browser.

## 💡 About This Project

This project was developed to demonstrate advanced software engineering principles while solving real-world financial analysis challenges. It showcases my ability to:

- Design and implement complex software systems
- Work with financial data and algorithms
- Create maintainable, well-structured code
- Apply object-oriented programming paradigms to financial domain problems


## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
