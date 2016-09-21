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
		String idCliente=this.getIdcliente();
		double amount=this.getWiretransferamount();
		
		String isVip="0";
		
		Map<String, Object> params= new HashMap<String,Object>() ;
		params.put("IDCLIENT",idCliente);
		
		invokeService("TR_TRFE","TR_TRFE-TRANLEG", "01", "ES", params);
		isVip=getContext().getParameterList().get("ISVIP").getValue().toString();
		
		double fee=0.0;
		
		fee=calculateFee(amount,isVip);
				
		setFeeamount(fee);
	}

//	private Double calculateFee(double amount, char isVip) {
//		// TODO Auto-generated method stub
//		Double f=0.0;
//		
//		if (isVip=='1'){
//			f=0.0;
//		}	
//		else{
//			if (amount>1000.00){
//				f=amount*0.005; 
//			}
//			else {
//				f=amount*0.01;
//			}
//		}
//		return f;
//	}
	 
	private double calculateFee(double amount, String isVip) {
		
		BundleContext bundleContext= FrameworkUtil.getBundle(this.getClass()).getBundleContext();
				
		RulesUtilImpl droolsUtil = new RulesUtilImpl(bundleContext, this.getClass().getResource("rules").toString());

		droolsUtil.switchOnEngine();

		Transfer transfer= new Transfer(amount,isVip,0.0);
		
		Collection<Transfer> collection = new ArrayList<Transfer>(1);
		collection.add(transfer);
		droolsUtil.fireAllRules(collection);
		
		return transfer.getFee(); 
		
	}}
