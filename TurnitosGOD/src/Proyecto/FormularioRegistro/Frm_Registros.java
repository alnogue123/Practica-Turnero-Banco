/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Proyecto.FormularioRegistro;

import Reportes.Frm_Reportes;
import InicioSesion.Frm_InicioSesion;
import Proyecto.Frm_Turnos;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import CrearEmpleados.Frm_CrearEmpleados;
import Conexion_base_datos_turnos.Conexion;
import CrearEmpleados.Empleado;
import InicioSesion.Usuario;
import static Proyecto.Frm_Turnos.PersonalizarTabla;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author mjiad
 */
public final class Frm_Registros extends javax.swing.JFrame {

    private final String[] Encabezado = {"Tipo  de Identificacion", "Numero de identificacion", "Turno"};
    private final String[] TiposIdentificaion = {"Cedula de Ciudadania", "Tarjeta de Identidad", "Registro Civil", "Pasaporte"};
    private final String[] Motivos = {"Retiros", "Prestamos", "Consignaciones"};
    private String TipoDocumento;
    private double numeroDocumento;
    private String Nombre;
    private String Apellido;
    private Date Fecha;
    private static Frm_Turnos Turnitos;
    private Frm_CrearEmpleados Empleados;
    private Frm_InicioSesion Inicio;
    private ArrayList<Cliente> ListaDocumentos;
    private static ArrayList<Cliente> listaCliente;
    private ArrayList<Cliente> ListaClientesEnEspera;
    private ArrayList<Usuario> ListaUsuarios;
    private ArrayList<Empleado> ListaEmpleados;
    int fila = 0;
    private Empleado empleadoLogueado;

    Conexion conexion = new Conexion();

    public Frm_Registros(Frm_InicioSesion inicio) {
        initComponents();
        ListenerRegistrarClientes();
        ListenerMostrarTabla();
        ListenerCambiarTurno();
        ListenerDelTeclado();
        ListaDocumentos = new ArrayList<>();
        listaCliente = new ArrayList<>();
        ListaEmpleados = new ArrayList<>();
        ListaUsuarios = new ArrayList<>();
        ListaClientesEnEspera = new ArrayList<>();

        Inicio = inicio;
        CrearTabla();
        Empleados = Frm_InicioSesion.getCrearEmpledos();
        Turnitos = Frm_InicioSesion.getTurnitos();
        Frm_Turnos.PersonalizarTabla(TablModificable);
        CrearComboBox();
    }

    public void ListenerCambiarTurno() {
        Btn_CambiarTurno.addActionListener((ActionEvent e) -> {
            try {
                SeleccionarFilaTabla(Turnitos.getTablaTurnos());
                int filaSeleccionada = Turnitos.getTablaTurnos().getSelectedRow();
                int columnaSeleccionada = Turnitos.getTablaTurnos().getSelectedColumn();
                if (filaSeleccionada == -1 || columnaSeleccionada == -1) {
                    fila = 0;
                    return;
                }
                listaCliente = conexion.llenarListaClientes(listaCliente);
                Object valorCelda = Turnitos.getTablaTurnos().getValueAt(filaSeleccionada, columnaSeleccionada);
                Turnitos.getLB_TurnoSeleccionado().setText(valorCelda.toString());
                Turnitos.ConstruirTabla();
                PersonalizarTabla(Turnitos.getTablaTurnos());
            } catch (ParseException | IndexOutOfBoundsException ex) {
                Logger.getLogger(Frm_Turnos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(rootPane, "Ocurrió un error al cambiar el turno.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void ListenerDelTeclado() {
        Txt_NumeroIdentificacion.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown()) {
                    if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                        try {
                            listaCliente = conexion.llenarListaClientes(listaCliente);
                        } catch (ParseException ex) {
                            Logger.getLogger(Frm_Registros.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Cliente encontrado = null;
                        for (Cliente clien : listaCliente) {
                            if (!Txt_NumeroIdentificacion.getText().equals("")) {
                                if (clien.getNumeroIdentificacion() == Double.parseDouble(Txt_NumeroIdentificacion.getText())) {
                                    encontrado = clien;
                                    break;
                                }
                            }
                        }

                        if (encontrado != null) {
                            CB_TipoIdentificacion.setSelectedItem(encontrado.getTipoIdendificacion());
                            TxT_Nombre_Cliente.setText(encontrado.getNombre());
                            TxT_Apellido_Cliente.setText(encontrado.getApellido());
                            CB_motivo.setSelectedItem(encontrado.getMotivo());
                        }
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

        });
    }

    public void setEmpleadoLogueado(Empleado empleado) {
        this.empleadoLogueado = empleado;
    }

    public void SeleccionarFilaTabla(JTable Tabla) {
        try {
            if (fila < Tabla.getRowCount() && fila >= 0) {
                int filaSeleccionada = fila;
                fila++;
                Tabla.selectAll();
                Tabla.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
                Tabla.scrollRectToVisible(Tabla.getCellRect(filaSeleccionada, 0, true));
                manejarEstadoClienteSeleccionado();
            }
        } catch (IndexOutOfBoundsException e) {
            fila = 0;
        }
    }

    private void manejarEstadoClienteSeleccionado() throws HeadlessException {
        int respuesta = JOptionPane.showConfirmDialog(rootPane, "¿El cliente fue atendido?", "Turnos", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            CambiarEstadoCliente(1, 1);
        } else if (respuesta == JOptionPane.NO_OPTION) {
            CambiarEstadoCliente(2, 2);
        }
    }

    private Cliente EncontrarClienteConElTurno(Object valorCelda, Cliente cliente) {
        for (Cliente clienteLista : ListaClientesEnEspera) {
            if (clienteLista.getEstado() == 3) {
                if (clienteLista.getTurno().equals(valorCelda.toString())) {
                    cliente = clienteLista;
                    System.out.println("Turno afectado: " + cliente.getTurno());
                    System.out.println("estado del cliente: " + cliente.getEstado());
                    break;
                }
                break;
            }
        }
        return cliente;
    }

    private void LLenarListasDesdeBaseDeDatos() throws ParseException {
        listaCliente = conexion.llenarListaClientes(listaCliente);
        ListaEmpleados = conexion.LlenarListaEmpledos(ListaEmpleados);
        ListaUsuarios = conexion.LlenarListaUsuarios(ListaUsuarios);
        ListaClientesEnEspera = conexion.llenarListaClientesEnEspera(ListaClientesEnEspera);
    }

    private void ValidarEstado(Cliente cliente, int accion) throws SQLException {
        if (cliente != null) {
            if (accion == 1) {
                conexion.ActualizarCliente(cliente, "Atendido");
            } else if (accion == 2) {
                conexion.ActualizarCliente(cliente, "Abandonado");
            }
        }
    }

    public void CambiarEstadoCliente(int accion, int abandono) {
        int filas = Turnitos.getTablaTurnos().getSelectedRow();
        if (filas != -1) {
            try {
                LLenarListasDesdeBaseDeDatos();
                Object valorCelda = Turnitos.getTablaTurnos().getValueAt(filas, 0);
                Cliente cliente = EncontrarClienteConElTurno(valorCelda, null);
                if (cliente != null) {
                    System.out.println("Turno afectado " + cliente.getTurno());
                    if (empleadoLogueado != null && abandono == 1) {
                        int idEmpleado = empleadoLogueado.getIdEmpleado();
                        System.out.println("Empleado logueado: " + empleadoLogueado.getNombre());
                        conexion.ActualizarTurno(cliente, idEmpleado);
                    }
                    ValidarEstado(cliente, accion);
                    Turnitos.ConstruirTabla();
                    PersonalizarTabla(Turnitos.getTablaTurnos());
                    if (Turnitos.getTablaTurnos().getRowCount() > 0) {
                        Turnitos.getTablaTurnos().setRowSelectionInterval(0, 0);
                    }
                }
            } catch (ParseException | SQLException ex) {
                Logger.getLogger(Frm_Registros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Sesion(String sesion) {
        this.setTitle("Registro del empleado " + "(" + "Sesion de " + sesion + ")");
        MenuItem_Reportes.setEnabled(true);
        MenuItem_CrearEmpleado.setEnabled(true);
        if (sesion.equals("Empleado")) {
            MenuItem_Reportes.setEnabled(false);
            MenuItem_CrearEmpleado.setEnabled(false);
        }
    }

    public ArrayList<Cliente> getListaDocumentos() {
        return this.ListaDocumentos;
    }

    private void CrearTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        DecimalFormat df = new DecimalFormat("#");
        modelo.setColumnIdentifiers(Encabezado);
        for (int i = 0; i < ListaDocumentos.size(); i++) {
            modelo.addRow(new Object[]{
                ListaDocumentos.get(i).getTipoIdendificacion(),
                df.format(ListaDocumentos.get(i).getNumeroIdentificacion()).replace(".0", ""),
                ListaDocumentos.get(i).getTurno()
            });
        }
        TablModificable.setModel(modelo);
        ListaDocumentos.clear();
    }

    public final void CrearComboBox() {
        CB_TipoIdentificacion.removeAllItems();
        CB_motivo.removeAllItems();
        CB_motivo.addItem("Seleccione");
        CB_TipoIdentificacion.addItem("Seleccione");
        for (String Motivo : Motivos) {
            CB_motivo.addItem(Motivo);
        }
        for (String TiposIdentificaion1 : TiposIdentificaion) {
            CB_TipoIdentificacion.addItem(TiposIdentificaion1);
        }
    }

    public void llenarVariables() {
        if (Txt_NumeroIdentificacion != null && !Txt_NumeroIdentificacion.getText().trim().isEmpty()) {
            TipoDocumento = CB_TipoIdentificacion.getSelectedItem().toString();
            Nombre = TxT_Nombre_Cliente.getText();
            Apellido = TxT_Apellido_Cliente.getText();
            Turnitos.setMotivo(CB_motivo.getSelectedItem().toString().toLowerCase());
            try {
                Fecha = (Date) conexion.ConseguirFechaActual();
            } catch (ParseException ex) {
            }
            Turnitos.EscogerMotivo();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Ingrese el número de identificación", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Empleado EncontrarEmpleados(Usuario usuarioEncontrado) {
        ListaEmpleados = conexion.LlenarListaEmpledos(ListaEmpleados);
        Empleado empleadoEncontrad = null;
        for (Empleado empleado : ListaEmpleados) {
            if (empleado.getNombre().equals(usuarioEncontrado.getNombreUsurio())) {
                empleadoEncontrad = empleado;
                break;
            }
        }
        return empleadoEncontrad;
    }

    public Usuario EncontrarUsuario(String Usuario, String contraseña) {
        ListaUsuarios = conexion.LlenarListaUsuarios(ListaUsuarios);
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : ListaUsuarios) {
            if (usuario.getNombreUsurio().equals(Usuario) && usuario.getContraseña().equals(contraseña)) {
                usuarioEncontrado = usuario;
                break;
            }
        }
        return usuarioEncontrado;
    }

    public Frm_InicioSesion getInicio() {
        return this.Inicio;
    }

    public JTable getTablModificable() {
        return TablModificable;
    }

    public JButton getBtn_registrar() {
        return Btn_registrar;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        panle = new javax.swing.JPanel();
        CB_motivo = new javax.swing.JComboBox<>();
        Txt_NumeroIdentificacion = new java.awt.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Btn_registrar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablModificable = new javax.swing.JTable();
        CB_TipoIdentificacion = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        TxT_Nombre_Cliente = new java.awt.TextField();
        jLabel5 = new javax.swing.JLabel();
        TxT_Apellido_Cliente = new java.awt.TextField();
        Btn_MostrarTabla = new javax.swing.JButton();
        Btn_CambiarTurno = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuItem_Reportes = new javax.swing.JMenuItem();
        MenuItem_CrearEmpleado = new javax.swing.JMenuItem();
        MenuItem_CerrarSesion = new javax.swing.JMenuItem();

        jLabel6.setText("jLabel6");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Registro del empleado");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panle.setBackground(new java.awt.Color(255, 255, 255));

        CB_motivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Numero de identificacion");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Tipo de Identificacion");

        Btn_registrar.setText("Registrar");

        jLabel3.setText("Motivo");

        TablModificable.setModel(new javax.swing.table.DefaultTableModel(
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
        TablModificable.setEnabled(false);
        jScrollPane1.setViewportView(TablModificable);

        CB_TipoIdentificacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Nombre del cliente");

        jLabel5.setText("Apellido del cliente");

        Btn_MostrarTabla.setText("Cargar tabla");

        Btn_CambiarTurno.setText("Cambiar Turno");

        javax.swing.GroupLayout panleLayout = new javax.swing.GroupLayout(panle);
        panle.setLayout(panleLayout);
        panleLayout.setHorizontalGroup(
            panleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Txt_NumeroIdentificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CB_motivo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Btn_registrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CB_TipoIdentificacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(TxT_Nombre_Cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addComponent(TxT_Apellido_Cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Btn_MostrarTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Btn_CambiarTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                .addContainerGap())
        );
        panleLayout.setVerticalGroup(
            panleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panleLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panleLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CB_TipoIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Txt_NumeroIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxT_Nombre_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxT_Apellido_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CB_motivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Btn_registrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Btn_MostrarTabla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Btn_CambiarTurno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jMenu1.setText("Opciones");

        MenuItem_Reportes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MenuItem_Reportes.setText("Reportes");
        MenuItem_Reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_ReportesActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItem_Reportes);

        MenuItem_CrearEmpleado.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MenuItem_CrearEmpleado.setText("Nuevo empleado");
        MenuItem_CrearEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_CrearEmpleadoActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItem_CrearEmpleado);

        MenuItem_CerrarSesion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MenuItem_CerrarSesion.setText("Cerrar Sesion");
        MenuItem_CerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItem_CerrarSesionActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItem_CerrarSesion);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void ListenerRegistrarClientes() {
        Btn_registrar.addActionListener((ActionEvent e) -> {
            RegistarClientes();
        });
    }

    public void ListenerMostrarTabla() {
        Btn_MostrarTabla.addActionListener((ActionEvent e) -> {
            try {
                ListaDocumentos = conexion.llenarListaClientes(ListaDocumentos);
                CrearTabla();
                Turnitos.ConstruirTabla();
                Frm_Turnos.PersonalizarTabla(Turnitos.getTablaTurnos());
            } catch (ParseException ex) {
                Logger.getLogger(Frm_Registros.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void RegistarClientes() {
        if (Validar()) {
            return;
        }
        if (ValidarClientesRepetidos()) {
            return;
        }
        try {
            llenarVariables();
            procesarDatosCliente();
            Turnitos.ListenerTabla();
            LimpiarCampos();
        } catch (ParseException | SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Ha ocurrido un error: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: " + e.toString());
        }
    }

    private void LimpiarCampos() {
        Txt_NumeroIdentificacion.setText("");
        TxT_Nombre_Cliente.setText("");
        TxT_Apellido_Cliente.setText("");
        CB_TipoIdentificacion.setSelectedIndex(0);
        CB_motivo.setSelectedIndex(0);
    }

    private void procesarDatosCliente() throws SQLException, ParseException {
        String turno = Turnitos.getTurno();
        String modulo = Turnitos.getModulo();
        String Motivo = Turnitos.getMotivo();
        Cliente cliente = new Cliente(TipoDocumento, numeroDocumento, Nombre, Apellido, turno, modulo, 3, Motivo, 0);
        conexion.InsertarClientes(cliente);
        conexion.InsertarTipoTurno(cliente);
        String Usuario = Inicio.getUsuario();
        String contraseña = Inicio.getContraseña();
        Usuario usuarioEncontrado = EncontrarUsuario(Usuario, contraseña);
        Empleado Empleado = EncontrarEmpleados(usuarioEncontrado);
        conexion.InsertarTurno(cliente, Empleado);
    }

    private boolean Validar() {
        try {
            double numerovalidado = Double.parseDouble(Txt_NumeroIdentificacion.getText());
            numeroDocumento = numerovalidado;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Ingresa un numero de documento valido");
            return true;
        }

        if (Txt_NumeroIdentificacion.getText().length() != 10) {
            JOptionPane.showMessageDialog(rootPane, "el numero de documento debe ser de 10 digitos");
            return true;
        }

        if (CB_TipoIdentificacion.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione su tipo de documento", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (CB_motivo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Seleccione un motivo", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }

        if (TxT_Nombre_Cliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "El nombre no puede estar en blanco", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }

        if (TxT_Apellido_Cliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "El Apellido no puede estar en blanco", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }

        System.out.println("Valido todo");
        return false;
    }

    private boolean ValidarClientesRepetidos() {
        try {
            ListaDocumentos = conexion.llenarListaClientes(ListaDocumentos);
        } catch (ParseException ex) {
            Logger.getLogger(Frm_Registros.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean encontrado = false;
        for (Cliente listaDocumento : ListaDocumentos) {
            if (listaDocumento.getNumeroIdentificacion() == Double.parseDouble(Txt_NumeroIdentificacion.getText().trim())) {
                if (listaDocumento.getEstado() == 3) {
                    encontrado = true;
                    break;
                }
            }
        }
        if (encontrado) {
            JOptionPane.showMessageDialog(rootPane, "este cliente aun se encuentra esperando", "ERROR", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }


    private void MenuItem_CerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_CerrarSesionActionPerformed
        int eleccion = JOptionPane.showConfirmDialog(rootPane, "¿Estas seguro de que deseas cerrar esta sesion?", "Alerta", JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE);
        if (eleccion == JOptionPane.YES_OPTION) {
            empleadoLogueado = null;
            Inicio.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_MenuItem_CerrarSesionActionPerformed

    private void MenuItem_ReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_ReportesActionPerformed
        Frm_Reportes reportes = new Frm_Reportes(this);
        reportes.setVisible(true);
    }//GEN-LAST:event_MenuItem_ReportesActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int eleccion = JOptionPane.showConfirmDialog(rootPane, "¿Estas seguro de que deseas cerrar esta sesion?", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (eleccion == JOptionPane.YES_OPTION) {
            empleadoLogueado = null;
            Inicio.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void MenuItem_CrearEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItem_CrearEmpleadoActionPerformed
        Empleados.setVisible(true);
        Empleados.setResizable(false);
    }//GEN-LAST:event_MenuItem_CrearEmpleadoActionPerformed

    public void MostrarTabla() {
        System.out.println("Mostro la tabla");
        try {
            ListaDocumentos.clear();
            conexion.llenarListaClientes(ListaDocumentos);
            CrearTabla();
        } catch (ParseException ex) {
            Logger.getLogger(Frm_Registros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Frm_Registros.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Registros.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Registros.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Registros.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Frm_Registros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_CambiarTurno;
    private javax.swing.JButton Btn_MostrarTabla;
    private javax.swing.JButton Btn_registrar;
    private javax.swing.JComboBox<String> CB_TipoIdentificacion;
    private javax.swing.JComboBox<String> CB_motivo;
    private javax.swing.JMenuItem MenuItem_CerrarSesion;
    private javax.swing.JMenuItem MenuItem_CrearEmpleado;
    private javax.swing.JMenuItem MenuItem_Reportes;
    private static javax.swing.JTable TablModificable;
    private java.awt.TextField TxT_Apellido_Cliente;
    private java.awt.TextField TxT_Nombre_Cliente;
    private java.awt.TextField Txt_NumeroIdentificacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panle;
    // End of variables declaration//GEN-END:variables
}
