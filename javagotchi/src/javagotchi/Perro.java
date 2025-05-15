package javagotchi;

/**
 * @author Robert
 * @version 1.0
 * @since 15/05
 */
public class Perro extends Mascota {
	
	private static final String ASCII_1 = 
			  "─────▄█▄█─────────────\r\n"
			+ "────█████▄▄▄──────────\r\n"
			+ "──────███████▄────────\r\n"
			+ "──────█▀█▀█████───────\r\n"
			+ "─────▄█▄█─▄████▄▄▀────";
	
	private static final String ASCII_2 = 
			          "─────▄█▄█─────────────\r\n"
					+ "────█████▄▄▄──────────\r\n"
					+ "──────███████▄────────\r\n"
					+ "──────█▀█▀█████───────\r\n"
					+ "─────▄█▄█─▄████▀▄▄────";

	/**
	 * Constructor completo para recoger datos de la BD, asigna 
	 * tipo PERRO automáticamente
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
	public Perro(int id, String nombre, String usernameDuenio, String color, String sexo, String fechaCreacion,
			int nutricion, int limpieza, int felicidad) {
		super(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza, felicidad);
		this.tipo = "PERRO";
		this.frames = new String[2];
		cargarFrames();
	}

	/**
	 * Constructor para crear a la mascota desde la APP, el resto de atributos se
	 * crean con valores automáticos en la BD
	 * @param nombre
	 * @param usernameDuenio
	 * @param sexo
	 */
	public Perro(String nombre, String usernameDuenio, String sexo) {
		super(nombre, usernameDuenio, sexo);
		this.tipo = "PERRO";
		this.frames = new String[2];
		cargarFrames();
	}
	
	@Override
	protected void cargarFrames() {
		frames[0] = colorAnsi.getAnsi(String.valueOf(this.color)) + ASCII_1;
		frames[1] = colorAnsi.getAnsi(String.valueOf(this.color)) + ASCII_2;
	}
	
}
