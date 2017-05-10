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
		request.setAttribute("rulesURL", rulesURL);
		request.setAttribute("datasetURL", datasetURL);
		request.getRequestDispatcher("clean1.jsp").forward(request,response);
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
