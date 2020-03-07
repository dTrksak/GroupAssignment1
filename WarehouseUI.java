package main;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
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
	WarehouseHandler handle = new WarehouseHandler();
	InputHandler Ihandle = new InputHandler();
	CompleteUI text = new CompleteUI();
	String S = "12513";
	//Warehouse UIwarehouse =  handle.addWarehouse(S,"");

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
		Warehouse warehouse =  handle.addWarehouse(S,"");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		
		JLabel lblToggleReciept = new JLabel("Toggle Reciept");
		
		JLabel lblAddShipment = new JLabel("Add Shipment");
		
		JLabel lblPrintShipments = new JLabel("Print Shipments");
		
		JLabel lblExport = new JLabel("Export Shipments");
		
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ihandle.showData();
				}
			
			});
	
		
		JButton btnAdd = new JButton("Add");
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
						
						try {
							Ihandle.createShipmentProcess(shipInfo);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
			
			}
		});

		
		JButton tglbtnOnoff = new JButton("On/Off");
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
						
						if(status.equals("ON")) {
							Ihandle.enableFreight(warehouseID);;
						}else {
							Ihandle.endFreight(warehouseID);
						}
					}
				});

			
			}
		});
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					Ihandle.exportAllWarehouse();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JLabel lblGetWarehouseName = new JLabel("get Warehouse name ");
		
		JButton btnNewButton = new JButton("Get Name");
		btnNewButton.addActionListener(new ActionListener() {
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

						String name = Ihandle.getWarehouseName(field.getText());
						
						JOptionPane.showMessageDialog(null, name);
					}
				});

			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGetWarehouseName)
						.addComponent(lblToggleReciept)
						.addComponent(lblPrintShipments)
						.addComponent(lblAddShipment)
						.addComponent(lblExport))
					.addPreferredGap(ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton)
						.addComponent(tglbtnOnoff)
						.addComponent(btnPrint)
						.addComponent(btnAdd)
						.addComponent(btnExport))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGetWarehouseName)
						.addComponent(btnNewButton))
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrintShipments)
						.addComponent(btnPrint))
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblAddShipment)
						.addComponent(btnAdd))
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblToggleReciept)
						.addComponent(tglbtnOnoff))
					.addGap(37)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblExport)
							.addComponent(btnExport))))
					
					
		;
		contentPane.setLayout(gl_contentPane);
	}
}



