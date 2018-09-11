package site.duanzy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.duanzy.ShadowsocksApplication;
import site.duanzy.entity.Ss;
import site.duanzy.mapper.SsMapper;
import site.duanzy.service.SsService;
import site.duanzy.utils.FileUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ss账号操作
 * @author leo
 * @date 2018/9/10 15:07
 */
@Service
public class SsServiceImpl implements SsService {

    private static Logger log = LoggerFactory.getLogger(SsServiceImpl.class);

    @Autowired
    private SsMapper mapper;
    @Autowired
    private FileUtil fileUtil;

    @Override
    public List<Ss> getAllAccount() {
        return mapper.getAccounts(null);
    }

    @Override
    public List<Ss> getNotExpiredAccount() {
        Map<String, Object> param = new HashMap<>();
        param.put("endTime","endTime");
        List<Ss> accounts = mapper.getAccounts(param);
        return accounts;
    }

    @Override
    public int addAccount(Ss ss) {
        //先添加到数据库
        int i = mapper.insertSelective(ss);
        log.info("添加账号1-->添加到数据库-->结果[{}]",i);
        if(i > 0){
            //数据库添加成功，添加到文件
//            -1：添加失败，0:端口已经存在，1：添加成功
            int i1 = fileUtil.addAccount(ss.getPort(), ss.getPassword());
            log.info("添加账号1-->添加到文件-->结果[{}]",i1);
            if(1 == i1){
                //文件添加成功，添加端口到防火墙开放
                int i2 = fileUtil.addOpenPort(ss.getPort());
                log.info("添加账号1-->添加端口到防火墙开放-->结果[{}]",i2);
                if(1 == i2){
                    //防火墙开放成功，重启SS
                    int i3 = fileUtil.restartSs();
                    log.info("添加账号1-->重启SS-->结果[{}]",i3);
                    if(1 == i3){
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int addAccount(int port, String password) {
        int i1 = fileUtil.addAccount(port, password);
        log.info("添加账号2-->添加到文件-->结果[{}]",i1);
        if(1 == i1){
            //文件添加成功，添加端口到防火墙开放
            int i2 = fileUtil.addOpenPort(port);
            log.info("添加账号2-->添加端口到防火墙开放-->结果[{}]",i2);
            if(1 == i2){
                //防火墙开放成功，重启SS
                int i3 = fileUtil.restartSs();
                log.info("添加账号2-->重启SS-->结果[{}]",i3);
                if(1 == i3){
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public int delAccountByPort(int port) {
        //先删除 数据库中的记录
        int i = mapper.deleteByPort(port);
        log.info("删除账号1-->删除数据库中的记录-->结果[{}]",i);
        if(i > 0){
            //数据库删除成功，删除文件中的记录
//          -1：删除失败，0：端口不存在，1：删除成功
            int i1 = fileUtil.delAccount(port);
            log.info("删除账号1-->删除文件中的记录-->结果[{}]",i1);
            if (delConfig(port, i1)) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int delConfigAccount(int port) {
        int i1 = fileUtil.delAccount(port);
        log.info("删除账号2-->删除文件中的记录-->结果[{}]",i1);
        if (delConfig(port, i1)) {
            return 1;
        }
        return 0;
    }

    /**
     * 删除 配置文件 中 的账号
     * @param port
     * @param i1
     * @return
     */
    private boolean delConfig(int port, int i1) {
        if(1 == i1){
            //删除文件中的记录成功，再 关闭 端口
            int i2 = fileUtil.delOpenPort(port);
            log.info("删除账号1/2-->关闭端口-->结果[{}]",i2);
            if(1 == i2){
                //防火墙端口关闭成功，重启SS
                int i3 = fileUtil.restartSs();
                log.info("删除账号1/2-->重启SS-->结果[{}]",i3);
                if(1 == i3){
                    return true;
                }
            }
        }
        return false;
    }
}
