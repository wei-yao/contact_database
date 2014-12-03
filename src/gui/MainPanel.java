package gui;


import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *  Ö÷»­Ãæ.
 * @author zhaolu
 *
 */
public class MainPanel extends JFrame {
	  private JPanel pnlAll = new JPanel();
	  private JList list;
	  private DefaultListModel listModel=new DefaultListModel();
	  public static void main(final String[] args) {
	        new MainPanel();
	    }
	  public MainPanel(){
//		    add(pnlAll);
		    initialzieList();
	        setSize(280, 210);
	        setResizable(true);
	        setLocationRelativeTo(null);
	        setTitle("Visual Cryptography");
	        setVisible(true);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
//	        listModel.addElement("Jane Doe");
	  }
	  private void refreshData(ArrayList<String> data){
		  listModel.clear();
		  
	  }
	private void initialzieList() {
//		 listModel.addElement("Jane Doe");
//	        listModel.addElement("John Smith");
//	        listModel.addElement("Kathy Green");
//	        listModel.addElement("Jane Doe");
//	        listModel.addElement("John Smith");
//	        listModel.addElement("Kathy Green");
//	        listModel.addElement("Jane Doe");
//	        listModel.addElement("John Smith");
//	        listModel.addElement("Kathy Green");
//	        listModel.addElement("Jane Doe");
//	        listModel.addElement("John Smith");
//	        listModel.addElement("Kathy Green");
		 list = new JList(listModel); 
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      list.setSelectedIndex(0);
	      list.setVisibleRowCount(2);
	      JScrollPane listScrollPane = new JScrollPane(list);
	      add(listScrollPane, BorderLayout.CENTER);
	}
}
