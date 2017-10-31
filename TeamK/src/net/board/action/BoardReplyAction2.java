package net.board.action;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;
import net.board.db.BoardReplyBean;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class BoardReplyAction2 implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardReplyAction execute()");
		request.setCharacterEncoding("utf-8");
		
		BoardReplyBean rb = new BoardReplyBean();
		BoardBean bb = new BoardBean();
		BoardDAO bdao = new BoardDAO();
		
		String rid = request.getParameter("rId"); //rid에 리플 작성자 아이디 저장
		String rcontent = request.getParameter("rContent"); //rcontent에 리플내용 저장
		System.out.println("아작스 리플내용="+rcontent);
		System.out.println("아작스 작성자="+rid);
		int rNum = Integer.parseInt(request.getParameter("rNum")); //rNum에 글번호 저장
		String pageNum = request.getParameter("pageNum");
		String wEmail = request.getParameter("wEmail"); //wEmail에 글작성자 email주소 저장
		String wContent = request.getParameter("wContent"); //wContent에 글작성자의 글내용 저장
		
		rb.setId(rid);
		rb.setGroup_del(rNum);
		rb.setContent(rcontent);
		bdao.insertReplyBoard(rb); //리플 db서버에 저장
		
		String ts = "답변완료";
		bb.setNum(rNum); //글번호
		bb.setType_select(ts); //"답변완료"
		bdao.updateType_select(rNum,ts); //해당 번호의 글의 type_select 컬럼에 "답변완료" 추가
		
		int rcount = bdao.getBoardReplyCount(rNum);
		List<BoardReplyBean> lrb = bdao.getBoardReplyList(rNum);
		//AJAX 페이지 board2/reply2.jsp 때문에 값 다시한번 저장
		request.setAttribute("lrb", lrb);
		request.setAttribute("rcount", rcount);
		request.setAttribute("rNum", String.valueOf(rNum));
		request.setAttribute("wEmail", wEmail);
		request.setAttribute("wContent", wContent);
		
String email = wEmail;//받는사람의 이메일 주소에 글작성자의 이메일주소 wEmail 넣기
			
		String sender="itwillbs8@itwillbs8.cafe24.com"; // 이메일 발신자
		String receiver= email; //받는사람
		String subject = "답변이 왔습니다."; //메일 제목
	
		String content1=  "문의내용 : ["+wContent+"] <br> 답변내용 : ["+rcontent+"]";
		//메일 내용 content1에  wContent 글작성자의 글내용, rcontent 리플 작성자의 리플내용 넣음.
		
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
			message.setContent(content1,"text/html;charset=utf-8");
			message.setSentDate(new java.util.Date());
			
			Transport transport= s.getTransport("smtp") ;
			transport.connect(server,"itwillbs8","itwillbs8030909");
			transport.sendMessage(message,message.getAllRecipients());
			transport.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		ActionForward forward = new ActionForward();
   		forward.setPath("./board2/reply2.jsp");
   		forward.setRedirect(false);	
		
		return forward;
		
		
	}
}
