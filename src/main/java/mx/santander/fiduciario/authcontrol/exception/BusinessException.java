package mx.santander.fiduciario.authcontrol.exception;

import mx.santander.fiduciario.authcontrol.controller.exception.model.ModelException;
import mx.santander.fiduciario.authcontrol.exception.catalog.BusinessCatalog;

public class BusinessException extends ModelException{

	/**
	 * 
	 * @param catalog Excepcion del catalogo de BusinessCatalog
	 * @param description Descripcion detallada de la excepcion
	 */
	public BusinessException(BusinessCatalog catalog, String description) {
		super(catalog.getHtttpStatus(), catalog.getCode(), catalog.getMessage(), catalog.getLevelException().toString(), description);
	}
	
	/**
	 * 
	 * @param catalog Descripcion detallada de la excepcion
	 */
	public BusinessException(BusinessCatalog catalog) {
		super(catalog.getHtttpStatus(), catalog.getCode(), catalog.getMessage(), catalog.getLevelException().toString());
	}

	private static final long serialVersionUID = 1L;

}
