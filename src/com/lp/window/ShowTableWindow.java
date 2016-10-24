package com.lp.window;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.lp.db.JdbcUtil;
import com.lp.ibatis.CreateIbatisFile;
 
public class ShowTableWindow {
	
	private JFrame frame = new JFrame("选择表");// 定义窗体
	private Container container = frame.getContentPane();// 得到窗体容器
	private JPanel panel = new JPanel();// 定义一个面板
	
	//表名checkBox
	private List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
	
	public ShowTableWindow(final MainWindow mainWindow){
		mainWindow.setVisible(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1000, 800);
		frame.setVisible(true);
		//
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));//new BoxLayout(jpanel,BoxLayout.Y_AXIS)   new FlowLayout(FlowLayout.LEADING)
		//添加滚动条
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setViewportView(panel);
		//
		
		//
        List<String> tableNameList = getTableName();
        for(int i=0; i<tableNameList.size(); i++) {
        	JCheckBox jcb = new JCheckBox(tableNameList.get(i));
        	jcb.setVisible(true);
        	panel.add(jcb);
        	checkBoxList.add(jcb);
        }
        //
        JButton selectAll = new JButton("全选");
        selectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<checkBoxList.size(); i++) {
					JCheckBox jb = checkBoxList.get(i);
					jb.setSelected(true);
				}
			}
		});
        panel.add(selectAll);
        //
    	JButton deSelectAll = new JButton("反选");
    	deSelectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<checkBoxList.size(); i++) {
					JCheckBox jb = checkBoxList.get(i);
					if(jb.isSelected()) {
						jb.setSelected(false);
					} else {
						jb.setSelected(true);
					}
				}
			}
		});
    	panel.add(deSelectAll);
        //
        JButton btnNewButton = new JButton("确定");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		List<String> tableNameSelected = new ArrayList<String>();
        		for(int i=0; i<checkBoxList.size(); i++) {
        			if(checkBoxList.get(i).isSelected()) {
        				tableNameSelected.add(checkBoxList.get(i).getText().trim());
        			}
        		}
        		CreateStatusWindow createStatusWindow = new CreateStatusWindow(frame);
        		//创建ibatis文件
        		CreateIbatisFile.createIbatisFile(tableNameSelected, createStatusWindow);
        	}
        });
        panel.add(btnNewButton);
        //
        JButton backButton = new JButton("返回");
        backButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		mainWindow.setVisible(true);
        		frame.setVisible(false);
        	}
        });
        panel.add(backButton);
        //加载面板
        container.add(scroll);
	}
	
	public List<String> getTableName() {
		List<String> list = new ArrayList<String>();
		try {
			Connection conn = JdbcUtil.getDefaultConnection();
			PreparedStatement pst = conn.prepareStatement("SHOW TABLE STATUS");
			ResultSet resultSet = pst.executeQuery();
			while(resultSet.next()) {
				String tableName = resultSet.getString("Name");
				list.add(tableName);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
    
}

















