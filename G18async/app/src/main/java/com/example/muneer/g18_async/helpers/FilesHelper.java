package com.example.muneer.g18_async.helpers;
import java.io.*;


public class FilesHelper {
	public String createFolder(String parentFolderPath, String folderName) {
		String newFolderPath = parentFolderPath + "\\" + folderName;
		File file = new File(newFolderPath);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Folder: " + folderName + " created in " + parentFolderPath);
                return newFolderPath;
            } else {
                System.out.println("Failed to create Folder: " + folderName + " in " + parentFolderPath);
                return null;
            }
        } else {
        	System.out.println("Folder: " + folderName + " already exists in " + parentFolderPath);
        	return newFolderPath;
    	}
	}

	public String getFileContent(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] data = new byte[(int) file.length()];
        try {
            fis.read(data);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

        String str = null;
        try {
            str = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str;
    }


}
