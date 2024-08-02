package zacarias.desktopSchedule.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static zacarias.desktopSchedule.helper.JDBC.connection;

import zacarias.desktopSchedule.helper.JDBC;
import zacarias.desktopSchedule.model.Appointments;
import zacarias.desktopSchedule.model.Users;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * The type Appointment dao.
 */
public class AppointmentDao {
    private static int id;
    private static String title;
    private static String description;
    private static String location;
    private static String type;
    private static LocalDateTime start;
    private static LocalDateTime end;
    private static Timestamp createDate;
    private static String createdBy;
    private static Timestamp lastUpdate;
    private static String lastUpdatedBy;
    private static int customerId;
    private static int userId;
    private static int contactId;

    /**
     * Gets all appointments.
     *
     * @return all appointments
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getInt("Appointment_ID");
            title = resultSet.getString("Title");
            description = resultSet.getString("Description");
            location = resultSet.getString("Location");
            type = resultSet.getString("Type");
            start = resultSet.getTimestamp("Start").toLocalDateTime();
            end = resultSet.getTimestamp("End").toLocalDateTime();
            createDate = resultSet.getTimestamp("Create_Date");
            createdBy = resultSet.getString("Created_By");
            lastUpdate = resultSet.getTimestamp("Last_Update");
            lastUpdatedBy = resultSet.getString("Last_Updated_By");
            customerId = resultSet.getInt("Customer_ID");
            userId = resultSet.getInt("User_ID");
            contactId = resultSet.getInt("Contact_ID");

            Appointments appointment = new Appointments(id, title, description, location, type, start, end, customerId, userId, contactId);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    /**
     * Insert appointment.
     *
     * @param appointment the appointment
     * @throws SQLException the sql exception
     */
    public static void insertAppointment(Appointments appointment) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            preparedStatement.setInt(7, appointment.getCustomerId());
            preparedStatement.setInt(8, appointment.getUserId());
            preparedStatement.setInt(9, appointment.getContactId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                System.out.println("Appointment inserted successfully.");
            }
        }
    }

    /**
     * Update appointment.
     *
     * @param appointment the appointment
     * @throws SQLException the sql exception
     */
    public static void updateAppointment(Appointments appointment) throws SQLException {
        String updateStatement = "UPDATE appointments " +
                "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?";
        try (
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(updateStatement)) {
            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(appointment.getStart()));
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(appointment.getEnd()));
            preparedStatement.setInt(7, appointment.getCustomerId());
            preparedStatement.setInt(8, appointment.getUserId());
            preparedStatement.setInt(9, appointment.getContactId());
            preparedStatement.setInt(10, appointment.getAppointmentId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                System.out.println("Appointment updated successfully.");
            }
        }
    }

    /**
     * Delete appointment.
     *
     * @param selectedAppointment the selected appointment
     * @throws SQLException the sql exception
     */
    public static void deleteAppointment(Appointments selectedAppointment) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ? ";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, selectedAppointment.getAppointmentId());
        int rows = preparedStatement.executeUpdate();
        if (rows == 1) {
            System.out.println("Appointment deleted successfully.");
        }
    }

    /**
     * Gets upcoming appointments.
     *
     * @param user the user
     * @return upcoming appointments
     * @throws SQLException the sql exception
     */
    public static Appointments getUpcomingAppointments(Users user) throws SQLException {
        ObservableList<Appointments> list = AppointmentDao.getAllAppointments();
            LocalDateTime upcomingAppointment = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            LocalDateTime nowPlusFifteen = upcomingAppointment.plusMinutes(15).truncatedTo(ChronoUnit.SECONDS);
            for(Appointments appointment : list) {
                if (appointment.getUserId() != user.getUserId()) {
                    continue;
                }
                if((appointment.getStart().isAfter(upcomingAppointment) && appointment.getStart().isBefore(nowPlusFifteen))) {
                    return appointment;
                }
            }
        return null;
    }

    /**
     * Appointments by customer observable list.
     *
     * @param customerId the customer id
     * @return observable list
     */
    public static ObservableList<Appointments> appointmentsByCustomer(int customerId){
        ObservableList<Appointments> appointmentsByCustomer = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM appointments WHERE Customer_ID = ?";
        try(PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(selectStatement)){
            preparedStatement.setInt(1, customerId);

            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            //while next in result set - make new appointment and put into observable list
            while(resultSet.next()){
                int appointmentId = resultSet.getInt("Appointment_ID");
                String appointmentTitle = resultSet.getString("Title");
                String appointmentDescription = resultSet.getString("Description");
                String appointmentLocation = resultSet.getString("Location");
                String appointmentType = resultSet.getString("Type");
                LocalDateTime appointmentStart = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = resultSet.getTimestamp("End").toLocalDateTime();
                int userId = resultSet.getInt("Customer_ID");
                int contactId = resultSet.getInt("User_ID");

                Appointments appointment = new Appointments(id, title, description, location, type, start, end, customerId, userId, contactId);
                appointmentsByCustomer.add(appointment);
            }
        }
        catch(SQLException e){
        }
        return appointmentsByCustomer;
    }

    /**
     * Delete appointments by customer boolean.
     *
     * @param customerId the customer id
     * @return boolean
     */
    public static boolean deleteAppointmentsByCustomer(int customerId) {
        String deleteStatement = "Delete FROM appointments WHERE Customer_ID = ?";

        try(PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(deleteStatement)){
            preparedStatement.setInt(1, customerId);
            preparedStatement.execute();

            if(preparedStatement.getUpdateCount() > 0){
                return true;
            }
            else{
                return false;
            }
        }
        catch(SQLException e){
            return false;
        }
    }

    /**
     * Gets week appointment table.
     *
     * @return week appointment table
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointments> getWeekAppointmentTable() throws SQLException {
        ObservableList<Appointments> weekAppointments = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM appointments " +
                "INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID " +
                "INNER JOIN users ON appointments.User_ID = users.User_ID " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE Week(Start)=? AND Year(Start)=Year(Current_date)"+
                //"WHERE Start >= now() AND Start <= ? " +
                "ORDER BY Appointment_ID ASC";
        LocalDate ldt = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = ldt.get(weekFields.weekOfWeekBasedYear())-1;
        //return getAppointmentsByQuery(selectStatement, currentMonth);
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(selectStatement);
        preparedStatement.setInt(1,currentWeek);
        System.out.println(preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getInt("Appointment_ID");
            title = resultSet.getString("Title");
            description = resultSet.getString("Description");
            location = resultSet.getString("Location");
            type = resultSet.getString("Type");
            start = resultSet.getTimestamp("Start").toLocalDateTime();
            end = resultSet.getTimestamp("End").toLocalDateTime();
            createDate = resultSet.getTimestamp("Create_Date");
            createdBy = resultSet.getString("Created_By");
            lastUpdate = resultSet.getTimestamp("Last_Update");
            lastUpdatedBy = resultSet.getString("Last_Updated_By");
            customerId = resultSet.getInt("Customer_ID");
            userId = resultSet.getInt("User_ID");
            contactId = resultSet.getInt("Contact_ID");

            Appointments appointment = new Appointments(id, title, description, location, type, start, end, customerId, userId, contactId);
            weekAppointments.add(appointment);
        }
        return weekAppointments;
    }

    /**
     * Gets month appointment table.
     *
     * @return month appointment table
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointments> getMonthAppointmentTable() throws SQLException {
        ObservableList<Appointments> monthlyAppointments = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM appointments " +
                "INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID " +
                "INNER JOIN users ON appointments.User_ID = users.User_ID " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE Month(Start)=? AND Year(Start)=Year(Current_date)"+ //month and start
                //"WHERE Start >= now() AND Start <= ? " +
                "ORDER BY Appointment_ID ASC";
        int currentMonth = LocalDateTime.now().getMonthValue();
        //return getAppointmentsByQuery(selectStatement, currentMonth);
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(selectStatement);
        preparedStatement.setInt(1,currentMonth);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getInt("Appointment_ID");
            title = resultSet.getString("Title");
            description = resultSet.getString("Description");
            location = resultSet.getString("Location");
            type = resultSet.getString("Type");
            start = resultSet.getTimestamp("Start").toLocalDateTime();
            end = resultSet.getTimestamp("End").toLocalDateTime();
            createDate = resultSet.getTimestamp("Create_Date");
            createdBy = resultSet.getString("Created_By");
            lastUpdate = resultSet.getTimestamp("Last_Update");
            lastUpdatedBy = resultSet.getString("Last_Updated_By");
            customerId = resultSet.getInt("Customer_ID");
            userId = resultSet.getInt("User_ID");
            contactId = resultSet.getInt("Contact_ID");

            Appointments appointment = new Appointments(id, title, description, location, type, start, end, customerId, userId, contactId);
            monthlyAppointments.add(appointment);
        }
        return monthlyAppointments;
    }

    /**
     * Gets conflicting appointments.
     *
     * @param customerId    the customer id
     * @param appointmentId the appointment id
     * @param localStart    the local start
     * @param localEnd      the local end
     * @return conflicting appointments
     */
    public static ObservableList<Appointments> getConflictingAppointments(int customerId, int appointmentId, LocalDateTime localStart, LocalDateTime localEnd) {
        ObservableList<Appointments> appointmentsConflicting = FXCollections.observableArrayList();
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId utcZone = ZoneId.of("UTC");

        LocalDateTime appointmentStart = localStart.atZone(localZone).withZoneSameInstant(utcZone).toLocalDateTime();
        LocalDateTime appointmentEnd = localEnd.atZone(localZone).withZoneSameInstant(utcZone).toLocalDateTime();

        String selectStatement = "SELECT * FROM appointments WHERE Customer_ID = ?" +
                " AND ((End > ? AND Start < ?) OR (Start >= ? AND End <= ?))" +
                " AND NOT Appointment_ID = ?";
        try (PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(selectStatement)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setObject(2, appointmentStart);
            preparedStatement.setObject(3, appointmentEnd);
            preparedStatement.setObject(4, appointmentStart);
            preparedStatement.setObject(5, appointmentEnd);
            preparedStatement.setInt(6, appointmentId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointments appointment = new Appointments(id, title, description, location, type, start, end, customerId, userId, contactId);
                appointmentsConflicting.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsConflicting;
    }
}