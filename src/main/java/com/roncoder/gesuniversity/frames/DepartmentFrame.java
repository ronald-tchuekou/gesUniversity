/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roncoder.gesuniversity.frames;

import com.roncoder.gesuniversity.db.DBManager;
import com.roncoder.gesuniversity.models.ges3.Department;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ronal
 */
public class DepartmentFrame extends javax.swing.JDialog {

    private final DBManager db;
    private Department department;
    private final DefaultTableModel table_model;
    private int selectedRow = -1;
    private boolean is_edit_mode = false;
    private final Dashboard dashboard;

    /**
     * Creates new form Department
     *
     * @param frame
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public DepartmentFrame(javax.swing.JFrame frame) {
        super(frame, true);
        dashboard = (Dashboard) frame;
        db = new DBManager();
        initComponents();
        setFacultyComboBox();
        table_model = (DefaultTableModel) tab_departs.getModel();
        this.setLocationRelativeTo(null);
        get_allDepartment();
        btn_modifier.setEnabled(false);
        btn_supprimer.setEnabled(false);
        rowSelectionListener();
    }

    private void setFacultyComboBox() {
        try {
            List<String> fac = new ArrayList<>();
            db.select("faculties", new String[]{"*"});
            ResultSet result = db.get();
            while (result.next()) {
                fac.add(result.getString("code"));
            }
            faculty.setModel(new javax.swing.DefaultComboBoxModel<>(fac.toArray(new String[]{})));
            faculty.setSelectedIndex(0);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rowSelectionListener() {
        ListSelectionModel model = tab_departs.getSelectionModel();
        model.addListSelectionListener(arg -> {
            if (!arg.getValueIsAdjusting()) {
                selectedRow = model.getMinSelectionIndex();
                if (tab_departs.getSelectedRowCount() > 0) {
                    department = new Department(
                            table_model.getValueAt(selectedRow, 0).toString(),
                            table_model.getValueAt(selectedRow, 2).toString(),
                            table_model.getValueAt(selectedRow, 1).toString()
                    );
                    btn_modifier.setEnabled(true);
                    btn_supprimer.setEnabled(true);
                }
            }
        });
    }

    public void get_allDepartment() {
        DefaultTableModel model = (DefaultTableModel) tab_departs.getModel();
        try {
            db.select("departments", new String[]{"*"});
            ResultSet result = db.get();
            while (result.next()) {
                Department depart = new Department(result.getString("code"),
                        result.getString("faculty_code"), result.getString("libelle"));
                model.addRow(new Object[]{
                    depart.getCode(), depart.getLibelle(), depart.getFaculty_code()
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        app_options = new javax.swing.JPanel();
        app_description = new javax.swing.JLabel();
        close_btn = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tab_departs = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        code = new javax.swing.JTextField();
        libelle = new javax.swing.JTextField();
        faculty = new javax.swing.JComboBox<>();
        btn_annuler = new javax.swing.JButton();
        btn_modifier = new javax.swing.JButton();
        btn_supprimer = new javax.swing.JButton();
        btn_ajouter = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(400, 400));
        setMinimumSize(new java.awt.Dimension(400, 400));
        setUndecorated(true);

        app_options.setBackground(new java.awt.Color(224, 224, 224));
        app_options.setMaximumSize(new java.awt.Dimension(300, 32));
        app_options.setMinimumSize(new java.awt.Dimension(300, 32));

        app_description.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        app_description.setForeground(new java.awt.Color(51, 51, 51));
        app_description.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        app_description.setText("Departements");

        close_btn.setBackground(new java.awt.Color(255, 0, 0));
        close_btn.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\cancel.png")); // NOI18N
        close_btn.setToolTipText("Fermer");
        close_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        close_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close_btnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                close_btnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                close_btnMouseExited(evt);
            }
        });

        javax.swing.GroupLayout app_optionsLayout = new javax.swing.GroupLayout(app_options);
        app_options.setLayout(app_optionsLayout);
        app_optionsLayout.setHorizontalGroup(
            app_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(app_optionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(app_description, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(close_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        app_optionsLayout.setVerticalGroup(
            app_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(app_optionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(app_description, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(close_btn, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel1.setBackground(new java.awt.Color(29, 53, 87));
        jPanel1.setToolTipText("");
        jPanel1.setLayout(null);

        tab_departs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Libelle", "Faculté"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tab_departs.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tab_departs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tab_departs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tab_departs.setShowGrid(true);
        jScrollPane1.setViewportView(tab_departs);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(6, 219, 388, 140);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Faculté : ");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 20, 100, 20);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Libelle : ");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 100, 100, 20);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Code : ");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(30, 60, 100, 20);

        code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeActionPerformed(evt);
            }
        });
        jPanel1.add(code);
        code.setBounds(140, 50, 210, 30);
        jPanel1.add(libelle);
        libelle.setBounds(140, 90, 210, 30);

        faculty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facultyActionPerformed(evt);
            }
        });
        jPanel1.add(faculty);
        faculty.setBounds(140, 10, 210, 30);

        btn_annuler.setBackground(new java.awt.Color(42, 157, 143));
        btn_annuler.setForeground(new java.awt.Color(255, 255, 255));
        btn_annuler.setText("Annuler");
        btn_annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annulerActionPerformed(evt);
            }
        });
        jPanel1.add(btn_annuler);
        btn_annuler.setBounds(30, 144, 90, 30);

        btn_modifier.setBackground(new java.awt.Color(69, 123, 157));
        btn_modifier.setForeground(new java.awt.Color(255, 255, 255));
        btn_modifier.setText("Modifier");
        btn_modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifierActionPerformed(evt);
            }
        });
        jPanel1.add(btn_modifier);
        btn_modifier.setBounds(90, 184, 90, 30);

        btn_supprimer.setBackground(new java.awt.Color(230, 57, 70));
        btn_supprimer.setForeground(new java.awt.Color(255, 255, 255));
        btn_supprimer.setText("Supprimer");
        btn_supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supprimerActionPerformed(evt);
            }
        });
        jPanel1.add(btn_supprimer);
        btn_supprimer.setBounds(260, 184, 100, 30);

        btn_ajouter.setBackground(new java.awt.Color(2, 128, 144));
        btn_ajouter.setForeground(new java.awt.Color(255, 255, 255));
        btn_ajouter.setText("Ajouter");
        btn_ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ajouterActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ajouter);
        btn_ajouter.setBounds(190, 144, 100, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(app_options, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(app_options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void close_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_btnMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_close_btnMouseClicked

    private void close_btnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_btnMouseEntered
        close_btn.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\cancel_hover.png")); // NOI18N
    }//GEN-LAST:event_close_btnMouseEntered

    private void close_btnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_btnMouseExited
        close_btn.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\cancel.png")); // NOI18N
    }//GEN-LAST:event_close_btnMouseExited

    private void facultyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facultyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facultyActionPerformed

    private void codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codeActionPerformed

    private void btn_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annulerActionPerformed
        code.setEnabled(true);
        code.setText("");
        libelle.setText("");
        is_edit_mode = false;
        btn_ajouter.setText("Ajouter");
        btn_modifier.setEnabled(false);
        btn_supprimer.setEnabled(false);
    }//GEN-LAST:event_btn_annulerActionPerformed

    private void btn_modifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifierActionPerformed
        code.setEnabled(false);
        code.setText(department.getCode());
        libelle.setText(department.getLibelle());
        faculty.setSelectedItem(department.getFaculty().getLibelle());
        btn_ajouter.setText("Valider");
        is_edit_mode = true;
    }//GEN-LAST:event_btn_modifierActionPerformed

    private void btn_supprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supprimerActionPerformed
        if (selectedRow != -1) {
            int decision = JOptionPane.showConfirmDialog(null, "Souhait-vous vraiment supprimer ce Departement ?");
            if (decision == 0) {
                if (department.delete()) {
                    dashboard.removeDepartmentCount();
                    table_model.removeRow(tab_departs.getSelectedRow());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un élément à supprimer");
        }
    }//GEN-LAST:event_btn_supprimerActionPerformed

    private void btn_ajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ajouterActionPerformed

        String code_str = code.getText();
        String libelle_str = libelle.getText();
        String faculty_str = String.valueOf(faculty.getSelectedItem());

        if (code_str.equals("")) {
            JOptionPane.showMessageDialog(null, "Indiquer le code du departement");
            return;
        }
        if (libelle_str.equals("")) {
            JOptionPane.showMessageDialog(null, "Indiquer le libellé du departement");
            return;
        }
        if (!is_edit_mode) {
            Department dep = new Department(code_str, faculty_str, libelle_str);
            if (dep.save()) {
                DefaultTableModel model = (DefaultTableModel) tab_departs.getModel();
                model.addRow(new Object[]{code_str, libelle_str, faculty_str});
                dashboard.addDepartmentCount();
            }
        } else {
            if (code_str.equals(department.getCode()) && libelle_str.equals(department.getLibelle())
                    && faculty_str.equals(department.getFaculty_code())) {
                JOptionPane.showMessageDialog(null, "Rien n'a été modefier dans le formulaire.");
                return;
            }
            department.setCode(code_str);
            department.setLibelle(libelle_str);
            department.setFaculty_code(faculty_str);
            if (department.update()) {
                table_model.setValueAt(department.getCode(), selectedRow, 0);
                table_model.setValueAt(department.getLibelle(), selectedRow, 1);
                table_model.setValueAt(department.getFaculty_code(), selectedRow, 2);
            }
        }
        code.setText("");
        faculty.setSelectedIndex(0);
        libelle.setText("");
        code.setEnabled(true);
    }//GEN-LAST:event_btn_ajouterActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DepartmentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
 /* java.awt.EventQueue.invokeLater(() -> {
            new DepartmentFrame().setVisible(true);
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel app_description;
    private javax.swing.JPanel app_options;
    private javax.swing.JButton btn_ajouter;
    private javax.swing.JButton btn_annuler;
    private javax.swing.JButton btn_modifier;
    private javax.swing.JButton btn_supprimer;
    private javax.swing.JLabel close_btn;
    private javax.swing.JTextField code;
    private javax.swing.JComboBox<String> faculty;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField libelle;
    private javax.swing.JTable tab_departs;
    // End of variables declaration//GEN-END:variables
}