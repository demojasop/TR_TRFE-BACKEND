package com.bs.tr_trfe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accenture.jasop.domain.transaction.ContextStatusType;

/**
 * Global Transference Fee
 * Business Logic implementation.
 * @author alejandro.blasco
 *
 */
public class TR_TRFETRANFEE01ESService extends AbstractTR_TRFETRANFEE01ESService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TR_TRFETRANFEE01ESService.class);


	@Override
	public void execute() {
		LOGGER.debug("Global Transference Fee");
		
		/*
		 * Input parameters
		 */
		// Client ID
		String idCliente=this.getIdcliente();
		// Transfer amount
		double amount=this.getWiretransferamount();
		
		String isVip="0";
		
		// Get client data from legacy
		Map<String, Object> params= new HashMap<String,Object>() ;
		params.put("IDCLIENT",idCliente);
		invokeService("TR_TRFE","TR_TRFE-TRANLEG", "01", "ES", params);
		isVip=getContext().getParameterList().get("ISVIP").getValue().toString();
		
		double fee=0.0;
		
		// Get fee depending on business rule
		fee=calculateFee(amount,isVip);
				
		setFeeamount(fee);
	}

	/**
	 * Get fee depending on business rule.
	 * 
	 * @param amount
	 * @param isVip
	 * @return
	 */
	private double calculateFee(double amount, String isVip) {
		
		BundleContext bundleContext= FrameworkUtil.getBundle(this.getClass()).getBundleContext();
				
		LOGGER.debug("Valor calculateFee:"+ " Amount=" + amount+ " isVip=" + isVip);
		LOGGER.debug("this.getClass().getResource:" +this.getClass().getResource("/rules") );
		
		// Invoke Drools engine
		RulesUtilImpl droolsUtil = new RulesUtilImpl(bundleContext, "/rules");

		droolsUtil.switchOnEngine();

		Transfer transfer= new Transfer(amount,isVip,0.0);
		
		Collection<Transfer> collection = new ArrayList<Transfer>(1);
		collection.add(transfer);
		droolsUtil.fireAllRules(collection);
		
		return transfer.getFee(); 
		
	}}
