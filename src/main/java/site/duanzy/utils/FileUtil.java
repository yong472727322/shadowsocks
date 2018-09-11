package site.duanzy.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Set;

/**
 * 操作文件工具类
 * @author leo
 * @date 2018/9/10 11:55
 */
@Component
public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    @Value("${ss.config.path}")
    private String path;

    /**
     * 获取文件中的端口列表
     */
    public Set<String> getFilePorts(){
        log.info("获取文件[{}]中的端口列表",path);
        Set<String> strings = null;
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(new File(path));
            br  = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = br.readLine())!=null) {
                sb.append(line);
            }
            JSONObject parse = (JSONObject) JSONObject.parse(sb.toString());
            Object port_password = parse.get("port_password");
            JSONObject parse1 = (JSONObject) JSONObject.parse(port_password.toString());
            strings = parse1.keySet();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                if(null != br){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != fr){
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strings;
    }

    /**
     * 删除 账号
     * @param port 端口
     * @return -1：删除失败，0：端口不存在，1：删除成功
     */
    public int delAccount(int port){
        log.info("删除 账号 [{}]",port);
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fr = new FileReader(new File(path));
            br  = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = br.readLine())!=null) {
                sb.append(line);
            }
            JSONObject parse = (JSONObject) JSONObject.parse(sb.toString());
            Object port_password = parse.get("port_password");
            JSONObject parse1 = (JSONObject) JSONObject.parse(port_password.toString());
            //遍历所有的端口，如果不存在则返回
            int i = 0;
            Set<String> strings = parse1.keySet();
            for (String str : strings){
                if(Integer.parseInt(str) == port){
                    i ++;
                }
            }

            if(0 == i){
                log.info("遍历所有的端口，如果不存在则返回 [{}]",port);
                return 0;
            }

            //删除一个端口
            parse1.remove(String.valueOf(port));
            parse.put("port_password",parse1.toJSONString());
            String s = parse.toJSONString();
            String s1 = s.replaceAll("\"\\{\\\\", "{");
            String s2 = s1.replaceAll("\\\\\"}\"", "\"}");
            String s3 = s2.replaceAll("\\\\", "");
            fw = new FileWriter(new File(path));
            bw = new BufferedWriter(fw);
            bw.write(s3);

        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }finally {
            try {
                if(null != bw){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != fw){
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != br){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != fr){
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    /**
     * 添加 账号
     * @param port 端口
     * @param password 密码
     * @return -1：添加失败，0:端口已经存在，1：添加成功
     */
    public int addAccount(int port, String password){
        log.info("添加 账号[{}] 密码[{}]",port,password);
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fr = new FileReader(new File(path));
            br  = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = br.readLine())!=null) {
                sb.append(line);
            }
            JSONObject parse = (JSONObject) JSONObject.parse(sb.toString());
            Object port_password = parse.get("port_password");
            JSONObject parse1 = (JSONObject) JSONObject.parse(port_password.toString());
            //遍历所有的端口，如果存在则不添加
            Set<String> strings = parse1.keySet();
            for (String str : strings){
                if(Integer.parseInt(str) == port){
                    log.info("账号[{}]已经存在",port);
                    return 0;
                }
            }

            //增加一个端口
            parse1.put(String.valueOf(port),password);
            parse.put("port_password",parse1.toJSONString());
            String s = parse.toJSONString();
            String s1 = s.replaceAll("\"\\{\\\\", "{");
            String s2 = s1.replaceAll("\\\\\"}\"", "\"}");
            String s3 = s2.replaceAll("\\\\", "");
            fw = new FileWriter(new File(path));
            bw = new BufferedWriter(fw);
            bw.write(s3);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }finally {
            try {
                if(null != bw){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != fw){
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != br){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != fr){
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }


    /**
     * 执行sh脚本
     * @param exec
     * @return
     */
    public String execShell(String exec){
        Runtime runtime = Runtime.getRuntime();
        String charsetName = "UTF-8";
        String result = null;
        try {
            log.info("exec shell is [{}]",exec);
            Process process = runtime.exec(exec);
            InputStream iStream = process.getInputStream();
            InputStreamReader iSReader = new InputStreamReader(iStream,charsetName);
            BufferedReader bReader = new BufferedReader(iSReader);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = bReader.readLine()) != null) {
                sb.append(line);
            }
            iStream.close();
            iSReader.close();
            bReader.close();
            result  = new String(sb.toString().getBytes(charsetName));
        } catch (Exception e) {
            log.error("exec shell [{}] fail, result is [{}]",exec,e);
        }
        return result;
    }

    /**
     * 添加开放端口
     * @param port
     * @return
     */
    public int addOpenPort(int port){
        String s = execShell(" /root/add-open-port.sh  " + port);
        log.info("添加开放端口 [{}]，结果[{}]",port,s);
        if(s.contains("success")){
            return 1;
        }
        return 0;
    }

    /**
     * 删除开放端口
     * @param port
     * @return
     */
    public int delOpenPort(int port){
        String s = execShell(" /root/del-open-port.sh  " + port);
        log.info("删除开放端口 [{}]，结果[{}]",port,s);
        if(s.contains("success")){
            return 1;
        }
        return 0;
    }

    /**
     * 重启SS
     * @return
     */
    public int restartSs(){
        String s = execShell(" /root/ss-restart.sh " );
        log.info("重启SS，结果[{}]",s);
        if(s.contains("started")){
            return 1;
        }
        return 0;
    }

}
