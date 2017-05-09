package util;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
 
/**
 * 功能说明：用来存储业务定义的sessionId和连接的对应关系
 * 利用业务逻辑中组装的sessionId获取有效连接后进行后续操作
*/
public class SessionUtils {
 
 public static Map<String, Session> clients = new ConcurrentHashMap<>();
 
 public static void put(Session session){
 clients.put(getKey(), session);
}
 
 public static Session get(){
 return clients.get(getKey());
}
 
 public static void remove(){
 clients.remove(getKey());
}
 
/**
 * 判断是否有连接
 * @param relationId
 * @param userCode
 * @return
*/
 public static boolean hasConnection() {
 return clients.containsKey(getKey());
}
 
/**
 * 组装唯一识别的key
 * @param relationId
 * @param userCode
 * @return
*/
 public static String getKey() {
 return "1";
}
 
}