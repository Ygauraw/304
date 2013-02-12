package cards.threenotfour.network;

import java.net.UnknownHostException;

public class Test {

	// private static final int WAIT_TIME = 120;

	public static void main(String[] args) throws UnknownHostException {

		try {
			MatchRequester matchRequester = new MatchRequester();
			matchRequester.getGame();
		} catch (Exception e) {
			System.err.println("Unable to get a game!");
			System.err.println(e.getMessage());
		}

	}

	/*
	 * public static void upnpTest() throws SocketException, UnknownHostException,
	 * IOException, SAXException, ParserConfigurationException,
	 * InterruptedException {
	 * 
	 * int a = 1; if (a == 1) return; Logger logger = LogUtils.getLogger();
	 * logger.info("Starting weupnp");
	 * 
	 * GatewayDiscover discover = new GatewayDiscover();
	 * logger.info("Looking for Gateway Devices"); discover.discover();
	 * GatewayDevice d = discover.getValidGateway();
	 * 
	 * if (null != d) { logger.log(Level.INFO, "Gateway device found.\n{0} ({1})",
	 * new Object[] { d.getModelName(), d.getModelDescription() }); } else {
	 * logger.info("No valid gateway device found."); return; }
	 * 
	 * InetAddress localAddress = d.getLocalAddress(); logger.log(Level.INFO,
	 * "Using local address: {0}", localAddress); String externalIPAddress =
	 * d.getExternalIPAddress(); logger.log(Level.INFO, "External address: {0}",
	 * externalIPAddress); PortMappingEntry portMapping = new PortMappingEntry();
	 * 
	 * logger.log(Level.INFO, "Attempting to map port {0}", SERVER_PORT);
	 * logger.log(Level.INFO,
	 * "Querying device to see if mapping for port {0} already exists",
	 * SERVER_PORT);
	 * 
	 * if (!d.getSpecificPortMappingEntry(SERVER_PORT, "TCP", portMapping)) {
	 * logger.info("Sending port mapping request");
	 * 
	 * if (d.addPortMapping(SERVER_PORT, SERVER_PORT,
	 * localAddress.getHostAddress(), "TCP", "test")) { logger.log(Level.INFO,
	 * "Mapping succesful: waiting {0} seconds before removing mapping.",
	 * WAIT_TIME);
	 * 
	 * Thread.sleep(1000 * WAIT_TIME); d.deletePortMapping(SERVER_PORT, "TCP");
	 * 
	 * logger.info("Port mapping removed"); logger.info("Test SUCCESSFUL"); } else
	 * { logger.info("Port mapping removal failed"); logger.info("Test FAILED"); }
	 * 
	 * } else { logger.info("Port was already mapped. Aborting test."); }
	 * 
	 * logger.info("Stopping weupnp"); }
	 */
}
