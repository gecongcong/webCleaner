package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import data.Domain;
import data.Rule;
import data.Tuple;
import tuffy.main.MLNmain;

@WebServlet("/DataCleanServlet")
public class DataCleanServlet extends HttpServlet {
	private String rulesURL = null;
	private String datasetURL = null;
	private static final long serialVersionUID = 1L;
	private String baseURL = "E:\\experiment\\";
	
    public DataCleanServlet() {super();}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean cleanResult = false;
		getFilesUrl(request,response);
		String currentDIR =  baseURL + "UploadFiles\\";//获得当前工程路径
		System.out.println("STOREING FILE DIR:"+currentDIR);
		
		File dir = new File(currentDIR);
		if (dir.exists()) {// 判断目录是否存在
			System.out.println("目录已存在！");
		}
		if (!currentDIR.endsWith(File.separator)) {// 结尾是否以"/"结束
			currentDIR = currentDIR + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
			System.out.println("创建目录成功！" + currentDIR);
		} else {
			System.out.println("创建目录失败！");
		}
		try {
			startClean(rulesURL, datasetURL, request, response);
			cleanResult = true;
			request.setAttribute("cleanResult", cleanResult);
			request.getRequestDispatcher("loadFile.jsp").forward(request,response);
		} catch (SQLException e) {
			e.printStackTrace();
			cleanResult = false;
			request.setAttribute("cleanResult", cleanResult);
			request.getRequestDispatcher("loadFile.jsp").forward(request,response);
		}
	}
	
	public void startClean(String rulesFile, String dataURL, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		
		double startTime = System.currentTimeMillis();    //获取开始时间
		
		Rule rule = new Rule();
		String evidence_outFile = baseURL + "evidence.db";
		
		String splitString = ",";
		boolean ifHeader = true;
		
		List<Tuple> rules = rule.loadRules(dataURL, rulesFile, splitString);
		
		rule.initData(dataURL, splitString, ifHeader);
		
		rule.formatEvidence(evidence_outFile);
		
		
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
		
		String mlnFileURL = baseURL+"dataSet\\test\\prog.mln";//prog.mln
		list.add(mlnFileURL);
		
		String evidence_args = "-e";
		list.add(evidence_args);
		
		String evidenceFileURL = baseURL+"dataSet\\test\\evidence.db"; //samples/smoke/
		list.add(evidenceFileURL);
		
		String queryFile_args = "-queryFile";
		list.add(queryFile_args);
		
		String queryFileURL = baseURL+"dataSet\\test\\query.db";
		list.add(queryFileURL);
		
		String outFile_args = "-r";
		list.add(outFile_args);
		
		String weightFileURL = baseURL+"dataSet\\test\\out.txt";
		list.add(weightFileURL);
		
		String noDropDB = "-keepData";
		list.add(noDropDB);
		
		String maxIter_args = "-dMaxIter";
		list.add(maxIter_args);
		
		String maxIter = "300";
		list.add(maxIter);

		
		String[] learnwt = list.toArray(new String[list.size()]);
		
		HashMap<String, Double> attributesPROB = MLNmain.main(learnwt);	//入口：参数学习 weight learning——using 'Diagonal Newton discriminative learning'

		//打印MLN marginal计算得到的概率
		Iterator<Entry<String, Double>> iter = attributesPROB.entrySet().iterator(); 
        while(iter.hasNext()){ 
            Entry<String, Double> me = iter.next() ; 
            System.out.println(me.getKey() + " --> " + me.getValue()) ; 
        }
        
        Domain domain = new Domain();
		
		domain.header = rule.header;
        
        //区域划分 形成Domains
        domain.init(dataURL, splitString, ifHeader, rules);
        //对每个Domain执行group by key操作
        domain.groupByKey(domain.domains, rules);
      		
        //根据MLN的概率修正错误数据
        domain.correctByMLN(domain.Domain_to_Groups, attributesPROB, domain.header, domain.domains);
        
        //打印修正后的Domain
        domain.printDomainContent(domain.domains);
        
        System.out.println(">>> Find Duplicate Values...");
        
        List<List<Integer>> keysList = domain.combineDomain(domain.Domain_to_Groups); 	//返回所有重复数组的tupleID,并记录重复元组
        
        System.out.println("\n\tDuplicate keys: ");
        int c=0;
        if(null == keysList || keysList.isEmpty())System.out.println("\tNo duplicate exists.");
        else{
        	for(List<Integer> keyList: keysList){
	        	System.out.print("\tGroup "+(++c)+": ");
	        	if(keyList==null){
	        		System.out.println();
	        		continue;
	        	}
	        	for(int key: keyList){
	        		System.out.print(key+" ");
	        	}
	      		System.out.println();
	      	}
          	System.out.println("\n>>> Delete duplicate tuples");
          	
          	//执行去重操作
          	domain.deleteDuplicate(keysList, domain.dataSet);
          	
          	System.out.println(">>> completed!");
        }
	    
      	//打印删除‘后’的数据集内容
//      	domain.printDataSet(domain.dataSet);
      	
//      	domain.printConflicts(domain.conflicts);
      	
      	domain.findCandidate(domain.conflicts, domain.Domain_to_Groups, domain.domains, attributesPROB);
      	
//      	domain.printDataSet(domain.dataSet);
      	
      	double endTime = System.currentTimeMillis();    //获取结束时间
      	
      	double totalTime= (endTime-startTime)/1000;
      	DecimalFormat df = new DecimalFormat("#.00");
      	
      	System.out.println("程序运行时间： "+df.format(totalTime)+"s"); 
	}

	
	public void getFilesUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//获得磁盘文件条目工厂  
        DiskFileItemFactory factory = new DiskFileItemFactory(); 
        
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室  
        String path = baseURL+"UploadFiles\\";
        factory.setRepository(new File(path));  
        factory.setSizeThreshold(1024*1024); 
        //高水平的API文件上传处理  
        ServletFileUpload upload = new ServletFileUpload(factory); 
        
        try {  
            //可以上传多个文件  
        	List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
        	
//        	for(FileItem item : list){  
//            	//获取表单的属性名字  
//            	String name = item.getFieldName();  
//            	//如果获取的 表单信息是普通的 文本 信息  
//                if(item.isFormField()){                     
//                    //获取用户具体输入的字符串 ，因为表单提交过来的是 字符串类型的  
//                    String value = item.getString();
//                    System.out.println("item.name:"+name);
//                    System.out.println("item.value:"+value);
//                    path += value+"\\";
//                    break;
//                }  
//            }
        	
            for(FileItem item : list){  
            	//获取表单的属性名字  
            	String name = item.getFieldName();  
            	System.out.println("item.name:"+name);
            	
                if(!item.isFormField()){ //是文件          
                	//获取路径名  
                    String value = item.getName();
                    System.out.println("item.value:"+value);
                    
                    if(name.equals("dataset")){
                		datasetURL = path + value;
                	}
                	if(name.equals("rules")){
                		rulesURL = path + value;
                	}
                    
                    int start = value.lastIndexOf("\\");
                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠
                    String filename = value.substring(start+1); 
                    System.out.println("path: "+path);
                    File fileParent = new File(path);
                    if  (!fileParent .exists()  && !fileParent.isDirectory())      
                    {       
                        //System.out.println("//不存在");  
                        fileParent.mkdirs();
                    }
                    File fileChild = new File(path,filename);
                    OutputStream out = new FileOutputStream(fileChild);  
                    InputStream in = item.getInputStream();  
                    int length = 0 ;  
                    byte [] buf = new byte[1024] ;  
                    
                    System.out.println("获取上传文件的总共的容量："+item.getSize());  
  
                    // in.read(buf) 每次读到的数据存放在   buf 数组中  
                    while( (length = in.read(buf) ) != -1){  
                        //在   buf 数组中 取出数据 写到 （输出流）磁盘上  
                        out.write(buf, 0, length);  
                    }
                    in.close();  
                    out.close();
                }  
            }
        }catch (FileUploadException e) {  
        	e.printStackTrace();
	    }  
	    catch (Exception e) {           
	        e.printStackTrace();  
	    }
	}
}
