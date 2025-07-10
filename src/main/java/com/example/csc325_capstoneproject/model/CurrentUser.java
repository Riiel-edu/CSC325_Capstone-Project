package com.example.csc325_capstoneproject.model;

/**
 * A class which stores the information of the User that is currently logged in. All methods in this class are static.
 * @since 6/27/2025
 * @author Nathaniel Rivera
 */
public class CurrentUser {

    /**
     * A static variable which stores the username of the current User.
     */
    public static String username;
    /**
     * A static variable which stores the UID of the current User
     */
    public static String uid;
    /**
     * A static variable which stores the profile picture of the current User
     */
    public static String pfp;

    /**
     * A static method which sets which User is currently logged into the application.
     * @param user The User that is current logging into the app.
     * @since 6/27/2025
     * @author Nathaniel Rivera
     */
    public static void setCurrentUser(User user) {
        username = user.getUsername();
        uid = user.getUID();
    }

    /**
     * Static method which returns the current User's username.
     * @return The current User's username.
     * @since 6/27/2025
     * @author Nathaniel Rivera
     */
    public static String getCurrentUsername() {
        return username;
    }

    /**
     * Static method which returns the current User's UID.
     * @return The current User's UID.
     * @since 6/27/2025
     * @author Nathaniel Rivera
     */
    public static String getCurrentUID() {
        return uid;
    }

    /**
     * Static method which logs the user out of the application.
     * @since 7/9/2025
     * @author Nathaniel Rivera
     */
    public static void logOut() {
        username = null;
        uid = null;
    }

    /**
     * Static method which returns the users profile picture.
     * @return pfp The current User's pfp.
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    public static String getPFP() {
        return pfp;
    }

    /**
     * Static method which sets the users profile picture
     * @param picture the new pfp of the user.
     * @since 7/10/2025
     * @author Nathaniel Rivera
     */
    public static void setPFP(String picture) {
        pfp = picture;
    }

}
