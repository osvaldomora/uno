package mx.santander.fiduciario.authcontrol.controller;

public class Test {

	public static void main(String[] args) {

		String pass = "1234";
		System.out.println(pass.matches("[a-zA-Z0-9]{8}"));
		if (!pass.matches("[a-zA-Z0-9]{8}")) {

			System.out.println("longitud no permitida o esta usando caracteres no alfanumericos");
			for (int i = 0; i < pass.length() - 1; i++) {
				if (pass.charAt(i) == pass.charAt(i + 1)) {
					System.out.println("contiene caracteres consecutivos:" + pass.charAt(i));
					break;
				}
			}
		} 
		
		System.out.println("contraseña correcta");

	}

}
