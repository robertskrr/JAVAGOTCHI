package javagotchi;

/**
 * @author Robert
 * @version 1.0
 * @since 15/05
 */
public class Pajaro extends Mascota {
	private static final String ASCII_1 = 
	           "    /\"\"\\      ,\r\n"
	          + "   <>^  L____/|\r\n"
	          + "    `) /`   , /\r\n"
	          + "     \\ `---' /\r\n"
	          + "      `'\";\\)`\r\n"
	          + "        _/_Y";

	private static final String ASCII_2 = 
	           "    /\"\"\\     ,\r\n"
	         + "   <>^  L___/|\r\n"
	         + "    `) /`  ./ \\\r\n"
	         + "     / `---'  /\r\n"
	         + "     `'\";\\)\"`\r\n"
	         + "       _/_Y";

	
	/**
	 * Constructor completo para recoger datos de la BD, asigna 
	 * tipo PAJARO automáticamente
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
	public Pajaro(int id, String nombre, String usernameDuenio, String color, String sexo, String fechaCreacion,
			int nutricion, int limpieza, int felicidad) {
		super(id, nombre, usernameDuenio, color, sexo, fechaCreacion, nutricion, limpieza, felicidad);
		this.tipo = "Pajaro";
		cargarFrames();
	}
	
	/**
	 * Constructor para crear a la mascota desde la APP, el resto de atributos se
	 * crean con valores automáticos en la BD
	 * @param nombre
	 * @param usernameDuenio
	 * @param sexo
	 */
	public Pajaro(String nombre, String usernameDuenio, String sexo) {
		super(nombre, usernameDuenio, sexo);
		this.tipo = "Pajaro";
		cargarFrames();
	}
	
	/**
	 * Carga los frames en el array
	 */
	protected void cargarFrames() {
		frames[0] = colorAnsi.getAnsi(String.valueOf(this.color)) + ASCII_1;
		frames[1] = colorAnsi.getAnsi(String.valueOf(this.color)) + ASCII_2;
	}
}
