use turnos;
delimiter //

create procedure GetTurnos() begin
	select idTurno,Nombre,modulo from turno inner join tipoturno on turno.TipoTurno_id = tipoturno.id;
end;//

Create Procedure MostrarTurnosEnEspera() begin
	select NombreTurno, Modulo from turno t inner join clientes c on c.idClientes = Clientes_idClientes where c.id_Estado = 3;
end//

select * from usuario;

Create procedure MostrarClientesEnEspera() begin
	 select idClientes, c.TipoIdentificacion,
    t.TiempoEsperado,
    t.NombreTurno,
    c.NumeroIdentificacion,
    c.Nombre, 
    Apellido,
    tp.Modulo,
    tp.Tipo,
    t.HoraFin,
    c.id_Estado
    from clientes c inner join turno t on  c.idClientes = t.Clientes_idClientes
    inner join tipoturno tp on t.TipoTurno_id = tp.id
    inner join estadocliente es on es.idEstado = c.id_Estado where c.id_Estado = 3 ;
end;//

create procedure MostrarClientesAtendidosEnElDia() begin
	select count(idTurno) as 'Clientes atendidos en el dia',
    MIN(Fecha) AS FechaInicio, 
	MAX(Fecha) AS FechaFin 
	from turno 
    inner join clientes on clientes.idClientes = Turno.Clientes_idClientes
	INNER JOIN estadocliente ON estadocliente.idEstado = clientes.id_Estado
	where estadocliente.Estado = 'Atendido' and turno.fecha = curdate();
end;//

create procedure MostrarClientesAtendidosEnLaSemana() begin
	SELECT COUNT(DISTINCT idTurno) AS 'Clientes atendidos en la semana', 
		WEEK(Fecha) AS 'Semana del año', 
		MIN(Fecha) AS FechaInicio, 
		MAX(Fecha) AS FechaFin 
		FROM turno 
		inner join clientes on clientes.idClientes = Turno.Clientes_idClientes
		INNER JOIN estadocliente ON estadocliente.idEstado = clientes.id_Estado
		WHERE estadocliente.Estado = 'Atendido'
		AND YEAR(Fecha) = YEAR(CURDATE()) GROUP BY WEEK(Fecha) LIMIT 0, 50000;
end;//

create procedure MostrarClientesAtendidosEnElMes() begin
	SELECT COUNT(DISTINCT idTurno) AS 'Clientes atendidos en el mes', MONTH(Fecha) AS Mes, MIN(Fecha) AS FechaInicio, 
			MAX(Fecha) AS FechaFin FROM turno 
            inner join clientes on clientes.idClientes = Turno.Clientes_idClientes
			INNER JOIN estadocliente ON estadocliente.idEstado = clientes.id_Estado
			WHERE estadocliente.Estado = 'Atendido' 
			AND YEAR(Fecha) = YEAR(CURDATE())
			GROUP BY Mes ORDER BY Mes;
end;//


create procedure MostrarClientesQueAbandonron() begin
	Select Nombre, Apellido,HoraInicio, HoraFin,Estado from clientes
	inner join turno on turno.Clientes_idClientes = clientes.idClientes
	inner join estadocliente on estadocliente.idEstado = clientes.id_Estado where estadocliente.Estado like 'Abandonado'  ;
end;//

create procedure TiempoEsperaPorCliente() begin
select Nombre, Apellido, Fecha, HoraInicio, HoraFin, calcular_tiempo(idTurno) 'Tiempo de espera', Estado from clientes
	inner join turno on turno.Clientes_idClientes = clientes.idClientes
	inner join estadocliente on estadocliente.idEstado = clientes.id_Estado 
    where estadocliente.Estado like 'Atendido' || estadocliente.Estado like 'Abandonado';
end;//


create procedure MostrarProductividadEmpleados() begin
	SELECT emp.idEmpleados,
           emp.Nombre AS `Nombre del empleado`,
           emp.Apellido AS `Apellido del empleado`,
           emp.Cargo AS `Cargo del empleado`,
           ProductividadEmpleados(emp.idEmpleados) AS `Cantidad de turnos atendidos`
    FROM empleados emp
    LEFT JOIN turno t ON t.Empleados_idEmpleados = emp.idEmpleados
    LEFT JOIN clientes c ON c.idClientes = t.Clientes_idClientes AND c.id_Estado = 1
    GROUP BY emp.idEmpleados, emp.Nombre, emp.Apellido, emp.Cargo;
end;//
    
call MostrarProductividadEmpleados()//
select * from turno;//

create procedure mostarTipoTurno() begin
	select * from tipoturno;
end;//

create procedure mostrarUsuarios() begin
	select idUsuario,NombreUsuario, Contraseña, RolUsuario,IdEmpleado from usuario us inner join empleados em on us.IdEmpleado = em.idEmpleados;
end;//

create procedure MostrarClientesAtendidos() begin 
	select TipoIdentificacion,NumeroIdentificacion,Nombre,Apellido,Estado from clientes inner join estadocliente on clientes.id_Estado= estadocliente.idEstado where Estado like 'Atendido';
end;//

create procedure mostrarclientes() begin
    select idClientes, c.TipoIdentificacion,
    t.TiempoEsperado,
    t.NombreTurno,
    c.NumeroIdentificacion,
    c.Nombre, 
    Apellido,
    tp.Modulo,
    tp.Tipo,
    t.HoraFin,
    c.id_Estado
    from clientes c inner join turno t on  c.idClientes = t.Clientes_idClientes
    inner join tipoturno tp on t.TipoTurno_id = tp.id
    inner join estadocliente es on es.idEstado = c.id_Estado;
end;//

create procedure MostrarEmpleados() begin
	select idEmpleados,Nombre,Apellido,Cargo,FechaIngreso,Identificacion from empleados;
end;

Create procedure BorrarEmpleados() begin
	DELETE FROM RolEmpleado WHERE idRolEmpleado = p_idRolEmpleado;
end;//


CREATE PROCEDURE InsertarClienteAuto(
    IN p_TipoIdentificacion VARCHAR(45),
    IN p_NumeroIdentificacion VARCHAR(45),
    IN p_Nombre VARCHAR(45),
    IN p_Apellido VARCHAR(50)
)
BEGIN
    DECLARE last_idEstado INT;

    -- Obtener el último id de EstadoCliente
    SELECT idEstado INTO last_idEstado
    FROM EstadoCliente
    ORDER BY idEstado DESC
    LIMIT 1;

    -- Insertar en la tabla Clientes
    INSERT INTO Clientes (TipoIdentificacion, NumeroIdentificacion, Nombre, Apellido, FechaRegistro, id_Estado)
    VALUES (p_TipoIdentificacion, p_NumeroIdentificacion, p_Nombre, p_Apellido, curdate(), last_idEstado);
END //




CREATE PROCEDURE InsertarEmpleado(
    IN p_Identificacion bigint,
    IN p_Nombre VARCHAR(45),
    IN p_Apellido VARCHAR(45),
    IN p_FechaIngreso DATE,
    IN p_Cargo VARCHAR(100)
)
BEGIN
    -- Insertar en la tabla Empleados
    INSERT INTO Empleados (Identificacion, Nombre, Apellido, FechaIngreso, Cargo)
    VALUES (p_Identificacion, p_Nombre, p_Apellido, p_FechaIngreso, p_Cargo);
END //

select * from clientes;

CREATE PROCEDURE InsertarTipoTurnoAuto(
    IN p_Tipo ENUM('Retiros', 'Consignaciones', 'Prestamos'),
    IN p_Nombre VARCHAR(45)
)
BEGIN
    DECLARE last_idEmpleado INT;

    -- Obtener el último id de Empleados
    SELECT idEmpleados INTO last_idEmpleado
    FROM Empleados
    ORDER BY idEmpleados DESC
    LIMIT 1;

    -- Insertar en la tabla TipoTurno
    INSERT INTO TipoTurno (Tipo, Modulo, idEmpleado)
    VALUES (p_Tipo, p_Nombre, last_idEmpleado);
END //


CREATE PROCEDURE InsertarHistorialTurnoAuto(
    IN p_Fecha_Cambio DATE,
    IN p_Estado_Anterior ENUM('Pendiente', 'Atendido', 'Cancelado'),
    IN p_Estado_Nuevo ENUM('Pendiente', 'Atendido', 'Cancelado')
)
BEGIN
    DECLARE last_TipoTurno_id INT;
    DECLARE last_Turno_idTurno INT;

    -- Obtener el último id de TipoTurno
    SELECT id INTO last_TipoTurno_id
    FROM TipoTurno
    ORDER BY id DESC
    LIMIT 1;

    -- Obtener el último id de Turno
    SELECT idTurno INTO last_Turno_idTurno
    FROM Turno
    ORDER BY idTurno DESC
    LIMIT 1;

    -- Insertar en la tabla Historial_de_turnos
    INSERT INTO Historial_de_turnos (TipoTurno_id, Turno_idTurno, Fecha_Cambio, Estado_Anterior, Estado_Nuevo)
    VALUES (last_TipoTurno_id, last_Turno_idTurno, p_Fecha_Cambio, p_Estado_Anterior, p_Estado_Nuevo);
END //

call MostrarProductividadEmpleados()//

CREATE PROCEDURE InsertarTurnoAuto(
    IN p_Fecha DATE,
    IN p_HoraInicio TIME,
    IN p_HoraFin TIME,
    IN p_TiempoEsperado TIME,
    IN p_Modulo varchar(45),
    IN p_NombreTurno varchar(10)
)
BEGIN
    DECLARE last_Clientes_id INT;
    DECLARE last_TipoTurno_id INT;

    -- Obtener el último id de Clientes
    SELECT idClientes INTO last_Clientes_id
    FROM Clientes
    ORDER BY idClientes DESC
    LIMIT 1;

    -- Obtener el último id de TipoTurno
    SELECT id INTO last_TipoTurno_id
    FROM TipoTurno
    ORDER BY id DESC
    LIMIT 1;

    -- Insertar en la tabla Turno
    INSERT INTO Turno (Fecha, HoraInicio, HoraFin, TiempoEsperado, Clientes_idClientes, TipoTurno_id, Modulo, NombreTurno)
    VALUES (p_Fecha, p_HoraInicio, p_HoraFin, p_TiempoEsperado, last_Clientes_id, last_TipoTurno_id, p_Modulo, p_NombreTurno);
END //


SELECT * FROM turno//

CREATE PROCEDURE InsertarUsuarioAuto(
    IN p_NombreUsuario VARCHAR(45),
    IN p_Contraseña VARCHAR(45),
    IN p_RolUsuario ENUM('Empleado', 'Administrador')
)
BEGIN
    DECLARE last_idEmpleado INT;

    -- Obtener el último id de Empleados
    SELECT idEmpleados INTO last_idEmpleado
    FROM Empleados
    ORDER BY idEmpleados DESC
    LIMIT 1;

    -- Insertar en la tabla Usuario
    INSERT INTO Usuario (NombreUsuario, Contraseña, RolUsuario, IdEmpleado)
    VALUES (p_NombreUsuario, p_Contraseña, p_RolUsuario, last_idEmpleado);
END //


CREATE PROCEDURE ActualizarCliente (
    idCliente INT,
    opcion VARCHAR(20)
)
BEGIN
    DECLARE idTurnos INT;
    DECLARE clienteEnEspera INT;

    IF EXISTS (SELECT 1 FROM clientes WHERE idClientes = idClientes) THEN
        SET idTurnos = (
            SELECT idTurno 
            FROM turno
            WHERE Clientes_idClientes = idCliente
            LIMIT 1
        );

        SET clienteEnEspera = (
            SELECT id_Estado 
            FROM clientes c 
            INNER JOIN turno t 
            ON c.idClientes = t.Clientes_idClientes 
            WHERE c.id_Estado = 3 
            AND t.idTurno = idTurnos 
            AND c.idClientes = idCliente
        );

        IF clienteEnEspera = 3 THEN
            IF opcion = "Atendido" THEN
                UPDATE clientes 
                SET id_Estado = 1
                WHERE idClientes = idCliente;
            ELSEIF opcion = "Abandonado" THEN
                UPDATE clientes 
                SET id_Estado = 2
                WHERE idClientes = idCliente;
            END IF;

            UPDATE turno 
            SET HoraFin = NOW(), 
                TiempoEsperado = calcular_tiempo(idTurnos)
            WHERE idTurno = idTurnos;
        END IF;
    END IF;
END //
 

Create procedure ActualizarTurno(ideEmpleado int, idCliente int, nombreTurno varchar(5)) begin
	declare clienteEnEspera int;
     SET clienteEnEspera = (SELECT id_Estado FROM clientes c 
        INNER JOIN turno t ON c.idClientes = t.Clientes_idClientes 
        WHERE c.id_Estado = 3 AND t.NombreTurno = nombreTurno AND c.idClientes = idCliente);
    
    if clienteEnEspera = 3 then
		update turno set Empleados_idEmpleados = ideEmpleado where Clientes_idClientes = idCliente;
    end if;
end//

call ActualizarCliente(2,'Atendido');//

SELECT id_Estado 
        FROM clientes c 
        INNER JOIN turno t 
        ON c.idClientes = t.Clientes_idClientes 
        WHERE c.id_Estado = 3 
        AND t.NombreTurno = 'R4' 
        AND c.idClientes = 2;//

select * from turno;//
select * from clientes c inner join turno t on c.idClientes = t.Clientes_idClientes ;//

CREATE PROCEDURE ConseguirUltimaIdEmpleado()
BEGIN
    SELECT idEmpleados
    FROM empleados order by idEmpleados desc limit 1;
END //




delimiter ;
call MostrarEmpleados();
call mostrarclientes();
call MostrarClientesAtendidosEnElDia();
call MostrarClientesAtendidosEnLaSemana();
call MostrarClientesAtendidos();
call MostrarClientes();

call ConseguirUltimaIdEmpleado();

DELIMITER //


    
    
