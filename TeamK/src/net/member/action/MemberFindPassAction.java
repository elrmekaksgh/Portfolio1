package net.member.action;

import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tq.util.BCrypt;

import net.member.db.MemberDAO;

public class MemberFindPassAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String checknum = "";
		
		MemberDAO mdao = new MemberDAO();
		String pass = mdao.findpass(id, name, email);
		
		if(pass!=""){
			final char[] characters = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F',
					'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
					'Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w'
					,'x','y','z','!','@','#','$','%','^','&','*','(',')'};

			Random random = new Random();

			StringBuffer buf = new StringBuffer(6);

			for (int i = 0; i < 6; i++) {
				buf.append(characters[random.nextInt(characters.length)]);
			}
			checknum = buf.toString();
			String newpass=BCrypt.hashpw(checknum, BCrypt.gensalt(12));
			mdao.passchange(id, newpass);
			String sender = "itwillbs8@itwillbs8.cafe24.com";
			String receiver = email;
			String subject = "[Team K 여행사]비밀번호 조회";

			String content = name+"고객님의  임시 비밀번호는 :  "+ checknum + " 입니다.";

			String server = "smtp.cafe24.com";

			try {
				Properties properties = new Properties();
				properties.put("mail.smtp.host", server);
				Session s = Session.getDefaultInstance(properties, null);
				Message message = new MimeMessage(s);

				Address sender_address = new InternetAddress(sender);
				Address receiver_address = new InternetAddress(receiver);

				message.setHeader("content-type", "text/html;charset=utf-8");
				message.setFrom(sender_address);
				message.addRecipient(Message.RecipientType.TO, receiver_address);
				message.setSubject(subject);
				message.setContent(content, "text/html;charset=utf-8");
				message.setSentDate(new java.util.Date());

				Transport transport = s.getTransport("smtp");
				transport.connect(server, "itwillbs8", "itwillbs8030909");
				transport.sendMessage(message, message.getAllRecipients());
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('이메일로 비밀번호가 발송되었습니다.');");
				out.println("location.href='./MemberLogin.me'");
				out.println("</script>");
				out.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(pass==""){
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('이름,아이디,이메일을 다시 정확하게 입력해주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		return null;
	}

}
