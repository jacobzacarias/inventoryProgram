package zacarias.desktopSchedule.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Contacts.
 */
public class Contacts {
    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     *
     */
    private static ObservableList<Contacts> contacts = FXCollections.observableArrayList();

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return(contactName);
    }

    /**
     * Instantiates a new Contacts.
     *
     * @param contactId    the contact id
     * @param contactName  the contact name
     * @param contactEmail the contact email
     */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets contact id.
     *
     * @return contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets contact name.
     *
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets contact email.
     *
     * @return contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets contact email.
     *
     * @param contactEmail the contact email
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Add contacts.
     *
     * @param newContact the new contact
     */
    public static void addContacts(Contacts newContact) {
        contacts.add(newContact);
    }

    /**
     * Get contacts observable list.
     *
     * @return observable list
     */
    public static ObservableList<Contacts> getContacts(){
        return contacts;
    }
}