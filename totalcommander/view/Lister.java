package totalcommander.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Lister {
	String content;
	public Lister(String content){
			this.content = content;
			buildWindow();
	}
	
	private void buildWindow(){
		JFrame frame = new JFrame();
		JScrollPane scroll = new JScrollPane();
		JTextArea text = new JTextArea();
		JPanel panel = new JPanel();
		
		frame.setSize(700, 800);
		scroll.setViewportView(text);
		text.setText(content);
		text.setEditable(false);
		
		panel.setLayout(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);
		frame.add(panel);

		frame.setVisible(true);
	}
	
}
