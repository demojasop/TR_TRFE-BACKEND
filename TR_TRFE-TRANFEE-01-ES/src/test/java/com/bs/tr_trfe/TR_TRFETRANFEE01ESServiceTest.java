package com.bs.tr_trfe;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.accenture.jasop.domain.transaction.Context;
import com.accenture.jasop.domain.transaction.ParameterTable;
import com.accenture.jasop.domain.transaction.TransactionParameter;
import com.accenture.jasop.test.osgi.DummyBundleContext;


/**
 * Test for transaction TR_TRFETRANFEE01ESService
 */
//Uncomment for Transactional testing
//@Transactional(value = "txManager")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/jasop-test.xml",
		"classpath:/META-INF/spring/TR_TRFE-TRANFEE01ESTest.xml" })
public class TR_TRFETRANFEE01ESServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(TR_TRFETRANFEE01ESServiceTest.class);
	
	@Autowired
	private TR_TRFETRANFEE01ESService transaction;
	
	@Resource(name="dummyBundleContext")
	private DummyBundleContext bundleContext;
	
	
	@Before
	public void initializeClass() throws Exception {		
		this.transaction.start(bundleContext);
		
		// Initializing transaction to test
		this.transaction.setContext(new Context());
		this.transaction.getContext().setLanguageCode("ES");		
	}
	
	@Test
	public void testNotNull(){
		Assert.assertNotNull(this.transaction);
		this.transaction.execute();
	}	
	

	private void addTable(final String key, final List<Map<String, Object>> table) {	
		ParameterTable parameterTable = new ParameterTable();		
		parameterTable.setParameterTableList(table);
		this.addParameter(key, parameterTable);		
	}
	
	private void addParameter(final String parameter, final Object value) {
		final TransactionParameter tParameter = new TransactionParameter(
				parameter, value);
		transaction.getContext().getParameterList().put(parameter, tParameter);
	}

	private Object getParameter(final String parameter) {
		final TransactionParameter param = transaction.getContext()
				.getParameterList().get(parameter);
		return param.getValue();
	}
	
	private Object getTableParameter(final int rowNumber, final String table, final String key)
	{
		ParameterTable param = (ParameterTable)getParameter(table);
		Map<String,Object> row = param.get(rowNumber);
		return row.get(key);
	}
}
