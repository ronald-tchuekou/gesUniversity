/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roncoder.gesuniversity.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ronal
 */
public class DBManager {

    // Database instance.
    private Connection con;
    private PreparedStatement preparedStatement;
    private String query = "";

    public DBManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Le pilote est OK !");
            // Déclaration des variables de configurations.
            String dns = "jdbc:mysql://localhost:3306/" + Config.DB_NAME + "?zeroDateTimeBehavior=convertToNull";
            String user = Config.USER;
            String pass = Config.PASSWORD;

            // Connexion à la base de données.
            con = DriverManager.getConnection(dns, user, pass);

            System.out.println("La conexion à la base de données est éffectuée !");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error lors de connexion à la base de données => "
                    + e.getMessage());
        }
    }

    /**
     * Create new table inside the database.
     *
     * @param table_name
     */
    public void table(String table_name) {
        query = "CREATE TABLE IF NOT EXISTS `" + table_name + "` (";
    }

    /**
     * Create integer field inside the table.
     *
     * @param column_name
     * @param options
     */
    public void integer(String column_name, String options) {
        if (query.trim().charAt(query.length() - 1) != '(') {
            query += ", `" + column_name + "` INT(11) " + options;
        } else {
            query += "`" + column_name + "` INT(11) " + options;
        }
    }

    /**
     * Create string fields inside the database.
     *
     * @param column_name
     * @param options
     */
    public void string(String column_name, String options) {
        if (query.trim().charAt(query.length() - 1) != '(') {
            query += ", `" + column_name + "` VARCHAR(255) " + options;
        } else {
            query += "`" + column_name + "` VARCHAR(255) " + options;
        }
    }

    /**
     * Create text field inside the table.
     *
     * @param column_name
     * @param options
     */
    public void text(String column_name, String options) {
        if (query.trim().charAt(query.length() - 1) != '(') {
            query += ", `" + column_name + "` TEXT " + options;
        } else {
            query += "`" + column_name + "` TEXT " + options;
        }
    }

    /**
     * Create date field.
     *
     * @param column_name
     * @param options
     */
    public void date(String column_name, String options) {
        if (query.trim().charAt(query.length() - 1) != '(') {
            query += ", `" + column_name + "` DATE " + options;
        } else {
            query += "`" + column_name + "` DATE " + options;
        }
    }

    /**
     * Create time field.
     *
     * @param column_name
     * @param options
     */
    public void time(String column_name, String options) {
        if (query.trim().charAt(query.length() - 1) != '(') {
            query += ", `" + column_name + "` TIME " + options;
        } else {
            query += "`" + column_name + "` TIME " + options;
        }
    }

    /**
     * Create timestamps fields.
     */
    public void timestamps() {
        if (query.trim().charAt(query.length() - 1) != '(') {
            query += ", `created_at` DATE"
                    + ", `updated_at` DATE";
        } else {
            query += " `created_at` DATE"
                    + ", `updated_at` DATE";
        }
    }

    /**
     * Execute the statement.
     *
     * @return boolean
     */
    public boolean aply_table_creation() {
        query += ");";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error are provided <===> " + e);
        }
        return false;
    }

    /**
     * Create index.
     *
     * @param index_name
     * @param table_name
     * @param column_name
     * @return boolean
     */
    public boolean index(String index_name, String table_name, String column_name) {
        String index_query = "CREATE INDEX `" + index_name + "` ON `" + table_name + "` (`" + column_name + "`);";
        try {
            preparedStatement = con.prepareStatement(index_query);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error has provided : " + e);
        }
        return false;
    }

    /**
     * Select element.
     *
     * @param table_name
     * @param columns
     */
    public void select(String table_name, String[] columns) {
        String column = String.join(", ", columns);
        query = "SELECT " + column + " FROM `" + table_name + "`";
    }

    /**
     * Where clause.
     *
     * @param column
     * @param critary
     */
    public void where(String column, Object critary) {
        query += " WHERE `" + column + "` = '" + critary + "'";
    }

    public void whereIn(String column, List<Object> checkList) {
        query += " WHERE `" + column + "` IN (";
        query += "'" + checkList.get(0) + "'";
        for (int i = 0; i < checkList.size(); i++) {
            query += ",'" + checkList.get(i) + "'";
        }
        query += ") ";
    }

    /**
     * and clause.
     *
     * @param column
     * @param critary
     */
    public void and(String column, Object critary) {
        query += " AND `" + column + "` = '" + critary + "'";
    }

    /**
     * or clause.
     *
     * @param column
     * @param critary
     */
    public void or(String column, Object critary) {
        query += " OR `" + column + "` = '" + critary + "'";
    }

    /**
     * Function that specify the running query.
     *
     * @param query
     */
    public void query(String query) {
        this.query = query;
    }

    /**
     * function to excute and get the datas.
     *
     * @return ReulstSet
     */
    public ResultSet get() {
        query += ";";
        try {
            preparedStatement = con.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error are provided ===> " + e);
        }
        return null;
    }

    /**
     * Function insert data into the table.
     *
     * @param table_name
     * @param columns
     * @param arguments
     * @return boolean.
     */
    public boolean insert(String table_name, String[] columns, Object[] arguments) {
        query = "INSERT INTO `" + table_name + "` SET ";
        query += "`" + columns[0] + "` = '" + arguments[0] + "'";
        for (int i = 1; i < columns.length; i++) {
            query += ", `" + columns[i] + "` = '" + arguments[i] + "'";
        }
        try {
            preparedStatement = con.prepareStatement(query, RETURN_GENERATED_KEYS);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error has provided : " + e);
        }
        return false;
    }

    /**
     * Get the last element insert id.
     *
     * @return long
     */
    public long getLastId() {
        try {
            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                return result.getLong(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    /**
     * Function that update the content of a table.
     *
     * @param table_name
     * @param columns
     * @param arguments
     * @param conditions
     * @return boolean.
     */
    public boolean update(String table_name, String[] columns, Object[] arguments,
            String conditions) {
        query = "UPDATE `" + table_name + "` SET ";
        query += "`" + columns[0] + "` = '" + arguments[0] + "'";
        for (int i = 1; i < columns.length; i++) {
            query += ", `" + columns[i] + "` = `" + arguments[i] + "`";
        }
        query += " " + conditions.trim();
        try {
            System.out.println("Query insertion is : " + query);
            preparedStatement = con.prepareStatement(query);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error has provided : " + e);
        }
        return false;
    }

    /**
     * Function to delete element.
     *
     * @param table_name
     */
    public void delete(String table_name) {
        query = "DELETE FROM `" + table_name + "` ";
    }

    /**
     * Function that aply the deletion.
     *
     * @return
     */
    public boolean aplyDeletion() {
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getQuery() {
        return query;
    }

    /**
     * Function that delete database table.
     *
     * @param table_name
     * @return boolean
     */
    public boolean dropTable(String table_name) {
        try {
            preparedStatement = con.prepareStatement("DROP TABLE IF EXISTS `" + table_name + "`");
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
