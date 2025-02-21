package com.portfolio;

import com.portfolio.model.Portfolio;
import com.portfolio.model.Stock;
import com.portfolio.service.PortfolioManager;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class InvestmentPortfolioApp {
    private static final PortfolioManager portfolioManager = new PortfolioManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Investment Portfolio Analyzer");

        try {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1:
                        createPortfolio();
                        break;
                    case 2:
                        viewPortfolios();
                        break;
                    case 3:
                        addStock();
                        break;
                    case 4:
                        addPosition();
                        break;
                    case 5:
                        viewPortfolioDetails();
                        break;
                    case 6:
                        updateStockPrice();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

            System.out.println("Thank you for using Investment Portfolio Analyzer!");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void printMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Create new portfolio");
        System.out.println("2. View all portfolios");
        System.out.println("3. Add stock to database");
        System.out.println("4. Add position to portfolio");
        System.out.println("5. View portfolio details");
        System.out.println("6. Update stock price");
        System.out.println("0. Exit");
        System.out.println("================");
    }

    private static void createPortfolio() throws SQLException {
        System.out.println("\n--- Create New Portfolio ---");
        String name = getStringInput("Enter portfolio name: ");
        String description = getStringInput("Enter description: ");

        Portfolio portfolio = portfolioManager.createPortfolio(name, description);
        System.out.println("Portfolio created with ID: " + portfolio.getPortfolioId());
    }

    private static void viewPortfolios() throws SQLException {
        System.out.println("\n--- All Portfolios ---");
        var portfolios = portfolioManager.getAllPortfolios();

        if (portfolios.isEmpty()) {
            System.out.println("No portfolios found.");
            return;
        }

        for (Portfolio p : portfolios) {
            System.out.println(p.getPortfolioId() + ": " + p.getName());
        }
    }

    private static void addStock() throws SQLException {
        System.out.println("\n--- Add Stock ---");
        String symbol = getStringInput("Enter stock symbol: ");
        String name = getStringInput("Enter company name: ");
        String sector = getStringInput("Enter sector: ");
        double price = getDoubleInput("Enter current price: ");

        Stock stock = portfolioManager.addStock(symbol, name, sector, price);
        System.out.println("Stock added with ID: " + stock.getStockId());
    }

    private static void addPosition() throws SQLException {
        System.out.println("\n--- Add Position ---");

        // Get portfolio
        int portfolioId = getIntInput("Enter portfolio ID: ");

        // Get stock
        String symbol = getStringInput("Enter stock symbol: ");
        Stock stock = portfolioManager.addStock(
                symbol,
                getStringInput("Enter company name: "),
                getStringInput("Enter sector: "),
                getDoubleInput("Enter current price: ")
        );

        double quantity = getDoubleInput("Enter quantity: ");
        double price = getDoubleInput("Enter purchase price: ");

        portfolioManager.addPosition(portfolioId, stock, quantity, price);
        System.out.println("Position added successfully.");
    }

    private static void viewPortfolioDetails() throws SQLException {
        System.out.println("\n--- Portfolio Details ---");
        int portfolioId = getIntInput("Enter portfolio ID: ");

        Portfolio portfolio = portfolioManager.getPortfolio(portfolioId);
        if (portfolio == null) {
            System.out.println("Portfolio not found.");
            return;
        }

        System.out.println("Portfolio: " + portfolio.getName());
        System.out.println("Description: " + portfolio.getDescription());

        var positions = portfolio.getPositions();
        if (positions.isEmpty()) {
            System.out.println("No positions in this portfolio.");
            return;
        }

        System.out.println("\nPositions:");
        for (var position : positions) {
            System.out.printf("%-6s %-20s %8.2f shares @ $%-8.2f Current: $%-8.2f P/L: $%.2f (%.2f%%)\n",
                    position.getStock().getSymbol(),
                    position.getStock().getCompanyName(),
                    position.getQuantity(),
                    position.getPurchasePrice(),
                    position.getStock().getCurrentPrice(),
                    position.getUnrealizedPnL(),
                    position.getReturnPercentage());
        }

        Map<String, Object> stats = portfolioManager.getPortfolioStats(portfolio);

        System.out.println("\nPortfolio Statistics:");
        System.out.printf("Total Value: $%.2f\n", stats.get("totalValue"));
        System.out.printf("Total Cost: $%.2f\n", stats.get("totalCost"));
        System.out.printf("Total P/L: $%.2f\n", stats.get("totalPnL"));
        System.out.printf("Return: %.2f%%\n", stats.get("returnPercentage"));

        System.out.println("\nSector Allocation:");
        @SuppressWarnings("unchecked")
        Map<String, Double> sectorAllocation = (Map<String, Double>) stats.get("sectorAllocation");
        for (Map.Entry<String, Double> entry : sectorAllocation.entrySet()) {
            System.out.printf("%s: %.2f%%\n", entry.getKey(), entry.getValue());
        }
    }

    private static void updateStockPrice() throws SQLException {
        System.out.println("\n--- Update Stock Price ---");
        String symbol = getStringInput("Enter stock symbol: ");
        double newPrice = getDoubleInput("Enter new price: ");

        portfolioManager.updateStockPrice(symbol, newPrice);
        System.out.println("Stock price updated successfully.");
    }

    // Helper methods for user input
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}