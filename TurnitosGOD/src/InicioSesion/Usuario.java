/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InicioSesion;
import Conexion_base_datos_turnos.Conexion;
/**
 *
 * @author alnog
 */
public class Usuario {

    private String NombreUsurio;
    private String Contraseña;
    private String RolUsuario;
    Conexion conexion = new Conexion();
    private int IdEmpleado;

    public Usuario(String NombreUsurio, String Contraseña, String RolUsuario) {
        this.NombreUsurio = NombreUsurio;
        this.Contraseña = Contraseña;
        this.RolUsuario = RolUsuario;
    }

    public String getNombreUsurio() {
        return NombreUsurio;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public String getRolUsuario() {
        return RolUsuario;
    }

    public int getIdEmpleado() {
        return IdEmpleado;
    }
    
}
