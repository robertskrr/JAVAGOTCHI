package javagotchi;

/**
 * @author Robert
 * @version 1.0
 * @since 14/05
 */
public class Juego implements MostrarInformacion, Comparable<Juego> {
	// Para verificar tamaño máx con respecto a la BD
	private final int TAM_CODIGO = 20;
	private final int TAM_DESCRIPCION = 200;
	// Para verificar errores
	private final int ESTADISTICA_DEFAULT = 0;

	private String codigo;
	private String descripcion;
	private String tipoMascota;
	private int nutricionDisminuida;
	private int limpiezaDisminuida;
	private int felicidadAportada;

	/**
	 * Constructor completo
	 * 
	 * @param codigo
	 * @param descripcion
	 * @param tipoMascota
	 * @param nutricionDisminuida
	 * @param limpiezaDisminuida
	 * @param felicidadAportada
	 */
	public Juego(String codigo, String descripcion, String tipoMascota, int nutricionDisminuida, int limpiezaDisminuida,
			int felicidadAportada) {
		setCodigo(codigo);
		setDescripcion(descripcion);
		this.tipoMascota = tipoMascota;
		setNutricionDisminuida(nutricionDisminuida);
		setLimpiezaDisminuida(limpiezaDisminuida);
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
	 * @return the nutricionDisminuida
	 */
	public int getNutricionDisminuida() {
		return nutricionDisminuida;
	}

	/**
	 * Valida también que sea un número positivo
	 * 
	 * @param nutricionDisminuida the nutricionDisminuida to set
	 */
	public void setNutricionDisminuida(int nutricionDisminuida) {
		if (nutricionDisminuida < 0) {
			this.nutricionDisminuida = ESTADISTICA_DEFAULT;
			return;
		}
		this.nutricionDisminuida = nutricionDisminuida;
	}

	/**
	 * @return the limpiezaDisminuida
	 */
	public int getLimpiezaDisminuida() {
		return limpiezaDisminuida;
	}

	/**
	 * Valida también que sea un número positivo
	 * 
	 * @param limpiezaDisminuida the limpiezaDisminuida to set
	 */
	public void setLimpiezaDisminuida(int limpiezaDisminuida) {
		if (limpiezaDisminuida < 0) {
			this.limpiezaDisminuida = ESTADISTICA_DEFAULT;
			return;
		}
		this.limpiezaDisminuida = limpiezaDisminuida;
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
	public int compareTo(Juego j) {
		return Integer.compare(this.felicidadAportada, j.getFelicidadAportada());
	}

	@Override
	public void mostrarInfo() {
		System.out.println("-----------------------------------------------------------");
		System.out.println("🥎 CÓDIGO: " + this.codigo + " 🥎 DESCRIPCIÓN: " + this.descripcion + " 🥎 NUTRICIÓN: -"
				+ this.nutricionDisminuida + " 🥎 LIMPIEZA: -" + " 🥎 FELICIDAD: +" + this.felicidadAportada);
		System.out.println("-----------------------------------------------------------");
	}

}
