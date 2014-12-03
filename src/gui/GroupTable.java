package gui;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 


/*
 * TableRenderDemo.java requires no other files.
 */

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.example.database.ContactDatabaseAdapter;
import com.example.database.DbOperate;
import com.example.database.ContactDatabaseAdapter.GroupEntry;
import com.example.database.Contacter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/** 
 *显示群组的界面.
 */
public class GroupTable extends JPanel {
    private boolean DEBUG = false;
    private ArrayList<HashMap<String,Object>> data;
    private MyTableModel tableModel;
    private JTable table;
    public GroupTable() {
//        super(new GridLayout(1,0));
    	   super(new BorderLayout());
//        ArrayList<Contacter> ca=new ArrayList<Contacter>();
             data=getData();
//        as.add("gaowei");
//        ca.add(new Contacter("yao", "18788836154",as , 2));
             tableModel=new MyTableModel(data);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Set up column sizes.
        initColumnSizes(table);
        ArrayList<String> groupList=new ArrayList<String>();
		try {
			groupList = ContactDatabaseAdapter.getGroupList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        //Fiddle with the Sport column's cell editors/renderers.
//        setUpSportColumn(table, table.getColumnModel().getColumn(3),groupList);
//        setUpSportColumn(table, table.getColumnModel().getColumn(4),groupList);
//        setUpSportColumn(table, table.getColumnModel().getColumn(5),groupList);
        //Add the scroll pane to this panel.
        add(scrollPane);
        initializeButton();
        
//        data.add(new Contacter("yap", "123456", new ArrayList<String>(), 10));
    }
    private JButton confirmEdit=new JButton("确认修改"),cancelEdit=new JButton("取消修改");
    private JButton addButton=new JButton("添加分组"),deleteButton=new JButton("删除分组");
    private JButton  viewDetailBtn=new JButton("查看分组");
    private void initializeButton() {
    	 JPanel buttonPane = new JPanel();
         buttonPane.setLayout(new BoxLayout(buttonPane,
                                            BoxLayout.LINE_AXIS));
         buttonPane.add(addButton);
         buttonPane.add(viewDetailBtn);
         viewDetailBtn.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
				int selRow=table.getSelectedRow();
				if(selRow!=-1){
					long groupId=(Long) tableModel.getValueAt(selRow, 0);
					try {
						GroupDetail.createAndShowGUI(null, groupId);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
		
			}
		});
        addButton.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
			try {
				long nextId=	DbOperate.getNextGroupId();
				if(nextId!=-1){
				HashMap<String,Object> newRecord=new HashMap<String, Object>();
				newRecord.put(GroupEntry._ID, nextId);
				newRecord.put(GroupEntry.NAME, "");
				data.add(newRecord);
				tableModel.fireTableDataChanged();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("添加失败");
			}
			}
		});
        deleteButton.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
				int selectRow =table.getSelectedRow();
				if(selectRow!=-1){
					System.out.println("delete row "+selectRow );
					data.remove(selectRow);
					tableModel.fireTableDataChanged();
				}
			}
		});
        confirmEdit.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
				try {
					ContactDatabaseAdapter.CommitGroupChange(data);
					data.clear();
					data.addAll(ContactDatabaseAdapter.getGroupData());
					tableModel.fireTableDataChanged();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("写入失败");
				}
			}
		});
        cancelEdit.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
				data.clear();
				try {
					data.addAll(ContactDatabaseAdapter.getGroupData());
					tableModel.fireTableDataChanged();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("取消失败");
				}
				
			}
		});
         buttonPane.add(confirmEdit);
         buttonPane.add(cancelEdit);
       
         buttonPane.add(deleteButton);
         buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
         add(buttonPane, BorderLayout.PAGE_END);
         
	}
///**
// * 跳转到查看特定组的联系人界面.
// * @param groupId
// */
//	protected void goToDetail(long groupId) {
//		GroupDetail.createAndShowGUI(null, groupId);
//	}

	private ArrayList<HashMap<String, Object>> getData() {
    	try {
//			ContactDatabaseAdapter.setUp();
			return ContactDatabaseAdapter.getGroupData();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<HashMap<String, Object>>();
		}
	}

	/*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    private void initColumnSizes(JTable table) {
        MyTableModel model = (MyTableModel)table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < 2; i++) {
            column = table.getColumnModel().getColumn(i);

            comp = headerRenderer.getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                             getTableCellRendererComponent(
                                 table, longValues[i],
                                 false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;

            if (DEBUG) {
                System.out.println("Initializing width of column "
                                   + i + ". "
                                   + "headerWidth = " + headerWidth
                                   + "; cellWidth = " + cellWidth);
            }

            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }


    class MyTableModel extends AbstractTableModel {
    	  private String[] columnNames
    	  = {GroupEntry._ID,
                 GroupEntry.NAME,
    	   };
    	  private ArrayList<HashMap<String, Object>> data;
        public MyTableModel(ArrayList<HashMap<String,Object>> data){
        	this.data=data;
        }
        public final Object[] longValues = {"Jane", "Kathy",
                                            "None of the above",
                                            new Integer(20), Boolean.TRUE};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
        	if(row>=data.size())
         	   return "";
          return  data.get(row).get(columnNames[col]);
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }
            HashMap<String,Object > old,newObj;
            old=(HashMap<String, Object>) data.get(row);
            newObj=new HashMap<String, Object>(old);
            newObj.put(columnNames[col],value );
            data.set(row, newObj);
            fireTableCellUpdated(row, col);
            
            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
//            int numRows = getRowCount();
//            int numCols = getColumnCount();
//
//            for (int i=0; i < numRows; i++) {
//                System.out.print("    row " + i + ":");
//                for (int j=0; j < numCols; j++) {
//                    System.out.print("  " + data[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println("--------------------------");
//        }
    }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI(JFrame parent) {
        //Create and set up the window.
        JFrame frame = new JFrame("TableRenderDemo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if(parent!=null)
        frame.setLocationRelativeTo(parent);
        //Create and set up the content pane.
        GroupTable newContentPane = new GroupTable();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(null);
            }
        });
    }
}