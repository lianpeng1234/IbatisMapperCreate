package com.lp.window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.lp.db.DbFile;
import com.lp.db.FileConfigJsonEntity;
import com.lp.db.JdbcJsonEntity;
import com.lp.db.MysqlDataType;
import com.lp.util.FileUtil;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame aaa = new JFrame();
	private JPanel contentPane;
	private JTextField userField;// 数据库用户名
	private JTextField pwdField;// 数据库密码
	private JTextField dbIpField;// 数据库地址
	private JTextField dbNameField;// 数据库名称
	private JTextField ibatisDaoPackageField;// ibatisDao文件的package路径 输入库框
	private JTextField ibatisEntityPackageField;// ibatisEntity文件的package路径 输入库框
	private JTextField appServicePackageField;// 应用 service 层文件的package路径 输入库框
	private JTextField appServiceImplPackageField;// 应用 service 层实现文件的package路径 输入库框
	private JTextField deletePrefixField;//删除前缀
	private JCheckBox isCreateMoveSql;//是否生成动态sql
	private JComboBox<String> dbtype = new JComboBox<String>();//文件格式编码

	private JTextField fileSavePathField;// 文件保存路径
	private static MainWindow mainWindow = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow = new MainWindow();
					mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * 
     */
	public void initDbFile() {
		String content = FileUtil.readFile(DbFile.dbFilePath);
		if (content != null && DbFile.dbFilePath.length() > 0) {
			DbFile db = JSONObject.parseObject(content, DbFile.class);
			DbFile.singleton = db;
		} else {
			DbFile db = new DbFile();
			db.setJdbc(new JdbcJsonEntity());
			db.setFileConfig(new FileConfigJsonEntity());
			DbFile.singleton = db;
		}
	}

	public MainWindow() {
		//
		initDbFile();
		//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblUsername = new JLabel("数据库用户名");
		lblUsername.setBounds(12, 10, 150, 15);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("数据库密码");
		lblPassword.setBounds(12, 40, 150, 15);
		contentPane.add(lblPassword);

		JLabel jdbcUrl = new JLabel("数据库ip地址和端口号（例如 192.168.25.36:3306）");
		jdbcUrl.setBounds(12, 70, 300, 15);
		contentPane.add(jdbcUrl);

		JLabel dbName = new JLabel("数据库名称");
		dbName.setBounds(12, 100, 150, 15);
		contentPane.add(dbName);

		JLabel daoPackagePath = new JLabel("Dao文件package路径");
		daoPackagePath.setBounds(12, 130, 150, 15);
		contentPane.add(daoPackagePath);

		JLabel entityPackagePath = new JLabel("entity文件package路径");
		entityPackagePath.setBounds(12, 160, 150, 15);
		contentPane.add(entityPackagePath);

		JLabel appServicePackagePath = new JLabel("service接口java文件package路径");
		appServicePackagePath.setBounds(12, 190, 230, 15);
		contentPane.add(appServicePackagePath);

		JLabel appServiceImplPackagePath = new JLabel("service接口实现java文件package路径");
		appServiceImplPackagePath.setBounds(12, 220, 230, 15);
		contentPane.add(appServiceImplPackagePath);

		JLabel fileSavePath = new JLabel("文件保存路径");
		fileSavePath.setBounds(12, 250, 150, 15);
		contentPane.add(fileSavePath);

		JLabel ibatisFileCharset = new JLabel("文件编码");
		ibatisFileCharset.setBounds(12, 280, 150, 15);
		contentPane.add(ibatisFileCharset);
		
		JLabel deletePrefix = new JLabel("删除字符串");
		deletePrefix.setBounds(12, 310, 150, 15);
		contentPane.add(deletePrefix);
		
		isCreateMoveSql = new JCheckBox("勾选生成ibatis动态sql");
		isCreateMoveSql.setBounds(12, 340, 200, 20);
		isCreateMoveSql.setSelected(DbFile.singleton.getFileConfig().getIsCreateMoveSql());
		contentPane.add(isCreateMoveSql);
		
		//使用说明
		JTextArea instructions = new JTextArea(4,30);
		instructions.setBounds(12, 370, 1000, 200);
		instructions.setEditable(false);//文本区域不可编辑
		Font instFont=new Font("宋体", Font.PLAIN,15);
		instructions.setFont(instFont);
		instructions.setText(getInstructions());
		instructions.setForeground(Color.red);
		contentPane.add(instructions);
		// 数据库用户名
		userField = new JTextField();
		userField.setBounds(100, 10, 200, 21);
		userField.setText(DbFile.singleton.getJdbc().getUsername());
		contentPane.add(userField);
		userField.setColumns(10);
		// 数据库密码
		pwdField = new JTextField();
		pwdField.setBounds(100, 40, 200, 21);
		pwdField.setText(DbFile.singleton.getJdbc().getPassword());
		contentPane.add(pwdField);
		pwdField.setColumns(10);
		// 数据库ip地址和端口号
		dbIpField = new JTextField();
		dbIpField.setBounds(300, 70, 500, 21);
		dbIpField.setText(DbFile.singleton.getJdbc().getUrl());
		contentPane.add(dbIpField);
		dbIpField.setColumns(10);
		// 数据库名称
		dbNameField = new JTextField();
		dbNameField.setBounds(100, 100, 500, 21);
		dbNameField.setText(DbFile.singleton.getJdbc().getDbname());
		contentPane.add(dbNameField);
		dbNameField.setColumns(10);
		// dao文件package
		ibatisDaoPackageField = new JTextField();
		ibatisDaoPackageField.setBounds(200, 130, 500, 21);
		ibatisDaoPackageField.setText(DbFile.singleton.getFileConfig().getIbatisDaoPackage());
		contentPane.add(ibatisDaoPackageField);
		ibatisDaoPackageField.setColumns(10);
		// entity文件package
		ibatisEntityPackageField = new JTextField();
		ibatisEntityPackageField.setBounds(200, 160, 500, 21);
		ibatisEntityPackageField.setText(DbFile.singleton.getFileConfig().getIbatisEntityPackage());
		contentPane.add(ibatisEntityPackageField);
		ibatisEntityPackageField.setColumns(10);
		// 应用 service 层文件的package路径 输入库框
		appServicePackageField = new JTextField();
		appServicePackageField.setBounds(240, 190, 500, 21);
		appServicePackageField.setText(DbFile.singleton.getFileConfig().getServicePackage());
		contentPane.add(appServicePackageField);
		appServicePackageField.setColumns(10);
		// 应用 service 层实现文件的package路径 输入库框
		appServiceImplPackageField = new JTextField();
		appServiceImplPackageField.setBounds(240, 220, 500, 21);
		appServiceImplPackageField.setText(DbFile.singleton.getFileConfig().getServiceImplPackage());
		contentPane.add(appServiceImplPackageField);
		appServiceImplPackageField.setColumns(10);
		// 文件保存路径
		fileSavePathField = new JTextField();
		fileSavePathField.setBounds(200, 250, 250, 21);
		fileSavePathField.setText(DbFile.singleton.getFileConfig().getFileSavePath());
		contentPane.add(fileSavePathField);
		fileSavePathField.setColumns(10);
		JButton saveButton = new JButton("保存路径");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (chooser.showOpenDialog(aaa) == JFileChooser.APPROVE_OPTION) {
					fileSavePathField.setText(chooser.getSelectedFile().toString());
				}
			}
		});
		saveButton.setBounds(460, 250, 93, 23);
		contentPane.add(saveButton);
		//删除前缀
		deletePrefixField = new JTextField();
		deletePrefixField.setBounds(200, 310, 250, 21);
		deletePrefixField.setText(DbFile.singleton.getFileConfig().getDeleteStr());
		contentPane.add(deletePrefixField);
		deletePrefixField.setColumns(10);
		//文件编码格式
		dbtype.addItem("UTF-8");
		dbtype.addItem("GBK");
		if(StringUtils.isBlank(DbFile.singleton.getFileConfig().getIbatisFileCharset())) {
			dbtype.setSelectedItem("UTF-8");//默认选中 UTF-8
		} else {
			dbtype.setSelectedItem(DbFile.singleton.getFileConfig().getIbatisFileCharset());//默认选中
		}
		dbtype.setBounds(200, 280, 100, 21);
		contentPane.add(dbtype);
		//
		JButton btnNewButton = new JButton("连接");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveDbFileChange();//
				showTableWindow();
			}
		});
		btnNewButton.setBounds(12, 720, 93, 23);
		contentPane.add(btnNewButton);
		
	}

	private void saveDbFileChange() {
		//
		DbFile.singleton.getFileConfig().setIbatisDaoPackage(ibatisDaoPackageField.getText());//
		DbFile.singleton.getFileConfig().setIbatisEntityPackage(ibatisEntityPackageField.getText());//
		DbFile.singleton.getFileConfig().setFileSavePath(path(fileSavePathField.getText().replace("/", "\\")));//
		DbFile.singleton.getFileConfig().setIbatisFileCharset(dbtype.getSelectedItem().toString());
		DbFile.singleton.getFileConfig().setServicePackage(appServicePackageField.getText());
		DbFile.singleton.getFileConfig().setServiceImplPackage(appServiceImplPackageField.getText());
		DbFile.singleton.getFileConfig().setDeleteStr(deletePrefixField.getText());
		DbFile.singleton.getFileConfig().setIsCreateMoveSql(isCreateMoveSql.isSelected());
		//
		DbFile.singleton.getJdbc().setUsername(userField.getText());
		DbFile.singleton.getJdbc().setPassword(pwdField.getText());
		DbFile.singleton.getJdbc().setUrl(dbIpField.getText());
		DbFile.singleton.getJdbc().setDbname(dbNameField.getText());
		//
		FileUtil.wirteFile(DbFile.dbFilePath, JSONObject.toJSONString(DbFile.singleton));
	}

	private String path(String path) {
		if (path.endsWith("\\") || path.endsWith("/")) {
			return path;
		} else {
			return path + "\\";
		}
	}

	/**
	 * 使用说明
	 * @return
	 */
	private String getInstructions() {
		StringBuilder sbf = new StringBuilder();
		sbf.append("使用说明\n");
		sbf.append("1:只支持mysql数据库\n");
		sbf.append("2:支持的数据库类型\n");
		MysqlDataType[] dataTypeS = MysqlDataType.values();
		for(int dts=0; dts < dataTypeS.length; dts++) {
			sbf.append(dataTypeS[dts].getMySqlDataType());
			sbf.append(";");
			if((dts+1) % 10 == 0) {
				sbf.append("\n");
			}
		}
		sbf.append("\n==================================================================================");
		sbf.append("\n使用ibatis动态sql的缺点");
		sbf.append("\n1:使用动态sql，当系统故障，定位问题的时候，如果想知道是哪条sql出了问题，是很难定位到的，");
		sbf.append("\n必须要查看代码逻辑，如果代码逻辑复杂，那就坑爹了");
		return sbf.toString();
	}
	
	public void showTableWindow() {
		new ShowTableWindow(mainWindow);
	}

}
