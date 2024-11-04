use tienda;


-- Ejercicio 12


-- Ejercicio 13
SELECT p.nombre FROM producto p WHERE p.precio<=120;

-- Ejercicio 14
SELECT * FROM producto p where p.precio>=400;

-- Ejercicio 15
SELECT * FROM producto p WHERE p.precio between 80 and 300

-- Ejercicio 16
SELECT * FROM producto p WHERE p.precio>=200 and p.codigo_fabricante=6;

-- Ejercicio 17
SELECT * FROM  producto p WHERE p.codigo_fabricante = 1 or p.codigo_fabricante = 3 or p.codigo_fabricante = 5;

-- Ejercicio 18
SELECT p.nombre, p.precio*100 FROM producto p;

-- Ejercicio 19
SELECT nombre FROM fabricante WHERE nombre LIKE 's%';

-- Ejercicio 20
SELECT * FROM producto WHERE nombre LIKE '%Port_til%';

-- Ejercicio 21
SELECT * FROM producto WHERE nombre LIKE '%Monitor%' AND precio<=215;

-- Ejercicio 22
SELECT  p.nombre, p.precio FROM producto p WHERE precio>=180 ORDER BY p.precio, p.nombre;

-- Ejercicio 23
SELECT p.nombre, p.precio, f.nombre FROM producto p
                                             JOIN fabricante f ON p.codigo_fabricante = f.codigo
ORDER BY f.nombre ASC;

-- Ejercicio 24
SELECT p.nombre, p.precio FROM producto p ORDER BY p.precio DESC LIMIT 1;

-- Ejercicio 25
SELECT p.nombre FROM producto p
                         JOIN fabricante f on p.codigo_fabricante = f.codigo
WHERE p.precio>200 AND f.nombre = 'Crucial';

-- Ejercicio 29
SELECT * FROM fabricante f WHERE f.codigo Not In (SELECT producto.codigo_fabricante FROM producto);

-- Variante ejercicio 29
SELECT f.* FROM fabricante f
    LEFT JOIN producto p on f.codigo = p.codigo_fabricante
    WHERE p.codigo_fabricante IS NULL;

-- Ejercicio 30
SELECT count(*) FROM producto;

-- Ejercicio 31
SELECT count(distinct f.codigo) from fabricante f;

-- Ejercicio 32
SELECT AVG(p.precio) FROM producto p;

-- Ejercicio 33
SELECT min(p.precio) FROM producto p;

-- Ejercicio 35
SELECT count(p.codigo_fabricante) FROM producto p
    Join tienda.fabricante f on f.codigo = p.codigo_fabricante
    WHERE lower(f.nombre) = lower('asus');

-- Ejercicio 36
SELECT avg(p.precio) FROM producto p
    JOIN tienda.fabricante f on f.codigo = p.codigo_fabricante
    WHERE LOWER(f.nombre) = LOWER('asus');

-- Ejercicio 37
SELECT min(p.precio), max(p.precio), avg(p.precio), count(p.precio)
    FROM producto p
    JOIN tienda.fabricante f on f.codigo = p.codigo_fabricante
    WHERE lower(f.nombre) = lower('crucial');