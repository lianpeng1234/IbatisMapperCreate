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
	private JTextField userField;// ���ݿ��û���
	private JTextField pwdField;// ���ݿ�����
	private JTextField dbIpField;// ���ݿ��ַ
	private JTextField dbNameField;// ���ݿ�����
	private JTextField ibatisDaoPackageField;// ibatisDao�ļ���package·�� ������
	private JTextField ibatisEntityPackageField;// ibatisEntity�ļ���package·�� ������
	private JTextField appServicePackageField;// Ӧ�� service ���ļ���package·�� ������
	private JTextField appServiceImplPackageField;// Ӧ�� service ��ʵ���ļ���package·�� ������
	private JTextField appControllerPackageField;//Ӧ�� controller ��ʵ���ļ���package·�� �����
	private JTextField deletePrefixField;//ɾ��ǰ׺
	private JCheckBox isCreateMoveSql;//�Ƿ����ɶ�̬sql
	private JComboBox<String> dbtype = new JComboBox<String>();//�ļ���ʽ����
	private JTextField fileSavePathField;// �ļ�����·��
	private JTextField uploadPathField;// �ϴ��ļ�·��
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
	private void initDbFile() {
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

		int jTextFiledY = 10;
		int jlableY = 10;
		int jlableYStep = 30;
		
		JLabel lblUsername = new JLabel("���ݿ��û���");
		lblUsername.setBounds(12, jlableY, 150, 15);
		contentPane.add(lblUsername);
		// ���ݿ��û���
		userField = new JTextField();
		userField.setBounds(100, jTextFiledY, 200, 21);
		userField.setText(DbFile.singleton.getJdbc().getUsername());
		contentPane.add(userField);
		userField.setColumns(10);
		
		JLabel lblPassword = new JLabel("���ݿ�����");
		lblPassword.setBounds(12, jlableY+=jlableYStep, 150, 15);
		contentPane.add(lblPassword);
		// ���ݿ�����
		pwdField = new JTextField();
		pwdField.setBounds(100, jTextFiledY+=jlableYStep, 200, 21);
		pwdField.setText(DbFile.singleton.getJdbc().getPassword());
		contentPane.add(pwdField);
		pwdField.setColumns(10);
		
		
		JLabel jdbcUrl = new JLabel("���ݿ�ip��ַ�Ͷ˿ںţ����� 192.168.25.36:3306��");
		jdbcUrl.setBounds(12, jlableY+=jlableYStep, 300, 15);
		contentPane.add(jdbcUrl);
		// ���ݿ�ip��ַ�Ͷ˿ں�
		dbIpField = new JTextField();
		dbIpField.setBounds(300, jTextFiledY+=jlableYStep, 500, 21);
		dbIpField.setText(DbFile.singleton.getJdbc().getUrl());
		contentPane.add(dbIpField);
		dbIpField.setColumns(10);

		JLabel dbName = new JLabel("���ݿ�����");
		dbName.setBounds(12, jlableY+=jlableYStep, 150, 15);
		contentPane.add(dbName);
		// ���ݿ�����
		dbNameField = new JTextField();
		dbNameField.setBounds(100, jTextFiledY+=jlableYStep, 500, 21);
		dbNameField.setText(DbFile.singleton.getJdbc().getDbname());
		contentPane.add(dbNameField);
		dbNameField.setColumns(10);
		
		JLabel daoPackagePath = new JLabel("Dao�ļ�package·��");
		daoPackagePath.setBounds(12, jlableY+=jlableYStep, 150, 15);
		contentPane.add(daoPackagePath);
		// dao�ļ�package
		ibatisDaoPackageField = new JTextField();
		ibatisDaoPackageField.setBounds(200, jTextFiledY+=jlableYStep, 500, 21);
		ibatisDaoPackageField.setText(DbFile.singleton.getFileConfig().getIbatisDaoPackage());
		contentPane.add(ibatisDaoPackageField);
		ibatisDaoPackageField.setColumns(10);
		

		JLabel entityPackagePath = new JLabel("entity�ļ�package·��");
		entityPackagePath.setBounds(12, jlableY+=jlableYStep, 150, 15);
		contentPane.add(entityPackagePath);
		// entity�ļ�package
		ibatisEntityPackageField = new JTextField();
		ibatisEntityPackageField.setBounds(200, jTextFiledY+=jlableYStep, 500, 21);
		ibatisEntityPackageField.setText(DbFile.singleton.getFileConfig().getIbatisEntityPackage());
		contentPane.add(ibatisEntityPackageField);
		ibatisEntityPackageField.setColumns(10);
		
		JLabel appServicePackagePath = new JLabel("service�ӿ�java�ļ�package·��");
		appServicePackagePath.setBounds(12, jlableY+=jlableYStep, 230, 15);
		contentPane.add(appServicePackagePath);
		// Ӧ�� service ���ļ���package·�� ������
		appServicePackageField = new JTextField();
		appServicePackageField.setBounds(240, jTextFiledY+=jlableYStep, 500, 21);
		appServicePackageField.setText(DbFile.singleton.getFileConfig().getServicePackage());
		contentPane.add(appServicePackageField);
		appServicePackageField.setColumns(10);
		
		JLabel appServiceImplPackagePath = new JLabel("service�ӿ�ʵ��java�ļ�package·��");
		appServiceImplPackagePath.setBounds(12, jlableY+=jlableYStep, 230, 15);
		contentPane.add(appServiceImplPackagePath);
		// Ӧ�� service ��ʵ���ļ���package·�� ������
		appServiceImplPackageField = new JTextField();
		appServiceImplPackageField.setBounds(240, jTextFiledY+=jlableYStep, 500, 21);
		appServiceImplPackageField.setText(DbFile.singleton.getFileConfig().getServiceImplPackage());
		contentPane.add(appServiceImplPackageField);
		appServiceImplPackageField.setColumns(10);

		JLabel appControllerPackagePath = new JLabel("Controllerʵ��java�ļ�package·��");
		appControllerPackagePath.setBounds(12, jlableY+=jlableYStep, 230, 15);
		contentPane.add(appControllerPackagePath);
		//Ӧ�� controller ��ʵ���ļ���package·�� ������
		appControllerPackageField = new JTextField();
		appControllerPackageField.setBounds(240, jTextFiledY+=jlableYStep, 500, 21);
		appControllerPackageField.setText(DbFile.singleton.getFileConfig().getControllerPackage());
		contentPane.add(appControllerPackageField);
		appControllerPackageField.setColumns(10);
		
		JLabel fileSavePath = new JLabel("�ļ�����·��");
		fileSavePath.setBounds(12, jlableY+=jlableYStep, 150, 15);
		contentPane.add(fileSavePath);
		// �ļ�����·��
		int fileSavePathFieldY = jTextFiledY+=jlableYStep;
		fileSavePathField = new JTextField();
		fileSavePathField.setBounds(200, fileSavePathFieldY, 250, 21);
		fileSavePathField.setText(DbFile.singleton.getFileConfig().getFileSavePath());
		contentPane.add(fileSavePathField);
		fileSavePathField.setColumns(10);
		JButton saveButton = new JButton("����·��");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (chooser.showOpenDialog(aaa) == JFileChooser.APPROVE_OPTION) {
					fileSavePathField.setText(chooser.getSelectedFile().toString());
				}
			}
		});
		saveButton.setBounds(460, fileSavePathFieldY, 93, 23);
		contentPane.add(saveButton);
		
//		JLabel uploadPath = new JLabel("�ϴ�ҳ��·��");
//		uploadPath.setBounds(12, jlableY+=jlableYStep, 150, 15);
//		contentPane.add(uploadPath);
//		//�ϴ�ҳ���ļ�
//		int uploadPathFieldY = jTextFiledY+=jlableYStep;
//		uploadPathField = new JTextField();
//		uploadPathField.setBounds(200, uploadPathFieldY, 250, 21);
//		uploadPathField.setText(DbFile.singleton.getFileConfig().getUploadFilePath());
//		contentPane.add(uploadPathField);
//		uploadPathField.setColumns(10);
//		JButton uploadButton = new JButton("ѡ���ļ�");
//		uploadButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				JFileChooser chooser = new JFileChooser();
//				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//				if (chooser.showOpenDialog(aaa) == JFileChooser.APPROVE_OPTION) {
//					uploadPathField.setText(chooser.getSelectedFile().toString());
//				}
//			}
//		});
//		uploadButton.setBounds(460, uploadPathFieldY, 93, 23);
//		contentPane.add(uploadButton);

		JLabel ibatisFileCharset = new JLabel("�ļ�����");
		ibatisFileCharset.setBounds(12, jlableY+=jlableYStep, 150, 15);
		contentPane.add(ibatisFileCharset);
		//�ļ������ʽ
		dbtype.addItem("UTF-8");
		dbtype.addItem("GBK");
		if(StringUtils.isBlank(DbFile.singleton.getFileConfig().getIbatisFileCharset())) {
			dbtype.setSelectedItem("UTF-8");//Ĭ��ѡ�� UTF-8
		} else {
			dbtype.setSelectedItem(DbFile.singleton.getFileConfig().getIbatisFileCharset());//Ĭ��ѡ��
		}
		dbtype.setBounds(200, jTextFiledY+=jlableYStep, 100, 21);
		contentPane.add(dbtype);
		
		JLabel deletePrefix = new JLabel("ɾ���ַ���");
		deletePrefix.setBounds(12, jlableY+=jlableYStep, 150, 15);
		contentPane.add(deletePrefix);
		//ɾ��ǰ׺
		deletePrefixField = new JTextField();
		deletePrefixField.setBounds(200, jTextFiledY+=jlableYStep, 250, 21);
		deletePrefixField.setText(DbFile.singleton.getFileConfig().getDeleteStr());
		contentPane.add(deletePrefixField);
		deletePrefixField.setColumns(10);
		
		isCreateMoveSql = new JCheckBox("����ibatis��̬sql");
		isCreateMoveSql.setBounds(12, jlableY+=jlableYStep, 200, 20);
		isCreateMoveSql.setSelected(DbFile.singleton.getFileConfig().getIsCreateMoveSql());
		contentPane.add(isCreateMoveSql);
		//ʹ��˵��
		JTextArea instructions = new JTextArea(4,30);
		instructions.setBounds(12, jlableY+=jlableYStep, 1000, 200);
		instructions.setEditable(false);//�ı����򲻿ɱ༭
		Font instFont=new Font("����", Font.PLAIN,15);
		instructions.setFont(instFont);
		instructions.setText(getInstructions());
		instructions.setForeground(Color.red);
		contentPane.add(instructions);
		//
		JButton btnNewButton = new JButton("����");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveDbFileChange();//
				showTableWindow();
			}
		});
		btnNewButton.setBounds(12, 600, 93, 23);
		contentPane.add(btnNewButton);
		
	}

	private void saveDbFileChange() {
		//
		DbFile.singleton.getFileConfig().setIbatisDaoPackage(ibatisDaoPackageField.getText());//
		DbFile.singleton.getFileConfig().setIbatisEntityPackage(ibatisEntityPackageField.getText());//
		DbFile.singleton.getFileConfig().setFileSavePath(path(fileSavePathField.getText().replace("/", "\\"),true));//
//		DbFile.singleton.getFileConfig().setUploadFilePath(path(uploadPathField.getText().replace("/", "\\"),false));
		DbFile.singleton.getFileConfig().setIbatisFileCharset(dbtype.getSelectedItem().toString());
		DbFile.singleton.getFileConfig().setServicePackage(appServicePackageField.getText());
		DbFile.singleton.getFileConfig().setServiceImplPackage(appServiceImplPackageField.getText());
		DbFile.singleton.getFileConfig().setControllerPackage(appControllerPackageField.getText());
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

	private String path(String path, boolean isEndWithOppositeSlash) {
		if (path.endsWith("\\") || path.endsWith("/")) {
			return path;
		} else {
			if(isEndWithOppositeSlash) {
				return path + "\\";
			} else {
				return path;
			}
		}
	}
	
	/**
	 * ʹ��˵��
	 * @return
	 */
	private String getInstructions() {
		StringBuilder sbf = new StringBuilder();
		sbf.append("ʹ��˵��\n");
		sbf.append("1:ֻ֧��mysql���ݿ�\n");
		sbf.append("2:֧�ֵ����ݿ�����\n");
		MysqlDataType[] dataTypeS = MysqlDataType.values();
		for(int dts=0; dts < dataTypeS.length; dts++) {
			sbf.append(dataTypeS[dts].getMySqlDataType());
			sbf.append(";");
			if((dts+1) % 10 == 0) {
				sbf.append("\n");
			}
		}
		sbf.append("\n==================================================================================");
		sbf.append("\nʹ��ibatis��̬sql��ȱ��");
		sbf.append("\n1:ʹ�ö�̬sql����ϵͳ���ϣ���λ�����ʱ�������֪��������sql�������⣬�Ǻ��Ѷ�λ���ģ�");
		sbf.append("\n����Ҫ�鿴�����߼�����������߼����ӣ��ǾͿӵ���");
		return sbf.toString();
	}
	
	public void showTableWindow() {
		new ShowTableWindow(mainWindow);
	}

}
