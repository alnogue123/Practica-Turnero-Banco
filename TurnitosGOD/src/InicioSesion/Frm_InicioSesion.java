/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package InicioSesion;

import CrearEmpleados.Frm_CrearEmpleados;
import CrearEmpleados.Empleado;
import Proyecto.FormularioRegistro.Frm_Registros;
import Proyecto.Frm_Turnos;
import Reportes.Frm_Reportes;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import Conexion_base_datos_turnos.Conexion;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.util.HashMap;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author mjiad
 */
public class Frm_InicioSesion extends javax.swing.JFrame {

    /**
     * Creates new form Frm_InicioSesion
     */
    private static Frm_InicioSesion InicioSesion;
    private static Frm_CrearEmpleados CrearEmpledos;
    private static Frm_Registros Registros;
    private static Frm_Reportes Reportes;
    private static Frm_Turnos Turnitos;
    public String Sesion = "";
    private ArrayList<Empleado> ListaEmpleados;
    private Conexion Conexion = new Conexion();
    private ArrayList<Usuario> ListaUsuarios;
    private String usuario;
    private String contraseña;

    public Frm_InicioSesion() {
        initComponents();
        ListaUsuarios = new ArrayList<>();
        ListaEmpleados = new ArrayList<>();
        Listener();
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
        this.toFront();
    }

    public static Frm_InicioSesion getInicioSesion() {
        return InicioSesion;
    }

    public static Frm_CrearEmpleados getCrearEmpledos() {
        return CrearEmpledos;
    }

    public static Frm_Registros getRegistros() {
        return Registros;
    }

    public static Frm_Reportes getReportes() {
        return Reportes;
    }

    public static Frm_Turnos getTurnitos() {
        return Turnitos;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Txt_Usuario = new javax.swing.JTextField();
        Txt_Contraseña = new javax.swing.JPasswordField();
        Btn_Ingresar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("INICIO DE SESION");
        setName("FrameInicio"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(106, 62, 130));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/boton-de-cuenta-redonda-con-usuario-dentro.png"))); // NOI18N
        jLabel1.setText("  Iniciar Sesión");

        Txt_Usuario.setBackground(new java.awt.Color(27, 0, 58));
        Txt_Usuario.setForeground(new java.awt.Color(137, 226, 59));

        Txt_Contraseña.setBackground(new java.awt.Color(27, 0, 58));
        Txt_Contraseña.setForeground(new java.awt.Color(137, 226, 59));

        Btn_Ingresar.setBackground(new java.awt.Color(27, 0, 58));
        Btn_Ingresar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Btn_Ingresar.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Ingresar.setText("INGRESAR");
        Btn_Ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_IngresarActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Contraseña:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 57, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Txt_Contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Txt_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(Btn_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(2, 2, 2)
                .addComponent(Txt_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Txt_Contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Btn_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_IngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_IngresarActionPerformed
        usuario = Txt_Usuario.getText();
        contraseña = String.valueOf(Txt_Contraseña.getPassword());
        IniciarSesion(usuario, contraseña);
    }//GEN-LAST:event_Btn_IngresarActionPerformed

    public String getUsuario() {
        return usuario;
    }

    public JTextField getTxt_Usuario() {
        return Txt_Usuario;
    }

    public JPasswordField getTxt_Contraseña() {
        return Txt_Contraseña;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void IniciarSesion(String Usuario, String Contraseña) {
        ListaUsuarios = Conexion.LlenarListaUsuarios(ListaUsuarios);
        ListaEmpleados = Conexion.LlenarListaEmpledos(ListaEmpleados);
        HashMap<String, Empleado> empleadosMap = new HashMap<>();
        for (Empleado empleado : ListaEmpleados) {
            empleadosMap.put(empleado.getNombre(), empleado);
        }
        Usuario Usuario_Encontrado = null;
        Empleado EmpleadoLogeado;
        for (Usuario usr : ListaUsuarios) {
            if (usr.getNombreUsurio().equalsIgnoreCase(Txt_Usuario.getText())) {
                Usuario_Encontrado = usr;
                EmpleadoLogeado = empleadosMap.get(usr.getNombreUsurio());
                Registros.setEmpleadoLogueado(EmpleadoLogeado);
                break;
            }
        }
        EscribirUsuarios();

        if (Usuario_Encontrado == null) {
            JOptionPane.showMessageDialog(rootPane, "El nombre de usuario no esta registrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Usuario_Encontrado.getContraseña().equals(String.valueOf(Txt_Contraseña.getPassword()))) {
            JOptionPane.showMessageDialog(rootPane, "El nombre de usuario no esta registrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Sesion = Usuario_Encontrado.getRolUsuario().equalsIgnoreCase("Administrador") ? "Administrador" : "Empleado";
        Registros.Sesion(Sesion);
        Registros.setVisible(true);
        Txt_Usuario.setText("");
        Txt_Contraseña.setText(""); 
        this.dispose();
    }

    public void EscribirUsuarios() {
        for (Usuario usr : ListaUsuarios) {
            System.out.println(usr.getNombreUsurio());
            System.out.println(usr.getContraseña());
        }
    }

    public void Listener() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                int eleccion = JOptionPane.showConfirmDialog(rootPane, "¿quieres cerrar el programa? \n"
                        + "(se tomara como que el dia termino y se reiniciaran los turnos)", "alerta", JOptionPane.YES_NO_OPTION,
                         JOptionPane.WARNING_MESSAGE);
                if (eleccion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_InicioSesion.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_InicioSesion.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_InicioSesion.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_InicioSesion.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InicioSesion = new Frm_InicioSesion();
                CrearEmpledos = new Frm_CrearEmpleados();
                Turnitos = new Frm_Turnos();
                Registros = new Frm_Registros(InicioSesion);
                Reportes = new Frm_Reportes(Registros);
                InicioSesion.setVisible(true);
                Turnitos.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Ingresar;
    private javax.swing.JPasswordField Txt_Contraseña;
    private javax.swing.JTextField Txt_Usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
