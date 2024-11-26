/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto.folder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alnog
 */
public class EstilosDeLaTabla extends DefaultTableCellRenderer {

    private boolean HeaderActivado = false;
    private boolean CuerpoTablaActivado = false;
    private int filas;

    public EstilosDeLaTabla() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
    }

    public void setHeaderActivado(Boolean HeaderActivado) {
        this.HeaderActivado = HeaderActivado;
    }

    public void setCuerpoTablaActivado(boolean CuerpoTablaActivado) {
        this.CuerpoTablaActivado = CuerpoTablaActivado;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (HeaderActivado) {
            setText(value != null ? value.toString() : "");
            setBackground(new Color(104, 62, 130));
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 18));
        } else if (CuerpoTablaActivado) {
            setText(value != null ? value.toString() : "");

            if (row % 2 == 0) {
                setBackground(new Color(127, 86, 132));
            } else {
                setBackground(new Color(127, 106, 132));
            }
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.PLAIN, 16));
        }
        if (isSelected) {
            setBackground(new Color(85, 107, 47));
            setForeground(Color.YELLOW);
        }
        return c;
    }
}
