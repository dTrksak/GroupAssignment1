package edu.metrostate.ics372_androidstart_master;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHandler
{
	private static final Double kgToLb = 2.20462;

	/**
	 * Creates a list of shipments given a filepath
	 *
	 * @param str a filepath
	 * @return a list of shipments, OR null if the xml file was incorrectly
	 *         formatted
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public List<Shipment> parseXml(String str) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		File file = new File(str);
		Document doc;
		try {
			doc = docBuilder.parse(file);
		} catch(Exception e) {
			return null;
		}

		// Set up the warehouse list and variables
		String warehouseId = null;
		String warehouseName = null;
		List<Shipment> shipments = new ArrayList<>();

		// Set all the shipment variables
		String shipmentId = null;
		String shipmentMethod = null;
		String shipmentUnit = null; // Unit weight, lb or kg
		Float shipmentWeight = null;
		Long shipmentReceiptDate = null;

		boolean firstShipment = true;

		// Get a list of all elements in the document
		// The wild card * matches all tags
		NodeList list = doc.getElementsByTagName("*");
		for (int i = 0; i < list.getLength(); i++)
		{
			Element element = (Element) list.item(i);
			String nodeName = element.getNodeName();
			if (nodeName.equals("Warehouse"))
			{
				warehouseId = element.getAttribute("id");
				warehouseName = element.getAttribute("name");
			}
			else if (nodeName.equals("Shipment"))
			{
				/*
				 * // Check if the file was correctly formatted if (warehouseId != null &&
				 * warehouseName != null && shipmentId != null && shipmentMethod != null &&
				 * shipmentUnit != null && shipmentWeight != null && shipmentReceiptDate !=
				 * null) { // Add the previous shipment shipments.add(new Shipment(warehouseId,
				 * warehouseName, shipmentId, shipmentMethod, shipmentWeight,
				 * shipmentReceiptDate)); } else // If the file was incorrectly formatted { if
				 * (firstShipment) // First check if this is the very first shipment in the xml
				 * file { firstShipment = false; // If so, do not return null //
				 * shipments.add(new Shipment(warehouseId, warehouseName, shipmentId, //
				 * shipmentMethod, shipmentWeight, shipmentReceiptDate)); } else // If this is
				 * not the first shipment, return null {
				 * System.out.println("Error in the xml file, unable to import shipments.");
				 * return null; } }
				 */
				shipmentMethod = element.getAttribute("type");
				shipmentId = element.getAttribute("id");
			}
			else if (nodeName.equals("Weight"))
			{
				shipmentUnit = element.getAttribute("unit");
				if (shipmentUnit.equals("kg")) // Check the weight is correct
				{
					shipmentWeight = (float) (Float.valueOf(element.getChildNodes().item(0).getNodeValue()) * kgToLb);
				}
				else if (shipmentUnit.equals("lb"))
				{
					shipmentWeight = (float) (Float.valueOf(element.getChildNodes().item(0).getNodeValue()));
				}

				else // If the shipment unit is not kg or lb, file is incorrectly formatted
				{
					System.out.println("Xml file incorrectly formatted.");
					return null;
				}
			}
			else if (nodeName.equals("ReceiptDate"))
			{
				shipmentReceiptDate = Long.valueOf(element.getChildNodes().item(0).getNodeValue());
				System.out.println("sreceipt date: " + shipmentReceiptDate);
			}



			// Once all the shipment variables are found, add the shipment. *Don't reset the
			// warehouse variables*
			if (warehouseId != null && warehouseName != null && shipmentId != null && shipmentMethod != null && shipmentWeight != null && shipmentReceiptDate != null)
			{
				System.out.println("added shipment");
				// Add the previous shipment
				shipments.add(new Shipment(warehouseId, warehouseName, shipmentId, shipmentMethod, shipmentWeight, shipmentReceiptDate));
				shipmentId = null;
				shipmentMethod = null;
				shipmentUnit = null;
				shipmentWeight = null;
				shipmentReceiptDate = null;
			}
		}

		// Once all the tags have been checked, return the list of shipments

		return shipments;
	}
}
