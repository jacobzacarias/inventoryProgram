package zacarias.desktopSchedule.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zacarias.desktopSchedule.helper.JDBC;
import zacarias.desktopSchedule.model.Appointments;
import zacarias.desktopSchedule.model.Countries;
import zacarias.desktopSchedule.model.Customers;
import zacarias.desktopSchedule.model.FirstLevelDivisions;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;

import static zacarias.desktopSchedule.helper.JDBC.connection;

/**
 * Provides query methods for managing customers.
 */
public class CustomerDao {
    /**
     * Gets all customers.
     *
     * @return all customers
     * @throws SQLException the sql exception
     */
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        String query = "SELECT customers.*, countries.Country FROM customers " +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                "INNER JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID ";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                Timestamp createDate = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int divisionId = resultSet.getInt("Division_ID");
                String country = resultSet.getString("Country");
                Customers customer = new Customers(customerId, customerName, address, postalCode, phone, divisionId, country);
                allCustomers.add(customer);
            }
            return allCustomers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Adds new customers
     *
     * @param customer the customer
     * @throws SQLException the sql exception
     */
    public static void insertCustomer(Customers customer) throws SQLException {
        String query = "INSERT INTO customers (customer_Name, address, postal_Code, phone, division_ID) " +
                "VALUES(?, ?, ?, ?, ?)";
        try {
            int index = 1;
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
            preparedStatement.setString(index++, customer.getCustomerName());
            preparedStatement.setString(index++, customer.getAddress());
            preparedStatement.setString(index++, customer.getZip());
            preparedStatement.setString(index++, customer.getPhone());
            preparedStatement.setInt(index++, customer.getDivisionId());
            System.out.println(preparedStatement);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Customer was inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Update customer.
     *
     * @param updatedCustomer the updated customer
     * @throws SQLException the sql exception
     */
    public static void updateCustomer(Customers updatedCustomer) throws SQLException {
        String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        try {
            int index = 1;
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
            preparedStatement.setString(index++, updatedCustomer.getCustomerName());
            preparedStatement.setString(index++, updatedCustomer.getAddress());
            preparedStatement.setString(index++, updatedCustomer.getZip());
            preparedStatement.setString(index++, updatedCustomer.getPhone());
            preparedStatement.setInt(index++, updatedCustomer.getDivisionId());
            preparedStatement.setInt(index++, updatedCustomer.getCustomerId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer with ID " + updatedCustomer.getCustomerId() + " updated successfully.");
            } else {
                System.out.println("No customer found with the ID " + updatedCustomer.getCustomerId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Delete customer.
     *
     * @param selectedCustomer the selected customer
     * @throws SQLException the sql exception
     */
    public static void deleteCustomer(Customers selectedCustomer) throws SQLException {
        String query = "DELETE FROM customers WHERE Customer_ID = ?";
        try {PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, selectedCustomer.getCustomerId());
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted != 0) {
                System.out.println("Customer with ID " + selectedCustomer.getCustomerId() + " deleted successfully.");
            } else {
                System.out.println("No customer found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retrieves a customer by ID. The lambda expression does not require explicit iteration since customers are filtered to find the right ID.
     *
     * @param customerId The ID of the customer to be retrieved.
     * @return The customer with that specific ID.
     */
    public static Customers getCustomerById(int customerId){
        Customers customer = null;
        try {
            ObservableList<Customers> list = getAllCustomers().stream()
                    .filter(customers -> customers.getCustomerId() == customerId)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            customer = list.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer; //Returns customer regardless, should be the customer ID
    }

    /**
     * Gets customer by country id.
     *
     * @param countryId the country id
     * @return the customer by country id
     */
    public static ObservableList<Customers> getCustomerByCountryId(int countryId) {
        ObservableList<Customers> list = FXCollections.observableArrayList();
        try {
            list = getAllCustomers().stream()
                    .filter(customers -> customers.getCustomerId() == countryId)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}