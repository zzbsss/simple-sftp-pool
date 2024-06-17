package com.zzb.simple.sftp.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.zzb.simple.sftp.SftpPool;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zzb
 * @version 1.0
 * @description:
 * @date 2022/10/22 11:08
 */
public class SftpHelper {

    private static SftpHelper sftpHelper;

    @Autowired
    private SftpPool sftpPool;


    @PostConstruct
    public void init() {
        sftpHelper = this;
    }

    /**
     * 下载文件
     * @param dir 远程目录
     * @param name 远程文件名
     * @return 文件字节数组
     */
    public static  byte[] download(String dir, String name) {
        ChannelSftp sftp = sftpHelper.sftpPool.borrowObject();
        try {
            sftp.cd(dir);
            InputStream in = sftp.get(name);
            return inputStreamToByteArray(in);
        } catch (SftpException  e) {
            throw new RuntimeException("sftp下载文件出错", e);
        } finally {
            sftpHelper.sftpPool.returnObject(sftp);
        }
    }

    /**
     * 上传文件
     * @param dir 远程目录
     * @param name 远程文件名
     * @param in 输入流
     */
    public static  void upload(String dir, String name, InputStream in) {
        ChannelSftp sftp = sftpHelper.sftpPool.borrowObject();
        try {
            mkdirs(sftp, dir);
            sftp.cd(dir);
            sftp.put(in, name);
        } catch (SftpException e) {
            throw new RuntimeException("sftp上传文件出错", e);
        } finally {
            sftpHelper.sftpPool.returnObject(sftp);
        }
    }

    /**
     * 删除文件
     * @param dir 远程目录
     * @param name 远程文件名
     */
    public static  void delete(String dir, String name) {
        ChannelSftp sftp = sftpHelper.sftpPool.borrowObject();
        try {
            sftp.cd(dir);
            sftp.rm(name);
        } catch (SftpException e) {
            throw new RuntimeException("sftp删除文件出错", e);
        } finally {
            sftpHelper.sftpPool.returnObject(sftp);
        }
    }

    /**
     * 递归创建多级目录
     * @param dir 多级目录
     */
    private static  void mkdirs(ChannelSftp sftp, String dir) {
        String[] folders = dir.split("/");
        try {
            sftp.cd("/");
            for (String folder: folders) {
                if (folder.length()>0) {
                    try {
                        sftp.cd(folder);
                    } catch (Exception e) {
                        sftp.mkdir(folder);
                        sftp.cd(folder);
                    }
                }
            }
        } catch (SftpException e) {
            throw new RuntimeException("sftp创建目录出错", e);
        }
    }

    /**
     * 获取 连接对象 ChannelSftp
     * @return
     */
    public static ChannelSftp getChannelSftp() {
        return  sftpHelper.sftpPool.borrowObject();
    }

    /**
     * 归还连接对象
     * @param channelSftp
     */
    public static void returnObject(ChannelSftp channelSftp) {
        sftpHelper.sftpPool.returnObject(channelSftp);
    }

    /**
     * inputStream转byte数组
     *
     * @param inputStream 输入流对象
     * @return byte数组
     */
    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int num;
            while ((num = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, num);
            }
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
}
