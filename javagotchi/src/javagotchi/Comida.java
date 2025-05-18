package javagotchi;

/**
 * @author Robert
 * @version 1.0
 * @since 14/05
 */
public class Comida implements MostrarInformacion, Comparable<Comida> {
	// Para verificar tamaño máx con respecto a la BD
	private final int TAM_CODIGO = 20;
	private final int TAM_DESCRIPCION = 200;
	// Para verificar errores
	private final int ESTADISTICA_DEFAULT = 0;

	private String codigo;
	private String descripcion;
	private String tipoMascota;
	private int nutricionAportada;
	private int felicidadAportada;

	private ColorAnsi color = new ColorAnsi();

	/**
	 * Constructor completo
	 * 
	 * @param codigo
	 * @param descripcion
	 * @param tipoMascota
	 * @param nutricionAportada
	 * @param felicidadAportada
	 */
	public Comida(String codigo, String descripcion, String tipoMascota, int nutricionAportada, int felicidadAportada) {
		setCodigo(codigo);
		setDescripcion(descripcion);
		this.tipoMascota = tipoMascota;
		setNutricionAportada(nutricionAportada);
		setFelicidadAportada(felicidadAportada);
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo.substring(0, Math.min(TAM_CODIGO, codigo.length()));
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.substring(0, Math.min(TAM_DESCRIPCION, descripcion.length()));
	}

	/**
	 * @return the tipoMascota
	 */
	public String getTipoMascota() {
		return tipoMascota;
	}

	/**
	 * @param tipoMascota the tipoMascota to set
	 */
	public void setTipoMascota(String tipoMascota) {
		this.tipoMascota = tipoMascota;
	}

	/**
	 * @return the nutricionAportada
	 */
	public int getNutricionAportada() {
		return nutricionAportada;
	}

	/**
	 * Valida también que sea un número positivo
	 * 
	 * @param nutricionAportada the nutricionAportada to set
	 */
	public void setNutricionAportada(int nutricionAportada) {
		if (nutricionAportada < 0) {
			this.nutricionAportada = ESTADISTICA_DEFAULT;
			return;
		}
		this.nutricionAportada = nutricionAportada;
	}

	/**
	 * @return the felicidadAportada
	 */
	public int getFelicidadAportada() {
		return felicidadAportada;
	}

	/**
	 * Valida también que sea un número positivo
	 * 
	 * @param felicidadAportada the felicidadAportada to set
	 */
	public void setFelicidadAportada(int felicidadAportada) {
		if (felicidadAportada < 0) {
			this.felicidadAportada = ESTADISTICA_DEFAULT;
			return;
		}
		this.felicidadAportada = felicidadAportada;
	}

	@Override
	public int compareTo(Comida c) {
		return Integer.compare(this.nutricionAportada, c.getNutricionAportada());
	}

	@Override
	public void mostrarInfo() {
		System.out.println(color.getAnsi("NARANJA") + "\t-----------------------------------------------------------"
				+ color.getAnsi("RESET"));
		System.out.println(color.getAnsi("AMARILLO") + "\t🍽 CÓDIGO: " + color.getAnsi("RESET") + this.codigo
				+ color.getAnsi("AMARILLO") + " 🍽 DESCRIPCIÓN: " + color.getAnsi("RESET") + this.descripcion
				+ color.getAnsi("AMARILLO") + " 🍽 NUTRICIÓN: " + color.getAnsi("VERDE") + "+" + this.nutricionAportada
				+ color.getAnsi("AMARILLO") + " 🍽 FELICIDAD: " + color.getAnsi("VERDE") + "+"
				+ this.felicidadAportada);
		System.out.println(color.getAnsi("NARANJA") + "\t-----------------------------------------------------------"
				+ color.getAnsi("RESET"));
	}

}
