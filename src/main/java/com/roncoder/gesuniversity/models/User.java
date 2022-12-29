package com.roncoder.gesuniversity.models;

import com.roncoder.gesuniversity.db.DBManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ronal
 */
public class User {

    private int id;
    private String matricule;
    private String password;
    private String category;
    private final DBManager db;

    public User() {
        db = new DBManager();
    }

    public User(int id, String matricule, String password, String category) {
        this.id = id;
        this.matricule = matricule;
        this.password = password;
        this.category = category;
        db = new DBManager();
    }

    /**
     * Function that authenticate an user.
     *
     * @return boolean
     */
    public boolean login() {
        try {
            db.select("users", new String[]{"*"});
            db.where("matricule", matricule);
            db.and("password", password);
            ResultSet result = db.get();
            if (result.next()) {
                setCategory(result.getString("category"));
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "User_{" + "id=" + id
                + ", matricule=" + matricule
                + ", password=" + password
                + ", category=" + category
                + "}";
    }

    public class User_Category {

        public static final String STUDENT = "Student";
        public static final String ADMIN = "Admin";
    }

}
