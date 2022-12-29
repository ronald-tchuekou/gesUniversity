/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roncoder.gesuniversity.models.ges1;

import com.roncoder.gesuniversity.db.Database_generator;

/**
 *
 * @author ronal
 */
public class test {

    public static void main(String[] args) {
        Database_generator dg = new Database_generator();
        //dg.setTables();
        //dg.setDefaultUsers();
        String name = "tata'toto";
        String newName = name.replace("'", "''");
        System.out.print(newName);
    }
}
