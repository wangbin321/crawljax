package com.crawljax.examples;

import org.apache.commons.configuration.ConfigurationException;

import com.crawljax.browser.WebDriverIE;
import com.crawljax.condition.UrlCondition;
import com.crawljax.core.CrawljaxController;
import com.crawljax.core.CrawljaxException;
import com.crawljax.core.configuration.CrawlSpecification;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.InputSpecification;

/**
 * Crawls google.com in IE.
 * 
 * @author danny
 * @version @version $Id$
 */
public final class SiteSimpleExample {

	private static final String URL = "http://www.google.com";

	private static final String ALL_ANCHORS = "a";
	private static final String LANGUAGE_TOOLS = "Language Tools";

	private static final String HEADER_XPATH = "//DIV[@id='guser']";

	private static final int MAX_CRAWL_DEPTH = 1;
	private static final int MAX_STATES = 10;

	private SiteSimpleExample() {
		// Utility class
	}

	private static CrawlSpecification getCrawlSpecification() {
		CrawlSpecification crawler = new CrawlSpecification(URL);

		crawler.clickDefaultElements();
		crawler.ignore(ALL_ANCHORS).underXPath(HEADER_XPATH);
		crawler.ignore(ALL_ANCHORS).withText(LANGUAGE_TOOLS);

		// limit the crawling scope
		crawler.setMaximumStates(MAX_STATES);
		crawler.setDepth(MAX_CRAWL_DEPTH);

		crawler.setInputSpecification(getInputSpecification());

		// Make sure we only crawl Google and no external web site
		crawler.addCrawlCondition("Only crawl Google", new UrlCondition("google"));

		return crawler;
	}

	private static InputSpecification getInputSpecification() {
		InputSpecification input = new InputSpecification();

		// enter "Crawljax" in the search field
		input.field("q").setValue("Crawljax");
		return input;
	}

	private static CrawljaxConfiguration getConfig() {
		CrawljaxConfiguration crawljaxConfiguration = new CrawljaxConfiguration();
		crawljaxConfiguration.setBrowser(new WebDriverIE());
		crawljaxConfiguration.setCrawlSpecification(getCrawlSpecification());

		// Generate a crawl report
		// crawljaxConfiguration.addPlugin(new CrawlOverview());
		return crawljaxConfiguration;
	}

	/**
	 * @param args
	 *            none.
	 */
	public static void main(String[] args) {
		try {
			CrawljaxController crawljax = new CrawljaxController(getConfig());
			crawljax.run();
		} catch (CrawljaxException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
