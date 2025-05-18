package javagotchi;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Robert
 * @version 1.0
 * @since 13/05
 */
public class Usuario implements MostrarInformacion, Comparable<Usuario> {

	// Para verificar tamaño máx con respecto a la BD
	private final int TAM_USERNAME = 30;
	private final int TAM_CONTRASENIA = 50;
	private final int TAM_NOMBRE = 120;
	private final int TAM_EMAIL = 100;
	private final int TAM_CIUDAD = 50;

	private String username;
	private String contrasenia;
	private String nombreCompleto;
	private String email;
	private LocalDate fechaNac;
	private String ciudad;
	private LocalDate fechaRegistro;

	private ColorAnsi color = new ColorAnsi();

	/**
	 * Constructor completo a falta de fechaRegistro, usado para los create de la
	 * parte CRUD, ya que la fecha se genera automáticamente en la BD
	 * 
	 * @param username
	 * @param contrasenia
	 * @param nombreCompleto
	 * @param email
	 * @param fechaNac
	 * @param ciudad
	 */
	public Usuario(String username, String contrasenia, String nombreCompleto, String email, String fechaNac,
			String ciudad) {
		setUsername(username);
		setContrasenia(contrasenia);
		setNombreCompleto(nombreCompleto);
		setEmail(email);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.fechaNac = LocalDate.parse(fechaNac, f);
		setCiudad(ciudad);
	}

	/**
	 * Constructor completo, para el método read de la parte CRUD
	 * 
	 * @param username
	 * @param contrasenia
	 * @param nombreCompleto
	 * @param email
	 * @param fechaNac
	 * @param ciudad
	 * @param fechaRegistro
	 */
	public Usuario(String username, String contrasenia, String nombreCompleto, String email, String fechaNac,
			String ciudad, String fechaRegistro) {
		setUsername(username);
		setContrasenia(contrasenia);
		setNombreCompleto(nombreCompleto);
		setEmail(email);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.fechaNac = LocalDate.parse(fechaNac, f);
		setCiudad(ciudad);
		this.fechaRegistro = LocalDate.parse(fechaRegistro, f);
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username.substring(0, Math.min(TAM_USERNAME, username.length()));
	}

	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia.substring(0, Math.min(TAM_CONTRASENIA, contrasenia.length()));
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto.substring(0, Math.min(TAM_NOMBRE, nombreCompleto.length()));
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email.substring(0, Math.min(TAM_EMAIL, email.length()));
	}

	/**
	 * @return the fechaNac
	 */
	public LocalDate getFechaNac() {
		return fechaNac;
	}

	/**
	 * @param fechaNac the fechaNac to set
	 */
	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad.substring(0, Math.min(TAM_CIUDAD, ciudad.length()));
	}

	/**
	 * @return the fechaRegistro
	 */
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(username, other.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public void mostrarInfo() {
		System.out.println(color.getAnsi("NARANJA") + "⬇️⬇️ USUARIO " + color.getAnsi("VERDE")
				+ this.username.toUpperCase() + color.getAnsi("NARANJA") + " ⬇️⬇️" + color.getAnsi("RESET"));
		System.out.println(color.getAnsi("VERDE") + "🔸 Nombre: " + color.getAnsi("RESET") + this.nombreCompleto);
		System.out.println(color.getAnsi("VERDE") + "🔸 Email: " + color.getAnsi("RESET") + this.email);
		System.out.println(color.getAnsi("VERDE") + "🔸 Fecha Nacimiento: " + color.getAnsi("RESET") + this.fechaNac);
		System.out.println(color.getAnsi("VERDE") + "🔸 Ciudad: " + color.getAnsi("RESET") + this.ciudad);
		System.out
				.println(color.getAnsi("VERDE") + "🔸 Fecha Registro: " + color.getAnsi("RESET") + this.fechaRegistro);
	}

	/**
	 * Muestra la información del usuario para cuando se elija la opción de mostrar
	 * usuarios ordenados por 'X'
	 */
	public void mostrarInfoLista() {
		System.out.println(color.getAnsi("NARANJA")
				+ "================================================================================================"
				+ color.getAnsi("RESET"));
		System.out.println(color.getAnsi("VERDE") + "🔸 USERNAME: " + color.getAnsi("RESET") + this.username
				+ color.getAnsi("VERDE") + " 🔸 NOMBRE: " + color.getAnsi("RESET") + this.nombreCompleto
				+ color.getAnsi("VERDE") + " 🔸 EMAIL: " + color.getAnsi("RESET") + this.email + color.getAnsi("VERDE")
				+ " 🔸 FECHA NACIMIENTO: " + color.getAnsi("RESET") + this.fechaNac + color.getAnsi("VERDE")
				+ " 🔸 CIUDAD: " + color.getAnsi("RESET") + this.ciudad + color.getAnsi("VERDE")
				+ " 🔸 FECHA REGISTRO: " + color.getAnsi("RESET") + this.fechaRegistro);
		System.out.println(color.getAnsi("NARANJA")
				+ "================================================================================================"
				+ color.getAnsi("RESET"));
	}

	@Override
	public int compareTo(Usuario u) {
		return this.username.compareTo(u.getUsername());
	}

}
