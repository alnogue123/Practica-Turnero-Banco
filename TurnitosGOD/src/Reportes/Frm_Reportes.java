/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Reportes;

import Proyecto.FormularioRegistro.Frm_Registros;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import Conexion_base_datos_turnos.Conexion;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 *
 * @author mjiad
 */
public final class Frm_Reportes extends JDialog {

    private String NombreHolaArhivo = "";
    private String[] ListaCombo = {"clientes atendidos", "Clientes que abandonaron",
        "Tiempo promedio de espera por cliente", "Reporte productividad de empleados"};
    private Conexion conexion;

    public Frm_Reportes(Frm_Registros registros) {
        super(registros, true);
        initComponents();
        InicializarClases();
        llenaComboBox();
        RellenarTablos(0);
        RellenarComboBox();
        DefaultTableModel modelo = new DefaultTableModel();
        Tabla_Reportes.setModel(modelo);
    }

    private void RellenarComboBox() {
        CB_PeriodoTiempo.removeAllItems();
        CB_PeriodoTiempo.addItem("Seleccione");
        CB_PeriodoTiempo.addItem("Dia");
        CB_PeriodoTiempo.addItem("Semana");
        CB_PeriodoTiempo.addItem("mes");
        CB_PeriodoTiempo.setVisible(false);
    }

    private void InicializarClases() {
        conexion = new Conexion();
    }

    public void RellenarTablos(int Indice) {
        DefaultTableModel model = new DefaultTableModel();
        DecimalFormat df = new DecimalFormat("#");
        switch (Indice) {
            case 1:
                NombreHolaArhivo = ListaCombo[Indice - 1];
                CB_PeriodoTiempo.setVisible(true);
                conexion.ConsultasSql("Error al buscar los clientes atendidos", "call MostrarClientesAtendidos()", model, Tabla_Reportes);
                ClientesAtendidosPorDiaSemanaoMes(model);
                break;
            case 2:
                NombreHolaArhivo = ListaCombo[Indice - 1];
                CB_PeriodoTiempo.setVisible(false);
                conexion.ConsultasSql("Error al buscar los clientes que abandonaron",
                        "call MostrarClientesQueAbandonron()", model, Tabla_Reportes);
                break;
            case 3:
                NombreHolaArhivo = ListaCombo[Indice - 1];
                CB_PeriodoTiempo.setVisible(false);
                conexion.ConsultasSql("Hubo un error en la busqueda",
                        "call TiempoEsperaPorCliente()", model, Tabla_Reportes);
                break;
            case 4:
                NombreHolaArhivo = ListaCombo[Indice - 1];
                CB_PeriodoTiempo.setVisible(false);
                conexion.ConsultasSql("Error al buscar los clientes atendidos",
                        "call MostrarProductividadEmpleados()", model, Tabla_Reportes);
                break;
            default:
                break;
        }
        Tabla_Reportes.setModel(model);
    }

    private void ClientesAtendidosPorDiaSemanaoMes(DefaultTableModel model) {
        switch (CB_PeriodoTiempo.getSelectedIndex()) {
            case 0:
                NombreHolaArhivo = ListaCombo[0];
                model.setColumnCount(0);
                conexion.ConsultasSql("Error al buscar los clientes atendidos", "call MostrarClientesAtendidos()", model, Tabla_Reportes);
                break;
            case 1:
                NombreHolaArhivo = "Numero de clientes atendidos por dia";
                model.setColumnCount(0);
                conexion.ConsultasSql("Error al buscar los clientes atendidos", "call MostrarClientesAtendidosEnElDia()", model, Tabla_Reportes);
                break;
            case 2:
                NombreHolaArhivo = "Numero de clientes atendidos por semana";
                model.setColumnCount(0);
                conexion.ConsultasSql("Error al buscar los clientes atendidos", "call MostrarClientesAtendidosEnLaSemana()", model, Tabla_Reportes);
                break;
            case 3:
                NombreHolaArhivo = "Numero de clientes atendidos por mes";
                model.setColumnCount(0);
                conexion.ConsultasSql("Error al buscar los clientes atendidos", "call MostrarClientesAtendidosEnElMes()", model, Tabla_Reportes);
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        ComoBobox_Reportes = new javax.swing.JComboBox<>();
        Btn_ExportarExcel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla_Reportes = new javax.swing.JTable();
        CB_PeriodoTiempo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reportes");

        jLabel1.setText("Reportes");

        ComoBobox_Reportes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComoBobox_Reportes.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                ComoBobox_ReportesAncestorRemoved(evt);
            }
        });
        ComoBobox_Reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComoBobox_ReportesActionPerformed(evt);
            }
        });

        Btn_ExportarExcel.setText("Exportar Reporte (Excel)");
        Btn_ExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_ExportarExcelActionPerformed(evt);
            }
        });

        Tabla_Reportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(Tabla_Reportes);

        CB_PeriodoTiempo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CB_PeriodoTiempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CB_PeriodoTiempoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(534, 534, 534))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ComoBobox_Reportes, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Btn_ExportarExcel, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CB_PeriodoTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComoBobox_Reportes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_ExportarExcel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CB_PeriodoTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void llenaComboBox() {
        ComoBobox_Reportes.removeAllItems();
        ComoBobox_Reportes.addItem("Seleccione...");
        for (String ListaCombo1 : ListaCombo) {
            ComoBobox_Reportes.addItem(ListaCombo1);
        }
    }

    private void ComoBobox_ReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComoBobox_ReportesActionPerformed
        if (ComoBobox_Reportes.getSelectedIndex() > 0) {
            RellenarTablos(ComoBobox_Reportes.getSelectedIndex());
        }
    }//GEN-LAST:event_ComoBobox_ReportesActionPerformed

    public void EscogerRuta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escoja la ruta donde exportar el reporte");
        fileChooser.setSelectedFile(new File(NombreHolaArhivo));
        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoGuardado = fileChooser.getSelectedFile();
            String ruta = archivoGuardado.getAbsolutePath() + ".xls";
            try {
                ExportToExcel(ruta);
            } catch (IOException | WriteException ex) {
                Logger.getLogger(Frm_Reportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void ExportToExcel(String ruta) throws IOException, WriteException {
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(new File(ruta));
            WritableSheet sheet = workbook.createSheet(NombreHolaArhivo, 0);
            for (int i = 0; i < Tabla_Reportes.getColumnCount(); i++) {
                Label label = new Label(i, 0, Tabla_Reportes.getColumnName(i));
                sheet.addCell(label);
            }
            for (int fila = 0; fila < Tabla_Reportes.getRowCount(); fila++) {
                for (int colum = 0; colum < Tabla_Reportes.getColumnCount(); colum++) {
                    Object value = Tabla_Reportes.getValueAt(fila, colum);
                    Label label = new Label(colum, fila + 1, value != null ? value.toString() : "");
                    sheet.addCell(label);
                }
            }
            workbook.write();
            JOptionPane.showMessageDialog(Tabla_Reportes, "Informe exportado", "Exportar Informe", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | WriteException e) {
            JOptionPane.showMessageDialog(Tabla_Reportes, "No se pudo exportar", "Exportar informe", JOptionPane.ERROR_MESSAGE);
            throw e;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }


    private void Btn_ExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ExportarExcelActionPerformed
        if (ComoBobox_Reportes.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Escoja un opcion a Reportar");
        } else {
            EscogerRuta();
        }
    }//GEN-LAST:event_Btn_ExportarExcelActionPerformed

    private void ComoBobox_ReportesAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ComoBobox_ReportesAncestorRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_ComoBobox_ReportesAncestorRemoved

    private void CB_PeriodoTiempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CB_PeriodoTiempoActionPerformed
        ClientesAtendidosPorDiaSemanaoMes((DefaultTableModel) Tabla_Reportes.getModel());
    }//GEN-LAST:event_CB_PeriodoTiempoActionPerformed

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
            java.util.logging.Logger.getLogger(Frm_Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_ExportarExcel;
    private javax.swing.JComboBox<String> CB_PeriodoTiempo;
    private javax.swing.JComboBox<String> ComoBobox_Reportes;
    private javax.swing.JTable Tabla_Reportes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
