package com.androidlec.marketproject;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ConnectFTP {
    private final String TAG = "Connect FTP";
    public FTPClient ftpClient = null;

    public ConnectFTP(){
        ftpClient = new FTPClient();
    }

    public boolean ftpConnect(String host, String username, String password, int port){
        boolean result = false;

        try {
            ftpClient.connect(host, port);

            if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                result = ftpClient.login(username, password);
                ftpClient.enterLocalPassiveMode();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean ftpChangeDirectory(String directory){
        try {
            ftpClient.changeWorkingDirectory(directory);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean ftpUploadFile(String srcFilePath, String desFileName, String desDirectory){
        boolean result = false;
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            FileInputStream fis = new FileInputStream(srcFilePath);

            if(ftpChangeDirectory(desDirectory)) {
                result = ftpClient.storeFile(desFileName, fis);
            }
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public boolean ftpDisconnect(){
        boolean result = false;
        try {
            ftpClient.logout();
            ftpClient.disconnect();
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String[] ftpGetFileList(String directory){
        String[] fileList = null;
        int i = 0;
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(directory);
            fileList = new String[ftpFiles.length];
            for(FTPFile file : ftpFiles){
                String fileName = file.getName();

                if(file.isFile()){
                    fileList[i] = "(File)" + fileName;
                }else{
                    fileList[i] = "(Directory)" + fileName;
                }
                i++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return fileList;
    }

    public boolean ftpCreateDirectory(String directory){
        boolean result = false;
        try {
            result = ftpClient.makeDirectory(directory);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean ftpDeleteDirectory(String directory){
        boolean result = false;
        try {
            result = ftpClient.removeDirectory(directory);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean ftpDeleteFile(String file){
        boolean result = false;
        try{
            result = ftpClient.deleteFile(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean ftpRenameFile(String from, String to){
        boolean result = false;
        try {
            result = ftpClient.rename(from, to);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean ftpDownloadFile(String srcFilePath, String desFilePath){
        boolean result = false;
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);

            FileOutputStream fos = new FileOutputStream(desFilePath);
            result = ftpClient.retrieveFile(srcFilePath, fos);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
