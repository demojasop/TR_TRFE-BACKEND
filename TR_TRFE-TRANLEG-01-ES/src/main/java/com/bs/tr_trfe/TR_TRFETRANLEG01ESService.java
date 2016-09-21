package com.bs.tr_trfe;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Legacy Backend Data Client
 * Business Logic implementation.
 * @author alejandro.blasco
 *
 */
public class TR_TRFETRANLEG01ESService extends AbstractTR_TRFETRANLEG01ESService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TR_TRFETRANLEG01ESService.class);

	@Override
	public void execute() {
		LOGGER.debug("Legacy Backend Data Client");
		String client=getIdclient();
		
		String isVip="0";
		
		isVip=isVIPClient(client);
		
		
		setIsvip(isVip);
	}

	private String isVIPClient(String idclient) {
		
		String isVip="0";
		
		String vipUsers=getProperty("vipUsers");
		
		List<String> list = Arrays.asList(vipUsers.split(","));
		if (list.contains(idclient))
				isVip="1";
		
		
		return isVip;
		
	}
	
	

}
