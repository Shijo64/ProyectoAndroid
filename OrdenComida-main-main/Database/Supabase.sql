CREATE TABLE Mesas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE Cuentas (
    id SERIAL PRIMARY KEY,
    mesa_id INTEGER REFERENCES Mesas(id) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE Platillos (
    id SERIAL PRIMARY KEY,
    cuenta_id INTEGER REFERENCES Cuentas(id) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    cantidad INTEGER NOT NULL,
    extras TEXT
);


INSERT INTO Mesas (nombre) VALUES ('9');


SELECT id FROM Mesas WHERE nombre = '9';


INSERT INTO Cuentas (mesa_id, nombre) VALUES (1, 'Pepe');


SELECT id FROM Cuentas WHERE nombre = 'Pepe' AND mesa_id = 1;


INSERT INTO Platillos (cuenta_id, nombre, cantidad, extras) 
VALUES (1, 'Chiles rellenos', 1, NULL);

--query para agarra los platillos de una mesa en espesifico
SELECT Platillos.nombre, Platillos.cantidad, Platillos.extras
FROM Mesas
JOIN Cuentas ON Mesas.id = Cuentas.mesa_id
JOIN Platillos ON Cuentas.id = Platillos.cuenta_id
WHERE Mesas.nombre = '9';

