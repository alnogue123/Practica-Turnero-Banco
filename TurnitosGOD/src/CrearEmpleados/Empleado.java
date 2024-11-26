
package CrearEmpleados;

import java.util.Date;

public class Empleado {
    private int idEmpleado;
    private String Nombre,Apellido,Cargo;
    private long Numero_de_identificacion;
    private Date Fecha_de_ingreso;

    public Empleado(int id, String Nombre, String Apellido, String Cargo, Date Fecha_de_ingreso, long Numero_de_identificacion) {
        this.idEmpleado = id;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Cargo = Cargo;
        this.Fecha_de_ingreso = Fecha_de_ingreso;
        this.Numero_de_identificacion = Numero_de_identificacion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }
    
    public String getNombre() {
        return Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getCargo() {
        return Cargo;
    }
    
    public Date getFecha_de_ingreso() {
        return Fecha_de_ingreso;
    }

    public long getNumero_de_identificacion() {
        return Numero_de_identificacion;
    }
    
}
