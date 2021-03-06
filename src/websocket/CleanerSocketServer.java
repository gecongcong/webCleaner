package websocket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


import data.Domain;
import data.Rule;
import data.Tuple;
import tuffy.main.MLNmain;
import util.GetHttpSessionConfigurator;

@ServerEndpoint(value="/webSocket",configurator=GetHttpSessionConfigurator.class)
public class CleanerSocketServer {
	public String[] header = null;
	public static String baseURL = "E:\\experiment\\";
	private HttpSession httpSession;
	private String cleanedFileURL = null;
	private ArrayList<Integer> ignoredIDs = null;
	
    @OnMessage
    public void onMessage(String message, Session session, EndpointConfig config) throws IOException, InterruptedException {
    	
    	boolean cleanResult = false;
        // Print the client message for testing purposes
        System.out.println("Received: " + message);
        
        if(!message.equals("undefined")){
        	// Send the first message to the client
            session.getBasicRemote().sendText(">>> Start Cleaning...");
            
            String[] URLs = message.split(",");
            String rulesURL = URLs[0];
            String datasetURL = URLs[1];
            //String rulesURL = "E:\\experiment\\dataSet\\HAI\\rules.txt";
            //String datasetURL = "E:\\experiment\\dataSet\\HAI\\HAI-11q-10%-error.csv";
            try {
    			HashMap<Integer,String[]> dataSet = startClean(rulesURL, datasetURL, session);
    			cleanResult = true;
    			httpSession.setAttribute("cleanResult", cleanResult);
    			httpSession.setAttribute("header", header);
    			httpSession.setAttribute("dataSet", dataSet);
    			httpSession.setAttribute("cleanedFileURL", cleanedFileURL);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        }
        
    }

    public void close(){
    	this.onClose();
    }
    
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
    	httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println("connect to server");
    }

    @OnClose
    public void onClose() {
        System.out.println("cleaning finished!\nconnection closed");
    }
    
public HashMap<Integer,String[]> startClean(String rulesURL, String dataURL, Session session) throws SQLException, IOException{
		
		double startTime = System.currentTimeMillis();    //获取开始时间
		
		Rule rule = new Rule();
		Domain domain = new Domain();
		String evidence_outFile = baseURL + "dataSet\\HAI\\evidence.db";
		String rootURL = httpSession.getServletContext().getRealPath("out");
		System.out.println("rootURL"+rootURL);
		cleanedFileURL = rootURL+ "\\RDBSCleaner_cleaned.txt";//存放清洗后的数据集
		System.out.println("dataURL = "+dataURL);
		String splitString = ",";
		boolean ifHeader = true;
		
		List<Tuple> rules = rule.loadRules(dataURL, rulesURL, splitString, session);
		
		rule.initData(dataURL, splitString, ifHeader);
		
		rule.formatEvidence(evidence_outFile, session);
		
		ignoredIDs = rule.findIgnoredTuples(rules);
		
		domain.header = rule.header;
		
        header = rule.header;
        
		domain.createMLN(rule.header, rulesURL);
		
		//调用MLN相关的命令参数
		ArrayList<String> list = new ArrayList<String>();
		String marginal_args = "-marginal";
		list.add(marginal_args);
		
		String learnwt_args = "-learnwt";
		//list.add(learnwt_args);
		
		String nopart_args = "-nopart";
//		list.add(nopart_args);
		
		String mln_args = "-i";
		list.add(mln_args);
		
		String mlnFileURL = baseURL+"dataSet\\HAI\\prog.mln";//prog.mln
		list.add(mlnFileURL);
		
		String evidence_args = "-e";
		list.add(evidence_args);
		
		String evidenceFileURL = baseURL+"dataSet\\HAI\\evidence.db"; //samples/smoke/
		list.add(evidenceFileURL);
		
		String queryFile_args = "-queryFile";
		list.add(queryFile_args);
		
		String queryFileURL = baseURL+"dataSet\\HAI\\query.db";
		list.add(queryFileURL);
		
		String outFile_args = "-r";
		list.add(outFile_args);
		
		String weightFileURL = baseURL+"dataSet\\HAI\\out.txt";
		list.add(weightFileURL);
		
		String noDropDB = "-keepData";
		list.add(noDropDB);
		
		String maxIter_args = "-dMaxIter";
		list.add(maxIter_args);
		
		String maxIter = "500";
		list.add(maxIter);

		
		String[] learnwt = list.toArray(new String[list.size()]);
		
		HashMap<String, Double> attributesPROB = MLNmain.main(learnwt,session);	//入口：参数学习 weight learning——using 'Diagonal Newton discriminative learning'

		//打印MLN marginal计算得到的概率
//		Iterator<Entry<String, Double>> iter = attributesPROB.entrySet().iterator(); 
//        while(iter.hasNext()){ 
//            Entry<String, Double> me = iter.next() ; 
//            System.out.println(me.getKey() + " --> " + me.getValue()) ; 
//        }
		
        //区域划分 形成Domains
        domain.init(dataURL, splitString, ifHeader, rules);
        //对每个Domain执行group by key操作
        domain.groupByKey(domain.domains, rules);
      		
        //根据MLN的概率修正错误数据
        domain.correctByMLN(domain.Domain_to_Groups, attributesPROB, domain.header, domain.domains);
        
        //打印修正后的Domain
//        domain.printDomainContent(domain.domains);
        
        System.out.println(">>> Find Duplicate Values...");
        session.getBasicRemote().sendText(">>> Find Duplicate Values...");
        
        List<List<Integer>> keysList = domain.combineDomain(domain.Domain_to_Groups); 	//返回所有重复数组的tupleID,并记录重复元组
        
        //打印重复数据的Tuple ID
        if(null == keysList || keysList.isEmpty())System.out.println("\tNo duplicate exists.");
        else{
          	System.out.println("\n>>> Delete duplicate tuples");
          	session.getBasicRemote().sendText("\n>>> Delete duplicate tuples");
//          	domain.deleteDuplicate(keysList, domain.dataSet);	//执行去重操作
          	System.out.println(">>> completed!");
          	session.getBasicRemote().sendText(">>> completed!");
        }
//      	domain.printDataSet(domain.dataSet);
      	
//      	domain.printConflicts(domain.conflicts);
      	
      	domain.findCandidate(domain.conflicts, domain.Domain_to_Groups, domain.domains, attributesPROB, ignoredIDs);
      	
      	//print dataset after cleaning
//      	domain.printDataSet(domain.dataSet);
      	writeToFile(cleanedFileURL,domain.dataSet, domain.header);
//      	cleanedFileURL = httpSession.getServletContext().getContextPath()+ "/out/cleanedDataSet.data";//修改为相对路径;
      	System.out.println("cleanedDataSet.txt stored in="+cleanedFileURL);
      	double endTime = System.currentTimeMillis();    //获取结束时间
      	
      	double totalTime= (endTime-startTime)/1000;
      	DecimalFormat df = new DecimalFormat("#.00");
      	
      	System.out.println("程序运行时间： "+df.format(totalTime)+"s");
      	
      	session.getBasicRemote().sendText("程序运行时间： "+df.format(totalTime)+"s");
      	session.getBasicRemote().sendText("Finish.");
      	HashMap<Integer,String[]> dataSet = domain.dataSet;
      	return dataSet;
	}

	//写文件
	public void writeToFile(String cleanedFileURL, HashMap<Integer, String[]> dataSet, String[] header){
		File file = new File(cleanedFileURL);
		FileWriter fw = null;
		BufferedWriter writer = null;
      try {
          if (file.exists()) {// 判断文件是否存在
  			System.out.println("文件已存在: " + cleanedFileURL);
  		}
          else if (!file.getParentFile().exists()) {// 判断目标文件所在的目录是否存在
  			// 如果目标文件所在的文件夹不存在，则创建父文件夹
  			System.out.println("目标文件所在目录不存在，准备创建它！");
  			if (!file.getParentFile().mkdirs()) {// 判断创建目录是否成功
  				System.out.println("创建目标文件所在的目录失败！");
  			}
  		}
          else{
          	file.createNewFile();
          }
          fw = new FileWriter(file);
          writer = new BufferedWriter(fw);
          Iterator<Entry<Integer, String[]>> iter = dataSet.entrySet().iterator();
          writer.write(Arrays.toString(header).replaceAll("[\\[\\]]",""));
          writer.newLine();//换行
          while(iter.hasNext()){
          	Entry<Integer, String[]> entry = iter.next();
          	String line = Arrays.toString(entry.getValue()).replaceAll("[\\[\\]]","");
              writer.write(line);
              writer.newLine();//换行
          }
          writer.flush();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }catch (IOException e) {
          e.printStackTrace();
      }finally{
      	
      }
	}
}