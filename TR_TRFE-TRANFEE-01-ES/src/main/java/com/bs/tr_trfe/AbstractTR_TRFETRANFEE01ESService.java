package com.bs.tr_trfe;


import com.accenture.jasop.service.AbstractService;

public abstract class AbstractTR_TRFETRANFEE01ESService extends AbstractService {

	public AbstractTR_TRFETRANFEE01ESService(){
	}
	/**
	 * Return value for input parameter IDCLIENTE
	 */
	protected String getIdcliente()
	{
		return (String)getParameter("IDCLIENTE");
	}		
	/**
	 * Return value for input parameter WIRETRANSFERAMOUNT
	 */
	protected Double getWiretransferamount()
	{
		return (Double)getParameter("WIRETRANSFERAMOUNT");
	}		
	
	
	/**
	 * Set value for output parameter FEEAMOUNT
	 */
	protected void setFeeamount(final Double feeamount)
	{
		addParameter("FEEAMOUNT", feeamount);
	}		
	
}
