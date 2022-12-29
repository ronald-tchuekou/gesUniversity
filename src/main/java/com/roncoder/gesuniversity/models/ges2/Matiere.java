/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roncoder.gesuniversity.models.ges2;

import com.roncoder.gesuniversity.db.DBManager;

/**
 *
 * @author ronal
 */
public class Matiere {

    private String code;
    private String libelle;
    private int nb_credit;
    private String vol_cm;
    private String vol_td;
    private String vol_tp;
    private String category;
    private DBManager db;

    public Matiere(String code, String libelle, int nb_credit, String vol_cm,
            String vol_td, String vol_tp, String category) {
        this.code = code;
        this.libelle = libelle;
        this.nb_credit = nb_credit;
        this.vol_cm = vol_cm;
        this.vol_td = vol_td;
        this.vol_tp = vol_tp;
        this.category = category;
        db = new DBManager();
    }

    /**
     * Function to save this element to the database.
     *
     * @return boolean.
     */
    public boolean save() {
        String[] columns = new String[]{"code", "libelle", "nb_credit", "vol_cm", "vol_td", "vol_tp", "category"};
        Object[] arguments = new Object[]{code, libelle, nb_credit, vol_cm, vol_td, vol_tp, category};
        return db.insert("matieres", columns, arguments);
    }

    /**
     * Function to update this element to the database.
     *
     * @return
     */
    public boolean update() {
        String[] columns = new String[]{"libelle", "nb_credit", "vol_cm", "vol_td", "vol_tp", "category"};
        Object[] arguments = new Object[]{libelle, nb_credit, vol_cm, vol_td, vol_tp, category};
        return db.update("matieres", columns, arguments, " WHERE code = '" + code + "'");
    }

    /**
     * Function to delete this element to the database.
     *
     * @return
     */
    public boolean delete() {
        db.delete("matieres");
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

    public int getNb_credit() {
        return nb_credit;
    }

    public void setNb_credit(int nb_credit) {
        this.nb_credit = nb_credit;
    }

    public String getVol_cm() {
        return vol_cm;
    }

    public void setVol_cm(String vol_cm) {
        this.vol_cm = vol_cm;
    }

    public String getVol_td() {
        return vol_td;
    }

    public void setVol_td(String vol_td) {
        this.vol_td = vol_td;
    }

    public String getVol_tp() {
        return vol_tp;
    }

    public void setVol_tp(String vol_tp) {
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
        return "Matiere{"
                + "code=" + code
                + ", libelle=" + libelle
                + ", nb_credit=" + nb_credit
                + ", vol_cm=" + vol_cm
                + ", vol_td=" + vol_td
                + ", vol_tp=" + vol_tp
                + ", category=" + category
                + "}";
    }

}
