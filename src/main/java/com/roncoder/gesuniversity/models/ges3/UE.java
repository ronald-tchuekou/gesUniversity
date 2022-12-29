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
public class UE {

    private final DBManager db;
    public static String TABLE_NAME = "ues";
    private String code;
    private String filiere_code;
    private String libelle;
    private int nb_credit;
    private int vol_cm;
    private int vol_td;
    private int vol_tp;
    private String category;

    public UE(String code, String filiere_code, String libelle,
            int nb_credit, int vol_cm, int vol_td, int vol_tp, String category) {
        this.db = new DBManager();
        this.code = code;
        this.filiere_code = filiere_code;
        this.libelle = libelle;
        this.nb_credit = nb_credit;
        this.vol_cm = vol_cm;
        this.vol_td = vol_td;
        this.vol_tp = vol_tp;
        this.category = category;
    }

    public boolean save() {
        return db.insert(TABLE_NAME, new String[]{"code", "filiere_code", "libelle", "nb_credit", "vol_cm", "vol_td",
            "vol_tp", "category"},
                new Object[]{code, filiere_code, libelle, nb_credit, vol_cm, vol_td, vol_tp, category});
    }

    public boolean delete() {
        db.delete(TABLE_NAME);
        db.where("code", code);
        return db.aplyDeletion();
    }

    public boolean update() {
        return db.update(TABLE_NAME, new String[]{"filiere_code", "libelle", "nb_credit", "vol_cm", "vol_td",
            "vol_tp", "category"},
                new Object[]{filiere_code, libelle, nb_credit, vol_cm, vol_td, vol_tp, category},
                "WHERE code = '" + code + "'");
    }

    public Filiere getFiliere() {
        try {
            db.select("filieres", new String[]{"*"});
            db.where("code", filiere_code);
            ResultSet result = db.get();
            if (result.next()) {
                return new Filiere(result.getString("code"), result.getString("department_code"),
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

    public String getFiliere_code() {
        return filiere_code;
    }

    public void setFiliere_code(String filiere_code) {
        this.filiere_code = filiere_code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getNb_credit() {
        return nb_credit;
    }

    public void setNb_credit(int nb_credit) {
        this.nb_credit = nb_credit;
    }

    public int getVol_cm() {
        return vol_cm;
    }

    public void setVol_cm(int vol_cm) {
        this.vol_cm = vol_cm;
    }

    public int getVol_td() {
        return vol_td;
    }

    public void setVol_td(int vol_td) {
        this.vol_td = vol_td;
    }

    public int getVol_tp() {
        return vol_tp;
    }

    public void setVol_tp(int vol_tp) {
        this.vol_tp = vol_tp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "UE{"
                + "code=" + code
                + ", filiere_code=" + filiere_code
                + ", libelle=" + libelle
                + ", nb_credit=" + nb_credit
                + ", vol_cm=" + vol_cm
                + ", vol_td=" + vol_td
                + ", vol_tp=" + vol_tp
                + ", category=" + category
                + '}';
    }

}
