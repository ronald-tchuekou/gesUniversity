/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roncoder.gesuniversity.db;

/**
 *
 * @author ronal
 */
public class Database_generator {

    private final DBManager db;

    public Database_generator() {
        db = new DBManager();
    }

    public void setTables() {
        /**
         * *********************************
         * GES 1 ==== Salle table
         */
        db.table("salles");
        db.string("code", "PRIMARY KEY NOT NULL");
        db.string("libelle", "NOT NULL");
        db.integer("capacity", "NOT NULL");
        db.string("localisation", "NOT NULL");
        db.integer("num_campus", "NOT NULL");
        db.aply_table_creation();

        /**
         * *********************************
         * GES 2 ==== Salle table
         */
        db.table("matieres");
        db.string("code", "PRIMARY KEY NOT NULL");
        db.string("libelle", "NOT NULL");
        db.integer("nb_credit", "NOT NULL");
        db.string("vol_cm", "NOT NULL");
        db.string("vol_td", "NOT NULL");
        db.string("vol_tp", "NOT NULL");
        db.string("category", "NOT NULL");
        db.aply_table_creation();

        /**
         * *********************************
         * GES 3 ==== Faculties table
         */
        db.table("faculties");
        db.string("code", "PRIMARY KEY NOT NULL");
        db.string("libelle", "NOT NULL");
        db.aply_table_creation();

        /**
         * *********************************
         * GES 3 ==== Departments table
         */
        db.table("departments");
        db.string("code", "PRIMARY KEY NOT NULL");
        db.string("faculty_code", "NOT NULL");
        db.string("libelle", "NOT NULL");
        db.aply_table_creation();
        db.index("IN_faculty_code", "departments", "faculty_code");

        /**
         * *********************************
         * GES 3 ==== Filieres table
         */
        db.table("filieres");
        db.string("code", "PRIMARY KEY NOT NULL");
        db.string("department_code", "NOT NULL");
        db.string("libelle", "NOT NULL");
        db.aply_table_creation();
        db.index("IN_department_code", "filieres", "department_code");

        /**
         * *********************************
         * GES 3 ==== UE table
         */
        db.table("ues");
        db.string("code", "PRIMARY KEY NOT NULL");
        db.string("filiere_code", "NOT NULL");
        db.string("libelle", "NOT NULL");
        db.integer("nb_credit", "NOT NULL");
        db.integer("vol_cm", "NOT NULL");
        db.integer("vol_td", "NOT NULL");
        db.integer("vol_tp", "NOT NULL");
        db.string("category", "NOT NULL");
        db.aply_table_creation();
        db.index("IN_filiere_code", "ues", "filiere_code");

        /**
         * ******************************
         * USERS table.
         */
        // Create the table.
        db.table("users");
        db.integer("id", "PRIMARY KEY NOT NULL AUTO_INCREMENT");
        db.string("matricule", "NOT NULL");
        db.string("password", "NOT NULL");
        db.string("category", "NOT NULL");
        db.aply_table_creation();
        // Index.
        db.index("IN_matricule", "users", "matricule");
        db.index("IN_password", "users", "password");
    }

    public void setDefaultUsers() {
        String[] columns = new String[]{
            "matricule", "password", "category"
        };
        Object[] arguments = new Object[]{"12S12121", "0000", "Admin"};
        db.insert("users", columns, arguments);
        arguments = new String[]{"12S12122", "0000", "Student"};
        db.insert("users", columns, arguments);
    }
}
