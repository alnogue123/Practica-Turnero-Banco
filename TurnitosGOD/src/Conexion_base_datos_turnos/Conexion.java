/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion_base_datos_turnos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import CrearEmpleados.Empleado;
import InicioSesion.Usuario;
import Proyecto.FormularioRegistro.Cliente;
import Proyecto.FormularioRegistro.Frm_Registros;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alnog
 */
public class Conexion {

    private final String url = "jdbc:mysql://localhost/turnos";
    private final String username = "root";
    private final String password = "cbn2016"; // 578902 o cbn2016 o 135515
    

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ResultSet Getconexion(String procedure) throws SQLException {
        Connection con = DriverManager.getConnection(url, username, password);
        Statement stm = con.createStatement();
        return stm.executeQuery(procedure);
    }

    public Connection Getconexion() throws SQLException {
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }

    private Time ConseguirHoraActual() {
        Time horaActual = new Time(Calendar.getInstance().getTimeInMillis());
        return horaActual;
    }

    public java.sql.Date ConseguirFechaActual() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = dateFormat.format(new java.util.Date());
        java.util.Date parsed = dateFormat.parse(fechaFormateada);
        return new java.sql.Date(parsed.getTime());
    }

    public void ConsultasSql(String mensaje, String Procedure, DefaultTableModel model, JTable tabla) {
        try (ResultSet rs = Getconexion(Procedure)) {
            crearTabla(rs, model, tabla);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, mensaje + "\n" + e.toString());
        }
    }

    public void ConsultasSqlMultitablas(String mensaje, String Procedure, DefaultTableModel model1, JTable tabla1, DefaultTableModel model2, JTable tabla2) {
        try (ResultSet rs = Getconexion(Procedure)) {
            crearTabla(rs, model1, tabla1);
            crearTabla(rs, model2, tabla2);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, mensaje + "\n" + e.toString());
        }
    }

    public void crearTabla(ResultSet rs, DefaultTableModel model, JTable tabla) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();
        model.setColumnCount(0);
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            model.addColumn(metadata.getColumnLabel(i));
        }
        model.setRowCount(0);
        while (rs.next()) {
            Object[] filas = new Object[metadata.getColumnCount()];
            for (int i = 0; i < metadata.getColumnCount(); i++) {
                filas[i] = rs.getObject(i + 1);
            }
            model.addRow(filas);
        }
        tabla.setModel(model);
    }

    public void InsertarEmpleados(Empleado empleado) {
        String sqlEmpleados = "call InsertarEmpleado(?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            con.setAutoCommit(false);
            java.sql.Date fechaDeIngresoSQL = new java.sql.Date(empleado.getFecha_de_ingreso().getTime());
            PreparedStatement ps = con.prepareCall(sqlEmpleados);
            ps.setString(1, Double.toString(empleado.getNumero_de_identificacion()));
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApellido());
            ps.setDate(4, fechaDeIngresoSQL);
            ps.setString(5, empleado.getCargo());
            ps.execute();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar un empleado: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void InsertarTurno(Cliente cliente,Empleado empleado) throws ParseException {
        try (Connection con = Getconexion()) {
            String sql = "call InsertarTurnoAuto(?,?,?,?,?,?)";

            try (PreparedStatement GuardarTurno = con.prepareCall(sql)) {
                con.setAutoCommit(false);
                GuardarTurno.setDate(1, (java.sql.Date) ConseguirFechaActual());
                GuardarTurno.setTime(2, ConseguirHoraActual());
                GuardarTurno.setTime(3, null);
                GuardarTurno.setString(4, null);
                GuardarTurno.setString(5, cliente.getModulo());
                GuardarTurno.setString(6, cliente.getTurno());
                GuardarTurno.execute();
                con.commit();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la inserción:\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void InsertarClientes(Cliente cliente) throws SQLException, ParseException {
        Connection con = Getconexion();
        java.sql.Date fechaSql = new java.sql.Date(ConseguirFechaActual().getTime());
        PreparedStatement insertarClientes = con.prepareCall("call InsertarClienteAuto(?,?,?,?)");
        con.setAutoCommit(false);
        insertarClientes.setString(1, cliente.getTipoIdendificacion());
        insertarClientes.setString(2, Double.toString(cliente.getNumeroIdentificacion()));
        insertarClientes.setString(3, cliente.getNombre());
        insertarClientes.setString(4, cliente.getApellido());
        insertarClientes.execute();
        con.commit();
    }

    public void InsertarTipoTurno(Cliente cliente) throws SQLException {
        Connection con = Getconexion();
        String sql = "call InsertarTipoTurnoAuto(?,?)";
        PreparedStatement ps = con.prepareCall(sql);
        con.setAutoCommit(false);
        ps.setString(1, cliente.getMotivo());
        ps.setString(2, cliente.getModulo());
        ps.execute();
        con.commit();
    }

    public void InsertarUsuario(Usuario usuario) {
        try (Connection con = Getconexion()) {
            String sql = "call InsertarUsuarioAuto(?,?,?)";
            PreparedStatement ps = con.prepareCall(sql);
            con.setAutoCommit(false);
            ps.setString(1, usuario.getNombreUsurio());
            ps.setString(2, usuario.getContraseña());
            ps.setString(3, usuario.getRolUsuario());
            ps.execute();
            con.commit();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar el usuario: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<Cliente> llenarListaClientes(ArrayList<Cliente> ListaClientes) throws ParseException {
        ListaClientes.clear(); 
        try (ResultSet rs = Getconexion("call MostrarClientes()")) {
            while (rs.next()) {
                String TipoIdendificacion = rs.getString("TipoIdentificacion");
                String numeroIdentificacionStr = rs.getString("NumeroIdentificacion");
                double NumeroIdentificacion = numeroIdentificacionStr != null ? Double.parseDouble(numeroIdentificacionStr) : 0;
                String nombre = rs.getString("Nombre");
                String Apellido = rs.getString("Apellido");
                String Turno = rs.getString("NombreTurno");
                String modulo = rs.getString("modulo");
                int Estado = rs.getInt("id_Estado");
                String motivo = rs.getString("Tipo");
                int IdCliente = rs.getInt("idClientes");
                Cliente cliente = new Cliente(TipoIdendificacion, NumeroIdentificacion, nombre, Apellido, Turno, modulo, Estado, motivo, IdCliente);
                ListaClientes.add(cliente);          
            }
        } catch (SQLException e) {
            System.out.println("Error al llenar la lista: " + e.toString());
        }
        return ListaClientes;
    }
    
    public ArrayList<Cliente> llenarListaClientesEnEspera(ArrayList<Cliente> ListaClientes) throws ParseException {
        ListaClientes.clear(); 
        try (ResultSet rs = Getconexion("call MostrarClientesEnEspera()")) {
            while (rs.next()) {
                String TipoIdendificacion = rs.getString("TipoIdentificacion");
                String numeroIdentificacionStr = rs.getString("NumeroIdentificacion");
                double NumeroIdentificacion = numeroIdentificacionStr != null ? Double.parseDouble(numeroIdentificacionStr) : 0;
                String nombre = rs.getString("Nombre");
                String Apellido = rs.getString("Apellido");
                String Turno = rs.getString("NombreTurno");
                String modulo = rs.getString("modulo");
                int Estado = rs.getInt("id_Estado");
                String motivo = rs.getString("Tipo");
                int IdCliente = rs.getInt("idClientes");
                Cliente cliente = new Cliente(TipoIdendificacion, NumeroIdentificacion, nombre, Apellido, Turno, modulo, Estado, motivo, IdCliente);
                ListaClientes.add(cliente);          
            }
        } catch (SQLException e) {
            System.out.println("Error al llenar la lista: " + e.toString());
        }
        return ListaClientes;
    }

    public void ActualizarCliente(Cliente cliente, String opcion) throws SQLException {
        try (Connection con = Getconexion()) {
            DecimalFormat df = new DecimalFormat("#");
            String sql = "call ActualizarCliente(?,?)";
            PreparedStatement ps = con.prepareCall(sql);
            con.setAutoCommit(false);
            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, opcion);
            ps.execute();
            con.commit();
        } catch (SQLException e) {
            System.out.println("Error al enviar los datos: " + e.getMessage());
           Logger.getLogger(Frm_Registros.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ArrayList<Empleado> LlenarListaEmpledos(ArrayList<Empleado> ListaEmpleados) {
         ListaEmpleados.clear(); 
        try (ResultSet rs = Getconexion("call MostrarEmpleados()")) {
            while (rs.next()) {
                int IdEmpleado = rs.getInt("idEmpleados");
                String Nombre = rs.getString("Nombre");
                String Apellido = rs.getString("Apellido");
                String Cargo = rs.getString("Cargo");
                Date fechaIngreso = rs.getDate("FechaIngreso");

                String identificacionStr = rs.getString("Identificacion").replaceAll("[^\\d]", ""); // Eliminar caracteres no numéricos
                long Identificacion = Long.parseLong(identificacionStr);

                ListaEmpleados.add(new Empleado(IdEmpleado,Nombre, Apellido, Cargo, fechaIngreso, Identificacion));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al llenar la lista \n" + e.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en la conversión de identificación: " + e.toString());
        }
        return ListaEmpleados;
    }

    public ArrayList LlenarListaUsuarios(ArrayList<Usuario> ListaUsuarios) {
         ListaUsuarios.clear(); 
        try (ResultSet rs = Getconexion("call mostrarUsuarios()")) {
            while (rs.next()) {
                String NombreUsurio = rs.getString("NombreUsuario");
                String Contraseña = rs.getString("Contraseña");
                String RolUsuario = rs.getString("RolUsuario");
                ListaUsuarios.add(new Usuario(NombreUsurio, Contraseña, RolUsuario));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al llenar la lista \n" + e.toString());
        }
        return ListaUsuarios;
    }

    public int ConseguirIdEmpleado() {
        int idEmpleado = 0;
        try (ResultSet rs = Getconexion("call ConseguirUltimaIdEmpleado()")) {
            if (rs.next()) {
                idEmpleado = rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al traer la id del empleado \n" + e.toString());
        }
        return idEmpleado;
    }
    
    public void ActualizarTurno(Cliente cliente, int idEmpleado){
        try(Connection con = Getconexion()) {
            String sql = "call ActualizarTurno(?,?,?)";
            PreparedStatement ps = con.prepareCall(sql);
            con.setAutoCommit(false);
                ps.setInt(1, idEmpleado);
                ps.setInt(2, cliente.getIdCliente());
                ps.setString(3,cliente.getTurno());
                ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            System.out.println("no se actualizo el turno: " +e.toString());
        }
    }
}
