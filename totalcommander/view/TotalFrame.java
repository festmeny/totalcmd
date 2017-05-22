package totalcommander.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import totalcommander.listeners.ActionKeyListener;
import totalcommander.listeners.BottomButtonsListener;
import totalcommander.listeners.ExplorerListener;
import totalcommander.listeners.MouseFocusListener;
import totalcommander.listeners.SwitchDriveListener;
import totalcommander.logic.FileTableData;

public class TotalFrame {
	private JFrame frame;
	private FileTableData leftSideData = new FileTableData();
	private FileTableData rightSideData = new FileTableData();
	private JTable leftSideTable;
	private JTable rightSideTable;
	private JComboBox<Object> leftDrives;
	private JComboBox<Object> rightDrives;
	private JLabel leftSize;
	private JLabel rightSize;
	private JLabel leftPath;
	private JLabel rightPath = new JLabel("right path");
	private JButton buttonView = new JButton("F3 View");
	private JButton buttonCopy = new JButton("F5 Copy");
	private JButton buttonMove = new JButton("F6 Move");
	private JButton buttonNewFolder = new JButton("F7 NewFolder");
	private JButton buttonDelete = new JButton("F8 Delete");
	private JButton buttonExit = new JButton("Alt + F4 Exit");
	private JScrollPane leftScroll = new JScrollPane();
	private JScrollPane rightScroll = new JScrollPane();
	private AtomicBoolean leftSideFocusBool = new AtomicBoolean();
	
	public TotalFrame(){
			buildWindow();
			setListeners();
			
			frame.setVisible(true);
	}
	private void buildWindow(){
		frame = new JFrame("Ultimate Best TotalCommander");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 850, 600);
		frame.setMinimumSize(new Dimension(580,280));
		
		JPanel BottomBar = new JPanel();
		GroupLayout layout = new GroupLayout(frame.getContentPane());
		
		leftDrives = new JComboBox<Object>(File.listRoots());
		rightDrives = new JComboBox<Object>(File.listRoots());
		leftSideTable = new JTable(leftSideData);
		rightSideTable = new JTable(rightSideData);
		leftSize = new JLabel(leftSideData.getSpace(leftDrives.getSelectedItem()).toString());
		rightSize = new JLabel(rightSideData.getSpace(rightDrives.getSelectedItem()).toString());
		
		
		leftScroll.setViewportView(leftSideTable);
		leftScroll.getViewport().setBackground(new Color(255,255,255));
		rightScroll.setViewportView(rightSideTable);
		rightScroll.getViewport().setBackground(new Color(255,255,255));
		leftSideTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//MULTIPLE_INTERVAL_SELECTION
		leftSideTable.setShowGrid(false);
		leftSideTable.getTableHeader().setReorderingAllowed(false);
		leftSideTable.setAutoCreateColumnsFromModel( false );
		leftSideTable.getColumnModel().getColumn(0).setMaxWidth(15);
		rightSideTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rightSideTable.setShowGrid(false);
		rightSideTable.getTableHeader().setReorderingAllowed(false);
		rightSideTable.setAutoCreateColumnsFromModel( false );
		rightSideTable.getColumnModel().getColumn(0).setMaxWidth(15);
		leftPath = new JLabel(leftSideData.getPath());
		rightPath = new JLabel(rightSideData.getPath());
		
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.TRAILING)
					.addComponent(BottomBar, 0, 636, Short.MAX_VALUE)
					.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
									.addComponent(leftScroll, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
									.addGap(24))
								.addGroup(layout.createSequentialGroup()
									.addComponent(leftPath, 0, 313, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(layout.createSequentialGroup()
								.addComponent(leftDrives, 0, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(leftSize, 0 , GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(rightScroll, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
							.addComponent(rightPath, 0, 297, Short.MAX_VALUE)
							.addGroup(layout.createSequentialGroup()
								.addComponent(rightDrives, 0, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(rightSize, 0 , GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)))
						.addContainerGap())
		);
		
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(leftDrives, 0, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
							.addComponent(leftSize, 0 , GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
							.addComponent(rightDrives, 0, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(rightSize, 0 , GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rightPath, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(leftPath, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(leftScroll, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
						.addComponent(rightScroll, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(BottomBar, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
		);
			
			BottomBar.setLayout(new GridLayout(0, 6, 0, 0));
			BottomBar.add(buttonView);
			BottomBar.add(buttonCopy);
			BottomBar.add(buttonMove);
			BottomBar.add(buttonNewFolder);
			BottomBar.add(buttonDelete);
			BottomBar.add(buttonExit);
			
			frame.getContentPane().setLayout(layout);
	}
	
	private void setListeners(){
		leftDrives.addActionListener(new SwitchDriveListener(leftDrives, leftSideData, leftPath, leftSize));
		rightDrives.addActionListener(new SwitchDriveListener(rightDrives, rightSideData, rightPath, rightSize));
		leftSideTable.addMouseListener(new ExplorerListener(leftSideData, leftPath));
		rightSideTable.addMouseListener(new ExplorerListener(rightSideData, rightPath));
		leftScroll.addMouseListener(new MouseFocusListener(leftScroll, leftSideTable, rightScroll, rightSideTable, leftSideFocusBool));
		rightScroll.addMouseListener(new MouseFocusListener(leftScroll, leftSideTable, rightScroll, rightSideTable, leftSideFocusBool));
		leftSideTable.addMouseListener(new MouseFocusListener(leftScroll, leftSideTable, rightScroll, rightSideTable, leftSideFocusBool));
		rightSideTable.addMouseListener(new MouseFocusListener(leftScroll, leftSideTable, rightScroll, rightSideTable, leftSideFocusBool));

		buttonDelete.setActionCommand("delete");
		buttonNewFolder.setActionCommand("newFolder");
		buttonExit.setActionCommand("exit");
		buttonMove.setActionCommand("move");
		buttonCopy.setActionCommand("copy");
		buttonView.setActionCommand("view");
		
		buttonDelete.addActionListener(new BottomButtonsListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		buttonNewFolder.addActionListener(new BottomButtonsListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		buttonExit.addActionListener(new BottomButtonsListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		buttonMove.addActionListener(new BottomButtonsListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		buttonCopy.addActionListener(new BottomButtonsListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		buttonView.addActionListener(new BottomButtonsListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		
		frame.addKeyListener(new ActionKeyListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		leftSideTable.addKeyListener(new ActionKeyListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		leftScroll.addKeyListener(new ActionKeyListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		rightSideTable.addKeyListener(new ActionKeyListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		rightScroll.addKeyListener(new ActionKeyListener(leftSideTable,leftSideData,rightSideTable,rightSideData,leftSideFocusBool));
		
		leftSideTable.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int col = leftSideTable.columnAtPoint(e.getPoint());
		        String name = leftSideTable.getColumnName(col);
		        System.out.println(name);
		        leftSideData.setComparator(name);
		    }
		});
		
		rightSideTable.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int col = rightSideTable.columnAtPoint(e.getPoint());
		        String name = rightSideTable.getColumnName(col);
		        System.out.println(name);
		        rightSideData.setComparator(name);
		    }
		});
		
		leftSideTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		leftSideTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "Backspace");
		leftSideTable.getActionMap().put("Enter", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	int row = leftSideTable.getSelectedRow();
	        	if (row == -1)
	        		return;
	        	if(!leftSideData.explore(new String[]{(String)leftSideData.getValueAt(row, 1), (String)leftSideData.getValueAt(row, 2), (String)leftSideData.getValueAt(row, 3)}))
        			JOptionPane.showMessageDialog(new JFrame(), "Cannot open selected item!", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    });
		leftSideTable.getActionMap().put("Backspace", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	leftSideData.back();
	        }
	    });
		
		rightSideTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		rightSideTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "Backspace");
		rightSideTable.getActionMap().put("Enter", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	int row = rightSideTable.getSelectedRow();
	        	if (row == -1)
	        		return;
	        	if(!rightSideData.explore(new String[]{(String)rightSideData.getValueAt(row, 1), (String)rightSideData.getValueAt(row, 2), (String)rightSideData.getValueAt(row, 3)}))
        			JOptionPane.showMessageDialog(new JFrame(), "Cannot open selected item!", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    });
		rightSideTable.getActionMap().put("Backspace", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	rightSideData.back();
	        }
	    });
	}
}
