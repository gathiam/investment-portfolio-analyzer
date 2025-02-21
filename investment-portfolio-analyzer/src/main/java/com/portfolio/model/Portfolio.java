package com.portfolio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private int portfolioId;
    private String name;
    private LocalDateTime creationDate;
    private String description;
    private List<Position> positions;

    public Portfolio(String name, String description) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.positions = new ArrayList<>();
    }

    // Add a new position
    public void addPosition(Position position) {
        positions.add(position);
    }

    // Calculate total value of portfolio
    public double getTotalValue() {
        return positions.stream()
                .mapToDouble(Position::getCurrentValue)
                .sum();
    }

    // Getters and setters
    public int getPortfolioId() { return portfolioId; }
    public void setPortfolioId(int portfolioId) { this.portfolioId = portfolioId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Position> getPositions() { return new ArrayList<>(positions); }

    @Override
    public String toString() {
        return String.format("Portfolio[name=%s, positions=%d, value=%.2f]",
                name, positions.size(), getTotalValue());
    }
}
