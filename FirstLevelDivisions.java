package zacarias.desktopSchedule.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Date;

/**
 * The type First level divisions.
 */
public class FirstLevelDivisions {
    private int divisionId;
    private String divisionName;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;
    private int countryId;

    /**
     * Instantiates a new First level divisions.
     *
     * @param divisionId    the division id
     * @param divisionName  the division name
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdate    the last update
     * @param lastUpdatedBy the last updated by
     * @param countryId     the country id
     */
    public FirstLevelDivisions(int divisionId, String divisionName, Date createDate, String createdBy, Date lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }
    private static ObservableList<FirstLevelDivisions> firstLevelDivisions = FXCollections.observableArrayList();

    /**
     * Sets division id.
     *
     * @param divisionId the division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Get first level divisions observable list.
     *
     * @return observable list
     */
    public static ObservableList<FirstLevelDivisions> getFirstLevelDivisions(){
        return firstLevelDivisions;
    }

    /**
     * Add first level division.
     *
     * @param newDivision the new division
     */
    public static void addFirstLevelDivision(FirstLevelDivisions newDivision) {
    }

    /**
     * Gets division name.
     *
     * @return division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets division name.
     *
     * @param divisionName the division name
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

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
     * Gets division id.
     *
     * @return division id
     */
    public int getDivisionId() {
        return (divisionId);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return (divisionName);
    }
}
