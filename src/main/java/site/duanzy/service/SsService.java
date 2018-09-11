package site.duanzy.service;

import site.duanzy.entity.Ss;

import java.util.List;

/**
 * @author leo
 * @date 2018/9/10 15:03
 */
public interface SsService {

    /**
     * 获取所有的账号信息
     * @return
     */
    List<Ss> getAllAccount();

    /**
     * 获取未过期的账号信息
     */
    List<Ss> getNotExpiredAccount();

    /**
     * 添加账号
     * @param ss
     * @return
     */
    int addAccount(Ss ss);

    /**
     * 只添加到ss的配置文件
     * @param port
     * @param password
     * @return
     */
    int addAccount(int port,String password);

    /**
     * 删除账号
     * @param port
     * @return
     */
    int delAccountByPort(int port);

    /**
     * 只删除ss的配置文件中的端口
     * @param port
     * @return
     */
    int delConfigAccount(int port);
}
