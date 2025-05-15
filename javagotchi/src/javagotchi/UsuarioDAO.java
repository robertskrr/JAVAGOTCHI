package javagotchi;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * @author Robert
 * @version 1.0
 * @since 15/05
 */
public class UsuarioDAO {
	private Connection conexion;
	private final String USUARIO = "root";
	private final String PASSWORD = "root";
	private final String MAQUINA = "localhost";
	private final String BD = "javagotchi";

	private ColorAnsi color = new ColorAnsi();

	/**
	 * Constructor que inicia la conexión con la BD
	 */
	public UsuarioDAO() {
		conexion = conectar();
	}

	/**
	 * Conecta con la BD
	 * 
	 * @return
	 */
	private Connection conectar() {
		Connection con = null;
		String url = "jdbc:mysql://" + MAQUINA + "/" + BD;
		try {
			System.out.print(color.getAnsi("NARANJA") + "CARGANDO USUARIOS...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
			Thread.sleep(500);
			con = DriverManager.getConnection(url, USUARIO, PASSWORD);
			System.out.println(color.getAnsi("VERDE") + "¡USUARIOS CARGADOS!" + color.getAnsi("RESET"));
		} catch (SQLException e) {
			System.err.println("ERROR AL CARGAR LOS USUARIOS DE LA BD.");
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return con;
	}

	/**
	 * Crea un usuario en la BD
	 * 
	 * @param usuario
	 */
	public void create(Usuario usuario) {
		if (usuario == null) {
			System.err.println("ERROR. El usuario no puede ser null.");
			return;
		}
		String sql = "INSERT INTO USUARIO (username, contrasenia, nombre_completo, email, fecha_nac, ciudad) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, usuario.getUsername());
			sentencia.setString(2, usuario.getContrasenia());
			sentencia.setString(3, usuario.getNombreCompleto());
			sentencia.setString(4, usuario.getEmail());
			sentencia.setDate(5, Date.valueOf(usuario.getFechaNac()));
			sentencia.setString(6, usuario.getCiudad());
			System.out.print(color.getAnsi("NARANJA") + "CREANDO USUARIO '" + usuario.getUsername() + "'...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
			Thread.sleep(500);
			sentencia.executeUpdate();
			System.out.println(color.getAnsi("VERDE") + "¡USUARIO CREADO CON ÉXITO!" + color.getAnsi("RESET"));
		} catch (SQLException e) {
			System.err.println("Error al crear el usuario: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * Devuelve un usuario de la BD en forma de objeto
	 * 
	 * @param username
	 * @return
	 */
	public Usuario read(String username) {
		Usuario usuario = null;
		String sql = "SELECT * FROM USUARIO WHERE username = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, username);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				String contrasenia = rs.getString("contrasenia");
				String nombreCompleto = rs.getString("nombre_completo");
				String email = rs.getString("email");
				String fechaNac = rs.getString("fecha_nac");
				String ciudad = rs.getString("ciudad");
				String fechaRegistro = rs.getString("fecha_registro");

				usuario = new Usuario(username, contrasenia, nombreCompleto, email, fechaNac, ciudad, fechaRegistro);
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar el usuario: " + e.getMessage());
		}
		return usuario;
	}

	/**
	 * Actualiza al usuario en la BD
	 * 
	 * @param usuario
	 * @param oldUsername para indicar correctamente a la sentencia
	 */
	public void update(Usuario usuario, String oldUsername) {
		if (usuario == null) {
			System.err.println("ERROR. El usuario no puede ser null.");
			return;
		}
		String sql = "UPDATE USUARIO SET username = ?, contrasenia = ?, nombre_completo = ?, email = ?, fecha_nac = ?, ciudad = ? WHERE username = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, usuario.getUsername());
			sentencia.setString(2, usuario.getContrasenia());
			sentencia.setString(3, usuario.getNombreCompleto());
			sentencia.setString(4, usuario.getEmail());
			sentencia.setDate(5, Date.valueOf(usuario.getFechaNac()));
			sentencia.setString(6, usuario.getCiudad());
			sentencia.setString(7, oldUsername);
			System.out.print(color.getAnsi("NARANJA") + "MODIFICANDO USUARIO...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
			Thread.sleep(500);
			sentencia.executeUpdate();
			System.out.println(color.getAnsi("VERDE") + "¡USUARIO MODIFICADO CON ÉXITO!" + color.getAnsi("RESET"));
		} catch (SQLException e) {
			System.err.println("Error al actualizar el usuario: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * Elimina al usuario de la BD
	 * 
	 * @param username
	 */
	public void delete(String username) {
		String sql = "DELETE FROM USUARIO WHERE username = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, username);
			System.out.print(color.getAnsi("NARANJA") + "ELIMINANDO USUARIO...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
			Thread.sleep(500);
			int numDelete = sentencia.executeUpdate();
			if (numDelete == 0) {
				System.err.println("ERROR. El username' " + username + "' no existe en la BD.");
				return;
			}
			System.out.println(color.getAnsi("VERDE") + "¡USUARIO ELIMINADO CON ÉXITO!" + color.getAnsi("RESET"));
		} catch (SQLException e) {
			System.err.println("Error al eliminar el usuario: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * Devuelve la lista de todos los usuarios de la BD
	 * 
	 * @return
	 */
	public ArrayList<Usuario> listaUsuarios() {
		ArrayList<Usuario> listaUsuarios = new ArrayList<>();
		Usuario usuario = null;
		String sql = "SELECT * FROM USUARIO";
		try {
			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				String username = rs.getString("username");
				String contrasenia = rs.getString("contrasenia");
				String nombreCompleto = rs.getString("nombre_completo");
				String email = rs.getString("email");
				String fechaNac = rs.getString("fecha_nac");
				String ciudad = rs.getString("ciudad");
				String fechaRegistro = rs.getString("fecha_registro");

				usuario = new Usuario(username, contrasenia, nombreCompleto, email, fechaNac, ciudad, fechaRegistro);
				listaUsuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar todos los usuarios: " + e.getMessage());
		}
		return listaUsuarios;
	}

	/**
	 * Finaliza la conexión con la BD
	 */
	public void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			System.err.println("Error al cerrar la conexión: " + e.getMessage());
		}
	}
}
