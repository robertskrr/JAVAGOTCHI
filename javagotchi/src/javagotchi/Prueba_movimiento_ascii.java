package javagotchi;

public class Prueba_movimiento_ascii {

	public static void main(String[] args) throws InterruptedException {
		String frame1 = "─────▄█▄█─────────────\n" + "────█████▄▄▄──────────\n" + "──────███████▄────────\n"
				+ "░░░░░░█▀█▀█████░░░░░░░\n" + "░░░░░▄█▄█░▄████▄▄▀░░░░";

		String frame2 = "─────▄█▄█─────────────\n" + "────█████▄▄▄──────────\n" + "──────███████▄────────\n"
				+ "░░░░░░█▀█▀█████░░░░░░░\n" + "░░░░░▄█▄█░▄████▀▄▄░░░░";

		while (true) {
			clearConsole();
			System.out.println(frame1);
			Thread.sleep(500); // espera 500 milisegundos

			clearConsole();
			System.out.println(frame2);
			Thread.sleep(500); // espera 500 milisegundos
		}
	}

	private static void clearConsole() {
		// Imprimimos varias líneas en blanco para simular la limpieza
		for (int i = 0; i < 20; i++) {
			System.out.println();
		}
	}

}
