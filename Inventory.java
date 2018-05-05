package Project;

import java.text.DateFormat;
import javafx.*;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChartBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


public class Inventory extends JPanel implements ActionListener  {
	String signature="Dre's Inventory System";
	/**
	 * @param args
	 */
	 Font font= new Font("serif",Font.BOLD,30);
	 
	 String header="ITEMS\t\tQuantity Available";
	 
	 JPanel rightPanel= new JPanel();
		JPanel inventoryChart= new JPanel();
		JTextArea inventoryList= new JTextArea(header,5,20);
		JScrollPane scroller=new JScrollPane(inventoryList);
		JPanel mainPanel=new JPanel();
		JButton add=new JButton("ADD ITEM");
		JButton remove=new JButton("REMOVE ITEM");
		JButton records=new JButton("CHECK RECORDS");
		JButton total= new JButton("Total Worth");
		
	
	public Inventory(){
		setLayout(new FlowLayout());
		setBackground(Color.white);
		Title titles=new Title();
		
		
		add.addActionListener(this);
		remove.addActionListener(this);
		records.addActionListener(this);
		total.addActionListener(this);
	 
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black,3));
		mainPanel.setLayout(new GridLayout(6,1));
		mainPanel.add(titles);
		mainPanel.add(add);
		mainPanel.add(remove);
		mainPanel.add(records);
		mainPanel.add(total);
		mainPanel.setPreferredSize(new Dimension(350,504));
		
		rightPanel.setLayout(new GridLayout(2,1));
		
		inventoryList.setMargin( new Insets(7,7,7,7) );
		inventoryList.setEditable(false);
		inventoryList.setForeground(Color.white);
		inventoryList.setBackground(Color.black);
		inventoryList.setFont(new Font("Arial",Font.CENTER_BASELINE,12));
		
		scroller.setBorder(BorderFactory.createLineBorder(Color.black,3));
		rightPanel.add(scroller);
		
		inventoryChart.setPreferredSize(new Dimension(150,250));
		inventoryChart.setBackground(Color.white);
		inventoryChart.setBorder(BorderFactory.createLineBorder(Color.black,3));
		rightPanel.add(inventoryChart);
		for(int i=0;i<items.size();i++){
			
		}
		
		add(mainPanel);
		add(rightPanel); 
	}
	
	public class item{
		String itemName;
		int itemQuantity;
	    int itemPrice;	
	}
	
	public class record{
		String recType;
		String recName;
		int recQty;
		String time;
	}
	
	ArrayList<record> rec= new ArrayList<record>();
	ArrayList<item> items = new ArrayList<item>();
	
	public void addButton(){

		
		JPanel addPanel= new JPanel();
		JLabel nameLabel= new JLabel("Item Name");
		JLabel qtyLabel=new JLabel("Quantity");
		JLabel priceLabel =new JLabel("Price(#)");
		JTextField nameField= new JTextField(10);
		JTextField qtyField=new JTextField(10);
		JTextField priceField=new JTextField(10);
		
		addPanel.setLayout(new GridLayout(3,1));
		
		addPanel.add(nameLabel);
		addPanel.add(nameField);
		addPanel.add(qtyLabel);
		addPanel.add(qtyField);
		addPanel.add(priceLabel);
		addPanel.add(priceField);
		
		boolean existing=false;
		
		JOptionPane.showMessageDialog(add, addPanel,"Add Item",3);
		if(items.size()==0){
			
			item obj= new item();
			obj.itemName= nameField.getText();
			obj.itemPrice=Integer.parseInt(priceField.getText());
			obj.itemQuantity=Integer.parseInt(qtyField.getText());
			
			items.add(obj);
		
		}else{
		for(int i=0;i<items.size();i++){
			if(items.get(i).itemName.toLowerCase().equals(nameField.getText().toLowerCase())){
				existing=true;
				items.get(i).itemQuantity +=Integer.parseInt(qtyField.getText());
			}
		}
		if(existing==false){
				item obj=new item();
				obj.itemName= nameField.getText();
				obj.itemPrice=Integer.parseInt(priceField.getText());
				obj.itemQuantity=Integer.parseInt(qtyField.getText());
				
				items.add(obj);	
			}
		}
		updateInventoryList();
		DateFormat df=new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Calendar cal=  Calendar.getInstance();
		record transaction=new record();
		transaction.recName=nameField.getText();
		transaction.recQty=Integer.parseInt(qtyField.getText());
		transaction.recType= "Added";
		transaction.time=df.format(cal.getTime());
		rec.add(transaction);
		inventoryChart.removeAll();
		updateInventoryChart();		
	}
	public void updateInventoryChart(){
		DefaultPieDataset dataset= new DefaultPieDataset();
		for(int i=0;i<items.size();i++){
			dataset.setValue(items.get(i).itemName, items.get(i).itemQuantity);
		}
		JFreeChart chart=ChartFactory.createPieChart("Inventory Chart", dataset, true, true,false);
		PiePlot colorConfig=(PiePlot)chart.getPlot();
		for(int i=0; i<items.size();i++){
			colorConfig.setSectionPaint(i, new Color((int)Math.random()*600,(int)Math.random()*700,(int)Math.random()*800));
		}
		ChartPanel cp=new ChartPanel(chart);
		cp.setPreferredSize(new Dimension(250,250));
		inventoryChart.add(cp,new GridLayout(1,0));
		inventoryChart.validate();
	}
	
	public void removeButton(){
		JPanel addPanel= new JPanel();
		JLabel nameLabel= new JLabel("Item Name");
		JLabel qtyLabel=new JLabel("Quantity");
		JLabel priceLabel =new JLabel("Price(#)");
		JTextField nameField= new JTextField(10);
		JTextField qtyField=new JTextField(10);
		JTextField priceField=new JTextField(10);
		
		addPanel.setLayout(new GridLayout(2,1));
		
		addPanel.add(nameLabel);
		addPanel.add(nameField);
		addPanel.add(qtyLabel);
		addPanel.add(qtyField);
		
		JOptionPane.showMessageDialog(remove,addPanel,"Remove Item",3);
		if(items.size()==0){
			JOptionPane.showMessageDialog(remove, "There are no Items available");
		}else if(items.size()!=0){
		for(int i=0;i<items.size();i++){
			if(items.get(i).itemName.toLowerCase().equals(nameField.getText().toLowerCase())){
				items.get(i).itemQuantity -=Integer.parseInt(qtyField.getText());
				if(items.get(i).itemQuantity<0){
					items.get(i).itemQuantity +=Integer.parseInt(qtyField.getText());
					JOptionPane.showMessageDialog(remove,"You have only "+items.get(i).itemQuantity+" "+ items.get(i).itemName+ " left in the inventory\n Enter a valid number"	);
				}
				updateInventoryList();
				DateFormat df=new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Calendar cal=  Calendar.getInstance();
				record transaction=new record();
				transaction.recName=nameField.getText();
				transaction.recQty=Integer.parseInt(qtyField.getText());
				transaction.recType= "Removed";
				transaction.time=df.format(cal.getTime());
				rec.add(transaction);
			 	inventoryChart.removeAll();
				updateInventoryChart();			
			}
		}
		}
		else{
			JOptionPane.showMessageDialog(remove,nameField.getText()+" is not available on the inventory List");
		}
	}
	
	public void recordButton(){
		JPanel recPanel=new JPanel();
		JTextArea recField=new JTextArea();
		JScrollPane recScroll=new JScrollPane(recField);
	    recField.setMargin(new Insets(7,7,7,7));
		recScroll.setPreferredSize(new Dimension(500,250));
		recPanel.add(recScroll);
		recField.setFont(new Font("serif",Font.BOLD,12));
		recField.setForeground(Color.black);
		recField.setText("  Transaction Type\t Item name\t Number of item \t Date&Time");
		recField.setEditable(false);
		int j=0;
		for(int i=0;i<rec.size();i++){
			recField.append("\n"); 
			recField.append(++j +". "+ rec.get(i).recType +"\t\t   "+ rec.get(i).recName+"\t   "+rec.get(i).recQty+"\t\t"+rec.get(i).time);
		}
		JOptionPane.showMessageDialog(records,recPanel,"Transaction Record",3);
		
	}
	public void worth(){
		JPanel worthPanel=new JPanel();
		JTextArea worthArea= new JTextArea();
		JScrollPane scroll=new JScrollPane(worthArea);
		int totalPrice;
		int totalworth=0;
		worthArea.setEditable(false);
		worthArea.setMargin(new Insets(7,7,7,7));
		worthArea.setFont(new Font("serif",Font.BOLD,12));
		worthArea.setForeground(Color.black);
		scroll.setPreferredSize(new Dimension(500,250));
		worthPanel.add(scroll);
		worthArea.append(" Item\t\t"+"Item Price(#)\t\t"+"Item qunatity\t\t"+"Total Price");
		int j=0;
		for(int i=0;i<items.size();i++){
			totalPrice=items.get(i).itemPrice * items.get(i).itemQuantity;
			worthArea.append("\n"+ ++j +"." +items.get(i).itemName+"\t\t"+items.get(i).itemPrice+"\t\t"+items.get(i).itemQuantity+"\t\t"+totalPrice);
			totalworth += totalPrice;
		}
			worthArea.append("\nTotal worth= "+"#"+totalworth);
			JOptionPane.showMessageDialog(total, worthPanel,"Worth",1);
		
	}

	public  class Title extends JPanel{
		public void paintComponent(Graphics g){
			super.paintComponent(g);
		    Graphics2D g2;
		    g2=(Graphics2D)g;
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setFont(font);
			g2.setColor(new Color(50,100,150));
			g2.setBackground(Color.LIGHT_GRAY);
			g2.drawString(signature,10,25);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window= new JFrame("Inventory System");
		Inventory content= new Inventory();
		window.setContentPane(content);
		window.setSize(500,500);
		window.setLocation(300,100);
		window.setVisible(true);
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
	public void updateInventoryList(){
		inventoryList.setText(header);
		for(int i=0;i<items.size();i++){
			inventoryList.append("\n"+items.get(i).itemName+"\t\t"+items.get(i).itemQuantity);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="ADD ITEM"){
			addButton();
		}else if(e.getActionCommand()=="REMOVE ITEM"){
			removeButton();
		}else if(e.getActionCommand()=="CHECK RECORDS"){
			recordButton();
		}else if(e.getActionCommand()=="Total Worth"){
			worth();
		}
		
	}
}
