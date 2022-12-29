package com.roncoder.gesuniversity.frames;

import com.roncoder.gesuniversity.db.DBManager;
import com.roncoder.gesuniversity.models.ges1.Salle;
import com.roncoder.gesuniversity.models.ges2.Matiere;
import com.roncoder.gesuniversity.models.ges3.UE;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ronal
 */
public class Dashboard extends javax.swing.JFrame {

    private boolean is_admin = false;
    private final JPanel[] nav_panels, contentPanels;
    private final JLabel[] nav_titles;
    private final DefaultTableModel salle_table_model, in3_table_model, ue_table_model;
    private final DBManager db;
    private int selectedRow = -1;
    private int facultyCount = 0;
    private int departmentCount = 0;
    private int filiereCount = 0;

    // Ges Salle.
    private Salle salle;
    // Ges Matiere 
    private Matiere matiere;
    // Ges UE
    private UE ue;

    /**
     * Creates new form Dashboard
     *
     * @param is_admin
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Dashboard(boolean is_admin) {
        this.is_admin = is_admin;
        db = new DBManager();
        initComponents();
        if (!is_admin) {
            setStudentComponents();
        }
        this.nav_titles = new JLabel[]{title_1, title_2, title_3};
        this.nav_panels = new JPanel[]{nav_1, nav_2, nav_3};
        this.contentPanels = new JPanel[]{contentPanel1, contentPanel2, contentPanel3};
        setLocationRelativeTo(null);
        setNav(0);
        rowSelectedListener();
        rowSelectedListenerMatiere();
        rowSelectedListenerUE();
        disabledBtns();

        salle_table_model = (DefaultTableModel) salles_table.getModel();
        in3_table_model = (DefaultTableModel) in3_table.getModel();
        ue_table_model = (DefaultTableModel) ue_table.getModel();

        // Get all data.
        getAllSalles();
        getAll_in3();
        getAll_ue();

        // Counters
        getFacultyCount();
        getDepartmentCount();
        getFiliereCount();
    }

    private void setStudentComponents() {
        this.ts_options.setVisible(false);
        this.in3_options.setVisible(false);
        this.ue_options.setVisible(false);
        this.title_1.setText("Consultation des salles");
        this.title_2.setText("Consultation des UE (IN3)");
        this.title_3.setText("Consultation des UE (Facultés)");
    }

    public void getFacultyCount() {
        try {
            db.select("faculties", new String[]{"*"});
            ResultSet result = db.get();
            while (result.next()) {
                this.facultyCount++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        faculty_count.setText(String.valueOf(facultyCount));
    }

    public void getDepartmentCount() {
        try {
            db.select("departments", new String[]{"*"});
            ResultSet result = db.get();
            while (result.next()) {
                this.departmentCount++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        department_count.setText(String.valueOf(departmentCount));
    }

    public void getFiliereCount() {
        try {
            db.select("filieres", new String[]{"*"});
            ResultSet result = db.get();
            while (result.next()) {
                this.filiereCount++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        filiere_count.setText(String.valueOf(filiereCount));
    }

    public void addFacultyCount() {
        this.facultyCount++;
        faculty_count.setText(String.valueOf(facultyCount));
    }

    public void removeFacultyCount() {
        this.facultyCount--;
        faculty_count.setText(String.valueOf(facultyCount));
    }

    public void addDepartmentCount() {
        this.departmentCount++;
        department_count.setText(String.valueOf(departmentCount));
    }

    public void removeDepartmentCount() {
        this.departmentCount--;
        department_count.setText(String.valueOf(departmentCount));
    }

    public void addFiliereCount() {
        this.filiereCount++;
        filiere_count.setText(String.valueOf(filiereCount));
    }

    public void removeFiliereCount() {
        this.filiereCount--;
        filiere_count.setText(String.valueOf(filiereCount));
    }

    private void disabledBtns() {
        btn_remove_salle.setEnabled(false);
        btn_edit_salle.setEnabled(false);
        btn_remove_in3.setEnabled(false);
        btn_edit_in3.setEnabled(false);
    }

    private void rowSelectedListenerUE() {
        ListSelectionModel model = ue_table.getSelectionModel();
        model.addListSelectionListener(arg -> {
            if (!arg.getValueIsAdjusting()) {
                selectedRow = model.getMinSelectionIndex();
                if (ue_table.getSelectedRowCount() > 0) {
                    ue = new UE(
                            ue_table_model.getValueAt(selectedRow, 0).toString(),
                            ue_table_model.getValueAt(selectedRow, 1).toString(),
                            ue_table_model.getValueAt(selectedRow, 7).toString(),
                            Integer.parseInt(ue_table_model.getValueAt(selectedRow, 2).toString()),
                            Integer.parseInt(ue_table_model.getValueAt(selectedRow, 3).toString()),
                            Integer.parseInt(ue_table_model.getValueAt(selectedRow, 4).toString()),
                            Integer.parseInt(ue_table_model.getValueAt(selectedRow, 5).toString()),
                            ue_table_model.getValueAt(selectedRow, 6).toString()
                    );
                    btn_remove_ue.setEnabled(true);
                    btn_edit_ue.setEnabled(true);
                }
            }
        });
    }

    private void rowSelectedListenerMatiere() {
        ListSelectionModel model = in3_table.getSelectionModel();
        model.addListSelectionListener(arg -> {
            if (!arg.getValueIsAdjusting()) {
                selectedRow = model.getMinSelectionIndex();
                if (in3_table.getSelectedRowCount() > 0) {
                    matiere = new Matiere(
                            in3_table_model.getValueAt(selectedRow, 0).toString(),
                            in3_table_model.getValueAt(selectedRow, 1).toString(),
                            Integer.parseInt(in3_table_model.getValueAt(selectedRow, 2).toString()),
                            in3_table_model.getValueAt(selectedRow, 3).toString(),
                            in3_table_model.getValueAt(selectedRow, 4).toString(),
                            in3_table_model.getValueAt(selectedRow, 5).toString(),
                            in3_table_model.getValueAt(selectedRow, 6).toString()
                    );
                    btn_remove_in3.setEnabled(true);
                    btn_edit_in3.setEnabled(true);
                }
            }
        });
    }

    private void rowSelectedListener() {
        ListSelectionModel model = salles_table.getSelectionModel();
        model.addListSelectionListener(arg -> {
            if (!arg.getValueIsAdjusting()) {
                selectedRow = model.getMinSelectionIndex();
                if (salles_table.getSelectedRowCount() > 0) {
                    salle = new Salle(
                            salle_table_model.getValueAt(selectedRow, 0).toString(),
                            salle_table_model.getValueAt(selectedRow, 1).toString(),
                            salle_table_model.getValueAt(selectedRow, 4).toString(),
                            Integer.parseInt(salle_table_model.getValueAt(selectedRow, 3).toString()),
                            Integer.parseInt(salle_table_model.getValueAt(selectedRow, 2).toString())
                    );
                    btn_remove_salle.setEnabled(true);
                    btn_edit_salle.setEnabled(true);
                }
            }
        });
    }

    private void getAllSalles() {
        try {
            db.select("salles", new String[]{"*"});
            ResultSet result = db.get();
            while (result.next()) {
                salle_table_model.addRow(new Object[]{
                    result.getString("code"),
                    result.getString("libelle"),
                    result.getInt("capacity"),
                    result.getInt("num_campus"),
                    result.getString("localisation")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getAll_in3() {
        try {
            db.select("matieres", new String[]{"*"});
            ResultSet result = db.get();
            while (result.next()) {
                in3_table_model.addRow(new Object[]{
                    result.getString("code"),
                    result.getString("libelle"),
                    result.getInt("nb_credit"),
                    result.getString("vol_cm"),
                    result.getString("vol_td"),
                    result.getString("vol_tp"),
                    result.getString("category")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getAll_ue() {
        try {
            //db.select("ues", new String[]{"*"});
            db.query("SELECT ues.*, fi.code as filiere, de.code as department, fa.code as faculty "
                    + "FROM ues INNER JOIN filieres as fi ON ues.filiere_code = fi.code "
                    + "INNER JOIN departments as de ON fi.department_code = de.code "
                    + "INNER JOIN faculties as fa ON de.faculty_code = fa.code;");
            ResultSet result = db.get();
            while (result.next()) {
                System.out.println(result.getString("code"));
                ue_table_model.addRow(new Object[]{
                    result.getString("code"),
                    result.getString("libelle"),
                    result.getInt("nb_credit"),
                    result.getInt("vol_cm"),
                    result.getInt("vol_td"),
                    result.getInt("vol_tp"),
                    result.getString("category"),
                    result.getString("filiere"),
                    result.getString("department"),
                    result.getString("faculty")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addSalle(Salle salle) {
        if (salle.save()) {
            salle_table_model.addRow(new Object[]{salle.getCode(), salle.getLibelle(), salle.getCapacity(),
                salle.getNum_campue(), salle.getLocalisation()});
        }
    }

    public void addMatiere(Matiere matiere) {
        if (matiere.save()) {
            in3_table_model.addRow(new Object[]{matiere.getCode(), matiere.getLibelle(), matiere.getNb_credit(),
                matiere.getVol_cm(), matiere.getVol_td(), matiere.getVol_tp(), matiere.getCategory()});
        }
    }

    public void addUE(UE ue) {
        String department = ue.getFiliere().getDepartment().getCode();
        String faculty = ue.getFiliere().getDepartment().getFaculty().getCode();
        if (ue.save()) {
            ue_table_model.addRow(new Object[]{ue.getCode(), ue.getLibelle(), ue.getNb_credit(),
                ue.getVol_cm(), ue.getVol_td(), ue.getVol_tp(), ue.getCategory(), ue.getFiliere_code(),
                department, faculty
            });
        }
    }

    public void updateSalle(Salle salle) {
        if (salle.update()) {
            // Update the table.
            salle_table_model.setValueAt(salle.getLibelle(), selectedRow, 1);
            salle_table_model.setValueAt(salle.getCapacity(), selectedRow, 2);
            salle_table_model.setValueAt(salle.getNum_campue(), selectedRow, 3);
            salle_table_model.setValueAt(salle.getLocalisation(), selectedRow, 4);
        }
    }

    public void updateMatiere(Matiere matiere) {
        if (matiere.update()) {
            // Update the table.
            in3_table_model.setValueAt(matiere.getLibelle(), selectedRow, 1);
            in3_table_model.setValueAt(matiere.getNb_credit(), selectedRow, 2);
            in3_table_model.setValueAt(matiere.getVol_cm(), selectedRow, 3);
            in3_table_model.setValueAt(matiere.getVol_td(), selectedRow, 4);
            in3_table_model.setValueAt(matiere.getVol_tp(), selectedRow, 5);
            in3_table_model.setValueAt(matiere.getCategory(), selectedRow, 6);
        }
    }

    public void updateUE(UE ue) {

        if (ue.update()) {
            // Update the table.
            ue_table_model.setValueAt(ue.getLibelle(), selectedRow, 1);
            ue_table_model.setValueAt(ue.getNb_credit(), selectedRow, 2);
            ue_table_model.setValueAt(ue.getVol_cm(), selectedRow, 3);
            ue_table_model.setValueAt(ue.getVol_td(), selectedRow, 4);
            ue_table_model.setValueAt(ue.getVol_tp(), selectedRow, 5);
            ue_table_model.setValueAt(ue.getCategory(), selectedRow, 6);
            ue_table_model.setValueAt(ue.getFiliere_code(), selectedRow, 7);
        }
    }

    private void setNav(int position) {
        for (int i = 0; i < nav_titles.length; i++) {
            if (position == i) {
                nav_panels[i].setBackground(new java.awt.Color(1, 39, 92));
                nav_titles[i].setForeground(new java.awt.Color(255, 255, 255));
                contentPanels[i].setVisible(true);
            } else {
                nav_panels[i].setBackground(null);
                nav_titles[i].setForeground(new java.awt.Color(204, 204, 204));
                contentPanels[i].setVisible(false);
            }
        }
        selectedRow = -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nav_bar = new javax.swing.JPanel();
        nav_header = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        app_name = new javax.swing.JLabel();
        nav_1 = new javax.swing.JPanel();
        title_1 = new javax.swing.JLabel();
        icon_1 = new javax.swing.JLabel();
        nav_2 = new javax.swing.JPanel();
        title_2 = new javax.swing.JLabel();
        icon_2 = new javax.swing.JLabel();
        nav_3 = new javax.swing.JPanel();
        title_3 = new javax.swing.JLabel();
        icon_3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        app_options = new javax.swing.JPanel();
        app_description = new javax.swing.JLabel();
        close_btn = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        contentPanel1 = new javax.swing.JPanel();
        js_ts = new javax.swing.JScrollPane();
        salles_table = new javax.swing.JTable();
        ts_options = new javax.swing.JPanel();
        btn_add_salle = new javax.swing.JLabel();
        btn_remove_salle = new javax.swing.JLabel();
        btn_edit_salle = new javax.swing.JLabel();
        edit_search_salle = new javax.swing.JTextField();
        btn_search_salle = new javax.swing.JButton();
        manage_salle_label = new javax.swing.JLabel();
        btn_left_page_salle = new javax.swing.JButton();
        btn_right_page_salle = new javax.swing.JButton();
        salle_num_page = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        contentPanel2 = new javax.swing.JPanel();
        js_in3 = new javax.swing.JScrollPane();
        in3_table = new javax.swing.JTable();
        in3_options = new javax.swing.JPanel();
        btn_add_in3 = new javax.swing.JLabel();
        btn_remove_in3 = new javax.swing.JLabel();
        btn_edit_in3 = new javax.swing.JLabel();
        edit_search_in3 = new javax.swing.JTextField();
        btn_search_in3 = new javax.swing.JButton();
        manage_in3_label = new javax.swing.JLabel();
        btn_left_page_in3 = new javax.swing.JButton();
        btn_right_page_in3 = new javax.swing.JButton();
        in3_num_page = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        contentPanel3 = new javax.swing.JPanel();
        js_ue = new javax.swing.JScrollPane();
        ue_table = new javax.swing.JTable();
        ue_options = new javax.swing.JPanel();
        btn_add_ue = new javax.swing.JLabel();
        btn_remove_ue = new javax.swing.JLabel();
        btn_edit_ue = new javax.swing.JLabel();
        manage_ue_label = new javax.swing.JLabel();
        btn_left_page_ue = new javax.swing.JButton();
        btn_right_page_ue = new javax.swing.JButton();
        ue_num_page = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jPanel5 = new javax.swing.JPanel();
        faculties_ges = new javax.swing.JPanel();
        faculties = new javax.swing.JLabel();
        faculty_count = new javax.swing.JLabel();
        department_ges = new javax.swing.JPanel();
        departments = new javax.swing.JLabel();
        department_count = new javax.swing.JLabel();
        filieres_ges = new javax.swing.JPanel();
        filieres = new javax.swing.JLabel();
        filiere_count = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        edit_search_faculty = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        edit_search_department = new javax.swing.JTextField();
        edit_search_filiere = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        edit_search_ue = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_search_ue = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 600));
        setUndecorated(true);
        setResizable(false);

        nav_bar.setBackground(new java.awt.Color(29, 53, 87));
        nav_bar.setAutoscrolls(true);

        nav_header.setBackground(new java.awt.Color(255, 115, 20));
        nav_header.setMaximumSize(new java.awt.Dimension(246, 60));
        nav_header.setMinimumSize(new java.awt.Dimension(246, 60));
        nav_header.setName(""); // NOI18N
        nav_header.setPreferredSize(new java.awt.Dimension(246, 60));

        logo.setIcon(new javax.swing.ImageIcon("F:\\documents_Ronald\\Downloads\\un_logo.png")); // NOI18N

        app_name.setFont(new java.awt.Font("Calibri", 1, 32)); // NOI18N
        app_name.setForeground(new java.awt.Color(255, 255, 255));
        app_name.setText("GesUniversity");

        javax.swing.GroupLayout nav_headerLayout = new javax.swing.GroupLayout(nav_header);
        nav_header.setLayout(nav_headerLayout);
        nav_headerLayout.setHorizontalGroup(
            nav_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nav_headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(app_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        nav_headerLayout.setVerticalGroup(
            nav_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nav_headerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nav_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(app_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        nav_1.setBackground(new java.awt.Color(1, 39, 92));
        nav_1.setToolTipText("Gestion des salles");
        nav_1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nav_1.setPreferredSize(new java.awt.Dimension(0, 50));
        nav_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nav_1MouseClicked(evt);
            }
        });
        nav_1.setLayout(null);

        title_1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        title_1.setForeground(new java.awt.Color(255, 255, 255));
        title_1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        title_1.setText("Gestion des salles");
        nav_1.add(title_1);
        title_1.setBounds(40, 6, 200, 38);

        icon_1.setIcon(new javax.swing.ImageIcon("F:\\documents_Ronald\\Downloads\\global-network.png")); // NOI18N
        nav_1.add(icon_1);
        icon_1.setBounds(6, 0, 32, 50);

        nav_2.setToolTipText("Gestion des UE pour IN3");
        nav_2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nav_2.setMaximumSize(new java.awt.Dimension(2460, 4000));
        nav_2.setMinimumSize(new java.awt.Dimension(0, 50));
        nav_2.setName(""); // NOI18N
        nav_2.setPreferredSize(new java.awt.Dimension(0, 50));
        nav_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nav_2MouseClicked(evt);
            }
        });
        nav_2.setLayout(null);

        title_2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        title_2.setForeground(new java.awt.Color(204, 204, 204));
        title_2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        title_2.setText("Gestion des UE pour IN3");
        nav_2.add(title_2);
        title_2.setBounds(40, 6, 200, 38);

        icon_2.setIcon(new javax.swing.ImageIcon("F:\\documents_Ronald\\Downloads\\global-network.png")); // NOI18N
        nav_2.add(icon_2);
        icon_2.setBounds(6, 0, 32, 50);

        nav_3.setToolTipText("Gestion des UE");
        nav_3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nav_3.setMaximumSize(new java.awt.Dimension(2460, 6000));
        nav_3.setMinimumSize(new java.awt.Dimension(0, 50));
        nav_3.setName(""); // NOI18N
        nav_3.setPreferredSize(new java.awt.Dimension(0, 50));
        nav_3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nav_3MouseClicked(evt);
            }
        });
        nav_3.setLayout(null);

        title_3.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        title_3.setForeground(new java.awt.Color(204, 204, 204));
        title_3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        title_3.setText("Gestion des UE");
        nav_3.add(title_3);
        title_3.setBounds(40, 6, 200, 38);

        icon_3.setIcon(new javax.swing.ImageIcon("F:\\documents_Ronald\\Downloads\\global-network.png")); // NOI18N
        nav_3.add(icon_3);
        icon_3.setBounds(6, 0, 32, 50);

        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("© Copuright 2020, roncoder");

        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Powered by:");

        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Tchuekoiu Ronald - dev*");

        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Tioko Franck - dev");

        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Waindja Jessica - dev");

        jPanel2.setBackground(new java.awt.Color(2, 128, 144));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\logout.png")); // NOI18N
        jLabel14.setText("Deconnexion");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addContainerGap())
        );

        javax.swing.GroupLayout nav_barLayout = new javax.swing.GroupLayout(nav_bar);
        nav_bar.setLayout(nav_barLayout);
        nav_barLayout.setHorizontalGroup(
            nav_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nav_header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(nav_1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
            .addComponent(nav_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(nav_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(nav_barLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nav_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        nav_barLayout.setVerticalGroup(
            nav_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nav_barLayout.createSequentialGroup()
                .addComponent(nav_header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nav_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nav_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nav_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel10)
                .addGap(0, 0, 0)
                .addComponent(jLabel11)
                .addGap(0, 0, 0)
                .addComponent(jLabel12)
                .addGap(0, 0, 0)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        app_options.setBackground(new java.awt.Color(224, 224, 224));
        app_options.setPreferredSize(new java.awt.Dimension(665, 24));

        app_description.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        app_description.setForeground(new java.awt.Color(51, 51, 51));
        app_description.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        app_description.setText("APPLICATION DE GESTION DES SALLES ET DES UE (TP)");

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

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\redius.png")); // NOI18N
        jLabel6.setToolTipText("Réduire");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout app_optionsLayout = new javax.swing.GroupLayout(app_options);
        app_options.setLayout(app_optionsLayout);
        app_optionsLayout.setHorizontalGroup(
            app_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(app_optionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(app_description, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(close_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        app_optionsLayout.setVerticalGroup(
            app_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(app_optionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(app_description, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(app_optionsLayout.createSequentialGroup()
                .addGroup(app_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(close_btn)
                    .addComponent(jLabel6))
                .addGap(0, 27, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(22, 26, 29));
        jPanel1.setLayout(new java.awt.CardLayout());

        contentPanel1.setLayout(null);

        js_ts.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        js_ts.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        js_ts.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        salles_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Libelle", "Capacité", "Num Campus", "Localisation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        salles_table.setRowHeight(23);
        salles_table.setSelectionBackground(new java.awt.Color(29, 53, 87));
        salles_table.setSelectionForeground(new java.awt.Color(204, 204, 204));
        salles_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        salles_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        salles_table.setShowGrid(true);
        salles_table.getTableHeader().setReorderingAllowed(false);
        js_ts.setViewportView(salles_table);

        contentPanel1.add(js_ts);
        js_ts.setBounds(6, 76, 729, 387);

        ts_options.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        ts_options.setPreferredSize(new java.awt.Dimension(32, 137));

        btn_add_salle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_add_salle.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\add.png")); // NOI18N
        btn_add_salle.setToolTipText("Ajouter une salle");
        btn_add_salle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_add_salle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_add_salle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_add_salleMouseClicked(evt);
            }
        });

        btn_remove_salle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_remove_salle.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\minus.png")); // NOI18N
        btn_remove_salle.setToolTipText("Supprimer la salle");
        btn_remove_salle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_remove_salle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_remove_salle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_remove_salleMouseClicked(evt);
            }
        });

        btn_edit_salle.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\edit.png")); // NOI18N
        btn_edit_salle.setToolTipText("Modifier");
        btn_edit_salle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_edit_salle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_edit_salle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_edit_salleMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ts_optionsLayout = new javax.swing.GroupLayout(ts_options);
        ts_options.setLayout(ts_optionsLayout);
        ts_optionsLayout.setHorizontalGroup(
            ts_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_add_salle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_remove_salle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ts_optionsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_edit_salle))
        );
        ts_optionsLayout.setVerticalGroup(
            ts_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ts_optionsLayout.createSequentialGroup()
                .addComponent(btn_add_salle)
                .addGap(0, 0, 0)
                .addComponent(btn_remove_salle)
                .addGap(0, 0, 0)
                .addComponent(btn_edit_salle)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        contentPanel1.add(ts_options);
        ts_options.setBounds(735, 76, 34, 387);

        edit_search_salle.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        edit_search_salle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_search_salleActionPerformed(evt);
            }
        });
        edit_search_salle.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                edit_search_sallePropertyChange(evt);
            }
        });
        contentPanel1.add(edit_search_salle);
        edit_search_salle.setBounds(593, 42, 142, 28);

        btn_search_salle.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\search.png")); // NOI18N
        btn_search_salle.setToolTipText("Rechercher");
        btn_search_salle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_salleActionPerformed(evt);
            }
        });
        contentPanel1.add(btn_search_salle);
        btn_search_salle.setBounds(735, 42, 34, 28);

        manage_salle_label.setBackground(new java.awt.Color(29, 53, 87));
        manage_salle_label.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        manage_salle_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manage_salle_label.setText("Tableau contenant la liste des salles de l'université");
        contentPanel1.add(manage_salle_label);
        manage_salle_label.setBounds(6, 6, 763, 30);

        btn_left_page_salle.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\left_arrow.png")); // NOI18N
        btn_left_page_salle.setBorder(null);
        btn_left_page_salle.setMaximumSize(new java.awt.Dimension(30, 30));
        btn_left_page_salle.setMinimumSize(new java.awt.Dimension(30, 30));
        btn_left_page_salle.setPreferredSize(new java.awt.Dimension(30, 30));
        btn_left_page_salle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_left_page_salleActionPerformed(evt);
            }
        });
        contentPanel1.add(btn_left_page_salle);
        btn_left_page_salle.setBounds(17, 469, 30, 30);

        btn_right_page_salle.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\right_arrow.png")); // NOI18N
        btn_right_page_salle.setBorder(null);
        btn_right_page_salle.setMaximumSize(new java.awt.Dimension(30, 30));
        btn_right_page_salle.setMinimumSize(new java.awt.Dimension(30, 30));
        btn_right_page_salle.setPreferredSize(new java.awt.Dimension(30, 30));
        btn_right_page_salle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_right_page_salleActionPerformed(evt);
            }
        });
        contentPanel1.add(btn_right_page_salle);
        btn_right_page_salle.setBounds(106, 469, 30, 30);

        salle_num_page.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salle_num_page.setText("1");
        salle_num_page.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        contentPanel1.add(salle_num_page);
        salle_num_page.setBounds(53, 469, 47, 30);

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel5.setText("Mini documentation:");
        contentPanel1.add(jLabel5);
        jLabel5.setBounds(17, 511, 160, 17);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextPane1.setText("Sur ce tableau, pour modifier un élément, selectionner dans le tableau et cliquer sur le bouton *edit*. \nPour supprimer  un élément du tabeau, selectionner l'élément et cliquer su le bouton *moins*.\nPour ajouter un nouveau élément dans le tableau, cliquer sur le bouton *plus*.");
        jTextPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextPane1.setSelectionColor(new java.awt.Color(29, 18, 59));
        jScrollPane2.setViewportView(jTextPane1);

        contentPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(6, 534, 763, 68);

        jPanel1.add(contentPanel1, "card4");

        contentPanel2.setPreferredSize(new java.awt.Dimension(0, 0));
        contentPanel2.setLayout(null);

        js_in3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        js_in3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        js_in3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        in3_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Libelle", "Nbre credit", "Vol. Hor. CM", "Vol. Hor. TD", "Vol. Hor. TP", "Categorie"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        in3_table.setRowHeight(23);
        in3_table.setSelectionBackground(new java.awt.Color(29, 53, 87));
        in3_table.setSelectionForeground(new java.awt.Color(204, 204, 204));
        in3_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        in3_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        in3_table.setShowGrid(true);
        in3_table.getTableHeader().setReorderingAllowed(false);
        js_in3.setViewportView(in3_table);

        contentPanel2.add(js_in3);
        js_in3.setBounds(6, 76, 729, 387);

        in3_options.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        in3_options.setPreferredSize(new java.awt.Dimension(32, 137));

        btn_add_in3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_add_in3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\add.png")); // NOI18N
        btn_add_in3.setToolTipText("Ajouter une salle");
        btn_add_in3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_add_in3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_add_in3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_add_in3MouseClicked(evt);
            }
        });

        btn_remove_in3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_remove_in3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\minus.png")); // NOI18N
        btn_remove_in3.setToolTipText("Supprimer la salle");
        btn_remove_in3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_remove_in3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_remove_in3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_remove_in3MouseClicked(evt);
            }
        });

        btn_edit_in3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\edit.png")); // NOI18N
        btn_edit_in3.setToolTipText("Modifier");
        btn_edit_in3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_edit_in3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_edit_in3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_edit_in3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout in3_optionsLayout = new javax.swing.GroupLayout(in3_options);
        in3_options.setLayout(in3_optionsLayout);
        in3_optionsLayout.setHorizontalGroup(
            in3_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_add_in3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_remove_in3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, in3_optionsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_edit_in3))
        );
        in3_optionsLayout.setVerticalGroup(
            in3_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(in3_optionsLayout.createSequentialGroup()
                .addComponent(btn_add_in3)
                .addGap(0, 0, 0)
                .addComponent(btn_remove_in3)
                .addGap(0, 0, 0)
                .addComponent(btn_edit_in3)
                .addGap(0, 283, Short.MAX_VALUE))
        );

        contentPanel2.add(in3_options);
        in3_options.setBounds(735, 76, 34, 387);

        edit_search_in3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        edit_search_in3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_search_in3ActionPerformed(evt);
            }
        });
        contentPanel2.add(edit_search_in3);
        edit_search_in3.setBounds(593, 42, 142, 28);

        btn_search_in3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\search.png")); // NOI18N
        btn_search_in3.setToolTipText("Rechercher");
        btn_search_in3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_in3ActionPerformed(evt);
            }
        });
        contentPanel2.add(btn_search_in3);
        btn_search_in3.setBounds(735, 42, 34, 28);

        manage_in3_label.setBackground(new java.awt.Color(29, 53, 87));
        manage_in3_label.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        manage_in3_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manage_in3_label.setText("Tableau contenant la liste des ue pour IN3");
        contentPanel2.add(manage_in3_label);
        manage_in3_label.setBounds(6, 6, 763, 30);

        btn_left_page_in3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\left_arrow.png")); // NOI18N
        btn_left_page_in3.setBorder(null);
        btn_left_page_in3.setMaximumSize(new java.awt.Dimension(30, 30));
        btn_left_page_in3.setMinimumSize(new java.awt.Dimension(30, 30));
        btn_left_page_in3.setPreferredSize(new java.awt.Dimension(30, 30));
        btn_left_page_in3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_left_page_in3ActionPerformed(evt);
            }
        });
        contentPanel2.add(btn_left_page_in3);
        btn_left_page_in3.setBounds(18, 469, 30, 30);

        btn_right_page_in3.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\right_arrow.png")); // NOI18N
        btn_right_page_in3.setBorder(null);
        btn_right_page_in3.setMaximumSize(new java.awt.Dimension(30, 30));
        btn_right_page_in3.setMinimumSize(new java.awt.Dimension(30, 30));
        btn_right_page_in3.setPreferredSize(new java.awt.Dimension(30, 30));
        btn_right_page_in3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_right_page_in3ActionPerformed(evt);
            }
        });
        contentPanel2.add(btn_right_page_in3);
        btn_right_page_in3.setBounds(107, 469, 30, 30);

        in3_num_page.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        in3_num_page.setText("1");
        in3_num_page.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        contentPanel2.add(in3_num_page);
        in3_num_page.setBounds(54, 469, 47, 30);

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel7.setText("Mini documentation:");
        contentPanel2.add(jLabel7);
        jLabel7.setBounds(18, 511, 160, 17);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextPane2.setEditable(false);
        jTextPane2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextPane2.setText("Sur ce tableau, pour modifier un élément, selectionner dans le tableau et cliquer sur le bouton *edit*. \nPour supprimer  un élément du tabeau, selectionner l'élément et cliquer su le bouton *moins*.\nPour ajouter un nouveau élément dans le tableau, cliquer sur le bouton *plus*.");
        jTextPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextPane2.setSelectionColor(new java.awt.Color(29, 18, 59));
        jScrollPane3.setViewportView(jTextPane2);

        contentPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(6, 534, 763, 63);

        jPanel1.add(contentPanel2, "card2");

        contentPanel3.setLayout(null);

        js_ue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        js_ue.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        js_ue.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        ue_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Libelle", "Credit", "V.H. CM", "V.H. TD", "V.H. TP", "Categorie", "Filière", "Departement", "Faculté"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ue_table.setRowHeight(23);
        ue_table.setSelectionBackground(new java.awt.Color(29, 53, 87));
        ue_table.setSelectionForeground(new java.awt.Color(204, 204, 204));
        ue_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ue_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ue_table.setShowGrid(true);
        ue_table.getTableHeader().setReorderingAllowed(false);
        js_ue.setViewportView(ue_table);
        if (ue_table.getColumnModel().getColumnCount() > 0) {
            ue_table.getColumnModel().getColumn(0).setResizable(false);
            ue_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            ue_table.getColumnModel().getColumn(1).setPreferredWidth(150);
            ue_table.getColumnModel().getColumn(2).setResizable(false);
            ue_table.getColumnModel().getColumn(2).setPreferredWidth(50);
            ue_table.getColumnModel().getColumn(3).setResizable(false);
            ue_table.getColumnModel().getColumn(3).setPreferredWidth(60);
            ue_table.getColumnModel().getColumn(4).setResizable(false);
            ue_table.getColumnModel().getColumn(4).setPreferredWidth(60);
            ue_table.getColumnModel().getColumn(5).setResizable(false);
            ue_table.getColumnModel().getColumn(5).setPreferredWidth(60);
        }

        contentPanel3.add(js_ue);
        js_ue.setBounds(6, 180, 729, 274);

        ue_options.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        ue_options.setPreferredSize(new java.awt.Dimension(32, 137));

        btn_add_ue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_add_ue.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\add.png")); // NOI18N
        btn_add_ue.setToolTipText("Ajouter une salle");
        btn_add_ue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_add_ue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_add_ue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_add_ueMouseClicked(evt);
            }
        });

        btn_remove_ue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_remove_ue.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\minus.png")); // NOI18N
        btn_remove_ue.setToolTipText("Supprimer la salle");
        btn_remove_ue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_remove_ue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_remove_ue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_remove_ueMouseClicked(evt);
            }
        });

        btn_edit_ue.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\edit.png")); // NOI18N
        btn_edit_ue.setToolTipText("Modifier");
        btn_edit_ue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        btn_edit_ue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_edit_ue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_edit_ueMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ue_optionsLayout = new javax.swing.GroupLayout(ue_options);
        ue_options.setLayout(ue_optionsLayout);
        ue_optionsLayout.setHorizontalGroup(
            ue_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_add_ue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_remove_ue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ue_optionsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_edit_ue))
        );
        ue_optionsLayout.setVerticalGroup(
            ue_optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ue_optionsLayout.createSequentialGroup()
                .addComponent(btn_add_ue)
                .addGap(0, 0, 0)
                .addComponent(btn_remove_ue)
                .addGap(0, 0, 0)
                .addComponent(btn_edit_ue)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        contentPanel3.add(ue_options);
        ue_options.setBounds(735, 180, 34, 274);

        manage_ue_label.setBackground(new java.awt.Color(29, 53, 87));
        manage_ue_label.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        manage_ue_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manage_ue_label.setText("Tableau contenant la liste des ue des facultés");
        contentPanel3.add(manage_ue_label);
        manage_ue_label.setBounds(6, 91, 763, 30);

        btn_left_page_ue.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\left_arrow.png")); // NOI18N
        btn_left_page_ue.setBorder(null);
        btn_left_page_ue.setMaximumSize(new java.awt.Dimension(30, 30));
        btn_left_page_ue.setMinimumSize(new java.awt.Dimension(30, 30));
        btn_left_page_ue.setPreferredSize(new java.awt.Dimension(30, 30));
        btn_left_page_ue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_left_page_ueActionPerformed(evt);
            }
        });
        contentPanel3.add(btn_left_page_ue);
        btn_left_page_ue.setBounds(18, 460, 30, 30);

        btn_right_page_ue.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\right_arrow.png")); // NOI18N
        btn_right_page_ue.setBorder(null);
        btn_right_page_ue.setMaximumSize(new java.awt.Dimension(30, 30));
        btn_right_page_ue.setMinimumSize(new java.awt.Dimension(30, 30));
        btn_right_page_ue.setPreferredSize(new java.awt.Dimension(30, 30));
        btn_right_page_ue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_right_page_ueActionPerformed(evt);
            }
        });
        contentPanel3.add(btn_right_page_ue);
        btn_right_page_ue.setBounds(107, 460, 30, 30);

        ue_num_page.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ue_num_page.setText("1");
        ue_num_page.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        contentPanel3.add(ue_num_page);
        ue_num_page.setBounds(54, 461, 47, 29);

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel8.setText("Mini documentation:");
        contentPanel3.add(jLabel8);
        jLabel8.setBounds(18, 508, 160, 17);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextPane3.setEditable(false);
        jTextPane3.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextPane3.setText("Sur ce tableau, pour modifier un élément, selectionner dans le tableau et cliquer sur le bouton *edit*. \nPour supprimer  un élément du tabeau, selectionner l'élément et cliquer su le bouton *moins*.\nPour ajouter un nouveau élément dans le tableau, cliquer sur le bouton *plus*.");
        jTextPane3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextPane3.setSelectionColor(new java.awt.Color(29, 18, 59));
        jScrollPane4.setViewportView(jTextPane3);

        contentPanel3.add(jScrollPane4);
        jScrollPane4.setBounds(6, 531, 763, 68);

        faculties_ges.setBackground(new java.awt.Color(69, 123, 157));
        faculties_ges.setForeground(new java.awt.Color(255, 255, 255));
        faculties_ges.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        faculties_ges.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                faculties_gesMouseClicked(evt);
            }
        });

        faculties.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        faculties.setForeground(new java.awt.Color(255, 255, 255));
        faculties.setText("Facultés");

        faculty_count.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        faculty_count.setForeground(new java.awt.Color(255, 255, 255));
        faculty_count.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        faculty_count.setText("5");
        faculty_count.setToolTipText("");

        javax.swing.GroupLayout faculties_gesLayout = new javax.swing.GroupLayout(faculties_ges);
        faculties_ges.setLayout(faculties_gesLayout);
        faculties_gesLayout.setHorizontalGroup(
            faculties_gesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faculties_gesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(faculties)
                .addGap(18, 18, 18)
                .addComponent(faculty_count, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );
        faculties_gesLayout.setVerticalGroup(
            faculties_gesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faculties_gesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(faculties_gesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, faculties_gesLayout.createSequentialGroup()
                        .addComponent(faculties)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, faculties_gesLayout.createSequentialGroup()
                        .addComponent(faculty_count)
                        .addContainerGap())))
        );

        department_ges.setBackground(new java.awt.Color(230, 57, 70));
        department_ges.setForeground(new java.awt.Color(255, 255, 255));
        department_ges.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_ges.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_gesMouseClicked(evt);
            }
        });

        departments.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        departments.setForeground(new java.awt.Color(255, 255, 255));
        departments.setText("Departements");

        department_count.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        department_count.setForeground(new java.awt.Color(255, 255, 255));
        department_count.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        department_count.setText("15");
        department_count.setToolTipText("");

        javax.swing.GroupLayout department_gesLayout = new javax.swing.GroupLayout(department_ges);
        department_ges.setLayout(department_gesLayout);
        department_gesLayout.setHorizontalGroup(
            department_gesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(department_gesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(departments)
                .addGap(18, 18, 18)
                .addComponent(department_count, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        department_gesLayout.setVerticalGroup(
            department_gesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(department_gesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(department_gesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, department_gesLayout.createSequentialGroup()
                        .addComponent(departments)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, department_gesLayout.createSequentialGroup()
                        .addComponent(department_count, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        filieres_ges.setBackground(new java.awt.Color(42, 157, 143));
        filieres_ges.setForeground(new java.awt.Color(255, 255, 255));
        filieres_ges.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        filieres_ges.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filieres_gesMouseClicked(evt);
            }
        });

        filieres.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        filieres.setForeground(new java.awt.Color(255, 255, 255));
        filieres.setText("Filières");

        filiere_count.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        filiere_count.setForeground(new java.awt.Color(255, 255, 255));
        filiere_count.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        filiere_count.setText("10");
        filiere_count.setToolTipText("");

        javax.swing.GroupLayout filieres_gesLayout = new javax.swing.GroupLayout(filieres_ges);
        filieres_ges.setLayout(filieres_gesLayout);
        filieres_gesLayout.setHorizontalGroup(
            filieres_gesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filieres_gesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(filieres)
                .addGap(18, 18, 18)
                .addComponent(filiere_count, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                .addContainerGap())
        );
        filieres_gesLayout.setVerticalGroup(
            filieres_gesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filieres_gesLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(filieres_gesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, filieres_gesLayout.createSequentialGroup()
                        .addComponent(filieres)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, filieres_gesLayout.createSequentialGroup()
                        .addComponent(filiere_count)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(faculties_ges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(department_ges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(filieres_ges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(faculties_ges, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(department_ges, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(filieres_ges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        contentPanel3.add(jPanel5);
        jPanel5.setBounds(6, 6, 763, 73);

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel1.setText("Faculté:");

        edit_search_faculty.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        edit_search_faculty.setToolTipText("Faculté");
        edit_search_faculty.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        edit_search_faculty.setPreferredSize(new java.awt.Dimension(70, 24));
        edit_search_faculty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_search_facultyActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel2.setText("Departement:");

        edit_search_department.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        edit_search_department.setToolTipText("Departement");
        edit_search_department.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        edit_search_department.setPreferredSize(new java.awt.Dimension(70, 24));
        edit_search_department.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_search_departmentActionPerformed(evt);
            }
        });

        edit_search_filiere.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        edit_search_filiere.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        edit_search_filiere.setPreferredSize(new java.awt.Dimension(70, 24));
        edit_search_filiere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_search_filiereActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel3.setText("Filière:");

        edit_search_ue.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        edit_search_ue.setToolTipText("Code de la matière");
        edit_search_ue.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        edit_search_ue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_search_ueActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel4.setText("Code ue:");

        btn_search_ue.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\search.png")); // NOI18N
        btn_search_ue.setToolTipText("Rechercher");
        btn_search_ue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_ueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edit_search_faculty, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(edit_search_department, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edit_search_filiere, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edit_search_ue, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(btn_search_ue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_search_ue, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(edit_search_faculty, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(edit_search_department, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(edit_search_filiere, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(edit_search_ue, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        contentPanel3.add(jPanel6);
        jPanel6.setBounds(6, 127, 763, 47);

        jPanel1.add(contentPanel3, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(nav_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(app_options, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(app_options, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(nav_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void close_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_btnMouseClicked
        System.exit(0);
    }//GEN-LAST:event_close_btnMouseClicked

    private void close_btnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_btnMouseEntered
        close_btn.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\cancel_hover.png")); // NOI18N

    }//GEN-LAST:event_close_btnMouseEntered

    private void close_btnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close_btnMouseExited
        close_btn.setIcon(new javax.swing.ImageIcon("C:\\Users\\ronal\\Documents\\NetBeansProjects\\gesUniversity\\src\\main\\java\\com\\roncoder\\gesuniversity\\frames\\images\\cancel.png")); // NOI18N

    }//GEN-LAST:event_close_btnMouseExited

    private void nav_1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nav_1MouseClicked
        setNav(0);
    }//GEN-LAST:event_nav_1MouseClicked

    private void nav_2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nav_2MouseClicked
        setNav(1);
    }//GEN-LAST:event_nav_2MouseClicked

    private void nav_3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nav_3MouseClicked
        setNav(2);
    }//GEN-LAST:event_nav_3MouseClicked

    private void btn_search_salleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_salleActionPerformed
        String search_salle = edit_search_salle.getText();
        salles_search(search_salle);
    }//GEN-LAST:event_btn_search_salleActionPerformed

    private void edit_search_salleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_search_salleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_search_salleActionPerformed

    private void btn_left_page_salleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_left_page_salleActionPerformed

    }//GEN-LAST:event_btn_left_page_salleActionPerformed

    private void btn_right_page_salleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_right_page_salleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_right_page_salleActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        this.setState(Dashboard.ICONIFIED);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void edit_search_in3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_search_in3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_search_in3ActionPerformed

    private void btn_search_in3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_in3ActionPerformed
        String search_in3 = this.edit_search_in3.getText();
        in3_search(search_in3);
    }//GEN-LAST:event_btn_search_in3ActionPerformed

    private void btn_left_page_in3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_left_page_in3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_left_page_in3ActionPerformed

    private void btn_right_page_in3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_right_page_in3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_right_page_in3ActionPerformed

    private void edit_search_ueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_search_ueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_search_ueActionPerformed

    private void btn_search_ueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_ueActionPerformed
        String search_faculty = this.edit_search_faculty.getText();
        String search_department = this.edit_search_department.getText();
        String search_filiere = this.edit_search_filiere.getText();
        String search_ue = this.edit_search_ue.getText();
        ue_search(search_faculty, search_department, search_filiere, search_ue);
    }//GEN-LAST:event_btn_search_ueActionPerformed

    private void btn_left_page_ueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_left_page_ueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_left_page_ueActionPerformed

    private void btn_right_page_ueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_right_page_ueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_right_page_ueActionPerformed

    private void edit_search_filiereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_search_filiereActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_search_filiereActionPerformed

    private void edit_search_departmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_search_departmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_search_departmentActionPerformed

    private void edit_search_facultyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_search_facultyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_search_facultyActionPerformed

    private void faculties_gesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_faculties_gesMouseClicked
        if (!is_admin) {
            return;
        }
        new FacultyFrame(this).show();
    }//GEN-LAST:event_faculties_gesMouseClicked

    private void department_gesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_gesMouseClicked
        if (!is_admin) {
            return;
        }
        new DepartmentFrame(this).show();
    }//GEN-LAST:event_department_gesMouseClicked

    private void filieres_gesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filieres_gesMouseClicked
        if (!is_admin) {
            return;
        }
        new FiliereFrame(this).show();
    }//GEN-LAST:event_filieres_gesMouseClicked

    private void btn_add_salleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_add_salleMouseClicked
        new SalleDialog(this).show();
    }//GEN-LAST:event_btn_add_salleMouseClicked

    private void btn_add_in3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_add_in3MouseClicked
        new MatiereDialog(this).show();
    }//GEN-LAST:event_btn_add_in3MouseClicked

    private void btn_edit_salleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_edit_salleMouseClicked
        if (salle != null) {
            new SalleDialog(salle, this).show();
        }
    }//GEN-LAST:event_btn_edit_salleMouseClicked

    private void btn_remove_salleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_remove_salleMouseClicked
        if (selectedRow != -1) {
            int decision = JOptionPane.showConfirmDialog(null, "Souhait-vous vraiment supprimer cette salle ?");
            if (decision == 0) {
                DefaultTableModel salle_model = (DefaultTableModel) salles_table.getModel();
                if (salle.delete()) {
                    salle_model.removeRow(salles_table.getSelectedRow());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un élément à supprimer");
        }
    }//GEN-LAST:event_btn_remove_salleMouseClicked

    private void edit_search_sallePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_edit_search_sallePropertyChange

    }//GEN-LAST:event_edit_search_sallePropertyChange

    private void btn_remove_in3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_remove_in3MouseClicked
        if (selectedRow != -1) {
            int decision = JOptionPane.showConfirmDialog(null, "Souhait-vous vraiment supprer cette salle ?");
            if (decision == 0) {
                DefaultTableModel in3_model = (DefaultTableModel) in3_table.getModel();
                if (matiere.delete()) {
                    in3_model.removeRow(in3_table.getSelectedRow());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un élément à supprimer");
        }
    }//GEN-LAST:event_btn_remove_in3MouseClicked

    private void btn_edit_in3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_edit_in3MouseClicked
        if (matiere != null) {
            new MatiereDialog(matiere, this).show();
        }
    }//GEN-LAST:event_btn_edit_in3MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        this.setVisible(false);
        new LoginFrame().setVisible(true);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void btn_add_ueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_add_ueMouseClicked
        new UEFrame(this).show();
    }//GEN-LAST:event_btn_add_ueMouseClicked

    private void btn_remove_ueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_remove_ueMouseClicked
        if (selectedRow != -1) {
            int decision = JOptionPane.showConfirmDialog(null, "Souhait-vous vraiment supprimer cette UE ?");
            if (decision == 0) {
                DefaultTableModel ue_model = (DefaultTableModel) ue_table.getModel();
                if (ue.delete()) {
                    ue_model.removeRow(ue_table.getSelectedRow());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un élément à supprimer");
        }
    }//GEN-LAST:event_btn_remove_ueMouseClicked

    private void btn_edit_ueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_edit_ueMouseClicked
        if (ue != null) {
            new UEFrame(ue, this).show();
        }
    }//GEN-LAST:event_btn_edit_ueMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Set the Nimbus look and feel 
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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>
        // Create and display the form 
        /* java.awt.EventQueue.invokeLater(() -> {
            new Dashboard(true).setVisible(true);
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel app_description;
    private javax.swing.JLabel app_name;
    private javax.swing.JPanel app_options;
    private javax.swing.JLabel btn_add_in3;
    private javax.swing.JLabel btn_add_salle;
    private javax.swing.JLabel btn_add_ue;
    private javax.swing.JLabel btn_edit_in3;
    private javax.swing.JLabel btn_edit_salle;
    private javax.swing.JLabel btn_edit_ue;
    private javax.swing.JButton btn_left_page_in3;
    private javax.swing.JButton btn_left_page_salle;
    private javax.swing.JButton btn_left_page_ue;
    private javax.swing.JLabel btn_remove_in3;
    private javax.swing.JLabel btn_remove_salle;
    private javax.swing.JLabel btn_remove_ue;
    private javax.swing.JButton btn_right_page_in3;
    private javax.swing.JButton btn_right_page_salle;
    private javax.swing.JButton btn_right_page_ue;
    private javax.swing.JButton btn_search_in3;
    private javax.swing.JButton btn_search_salle;
    private javax.swing.JButton btn_search_ue;
    private javax.swing.JLabel close_btn;
    private javax.swing.JPanel contentPanel1;
    private javax.swing.JPanel contentPanel2;
    private javax.swing.JPanel contentPanel3;
    private javax.swing.JLabel department_count;
    private javax.swing.JPanel department_ges;
    private javax.swing.JLabel departments;
    private javax.swing.JTextField edit_search_department;
    private javax.swing.JTextField edit_search_faculty;
    private javax.swing.JTextField edit_search_filiere;
    private javax.swing.JTextField edit_search_in3;
    private javax.swing.JTextField edit_search_salle;
    private javax.swing.JTextField edit_search_ue;
    private javax.swing.JLabel faculties;
    private javax.swing.JPanel faculties_ges;
    private javax.swing.JLabel faculty_count;
    private javax.swing.JLabel filiere_count;
    private javax.swing.JLabel filieres;
    private javax.swing.JPanel filieres_ges;
    private javax.swing.JLabel icon_1;
    private javax.swing.JLabel icon_2;
    private javax.swing.JLabel icon_3;
    private javax.swing.JLabel in3_num_page;
    private javax.swing.JPanel in3_options;
    private javax.swing.JTable in3_table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JScrollPane js_in3;
    private javax.swing.JScrollPane js_ts;
    private javax.swing.JScrollPane js_ue;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel manage_in3_label;
    private javax.swing.JLabel manage_salle_label;
    private javax.swing.JLabel manage_ue_label;
    private javax.swing.JPanel nav_1;
    private javax.swing.JPanel nav_2;
    private javax.swing.JPanel nav_3;
    private javax.swing.JPanel nav_bar;
    private javax.swing.JPanel nav_header;
    private javax.swing.JLabel salle_num_page;
    private javax.swing.JTable salles_table;
    private javax.swing.JLabel title_1;
    private javax.swing.JLabel title_2;
    private javax.swing.JLabel title_3;
    private javax.swing.JPanel ts_options;
    private javax.swing.JLabel ue_num_page;
    private javax.swing.JPanel ue_options;
    private javax.swing.JTable ue_table;
    // End of variables declaration//GEN-END:variables

    private void salles_search(String search_salle) {
        salle_table_model.setRowCount(0);
        try {
            db.query("SELECT * FROM salles WHERE code LIKE '%" + search_salle + "%' "
                    + "OR libelle LIKE '%" + search_salle + "%' ");
            ResultSet result = db.get();
            while (result.next()) {
                salle_table_model.addRow(new Object[]{
                    result.getString("code"),
                    result.getString("libelle"),
                    result.getInt("capacity"),
                    result.getInt("num_campus"),
                    result.getString("localisation")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void in3_search(String search_salle) {
        in3_table_model.setRowCount(0);
        try {
            db.query("SELECT * FROM matieres WHERE code LIKE '%" + search_salle + "%' "
                    + "OR libelle LIKE '%" + search_salle + "%' ");
            ResultSet result = db.get();
            while (result.next()) {
                in3_table_model.addRow(new Object[]{
                    result.getString("code"),
                    result.getString("libelle"),
                    result.getInt("nb_credit"),
                    result.getString("vol_cm"),
                    result.getString("vol_td"),
                    result.getString("vol_tp"),
                    result.getString("category")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ue_search(String search_faculty, String search_department, String search_filiere, String search_ue) {
        ue_table_model.setRowCount(0);
        String query = "SELECT ues.*, fi.code as filiere, de.code as department, fa.code as faculty "
                + "FROM ues INNER JOIN filieres as fi ON ues.filiere_code = fi.code "
                + "INNER JOIN departments as de ON fi.department_code = de.code "
                + "INNER JOIN faculties as fa ON de.faculty_code = fa.code ";
        if (!search_faculty.isEmpty()) {
            query += "WHERE fa.code LIKE '%" + search_faculty + "%' "
                    + "OR fa.libelle LIKE '%" + search_faculty + "%' ";
        } else if (!search_department.isEmpty()) {
            query += "WHERE de.code LIKE '%" + search_department + "%' "
                    + "OR de.libelle LIKE '%" + search_department + "%' ";
        } else if (!search_filiere.isEmpty()) {
            query += "WHERE fi.code LIKE '%" + search_filiere + "%' "
                    + "OR fi.libelle LIKE '%" + search_filiere + "%' ";
        } else if (!search_ue.isEmpty()) {
            query += "WHERE ues.code LIKE '%" + search_ue + "%' "
                    + "OR ues.libelle LIKE '%" + search_ue + "%' ";
        }

        try {
            db.query(query);
            ResultSet result = db.get();
            while (result.next()) {
                ue_table_model.addRow(new Object[]{
                    result.getString("code"),
                    result.getString("libelle"),
                    result.getInt("nb_credit"),
                    result.getInt("vol_cm"),
                    result.getInt("vol_td"),
                    result.getInt("vol_tp"),
                    result.getString("category"),
                    result.getString("filiere"),
                    result.getString("department"),
                    result.getString("faculty")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
