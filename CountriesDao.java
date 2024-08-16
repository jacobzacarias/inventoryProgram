package zacarias.desktopSchedule.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import zacarias.desktopSchedule.helper.JDBC;

import zacarias.desktopSchedule.model.Countries;
import zacarias.desktopSchedule.model.FirstLevelDivisions;

import java.sql.*;

/**
 * The type Countries dao.
 */
public class CountriesDao {
    /**
     * Gets all countries.
     *
     * @return all countries
     * @throws SQLException the sql exception
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int countryId = resultSet.getInt("Country_ID");
            String countryName = resultSet.getString("Country");

            Countries countries = new Countries(countryId, countryName);
            allCountries.add(countries);
        }
        return allCountries;
    }

    /**
     * Gets country by id.
     *
     * @param countryId the country id
     * @return country by id
     * @throws SQLException the sql exception
     */
    public static Countries getCountryById(int countryId) throws SQLException {
        String selectStatement = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(selectStatement);
        preparedStatement.setInt(1, countryId);
        preparedStatement.executeQuery();

        ResultSet resultSet = preparedStatement.getResultSet();

        while (resultSet.next()) {
            String countryName = resultSet.getString("Country");

            Countries countries = new Countries(countryId, countryName);
            return countries;
        }
        return null;
    }

    /**
     * Gets country by division id.
     *
     * @param divisionId the division id
     * @return country by division id
     * @throws SQLException the sql exception
     */
    public static Countries getCountryByDivisionId(int divisionId) throws SQLException {
        FirstLevelDivisions div = FirstLevelDivisionsDao.getDivisionById(divisionId);
        Countries country = getCountryById(div.getCountryId());
        return country;
    }
}

//    private static final String SELECT_ALL_COUNTRIES = "SELECT * FROM countries";
//    private static final String SELECT_COUNTRY_BY_ID = "SELECT * FROM countries WHERE Country_ID = ?";
//    private static final String SELECT_REPORT_CUSTOMER_COUNT = "SELECT Division, COUNT(customers.Division_ID) as 'Count', Country " +
//            "FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
//            "INNER JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID " +
//            "WHERE first_level_divisions.COUNTRY_ID = ? GROUP BY Division";
//
//    public static ObservableList<Countries> getAllCountries() {
//        ObservableList<Countries> allCountries = FXCollections.observableArrayList();
//        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COUNTRIES)) {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int countryId = resultSet.getInt("Country_ID");
//                String countryName = resultSet.getString("Country");
//                Countries newCountry = new Countries(countryId, countryName);
//                allCountries.add(newCountry);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return allCountries;
//    }
//
//    public static Countries getCountry(int countryId) {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNTRY_BY_ID)) {
//            preparedStatement.setInt(1, countryId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String countryName = resultSet.getString("Country");
//                return new Countries(countryId, countryName);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static ObservableList<FirstLevelDivisions> getReportCustomerCountTable(int countryId) {
//        ObservableList<FirstLevelDivisions> monthTypeCountRecord = FXCollections.observableArrayList();
//        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REPORT_CUSTOMER_COUNT)) {
//            preparedStatement.setInt(1, countryId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                String country = resultSet.getString("Country");
//                String division = resultSet.getString("Division");
//                int count = resultSet.getInt("Count");
//                FirstLevelDivisions countryDivCount = new FirstLevelDivisions(country, division, count);
//                monthTypeCountRecord.add(countryDivCount);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return monthTypeCountRecord;
//    }
