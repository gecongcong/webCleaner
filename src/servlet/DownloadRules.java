package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import websocket.CleanerSocketServer;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/DownloadRules" })
public class DownloadRules extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadRules() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO Auto-generated method stub  
        System.out.println(">>> Download....");
        //��������ļ���  
        HttpSession session = request.getSession();
        String fileURL = (String) request.getSession().getAttribute("rulesURL");
        System.out.println("url = "+fileURL);  
        String filename = "rules.txt";
        //�����ļ�MIME����  
        response.setContentType(getServletContext().getMimeType(filename));  
        //����Content-Disposition  
        response.setHeader("Content-Disposition", "attachment;filename="+filename);  
        //��ȡĿ���ļ���ͨ��response��Ŀ���ļ�д���ͻ���  
        //��ȡĿ���ļ��ľ���·��  
        //String fullFileName = getServletContext().getRealPath("out/" + filename);  
        //System.out.println(fullFileName);  
        //��ȡ�ļ�  
        InputStream in = new FileInputStream(fileURL);  
        OutputStream out = response.getOutputStream();  
          
        //д�ļ�  
        int b;  
        while((b=in.read())!= -1)  
        {  
            out.write(b);  
        }  
          
        in.close();  
        out.close(); 

        session.invalidate();
    }  
  
    /** 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO Auto-generated method stub  
    }

}
