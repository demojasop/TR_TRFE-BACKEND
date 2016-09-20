package com.bs.tr_trfe;

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
		setIsvip(LegacyBackend.isVIPClient(getIdclient()));
	}

}
