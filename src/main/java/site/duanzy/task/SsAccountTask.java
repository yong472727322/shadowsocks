package site.duanzy.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.duanzy.entity.Ss;
import site.duanzy.service.SsService;
import site.duanzy.utils.FileUtil;

import java.util.List;
import java.util.Set;

/**
 * @author leo
 * @date 2018/9/10 17:01
 */
@Component
public class SsAccountTask {

    private static Logger log = LoggerFactory.getLogger(SsAccountTask.class);

    @Autowired
    private SsService service;
    @Autowired
    private FileUtil fileUtil;

    /**
     * 每分钟定时判断账号是否过期
     * @return
     */
//    @Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "0/5 * * * * ?")
    public void judgeAccount(){

        //查询过期账号
        List<Ss> notExpiredAccount = service.getNotExpiredAccount();
        log.info("每分钟定时判断账号是否过期，过期数量[{}]",notExpiredAccount.size());
        for (Ss ss : notExpiredAccount){

            log.info("账号[]过期，删除",ss.getPort());
            int i = service.delAccountByPort(ss.getPort());
            if(1 == i){
                log.info("账号[]过期，删除成功",ss.getPort());
            }else {
                log.info("账号[]过期，删除失败",ss.getPort());
            }

        }

        //文件中不存在 ， 数据库存在，添加
        log.info("每分钟定时判断 是否存在 文件中不存在，数据库存在 的数据");
        List<Ss> allAccount = service.getAllAccount();
        Set<String> filePorts = fileUtil.getFilePorts();
        for(Ss ss : allAccount){
            Integer port = ss.getPort();
            if(!filePorts.contains(String.valueOf(port))){
                //添加
                log.info("端口[{}]文件中不存在,数据库存在，添加",port);
                service.addAccount(port,ss.getPassword());
            }
        }

    }

}
