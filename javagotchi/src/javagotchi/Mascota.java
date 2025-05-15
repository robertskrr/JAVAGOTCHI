package javagotchi;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author Robert
 * @version 1.0
 * @since 14/05
 */
public abstract class Mascota {
	// Para verificar tamaño máx con respecto a la BD
	protected final int TAM_NOMBRE = 30;
	// Para verificar errores o rebasamiento de datos
	protected final int ESTADISTICA_MIN = 0;
	protected final int ESTADISTICA_MAX = 10;

	/**
	 * Enum de colores de las mascotas
	 */
	public enum Color {
		ROJO, VERDE, AMARILLO, AZUL, MORADO, CIAN
	}

	/**
	 * Enum del sexo de las mascotas
	 */
	public enum Sexo {
		MACHO, HEMBRA
	}

	protected int id;
	protected String nombre;
	protected String usernameDuenio;
	protected String tipo;
	protected Color color;
	protected Sexo sexo;
	protected LocalDate fechaCreacion;
	protected int nutricion;
	protected int limpieza;
	protected int felicidad;

	protected ColorAnsi colorAnsi;

	/**
	 * Constructor para recoger los datos de la BD, el tipo se asignará en las
	 * clases hijas
	 * 
	 * @param id
	 * @param nombre
	 * @param usernameDuenio
	 * @param color
	 * @param sexo
	 * @param fechaCreacion
	 * @param nutricion
	 * @param limpieza
	 * @param felicidad
	 */
	public Mascota(int id, String nombre, String usernameDuenio, String color, String sexo, String fechaCreacion,
			int nutricion, int limpieza, int felicidad) {
		this.id = id;
		setNombre(nombre);
		this.usernameDuenio = usernameDuenio;
		this.color = Color.valueOf(color.toUpperCase());
		this.sexo = Sexo.valueOf(sexo.toUpperCase());
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.fechaCreacion = LocalDate.parse(fechaCreacion, f);
		this.nutricion = nutricion;
		this.limpieza = limpieza;
		this.felicidad = felicidad;
		// Inicializa la clase colorAnsi para usarla al ver a las mascotas con su color
		this.colorAnsi = new ColorAnsi();
	}

	/**
	 * Constructor para crear a la mascota desde la APP, el resto de atributos se
	 * crean con valores automáticos en la BD
	 * 
	 * @param nombre
	 * @param usernameDuenio
	 * @param color
	 * @param sexo
	 * @param colorAnsi
	 */
	public Mascota(String nombre, String usernameDuenio, String sexo) {
		setNombre(nombre);
		this.usernameDuenio = usernameDuenio;
		// Inicializa la clase colorAnsi para usarla al ver a las mascotas con su color
		// y generar el color aleatorio
		this.colorAnsi = new ColorAnsi();
		this.color = Color.valueOf(colorAnsi.getColorAleatorio().toUpperCase());
		this.sexo = Sexo.valueOf(sexo.toUpperCase());
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre.substring(0, Math.min(TAM_NOMBRE, nombre.length()));
	}

	/**
	 * @return the usernameDuenio
	 */
	public String getUsernameDuenio() {
		return usernameDuenio;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the sexo
	 */
	public Sexo getSexo() {
		return sexo;
	}

	/**
	 * @return the fechaCreacion
	 */
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @return the nutricion
	 */
	public int getNutricion() {
		return nutricion;
	}

	/**
	 * @param nutricion the nutricion to set
	 */
	public void setNutricion(int nutricion) {
		if (nutricion < 0) {
			this.nutricion = ESTADISTICA_MIN;
			return;
		} else if (nutricion > 10) {
			this.nutricion = ESTADISTICA_MAX;
			return;
		}
		this.nutricion = nutricion;
	}

	/**
	 * @return the limpieza
	 */
	public int getLimpieza() {
		return limpieza;
	}

	/**
	 * @param limpieza the limpieza to set
	 */
	public void setLimpieza(int limpieza) {
		if (limpieza < 0) {
			this.limpieza = ESTADISTICA_MIN;
			return;
		} else if (limpieza > 10) {
			this.limpieza = ESTADISTICA_MAX;
			return;
		}
		this.limpieza = limpieza;
	}

	/**
	 * @return the felicidad
	 */
	public int getFelicidad() {
		return felicidad;
	}

	/**
	 * @param felicidad the felicidad to set
	 */
	public void setFelicidad(int felicidad) {
		if (felicidad < 0) {
			this.felicidad = ESTADISTICA_MIN;
			return;
		} else if (felicidad > 10) {
			this.felicidad = ESTADISTICA_MAX;
			return;
		}
		this.felicidad = felicidad;
	}

	/**
	 * Devuelve la edad de la mascota (cada día desde su creación es un año)
	 * 
	 * @return
	 */
	public int edad() {
		// La clase ChronoUnit nos permite realizar este calculo exacto de días
		return (int) ChronoUnit.DAYS.between(fechaCreacion, LocalDate.now());
	}

	/**
	 * Muestra el movimiento de la mascota
	 */
	public abstract void mostrarMovimiento();

	/**
	 * Recoge el ASCII de cada tipo de mascota
	 */
	public abstract void getAscii();

	/**
	 * Devuelve la representación gráfica de cada estadística
	 * 
	 * @param stat
	 * @return
	 */
	public String mostrarEstadistica(int estadistica) {
		switch (estadistica) {
		case 0:
			return colorAnsi.getAnsi("RESET") + "🟥🟥🟥🟥🟥🟥🟥🟥🟥🟥❌";
		case 1:
			return colorAnsi.getAnsi("ROJO") + "🟥" + colorAnsi.getAnsi("RESET") + "🟥🟥🟥🟥🟥🟥🟥🟥🟥";
		case 2:
			return colorAnsi.getAnsi("ROJO") + "🟥🟥" + colorAnsi.getAnsi("RESET") + "🟥🟥🟥🟥🟥🟥🟥🟥";
		case 3:
			return colorAnsi.getAnsi("NARANJA") + "🟥🟥🟥" + colorAnsi.getAnsi("RESET") + "🟥🟥🟥🟥🟥🟥🟥";
		case 4:
			return colorAnsi.getAnsi("NARANJA") + "🟥🟥🟥🟥" + colorAnsi.getAnsi("RESET") + "🟥🟥🟥🟥🟥🟥";
		case 5:
			return colorAnsi.getAnsi("AMARILLO") + "🟥🟥🟥🟥🟥" + colorAnsi.getAnsi("RESET") + "🟥🟥🟥🟥🟥";
		case 6:
			return colorAnsi.getAnsi("AMARILLO") + "🟥🟥🟥🟥🟥🟥" + colorAnsi.getAnsi("RESET") + "🟥🟥🟥🟥";
		case 7:
			return colorAnsi.getAnsi("VERDE") + "🟥🟥🟥🟥🟥🟥🟥" + colorAnsi.getAnsi("RESET") + "🟥🟥🟥";
		case 8:
			return colorAnsi.getAnsi("VERDE") + "🟥🟥🟥🟥🟥🟥🟥🟥" + colorAnsi.getAnsi("RESET") + "🟥🟥";
		case 9:
			return colorAnsi.getAnsi("VERDE") + "🟥🟥🟥🟥🟥🟥🟥🟥🟥" + colorAnsi.getAnsi("RESET") + "🟥";
		case 10:
			return colorAnsi.getAnsi("VERDE") + "🟥🟥🟥🟥🟥🟥🟥🟥🟥🟥" + colorAnsi.getAnsi("RESET");
		default:
			return colorAnsi.getAnsi("ROJO") + "🟥🟥🟥🟥🟥🟥🟥🟥🟥🟥 ERROR" + colorAnsi.getAnsi("RESET");
		}

	}

}
