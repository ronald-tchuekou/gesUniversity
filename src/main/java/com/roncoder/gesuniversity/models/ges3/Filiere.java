/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roncoder.gesuniversity.models.ges3;

import com.roncoder.gesuniversity.db.DBManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ronal
 */
public class Filiere {

    private String code;
    private String department_code;
    private String libelle;
    private final DBManager db;
    private static final String TABLE_NAME = "filieres";

    public Filiere(String code, String department_code, String libelle) {
        this.code = code;
        this.department_code = department_code;
        this.libelle = libelle;
        db = new DBManager();
    }

    public boolean save() {
        return db.insert(TABLE_NAME, new String[]{"code", "department_code", "libelle"},
                new Object[]{code, department_code, libelle});
    }

    public boolean delete() {
        db.delete(TABLE_NAME);
        db.where("code", code);
        return db.aplyDeletion();
    }

    public boolean update() {
        return db.update(TABLE_NAME, new String[]{"department_code", "libelle"},
                new Object[]{department_code, libelle}, "WHERE code = '" + code + "'");
    }

    public Department getDepartment() {
        try {
            db.select("departments", new String[]{"*"});
            db.where("code", department_code);
            ResultSet result = db.get();
            if (result.next()) {
                return new Department(result.getString("code"), result.getString("faculty_code"),
                        result.getString("libelle"));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Filiere{"
                + "code=" + code
                + "department_code=" + department_code
                + ", libelle=" + libelle
                + '}';
    }

}
