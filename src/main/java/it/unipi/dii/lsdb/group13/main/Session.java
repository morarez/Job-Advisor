package it.unipi.dii.lsdb.group13.main;

// class to maintain info about the session
public class Session {
    private static Session session = null;
    private String loggedUser = null;
    private String role = null;

    private Session() {}

    public static Session getSingleton() {
        if(session == null) {
            session = new Session();
        }
        return session;
    }

    public static void setLoggedUser(String username) {
        if (session == null) {
            throw new RuntimeException("Session is not active");
        } else {
            session.loggedUser = username;
        }
    }

    public static String getLoggedUser() {
        if (session == null) {
            throw new RuntimeException("Session is not active");
        } else {
            return session.loggedUser;
        }
    }

    public static void setRole(String role) {
        if (session == null) {
            throw new RuntimeException("Session is not active");
        } else {
            session.role = role;
        }
    }

    public static String getRole() {
        if (session == null) {
            throw new RuntimeException("Session is not active");
        } else {
            return session.role;
        }
    }
}
