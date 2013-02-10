package cards.threenotfour.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.wetorrent.upnp.GatewayDevice;
import org.wetorrent.upnp.GatewayDiscover;
import org.wetorrent.upnp.LogUtils;
import org.wetorrent.upnp.PortMappingEntry;
import org.xml.sax.SAXException;

import cards.threenotfour.player.NetworkPlayer;

public class Test {

	public static final int SERVER_PORT = 59422;
	private static final int WAIT_TIME = 120;

	public static void main(String[] args) throws UnknownHostException {

		NetworkMessageReceiver receiver = new NetworkMessageReceiver();
		new Thread(receiver).start();

		InetAddress server = InetAddress.getByName("192.168.1.2");
		NetworkPlayer p1 = new NetworkPlayer(server);
		p1.displayCurrentCards();

		try {
			upnpTest();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void upnpTest() throws SocketException, UnknownHostException, IOException,
			SAXException, ParserConfigurationException, InterruptedException {

		int a = 1;
		if (a == 1)
			return;
		Logger logger = LogUtils.getLogger();
		logger.info("Starting weupnp");

		GatewayDiscover discover = new GatewayDiscover();
		logger.info("Looking for Gateway Devices");
		discover.discover();
		GatewayDevice d = discover.getValidGateway();

		if (null != d) {
			logger.log(Level.INFO, "Gateway device found.\n{0} ({1})",
					new Object[] { d.getModelName(), d.getModelDescription() });
		} else {
			logger.info("No valid gateway device found.");
			return;
		}

		InetAddress localAddress = d.getLocalAddress();
		logger.log(Level.INFO, "Using local address: {0}", localAddress);
		String externalIPAddress = d.getExternalIPAddress();
		logger.log(Level.INFO, "External address: {0}", externalIPAddress);
		PortMappingEntry portMapping = new PortMappingEntry();

		logger.log(Level.INFO, "Attempting to map port {0}", SERVER_PORT);
		logger.log(Level.INFO, "Querying device to see if mapping for port {0} already exists",
				SERVER_PORT);

		if (!d.getSpecificPortMappingEntry(SERVER_PORT, "TCP", portMapping)) {
			logger.info("Sending port mapping request");

			if (d.addPortMapping(SERVER_PORT, SERVER_PORT, localAddress.getHostAddress(), "TCP", "test")) {
				logger.log(Level.INFO, "Mapping succesful: waiting {0} seconds before removing mapping.",
						WAIT_TIME);

				Thread.sleep(1000 * WAIT_TIME);
				d.deletePortMapping(SERVER_PORT, "TCP");

				logger.info("Port mapping removed");
				logger.info("Test SUCCESSFUL");
			} else {
				logger.info("Port mapping removal failed");
				logger.info("Test FAILED");
			}

		} else {
			logger.info("Port was already mapped. Aborting test.");
		}

		logger.info("Stopping weupnp");
	}
}
