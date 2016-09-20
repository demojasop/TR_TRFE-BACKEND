package com.bs.tr_trfe;

public class LegacyBackend {
	
	public static String isVIPClient(String client){
		
		String isVip="0";
		if(client.equalsIgnoreCase("11111111L"))
			isVip="1";
		return isVip;
		
	}

}
