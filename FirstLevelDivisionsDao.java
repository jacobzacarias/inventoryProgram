package zacarias.desktopSchedule.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zacarias.desktopSchedule.helper.JDBC;
import zacarias.desktopSchedule.model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.stream.Collectors;

/**
 * The type First level divisions dao.
 */
public class FirstLevelDivisionsDao {
    /**
     * Load first level divisions observable list.
     *
     * @return observable list
     * @throws SQLException the sql exception
     */
    public static ObservableList<FirstLevelDivisions> loadFirstLevelDivisions() throws SQLException {
        ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_Level_divisions";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            int divisionId = resultSet.getInt("Division_ID");
            String division = resultSet.getString("Division");
            Timestamp createDate = resultSet.getTimestamp("Create_Date");
            String createdBy = resultSet.getString("Created_By");
            Timestamp lastUpdated = resultSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int countryId = resultSet.getInt("Country_ID");

            FirstLevelDivisions newDivision = new FirstLevelDivisions(divisionId, division, createDate, createdBy, lastUpdated, lastUpdatedBy, countryId);
            allDivisions.add(newDivision);
        }
        return allDivisions;
    }

    /**
     * Retrieves a division by ID. Removes the need for explicit iteration by using the lambda expression to filter divisions to find the right ID.
     *
     * @param divisionId The ID of the division to be retrieved.
     * @return The division with that specific ID.
     */
    public static FirstLevelDivisions getDivisionById(int divisionId) {
        FirstLevelDivisions div = null;
        try {
            ObservableList<FirstLevelDivisions> list = loadFirstLevelDivisions().stream()
                    .filter(division -> division.getDivisionId() == divisionId)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            div = list.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return div; //Returns div regardless, should be the division ID
    }

    /**
     * Retrieves divisions by country ID.
     *
     * @param countryId The ID of the country with divisions to be retrieved.
     * @return A list of divisions associated with that specific country.
     */
    public static ObservableList<FirstLevelDivisions> getDivisionsByCountryId(int countryId) {
        ObservableList<FirstLevelDivisions> list = FXCollections.observableArrayList();
        try {
            list = loadFirstLevelDivisions().stream()
                    .filter(division -> division.getCountryId() == countryId)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}