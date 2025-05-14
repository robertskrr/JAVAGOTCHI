package javagotchi;

/**
 * @author Robert
 * @version 1.0
 * @since 14/05
 */
public interface Interacciones {
	/**
	 * Interacción de alimentar a la mascota
	 * 
	 * @param comida
	 */
	void comer(Comida comida);

	/**
	 * Interacción de jugar con la mascota
	 * 
	 * @param juego
	 */
	void jugar(Juego juego);

	/**
	 * Interacción de limpiar a la mascota
	 */
	void limpiar();
}
