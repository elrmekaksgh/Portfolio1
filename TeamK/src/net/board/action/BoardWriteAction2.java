package net.board.action;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;


public class BoardWriteAction2 implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteAction2 execute()");
		// net.board.db.BoardBean  net.board.db.BoardDAO
		request.setCharacterEncoding("utf-8");
		
		String realPath=request.getRealPath("/upload");
		System.out.println("물리적경로:"+realPath);
		int maxSize=5*1024*1024; //5MB
		MultipartRequest multi = new MultipartRequest(request,realPath,maxSize,"utf-8",new DefaultFileRenamePolicy());
		
	
		BoardBean bb = new BoardBean();
	
		bb.setId(multi.getParameter("id"));
		bb.setSubject(multi.getParameter("subject"));
		bb.setContent(multi.getParameter("content"));
		bb.setType(Integer.parseInt(multi.getParameter("type")));
		bb.setFile1(multi.getFilesystemName("file1"));
		bb.setFile2(multi.getFilesystemName("file2"));
		bb.setFile3(multi.getFilesystemName("file3"));
		bb.setFile4(multi.getFilesystemName("file4"));
		bb.setFile5(multi.getFilesystemName("file5"));
		bb.setEmail(multi.getParameter("email"));
		
		
		BoardDAO bdao = new BoardDAO();
		
		bdao.insertBoard2(bb);
		
		String sub = multi.getParameter("subject");
		String con = multi.getParameter("content");
			
		String email = "itwillbs8@itwillbs8.cafe24.com";//질문 등록시 메일받는사람(관리자)의 이메일 주소
	
		String sender="itwillbs8@itwillbs8.cafe24.com"; //보내는사람 메일 주소(수정X)
		String receiver= email;
		String subject = "새로운 문의가 왔습니다.["+sub+"]";
		
		String content=  "제목 : ["+sub+"] <br> 내용 : ["+con+"]";
		
		String server = "smtp.cafe24.com";
		
		try{
			Properties properties = new Properties();
			properties.put("mail.smtp.host", server);
			Session s = Session.getDefaultInstance(properties, null);
			Message message = new MimeMessage(s);
			
			Address sender_address=new InternetAddress(sender);
			Address receiver_address=new InternetAddress(receiver);
			
			message.setHeader("content-type","text/html;charset=utf-8");
			message.setFrom(sender_address);
			message.addRecipient(Message.RecipientType.TO,receiver_address);
			message.setSubject(subject);
			message.setContent(content,"text/html;charset=utf-8");
			message.setSentDate(new java.util.Date());
			
			Transport transport= s.getTransport("smtp") ;
			transport.connect(server,"itwillbs8","itwillbs8030909");
			transport.sendMessage(message,message.getAllRecipients());
			transport.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ActionForward forward = new ActionForward();
   		forward.setPath("./BoardList2.bo");
   		forward.setRedirect(true);	
		
		return forward;
	}

} 
