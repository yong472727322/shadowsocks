package site.duanzy.entity;

import java.util.Date;

public class Ss {
    /**
     * 
     */
    private Integer id;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 密码
     */
    private String password;

    /**
     * 最大连接数
     */
    private Integer clientNum;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 到期时间
     */
    private Date endTime;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 端口
     * @return port 端口
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 端口
     * @param port 端口
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 最大连接数
     * @return client_num 最大连接数
     */
    public Integer getClientNum() {
        return clientNum;
    }

    /**
     * 最大连接数
     * @param clientNum 最大连接数
     */
    public void setClientNum(Integer clientNum) {
        this.clientNum = clientNum;
    }

    /**
     * 开始时间
     * @return start_time 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 开始时间
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 到期时间
     * @return end_time 到期时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 到期时间
     * @param endTime 到期时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}