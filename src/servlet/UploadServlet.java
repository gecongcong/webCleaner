package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String baseURL = "E:\\experiment\\";
	private String rulesURL = null;
	private String datasetURL = null;
	
    
    public UploadServlet() { super();}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		request.setAttribute("rulesURL", rulesURL);
		request.setAttribute("datasetURL", datasetURL);
		request.getRequestDispatcher("clean1.jsp").forward(request,response);
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
            	//��ȡ������������  
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
