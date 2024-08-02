package zacarias.desktopSchedule.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * The type Users.
 */
public class Users {
    private int userId;
    private String username;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private static ObservableList<Users> users = FXCollections.observableArrayList();
    private static Users activeUser;

    /**
     * Instantiates a new Users.
     *
     * @param userId   the user id
     * @param username the username
     * @param password the password
     */
    public Users(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets user id.
     *
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets user name.
     *
     * @return user name
     */
    public String getUserName() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets create date.
     *
     * @return create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(LocalDateTime createDate) {
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
     * Gets last updated by.
     *
     * @return last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Get active user users.
     *
     * @return users
     */
    public static Users getActiveUser(){
        return activeUser;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return ("#"+Integer.toString(userId)+": "+username);
    }
}
