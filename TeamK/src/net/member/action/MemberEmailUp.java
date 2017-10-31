package net.member.action;

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

public class MemberEmailUp implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		System.out.println("Teamk MemberEmail execute()");
		String email = request.getParameter("email");
		String echeck = request.getParameter("echeck");

		String checknum = "";

		if (echeck.equals("0")) {
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

			String sender = "itwillbs8@itwillbs8.cafe24.com";
			String receiver = email;
			String subject = "[Team K 여행사]인증 번호";

			String content = "인증번호 : " + checknum ;

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
				transport.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("checknum", checknum);
		ActionForward forward = new ActionForward();
		forward.setPath("./member/emailcheck2.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
