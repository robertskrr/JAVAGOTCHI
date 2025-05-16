package javagotchi;

import java.sql.*;
import java.util.*;

/**
 * @author Robert
 * @version 1.0
 * @since 16/05
 */
public class MascotaDAO {
	private Connection conexion;
	private final String USUARIO = "root";
	private final String PASSWORD = "root";
	private final String MAQUINA = "localhost";
	private final String BD = "javagotchi";

	private ColorAnsi color = new ColorAnsi();

	/**
	 * Constructor que inicia la conexión con la BD
	 */
	public MascotaDAO() {
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
			System.out.print(color.getAnsi("NARANJA") + "CARGANDO MASCOTAS...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
			Thread.sleep(500);
			con = DriverManager.getConnection(url, USUARIO, PASSWORD);
			System.out.println(color.getAnsi("VERDE") + "¡MASCOTAS CARGADAS!" + color.getAnsi("RESET"));
		} catch (SQLException e) {
			System.err.println("ERROR AL CARGAR LAS MASCOTAS DE LA BD.");
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return con;
	}

	/**
	 * Crea una mascota en la BD
	 * 
	 * @param mascota
	 */
	public void create(Mascota mascota) {
		if (mascota == null) {
			System.err.println("ERROR. La mascota no puede ser null.");
			return;
		}
		String sql = "INSERT INTO MASCOTA(nombre, username_duenio, tipo_mascota, color, sexo) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, mascota.getNombre());
			sentencia.setString(2, mascota.getUsernameDuenio());
			sentencia.setString(3, mascota.getTipo());
			sentencia.setString(4, String.valueOf(mascota.getColor()));
			sentencia.setString(5, String.valueOf(mascota.getSexo()));
			System.out.print(color.getAnsi("NARANJA") + "CREANDO MASCOTA '" + mascota.getNombre() + "'...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
			Thread.sleep(500);
			sentencia.executeUpdate();
			System.out.println(color.getAnsi("VERDE") + "¡MASCOTA CREADA CON ÉXITO!" + color.getAnsi("RESET"));
		} catch (SQLException e) {
			System.err.println("Error al crear la mascota: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * Devuelve una mascota de la BD en forma de objeto
	 * 
	 * @param id
	 * @param usernameDuenio
	 * @return
	 */
	public Mascota read(int id, String usernameDuenio) {
		Mascota mascota = null;
		String sql = "SELECT * FROM MASCOTA WHERE id_mascota = ? AND username_duenio = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			sentencia.setString(2, usernameDuenio);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String tipoMascota = rs.getString("tipo_mascota");
				String color = rs.getString("color");
				String sexo = rs.getString("sexo");
				String fechaCreacion = rs.getString("fecha_creacion");
				int nutricion = rs.getInt("nutricion");
				int limpieza = rs.getInt("limpieza");
				int felicidad = rs.getInt("felicidad");
				// Depende del tipo crea un tipo u otro (Para generar su ASCII)
				switch (tipoMascota.toUpperCase()) {
				case "PERRO":
					mascota = new Perro(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza,
							felicidad);
					break;
				case "GATO":
					mascota = new Gato(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza,
							felicidad);
					break;
				case "PAJARO":
					mascota = new Pajaro(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza,
							felicidad);
					break;
				default:
					System.err.println("ERROR AL CONSULTAR MASCOTA");
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar la mascota: " + e.getMessage());
		}
		return mascota;
	}

	/**
	 * Actualiza las estadisticas de la mascota en la BD
	 * 
	 * @param mascota
	 */
	public void updateStats(Mascota mascota) {
		if (mascota == null) {
			System.err.println("ERROR. La mascota no puede ser null.");
			return;
		}
		String sql = "UPDATE MASCOTA SET nutricion = ?, limpieza = ?, felicidad = ? WHERE id_mascota = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, mascota.getNutricion());
			sentencia.setInt(2, mascota.getLimpieza());
			sentencia.setInt(3, mascota.getFelicidad());
			sentencia.setInt(4, mascota.getId());
			System.out.print(color.getAnsi("NARANJA") + "CARGANDO ESTADÍSTICAS DE '" + mascota.getNombre() + "'...");
			Thread.sleep(500);
			System.out.print(".....\n" + color.getAnsi("RESET"));
			sentencia.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al actualizar las estadísticas: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * Borra la mascota de la BD
	 * 
	 * @param id
	 */
	public void delete(int id) {
		String sql = "DELETE FROM MASCOTA WHERE id_mascota = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			System.out.print(color.getAnsi("NARANJA") + "ELIMINANDO MASCOTA...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
			Thread.sleep(500);
			sentencia.executeUpdate();
			System.out.println(color.getAnsi("VERDE") + "¡MASCOTA ELIMINADA CON ÉXITO!" + color.getAnsi("RESET"));
		} catch (SQLException e) {
			System.err.println("Error al eliminar la mascota: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * Devuelve la lista de mascotas de cada usuario
	 * 
	 * @param usernameDuenio
	 * @return
	 */
	public ArrayList<Mascota> listaMascotas(String usernameDuenio) {
		ArrayList<Mascota> listaMascotas = new ArrayList<>();
		Mascota mascota = null;
		String sql = "SELECT * FROM MASCOTA WHERE username_duenio = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, usernameDuenio);
			ResultSet rs = sentencia.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id_mascota");
				String nombre = rs.getString("nombre");
				String tipoMascota = rs.getString("tipo_mascota");
				String color = rs.getString("color");
				String sexo = rs.getString("sexo");
				String fechaCreacion = rs.getString("fecha_creacion");
				int nutricion = rs.getInt("nutricion");
				int limpieza = rs.getInt("limpieza");
				int felicidad = rs.getInt("felicidad");
				// Depende del tipo crea un tipo u otro (Para generar su ASCII)
				switch (tipoMascota.toUpperCase()) {
				case "PERRO":
					mascota = new Perro(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza,
							felicidad);
					break;
				case "GATO":
					mascota = new Gato(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza,
							felicidad);
					break;
				case "PAJARO":
					mascota = new Pajaro(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza,
							felicidad);
					break;
				default:
					System.err.println("ERROR AL CONSULTAR MASCOTA");
				}
				listaMascotas.add(mascota);
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar las mascotas: " + e.getMessage());
		}
		return listaMascotas;
	}

	/**
	 * Busca las mascotas por el nombre en concreto, devuelve una lista por si hay
	 * mascotas con el mismo nombre
	 * 
	 * @param nombre
	 * @param usernameDuenio
	 * @return
	 */
	public ArrayList<Mascota> buscarMascotas(String nombre, String usernameDuenio) {
		ArrayList<Mascota> listaMascotas = new ArrayList<>();
		Mascota mascota = null;
		String sql = "SELECT * FROM MASCOTA WHERE nombre = ? AND username_duenio = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, nombre);
			sentencia.setString(2, usernameDuenio);
			ResultSet rs = sentencia.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id_mascota");
				String tipoMascota = rs.getString("tipo_mascota");
				String color = rs.getString("color");
				String sexo = rs.getString("sexo");
				String fechaCreacion = rs.getString("fecha_creacion");
				int nutricion = rs.getInt("nutricion");
				int limpieza = rs.getInt("limpieza");
				int felicidad = rs.getInt("felicidad");
				// Depende del tipo crea un tipo u otro (Para generar su ASCII)
				switch (tipoMascota.toUpperCase()) {
				case "PERRO":
					mascota = new Perro(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza,
							felicidad);
					break;
				case "GATO":
					mascota = new Gato(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza,
							felicidad);
					break;
				case "PAJARO":
					mascota = new Pajaro(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza,
							felicidad);
					break;
				default:
					System.err.println("ERROR AL CONSULTAR MASCOTA");
				}
				listaMascotas.add(mascota);
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar las mascotas: " + e.getMessage());
		}
		return listaMascotas;
	}

	/**
	 * Guarda el registro de la alimentación de la mascota en la BD
	 * 
	 * @param mascota
	 * @param comida
	 */
	public void registrarAlimentacion(Mascota mascota, Comida comida) {
		String sql = "INSERT INTO REGISTRO_ALIMENTACION(cod_comida, id_mascota) VALUES (?, ?)";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, comida.getCodigo());
			sentencia.setInt(2, mascota.getId());
			System.out.print(color.getAnsi("NARANJA") + "GUARDANDO DATOS..");
			Thread.sleep(500);
			System.out.print("....... \n" + color.getAnsi("RESET"));
			sentencia.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al insertar registro de alimentación: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * Guarda el registro de los juegos de la mascota en la BD
	 * 
	 * @param mascota
	 * @param juego
	 */
	public void registrarJuego(Mascota mascota, Juego juego) {
		String sql = "INSERT INTO REGISTRO_JUEGO(cod_juego, id_mascota) VALUES (?, ?)";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, juego.getCodigo());
			sentencia.setInt(2, mascota.getId());
			System.out.print(color.getAnsi("NARANJA") + "GUARDANDO DATOS..");
			Thread.sleep(500);
			System.out.print("....... \n" + color.getAnsi("RESET"));
			sentencia.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al insertar registro de juego: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * Guarda el registro de la limpieza de la mascota en la BD
	 * 
	 * @param mascota
	 */
	public void registrarLimpieza(Mascota mascota) {
		String sql = "INSERT INTO REGISTRO_LIMPIEZA(id_mascota, felicidad_aportada) VALUES (?, ?)";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, mascota.getId());
			sentencia.setInt(2, mascota.felicidadAportadaLimpieza());
			System.out.print(color.getAnsi("NARANJA") + "GUARDANDO DATOS..");
			Thread.sleep(500);
			System.out.print("....... \n" + color.getAnsi("RESET"));
			sentencia.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al insertar registro de limpieza: " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
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
