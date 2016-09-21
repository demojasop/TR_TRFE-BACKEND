package com.bs.tr_trfe;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RulesUtilImpl {

	/**
	 * The Logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RulesUtilImpl.class);

	private KieBase kieBase;

	/**
	 * The bundle Context of the library
	 */
	private BundleContext bundleContext;

	/**
	 * The Drools rules files path
	 */
	private String rulePath;

	/*
	 * The droolsUtilImpl
	 */
	public RulesUtilImpl(final BundleContext bundleContext,
			final String rulePath) {
		this.bundleContext = bundleContext;
		this.rulePath = rulePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbva.elara.utility.rules.RulesUtil#switchOnEngine()
	 */
	public void switchOnEngine() {
		LOGGER.info("{};{}.switchOnEngine()", bundleContext.getBundle()
				.getSymbolicName(), this.getClass().getName());
		LOGGER.trace("Starting the drools engine session");
		this.kieBase = createKieBase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bbva.elara.utility.rules.RulesUtil#fireAllRules(java.util.Collection)
	 */

	public Collection<? extends Object> fireAllRules(
			final Collection<? extends Object> collection) {
		LOGGER.info("{};{}.fireAllRules()", bundleContext.getBundle()
				.getSymbolicName(), this.getClass().getName());
		final KieSession kieSession = this.kieBase.newKieSession();
		for (Object object : collection) {
			LOGGER.trace("The object {} has been inserted in"
					+ " the drools engine", object.toString());
			kieSession.insert(object);
		}
		kieSession.fireAllRules();
		final Collection<? extends Object> returnedCollection = kieSession
				.getObjects();
		kieSession.dispose();
		return returnedCollection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bbva.elara.utility.rules.RulesUtil#fireAllRules(java.util.Collection,
	 * int)
	 */

	public Collection<? extends Object> fireAllRules(
			final Collection<? extends Object> collection,
			final int numberOfRules) {
		LOGGER.info("{};{}.fireAllRules()", bundleContext.getBundle()
				.getSymbolicName(), this.getClass().getName());
		final KieSession kieSession = this.kieBase.newKieSession();
		for (Object object : collection) {
			LOGGER.trace("The object {} has been inserted in"
					+ " the drools engine", object.toString());
			kieSession.insert(object);
		}
		kieSession.fireAllRules(numberOfRules);
		final Collection<? extends Object> returnedCollection = kieSession
				.getObjects();
		kieSession.dispose();
		return returnedCollection;
	}

	/*
	 * Create KieBase necessary to launch the drools engine session
	 */
	private KieBase createKieBase() {
		String[] uuaa = bundleContext.getBundle().getSymbolicName().split("-");
		final String groupId = uuaa[0].toLowerCase();
		final String artifactId = uuaa[1].toLowerCase();
		final String version = bundleContext.getBundle().getVersion()
				.toString();
		ReleaseId releaseId = KieServices.Factory.get().newReleaseId(groupId,
				artifactId, version);
		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();
		List<URL> xlsList = getXLSFromBundle();
		for (URL xlsPath : xlsList) {
			Resource rs = ks.getResources().newUrlResource(xlsPath);
			// Generates a basic maven pom file with the given ReleaseId
			// (groupId, artifactId and version) and adds it to this
			// KieFileSystem
			kfs.generateAndWritePomXML(releaseId);
			// Adds the given Resource to this KieFileSystem
			kfs.write(rs);
		}
		ks.newKieBuilder(kfs).buildAll();
		return ks.newKieContainer(releaseId).getKieBase();
	}

	/**
	 * Search inside the bundle for the specific resources
	 * 
	 * @return List with the urls
	 */
	private List<URL> getXLSFromBundle() {
		final Bundle bundle = this.bundleContext.getBundle();
		Enumeration<URL> urlEnumeration = bundle.findEntries(this.rulePath,
				"*.xls", true);
		return Collections.list(urlEnumeration);
	}

}
