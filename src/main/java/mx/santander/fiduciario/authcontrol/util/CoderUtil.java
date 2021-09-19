package mx.santander.fiduciario.authcontrol.util;

public class CoderUtil {

	private CoderUtil() {}
	
	public static String getDecoder(byte[] bytesEndcoder) {
		String str = "";
		if(bytesEndcoder.length == 0) {
			return str;
		}
		str = new String(bytesEndcoder, java.nio.charset.StandardCharsets.UTF_8);
		return str;		
	}
	
	public static byte[] getEncoder(String strToEncode) {
		byte byteEncode [] = null;
		if(strToEncode == null) {
			return byteEncode;
		}
		byteEncode = strToEncode.getBytes();
		return byteEncode;		
	}

}
