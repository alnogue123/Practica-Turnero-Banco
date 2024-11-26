/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto.FormularioRegistro;

import java.sql.Date;

/**
 *
 * @author alnog
 */
public class Cliente {
    private int IdCliente;
    private String TipoIdendificacion;
    private double NumeroIdentificacion = 0;
    private String nombre;
    private String Apellido;
    private final String Turno;
    private final String modulo;
    private int Estado;
    private String motivo;
    private Date TiempoEsperado = null;
    
    public Cliente(String TipoIdendificacion, double NumeroIdentificacion, String nombre, String Apellido, String Turno, String modulo, int Estado, String motivo, int IdCliente) {
        this.IdCliente = IdCliente;
        this.TipoIdendificacion = TipoIdendificacion;
        this.NumeroIdentificacion = NumeroIdentificacion;
        this.nombre = nombre;
        this.Apellido = Apellido;
        this.Turno = Turno;
        this.modulo = modulo;
        this.Estado = Estado;
        this.motivo = motivo;
    }
    
    public int getIdCliente () {
        return IdCliente;
    }
    
    public Date getTiempoEsperado() {
        return TiempoEsperado;
    }
    
    public String getTipoIdendificacion() {
        return TipoIdendificacion;
    }

    public double getNumeroIdentificacion() {
        return NumeroIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getTurno() {
        return Turno;
    }

    public String getModulo() {
        return modulo;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int Estado) {
        this.Estado = Estado;
    }   
    
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
