package util;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
 
/**
 * ����˵���������洢ҵ�����sessionId�����ӵĶ�Ӧ��ϵ
 * ����ҵ���߼�����װ��sessionId��ȡ��Ч���Ӻ���к�������
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
 * �ж��Ƿ�������
 * @param relationId
 * @param userCode
 * @return
*/
 public static boolean hasConnection() {
 return clients.containsKey(getKey());
}
 
/**
 * ��װΨһʶ���key
 * @param relationId
 * @param userCode
 * @return
*/
 public static String getKey() {
 return "1";
}
 
}