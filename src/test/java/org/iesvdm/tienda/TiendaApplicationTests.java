package org.iesvdm.tienda;

import org.iesvdm.tienda.modelo.Fabricante;
import org.iesvdm.tienda.modelo.Producto;
import org.iesvdm.tienda.repository.FabricanteRepository;
import org.iesvdm.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;


@SpringBootTest
class TiendaApplicationTests {

	@Autowired
	FabricanteRepository fabRepo;
	
	@Autowired
	ProductoRepository prodRepo;
    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

	@Test
	void testAllFabricante() {
		var listFabs = fabRepo.findAll();
		
		listFabs.forEach(f -> {
			System.out.println(">>"+f+ ":");
			f.getProductos().forEach(System.out::println);
		});
	}
	
	@Test
	void testAllProducto() {
		var listProds = prodRepo.findAll();

		listProds.forEach( p -> {
			System.out.println(">>"+p+":"+"\nProductos mismo fabricante "+ p.getFabricante());
			p.getFabricante().getProductos().forEach(pF -> System.out.println(">>>>"+pF));
		});
				
	}

	
	/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
	@Test
	void test1() {
		var listProds = prodRepo.findAll();
		listProds.stream().map(p -> "nombre = " + p.getNombre() + " precio = " + p.getPrecio()).toList();
	}
	
	
	/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
	 */
	@Test
	void test2() {
		var listProdsPrecEur = prodRepo.findAll();

		var listProdsPrecDolar = listProdsPrecEur.stream()
				.map(p -> {
					Producto pDolar = new Producto();
					pDolar.setCodigo(p.getCodigo());
					pDolar.setNombre(p.getNombre());
					pDolar.setPrecio(p.getPrecio()*1.08);
					pDolar.setFabricante(p.getFabricante());
					return pDolar;
				})
				.toList();

		System.out.println("Lista prods precioEur: ");
		listProdsPrecEur.forEach(System.out::println);

		System.out.println("Lista prods precioDolar: ");
		listProdsPrecDolar.forEach(System.out::println);
	}
	
	/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {
		var listProds = prodRepo.findAll();

		listProds.forEach(p -> p.setNombre(p.getNombre().toUpperCase()));
	}
	
	/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
		var listFabs = fabRepo.findAll();

		listFabs.forEach(f -> {
			String nomIniciales = f.getNombre() + " " + f.getNombre().substring(0,2).toUpperCase();
			System.out.println(nomIniciales);
		});
	}
	
	/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {
		var listFabs = fabRepo.findAll();


	}
	
	/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
		var listFabs = fabRepo.findAll();

		listFabs.stream()
					.sorted(comparing(Fabricante::getNombre).reversed())
					.toList()
					.forEach(System.out::println);

	}
	
	/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
		var listProds = prodRepo.findAll();

		listProds.stream()
				.sorted(comparing(Producto::getNombre).thenComparing(Producto::getPrecio, reverseOrder()))
				.toList()
				.forEach(producto -> System.out.println(producto.getNombre()));


	}
	
	/**
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
		var listFabs = fabRepo.findAll();

		var lista = listFabs.stream()
				.limit(5)
				.toList();

		lista.forEach(System.out::println);
	}
	
	/**
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
		var listFabs = fabRepo.findAll();

		var lista = listFabs.stream()
				.skip(3)
				.limit(2)
				.toList();

		lista.forEach(System.out::println);

		Assertions.assertEquals(lista.getFirst().getNombre(), "Samsung");

	}
	
	/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
		var listProds = prodRepo.findAll();

		var productoBarato = listProds.stream()
				.sorted(comparing(Producto::getPrecio))
				.map(producto -> "Nombre: " + producto.getNombre() + ", Precio:  " + producto.getPrecio())
				.findAny();

//		Assertions.assertTrue(productoBarato.orElse(""));
	}
	
	/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
		var listProds = prodRepo.findAll();

		listProds.stream()
				.max(comparing(Producto::getPrecio))
				.ifPresentOrElse(producto -> System.out.println(producto.getNombre() + producto.getPrecio()),
						() -> System.out.println("Colección vacía"));

	}
	
	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 * 
	 */
	@Test
	void test12() {
		var listProds = prodRepo.findAll();

		var listProdsFab2 = listProds.stream()
				.filter(producto -> producto.getFabricante().getCodigo() == 2)
				.map(Producto::getNombre)
				.toList();

		System.out.println(listProdsFab2);

		Assertions.assertTrue(listProdsFab2.contains("Portátil Yoga 520") && listProdsFab2.contains("Portátil Ideapd 320"));
		Assertions.assertEquals(2, listProdsFab2.size());
	}
	
	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
						.filter(producto -> producto.getPrecio() <=120)
						.map(producto -> producto.getNombre())
						.toList();

		result.forEach(System.out::println);
		Assertions.assertEquals(3, result.size());
	}
	
	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
		var listProds = prodRepo.findAll();

		List<Producto> listProdsPrecio= listProds.stream()
					.filter(producto -> producto.getPrecio()>=400)
					.toList();

		listProdsPrecio.forEach(System.out::println);
		Assertions.assertEquals(3, listProdsPrecio.size());
	}
	
	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€. 
	 */
	@Test
	void test15() {
		var listProds = prodRepo.findAll();

		var listaProdsPrecio = listProds.stream()
									.filter(producto -> producto.getPrecio()>=80
											&& producto.getPrecio()<=300)
									.toList();

		listaProdsPrecio.forEach(System.out::println);
		Assertions.assertEquals(7, listaProdsPrecio.size());

	}
	
	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
		var listProds = prodRepo.findAll();

		var listProdsPrecio = listProds.stream()
									.filter(producto -> producto.getPrecio()>=200
										&& producto.getFabricante().getCodigo()==6)
									.toList();

		listProdsPrecio.forEach(System.out::println);
		Assertions.assertEquals(1, listProdsPrecio.size());
	}
	
	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
		var listProds = prodRepo.findAll();

		Set<Integer> codigos = Set.of(1,3,5);

		var result = listProds.stream()
				.filter(producto -> codigos.contains(producto.getFabricante().getCodigo()))
//				.filter(p -> p.getFabricante().getCodigo()==1
//						|| p.getFabricante().getCodigo()==3          *SIN USAR EL SET*
//						|| p.getFabricante().getCodigo()==5)
				.toList();

		result.forEach(System.out::println);
		Assertions.assertTrue(result.get(0).getFabricante().getCodigo()==1
				|| result.get(0).getFabricante().getCodigo()==3
				|| result.get(0).getFabricante().getCodigo()==5);
		Assertions.assertEquals(5, result.size());
	}
	
	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.map(producto -> "Nombre: " + producto.getNombre() + ", Precio(en céntimos): " + producto.getPrecio()*100)
				.toList();

		result.forEach(System.out::println);

		Assertions.assertEquals(11, result.size());
		Assertions.assertTrue(result.get(7).contains("55900"));
	}
	
	
	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {
		var listFabs = fabRepo.findAll();

		var result = listFabs.stream()
								.filter(t-> t.getNombre().substring(0,1).equalsIgnoreCase("S"))
								.toList();

		result.forEach(System.out::println);

		Assertions.assertEquals(2, result.size());
	}
	
	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
								.filter(p-> p.getNombre().matches(".*Port[á|a]til.*"))
								.toList();

		result.forEach(System.out::println);

		Assertions.assertEquals(2, result.size());
	}
	
	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.filter(producto -> producto.getNombre().contains("Monitor")
								&& producto.getPrecio()<215)
				.toList();

		result.forEach(System.out::println);
		Assertions.assertEquals(1, result.size());

	}
	
	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */

	@Test
	void test22() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.filter(producto -> producto.getPrecio()>=180)
				.sorted(comparing(Producto::getPrecio, reverseOrder())
						.thenComparing(Producto::getNombre))
				.toList();

		result.forEach(System.out::println);
		Assertions.assertEquals(7, result.size());

	}
	
	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. 
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.sorted(comparing(producto -> producto.getFabricante().getNombre()))
				.map(producto -> producto.getNombre()
						+ producto.getPrecio()
						+ producto.getFabricante().getNombre())

				.toList()
				;

		result.forEach(System.out::println);

		Assertions.assertEquals(11, result.size());
	}
	
	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.sorted(comparing(Producto::getPrecio,reverseOrder()))
				.findFirst()
				.map(producto -> producto.getNombre() + " "
						+ producto.getPrecio() + " "
						+ producto.getFabricante().getNombre())
				.orElse(null);

		System.out.println(result);
		Assertions.assertEquals("GeForce GTX 1080 Xtreme",result);
	}
	
	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		var listProds = prodRepo.findAll();
		//TODO	
	}
	
	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		var listProds = prodRepo.findAll();

		Set<String> setFabricantes = new HashSet<>();
		setFabricantes.add("Asus");
		setFabricantes.add("Hewelett-Packard");
		setFabricantes.add("Seagate");
		var result = listProds.stream()
				.filter(p-> setFabricantes.contains(p.getFabricante().getNombre()))
				.toList();
		result.forEach(System.out::println);
		Assertions.assertEquals(3, result.size());
		setFabricantes.forEach(s -> Assertions
				.assertTrue(result.stream()
						.anyMatch(p -> p.getFabricante().getNombre().equalsIgnoreCase(s))));

	}
	
	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:

Producto                Precio             Fabricante
-----------------------------------------------------
GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
Portátil Yoga 520      |452.79            |Lenovo
Portátil Ideapd 320    |359.64000000000004|Lenovo
Monitor 27 LED Full HD |199.25190000000003|Asus

	 */		
	@Test
	void test27() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.sorted(comparing(Producto::getPrecio,reverseOrder()).thenComparing(Producto::getNombre))
				.map(producto -> producto.getNombre() + " " + producto.getPrecio() + " " + producto.getFabricante().getNombre())
				.toList();

		result.forEach(System.out::println);
	}
	
	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. 
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. 
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
Fabricante: Asus

            	Productos:
            	Monitor 27 LED Full HD
            	Monitor 24 LED Full HD

Fabricante: Lenovo

            	Productos:
            	Portátil Ideapd 320
            	Portátil Yoga 520

Fabricante: Hewlett-Packard

            	Productos:
            	Impresora HP Deskjet 3720
            	Impresora HP Laserjet Pro M26nw

Fabricante: Samsung

            	Productos:
            	Disco SSD 1 TB

Fabricante: Seagate

            	Productos:
            	Disco duro SATA3 1TB

Fabricante: Crucial

            	Productos:
            	GeForce GTX 1080 Xtreme
            	Memoria RAM DDR4 8GB

Fabricante: Gigabyte

            	Productos:
            	GeForce GTX 1050Ti

Fabricante: Huawei

            	Productos:


Fabricante: Xiaomi

            	Productos:

	 */
	@Test
	void test28() {
		var listFabs = fabRepo.findAll();

		listFabs.stream()
				.map(fabricante ->
						"Fabricante: " + fabricante.getNombre() + "\n\n"
								+ "Productos: " + "\n"
								+ fabricante.getProductos()
								.stream()
								.map(producto -> producto.getNombre() + "\n")
								.collect(Collectors.joining()))
				.forEach(System.out::println);
	}
	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
		var listFabs = fabRepo.findAll();

		var result = listFabs.stream()
				.filter(fabricante -> fabricante.getProductos().isEmpty())
				.toList();

		result.forEach(System.out::println);
		Assertions.assertEquals(2, result.size());
	}
	
	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.count();

		System.out.println(result);

		Assertions.assertEquals(11, result);
	}

	
	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream().map(Producto::getFabricante).distinct().count();

		Assertions.assertEquals(7, result);
	}
	
	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.average();

		System.out.println(result.orElse(0.0));

		Assertions.assertEquals(271.7236363636364,result.orElse(0.0));

	}
	
	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.min();

		System.out.println(result.orElse(0.0));
		Assertions.assertEquals(59.99,result.orElse(0.0));
	}
	
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.sum();

//		var result = listProds.stream()
//				.mapToDouble(Producto::getPrecio)
//				.reduce(0, (left, right) -> left+right);

		System.out.println(result);

	}
	
	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.filter(producto -> producto.getFabricante().getNombre().toLowerCase().equals("asus"))
				.count();

        Assertions.assertEquals(2, result);
	}
	
	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(producto -> producto.getFabricante().getNombre().equalsIgnoreCase("asus"))
				.mapToDouble(Producto::getPrecio)
				.average()
				.orElse(0);

		System.out.println(result);

		Assertions.assertEquals(223.995, result);
	}
	
	
	/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. 
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.filter(producto -> producto
										.getFabricante()
										.getNombre()
										.equalsIgnoreCase("crucial"))
				//.limit(1)
				.map(producto -> new Double[]{
						producto.getPrecio(),
						producto.getPrecio(),
						producto.getPrecio(),
						1.0})
				.reduce((doubles, doubles2) -> new Double[]{
						Math.min(doubles[0],doubles2[0]),
						Math.max(doubles[0],doubles2[0]),
						doubles[2]+doubles2[2],
						doubles[3]+doubles2[3]})
				.orElse(new Double[]{});

		double media = result[3]>0 ? result[2]/result[3]: 0.0;
		System.out.println("El valor mínimo: " + result[0]);
		System.out.println("El valor máximo: " + result[1]);
		System.out.println("El valor medio: " + media);
		System.out.println("El número total de valores: " + result[3]);

		Assertions.assertTrue(result[0] > 120.0);
		Assertions.assertTrue(result[1] > 755.0);
		Assertions.assertTrue(result[2] > 475.5);

	}

	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes. 
	 * El listado también debe incluir los fabricantes que no tienen ningún producto. 
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. 
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 
     Fabricante     #Productos
-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
           Asus              2
         Lenovo              2
Hewlett-Packard              2
        Samsung              1
        Seagate              1
        Crucial              2
       Gigabyte              1
         Huawei              0
         Xiaomi              0

	 */
	@Test
	void test38() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.sorted(comparing(fabricante -> fabricante.getProductos().size(),reverseOrder()))
				.map(fabricante -> fabricante.getNombre() + "						"
							+ fabricante.getProductos().size())
				.toList();
		result.forEach(System.out::println);

		Assertions.assertEquals(9, result.size());
	}
	
	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. 
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.collect(Collectors.groupingBy(
						producto -> producto.getFabricante().getNombre(),
						Collectors.reducing(new Double[]{
								Double.MAX_VALUE,
								Double.MIN_VALUE,
								0.0,
								0.0
						}, producto -> new Double[]{
								producto.getPrecio(),
								producto.getPrecio(),
								1.0
						}, (acc, curr) -> new Double[]{
								Math.min(acc[0], curr[0]),
								Math.max(acc[1], curr[1]),
								acc[2] + curr[0],
								acc[3] + curr[2]
						})
				));

		result.forEach((fabricante, precios) -> {
			double media = precios[3] > 0 ? precios[2] / precios[3] : 0.0;

			System.out.println("Fabricante: " + fabricante);
			System.out.println("Precio mínimo: " + (precios[3] > 0 ? precios[0] : "No disponible"));
			System.out.println("Precio máximo: " + (precios[3] > 0 ? precios[1] : "No disponible"));
			System.out.println("Precio medio: " + media);
			System.out.println("Número total de productos: " + precios[3]);
			System.out.println();
		});
	}
	
	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. 
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
		var listProds = prodRepo.findAll();

		var result = listProds.stream()
				.collect(Collectors.groupingBy(
						producto -> producto.getFabricante().getCodigo(),
						Collectors.reducing(new Double[] {
								Double.MAX_VALUE,
								Double.MIN_VALUE,
								0.0,
								0.0
						}, producto -> new Double[] {
								producto.getPrecio(),
								producto.getPrecio(),
								1.0
						}, (acc, curr) -> new Double[] {
								Math.min(acc[0], curr[0]),
								Math.max(acc[1], curr[1]),
								acc[2] + curr[0],
								acc[3] + curr[2]
						})
				))
				.entrySet().stream()
				.filter(entry -> (entry.getValue()[2] / entry.getValue()[3]) > 200);

		result.forEach(entry -> {
			Double[] precios = entry.getValue();
			Double precioMinimo = precios[0];
			Double precioMaximo = precios[1];
			double precioMedio = precios[2] / precios[3];
			Double totalProductos = precios[3];

			System.out.println("Precio mínimo: " + precioMinimo);
			System.out.println("Precio máximo: " + precioMaximo);
			System.out.println("Precio medio: " + precioMedio);
			System.out.println("Número total de productos: " + totalProductos);
			System.out.println();
		});

	}
	
	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		var listFabs = fabRepo.findAll();

		var result = listFabs.stream()
				.filter(fabricante -> fabricante.getProductos().size() >= 2)
				.map(Fabricante::getNombre)
				.toList();


		result.forEach(System.out::println);

		Assertions.assertEquals(4, result.size());
	}
	
	/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. 
	 * Ordenado de mayor a menor número de productos.
	 */
	@Test
	void test42() {
		var listFabs = fabRepo.findAll();

		var result = listFabs.stream()
				.collect(Collectors.toMap(
						Fabricante::getNombre,
						fabricante -> fabricante.getProductos().stream()
								.filter(producto -> producto.getPrecio() >= 220)
								.count()
				))
				.entrySet().stream()
				.filter(entry -> entry.getValue() > 0)
				.sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
				.toList();

		result.forEach(System.out::println);

		Assertions.assertEquals(3, result.size());
	}
	/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		var listFabs = fabRepo.findAll();

		var result = listFabs.stream()
				.filter(fabricante -> fabricante.getProductos().stream()
						.mapToDouble(Producto::getPrecio)
						.sum() > 1000)
				.map(Fabricante::getNombre)
				.toList();

		result.forEach(System.out::println);

		Assertions.assertEquals(1, result.size());
	}
	
	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		var listFabs = fabRepo.findAll();

		var result = listFabs.stream()
				.map(fabricante -> new AbstractMap.SimpleEntry<>(
						fabricante.getNombre(),
						fabricante.getProductos().stream()
								.mapToDouble(Producto::getPrecio)
								.sum()
				))
				.filter(entry -> entry.getValue() > 1000)
				.sorted(Map.Entry.comparingByValue())
				.toList();

		result.forEach(System.out::println);

		Assertions.assertEquals(1, result.size());
	}
	
	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. 
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. 
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
	@Test
	void test45() {
		var listFabs = fabRepo.findAll();


	}
	
	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
	@Test
	void test46() {
		var listFabs = fabRepo.findAll();
		//TODO
	}

}
