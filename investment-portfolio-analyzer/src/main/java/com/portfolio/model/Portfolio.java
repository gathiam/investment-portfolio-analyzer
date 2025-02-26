package com.portfolio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an investment portfolio containing multiple positions.
 * @author Gaoussou Thiam
 * @date 02/17/2026
 * @see com.portfolio.model.Stock
 * @see com.portfolio.model.Position
 * @see com.portfolio.model.Transaction
 */
public class Portfolio {


    /**
     * Unique identifier for the portfolio.
     */
    private int portfolioId;

    /**
     * Name of the portfolio.
     */
    private String name;

    /**
     * The date and time when the portfolio was created.
     */
    private LocalDateTime creationDate;

    /**
     * Description of the portfolio.
     */
    private String description;

    /**
     * List of positions held in the portfolio.
     */
    private List<Position> positions;

    /**
     * Constructs a new Portfolio instance with the specified name and description.
     *
     * @param name The name of the portfolio.
     * @param description A brief description of the portfolio.
     */
    public Portfolio(String name, String description) {
        this.name = name;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.positions = new ArrayList<>();
    }

    /**
     * Adds a new position to the portfolio.
     *
     * @param position The position to be added.
     */
    public void addPosition(Position position) {
        positions.add(position);
    }

    /**
     * Calculates the total value of the portfolio based on its positions.
     *
     * @return The total value of the portfolio.
     */
    public double getTotalValue() {
        return positions.stream()
                .mapToDouble(Position::getCurrentValue)
                .sum();
    }

    /**
     * Gets the portfolio ID.
     *
     * @return The unique identifier of the portfolio.
     */
    public int getPortfolioId() { return portfolioId; }

    /**
     * Sets the portfolio ID.
     *
     * @param portfolioId The unique identifier to set.
     */
    public void setPortfolioId(int portfolioId) { this.portfolioId = portfolioId; }

    /**
     * Gets the name of the portfolio.
     *
     * @return The portfolio name.
     */
    public String getName() { return name; }

    /**
     * Sets the name of the portfolio.
     *
     * @param name The new portfolio name.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the creation date of the portfolio.
     *
     * @return The date and time the portfolio was created.
     */
    public LocalDateTime getCreationDate() { return creationDate; }

    /**
     * Gets the description of the portfolio.
     *
     * @return The portfolio description.
     */
    public String getDescription() { return description; }

    /**
     * Sets the description of the portfolio.
     *
     * @param description The new portfolio description.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets the list of positions in the portfolio.
     *
     * @return A copy of the list of positions.
     */
    public List<Position> getPositions() { return new ArrayList<>(positions); }


    /**
     * Returns a string representation of the portfolio.
     *
     * @return A formatted string containing portfolio details.
     */
    @Override
    public String toString() {
        return String.format("Portfolio[name=%s, positions=%d, value=%.2f]",
                name, positions.size(), getTotalValue());
    }
}
