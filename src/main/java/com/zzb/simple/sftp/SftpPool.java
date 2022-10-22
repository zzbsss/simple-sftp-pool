package com.zzb.simple.sftp;


import com.jcraft.jsch.ChannelSftp;

import com.zzb.simple.sftp.factory.SftpFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;


public class SftpPool {

    private GenericObjectPool<ChannelSftp> pool;

    public SftpPool(SftpFactory factory) {
        this.pool = new GenericObjectPool<>(factory, factory.getProperties().getPool());
    }

    /**
     * 获取一个sftp连接对象
     * @return sftp连接对象
     */
    public ChannelSftp borrowObject() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("获取ftp连接失败", e);
        }
    }

    /**
     * 归还一个sftp连接对象
     * @param channelSftp sftp连接对象
     */
    public void returnObject(ChannelSftp channelSftp) {
        if (channelSftp!=null) {
            pool.returnObject(channelSftp);
        }
    }

    public GenericObjectPool<ChannelSftp> getPool() {
        return pool;
    }

    public void setPool(GenericObjectPool<ChannelSftp> pool) {
        this.pool = pool;
    }
}
