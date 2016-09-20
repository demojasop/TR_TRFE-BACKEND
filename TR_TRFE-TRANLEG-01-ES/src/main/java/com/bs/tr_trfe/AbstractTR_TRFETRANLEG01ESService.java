package com.bs.tr_trfe;


import com.accenture.jasop.service.AbstractService;

public abstract class AbstractTR_TRFETRANLEG01ESService extends AbstractService {

	public AbstractTR_TRFETRANLEG01ESService(){
	}
	/**
	 * Return value for input parameter IDCLIENT
	 */
	protected String getIdclient()
	{
		return (String)getParameter("IDCLIENT");
	}		
	
	
	/**
	 * Set value for output parameter ISVIP
	 */
	protected void setIsvip(final String isvip)
	{
		addParameter("ISVIP", isvip);
	}		
	
}
