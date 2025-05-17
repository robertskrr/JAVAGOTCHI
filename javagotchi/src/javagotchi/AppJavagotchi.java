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
				default:
					break;
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
		return listaOriginal.stream().sorted().toList();
	}

	/**
	 * Devuelve la lista de usuarios ordenadas por fecha de registro
	 * 
	 * @param listaOriginal
	 * @return
	 */
	public static List<Usuario> listaUsuariosOrdenFechaRegistro(ArrayList<Usuario> listaOriginal) {
		return listaOriginal.stream().sorted(Comparator.comparing(Usuario::getFechaRegistro)).toList();
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
		if (!contrasenia.equals(usuarioOriginal.getContrasenia())) {
			System.err.println("☹ ¡CONTRASEÑA INCORRECTA! ☹");
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
		System.out.print(color.getAnsi("AMARILLO") + "⭐ Introduce tu username: "
				+ color.getAnsi("RESET"));
		String usernameDelete = sc.nextLine();
		if (!existeUsuario(usernameDelete)) {
			return;
		}
		Usuario usuarioDelete = bdUsuarios.read(usernameDelete);
		// Pide autenticación del usuario
		System.out.print(
				color.getAnsi("AMARILLO") + "⭐ Introduce tu contraseña para poder eliminar: " + color.getAnsi("RESET"));
		String contrasenia = sc.nextLine();
		if (!contrasenia.equals(usuarioDelete.getContrasenia())) {
			System.err.println("☹ ¡CONTRASEÑA INCORRECTA! ☹");
			return;
		}
		System.out.println(color.getAnsi("CIAN")
				+ "¿ESTÁS SEGURO DE ELIMINAR TU USUARIO? ESTO IMPLICA TAMBIÉN BORRAR A TUS MASCOTAS.");
		System.out.print(color.getAnsi("AMARILLO")
				+ "⭐ Introduce la contraseña nuevamente para confirmar la eliminación: " + color.getAnsi("RESET"));
		contrasenia = sc.nextLine();
		if (!contrasenia.equals(usuarioDelete.getContrasenia())) {
			System.err.println("🙂 ¡ELIMINACIÓN CANCELADA! 🙂");
			return;
		}
		bdUsuarios.delete(usernameDelete);
	}

}
