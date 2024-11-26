/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package CrearEmpleados;

import Conexion_base_datos_turnos.Conexion;
import InicioSesion.Usuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author alnog
 */
public class Frm_CrearEmpleados extends javax.swing.JFrame {

    private ArrayList<Empleado> ListaEmpleados;
    private final String[] CargosEmpleados = {"Cajero", "Ejecutivo de credito"};
    private final String UsuarioAdministrador = "Mejia";
    private final String ContraseñaAdministrador = "321";
    Conexion conexion = new Conexion();

    public Frm_CrearEmpleados() {
        initComponents();
        ListaEmpleados = new ArrayList<>();
        Date fechaDate = null;
        try {
            fechaDate = new SimpleDateFormat("dd/MM/yyyy").parse("24/10/2024");
        } catch (ParseException ex) {
            Logger.getLogger(Frm_CrearEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        RellenarComboBox();
    }

    private void RellenarComboBox() {
        CB_Cargo_Empleado.removeAllItems();
        CB_Cargo_Empleado.addItem("Seleccione...");
        for (String Cargo : CargosEmpleados) {
            CB_Cargo_Empleado.addItem(Cargo);
        }
    }

    private boolean Buscar_Empleado() {
        boolean Encontrado = false;
        ListaEmpleados = conexion.LlenarListaEmpledos(ListaEmpleados);
        for (Empleado Empleado : ListaEmpleados) {
            if (Empleado.getNumero_de_identificacion() == Long.parseLong(TxT_Numero_Identificacion.getText())) {
                Encontrado = true;
            }
        }
        return Encontrado;
    }

    private void RellenarCampos() {
        String numeroIdentificacionText = TxT_Numero_Identificacion.getText().trim();
        String nombre = TxT_Nombre.getText().trim();
        String apellido = TxT_Apellido.getText().trim();
        String cargo = CB_Cargo_Empleado.getSelectedItem().toString();
        String contrasena = TxT_Contraseña.getText().trim();
        long numeroIdentificacion;

        if (!validarCampos(numeroIdentificacionText, nombre, apellido, cargo, contrasena)) {
            return;
        }
        numeroIdentificacionText = numeroIdentificacionText.replace(".", "");
        try {
            numeroIdentificacion = Long.parseLong(numeroIdentificacionText);
        } catch (NumberFormatException e) {
            mostrarMensaje("El número de identificación no es válido", "error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (date_Fecha_ingreso.getDate() == null) {
            mostrarMensaje("Ingresa una fecha válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int idEmpleado = conexion.ConseguirIdEmpleado();
            Empleado empleado = new Empleado(idEmpleado,nombre, apellido, cargo, date_Fecha_ingreso.getDate(), numeroIdentificacion);
            conexion.InsertarEmpleados(empleado);
            conexion.InsertarUsuario(new Usuario(nombre, contrasena, "Empleado"));
            mostrarMensaje("Usuario: " + nombre + "\n" + "Contraseña: " + contrasena, "Empleado Creado con éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException e) {
            mostrarMensaje("Ingresa una fecha válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos(String numeroIdentificacionText, String nombre, String apellido, String cargo, String contrasena) {

        numeroIdentificacionText = numeroIdentificacionText.replaceAll("[^\\d]", ""); 

        if (numeroIdentificacionText.isEmpty()) {
            mostrarMensaje("El número de identificación no puede estar en blanco", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (nombre.isEmpty()) {
            mostrarMensaje("El nombre no puede estar en blanco", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (apellido.isEmpty()) {
            mostrarMensaje("El apellido no puede estar en blanco", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cargo.equalsIgnoreCase("Seleccione...")) {
            mostrarMensaje("Debe seleccionar un cargo", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (contrasena.isEmpty()) {
            mostrarMensaje("La contraseña no puede estar en blanco", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (Buscar_Empleado()) {
            mostrarMensaje("Este Empleado ya existe", "Crear usuario", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public ArrayList<Empleado> getListaEmpleados() {
        return ListaEmpleados;
    }

    private void mostrarMensaje(String mensaje, String titulo, int icon) {
        JOptionPane.showMessageDialog(rootPane, mensaje, titulo, icon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jDayChooser1 = new com.toedter.calendar.JDayChooser();
        jPanel10 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        TxT_Nombre = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        TxT_Apellido = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        TxT_Contraseña = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        TxT_Numero_Identificacion = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        date_Fecha_ingreso = new com.toedter.calendar.JDateChooser();
        jLabel50 = new javax.swing.JLabel();
        Btn_Crear_Usuario = new javax.swing.JButton();
        CB_Cargo_Empleado = new javax.swing.JComboBox<>();

        jLabel6.setText("jLabel6");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel51.setText("Nombre");

        jLabel52.setText("Apellido");

        jLabel53.setText("Cargo");

        jLabel54.setText("Contraseña");

        jLabel55.setText("Numero identificacion");

        jLabel56.setText("Fecha de ingreso");

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/OIP-removebg-preview (1).png"))); // NOI18N
        jLabel50.setText("Crear usuario");

        Btn_Crear_Usuario.setText("Crear usuario");
        Btn_Crear_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Crear_UsuarioActionPerformed(evt);
            }
        });

        CB_Cargo_Empleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(jLabel55))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TxT_Numero_Identificacion, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(TxT_Nombre)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52)
                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54)
                            .addComponent(jLabel56))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(TxT_Contraseña, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxT_Apellido)
                            .addComponent(date_Fecha_ingreso, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(CB_Cargo_Empleado, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(16, 16, 16))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel50)
                .addContainerGap(95, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(Btn_Crear_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(TxT_Numero_Identificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(TxT_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(TxT_Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(CB_Cargo_Empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(TxT_Contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(date_Fecha_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addGap(18, 18, 18)
                .addComponent(Btn_Crear_Usuario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_Crear_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Crear_UsuarioActionPerformed
        RellenarCampos();
    }//GEN-LAST:event_Btn_Crear_UsuarioActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_CrearEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_CrearEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_CrearEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_CrearEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Crear_Usuario;
    private javax.swing.JComboBox<String> CB_Cargo_Empleado;
    private javax.swing.JTextField TxT_Apellido;
    private javax.swing.JTextField TxT_Contraseña;
    private javax.swing.JTextField TxT_Nombre;
    private javax.swing.JTextField TxT_Numero_Identificacion;
    private com.toedter.calendar.JDateChooser date_Fecha_ingreso;
    private com.toedter.calendar.JDayChooser jDayChooser1;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel10;
    // End of variables declaration//GEN-END:variables
}
