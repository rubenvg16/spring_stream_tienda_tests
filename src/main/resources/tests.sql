use tienda;


-- Ejercicio 1
SELECT nombre, precio FROM producto;

-- Ejercicio 2
SELECT p.nombre, p.precio * 1.08
    FROM producto p;

-- Ejercicio 3
SELECT upper(nombre), precio
    FROM producto;

-- Ejercicio 4
SELECT f.nombre, upper(substring(f.nombre, 1, 2))
    FROM fabricante f;

-- Ejercicio 5
SELECT DISTINCT f.codigo
    FROM fabricante f
    JOIN producto p ON f.codigo = p.codigo_fabricante;

-- Ejercicio 6
SELECT nombre
    FROM fabricante
    ORDER BY nombre DESC;

-- Ejercicio 7
SELECT nombre
    FROM producto
    ORDER BY nombre ASC, precio DESC;

-- Ejercicio 8
SELECT *
    FROM fabricante
    LIMIT 5;

-- Ejercicio 9
SELECT *
    FROM fabricante
    LIMIT 2 OFFSET 3;  -- Empieza en el cuarto registro

-- Ejercicio 10
SELECT nombre, precio
    FROM producto
    ORDER BY precio ASC
    LIMIT 1;

-- Ejercicio 11
SELECT nombre, precio
    FROM producto
    ORDER BY precio DESC
    LIMIT 1;

-- Ejercicio 12
SELECT p.nombre
    FROM producto p
    WHERE p.codigo_fabricante = 2;

-- Ejercicio 13
SELECT p.nombre
    FROM producto p
    WHERE p.precio<=120;

-- Ejercicio 14
SELECT *
    FROM producto p
    WHERE p.precio>=400;

-- Ejercicio 15
SELECT *
    FROM producto p
    WHERE p.precio BETWEEN 80 AND 300;

-- Ejercicio 16
SELECT *
    FROM producto p
    WHERE p.precio>=200 AND p.codigo_fabricante=6;

-- Ejercicio 17
SELECT *
    FROM  producto p
    WHERE p.codigo_fabricante = 1
       OR p.codigo_fabricante = 3
       OR p.codigo_fabricante = 5;

-- Ejercicio 18
SELECT p.nombre, p.precio*100
    FROM producto p;

-- Ejercicio 19
SELECT nombre
    FROM fabricante
    WHERE nombre LIKE 's%';

-- Ejercicio 20
SELECT *
    FROM producto
    WHERE nombre LIKE '%Port_til%';

-- Ejercicio 21
SELECT *
    FROM producto
    WHERE nombre LIKE '%Monitor%' AND precio<=215;

-- Ejercicio 22
SELECT p.nombre, p.precio
    FROM producto p
    WHERE precio>=180
    ORDER BY p.precio, p.nombre;

-- Ejercicio 23
SELECT p.nombre, p.precio, f.nombre
    FROM producto p
    JOIN fabricante f ON p.codigo_fabricante = f.codigo
    ORDER BY f.nombre ASC;

-- Ejercicio 24
SELECT p.nombre, p.precio
    FROM producto p
    ORDER BY p.precio DESC LIMIT 1;

-- Ejercicio 25
SELECT p.nombre
    FROM producto p
    JOIN fabricante f on p.codigo_fabricante = f.codigo
    WHERE p.precio>200 AND f.nombre = 'Crucial';

-- Ejercicio 29
SELECT *
    FROM fabricante f
    WHERE f.codigo
    NOT IN (SELECT producto.codigo_fabricante FROM producto);

-- Variante ejercicio 29
SELECT f.* FROM fabricante f
    LEFT JOIN producto p on f.codigo = p.codigo_fabricante
    WHERE p.codigo_fabricante IS NULL;

-- Ejercicio 30
SELECT count(*) FROM producto;

-- Ejercicio 31
SELECT count(distinct f.codigo) FROM fabricante f;

-- Ejercicio 32
SELECT AVG(p.precio) FROM producto p;

-- Ejercicio 33
SELECT min(p.precio) FROM producto p;

-- Ejercicio 35
SELECT count(p.codigo_fabricante) FROM producto p
    JOIN tienda.fabricante f on f.codigo = p.codigo_fabricante
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

-- Ejercicio 38
SELECT f.nombre, COUNT(p.nombre)
    FROM fabricante f LEFT JOIN producto p ON f.codigo = p.codigo_fabricante
    GROUP BY f.nombre
    ORDER BY COUNT(p.nombre) DESC;

-- Ejercicio 39
SELECT f.nombre, min(p.precio), max(p.precio), avg(p.precio), count(p.codigo)
    FROM fabricante f
    LEFT JOIN tienda.producto p ON f.codigo = p.codigo_fabricante
    GROUP BY f.codigo;

-- Ejercicio 40
SELECT f.nombre, min(p.precio), max(p.precio), avg(p.precio), count(p.codigo)
    FROM fabricante f
    LEFT JOIN tienda.producto p ON f.codigo = p.codigo_fabricante
    GROUP BY f.codigo
    HAVING avg(p.precio) > 200;

-- Ejercicio 41
SELECT f.nombre
    FROM fabricante f JOIN tienda.producto p ON f.codigo = p.codigo_fabricante
    GROUP BY f.nombre
    HAVING count(p.codigo) >= 2;

-- Ejercicio 42
SELECT f.nombre, count(p.codigo)
    FROM fabricante f JOIN producto p ON f.codigo = p.codigo_fabricante
    WHERE p.precio >= 220
    GROUP BY f.nombre
    HAVING count(p.codigo) > 0
    ORDER BY count(p.codigo) DESC;


-- Ejercicio 43
SELECT f.nombre
    FROM fabricante f JOIN producto p ON f.codigo = p.codigo_fabricante
    GROUP BY f.nombre
    HAVING SUM(p.precio) > 1000;

-- Ejercicio 44
SELECT f.nombre, sum(p.precio)
    FROM fabricante f JOIN tienda.producto p ON f.codigo = p.codigo_fabricante
    GROUP BY f.nombre
    HAVING sum(p.precio) > 1000
    ORDER BY sum(p.precio) ASC;