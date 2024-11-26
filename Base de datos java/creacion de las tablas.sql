drop database if exists Turnos;
CREATE Database Turnos;
USE Turnos;

CREATE TABLE EstadoCliente (
    idEstado INT PRIMARY KEY AUTO_INCREMENT,
    Estado ENUM('Atendido', 'Abandonado', 'En Espera')
);

CREATE TABLE Clientes (
    idClientes INT primary key auto_increment,
    TipoIdentificacion VARCHAR(45),
    NumeroIdentificacion BIGINT,
    Nombre VARCHAR(45),
    Apellido varchar(50),
    FechaRegistro DATE,
    id_Estado int,
    FOREIGN KEY (id_Estado) REFERENCES EstadoCliente(idEstado)
);

CREATE TABLE Empleados (
    idEmpleados INT PRIMARY KEY auto_increment,
    Identificacion bigint,
    Nombre VARCHAR(45),
    Apellido VARCHAR(45),
    FechaIngreso DATE,
    Cargo VARCHAR(100)
);

CREATE TABLE TipoTurno (
    id INT PRIMARY KEY auto_increment,
    Tipo ENUM('Retiros', 'Consignaciones', 'Prestamos'),
    Modulo VARCHAR(45),
    idEmpleado int,
    foreign key (idEmpleado) references empleados(idEmpleados)
);

CREATE TABLE Turno (
    idTurno INT PRIMARY KEY AUTO_INCREMENT,
    Fecha DATE,
    HoraInicio TIME,
    HoraFin TIME,
    TiempoEsperado TIME,
    Clientes_idClientes INT,
    TipoTurno_id INT,
    Empleados_idEmpleados INT,
    Modulo VARCHAR(45),
    NombreTurno VARCHAR(10),
    FOREIGN KEY (Clientes_idClientes) REFERENCES Clientes(idClientes),
    FOREIGN KEY (TipoTurno_id) REFERENCES TipoTurno(id),
    FOREIGN KEY (Empleados_idEmpleados) REFERENCES Empleados(idEmpleados)
);


CREATE TABLE Historial_de_turnos (
    idHistorial INT PRIMARY KEY auto_increment,
    TipoTurno_id INT,
    Turno_idTurno INT,
    Fecha_Cambio DATE,
    Estado_Anterior ENUM('Pendiente', 'Atendido', 'Cancelado'),
    Estado_Nuevo ENUM('Pendiente', 'Atendido', 'Cancelado'),
    FOREIGN KEY (TipoTurno_id) REFERENCES TipoTurno(id),
    FOREIGN KEY (Turno_idTurno) REFERENCES Turno(idTurno)
);

create table Usuario (
    idUsuario int primary key auto_increment,
    NombreUsuario varchar(45),
    Contraseña varchar(45),
    RolUsuario ENUM('Empleado', 'Administrador'),
    IdEmpleado int,
    foreign key (IdEmpleado) references empleados(idEmpleados)
);


/*Respaldo tablas*/
CREATE TABLE Log_Turno (
    idLog INT PRIMARY KEY AUTO_INCREMENT,
    accion VARCHAR(255),
    idTurno INT,
    Fecha DATE,
    HoraInicio TIME,
    HoraFin TIME,
    TiempoEsperado DECIMAL(5,2),
    Clientes_idClientes INT,
    TipoTurno_id INT,
    Empleados_idEmpleados INT, 
    FechaLog TIMESTAMP
);

/*clientes*/
CREATE TABLE log_clientes (
    idlog INT PRIMARY KEY AUTO_INCREMENT,
    accion VARCHAR(255),
    idClientes INT,
    tipoIdentificacion VARCHAR(45),
    numeroIdentificacion VARCHAR(45),
    Nombre VARCHAR(45),
    Apellido VARCHAR(50),
    FechaRegistro DATE,
    Fechalog TIMESTAMP
);


CREATE TABLE log_Usuario  (
	idlog INT PRIMARY KEY AUTO_INCREMENT,
    accion VARCHAR(255),
	idUsuario INT,
    NombreUsuario VARCHAR(45),
    Contraseña VARCHAR(45),
    RolUsuario ENUM('Empleado', 'Administrador'),
    Fechalog TIMESTAMP
);


CREATE TABLE log_Empleados (
	idlog INT PRIMARY KEY AUTO_INCREMENT,
    accion VARCHAR(255),
    idEmpleados INT,
    Identificacion VARCHAR(45),
    Nombre VARCHAR(45),
    Apellido VARCHAR(45),
    FechaIngreso DATE,
    Cargo VARCHAR(100),
    Fechalog TIMESTAMP
);



INSERT INTO empleados(idEmpleados, Identificacion, Nombre, Apellido, FechaIngreso, Cargo) values (1,12,'Mejia','Diego','2024-10-29','Jefe');
INSERT INTO Usuario(idUsuario,NombreUsuario,Contraseña,RolUsuario,IdEmpleado)VALUES(1,'Mejia','321','Administrador',1);
INSERT INTO EstadoCliente (Estado) VALUES ('Atendido');
INSERT INTO EstadoCliente (Estado) VALUES ('Abandonado');
INSERT INTO EstadoCliente (Estado) VALUES ('En Espera');