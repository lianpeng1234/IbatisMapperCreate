package com.lp.window;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateStatusWindow {

	private JFrame frame = new JFrame("");// ���崰��
	private Container container = frame.getContentPane();// �õ���������
	private JPanel panel = new JPanel();// ����һ�����
	
	private JLabel text;
	
	public CreateStatusWindow(final JFrame showTableframe){
		showTableframe.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 500, 200, 200);
		frame.setVisible(true);
		//
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));//new BoxLayout(jpanel,BoxLayout.Y_AXIS)   new FlowLayout(FlowLayout.LEADING)
		//
		text = new JLabel("�ļ�������");
		text.setBounds(12, 40, 200, 200);
        panel.add(text);
		//
        JButton btnNewButton = new JButton("����");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		frame.setVisible(false);
        		showTableframe.setVisible(true);
        	}
        });
        panel.add(btnNewButton);
        //�������
        container.add(panel);
	}
	
	public void updateTextContent(String content) {
		text.setText(content);
	}
	
	public void addButton(JButton button) {
		panel.add(button);
	}
	
}
