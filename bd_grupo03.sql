DROP DATABASE IF EXISTS `db_ciberpet`;
-- Crear la base de datos
CREATE DATABASE `db_ciberpet`;
USE `db_ciberpet`;

CREATE TABLE TB_TIPO (
    idTipo INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(15)
);

CREATE TABLE TB_USUARIO (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60),
    apellidos VARCHAR(45),
    nombreUser VARCHAR(45),
    correo VARCHAR(100),
    contrasena VARCHAR(45),
    telefono VARCHAR(12),
    direccion VARCHAR(155),
    tipo INT,
    FOREIGN KEY (tipo) REFERENCES TB_TIPO(idTipo)
);

CREATE TABLE TB_SERVICIO (
    idServicio INT PRIMARY KEY AUTO_INCREMENT,
    nom_servicio VARCHAR(50),
    descrip_servicio VARCHAR(50),
    precio_servicio DOUBLE
);


CREATE TABLE TB_CITA (
    idCita INT PRIMARY KEY AUTO_INCREMENT,
    fecha_cita DATE,
    hora_cita VARCHAR(45),
    idUsuario INT,
    idServicio INT,
    Motivo VARCHAR(155),
    estado CHAR(1) NOT NULL DEFAULT 'P' CHECK (estado IN ('P', 'E', 'C', 'A')),
    FOREIGN KEY (idUsuario) REFERENCES TB_USUARIO(idUsuario),
    FOREIGN KEY (idServicio) REFERENCES TB_SERVICIO(idServicio)
);

CREATE TABLE TB_CATEGORIA (
    idCategoria INT PRIMARY KEY AUTO_INCREMENT,
    descrip_categoria VARCHAR(50)
);

CREATE TABLE TB_PRODUCTO (
    idProduct CHAR(5) PRIMARY KEY,
    nombre_producto VARCHAR(50),
    descrip_producto VARCHAR(100),
    precio DOUBLE,
    stock INT,
    idCategoria INT,
    estado BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (idCategoria) REFERENCES TB_CATEGORIA(idCategoria)
);

CREATE TABLE TB_COMPRA (
    idBoleta INT PRIMARY KEY AUTO_INCREMENT,
    fecha_boleta DATE,
    idUsuario INT,
    FOREIGN KEY (idUsuario) REFERENCES TB_USUARIO(idUsuario)
);

CREATE TABLE TB_DET_COMPRA (
    idBoleta INT,
    idProduct CHAR(5),
    cantidad INT,
    Total DOUBLE,
    PRIMARY KEY (idBoleta, idProduct),
    FOREIGN KEY (idBoleta) REFERENCES TB_COMPRA(idBoleta),
    FOREIGN KEY (idProduct) REFERENCES TB_PRODUCTO(idProduct)
);

INSERT INTO TB_SERVICIO (nom_servicio, descrip_servicio, precio_servicio) VALUES 
('Aseo', 'Aseo completo para mascotas.', 50.00),
('Castración', 'Procedimiento quirúrgico de castración.', 150.00),
('Nutrición', 'Consulta personalizada para mejorar la dieta.', 30.00);

INSERT INTO TB_TIPO (descripcion) VALUES 
('Administrador'),
('Cliente');

INSERT INTO TB_USUARIO (nombre, apellidos, nombreUser, correo, contrasena, telefono, direccion, tipo) VALUES 
('Juan', 'Pérez', 'juanp', 'juan.perez@gmail.com', '1234abcd', '987654321', 'Calle Ficticia 123', 2),
('María', 'Gómez', 'mariag', 'maria.gomez@gmail.com', 'abcd1234', '912345678', 'Avenida Real 456', 2),
('Carlos', 'López', 'carlosl', 'carlos.lopez@gmail.com', '5678abcd', '987654320', 'Calle 7 789', 2),
('Ana', 'Martínez', 'anam', 'ana.martinez@gmail.com', 'abcd5678', '922334455', 'Calle Central 101', 2),
('Pedro', 'Sánchez', 'pedros', 'pedro.sanchez@gmail.com', 'pqrs1234', '933445566', 'Calle de la Luna 200', 2),
('Laura', 'Rodríguez', 'laurar', 'laura.rodriguez@gmail.com', 'abcd7890', '944556677', 'Calle del Sol 300', 2),
('Sergio', 'Hernández', 'sergioh', 'sergio.hernandez@gmail.com', 'abcd1234', '955667788', 'Calle La Paz 400', 2),
('Julia', 'Vázquez', 'juliav', 'julia.vazquez@gmail.com', '1234pqrs', '966778899', 'Calle 8 500', 2),
('David', 'Ramírez', 'davidr', 'david.ramirez@gmail.com', '5678pqrs', '977889900', 'Calle del Río 600', 2),
('Marta', 'González', 'martag', 'marta.gonzalez@gmail.com', 'mnop1234', '988990011', 'Calle de la Sierra 700', 2),
('Juan', 'Rodríguez', 'juanr', 'juan.rodriguez@gmail.com', 'qwerty123', '999001122', 'Calle del Bosque 800', 2),
('Luis', 'Morales', 'luism', 'luis.morales@gmail.com', 'abcdxyz1', '900112233', 'Calle 9 900', 2),
('Isabel', 'Díaz', 'isabeld', 'isabel.diaz@gmail.com', 'xyz123ab', '911223344', 'Calle del Mar 1000', 2),
('Antonio', 'Fernández', 'antoniof', 'antonio.fernandez@gmail.com', 'abc123xyz', '922334455', 'Avenida Libertad 2000', 2),
('Beatriz', 'Jiménez', 'beatrizj', 'beatriz.jimenez@gmail.com', '1234efgh', '933445566', 'Calle Norte 3000', 2),
('Elena', 'Hernández', 'elenah', 'elena.hernandez@gmail.com', '5678ijkl', '944556677', 'Calle Este 4000', 2),
('Ricardo', 'Gutiérrez', 'ricardog', 'ricardo.gutierrez@gmail.com', 'mnop5678', '955667788', 'Calle Oeste 5000', 2),
('Sandra', 'Álvarez', 'sandraa', 'sandra.alvarez@gmail.com', '1234qrst', '966778899', 'Calle del Sur 6000', 2), 
('Javier', 'Serrano', 'javiers', 'javier.serrano@gmail.com', '5678uvwx', '977889900', 'Calle del Sol 7000', 2),
('David', 'Martínez', 'davidm', 'david.martinez@gmail.com', 'abcd1111', '988990011', 'Calle del Río 8000', 1),  -- Administrador
('Lucía', 'Pérez', 'luciap', 'lucia.perez@gmail.com', 'admin1234', '900112233', 'Avenida Central 9000', 1);  -- Administrador

INSERT INTO TB_CATEGORIA  VALUES (1,'Perros');
INSERT INTO TB_CATEGORIA  VALUES (2,'Gatos');
INSERT INTO TB_CATEGORIA  VALUES (3,'Accesorios');

-- Productos para Perros (P0001 - P0010)
INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0001', 'Pro Plan Adulto Perro', 'Alimento Adulto Razas Medianas, Sabor Pollo 13kg', 90.00, 20, 1);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0002', 'Pro Plan Cachorro Perro', 'Alimento Cachorro Razas Grandes Optistar', 70.00, 15, 1);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0003', 'Excellent Cachorro Perro', 'Alimento Completo Cachorro para Perros', 55.00, 25, 1);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0004', 'Nupec Cachorro', 'Alimento Nupec Cachorro', 60.00, 18, 1);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0005', 'Pro Plan Adulto Pequeño', 'Alimento Pro Plan Adulto, Razas Pequeñas', 85.00, 22, 1);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0006', 'Pro Plan Piel Sensible', 'Alimento Pro Plan Piel Sensible Razas Pequeñas', 87.00, 20, 1);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0007', 'Pro Plan Exigente', 'Alimento Pro Plan Exigente Optienrich', 95.00, 18, 1);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0008', 'Pro Plan Cachorro Sensible', 'Alimento Cachorro Piel Sensible All Breeds 3kg', 65.00, 15, 1);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0009', 'Pro Plan Cachorro 13kg', 'Alimento Pro Plan Cachorro Complete Optistar, 13kg', 100.00, 12, 1);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0010', 'Pro Plan OptiFit', 'Alimento Bajo en Calorías, Razas Grandes y Medianas', 110.00, 14, 1);

-- Productos para Gatos (P0011 - P0020)
INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0011', 'Pro Plan Adulto Gato', 'Alimento para Gatos Adultos, Sabor Pollo 13kg', 85.50, 20, 2);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0012', 'Pro Plan Cachorro Gato', 'Alimento para Cachorros Gato, Razas Grandes', 60.00, 15, 2);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0013', 'Excellent Cachorro Gato', 'Alimento Completo para Cachorros', 45.00, 25, 2);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0014', 'Pro Plan 24 Pouches', 'Paquete 24 Sobres de 85g, Gato Adulto, Pollo', 50.00, 18, 2);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0015', 'Pro Plan Urinary', 'Paquete 24 Sobres de 85g, Gato Urinary', 52.00, 10, 2);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0016', 'Bravecto Antipulgas', 'Antipulgas y Garrapatas para Gatos', 80.00, 30, 2);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0017', 'Excellent Urinary Gato', 'Alimento Gato Adulto Urinary + Regalo', 65.00, 20, 2);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0018', 'Nupec Felino Kitten', 'Paquete 2 Nupec Felino Kitten 1.5kg', 55.00, 22, 2);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0019', 'Feliway Classic', 'Recarga Feliway Classic 48 ML', 35.00, 12, 2);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0020', 'Activa Pets Colágeno', 'Suplemento Colágeno para Mascotas', 25.00, 50, 2);

-- Productos para Accesorios (P0021 - P0030)
INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0021', 'Collares para Cachorros', 'Juego de 12 Collares para Cachorros', 25.00, 50, 3);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0022', 'Collar para Gato con AirTag', 'Collar con Apple Air Tag y Campana para Gatos', 45.00, 30, 3);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0023', 'Meowant Caja Sanitaria', 'Caja Sanitaria Autolimpiable para Gatos', 120.00, 12, 3);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0024', 'Cama National Geographic', 'Cama para Mascotas National Geographic', 75.00, 20, 3);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0025', 'Carda Gato Madera', 'Carda para Gato de Madera, Chica', 20.00, 40, 3);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0026', 'Peine Cerrado Madera', 'Peine Cerrado para Gatos, De Madera', 18.00, 35, 3);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0027', 'Removedor Pelo Perro', 'Removedor de Pelo para Perro, Grooming', 28.00, 50, 3);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0028', 'Furminator Brush', 'Cepillo Furminator para Perros y Gatos', 60.00, 25, 3);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0029', 'Toallitas Húmedas Pet Wipes', 'Paquete Toallitas Húmedas para Mascotas', 15.00, 50, 3);

INSERT INTO TB_PRODUCTO (idProduct, nombre_producto, descrip_producto, precio, stock, idCategoria) 
VALUES ('P0030', 'Plato para Perro Gato', 'Plato de Comida para Perro o Gato', 12.00, 40, 3);

-- Citas de la tabla TB_CITA
INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-21', '10:00', 1, 1, 'Consulta de Aseo');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-22', '11:00', 2, 2, 'Consulta de Castración');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-22', '15:00', 3, 3, 'Consulta de Nutrición');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-23', '09:00', 4, 1, 'Consulta de Aseo');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-23', '10:30', 5, 2, 'Consulta de Castración');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-23', '14:00', 6, 3, 'Consulta de Nutrición');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-24', '08:30', 7, 1, 'Consulta de Aseo');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-24', '11:00', 8, 2, 'Consulta de Castración');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-24', '13:30', 9, 3, 'Consulta de Nutrición');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-25', '09:00', 10, 1, 'Consulta de Aseo');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-25', '11:00', 11, 2, 'Consulta de Castración');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-25', '14:00', 12, 3, 'Consulta de Nutrición');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-26', '08:00', 13, 1, 'Consulta de Aseo');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-26', '09:30', 14, 2, 'Consulta de Castración');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-26', '12:00', 15, 3, 'Consulta de Nutrición');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-27', '10:00', 16, 1, 'Consulta de Aseo');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-27', '13:00', 17, 2, 'Consulta de Castración');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-27', '16:00', 18, 3, 'Consulta de Nutrición');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-28', '09:00', 19, 1, 'Consulta de Aseo');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-28', '11:30', 20, 2, 'Consulta de Castración');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-29', '10:00', 15, 3, 'Consulta de Nutrición');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-29', '12:30', 1, 1, 'Consulta de Aseo');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-30', '09:00', 1, 2, 'Consulta de Castración');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-30', '14:00', 2, 3, 'Consulta de Nutrición');

INSERT INTO TB_CITA (fecha_cita, hora_cita, idUsuario, idServicio, Motivo) 
VALUES ('2024-12-31', '10:00', 3, 1, 'Consulta de Aseo');
