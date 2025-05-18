package javagotchi;

import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * @author Robert
 * @version 1.0
 * @since 16/05
 */
public class AppJavagotchi {

	private static UsuarioDAO bdUsuarios = new UsuarioDAO();

	private static MascotaDAO bdMascotas = new MascotaDAO();

	private static ComidaDAO bdComidas = new ComidaDAO();

	private static JuegoDAO bdJuegos = new JuegoDAO();

	private static Scanner sc = new Scanner(System.in);

	private static ColorAnsi color = new ColorAnsi();

	/**
	 * Método main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int opcion = 0;

		do {
			try {
				mostrarMenuPpal();

				System.out.print(color.getAnsi("AMARILLO") + "⭐ OPCIÓN: " + color.getAnsi("RESET"));
				opcion = sc.nextInt();
				sc.nextLine(); // Limpiar buffer

				switch (opcion) {
				case 1:
					crearUsuario();
					break;
				case 2:
					listaUsuarios();
					break;
				case 3:
					buscarUsuario();
					break;
				case 4:
					modificarUsuario();
					break;
				case 5:
					eliminarUsuario();
					break;
				case 6:
					accesoMenuMascotas();
					break;
				case 7:
					finalizarPrograma();
					break;
				default:
					System.err.println("☹ERROR☹. Opción no válida.");
				}
			} catch (InputMismatchException e) {
				System.err.println("☹ERROR☹. Introduce un número por favor.");
				sc.nextLine(); // Limpiar buffer
			}
		} while (opcion != 7);

	}

	/**
	 * Muestra el menú principal
	 */
	public static void mostrarMenuPpal() {
		System.out.println("\n" + color.getAnsi("NARANJA")
				+ "     ██╗ █████╗ ██╗   ██╗ █████╗  ██████╗  ██████╗ ████████╗ ██████╗██╗  ██╗██╗\r\n"
				+ "     ██║██╔══██╗██║   ██║██╔══██╗██╔════╝ ██╔═══██╗╚══██╔══╝██╔════╝██║  ██║██║\r\n"
				+ "     ██║███████║██║   ██║███████║██║  ███╗██║   ██║   ██║   ██║     ███████║██║\r\n"
				+ "██   ██║██╔══██║╚██╗ ██╔╝██╔══██║██║   ██║██║   ██║   ██║   ██║     ██╔══██║██║\r\n"
				+ "╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║╚██████╔╝╚██████╔╝   ██║   ╚██████╗██║  ██║██║\r\n"
				+ " ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝ ╚═════╝  ╚═════╝    ╚═╝    ╚═════╝╚═╝  ╚═╝╚═╝"
				+ color.getAnsi("RESET"));
		System.out.println(color.getAnsi("AMARILLO") + "⭐⭐ ELIGE UNA OPCIÓN ⭐⭐" + color.getAnsi("RESET"));
		System.out.println("1. 👤 Crear usuario 👤");
		System.out.println("2. 👥 Listar usuarios 👥");
		System.out.println("3. 🔎 Buscar usuario 🔎");
		System.out.println("4. ⚙ Modificar tu usuario ⚙");
		System.out.println("5. ☹ Eliminar tu usuario ☹");
		System.out.println("6. 🐶 Menú de mascotas 🐶");
		System.out.println("7. 🚫 SALIR 🚫");
	}

	/**
	 * Crea un usuario y lo añade a la BD
	 */
	public static void crearUsuario() {
		try {
			System.out.println(color.getAnsi("NARANJA") + "\n👤 CREACIÓN DE USUARIO 👤" + color.getAnsi("RESET"));
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Username: " + color.getAnsi("RESET"));
			String username = sc.nextLine();
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Contraseña: " + color.getAnsi("RESET"));
			String contrasenia = sc.nextLine();
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Nombre completo: " + color.getAnsi("RESET"));
			String nombreCompleto = sc.nextLine();
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Email: " + color.getAnsi("RESET"));
			String email = sc.nextLine();
			System.out
					.print(color.getAnsi("AMARILLO") + "⭐ Fecha de nacimiento (yyyy-MM-dd): " + color.getAnsi("RESET"));
			String fechaNac = sc.nextLine();
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Ciudad: " + color.getAnsi("RESET"));
			String ciudad = sc.nextLine();

			Usuario usuarioNew = new Usuario(username, contrasenia, nombreCompleto, email, fechaNac, ciudad);
			// Si coincide con algún username de la BD
			if (bdUsuarios.listaUsuarios().contains(usuarioNew)) {
				System.err.println("☹ERROR☹. Ya existe otro usuario con el mismo username.");
				return;
			}
			// Lo añade a la BD
			bdUsuarios.create(usuarioNew);
		} catch (DateTimeParseException e) {
			System.err.println("☹ERROR☹. Fecha no válida --> (yyyy-MM-dd)");
			return;
		}
	}

	/**
	 * Muestra los usuarios en el orden asignado
	 */
	public static void listaUsuarios() {
		try {
			System.out.println(color.getAnsi("NARANJA") + "\n👥 USUARIOS DE JAVAGOTCHI 👥" + color.getAnsi("RESET"));
			System.out.println("\t1. Ordenados por username.");
			System.out.println("\t2. Ordenados por fecha de registro.");
			System.out.print(color.getAnsi("AMARILLO") + "⭐ ELIGE EL ORDEN: " + color.getAnsi("RESET"));
			int opcionOrden = sc.nextInt();
			sc.nextLine();
			switch (opcionOrden) {
			case 1:
				System.out.println(
						color.getAnsi("NARANJA") + "👥 USUARIOS ORDENADOS POR USERNAME 👥" + color.getAnsi("RESET"));
				listaUsuariosOrdenUsername(bdUsuarios.listaUsuarios()).forEach(Usuario::mostrarInfoLista);
				break;
			case 2:
				System.out.println(color.getAnsi("NARANJA") + "👥 USUARIOS ORDENADOS POR FECHA DE REGISTRO 👥"
						+ color.getAnsi("RESET"));
				listaUsuariosOrdenFechaRegistro(bdUsuarios.listaUsuarios()).forEach(Usuario::mostrarInfoLista);
				break;
			default:
				System.err.println("☹ERROR☹. Opción de orden no válida.");
			}
		} catch (InputMismatchException e) {
			System.err.println("☹ERROR☹. Introduce un número por favor.");
			sc.nextLine(); // Limpia buffer
			return;
		}
	}

	/**
	 * Devuelve la lista de usuarios ordenadas por username
	 * 
	 * @param listaOriginal
	 * @return
	 */
	public static List<Usuario> listaUsuariosOrdenUsername(ArrayList<Usuario> listaOriginal) {
		return listaOriginal.stream()
				.sorted()
				.toList();
	}

	/**
	 * Devuelve la lista de usuarios ordenadas por fecha de registro
	 * 
	 * @param listaOriginal
	 * @return
	 */
	public static List<Usuario> listaUsuariosOrdenFechaRegistro(ArrayList<Usuario> listaOriginal) {
		return listaOriginal.stream()
				.sorted(Comparator.comparing(Usuario::getFechaRegistro))
				.toList();
	}

	/**
	 * Chequea si el usuario introducido existe en la BD
	 * 
	 * @param username
	 * @return
	 */
	public static boolean existeUsuario(String username) {
		if (bdUsuarios.read(username) == null) {
			System.err.println("☹ No hay ninguna coincidencia con el username '" + username + "' ☹");
			return false;
		}
		return true;
	}

	/**
	 * Busca a un usuario en la BD
	 */
	public static void buscarUsuario() {
		System.out.println(color.getAnsi("NARANJA") + "\n🔎 BÚSQUEDA DE USUARIO 🔎" + color.getAnsi("RESET"));
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce el username a buscar: " + color.getAnsi("RESET"));
		String usernameBusca = sc.nextLine();
		Usuario usuarioBusca = bdUsuarios.read(usernameBusca);
		if (!existeUsuario(usernameBusca)) {
			return;
		}
		System.out.println(color.getAnsi("VERDE") + "⭐ !USUARIO ENCONTRADO! ⭐" + color.getAnsi("RESET"));
		usuarioBusca.mostrarInfo();
	}

	/**
	 * Autenticación de usuario
	 * 
	 * @param username
	 * @param contrasenia
	 * @return
	 */
	public static boolean contraseniaCorrecta(Usuario usuario, String contrasenia) {
		if (!usuario.getContrasenia().equals(contrasenia)) {
			System.err.println("☹ ¡CONTRASEÑA INCORRECTA! ☹");
			return false;
		}
		return true;
	}

	/**
	 * Modifica los datos del usuario
	 */
	public static void modificarUsuario() {
		System.out.println(color.getAnsi("NARANJA") + "\n⚙ MODIFICACIÓN DE USUARIO ⚙" + color.getAnsi("RESET"));
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce el username del usuario a modificar: "
				+ color.getAnsi("RESET"));
		String usernameOriginal = sc.nextLine();
		if (!existeUsuario(usernameOriginal)) {
			return;
		}
		Usuario usuarioOriginal = bdUsuarios.read(usernameOriginal);
		// Pide autenticación del usuario
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce la contraseña actual para poder modificar: "
				+ color.getAnsi("RESET"));
		String contrasenia = sc.nextLine();
		if (!contraseniaCorrecta(usuarioOriginal, contrasenia)) {
			return;
		}
		try {
			System.out.println(color.getAnsi("VERDE") + "✅ USUARIO AUTENTICADO ✅" + color.getAnsi("RESET"));
			System.out.println(color.getAnsi("AMARILLO")
					+ "⭐ INTRODUCE LOS DATOS A MODIFICAR (O INTRO SI NO DESEA MODIFICAR EL CAMPO) ⭐"
					+ color.getAnsi("RESET"));
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Nuevo username: " + color.getAnsi("RESET"));
			String usernameNew = sc.nextLine();
			// Si el campo está vacio recupera los datos del usuario original
			if (usernameNew.isEmpty()) {
				usernameNew = usuarioOriginal.getUsername();
			}
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Nueva contraseña: " + color.getAnsi("RESET"));
			String contraseniaNew = sc.nextLine();
			if (contraseniaNew.isEmpty()) {
				contraseniaNew = usuarioOriginal.getContrasenia();
			}
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Nuevo nombre completo: " + color.getAnsi("RESET"));
			String nombreCompletoNew = sc.nextLine();
			if (nombreCompletoNew.isEmpty()) {
				nombreCompletoNew = usuarioOriginal.getNombreCompleto();
			}
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Nuevo email: " + color.getAnsi("RESET"));
			String emailNew = sc.nextLine();
			if (emailNew.isEmpty()) {
				emailNew = usuarioOriginal.getEmail();
			}
			System.out.print(
					color.getAnsi("AMARILLO") + "⭐ Nueva fecha de nacimiento (yyyy-MM-dd): " + color.getAnsi("RESET"));
			String fechaNacNew = sc.nextLine();
			if (fechaNacNew.isEmpty()) {
				fechaNacNew = String.valueOf(usuarioOriginal.getFechaNac());
			}
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Nueva ciudad: " + color.getAnsi("RESET"));
			String ciudadNew = sc.nextLine();
			if (ciudadNew.isEmpty()) {
				ciudadNew = usuarioOriginal.getCiudad();
			}

			Usuario usuarioUpdate = new Usuario(usernameNew, contraseniaNew, nombreCompletoNew, emailNew, fechaNacNew,
					ciudadNew);

			if (bdUsuarios.listaUsuarios().contains(usuarioUpdate)) {
				System.err.println("☹ERROR☹. Ya existe otro usuario con el mismo username.");
				return;
			}
			bdUsuarios.update(usuarioUpdate, usernameOriginal);

		} catch (DateTimeParseException e) {
			System.err.println("☹ERROR☹. Fecha no válida --> (yyyy-MM-dd)");
			return;
		}

	}

	/**
	 * Elimina al usuario y sus mascotas de la BD
	 */
	public static void eliminarUsuario() {
		System.out.println(color.getAnsi("NARANJA") + "\n☹ ELIMINACIÓN DE USUARIO ☹" + color.getAnsi("RESET"));
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce tu username: " + color.getAnsi("RESET"));
		String usernameDelete = sc.nextLine();
		if (!existeUsuario(usernameDelete)) {
			return;
		}
		Usuario usuarioDelete = bdUsuarios.read(usernameDelete);
		// Pide autenticación del usuario
		System.out.print(
				color.getAnsi("AMARILLO") + "⭐ Introduce tu contraseña para poder eliminar: " + color.getAnsi("RESET"));
		String contrasenia = sc.nextLine();
		if (!contraseniaCorrecta(usuarioDelete, contrasenia)) {
			return;
		}
		System.out.println(color.getAnsi("CIAN")
				+ "¿ESTÁS SEGURO DE ELIMINAR TU USUARIO? ESTO IMPLICA TAMBIÉN BORRAR A TUS MASCOTAS.");
		System.out.print(color.getAnsi("AMARILLO")
				+ "⭐ Introduce la contraseña nuevamente para confirmar la eliminación: " + color.getAnsi("RESET"));
		contrasenia = sc.nextLine();
		if (!contraseniaCorrecta(usuarioDelete, contrasenia)) {
			System.err.println("🙂 ¡ELIMINACIÓN CANCELADA! 🙂");
			return;
		}
		bdUsuarios.delete(usernameDelete);
	}

	/**
	 * Accede al menú pidiendo username y contraseña
	 */
	public static void accesoMenuMascotas() {
		System.out.println(color.getAnsi("NARANJA") + "\n🐶 ACCESO A MENÚ DE MASCOTAS 🐶" + color.getAnsi("RESET"));
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce tu username: " + color.getAnsi("RESET"));
		String username = sc.nextLine();
		if (!existeUsuario(username)) {
			return;
		}
		Usuario usuario = bdUsuarios.read(username);
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce tu contraseña: " + color.getAnsi("RESET"));
		String contrasenia = sc.nextLine();
		if (!contraseniaCorrecta(usuario, contrasenia)) {
			return;
		}
		int opcion = 0;
		do {
			try {
				mostrarMenuMascotas(usuario);
				System.out.print(color.getAnsi("AMARILLO") + "⭐ OPCIÓN: " + color.getAnsi("RESET"));
				opcion = sc.nextInt();
				sc.nextLine(); // Limpiar buffer
				switch (opcion) {
				case 1:
					accesoMenuInteraccionMascotas(usuario);
					break;
				case 2:
					listarMascotas(usuario);
					break;
				case 3:
					buscarMascota(usuario);
					break;
				case 4:
					crearMascota(usuario);
					break;
				case 5:
					eliminarMascota(usuario);
					break;
				case 6:
					System.out.println(
							color.getAnsi("NARANJA") + "⭐ Volviendo al menú principal..." + color.getAnsi("RESET"));
					break;
				default:
					System.err.println("☹ERROR☹. Opción no válida.");
				}
			} catch (InputMismatchException e) {
				System.err.println("☹ERROR☹. Introduce un número por favor.");
				sc.nextLine(); // Limpiar buffer
			}

		} while (opcion != 6);

	}

	/**
	 * Muestra el menú del usuario con respecto a sus mascotas
	 */
	public static void mostrarMenuMascotas(Usuario usuario) {
		System.out.println(color.getAnsi("CIAN") + "\n  __  __    _    ____   ____ ___ _____  _    ____  \r\n"
				+ " |  \\/  |  / \\  / ___| / ___/ _ \\_   _|/ \\  / ___| \r\n"
				+ " | |\\/| | / _ \\ \\___ \\| |  | | | || | / _ \\ \\___ \\ \r\n"
				+ " | |  | |/ ___ \\ ___) | |__| |_| || |/ ___ \\ ___) |\r\n"
				+ " |_|  |_/_/   \\_\\____/ \\____\\___/ |_/_/   \\_\\____/ \r"
				+ "                                                   " + color.getAnsi("RESET"));
		System.out.println(color.getAnsi("CIAN") + "👤 Sesión iniciada de '" + usuario.getUsername().toUpperCase()
				+ "' 👤" + color.getAnsi("RESET"));
		System.out.println(color.getAnsi("AMARILLO") + "⭐⭐ ELIGE UNA OPCIÓN ⭐⭐" + color.getAnsi("RESET"));
		System.out.println("1. 🐱 JUGAR 🐱");
		System.out.println("2. 🐥 Listar mascotas 🐥");
		System.out.println("3. 🔎 Buscar mascota 🔎");
		System.out.println("4. 🐶 Crear mascota 🐶");
		System.out.println("5. ☹ Eliminar mascota ☹");
		System.out.println("6. 🚫 Volver al menú principal 🚫");
	}

	/**
	 * Controla el acceso al menú de interacción con las mascotas del usuario
	 * 
	 * @param usuario
	 */
	public static void accesoMenuInteraccionMascotas(Usuario usuario) {
		if (bdMascotas.listaMascotas(usuario.getUsername()).isEmpty()) {
			System.err.println("☹AÚN NO HAY MASCOTAS REGISTRADAS PARA PODER JUGAR☹");
			return;
		}
		System.out.println(color.getAnsi("NARANJA") + "\n🐱 JUGAR 🐱" + color.getAnsi("RESET"));
		System.out.println(color.getAnsi("AMARILLO") + "⭐ Mostrando a tus mascotas..." + color.getAnsi("RESET"));
		// Muestra a tus mascotas en orden de creación para seleccionar el ID
		// correspondiente
		listaMascotasOrdenFechaCreacion(bdMascotas.listaMascotas(usuario.getUsername())).forEach(Mascota::mostrarInfo);
		System.out.print(color.getAnsi("AMARILLO")
				+ "⭐ ID de la mascota con la que quieres jugar (o -1 para volver atrás): " + color.getAnsi("RESET"));
		int idJugar;
		try {
			idJugar = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.err.println("☹ERROR☹. Introduce un número por favor.");
			sc.nextLine(); // Limpia buffer
			return;
		}

		if (idJugar == -1) {
			System.out
					.println(color.getAnsi("NARANJA") + "⭐ Volviendo al menú de mascotas..." + color.getAnsi("RESET"));
			return;
		}
		Mascota mascotaJugar = bdMascotas.read(idJugar, usuario.getUsername());
		if (mascotaJugar == null) {
			System.err.println("☹NO HAY NINGUNA MASCOTA DEL USUARIO '" + usuario.getUsername().toUpperCase()
					+ "' REGISTRADA CON EL ID '" + idJugar + "'☹");
			return;
		}
		// MENÚ DE INTERACCIÓN
		int opcion = 0;
		do {
			try {
				// Siempre recoge a la mascota actualizada de la BD
				mascotaJugar = bdMascotas.read(idJugar, usuario.getUsername());
				mostrarMenuInteraccionMascotas(mascotaJugar);
				mascotaJugar.alertasEstadisticas();
				System.out.print(color.getAnsi("AMARILLO") + "⭐ OPCIÓN: " + color.getAnsi("RESET"));
				opcion = sc.nextInt();
				sc.nextLine(); // Limpiar buffer
				switch (opcion) {
				case 1:
					mascotaJugar.mostrarMovimiento();
					break;
				case 2:
					alimentarMascota(mascotaJugar);
					break;
				case 3:
					jugarConMascota(mascotaJugar);
					break;
				case 4:
					limpiarMascota(mascotaJugar);
					break;
				case 5:
					System.out.println(
							color.getAnsi("NARANJA") + "⭐ Volviendo al menú de mascotas..." + color.getAnsi("RESET"));
					break;
				default:
					System.err.println("☹ERROR☹. Opción no válida.");
				}

			} catch (InputMismatchException e) {
				System.err.println("☹ERROR☹. Introduce un número por favor.");
				sc.nextLine(); // Limpia buffer
			}
		} while (opcion != 5);
	}

	/**
	 * Muestra el menú de interacción de cada mascota
	 */
	public static void mostrarMenuInteraccionMascotas(Mascota mascota) {
		mascota.mostrarInfoDetallado();
		System.out.println(color.getAnsi("AMARILLO") + "\n⭐⭐ ELIGE UNA OPCIÓN ⭐⭐" + color.getAnsi("RESET"));
		System.out.println("1. 👀 Observar mascota 👀");
		System.out.println("2. 🥦 Alimentar 🥦");
		System.out.println("3. 🥎 Jugar 🥎");
		System.out.println("4. 🧽 Bañar 🧽");
		System.out.println("5. 🚫 Volver al menú de mascotas 🚫");
	}

	/**
	 * Muestra las mascotas en el orden asignado
	 */
	public static void listarMascotas(Usuario usuario) {
		if (bdMascotas.listaMascotas(usuario.getUsername()).isEmpty()) {
			System.err.println("☹AÚN NO HAY MASCOTAS REGISTRADAS PARA PODER LISTAR☹");
			return;
		}
		try {
			System.out.println(color.getAnsi("NARANJA") + "\n🐥 MASCOTAS DE " + usuario.getUsername().toUpperCase()
					+ " 🐥" + color.getAnsi("RESET"));
			System.out.println("\t1. Ordenadas por nombre.");
			System.out.println("\t2. Ordenadas por tipo.");
			System.out.println("\t3. Ordenadas por fecha de creación.");
			System.out.print(color.getAnsi("AMARILLO") + "⭐ ELIGE EL ORDEN: " + color.getAnsi("RESET"));
			int opcionOrden = sc.nextInt();
			sc.nextLine();
			switch (opcionOrden) {
			case 1:
				System.out.println(
						color.getAnsi("NARANJA") + "🐥 MASCOTAS ORDENADAS POR NOMBRE 🐥" + color.getAnsi("RESET"));
				listaMascotasOrdenNombre(bdMascotas.listaMascotas(usuario.getUsername()))
				.forEach(Mascota::mostrarInfo);
				break;
			case 2:
				System.out.println(
						color.getAnsi("NARANJA") + "🐥 MASCOTAS ORDENADAS POR TIPO 🐥" + color.getAnsi("RESET"));
				listaMascotasOrdenTipo(bdMascotas.listaMascotas(usuario.getUsername()))
				.forEach(Mascota::mostrarInfo);
				break;
			case 3:
				System.out.println(color.getAnsi("NARANJA") + "🐥 MASCOTAS ORDENADAS POR FECHA DE CREACIÓN 🐥"
						+ color.getAnsi("RESET"));
				listaMascotasOrdenFechaCreacion(bdMascotas.listaMascotas(usuario.getUsername()))
						.forEach(Mascota::mostrarInfo);
				break;
			default:
				System.err.println("☹ERROR☹. Opción de orden no válida.");
			}
		} catch (InputMismatchException e) {
			System.err.println("☹ERROR☹. Introduce un número por favor.");
			sc.nextLine(); // Limpia buffer
			return;
		}
	}

	/**
	 * Devuelve la lista de mascotas ordenadas por nombre
	 * 
	 * @param listaOriginal
	 * @return
	 */
	public static List<Mascota> listaMascotasOrdenNombre(ArrayList<Mascota> listaOriginal) {
		return listaOriginal.stream()
				.sorted()
				.toList();
	}

	/**
	 * Devuelve la lista de usuarios ordenadas por tipo, cuando sean iguales ordena
	 * por nombre
	 * 
	 * @param listaOriginal
	 * @return
	 */
	public static List<Mascota> listaMascotasOrdenTipo(ArrayList<Mascota> listaOriginal) {
		return listaOriginal.stream()
				.sorted(Comparator.comparing(Mascota::getTipo)
				.thenComparing(Comparator.naturalOrder()))
				.toList();
	}

	/**
	 * Devuelve la lista de usuarios ordenadas por fecha de creacion, cuando sean
	 * iguales ordena por ID
	 * 
	 * @param listaOriginal
	 * @return
	 */
	public static List<Mascota> listaMascotasOrdenFechaCreacion(ArrayList<Mascota> listaOriginal) {
		return listaOriginal.stream()
				.sorted(Comparator.comparing(Mascota::getFechaCreacion)
				.thenComparing(Mascota::getId))
				.toList();
	}

	/**
	 * Busca a la mascota del usuario
	 */
	public static void buscarMascota(Usuario usuario) {
		if (bdMascotas.listaMascotas(usuario.getUsername()).isEmpty()) {
			System.err.println("☹AÚN NO HAY MASCOTAS REGISTRADAS PARA PODER BUSCAR☹");
			return;
		}
		System.out.println(color.getAnsi("NARANJA") + "\n🔎 BÚSQUEDA DE MASCOTA 🔎" + color.getAnsi("RESET"));
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce el nombre a buscar: " + color.getAnsi("RESET"));
		String nombreBusca = sc.nextLine();
		// Devuelve una lista por si hay más de una mascota del usuario con el mismo
		// nombre
		ArrayList<Mascota> mascotasCoincidencia = bdMascotas.buscarMascotas(nombreBusca, usuario.getUsername());
		if (mascotasCoincidencia.isEmpty()) {
			System.err.println("☹NO HAY NINGUNA COINCIDENCIA CON EL NOMBRE DE LA MASCOTA '" + nombreBusca + "'☹");
			return;
		}
		System.out.println(color.getAnsi("VERDE") + "⭐ ¡MASCOTA ENCONTRADA! ⭐" + color.getAnsi("RESET"));
		mascotasCoincidencia.stream().forEach(Mascota::mostrarInfo);
	}

	/**
	 * Creación de mascota del usuario
	 */
	public static void crearMascota(Usuario usuario) {
		try {
			System.out.println(color.getAnsi("NARANJA") + "\n🐶 CREACIÓN DE MASCOTA 🐶" + color.getAnsi("RESET"));
			Mascota mascota;
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Nombre de la mascota: " + color.getAnsi("RESET"));
			String nombre = sc.nextLine();
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Tipo de mascota (1. Perro, 2. Gato, 3. Pájaro): "
					+ color.getAnsi("RESET"));
			int opcionTipo = sc.nextInt();
			sc.nextLine(); // Limpia buffer
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Sexo de la mascota (1. Macho, 2. Hembra): "
					+ color.getAnsi("RESET"));
			int opcionSexo = sc.nextInt();
			sc.nextLine(); // Limpia buffer
			String sexo;
			switch (opcionSexo) {
			case 1:
				sexo = "MACHO";
				break;
			case 2:
				sexo = "HEMBRA";
				break;
			default:
				System.err.println("☹ERROR☹. Opción de sexo no válida.");
				return;
			}
			// Creamos la mascota por el tipo elegido
			switch (opcionTipo) {
			case 1:
				mascota = new Perro(nombre, usuario.getUsername(), sexo);
				break;
			case 2:
				mascota = new Gato(nombre, usuario.getUsername(), sexo);
				break;
			case 3:
				mascota = new Pajaro(nombre, usuario.getUsername(), sexo);
				break;
			default:
				System.err.println("☹ERROR☹. Opción de tipo no válida.");
				return;
			}
			// Inserta la mascota en la BD
			bdMascotas.create(mascota);
		} catch (InputMismatchException e) {
			System.err.println("☹ERROR☹. Introduce un número por favor.");
			sc.nextLine(); // Limpia buffer
			return;
		}
	}

	/**
	 * Elimina a la mascota del usuario de la BD
	 * 
	 * @param usuario
	 */
	public static void eliminarMascota(Usuario usuario) {
		if (bdMascotas.listaMascotas(usuario.getUsername()).isEmpty()) {
			System.err.println("☹AÚN NO HAY MASCOTAS REGISTRADAS PARA PODER ELIMINAR☹");
			return;
		}
		try {
			System.out.println(color.getAnsi("NARANJA") + "\n☹ ELIMINACIÓN DE MASCOTA ☹" + color.getAnsi("RESET"));
			System.out.println(color.getAnsi("AMARILLO") + "⭐ Mostrando a tus mascotas..." + color.getAnsi("RESET"));
			// Muestra a tus mascotas en orden de creación para seleccionar el ID
			// correspondiente
			listaMascotasOrdenFechaCreacion(bdMascotas.listaMascotas(usuario.getUsername()))
					.forEach(Mascota::mostrarInfo);
			System.out.print(color.getAnsi("AMARILLO") + "⭐ ID de la mascota a eliminar (o -1 para volver atrás): "
					+ color.getAnsi("RESET"));
			int idEliminar = sc.nextInt();
			sc.nextLine();
			if (idEliminar == -1) {
				System.err.println("🙂 ¡ELIMINACIÓN CANCELADA! 🙂");
				return;
			}
			Mascota mascotaDelete = bdMascotas.read(idEliminar, usuario.getUsername());
			if (mascotaDelete == null) {
				System.err.println("☹NO HAY NINGUNA MASCOTA DEL USUARIO '" + usuario.getUsername().toUpperCase()
						+ "' REGISTRADA CON EL ID '" + idEliminar + "'☹");
				return;
			}
			System.out.println(color.getAnsi("CIAN") + "⬇️⬇️¿ESTÁS SEGURO DE ELIMINAR A TU MASCOTA?⬇️⬇️");
			mascotaDelete.mostrarInfo();
			System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce tu contraseña para confirmar la eliminación: "
					+ color.getAnsi("RESET"));
			String contraseniaUsuario = sc.nextLine();
			if (!contraseniaCorrecta(usuario, contraseniaUsuario)) {
				System.err.println("🙂 ¡ELIMINACIÓN CANCELADA! 🙂");
				return;
			}
			bdMascotas.delete(idEliminar);
		} catch (InputMismatchException e) {
			System.err.println("☹ERROR☹. Introduce un número por favor.");
			sc.nextLine(); // Limpia buffer
			return;
		}
	}

	/**
	 * Alimenta a la mascota en función de su tipo
	 * 
	 * @param mascota
	 */
	public static void alimentarMascota(Mascota mascota) {
		System.out.println(color.getAnsi("CIAN") + "\n🥦 ALIMENTACIÓN DE " + mascota.getNombre().toUpperCase() + " 🥦"
				+ color.getAnsi("RESET"));
		System.out.println(color.getAnsi("AMARILLO") + "⬇️⬇️ COMIDAS DISPONIBLES ⬇️⬇️" + color.getAnsi("RESET"));
		bdComidas.listaComidas(mascota.getTipo()).forEach(Comida::mostrarInfo);
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce el código del alimento que quieres dar: "
				+ color.getAnsi("RESET"));
		String codigoComida = sc.nextLine();
		Comida comida = bdComidas.read(codigoComida);
		if (comida == null) {
			System.err.println("☹NO HAY NINGUNA COINCIDENCIA CON EL CÓDIGO '" + codigoComida + "'☹");
			return;
		}
		// Comprueba en el caso de existir que sea para el tipo de mascota
		if (!comida.getTipoMascota().equalsIgnoreCase(mascota.getTipo())) {
			System.err.println("☹EL CÓDIGO INTRODUCIDO NO CORRESPONDE AL TIPO DE TU MASCOTA☹");
			return;
		}
		// Si la mascota está llena vuelve atrás
		if (!mascota.comer(comida)) {
			return;
		}
		// Mensaje de proceso de comer
		try {
			System.out.print(color.getAnsi(String.valueOf(mascota.getColor())) + mascota.getNombre()
					+ color.getAnsi("NARANJA") + " está comiendo " + color.getAnsi("VERDE") + comida.getDescripcion()
					+ color.getAnsi("NARANJA") + "...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
		} catch (InterruptedException e) {
			System.out.println("ERROR" + e.getMessage());
			return;
		}
		// Guarda el registro de la alimentación
		bdMascotas.registrarAlimentacion(mascota, comida);
		// Chequea la felicidad en cuanto a las otras stats
		mascota.checkFelicidad();
		// Actualiza las stats de la mascota
		bdMascotas.updateStats(mascota);
	}

	/**
	 * Juega con la mascota en función de su tipo
	 * 
	 * @param mascota
	 */
	public static void jugarConMascota(Mascota mascota) {
		System.out.println(color.getAnsi("CIAN") + "\n🥎 JUEGO CON " + mascota.getNombre().toUpperCase() + " 🥎"
				+ color.getAnsi("RESET"));
		System.out.println(color.getAnsi("AMARILLO") + "⬇️⬇️ JUEGOS DISPONIBLES ⬇️⬇️" + color.getAnsi("RESET"));
		bdJuegos.listaJuegos(mascota.getTipo()).forEach(Juego::mostrarInfo);
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce el código del juego que quieres jugar: "
				+ color.getAnsi("RESET"));
		String codigoJuego = sc.nextLine();
		Juego juego = bdJuegos.read(codigoJuego);
		if (juego == null) {
			System.err.println("☹NO HAY NINGUNA COINCIDENCIA CON EL CÓDIGO '" + codigoJuego + "'☹");
			return;
		}
		// Comprueba en el caso de existir que sea para el tipo de mascota
		if (!juego.getTipoMascota().equalsIgnoreCase(mascota.getTipo())) {
			System.err.println("☹EL CÓDIGO INTRODUCIDO NO CORRESPONDE AL TIPO DE TU MASCOTA☹");
			return;
		}
		// Si la mascota no quiere jugar vuelve atrás
		if (!mascota.jugar(juego)) {
			return;
		}
		// Mensaje de proceso de jugar
		try {
			System.out.print(color.getAnsi(String.valueOf(mascota.getColor())) + mascota.getNombre()
					+ color.getAnsi("NARANJA") + " está jugando " + color.getAnsi("VERDE") + juego.getDescripcion()
					+ color.getAnsi("NARANJA") + "...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
		} catch (InterruptedException e) {
			System.out.println("ERROR" + e.getMessage());
			return;
		}
		// Guarda el registro del juego
		bdMascotas.registrarJuego(mascota, juego);
		// Chequea la felicidad en cuanto a las otras stats
		mascota.checkFelicidad();
		// Actualiza las stats de la mascota
		bdMascotas.updateStats(mascota);
	}

	/**
	 * Limpia a la mascota
	 * 
	 * @param mascota
	 */
	public static void limpiarMascota(Mascota mascota) {
		System.out.println(color.getAnsi("CIAN") + "\n🧽 LIMPIEZA DE " + mascota.getNombre().toUpperCase() + " 🧽"
				+ color.getAnsi("RESET"));
		// Guardamos la felicidaadAportada antes de que cambie al limpiar, para
		// almacenarlo en la BD
		int felicidadAportada = mascota.felicidadAportadaLimpieza();
		// Si la mascota está limpia vuelve atrás
		if (!mascota.limpiar()) {
			return;
		}
		// Mensaje de proceso de limpiar
		try {
			System.out.print(color.getAnsi(String.valueOf(mascota.getColor())) + mascota.getNombre()
					+ color.getAnsi("NARANJA") + " se está dando un baño...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
		} catch (InterruptedException e) {
			System.out.println("ERROR" + e.getMessage());
			return;
		}
		// Guarda el registro de la limpieza
		bdMascotas.registrarLimpieza(mascota, felicidadAportada);
		// Chequea la felicidad en cuanto a las otras stats
		mascota.checkFelicidad();
		// Actualiza las stats de la mascota
		bdMascotas.updateStats(mascota);
	}

	/**
	 * Finaliza el programa
	 */
	public static void finalizarPrograma() {
		try {
			System.out.println(color.getAnsi("VERDE") + "\n🐥 ¡GRACIAS POR JUGAR! 🐥");
			Thread.sleep(500);
			// Cerramos todas las conexiones
			bdUsuarios.cerrarConexion();
			bdMascotas.cerrarConexion();
			bdComidas.cerrarConexion();
			bdJuegos.cerrarConexion();
			System.out.print(color.getAnsi("NARANJA") + "CERRANDO JAVAGOTCHI...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
		} catch (InterruptedException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

}
