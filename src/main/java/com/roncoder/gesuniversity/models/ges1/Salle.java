/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roncoder.gesuniversity.models.ges1;

import com.roncoder.gesuniversity.db.DBManager;

/**
 *
 * @author ronal
 */
public class Salle {

    private String code;
    private String libelle;
    private String localisation;
    private int num_campus;
    private int capacity;
    private final DBManager db;

    public Salle(String code, String libelle, String localisation, int Num_campue, int capacity) {
        this.code = code;
        this.libelle = libelle;
        this.localisation = localisation;
        this.num_campus = Num_campue;
        this.capacity = capacity;
        db = new DBManager();
    }

    /**
     * Function to save this element to the database.
     *
     * @return boolean.
     */
    public boolean save() {
        String[] columns = new String[]{"code", "libelle", "localisation", "num_campus", "capacity"};
        Object[] arguments = new Object[]{code, libelle, localisation, num_campus, capacity};
        return db.insert("salles", columns, arguments);
    }

    /**
     * Function to update this element to the database.
     *
     * @return
     */
    public boolean update() {
        String[] columns = new String[]{"libelle", "localisation", "num_campus", "capacity"};
        Object[] arguments = new Object[]{libelle, localisation, num_campus, capacity};
        return db.update("salles", columns, arguments, " WHERE code = '" + code + "'");
    }

    /**
     * Function to delete this element to the database.
     *
     * @return
     */
    public boolean delete() {
        db.delete("salles");
        db.where("code", code);
        return db.aplyDeletion();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getNum_campue() {
        return num_campus;
    }

    public void setNum_campue(int Num_campue) {
        this.num_campus = Num_campue;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Salle{"
                + "code=" + code
                + ", libelle=" + libelle
                + ", localisation=" + localisation
                + ", Num_campue=" + num_campus
                + ", capacity=" + capacity
                + "}";
    }

}
