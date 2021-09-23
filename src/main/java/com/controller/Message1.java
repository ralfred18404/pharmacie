package com.controller;







import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Message1 {
	private final String sender = "gishushuauca@gmail.com";
	private final String password = "fred18404";
	

	public void successMessage(String details,String message) {
		FacesContext ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,details,message));
	}
	public void errorMessage(String details,String message) {
		FacesContext ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,details,message));
	
	}
	
	@SuppressWarnings("unused")
	public boolean validateId(final String id, final String dob) {
		if (!id.isEmpty() || id.length() > 0 || id != null || id != "") {
			if (id.startsWith("1")) {
				if (id.trim().replace(" ", "").length() == 16) {
					String check = id.trim().replace(" ", "").substring(1, 5);
					String val[] = dob.split("/");
					if (check.equals(val[2])) {
						return true;
					} else {
						return false;
					}

				} else {
					return false;
				}
			} else {
				return false;
			}
		} else
			return false;
	}
	
	public boolean validatePhone(String phone) {
		if (phone.length() == 10) {
			if (phone.startsWith("078") || phone.startsWith("072") || phone.startsWith("073")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
//	public boolean sendCode(String message1,Student student) {
//		try {
//			
//
//				StringBuilder stringBuilder = new StringBuilder();
//				stringBuilder.append(
//						"<h1 style=font-weight: bold; color: maroon;><center>ADVENTIST UNIVERSITY OF CENTRAL AFRICA</center></h1></center><br />");
//				stringBuilder.append("Hello ,"  +student.getFirstName()+" "+student.getLastName()  + "<br />");
//			
//				stringBuilder.append("</br>"+message1 + "<br />");
//
//				stringBuilder.append("<br /><br /> Thanks!!");
//				String emailMessage = stringBuilder.toString();
//
//				// Assuming you are sending email through relay.jangosmtp.net
//				String host = "smtp.gmail.com";
//
//				Properties props = new Properties();
//				props.put("mail.smtp.auth", "true");
//				props.put("mail.smtp.starttls.enable", "true");
//				props.put("mail.smtp.host", host);
//				props.put("mail.smtp.port", "587");
//
//				// Get the Session object.
//				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(sender, password);
//					}
//				});
//
//				// Create a default MimeMessage object.
//				MimeMessage message = new MimeMessage(session);
//
//				// Set From: header field of the header.
//				message.setFrom(new InternetAddress(sender));
//
//				// Set To: header field of the header.
//				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(student.getEmail()));
//
//				// Set Subject: header field
//				message.setSubject("AUCA");
//
//				// Now set the actual message
//
//				message.setContent(emailMessage, "text/html");
//				// Send message
//				Transport.send(message);
//				return true;
//			
//		} catch (Exception e) {
//			FacesContext.getCurrentInstance().addMessage(null,
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "."));
//			e.printStackTrace();
//			return false;
//		}
//}
	
	
	public  boolean netIsAvailable() {
	    try {
	        final URL url = new URL("http://www.gmail.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	        conn.getInputStream().close();
	        return true;
	    } catch (IOException e) {
	    	FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "."));
	        return false;
	    }
	}
//	public void sendCodeD(String code,Distributor distributor) {
//		try {
//			
//
//				StringBuilder stringBuilder = new StringBuilder();
//				stringBuilder.append(
//						"<h1 style=font-weight: bold; color: maroon;><center>INYANGE INDUSTRY Ltd</center></h1></center><br />");
//				stringBuilder.append("Hello ,"  +distributor.getFirstName()+" "+distributor.getLastName()  + "<br />");
//				stringBuilder.append(
//						"THE CODE THAT ALLOW YOU TO CREATE NEW ACCOUNT THE SYSTEM IS :<br /><br />");
//				stringBuilder.append("<b>CODE:</b>&nbsp;&nbsp;"+code + "<br />");
//
//				stringBuilder.append("<br /><br /> Thanks!!");
//				String emailMessage = stringBuilder.toString();
//
//				// Assuming you are sending email through relay.jangosmtp.net
//				String host = "smtp.gmail.com";
//
//				Properties props = new Properties();
//				props.put("mail.smtp.auth", "true");
//				props.put("mail.smtp.starttls.enable", "true");
//				props.put("mail.smtp.host", host);
//				props.put("mail.smtp.port", "587");
//
//				// Get the Session object.
//				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(sender, password);
//					}
//				});
//
//				// Create a default MimeMessage object.
//				MimeMessage message = new MimeMessage(session);
//
//				// Set From: header field of the header.
//				message.setFrom(new InternetAddress(sender));
//
//				// Set To: header field of the header.
//				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(distributor.getEmail()));
//
//				// Set Subject: header field
//				message.setSubject("Email Code");
//
//				// Now set the actual message
//
//				message.setContent(emailMessage, "text/html");
//				// Send message
//				Transport.send(message);
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//						"Codes send to your email,please check your email", "."));
//			
//		} catch (Exception e) {
//			FacesContext.getCurrentInstance().addMessage(null,
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "."));
//			e.printStackTrace();
//		}
//}
	public String getSender() {
		return sender;
	}
	public String getPassword() {
		return password;
	}
	
	
}
