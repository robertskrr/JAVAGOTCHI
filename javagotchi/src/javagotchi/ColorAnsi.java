package javagotchi;

import java.util.*;

/**
 * @author Robert
 * @version 1.0
 * @since 14/05
 */
public class ColorAnsi {
	private Map<String, String> colores;

	/**
	 * Constructor que inicializa el mapa de colores
	 * 
	 * @param colores
	 */
	public ColorAnsi() {
		this.colores = new HashMap<>();
		colores.put("ROJO", "\u001B[31m");
		colores.put("VERDE", "\u001B[32m");
		colores.put("AMARILLO", "\u001B[33m");
		colores.put("AZUL", "\u001B[34m");
		colores.put("MORADO", "\u001B[35m");
		colores.put("CIAN", "\u001B[36m");
		colores.put("RESET", "\u001B[0m");
		colores.put("NARANJA", "\u001B[38;5;208m");
	}

	/**
	 * Método que devuelve el ANSI del color establecido, si no coincide con los
	 * guardados devuelve el color por defecto de la terminal
	 * 
	 * @param colorNombre
	 * @return
	 */
	public String getAnsi(String colorNombre) {
		return colores.getOrDefault(colorNombre.toUpperCase(), "\u001B[0m");
	}

	/**
	 * Genera un color aleatorio para la mascota, evitando que sea el color RESET o
	 * el naranja (es para el título del juego)
	 * 
	 * @return
	 */
	public String getColorAleatorio() {
		List<String> claveColores = new ArrayList<>(colores.keySet());
		String colorAleatorio;

		// Repite el bucle si coincide con alguno de los colores excluidos
		do {
			int indiceRandom = (int) (Math.random() * claveColores.size());
			colorAleatorio = claveColores.get(indiceRandom);
		} while (colorAleatorio.equals("NARANJA") || colorAleatorio.equals("RESET"));

		return colorAleatorio;
	}

}
