package site.duanzy;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import site.duanzy.entity.Ss;
import site.duanzy.service.SsService;
import site.duanzy.utils.FileUtil;

import java.util.List;
import java.util.Set;

@EnableScheduling
@SpringBootApplication
@MapperScan({"site.duanzy.mapper"})
public class ShadowsocksApplication implements CommandLineRunner{

	private static Logger log = LoggerFactory.getLogger(ShadowsocksApplication.class);

	@Autowired
	private SsService service;
	@Autowired
	private FileUtil fileUtil;

	public static void main(String[] args) {
		SpringApplication.run(ShadowsocksApplication.class, args);
	}

	/**
	 * 项目一启动的时间，先读取 ss的配置文件，和数据库中的数据进行比对
	 * 保证数据库和配置文件一致
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(String... args) throws Exception {
		List<Ss> allAccount = service.getAllAccount();

		Set<String> filePorts = fileUtil.getFilePorts();

		log.info("初始化 判断 是否存在 文件[{}]中存在，数据库[{}]不存在 的数据",filePorts.size(),allAccount.size());
		//文件中存在 ，数据库不存在，直接删除
		for(String str : filePorts){
			int i1 = Integer.parseInt(str);
			int i = 0;
			for (Ss ss : allAccount){
				if(ss.getPort() == i1){
					i ++;
					break;
				}
			}
			if(0 == i){
				log.info("初始化 端口[{}]文件中存在,数据库不存在，直接删除",str);
				service.delConfigAccount(i1);
			}
		}

		//文件中不存在 ， 数据库存在，添加
		log.info("初始化 判断 是否存在 文件中不存在，数据库存在 的数据");
		for(Ss ss : allAccount){
			Integer port = ss.getPort();
			if(!filePorts.contains(String.valueOf(port))){
				//添加
				log.info("初始化 端口[{}]文件中不存在,数据库存在，添加",port);
				service.addAccount(port,ss.getPassword());
			}
		}

	}
}
