package javagotchi;

import java.sql.*;
import java.util.*;

/**
 * @author Robert
 * @version 1.0
 * @since 16/05
 */
public class JuegoDAO {
	private Connection conexion;
	private final String USUARIO = "root";
	private final String PASSWORD = "root";
	private final String MAQUINA = "localhost";
	private final String BD = "javagotchi";

	private ColorAnsi color = new ColorAnsi();

	/**
	 * Constructor que inicia la conexión con la BD
	 */
	public JuegoDAO() {
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
			System.out.print(color.getAnsi("NARANJA") + "CARGANDO JUEGOS...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
			Thread.sleep(500);
			con = DriverManager.getConnection(url, USUARIO, PASSWORD);
			System.out.println(color.getAnsi("VERDE") + "¡JUEGOS CARGADOS!" + color.getAnsi("RESET"));
		} catch (SQLException e) {
			System.err.println("ERROR AL CARGAR LOS JUEGOS DE LA BD.");
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return con;
	}

	/**
	 * Devuelve un juego de la BD en forma de objeto
	 * 
	 * @param codigo
	 * @return
	 */
	public Juego read(String codigo) {
		Juego juego = null;
		String sql = "SELECT * FROM JUEGO WHERE cod_juego = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, codigo);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				String descripcion = rs.getString("descripcion");
				String tipoMascota = rs.getString("tipo_mascota");
				int nutricionDisminuida = rs.getInt("nutricion_disminuida");
				int limpiezaDisminuida = rs.getInt("limpieza_disminuida");
				int felicidadAportada = rs.getInt("felicidad_aportada");

				juego = new Juego(codigo, descripcion, tipoMascota, nutricionDisminuida, limpiezaDisminuida,
						felicidadAportada);
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar el juego: " + e.getMessage());
		}
		return juego;
	}

	/**
	 * Devuelve la lista de juegos filtrada por tipo de mascota de la BD
	 * 
	 * @param tipoMascota
	 * @return
	 */
	public ArrayList<Juego> listaJuegos(String tipoMascota) {
		ArrayList<Juego> listaJuegos = new ArrayList<>();
		Juego juego = null;
		String sql = "SELECT * FROM JUEGO WHERE tipo_mascota = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, tipoMascota);
			ResultSet rs = sentencia.executeQuery();
			while (rs.next()) {
				String codigo = rs.getString("cod_juego");
				String descripcion = rs.getString("descripcion");
				int nutricionDisminuida = rs.getInt("nutricion_disminuida");
				int limpiezaDisminuida = rs.getInt("limpieza_disminuida");
				int felicidadAportada = rs.getInt("felicidad_aportada");
				juego = new Juego(codigo, descripcion, tipoMascota, nutricionDisminuida, limpiezaDisminuida,
						felicidadAportada);
				listaJuegos.add(juego);
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar los juegos: " + e.getMessage());
		}
		return listaJuegos;
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
