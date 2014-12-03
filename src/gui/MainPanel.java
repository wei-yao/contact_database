package gui;



import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.example.database.ContactDatabaseAdapter;

/**
 *  主画面.
 * @author zhaolu
 *
 */
public class MainPanel extends JFrame {
	  private JPanel pnlAll = new JPanel();
//	  private JList list;
//	  private DefaultListModel listModel=new DefaultListModel();
	  public static void main(final String[] args) throws SQLException {
	        new MainPanel();
	    }
	  private JButton contactBtn,groupBtn;
	  public MainPanel() throws SQLException{
			ContactDatabaseAdapter.setUp();
		  contactBtn=new JButton("查看联系人");
		  contactBtn.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
				goToContactList();
			}
		});
		  groupBtn=new JButton("查看群组");
		  groupBtn.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
				goToGroupList();
			}
		});
		pnlAll.add(contactBtn);
		pnlAll.add(groupBtn);
		  add(pnlAll);
//		    initialzieList();
	        setSize(280, 210);
	        setResizable(true);
	        setLocationRelativeTo(null);
	        setTitle("人际关系管理系统");
	        setVisible(true);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
//	        listModel.addElement("Jane Doe");
	  }
protected void goToGroupList() {
	GroupTable.createAndShowGUI(this);
	}
	//	  private void refreshData(ArrayList<String> data){
//		  listModel.clear();
//		  
//	  }
//	private void initialzieList() {
////		 listModel.addElement("Jane Doe");
////	        listModel.addElement("John Smith");
////	        listModel.addElement("Kathy Green");
////	        listModel.addElement("Jane Doe");
////	        listModel.addElement("John Smith");
////	        listModel.addElement("Kathy Green");
////	        listModel.addElement("Jane Doe");
////	        listModel.addElement("John Smith");
////	        listModel.addElement("Kathy Green");
////	        listModel.addElement("Jane Doe");
////	        listModel.addElement("John Smith");
////	        listModel.addElement("Kathy Green");
//		 list = new JList(listModel); 
//		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//	      list.setSelectedIndex(0);
//	      list.setVisibleRowCount(2);
//	      JScrollPane listScrollPane = new JScrollPane(list);
//	      add(listScrollPane, BorderLayout.CENTER);
//	}
	  private void goToContactList() {
//		 remove(pnlAll);
//		 setContentPane(new TableRenderDemo());
//		  add(new TableRenderDemo());
		  ContactTable.createAndShowGUI(this);
		}
}
