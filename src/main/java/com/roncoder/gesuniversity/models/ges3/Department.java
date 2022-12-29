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
public class Department {

    private String code;
    private String faculty_code;
    private String libelle;
    private final DBManager db;
    private static final String TABLE_NAME = "departments";

    public Department(String code, String faculty_code, String libelle) {
        this.code = code;
        this.faculty_code = faculty_code;
        this.libelle = libelle;
        db = new DBManager();
    }

    public boolean save() {
        return db.insert(TABLE_NAME, new String[]{"code", "faculty_code", "libelle"},
                new Object[]{code, faculty_code, libelle});
    }

    public boolean delete() {
        db.delete(TABLE_NAME);
        db.where("code", code);
        return db.aplyDeletion();
    }

    public boolean update() {
        return db.update(TABLE_NAME, new String[]{"faculty_code", "libelle"},
                new Object[]{faculty_code, libelle}, "WHERE code = '" + code + "'");
    }

    public Faculty getFaculty() {
        try {
            db.select("faculties", new String[]{"*"});
            db.where("code", faculty_code);
            ResultSet result = db.get();
            if (result.next()) {
                return new Faculty(result.getString("code"), result.getString("libelle"));
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

    public String getFaculty_code() {
        return faculty_code;
    }

    public void setFaculty_code(String faculty_code) {
        this.faculty_code = faculty_code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Department{"
                + "code=" + code
                + "faculty_code=" + faculty_code
                + ", libelle=" + libelle
                + '}';
    }

}
