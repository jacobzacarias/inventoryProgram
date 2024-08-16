package zacarias.desktopSchedule.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zacarias.desktopSchedule.helper.JDBC;
import zacarias.desktopSchedule.model.Contacts;
import zacarias.desktopSchedule.model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * The type Contacts dao.
 */
public class ContactsDao {
    private static int contactId;
    private static String contactName;
    private static String contactEmail;

    /**
     * Gets all contacts.
     *
     * @return all contacts
     * @throws SQLException the sql exception
     */
    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            contactId = resultSet.getInt("Contact_ID");
            contactName = resultSet.getString("Contact_Name");
            contactEmail = resultSet.getString("Email");

            Contacts newContact = new Contacts(contactId, contactName, contactEmail);
            allContacts.add(newContact);
        }
        return allContacts;
    }

    /**
     * Get contact by id contacts.
     *
     * @param contactId the contact id
     * @return contacts
     */
    public static Contacts getContactById(int contactId){
        Contacts contact = null;
        try {
            ObservableList<Contacts> list = getAllContacts().stream()
                    .filter(contacts -> contacts.getContactId() == contactId)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            contact = list.get(0);
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contact; //Returns contact regardless, should be the contact ID
    }
}
