/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roncoder.gesuniversity.frames;

import com.roncoder.gesuniversity.db.DBManager;
import com.roncoder.gesuniversity.models.ges3.Faculty;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ronal
 */
public class FacultyFrame extends javax.swing.JDialog {

    private final DBManager db;
    private Faculty faculty;
    private final DefaultTableModel table_model;
    private int selectedRow = -1;
    private boolean is_edit_mode = false;
    private Dashboard dashboard;

    /**
     * Creates new form Faculty
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FacultyFrame() {
        initComponents();
        table_model = (DefaultTableModel) tab_faculty.getModel();
        this.setLocationRelativeTo(null);
        db = new DBManager();
        get_allFaculties();
        btn_modifier.setEnabled(false);
        btn_supprimer.setEnabled(false);
        rowSelectionListener();
    }

    /**
     * Creates new form Faculty
     *
     * @param frame
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FacultyFrame(JFrame frame) {
        super(frame, true);
        initComponents();
        dashboard = (Dashboard) frame;
        table_model = (DefaultTableModel) tab_faculty.getModel();
        this.setLocationRelativeTo(null);
        db = new DBManager();
        get_allFaculties();
        btn_modifier.setEnabled(false);
        btn_supprimer.setEnabled(false);
        rowSelectionListener();
    }

    private void rowSelectionListener() {
        ListSelectionModel model = tab_faculty.getSelectionModel();
        model.addListSelectionListener(arg -> {
            if (!arg.getValueIsAdjusting()) {
                selectedRow = model.getMinSelectionIndex();
                if (tab_faculty.getSelectedRowCount() > 0) {
                    faculty = new Faculty(
                            table_model.getValueAt(selectedRow, 0).toString(),
                            table_model.getValueAt(selectedRow, 1).toString()
                    );
                    btn_modifier.setEnabled(true);
                    btn_supprimer.setEnabled(true);
                }
            }
        });
    }

    public void get_allFaculties() {
        DefaultTableModel model = (DefaultTableModel) tab_faculty.getModel();
        try {
            db.select("faculties", new String[]{"*"});
            ResultSet result = db.get();
            while (result.next()) {
                model.addRow(new Object[]{
                    result.getString("code"),
                    result.getString("libelle")
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
        tab_faculty = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        code = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        libelle = new javax.swing.JTextField();
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
        app_description.setText("Facultés");

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
        jPanel1.setLayout(null);

        tab_faculty.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Libelle"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tab_faculty.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tab_faculty.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tab_faculty.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tab_faculty.setShowGrid(true);
        jScrollPane1.setViewportView(tab_faculty);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(6, 196, 388, 160);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Code : ");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 30, 60, 20);

        code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeActionPerformed(evt);
            }
        });
        jPanel1.add(code);
        code.setBounds(110, 20, 240, 30);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Libelle:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 80, 60, 20);

        libelle.setText(" ");
        libelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                libelleActionPerformed(evt);
            }
        });
        jPanel1.add(libelle);
        libelle.setBounds(110, 70, 240, 30);

        btn_annuler.setBackground(new java.awt.Color(42, 157, 143));
        btn_annuler.setForeground(new java.awt.Color(255, 255, 255));
        btn_annuler.setText("Annuler");
        btn_annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_annulerActionPerformed(evt);
            }
        });
        jPanel1.add(btn_annuler);
        btn_annuler.setBounds(30, 114, 90, 30);

        btn_modifier.setBackground(new java.awt.Color(69, 123, 157));
        btn_modifier.setForeground(new java.awt.Color(255, 255, 255));
        btn_modifier.setText("Modifier");
        btn_modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifierActionPerformed(evt);
            }
        });
        jPanel1.add(btn_modifier);
        btn_modifier.setBounds(90, 154, 90, 30);

        btn_supprimer.setBackground(new java.awt.Color(230, 57, 70));
        btn_supprimer.setForeground(new java.awt.Color(255, 255, 255));
        btn_supprimer.setText("Supprimer");
        btn_supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supprimerActionPerformed(evt);
            }
        });
        jPanel1.add(btn_supprimer);
        btn_supprimer.setBounds(260, 154, 100, 30);

        btn_ajouter.setBackground(new java.awt.Color(2, 128, 144));
        btn_ajouter.setForeground(new java.awt.Color(255, 255, 255));
        btn_ajouter.setText("Ajouter");
        btn_ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ajouterActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ajouter);
        btn_ajouter.setBounds(190, 114, 100, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(app_options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void libelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_libelleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_libelleActionPerformed

    private void btn_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_annulerActionPerformed
        code.setEnabled(true);
        code.setText("");
        libelle.setText("");
        is_edit_mode = false;
        btn_ajouter.setText("Ajouter");
        btn_modifier.setEnabled(false);
        btn_supprimer.setEnabled(false);
    }//GEN-LAST:event_btn_annulerActionPerformed

    private void codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codeActionPerformed

    private void btn_ajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ajouterActionPerformed

        String code_str = code.getText();
        String libelle_str = libelle.getText();

        if (code_str.equals("")) {
            JOptionPane.showMessageDialog(null, "Indiquer le code de la faculté");
            return;
        }
        if (libelle_str.equals("")) {
            JOptionPane.showMessageDialog(null, "Indiquer le libellé de la faculté");
            return;
        }
        if (!is_edit_mode) {
            Faculty fac = new Faculty(code_str, libelle_str);
            if (fac.save()) {
                DefaultTableModel model = (DefaultTableModel) tab_faculty.getModel();
                model.addRow(new Object[]{code_str, libelle_str});
                dashboard.addFacultyCount();
            }
        } else {
            if (code_str.equals(faculty.getCode()) && libelle_str.equals(faculty.getLibelle())) {
                JOptionPane.showMessageDialog(null, "Rien n'a été modefier dans le formulaire.");
                return;
            }
            faculty.setCode(code_str);
            faculty.setLibelle(libelle_str);
            if (faculty.update()) {
                table_model.setValueAt(faculty.getCode(), selectedRow, 0);
                table_model.setValueAt(faculty.getLibelle(), selectedRow, 1);
            }
        }
        code.setText("");
        libelle.setText("");
        code.setEnabled(true);
    }//GEN-LAST:event_btn_ajouterActionPerformed

    private void btn_modifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifierActionPerformed
        code.setEnabled(false);
        code.setText(faculty.getCode());
        libelle.setText(faculty.getLibelle());
        btn_ajouter.setText("Valider");
        is_edit_mode = true;
    }//GEN-LAST:event_btn_modifierActionPerformed

    private void btn_supprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supprimerActionPerformed
        if (selectedRow != -1) {
            int decision = JOptionPane.showConfirmDialog(null, "Souhait-vous vraiment supprimer cette Faculté ?");
            if (decision == 0) {
                if (faculty.delete()) {
                    dashboard.removeFacultyCount();
                    table_model.removeRow(tab_faculty.getSelectedRow());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un élément à supprimer");
        }
    }//GEN-LAST:event_btn_supprimerActionPerformed

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
            java.util.logging.Logger.getLogger(FacultyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FacultyFrame().setVisible(true);
        });
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField libelle;
    private javax.swing.JTable tab_faculty;
    // End of variables declaration//GEN-END:variables
}