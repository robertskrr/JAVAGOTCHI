package javagotchi;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author Robert
 * @version 1.0
 * @since 14/05
 */
public abstract class Mascota implements MostrarInformacion, Interacciones, Comparable<Mascota> {
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
	protected String[] frames = new String[2];

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
		setNutricion(nutricion);
		setLimpieza(limpieza);
		setFelicidad(felicidad);
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
	 * Carga los frames en el array
	 */
	protected abstract void cargarFrames();

	/**
	 * Muestra el movimiento de la mascota
	 */
	public void mostrarMovimiento() {
		try {
			Thread.sleep(600); // Pequeña pausa antes de mostrar el movimiento
			for (int i = 0; i < 5; i++) {
				limpiarConsola();
				System.out.println(frames[0]);
				Thread.sleep(450); // espera 450 milisegundos

				limpiarConsola();
				System.out.println(frames[1]);
				Thread.sleep(450); // espera 450 milisegundos
				limpiarConsola();

				// Resetea el color del texto al final del bucle
				if (i == 4) {
					System.out.println(colorAnsi.getAnsi("RESET"));
				}
			}
		} catch (InterruptedException e) {
			System.err.println("ERROR AL MOSTRAR MOVIMIENTO DE MASCOTA.");
		}
	}

	/**
	 * Recoge el ASCII de cada tipo de mascota con su color
	 */
	private void getAscii() {
		System.out.println(frames[0] + colorAnsi.getAnsi("RESET"));
	}

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

	@Override
	public void mostrarInfo() {
		System.out.println("⭐ ID: " + this.id + " ⭐ NOMBRE: " + this.nombre + " ⭐ TIPO: " + this.tipo + " ⭐ COLOR: "
				+ this.color + " ⭐ SEXO: " + this.sexo + " ⭐ FECHA CREACIÓN: " + this.fechaCreacion);
	}

	/**
	 * Muestra su información de forma más detallada, para mostrar su ASCII y
	 * estadísticas
	 */
	public void mostrarInfoDetallado() {
		System.out.println("\n" + colorAnsi.getAnsi("NARANJA") + "⭐⭐⭐ MASCOTA DE '" + this.usernameDuenio.toUpperCase()
				+ "' ⭐⭐⭐" + colorAnsi.getAnsi("RESET"));
		System.out.println("⭐ NOMBRE: " + this.nombre);
		System.out.println("⭐ TIPO: " + this.tipo);
		System.out.println("⭐ " + this.sexo);
		System.out.println("⭐ EDAD: " + this.edad() + " AÑOS");
		System.out.println("⭐ NUTRICIÓN: " + mostrarEstadistica(this.nutricion));
		System.out.println("⭐ LIMPIEZA: " + mostrarEstadistica(this.limpieza));
		System.out.println("⭐ FELICIDAD: " + mostrarEstadistica(this.felicidad) + "\n");
		getAscii();
	}

	/**
	 * Chequea el decremento de los niveles de felicidad con respecto a las otras
	 * estadísticas
	 */
	private void checkFelicidad() {
		// Si la nutrición es 5 o menor decrementa felicidad
		if (this.nutricion < 5) {
			setFelicidad(this.felicidad - 1); // Revisa que no sea nunca negativo el valor final
		}
		// Si la limpieza es 5 o menor decrementa felicidad
		if (this.limpieza < 5) {
			setFelicidad(this.felicidad - 1);
		}
	}

	/**
	 * Muestra las alertas de estadísticas bajas y revisa la felicidad también
	 */
	public void checkEstadisticas() {
		if (this.nutricion == ESTADISTICA_MIN) {
			System.err.println("¡" + this.nombre + " tiene mucha hambre! ¡Aliméntala pronto!");
		}

		if (this.limpieza == ESTADISTICA_MIN) {
			System.err.println("¡" + this.nombre + " está muy sucia! ¡Báñala cuánto antes!");
		}

		checkFelicidad();
		if (this.felicidad == ESTADISTICA_MIN) {
			System.err.println("¡" + this.nombre + " está muy triste! ¡Revisa qué es lo que necesita!");
		}
	}

	@Override
	public void comer(Comida comida) {
		if (this.nutricion == ESTADISTICA_MAX) {
			System.out.println(colorAnsi.getAnsi("VERDE") + "¡" + this.nombre
					+ " no quiere comer más! ¡Ha comido lo suficiente!" + colorAnsi.getAnsi("RESET"));
			return;
		}
		// Depende del hambre que tenga aumenta la felicidad a parte de la que aporta la
		// comida
		int aumentoFelicidad = comida.getFelicidadAportada();
		int newNutricion = this.nutricion + comida.getNutricionAportada();

		// Si tiene mucha hambre se suma un bonus
		if (this.nutricion < 4) {
			aumentoFelicidad += 2;
		} else if (this.nutricion < 8) {
			aumentoFelicidad += 1;
		}

		setNutricion(newNutricion);
		setFelicidad(this.felicidad + aumentoFelicidad);
	}

	@Override
	public void jugar(Juego juego) {
		// Si tiene mucha hambre no querrá jugar
		if (this.nutricion == 0) {
			System.err.println("¡" + this.nombre + " tiene mucha hambre! No tiene ganas de jugar.");
			return;
		}
		// Actualiza las estadísticas con lo que aporta y resta cada juego
		int newNutricion = this.nutricion - juego.getNutricionDisminuida();
		setNutricion(newNutricion);

		int newLimpieza = this.limpieza - juego.getLimpiezaDisminuida();
		setLimpieza(newLimpieza);

		int newFelicidad = this.felicidad + juego.getFelicidadAportada();
		setFelicidad(newFelicidad);
	}

	@Override
	public void limpiar() {
		if (this.limpieza == ESTADISTICA_MAX) {
			System.out.println(colorAnsi.getAnsi("VERDE") + "¡" + this.nombre
					+ " está muy limpia! ¡No es necesario bañarse ahora mismo!" + colorAnsi.getAnsi("RESET"));
			return;
		}
		// Depende del nivel de limpieza que tenía aumenta más o menos la felicidad
		if (this.limpieza < 4) {
			setFelicidad(this.felicidad + 4);
		} else if (this.limpieza < 8) {
			setFelicidad(this.felicidad + 3);
		} else {
			setFelicidad(this.felicidad + 2);
		}
		setLimpieza(ESTADISTICA_MAX);
	}

	/**
	 * Imprimimos varias líneas en blanco para simular la limpieza
	 */
	private void limpiarConsola() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

	@Override
	public int compareTo(Mascota m) {
		return this.nombre.compareTo(m.getNombre());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mascota other = (Mascota) obj;
		return id == other.id;
	}

}
