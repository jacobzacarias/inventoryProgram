package zacarias.desktopSchedule.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zacarias.desktopSchedule.DAO.CustomerDao;

import java.sql.Timestamp;
import java.util.Date;

/**
 * The type Customers.
 */
public class Customers {
    private int customerId;
    private String customerName;
    private String address;
    private String zip;
    private String phone;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private String country;

    private ObservableList<Appointments> appointments = FXCollections.observableArrayList();
    private static ObservableList<Customers> customers = FXCollections.observableArrayList();

    /**
     * Instantiates a new Customers.
     *
     * @param customerId   the customer id
     * @param customerName the customer name
     * @param address      the address
     * @param zip          the zip
     * @param phone        the phone
     * @param divisionId   the division id
     * @param country      the country
     */
    public Customers(int customerId, String customerName, String address, String zip, String phone, int divisionId, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.divisionId = divisionId;
        this.country = country;
    }

    /**
     * Gets customers.
     *
     * @return the customers
     */
    public static ObservableList<Customers> getCustomers() {
        return customers;
    }

    /**
     * Gets customer id.
     *
     * @return customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets customer name.
     *
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets address.
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets zip.
     *
     * @return zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets zip.
     *
     * @param zip the zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Gets phone.
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets create date.
     *
     * @return create date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets created by.
     *
     * @return created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets last update.
     *
     * @return last update
     */
    public java.sql.Timestamp getLastUpdate() {
        return (Timestamp) lastUpdate;
    }

    /**
     * Sets last update.
     *
     * @param lastUpdate the last update
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets last updated by.
     *
     * @return last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets last updated by.
     *
     * @param lastUpdatedBy the last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets division id.
     *
     * @return division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets division id.
     *
     * @param divisionId the division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets country.
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets appointments.
     *
     * @return the appointments
     */
    public ObservableList<Appointments> getAppointments() {
        return appointments;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() { return (customerName); }
}