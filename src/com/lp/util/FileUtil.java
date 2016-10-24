package com.lp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.lp.db.DbFile;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FileUtil {

	private static File createFile(String path) {
		File file = new File(path);
		//�ж�Ŀ���ļ����ڵ�Ŀ¼�Ƿ����  
        if(!file.getParentFile().exists()) {  
            if(!file.getParentFile().mkdirs()) {  
                System.err.println("����Ŀ���ļ�����Ŀ¼ʧ�ܣ�");  
            }  
        }
        return file;
	}
	
	public static void writeIbatisFile(Template template, Map<String, Object> rootMap, String fileName, String folder) {
		String savePath = DbFile.singleton.getFileConfig().getFileSavePath();
		String fielPath = null;
		if(savePath.endsWith("\\") || savePath.endsWith("/")) {
			fielPath = savePath + folder + "\\" + fileName;
		} else {
			fielPath = savePath + "\\" + folder + "\\" + fileName;
		}
		
		File file = createFile(fielPath);
		
        FileOutputStream fileOut = null;
        OutputStreamWriter outStream = null;
        BufferedWriter bw = null;
		try {
			fileOut = new FileOutputStream(file);
			outStream = new OutputStreamWriter(fileOut, DbFile.singleton.getFileConfig().getIbatisFileCharset());
			bw = new BufferedWriter(outStream);
			template.process(rootMap, bw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(bw != null) {
					bw.close();
					bw = null;
				}
				if(outStream != null) {
					outStream.close();
					outStream = null;
				}
				if(fileOut != null) {
					fileOut.close();
					fileOut = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ȡ�ļ�����
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		createFile(filePath);
		FileInputStream fin = null;
		InputStreamReader inStream = null;
		BufferedReader bReader = null;
		try {
			fin = new FileInputStream(filePath);
			inStream = new InputStreamReader(fin);
			bReader = new BufferedReader(inStream);
			String line = null;
			StringBuilder content = new StringBuilder();
			while( (line=bReader.readLine()) != null) {
				content.append(line);
			}
			return content.toString();
		} catch (FileNotFoundException e) {
			if(!filePath.endsWith("dbFile.txt")) {
				e.printStackTrace();
			} else {
				System.out.println("dbFile.txt�ļ�������");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bReader != null) {
					bReader.close();
					bReader = null;
				}
				if(inStream != null) {
					inStream.close();
					inStream = null;
				}
				if(fin != null) {
					fin.close();
					fin = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * ʹ��ϵͳĬ�ϵı��뷽ʽ
	 * д���ļ�����
	 * @param filePath
	 * @param content
	 */
	public static void wirteFile(String filePath, String content) {
		createFile(filePath);
		
		FileOutputStream fOut = null;
		OutputStreamWriter outStream = null;
		BufferedWriter bWrite = null;
		try {
			fOut = new FileOutputStream(filePath,false);
			outStream = new OutputStreamWriter(fOut);
			bWrite = new BufferedWriter(outStream);
			bWrite.write(content);
			bWrite.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bWrite != null) {
					bWrite.close();
					bWrite = null;
				}
				if(outStream != null) {
					outStream.close();
					outStream = null;
				}
				if(fOut != null) {
					fOut.close();
					fOut = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
