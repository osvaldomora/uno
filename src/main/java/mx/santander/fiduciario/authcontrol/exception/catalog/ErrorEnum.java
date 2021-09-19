package mx.santander.fiduciario.authcontrol.exception.catalog;


/**
 * @author David Gonzalez - (Arquetipo creado por Santander Tecnologia Mexico)
 * 
 * The Enum ErrorEnum.
 * 
 * Esta clase permite la enumeracion de diferentes mensajes de excepcion
 * utilizados en los cuerpos de respuesta HTTP arrojados por el servicio
 * 
 * Es posible agregar nuevos mensajes personalizados
 * para permitir que el servicio sea mas explicito,
 * recordando siempre que es importante evitar arrojar informacion sensible
 * 
 */
public enum ErrorEnum {

	EXC_GENERICO("EXC.000", "Error generico", "Error generico de servidor", LevelException.ERROR.toString(), ""),

	EXC_ERROR_PARAMS("EXC.001", "Parametros invalidos", "Parametros invalidos de consumo", LevelException.WARN.toString(), ""),
	
	EXC_DUPLICADO("EXC.100", "Consulta duplicado", "Consulta ya existe, no puede ser sobrescrita", LevelException.WARN.toString(), ""),

	EXC_OPER_CON_ERRORES("EXC.103", "Operacion con errores", "Operacion con errores", LevelException.ERROR.toString(), "");

	
    private final String code;
    
    private final String message;

	private final String description;

    private final String level;

    private final String moreInfo;

    
    ErrorEnum(final String code, final String message, 
    		final String description, final String level, final String moreInfo ) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.level = level;
        this.moreInfo = moreInfo;
    }


	public String getCode() {
		return code;
	}


	public String getMessage() {
		return message;
	}


	public String getDescription() {
		return description;
	}


	public String getLevel() {
		return level;
	}


	public String getMoreInfo() {
		return moreInfo;
	}



}
