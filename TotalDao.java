package zacarias.desktopSchedule.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zacarias.desktopSchedule.model.Total;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static zacarias.desktopSchedule.helper.JDBC.connection;

/**
 * The type Total dao.
 */
public class TotalDao {
    /**
     * Gets total appointment reports.
     *
     * @return total appointment reports
     */
    public static ObservableList<Total> getTotalAppointmentReports() {
        ObservableList<Total> monthTypeCountRecord = FXCollections.observableArrayList();
        String selectStatement = "SELECT MonthName(Start) AS MName, Month(Start) AS MNumber, Type, Count(*) AS count FROM appointments " +
                "GROUP BY MName, MNumber, Type ORDER BY MNumber, Type";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String month = resultSet.getString("MName");
                    String type = resultSet.getString("Type");
                    int count = resultSet.getInt("Count");

                    Total totalMonthTypes = new Total(month, type, count);
                    monthTypeCountRecord.add(totalMonthTypes);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return monthTypeCountRecord;
    }

    /**
     * Gets total customer reports.
     *
     * @return total customer reports
     */
    public static ObservableList<Total> getTotalCustomerReports() {
        ObservableList<Total> monthTypeCountRecord = FXCollections.observableArrayList();

        //Selects the total amount of customers by country and division
        String selectStatement = "SELECT Division, COUNT(customers.Division_ID) as 'Count', Country " +
                "FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                "INNER JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID " +
                "GROUP BY Country, Division ORDER BY Country, Division";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt("count");
                String country = resultSet.getString("country");
                String division = resultSet.getString("division");

                Total totalCountryDivisions = new Total(country, division, count);
                monthTypeCountRecord.add(totalCountryDivisions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthTypeCountRecord;
    }
}