package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class CompleteUI extends JFrame
{
	public JPanel contentPane;
	JTextField textField;
	FileOperations input = new FileOperations();
	JsonHandler jhandle = new JsonHandler();
	InputHandler Ihandle = new InputHandler();
	WarehouseHandler handle = WarehouseHandler.getInstance();

	/**
	 * Launch the application.
	 */
	public void launchUI()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					CompleteUI frame = new CompleteUI();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CompleteUI()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnXml = new JButton("XML");
		btnXml.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Ihandle.importXmlProcess();
				}
				catch (ParserConfigurationException | SAXException | IOException e)
				{
					// e.printStackTrace();
				}
			}
		});

		JLabel lblOr = new JLabel("Or");

		JButton btnJson = new JButton("Json");
		btnJson.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Ihandle.importShipmentProcess();
				}
				catch (Exception ew)
				{
				}
			}
		});

		JLabel lblImportShipment = new JLabel("Import Shipment");

		JLabel lblWarehouseInfo = new JLabel("Warehouse info");

		textField = new JTextField();

		textField.setColumns(10);

		JLabel lblEnterWarehouseid = new JLabel("Enter WarehouseID");

		JLabel lblExportAllshipments = new JLabel("Export All Shipments");

		JButton btnExportJson = new JButton("Export");
		btnExportJson.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				try
				{
					Ihandle.exportAllWarehouse();
				}
				catch (IOException p)
				{
					System.out.println("Export Failed");

				}
			}
		});

		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String w = textField.getText();
				Ihandle.createWarehouseProcess(w, "");
				WarehouseUI wareUI = new WarehouseUI();

				wareUI.setVisible(true);
			}

		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(lblWarehouseInfo).addGap(18).addComponent(lblEnterWarehouseid).addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE).addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnGo)).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(12).addComponent(lblImportShipment).addGap(45).addComponent(btnXml)).addComponent(lblExportAllshipments)).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(32).addComponent(lblOr).addGap(43).addComponent(btnJson)).addGroup(gl_contentPane.createSequentialGroup().addGap(49).addComponent(btnExportJson))))).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(30).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnXml).addComponent(lblOr).addComponent(btnJson).addComponent(lblImportShipment)).addGap(42).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblExportAllshipments).addComponent(btnExportJson)).addGap(31).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblWarehouseInfo).addComponent(lblEnterWarehouseid).addComponent(btnGo)).addContainerGap(65, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
