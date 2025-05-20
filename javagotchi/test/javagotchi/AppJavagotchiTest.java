package javagotchi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import java.util.*;

class AppJavagotchiTest {

	@Test
	void testExisteUsuario() {
		// Ejecutamos el método para un usuario existente en la BD
		boolean existe = AppJavagotchi.existeUsuario("robertskrr");
		assertTrue(existe);
		// Comprobamos que al leerlo no sea null
		Usuario usuarioExistente = AppJavagotchi.bdUsuarios.read("robertskrr");
		assertNotNull(usuarioExistente);
		// Ejecutamos el método para un usuario no existente
		boolean noExiste = AppJavagotchi.existeUsuario("noExiste");
		assertFalse(noExiste);
		// Comprobamos que al leerlo sea null
		Usuario usuarioInexistente = AppJavagotchi.bdUsuarios.read("noExiste");
		assertNull(usuarioInexistente);
	}

	@Test
	void testContraseniaCorrecta() {
		// Creamos el usuario01 desde la BD y desde la APP, y verificamos la contraseña
		// correcta
		Usuario usuario01 = AppJavagotchi.bdUsuarios.read("usuario01");
		boolean correcta = AppJavagotchi.contraseniaCorrecta(usuario01, "pass123");
		assertTrue(correcta);
		// Comprobamos que devuelva si es incorrecta
		boolean incorrecta = AppJavagotchi.contraseniaCorrecta(usuario01, "incorrecta");
		assertFalse(incorrecta);
	}

	@Test
	void testListaUsuariosOrdenFechaRegistro() {
		// Creamos la lista sin ordenar
		ArrayList<Usuario> listaSinOrdenar = AppJavagotchi.bdUsuarios.listaUsuarios();
		assertFalse(listaSinOrdenar.isEmpty());
		// El primero de la lista desordenada debería de ser el usuario 'jelenmcz'
		Usuario usuarioAntesOrdenar = listaSinOrdenar.getFirst();
		usuarioAntesOrdenar.mostrarInfoLista(); // Lo muestra en consola para verificar
		assertEquals(usuarioAntesOrdenar, listaSinOrdenar.get(0));

		// Ordenamos la lista
		List<Usuario> listaOrdenadaFechaRegistro = AppJavagotchi.listaUsuariosOrdenFechaRegistro(listaSinOrdenar);
		// El primero debería de ser el usuario 'usuario03', ya que tiene la fecha más
		// antigua
		Usuario usuarioPrimerOrdenado = listaOrdenadaFechaRegistro.getFirst();
		usuarioPrimerOrdenado.mostrarInfoLista(); // Lo muestra en consola para verificar
		// Comprobamos que el orden sea el correcto
		assertEquals(usuarioPrimerOrdenado, listaOrdenadaFechaRegistro.get(0));
		assertNotEquals(usuarioAntesOrdenar, listaOrdenadaFechaRegistro.get(0));
	}

	@Test
	void testJugarConMascota() {
		// Para los test de interacciones usaremos objetos de tipo local, sin acceder a
		// la BD de mascotas
		Mascota gatoTest = new Gato(99, "TESTGATO", "robertskrr", "ROJO", "MACHO", "2025-05-19", 5, 5, 5);
		// Comprobamos las estadísticas antes de jugar
		assertEquals(5, gatoTest.getNutricion());
		assertEquals(5, gatoTest.getLimpieza());
		assertEquals(5, gatoTest.getFelicidad());
		// Elegimos el juego
		Juego juego = AppJavagotchi.bdJuegos.read("J004");
		juego.mostrarInfo(); // -2 nutricion, -1 limpieza, +3 felicidad
		gatoTest.jugar(juego);
		// Chequea nueva felicidad por el resto de estadísticas
		gatoTest.checkFelicidad(); // -1 por nutricion < 5 y -1 por limpieza < 5
		// Comprobamos que las estadísticas han cambiado
		assertEquals(3, gatoTest.getNutricion()); // 5 - 2 (nutricion comida)
		assertEquals(4, gatoTest.getLimpieza()); // 5 - 1 (limpieza comida)
		assertEquals(6, gatoTest.getFelicidad()); // 5 + 3 (felicidad comida) - 2 (checkFelicidad)
	}

	@Test
	void testAlimentarMascota() {
		// Para los test de interacciones usaremos objetos de tipo local, sin acceder a
		// la BD de mascotas
		Mascota perroTest = new Perro(100, "TESTPERRO", "robertskrr", "AMARILLO", "HEMBRA", "2025-05-20", 5, 5, 5);
		// Comprobamos las estadísticas antes de comer
		assertEquals(5, perroTest.getNutricion());
		assertEquals(5, perroTest.getFelicidad());
		// Elegimos la comida
		Comida comida = AppJavagotchi.bdComidas.read("C001");
		comida.mostrarInfo(); // +3 nutricion, +1 felicidad
		// Como la nutrición de la mascota es menor que 8 la felicidad al comer
		// aumentará en +1
		perroTest.comer(comida);
		// Chequea nueva felicidad por el resto de estadísticas
		perroTest.checkFelicidad();
		assertEquals(8, perroTest.getNutricion()); // 5 + 3 (nutricion comida)
		assertEquals(7, perroTest.getFelicidad()); // 5 + 1 (felicidad comida) + 1 (felicidad extra por hambre)
	}

	@Test
	void limpiarMascota() {
		// Para los test de interacciones usaremos objetos de tipo local, sin acceder a
		// la BD de mascotas
		Mascota pajaroTest = new Pajaro(101, "TESTPAJARO", "robertskrr", "VERDE", "HEMBRA", "2025-05-21", 5, 0, 5);
		// Comprobamos las estadísticas antes de comer
		assertEquals(0, pajaroTest.getLimpieza());
		assertEquals(5, pajaroTest.getFelicidad());

		// Al estar a un nivel de limpieza inferior a 4 la felicidad aportada por
		// limpiarlo debería ser de 4
		int felicidadAportada = pajaroTest.felicidadAportadaLimpieza();
		assertEquals(4, felicidadAportada);
		// Limpiamos la mascota
		pajaroTest.limpiar();
		// Chequea nueva felicidad por el resto de estadísticas
		pajaroTest.checkFelicidad();
		// Revisamos las estadísticas actualizadas
		assertEquals(10, pajaroTest.getLimpieza()); // Limpieza al máximo
		assertEquals(9, pajaroTest.getFelicidad()); // 5 + 4 (felicidadAportada)

	}

	@AfterAll
	static void cerrarConexion() {
		AppJavagotchi.finalizarPrograma();
	}

}
