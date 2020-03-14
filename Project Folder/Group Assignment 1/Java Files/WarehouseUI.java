package main;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;


public class WarehouseUI extends JFrame {

	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=63,14
	 */
	/**
	 * Launch the application.
	 */
	WarehouseHandler handle = WarehouseHandler.getInstance();
	InputHandler Ihandle = new InputHandler();
	CompleteUI text = new CompleteUI();
	String S = null;

	public void setupFrame() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WarehouseUI frame = new WarehouseUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WarehouseUI() {
		//Warehouse warehouse =  handle.addWarehouse(S,"");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		
		JLabel lblToggleReciept = new JLabel("Toggle Reciept");
		
		JLabel lblAddWarehouse = new JLabel("Add Warehouse");
		
		JLabel lblAddShipment = new JLabel("Add Shipment");
		
		JLabel lblPrintShipments = new JLabel("Print Shipments");
		
		JLabel lblExport = new JLabel("Export Warehouse Shipments");
		
		
		
		JButton btnPrint = new JButton("Print"); // Prints all current data onto a message panel
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Warehouse> list = handle.getAllWarehouses();

				// converts warehouse list to a string so that its able to print out in JPanel
			    StringBuffer sb = new StringBuffer();
			    if (list != null)
				{
					for (int i = 0; i < list.size(); i++)
					{
						sb.append("Warehouse " + list.get(i).getWarehouseID() + ", " + list.get(i).getWarehouseName() + " - ");
						sb.append(list.get(i).getShipmentList().toString());
						sb.append("\n\n");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "There are not warehouses to display");
				}
			    
			    String str = sb.toString();
				JOptionPane.showMessageDialog(null, str);
				
				}
			
			});
		
		JButton btnAddWarehouse = new JButton("Add Warehouse"); // Creates a new warehouse
		btnAddWarehouse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				JFrame frame = new JFrame();
				frame.setSize(400, 300);
				frame.setLayout(new FlowLayout());
				JLabel addWarehouseID = new JLabel("Enter WarehouseID");
				JTextField field = new JTextField(20);
				frame.add(addWarehouseID);
				frame.add(field);
				JLabel addWarehouseName = new JLabel("Enter warehouse name");
				JTextField field2 = new JTextField(20);
				frame.add(addWarehouseName);
				frame.add(field2);
				
				
				frame.setVisible(true);
				JButton doneBtn = new JButton("Done");
				frame.add(doneBtn);
				doneBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String wareHouseIDInit = field.getText();
						String wareHouseNameInit = field2.getText();
						
						if(wareHouseIDInit.equals("") || wareHouseNameInit.equals("")) {
							JOptionPane.showMessageDialog(null, "All fields should have a value.");
						}
						// User cannot create a warehouse here, they must use the create warehouse button.
						else if(handle.getWarehouse(wareHouseIDInit) == null) {
							
							Ihandle.createWarehouseProcess(wareHouseIDInit, wareHouseNameInit);
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //Close the warehouse window
						} else {
							JOptionPane.showMessageDialog(null, "Warehouse "+wareHouseIDInit+" already exists.");
						}
					}
				});
			}
		});
		
		JButton btnAdd = new JButton("Add Shipment"); // Creates a new shipment
		btnAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				String[] shipInfo = new String[6];
				JFrame frame = new JFrame();
				frame.setSize(400, 400);
				frame.setLayout(new FlowLayout());
				JLabel addWarehouseID = new JLabel("Enter WarehouseID");
				JTextField field = new JTextField(20);
				frame.add(addWarehouseID);
				frame.add(field);
				JLabel addWarehouseName = new JLabel("Add warehouse name");
				JTextField field2 = new JTextField(20);
				frame.add(addWarehouseName);
				frame.add(field2);
				JLabel addShipmentID = new JLabel("Add ShipmentID");
				JTextField field3 = new JTextField(20);
				frame.add(addShipmentID);
				frame.add(field3);
				JLabel addShipmentMethod = new JLabel("Add Shipment Method");
				JTextField field4 = new JTextField(20);
				frame.add(addShipmentMethod);
				frame.add(field4);
				JLabel addShipmentWeight = new JLabel("Add Shipment Weight");
				JTextField field5 = new JTextField(20);
				frame.add(addShipmentWeight);
				frame.add(field5);
				JLabel addShipmentDate = new JLabel("Add Shipment Date");
				JTextField field6 = new JTextField(20);
				frame.add(addShipmentDate);
				frame.add(field6);
				
				
				frame.setVisible(true);
				JButton doneBtn = new JButton("Done");
				frame.add(doneBtn);
				doneBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String wareHouseIDInit = field.getText();
						shipInfo[0] = wareHouseIDInit;
						String wareHouseNameInit = field2.getText();
						shipInfo[1] = wareHouseNameInit;
						String wareHouseShipmentIDInit = field3.getText();
						shipInfo[2] = wareHouseShipmentIDInit;
						String shipmentMethodInit = field4.getText();
						shipInfo[3] = shipmentMethodInit;
						String wareHouseWeightInit = field5.getText();
						shipInfo[4] = wareHouseWeightInit;
						String shipmentDateInit = field6.getText();
						shipInfo[5] = shipmentDateInit;
						
						if(wareHouseIDInit.equals("") || wareHouseNameInit.equals("") || wareHouseShipmentIDInit.equals("") ||
								shipmentMethodInit.equals("") || wareHouseWeightInit.equals("") || shipmentDateInit.equals("")) {
							JOptionPane.showMessageDialog(null, "All fields should have a value.");
						}
						// User cannot create a warehouse here, they must use the create warehouse button.
						else if(handle.getWarehouse(wareHouseIDInit) != null) {
							try {
								Shipment s = Ihandle.createShipmentProcess(shipInfo);
								if(s != null)
								{
									//List<Shipment> saveList = new ArrayList<>();
									//saveList.add(s);
									//RecoverData.saveData(saveList);
									JOptionPane.showMessageDialog(null, "Shipment added to warehouse "+handle.getWarehouse(wareHouseIDInit).getWarehouseID()+".");
									frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //Close the warehouse window
								}
								else if(handle.getWarehouse(wareHouseIDInit).getAvailability() == false) {
									JOptionPane.showMessageDialog(null, "Warehouse "+handle.getWarehouse(wareHouseIDInit).getWarehouseID()+"'s freight receipt is disabled.");
								}
								else {
									JOptionPane.showMessageDialog(null, "Could not add shipment to warehouse "+handle.getWarehouse(wareHouseIDInit).getWarehouseID()+". "
											+ "Make sure the shipment information is correct. Also warehouse "+handle.getWarehouse(wareHouseIDInit).getWarehouseID()+
											"'s name is '"+handle.getWarehouse(wareHouseIDInit).getWarehouseName()+"'.");
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Warehouse "+wareHouseIDInit+" doesn't exist.");
						}
					}
				});
				
			
			}
		});

		
		JButton tglbtnOnoff = new JButton("On/Off"); // Turns warehouse on or off
		tglbtnOnoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				JFrame frame = new JFrame();
				frame.setSize(300, 300);
				frame.setLayout(new FlowLayout());
				JLabel addWarehouseID = new JLabel("Enter WarehouseID");
				JTextField field = new JTextField(20);
				frame.add(addWarehouseID);
				frame.add(field);
				JLabel avalibility = new JLabel("ON or OFF");
				JTextField field2 = new JTextField(20);
				frame.add(avalibility);
				frame.add(field2);
				
				frame.setVisible(true);
				JButton doneBtn = new JButton("Done");
				frame.add(doneBtn);
				doneBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						String warehouseID = field.getText();
						String status = field2.getText();
						
						if(handle.getWarehouse(warehouseID) != null)
						{
							if(status.equalsIgnoreCase("ON")) {
								Ihandle.enableFreight(warehouseID);
								JOptionPane.showMessageDialog(null, "Warehouse "+handle.getWarehouse(warehouseID).getWarehouseID()+" freight receipt is enabled.");
								frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //Close the on/off window
							} else if(status.equalsIgnoreCase("OFF")) {
								Ihandle.endFreight(warehouseID);
								JOptionPane.showMessageDialog(null, "Warehouse "+handle.getWarehouse(warehouseID).getWarehouseID()+" freight receipt is disabled.");
								frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //Close the on/off window
							} else {
								JOptionPane.showMessageDialog(null, "Please type ON or OFF"); //Don't close the window, if the user messed up the input
							}
						} else {
							JOptionPane.showMessageDialog(null, "Warehouse "+warehouseID+" doesn't exist.");
						}
					}
				});

			
			}
		});
		
		JButton btnExport = new JButton("Export"); // Exports all current data to a Json file 
		btnExport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame frame = new JFrame();
				frame.setSize(300, 200);
				frame.setLayout(new FlowLayout());
				JLabel addWarehouseID = new JLabel("Enter WarehouseID");
				JTextField wField = new JTextField(20);
				frame.add(addWarehouseID);
				frame.add(wField);
				
				frame.setVisible(true);
				JButton doneBtn = new JButton("Done");
				frame.add(doneBtn);
				doneBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String warehouseID = wField.getText();
						
						try {
							if(handle.getWarehouse(warehouseID) != null)
							{
								Ihandle.exportWarehouse(warehouseID);
								frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
							} else {
								JOptionPane.showMessageDialog(null, "Warehouse "+warehouseID+" doesn't exist.");
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});
		
		JLabel lblGetWarehouseName = new JLabel("get Warehouse name ");
		
		JButton getName = new JButton("Get Name"); // Given a warehouse ID displays the warehouse Name
		getName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new JFrame();
				frame.setSize(300, 300);
				frame.setLayout(new FlowLayout());
				JLabel addWarehouseID = new JLabel("Enter WarehouseID");
				JTextField field = new JTextField(20);
				frame.add(addWarehouseID);
				frame.add(field);
				
				frame.setVisible(true);
				JButton doneBtn = new JButton("Done");
				frame.add(doneBtn);
				doneBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){

						Warehouse w = handle.getWarehouse(field.getText());
						if(w != null)
						{
							JOptionPane.showMessageDialog(null, "Warehouse "+w.getWarehouseID()+"'s name is "+w.getWarehouseName()+".");
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						} else {
							JOptionPane.showMessageDialog(null, "Warehouse "+field.getText()+" does not exist.");
						}
					}
				});

			}
		});
		
		// Creates layout for all buttons
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGetWarehouseName)
						.addComponent(lblToggleReciept)
						.addComponent(lblPrintShipments)
						.addComponent(lblAddWarehouse)
						.addComponent(lblAddShipment)
						.addComponent(lblExport))
					.addPreferredGap(ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(getName)
						.addComponent(tglbtnOnoff)
						.addComponent(btnPrint)
						.addComponent(btnAddWarehouse)
						.addComponent(btnAdd)
						.addComponent(btnExport))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGetWarehouseName)
						.addComponent(getName))
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrintShipments)
						.addComponent(btnPrint))
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblAddWarehouse)
						.addComponent(btnAddWarehouse))
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblAddShipment)
						.addComponent(btnAdd))
					.addGap(23) //22
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblToggleReciept)
						.addComponent(tglbtnOnoff))
					.addGap(23) //37
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblExport)
							.addComponent(btnExport))))
					
					
		;
		contentPane.setLayout(gl_contentPane);
	}
}




