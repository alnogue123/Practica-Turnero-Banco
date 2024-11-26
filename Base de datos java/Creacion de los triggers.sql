delimiter //

/*Triggers tabla turnos*/
-- Trigger para INSERT
CREATE TRIGGER Before_Insert_Turno
BEFORE INSERT ON Turno
FOR EACH ROW
BEGIN
    INSERT INTO Log_Turno (accion, idTurno, Fecha, HoraInicio, HoraFin, TiempoEsperado, Clientes_idClientes, TipoTurno_id, Empleados_idEmpleados, FechaLog)
    VALUES ('insert', NEW.idTurno, NEW.Fecha, NEW.HoraInicio, NEW.HoraFin, NEW.TiempoEsperado, NEW.Clientes_idClientes, NEW.TipoTurno_id, NEW.Empleados_idEmpleados, now());
END//

-- Trigger para UPDATE
CREATE TRIGGER Before_Update_Turno
BEFORE UPDATE ON Turno
FOR EACH ROW
BEGIN
    INSERT INTO Log_Turno (accion, idTurno, Fecha, HoraInicio, HoraFin, TiempoEsperado, Clientes_idClientes, TipoTurno_id, Empleados_idEmpleados, FechaLog)
    VALUES ('update', OLD.idTurno, OLD.Fecha, OLD.HoraInicio, OLD.HoraFin, OLD.TiempoEsperado, OLD.Clientes_idClientes, OLD.TipoTurno_id, OLD.Empleados_idEmpleados, now());
END//

-- Trigger para DELETE
CREATE TRIGGER Before_Delete_Turno
BEFORE DELETE ON Turno
FOR EACH ROW
BEGIN
    INSERT INTO Log_Turno (accion, idTurno, Fecha, HoraInicio, HoraFin, TiempoEsperado, Clientes_idClientes, TipoTurno_id, Empleados_idEmpleados, FechaLog)
    VALUES ('delete', OLD.idTurno, OLD.Fecha, OLD.HoraInicio, OLD.HoraFin, OLD.TiempoEsperado, OLD.Clientes_idClientes, OLD.TipoTurno_id, OLD.Empleados_idEmpleados, now());
END//




/*Trigger tabla clientes*/
-- Trigger para INSERT
CREATE TRIGGER Before_Insert_Clientes
BEFORE INSERT ON Clientes
FOR EACH ROW
BEGIN
    INSERT INTO log_clientes (accion, idClientes, tipoIdentificacion, numeroIdentificacion, Nombre, Apellido, FechaRegistro, Fechalog)
    VALUES ('insert', NEW.idClientes, NEW.tipoIdentificacion, NEW.numeroIdentificacion, NEW.Nombre, NEW.Apellido, NEW.FechaRegistro, NOW());
END//

-- Trigger para UPDATE
CREATE TRIGGER Before_Update_Clientes
BEFORE UPDATE ON Clientes
FOR EACH ROW
BEGIN
    INSERT INTO log_clientes (accion, idClientes, tipoIdentificacion, numeroIdentificacion, Nombre, Apellido, FechaRegistro, Fechalog)
    VALUES ('update', OLD.idClientes, OLD.tipoIdentificacion, OLD.numeroIdentificacion, OLD.Nombre, OLD.Apellido, OLD.FechaRegistro, NOW());
END//

-- Trigger para DELETE
CREATE TRIGGER Before_Delete_Clientes
BEFORE DELETE ON Clientes
FOR EACH ROW
BEGIN
    INSERT INTO log_clientes (accion, idClientes, tipoIdentificacion, numeroIdentificacion, Nombre, Apellido, FechaRegistro, Fechalog)
    VALUES ('delete', OLD.idClientes, OLD.tipoIdentificacion, OLD.numeroIdentificacion, OLD.Nombre, OLD.Apellido, OLD.FechaRegistro, NOW());
END//




/*Triggers para usuario*/
-- Trigger para INSERT
CREATE TRIGGER Before_Insert_Usuario
BEFORE INSERT ON Usuario
FOR EACH ROW
BEGIN
    INSERT INTO log_Usuario (accion, idUsuario, NombreUsuario, Contraseña, RolUsuario, Fechalog)
    VALUES ('insert', NEW.idUsuario, NEW.NombreUsuario, NEW.Contraseña, NEW.RolUsuario, NOW());
END//

-- Trigger para UPDATE
CREATE TRIGGER Before_Update_Usuario
BEFORE UPDATE ON Usuario
FOR EACH ROW
BEGIN
    INSERT INTO log_Usuario (accion, idUsuario, NombreUsuario, Contraseña, RolUsuario, Fechalog)
    VALUES ('update', OLD.idUsuario, OLD.NombreUsuario, OLD.Contraseña, OLD.RolUsuario, NOW());
END//

-- Trigger para DELETE
CREATE TRIGGER Before_Delete_Usuario
BEFORE DELETE ON Usuario
FOR EACH ROW
BEGIN
    INSERT INTO log_Usuario (accion, idUsuario, NombreUsuario, Contraseña, RolUsuario, Fechalog)
    VALUES ('delete', OLD.idUsuario, OLD.NombreUsuario, OLD.Contraseña, OLD.RolUsuario, NOW());
END//




/*Triggers para Empleados*/
-- Trigger para INSERT
CREATE TRIGGER Before_Insert_Empleados
BEFORE INSERT ON Empleados
FOR EACH ROW
BEGIN
    INSERT INTO log_Empleados (accion, idEmpleados, Identificacion, Nombre, Apellido, FechaIngreso, Cargo, Fechalog)
    VALUES ('insert', NEW.idEmpleados, NEW.Identificacion, NEW.Nombre, NEW.Apellido, NEW.FechaIngreso, NEW.Cargo, NOW());
END//

-- Trigger para UPDATE
CREATE TRIGGER Before_Update_Empleados
BEFORE UPDATE ON Empleados
FOR EACH ROW
BEGIN
    INSERT INTO log_Empleados (accion, idEmpleados, Identificacion, Nombre, Apellido, FechaIngreso, Cargo, Fechalog)
    VALUES ('update', OLD.idEmpleados, OLD.Identificacion, OLD.Nombre, OLD.Apellido, OLD.FechaIngreso, OLD.Cargo,  NOW());
END//

-- Trigger para DELETE
CREATE TRIGGER Before_Delete_Empleados
BEFORE DELETE ON Empleados
FOR EACH ROW
BEGIN
    INSERT INTO log_Empleados (accion, idEmpleados, Identificacion, Nombre, Apellido, FechaIngreso, Cargo, Fechalog)
    VALUES ('delete', OLD.idEmpleados, OLD.Identificacion, OLD.Nombre, OLD.Apellido, OLD.FechaIngreso, OLD.Cargo, NOW());
END//
