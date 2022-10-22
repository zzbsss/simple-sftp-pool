## 使用方式
### 开启
- 配置文件中添加如下配置
```properties
sftp.pool.enabled=true

```
### 使用
- 添加sftp相关配置
```properties
# sftp 配置
sftp.host=127.0.0.1
sftp.port=22
sftp.username=test
sftp.password=test
sftp.timeout=30000
```
- 代码示例
```java
 // 下载
 SftpHelper.download("/upload/ftp/439900110/test", name);
 // 上传
 File file = new File("C:\\Users\\zzb\\Desktop\\test.txt");
 SftpHelper.upload("/upload/ftp/439900110/test",file.getName(), new FileInputStream(file));
 // 从池中获取连接对象
 ChannelSftp sftp =  SftpHelper.getChannelSftp()
```
