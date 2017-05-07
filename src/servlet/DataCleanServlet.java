package servlet;

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
	private String baseURL = "D:\\experiment\\";
	
    public DataCleanServlet() {super();}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean cleanResult = false;
		getFilesUrl(request,response);
		String currentDIR =  baseURL + "UploadFiles\\";//��õ�ǰ����·��
		System.out.println("STOREING FILE DIR:"+currentDIR);
		
		File dir = new File(currentDIR);
		if (dir.exists()) {// �ж�Ŀ¼�Ƿ����
			System.out.println("Ŀ¼�Ѵ��ڣ�");
		}
		if (!currentDIR.endsWith(File.separator)) {// ��β�Ƿ���"/"����
			currentDIR = currentDIR + File.separator;
		}
		if (dir.mkdirs()) {// ����Ŀ��Ŀ¼
			System.out.println("����Ŀ¼�ɹ���" + currentDIR);
		} else {
			System.out.println("����Ŀ¼ʧ�ܣ�");
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
//		List<Integer> ignoredIDs = new ArrayList<Integer>();
		double startTime = System.currentTimeMillis();    //��ȡ��ʼʱ��
		
		Rule rule = new Rule();
		String evidence_outFile = baseURL + "dataSet\\HAI\\evidence.db";
		String cleanedFileURL = baseURL+ "cleanedDataSet.txt";//�����ϴ������ݼ�
		
		String splitString = ",";
		boolean ifHeader = true;
		
		List<Tuple> rules = rule.loadRules(dataURL, rulesFile, splitString);
		
		rule.initData(dataURL, splitString, ifHeader);
		
		rule.formatEvidence(evidence_outFile);
		
//		ignoredIDs = rule.findIgnoredTuples(rules);
		
		//����MLN��ص��������
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
		
		String maxIter = "300";
		list.add(maxIter);

		
		String[] learnwt = list.toArray(new String[list.size()]);
		
		HashMap<String, Double> attributesPROB = MLNmain.main(learnwt);	//��ڣ�����ѧϰ weight learning����using 'Diagonal Newton discriminative learning'

		//��ӡMLN marginal����õ��ĸ���
		Iterator<Entry<String, Double>> iter = attributesPROB.entrySet().iterator(); 
        while(iter.hasNext()){ 
            Entry<String, Double> me = iter.next() ; 
            System.out.println(me.getKey() + " --> " + me.getValue()) ; 
        }
        
        Domain domain = new Domain();
		
		domain.header = rule.header;
        
        //���򻮷� �γ�Domains
        domain.init(dataURL, splitString, ifHeader, rules);
        //��ÿ��Domainִ��group by key����
        domain.groupByKey(domain.domains, rules);
      		
        //����MLN�ĸ���������������
        domain.correctByMLN(domain.Domain_to_Groups, attributesPROB, domain.header, domain.domains);
        
        //��ӡ�������Domain
        domain.printDomainContent(domain.domains);
        
        System.out.println(">>> Find Duplicate Values...");
        
        List<List<Integer>> keysList = domain.combineDomain(domain.Domain_to_Groups); 	//���������ظ������tupleID,����¼�ظ�Ԫ��
        
        //��ӡ�ظ����ݵ�Tuple ID
        System.out.println("\n\tDuplicate keys: ");
        if(null == keysList || keysList.isEmpty())System.out.println("\tNo duplicate exists.");
        else{
          	System.out.println("\n>>> Delete duplicate tuples");
          	domain.deleteDuplicate(keysList, domain.dataSet);	//ִ��ȥ�ز���
          	System.out.println(">>> completed!");
        }
//      	domain.printDataSet(domain.dataSet);
      	
//      	domain.printConflicts(domain.conflicts);
      	
      	domain.findCandidate(domain.conflicts, domain.Domain_to_Groups, domain.domains, attributesPROB);
      	//print dataset after cleaning
      	domain.printDataSet(domain.dataSet);
      	writeToFile(cleanedFileURL,domain.dataSet, domain.header);
      	double endTime = System.currentTimeMillis();    //��ȡ����ʱ��
      	
      	double totalTime= (endTime-startTime)/1000;
      	DecimalFormat df = new DecimalFormat("#.00");
      	
      	System.out.println("��������ʱ�䣺 "+df.format(totalTime)+"s"); 
	}
	
	//д�ļ�
	public void writeToFile(String cleanedFileURL, HashMap<Integer, String[]> dataSet, String[] header){
		File file = new File(cleanedFileURL);
		FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file);
            if (file.exists()) {// �ж��ļ��Ƿ����
    			System.out.println("�ļ��Ѵ���: " + cleanedFileURL);
    		}
            else if (!file.getParentFile().exists()) {// �ж�Ŀ���ļ����ڵ�Ŀ¼�Ƿ����
    			// ���Ŀ���ļ����ڵ��ļ��в����ڣ��򴴽����ļ���
    			System.out.println("Ŀ���ļ�����Ŀ¼�����ڣ�׼����������");
    			if (!file.getParentFile().mkdirs()) {// �жϴ���Ŀ¼�Ƿ�ɹ�
    				System.out.println("����Ŀ���ļ����ڵ�Ŀ¼ʧ�ܣ�");
    			}
    		}
            else{
            	file.createNewFile();
            }
            writer = new BufferedWriter(fw);
            Iterator<Entry<Integer, String[]>> iter = dataSet.entrySet().iterator();
            writer.write(Arrays.toString(header).replaceAll("[\\[\\]]",""));
            writer.newLine();//����
            while(iter.hasNext()){
            	Entry<Integer, String[]> entry = iter.next();
            	String line = Arrays.toString(entry.getValue()).replaceAll("[\\[\\]]","");
            	System.out.println(line);
                writer.write(line);
                writer.newLine();//����
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
        	
        }
	}
	
	public void getFilesUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//��ô����ļ���Ŀ����  
        DiskFileItemFactory factory = new DiskFileItemFactory(); 
        
        //���� ����Ĵ�С�����ϴ��ļ������������û���ʱ��ֱ�ӷŵ� ��ʱ�洢��  
        String path = baseURL+"UploadFiles\\";
        factory.setRepository(new File(path));  
        factory.setSizeThreshold(1024*1024); 
        //��ˮƽ��API�ļ��ϴ�����  
        ServletFileUpload upload = new ServletFileUpload(factory); 
        
        try {  
            //�����ϴ�����ļ�  
        	List<FileItem> list = (List<FileItem>)upload.parseRequest(request);

            for(FileItem item : list){  
            	//��ȡ��������������  
            	String name = item.getFieldName();  
            	System.out.println("item.name:"+name);
            	
                if(!item.isFormField()){ //���ļ�          
                	//��ȡ·����  
                    String value = item.getName();
                    System.out.println("item.value:"+value);
                    
                    if(name.equals("dataset")){
                		datasetURL = path + value;
                	}
                	if(name.equals("rules")){
                		rulesURL = path + value;
                	}
                    
                    int start = value.lastIndexOf("\\");
                    //��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б��
                    String filename = value.substring(start+1); 
                    System.out.println("path: "+path);
                    File fileParent = new File(path);
                    if  (!fileParent .exists()  && !fileParent.isDirectory())      
                    {       
                        //System.out.println("//������");  
                        fileParent.mkdirs();
                    }
                    File fileChild = new File(path,filename);
                    OutputStream out = new FileOutputStream(fileChild);  
                    InputStream in = item.getInputStream();  
                    int length = 0 ;  
                    byte [] buf = new byte[1024] ;  
                    
                    System.out.println("��ȡ�ϴ��ļ����ܹ���������"+item.getSize());  
  
                    // in.read(buf) ÿ�ζ��������ݴ����   buf ������  
                    while( (length = in.read(buf) ) != -1){  
                        //��   buf ������ ȡ������ д�� ���������������  
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