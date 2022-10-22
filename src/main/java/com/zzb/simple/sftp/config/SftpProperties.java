package com.zzb.simple.sftp.config;

/**
 * @author zzb
 * @version 1.0
 * @description:
 * @date 2022/10/22 11:04
 */

import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sftp")
public class SftpProperties {

    /**
     * sftp 主机地址
     */
    private String host;
    /**
     * sftp 端口
     */
    private int port = 22;
    /**
     * sftp 用户名
     */
    private String username;
    /**
     * sftp 密码
     */
    private String password;

    /**
     * 连接sftp服务器，默认超时时间30000
     */
    private int timeout = 30000;

    private Pool pool = new Pool();

    public static class Pool extends GenericObjectPoolConfig<ChannelSftp> {
        /**
         * 最大连接数
         */
        private int maxTotal = DEFAULT_MAX_TOTAL;
        /**
         * 最大空闲连接数
         */
        private int maxIdle = DEFAULT_MAX_IDLE;
        /**
         * 最小空闲连接数
         */
        private int minIdle = DEFAULT_MIN_IDLE;

        public Pool() {
            super();
        }
        @Override
        public int getMaxTotal() {
            return maxTotal;
        }
        @Override
        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }
        @Override
        public int getMaxIdle() {
            return maxIdle;
        }
        @Override
        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }
        @Override
        public int getMinIdle() {
            return minIdle;
        }
        @Override
        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
