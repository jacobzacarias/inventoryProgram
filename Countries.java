package zacarias.desktopSchedule.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The type Countries.
 */
public class Countries {
    private int countryId;
    private String countryName;
    private LocalDateTime countryDateCreated;
    private String countryCreatedBy;
    private Timestamp countryLastUpdated;
    private String countryLastUpdatedBy;

    /**
     * Instantiates a new Countries.
     *
     * @param countryId   the country id
     * @param countryName the country name
     */
    public Countries(int countryId, String countryName){
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Add countries.
     *
     * @param newCountry the new country
     */
    public static void addCountries(Countries newCountry) {
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() { return (countryName); }

    /**
     * Gets country id.
     *
     * @return country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets country id.
     *
     * @param countryId the country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets country name.
     *
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets country name.
     *
     * @param countryName the country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets country date created.
     *
     * @return country date created
     */
    public LocalDateTime getCountryDateCreated() {
        return countryDateCreated;
    }

    /**
     * Sets country date created.
     *
     * @param countryDateCreated the country date created
     */
    public void setCountryDateCreated(LocalDateTime countryDateCreated) {
        this.countryDateCreated = countryDateCreated;
    }

    /**
     * Gets country created by.
     *
     * @return country created by
     */
    public String getCountryCreatedBy() {
        return countryCreatedBy;
    }

    /**
     * Sets country created by.
     *
     * @param countryCreatedBy the country created by
     */
    public void setCountryCreatedBy(String countryCreatedBy) {
        this.countryCreatedBy = countryCreatedBy;
    }

    /**
     * Gets country last updated.
     *
     * @return country last updated
     */
    public Timestamp getCountryLastUpdated() {
        return countryLastUpdated;
    }

    /**
     * Sets country last updated.
     *
     * @param countryLastUpdated the country last updated
     */
    public void setCountryLastUpdated(Timestamp countryLastUpdated) {
        this.countryLastUpdated = countryLastUpdated;
    }

    /**
     * Gets country last updated by.
     *
     * @return country last updated by
     */
    public String getCountryLastUpdatedBy() {
        return countryLastUpdatedBy;
    }

    /**
     * Sets country last updated by.
     *
     * @param countryLastUpdatedBy the country last updated by
     */
    public void setCountryLastUpdatedBy(String countryLastUpdatedBy) {
        this.countryLastUpdatedBy = countryLastUpdatedBy;
    }
}
