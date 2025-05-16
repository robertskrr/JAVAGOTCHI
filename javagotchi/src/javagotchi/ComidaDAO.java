package javagotchi;

import java.sql.*;
import java.util.*;

/**
 * @author Robert
 * @version 1.0
 * @since 16/05
 */
public class ComidaDAO {
	private Connection conexion;
	private final String USUARIO = "root";
	private final String PASSWORD = "root";
	private final String MAQUINA = "localhost";
	private final String BD = "javagotchi";

	private ColorAnsi color = new ColorAnsi();

	/**
	 * Constructor que inicia la conexión con la BD
	 */
	public ComidaDAO() {
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
			System.out.print(color.getAnsi("NARANJA") + "CARGANDO COMIDAS...");
			Thread.sleep(500);
			System.out.print(".....");
			Thread.sleep(500);
			System.out.print("....... " + color.getAnsi("RESET"));
			Thread.sleep(500);
			con = DriverManager.getConnection(url, USUARIO, PASSWORD);
			System.out.println(color.getAnsi("VERDE") + "¡COMIDAS CARGADAS!" + color.getAnsi("RESET"));
		} catch (SQLException e) {
			System.err.println("ERROR AL CARGAR LOS USUARIOS DE LA BD.");
		} catch (InterruptedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return con;
	}

	/**
	 * Devuelve una comida de la BD en forma de objeto
	 * 
	 * @param codigo
	 * @return
	 */
	public Comida read(String codigo) {
		Comida comida = null;
		String sql = "SELECT * FROM COMIDA WHERE cod_comida = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, codigo);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				String descripcion = rs.getString("descripcion");
				String tipoMascota = rs.getString("tipo_mascota");
				int nutricionAportada = rs.getInt("nutricion_aportada");
				int felicidadAportada = rs.getInt("felicidad_aportada");

				comida = new Comida(codigo, descripcion, tipoMascota, nutricionAportada, felicidadAportada);
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar la comida: " + e.getMessage());
		}
		return comida;
	}

	/**
	 * Devuelve la lista de comidas filtrada por tipo de mascota de la BD
	 * 
	 * @param tipoMascota
	 * @return
	 */
	public ArrayList<Comida> listaComidas(String tipoMascota) {
		ArrayList<Comida> listaComidas = new ArrayList<>();
		Comida comida = null;
		String sql = "SELECT * FROM COMIDA WHERE tipo_mascota = ?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, tipoMascota);
			ResultSet rs = sentencia.executeQuery();
			while (rs.next()) {
				String codigo = rs.getString("cod_comida");
				String descripcion = rs.getString("descripcion");
				int nutricionAportada = rs.getInt("nutricion_aportada");
				int felicidadAportada = rs.getInt("felicidad_aportada");

				comida = new Comida(codigo, descripcion, tipoMascota, nutricionAportada, felicidadAportada);
				listaComidas.add(comida);
			}
		} catch (SQLException e) {
			System.err.println("Error al consultar las comidas: " + e.getMessage());
		}
		return listaComidas;
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
