//package site.duanzy;
//
//import com.alibaba.fastjson.JSONObject;
//import org.junit.Test;
//
//import java.io.*;
//import java.util.Set;
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
//public class ShadowsocksApplicationTests {
//
//	private static final String PATH = "D:\\Users\\dzy\\Desktop\\ss.txt";
//
//	public void readFile(String path) throws Exception {
//		FileReader fr = new FileReader(new File(path));
//
//		BufferedReader br  = new BufferedReader(fr);
//
//		StringBuffer sb = new StringBuffer();
//
//
//		String line = null;
//		while((line = br.readLine())!=null) {
//			sb.append(line);
//		}
//
//		JSONObject parse = (JSONObject) JSONObject.parse(sb.toString());
//		Object port_password = parse.get("port_password");
////		System.out.println(port_password);
//
//		JSONObject parse1 = (JSONObject) JSONObject.parse(port_password.toString());
//		Set<String> strings = parse1.keySet();
//		for (String str : strings){
//			System.out.println(str+"-->"+parse1.get(str));
//		}
//
//
//
//	}
//
//	@Test
//	public void test() throws Exception {
//		String path = "D:\\Users\\dzy\\Desktop\\utils.txt";
//		readFile(path);
//	}
//
//	@Test
//	public void write() throws Exception {
////		{
////			"server":"198.58.126.56",
////				"local_port":1080,
////				"port_password":{
////			"1035":"password1035",
////					"1036":"password",
////					"1037":"your_passwd",
////					"1038":"password1038",
////					"1039":"password1039",
////					"1040":"password1040"
////		},
////			"timeout":600,
////				"method":"aes-256-cfb"
////		}
//
//		String path = "D:\\Users\\dzy\\Desktop\\utils.txt";
//
//		FileReader fr = new FileReader(new File(path));
//
//		BufferedReader br  = new BufferedReader(fr);
//
//		StringBuffer sb = new StringBuffer();
//
//
//		String line = null;
//		while((line = br.readLine())!=null) {
//			sb.append(line);
//		}
//
//		JSONObject parse = (JSONObject) JSONObject.parse(sb.toString());
//		Object port_password = parse.get("port_password");
////		System.out.println(port_password);
//
//		JSONObject parse1 = (JSONObject) JSONObject.parse(port_password.toString());
//		Set<String> strings = parse1.keySet();
//		for (String str : strings){
//			System.out.println(str+"-->"+parse1.get(str));
//		}
//
//		//增加一个端口
//
//		parse1.put("1041","password1041");
//
//		parse.put("port_password",parse1.toJSONString());
//
//		System.out.println(parse.toJSONString());
//
//		String s = parse.toJSONString();
//		String s1 = s.replaceAll("\\\\", "");
//		System.out.println(s1);
//	}
//
//	@Test
//	public void test2() throws Exception {
//		int pass8080 = addAccount(8080, "pass8080");
//		System.out.println(pass8080);
//		pass8080 = addAccount(9090, "pass9090");
//		System.out.println(pass8080);
//		pass8080 = addAccount(9090, "pass9090");
//		System.out.println(pass8080);
//
//	}
//
//	@Test
//    public void test3(){
//        int i = delAccount(9090);
//        System.out.println(i);
//        i = delAccount(1035);
//        System.out.println(i);
//        i = delAccount(1035);
//        System.out.println(i);
//    }
//
//
//
//
//    /**
//     * 删除 账号
//     * @param port 端口
//     * @return -1：删除失败，0：端口不存在，1：删除成功
//     */
//    public int delAccount(int port){
//        String path = PATH;
//        FileReader fr = null;
//        BufferedReader br = null;
//        FileWriter fw = null;
//        BufferedWriter bw = null;
//        try{
//            fr = new FileReader(new File(path));
//            br  = new BufferedReader(fr);
//            StringBuffer sb = new StringBuffer();
//            String line = null;
//            while((line = br.readLine())!=null) {
//                sb.append(line);
//            }
//            JSONObject parse = (JSONObject) JSONObject.parse(sb.toString());
//            Object port_password = parse.get("port_password");
//            JSONObject parse1 = (JSONObject) JSONObject.parse(port_password.toString());
//            //遍历所有的端口，如果不存在则返回
//            int i = 0;
//            Set<String> strings = parse1.keySet();
//            for (String str : strings){
//                if(Integer.parseInt(str) == port){
//                    i ++;
//                }
//            }
//
//            if(0 == i){
//                return 0;
//            }
//
//            //删除一个端口
//            parse1.remove(String.valueOf(port));
//            parse.put("port_password",parse1.toJSONString());
//            String s = parse.toJSONString();
//            String s1 = s.replaceAll("\"\\{\\\\", "{");
//            String s2 = s1.replaceAll("\\\\\"}\"", "\"}");
//            String s3 = s2.replaceAll("\\\\", "");
//            fw = new FileWriter(new File(path));
//            bw = new BufferedWriter(fw);
//            bw.write(s3);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }finally {
//            try {
//                if(null != bw){
//                    bw.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if(null != bw){
//                    fw.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if(null != bw){
//                    br.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if(null != bw){
//                    fr.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return 1;
//    }
//
//
//	/**
//	 * 添加 账号
//	 * @param port 端口
//	 * @param password 密码
//	 * @return -1：添加失败，0:端口已经存在，1：添加成功
//	 */
//	public int addAccount(int port, String password){
//		String path = PATH;
//		try{
//			FileReader fr = new FileReader(new File(path));
//			BufferedReader br  = new BufferedReader(fr);
//			StringBuffer sb = new StringBuffer();
//			String line = null;
//			while((line = br.readLine())!=null) {
//				sb.append(line);
//			}
//			JSONObject parse = (JSONObject) JSONObject.parse(sb.toString());
//			Object port_password = parse.get("port_password");
//			JSONObject parse1 = (JSONObject) JSONObject.parse(port_password.toString());
//			//遍历所有的端口，如果存在则不添加
//			Set<String> strings = parse1.keySet();
//			for (String str : strings){
//				if(Integer.parseInt(str) == port){
//					return 0;
//				}
//			}
//
//
//			//增加一个端口
//			parse1.put(String.valueOf(port),password);
//			parse.put("port_password",parse1.toJSONString());
//			String s = parse.toJSONString();
//			String s1 = s.replaceAll("\"\\{\\\\", "{");
//			String s2 = s1.replaceAll("\\\\\"}\"", "\"}");
//			String s3 = s2.replaceAll("\\\\", "");
//			FileWriter fw = new FileWriter(new File(path));
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(s3);
//			bw.close();
//			fw.close();
//			br.close();
//			fr.close();
//		}catch (Exception e){
//			e.printStackTrace();
//			return -1;
//		}
//		return 1;
//	}
//
//}
