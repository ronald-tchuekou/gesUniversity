/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roncoder.gesuniversity.models.ges3;

import com.roncoder.gesuniversity.db.DBManager;

/**
 *
 * @author ronal
 */
public class Faculty {

    private String code;
    private String libelle;
    private final DBManager db;

    public Faculty(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
        db = new DBManager();
    }

    public boolean save() {
        return db.insert("faculties", new String[]{"code", "libelle"}, new Object[]{code, libelle});

    }

    public boolean delete() {
        db.delete("faculties");
        db.where("code", code);
        return db.aplyDeletion();
    }

    public boolean update() {
        return db.update("faculties", new String[]{"libelle"}, new Object[]{libelle},
                "WHERE code = '" + code + "'");
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

    @Override
    public String toString() {
        return "Faculty{" + "code=" + code + ", libelle=" + libelle + '}';
    }

}
