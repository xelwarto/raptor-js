package org.jdog.Raptor.js.mods.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Message.RecipientType;
import javax.mail.util.ByteArrayDataSource;

public class SendMessage {

	private String messageFrom = null;
	private String messageSubject = null;
	private String smtpServer = null;
	private String smtpPort = "25";
	private String smtpUser = null;
	private String smtpPassword = null;
	private Date messageDate = null;
	private boolean debug = false;
	private boolean sendSingle = false;
	private List<String> errors = new ArrayList<String>();
	private List<HashMap<String, Object>> messageRecipients = new ArrayList<HashMap<String, Object>>();
	private String defaultMimeType = "application/octet-stream";
	private List<BodyPart> bodyParts = new ArrayList<BodyPart>();
	private List<BodyPart> fileParts = new ArrayList<BodyPart>();

	public SendMessage() {
	}

	public SendMessage(String smtpServer) {
		if (smtpServer != null) {
			this.smtpServer = smtpServer;
		}
	}

	public SendMessage(String smtpServer, String smtpUser, String smtpPassword) {
		if (smtpServer != null) {
			this.smtpServer = smtpServer;
			this.smtpUser = smtpUser;
			this.smtpPassword = smtpPassword;
		}
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		if (messageDate != null) {
			this.messageDate = messageDate;
		}
	}

	public String getError() {
		return errors.toString();
	}

	public List<String> getErrors() {
		return errors;
	}

	public boolean hasError() {
		if (errors != null && !errors.isEmpty()) {
			return true;
		}
		return false;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		if (smtpPort != null) {
			this.smtpPort = smtpPort;
		}
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		if (smtpServer != null) {
			this.smtpServer = smtpServer;
		}
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		if (smtpPassword != null) {
			this.smtpPassword = smtpPassword;
		}
	}

	public String getSmtpUser() {
		return smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		if (smtpUser != null) {
			this.smtpUser = smtpUser;
		}
	}

	public void addMessageTo(String address) {
		if (address != null && !address.equals("")) {
			address = removeReturns(address);
			messageRecipients.add(_addRecipient(RecipientType.TO, address));
		}
	}

	public void setMessageTo(String addressList) {
		if (addressList != null && !addressList.equals("")) {
			addressList = removeReturns(addressList);
			String[] addresses = addressList.split(",");
			if (addresses != null) {
				for (int i = 0; i < addresses.length; i++) {
					messageRecipients.add(_addRecipient(RecipientType.TO,
							addresses[i]));
				}
			}
		}
	}

	public void setMessageTo(String[] addresses) {
		if (addresses != null && addresses.length > 0) {
			for (int i = 0; i < addresses.length; i++) {
				messageRecipients.add(_addRecipient(RecipientType.TO,
						addresses[i]));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void setMessageTo(List addressList) {
		if (addressList != null && !addressList.isEmpty()) {
			Iterator it = addressList.iterator();
			while (it.hasNext()) {
				String address = (String) it.next();
				messageRecipients.add(_addRecipient(RecipientType.TO, address));
			}
		}
	}

	public void addMessageCC(String address) {
		if (address != null && !address.equals("")) {
			address = removeReturns(address);
			messageRecipients.add(_addRecipient(RecipientType.CC, address));
		}
	}

	public void setMessageCC(String addressList) {
		if (addressList != null && !addressList.equals("")) {
			addressList = removeReturns(addressList);
			String[] addresses = addressList.split(",");
			if (addresses != null) {
				for (int i = 0; i < addresses.length; i++) {
					messageRecipients.add(_addRecipient(RecipientType.CC,
							addresses[i]));
				}
			}
		}
	}

	public void setMessageCC(String[] addresses) {
		if (addresses != null && addresses.length > 0) {
			for (int i = 0; i < addresses.length; i++) {
				messageRecipients.add(_addRecipient(RecipientType.CC,
						addresses[i]));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void setMessageCC(List addressList) {
		if (addressList != null && !addressList.isEmpty()) {
			Iterator it = addressList.iterator();
			while (it.hasNext()) {
				String address = (String) it.next();
				messageRecipients.add(_addRecipient(RecipientType.CC, address));
			}
		}
	}

	public void addMessageBCC(String address) {
		if (address != null && !address.equals("")) {
			address = removeReturns(address);
			messageRecipients.add(_addRecipient(RecipientType.BCC, address));
		}
	}

	public void setMessageBCC(String addressList) {
		if (addressList != null && !addressList.equals("")) {
			addressList = removeReturns(addressList);
			String[] addresses = addressList.split(",");
			if (addresses != null) {
				for (int i = 0; i < addresses.length; i++) {
					messageRecipients.add(_addRecipient(RecipientType.BCC,
							addresses[i]));
				}
			}
		}
	}

	public void setMessageBCC(String[] addresses) {
		if (addresses != null && addresses.length > 0) {
			for (int i = 0; i < addresses.length; i++) {
				messageRecipients.add(_addRecipient(RecipientType.BCC,
						addresses[i]));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void setMessageBCC(List addressList) {
		if (addressList != null && !addressList.isEmpty()) {
			Iterator it = addressList.iterator();
			while (it.hasNext()) {
				String address = (String) it.next();
				messageRecipients
						.add(_addRecipient(RecipientType.BCC, address));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public List<String> getMessageTo() {
		List<String> addresses = new ArrayList<String>();
		Iterator it = messageRecipients.iterator();
		while (it.hasNext()) {
			HashMap recipient = (HashMap) it.next();
			String address = (String) recipient.get("ADDR");
			RecipientType type = (RecipientType) recipient.get("TYPE");

			if (address != null && type != null) {
				if (type.equals(RecipientType.TO)) {
					addresses.add(address);
				}
			}
		}
		return addresses;
	}

	@SuppressWarnings("rawtypes")
	public List<String> getMessageCC() {
		List<String> addresses = new ArrayList<String>();
		Iterator it = messageRecipients.iterator();
		while (it.hasNext()) {
			HashMap recipient = (HashMap) it.next();
			String address = (String) recipient.get("ADDR");
			RecipientType type = (RecipientType) recipient.get("TYPE");

			if (address != null && type != null) {
				if (type.equals(RecipientType.CC)) {
					addresses.add(address);
				}
			}
		}
		return addresses;
	}

	@SuppressWarnings("rawtypes")
	public List<String> getMessageBCC() {
		List<String> addresses = new ArrayList<String>();
		Iterator it = messageRecipients.iterator();
		while (it.hasNext()) {
			HashMap recipient = (HashMap) it.next();
			String address = (String) recipient.get("ADDR");
			RecipientType type = (RecipientType) recipient.get("TYPE");

			if (address != null && type != null) {
				if (type.equals(RecipientType.BCC)) {
					addresses.add(address);
				}
			}
		}
		return addresses;
	}

	public void setMessageFrom(String messageFrom) {
		if (messageFrom != null) {
			messageFrom = removeReturns(messageFrom);
			this.messageFrom = messageFrom;
		}
	}

	public String getMessageFrom() {
		return messageFrom;
	}

	public void setMessageSubject(String messageSubject) {
		if (messageSubject != null) {
			messageSubject = removeReturns(messageSubject);
			this.messageSubject = messageSubject;
		}
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setDebug() {
		debug = true;
	}

	public void sendSingle() {
		sendSingle = true;
	}

	public void sendMulti() {
		sendSingle = false;
	}

	public void addTxtBody(String msgBody) {
		if (msgBody != null) {
			addBodyPart(msgBody, "text/plain; charset=UTF-8");
		}
	}

	public void addHtmlBody(String msgBody) {
		if (msgBody != null) {
			addBodyPart(msgBody, "text/html; charset=UTF-8");
		}
	}

	public void addBodyPart(String msgBody, String msgType) {
		if (msgBody != null && msgType != null) {
			try {
				BodyPart bodyPart = new MimeBodyPart();
				bodyPart.setContent(msgBody, msgType);
				addBodyPart(bodyPart);
			} catch (MessagingException e) {
				errors.add("Add Body Part - " + e.toString());
			}
		}
	}

	public void addBodyPart(BodyPart bodyPart) {
		if (bodyPart != null) {
			bodyParts.add(bodyPart);
		}
	}

	public void addFile(String filePath) {
		if (filePath != null) {
			File file = new File(filePath);
			addFile(file);
		} else {
			errors.add("Add File - null pointer exception");
		}
	}

	public void addFile(String fileDir, String fileName) {
		if (fileDir != null && fileName != null) {
			File file = new File(fileDir, fileName);
			addFile(file);
		} else {
			errors.add("Add File - null pointer exception");
		}
	}

	public void addFile(File file) {
		if (file != null) {
			if (file.exists() && file.isFile()) {
				try {
					BodyPart filePart = new MimeBodyPart();
					DataSource fileSource = new FileDataSource(file);
					filePart.setDataHandler(new DataHandler(fileSource));
					filePart.setFileName(file.getName());
					fileParts.add(filePart);
				} catch (MessagingException e) {
					errors.add("Add File - " + e.toString());
				}
			} else {
				errors.add("Add File - File object does not exist or is not a file");
			}
		} else {
			errors.add("Add File - null pointer exception");
		}
	}

	public void addFile(InputStream input, String fileName) {
		addFile(input, fileName, null);
	}

	public void addFile(InputStream input, String fileName, String mimeType) {
		if (input != null && fileName != null) {
			if (mimeType == null || mimeType.equals("")) {
				mimeType = defaultMimeType;
			}
			try {
				BodyPart filePart = new MimeBodyPart();
				DataSource ds = new ByteArrayDataSource(input, mimeType);
				filePart.setDataHandler(new DataHandler(ds));
				filePart.setFileName(fileName);
				fileParts.add(filePart);
			} catch (Exception e) {
				errors.add("Add File - " + e.toString());
			}
		} else {
			errors.add("Add File - null pointer exception");
		}
	}

	public void sendMessage() {
		if (messageFrom == null || messageFrom.equals("")) {
			errors.add("Missing message from email address");
		}

		if (messageSubject == null || messageSubject.equals("")) {
			errors.add("Missing message subject");
		}

		if (messageRecipients == null || messageRecipients.size() == 0) {
			errors.add("No recipient addresses found");
		}

		if (smtpPort == null || smtpPort.equals("")) {
			errors.add("Missing SMTP port");
		}

		if (sendSingle) {
			if (smtpServer == null) {
				errors.add("Missing SMTP server host");
			}
		}

		if (errors == null || errors.size() == 0) {
			_sendMessage();
		}
	}

	private HashMap<String, Object> _addRecipient(RecipientType type,
			String address) {
		HashMap<String, Object> recipient = new HashMap<String, Object>();
		recipient.put("ADDR", address);
		recipient.put("TYPE", type);
		return recipient;
	}

	private String findSmtpServer(String to) {
		String smtpHost = "";
		try {
			String[] emailParts = new String[2];
			emailParts = to.split("@");

			smtpHost = MXHandler.findMX(emailParts[1]);
		} catch (Exception e) {
			errors.add("Unable to find smtp server for " + to + ": "
					+ e.toString());
		}
		return smtpHost;
	}

	private Session getSession(String server) {
		Session session = null;
		Properties props = new Properties();
		props.put("mail.smtp.host", server);
		props.put("mail.smtp.port", smtpPort);

		if (smtpUser != null && smtpPassword != null) {
			SmtpAuthenticator auth = new SmtpAuthenticator();
			auth.setUser(smtpUser);
			auth.setPassword(smtpPassword);
			if (auth != null) {
				props.put("mail.smtp.auth", "true");
				session = Session.getInstance(props, auth);
			}
		} else {
			session = Session.getInstance(props);
		}

		if (debug) {
			session.setDebug(true);
		}
		return session;
	}

	@SuppressWarnings("rawtypes")
	private void _sendMessage() {
		if (sendSingle) {
			Session session = getSession(smtpServer);
			if (session != null) {
				MimeMessage msg = null;
				try {
					msg = new MimeMessage(session);
				} catch (Exception e) {
					errors.add("Create MimeMessage - " + e.toString());
					msg = null;
				}

				if (msg != null) {
					try {
						Iterator it = messageRecipients.iterator();
						while (it.hasNext()) {
							HashMap recipient = (HashMap) it.next();
							String address = (String) recipient.get("ADDR");
							RecipientType type = (RecipientType) recipient
									.get("TYPE");

							if (address != null && type != null) {
								InternetAddress iAddress = new InternetAddress(
										address);
								if (iAddress != null) {
									msg.addRecipient(type, iAddress);
								}
							}
						}
					} catch (MessagingException e) {
						errors.add("Add Msg Recipirnts - " + e.toString());
					}

					try {
						InternetAddress mesgFrom = new InternetAddress(
								messageFrom);
						if (mesgFrom != null) {
							msg.setFrom(mesgFrom);
						}
					} catch (MessagingException e) {
						errors.add("Add Msg From - " + e.toString());
					}

					try {
						if (getMessageDate() != null) {
							msg.setSentDate(getMessageDate());
						}
					} catch (MessagingException e) {
						errors.add("Add Msg Date - " + e.toString());
					}

					try {
						msg.setSubject(messageSubject);
					} catch (MessagingException e) {
						errors.add("Add Msg Subject - " + e.toString());
					}
					try {
						msg.setContent(getMsgContent());
					} catch (MessagingException e) {
						errors.add("Add Msg Content - " + e.toString());
					}

					if (errors == null || errors.size() == 0) {
						try {
							Transport.send(msg);
						} catch (MessagingException e) {
							errors.add("Send Msg - " + e.toString());
						}
					}
				}
			} else {
				errors.add("Unable to create new message session");
			}
		} else {
			Iterator it = messageRecipients.iterator();
			while (it.hasNext()) {
				HashMap recipient = (HashMap) it.next();
				String address = (String) recipient.get("ADDR");
				RecipientType type = (RecipientType) recipient.get("TYPE");

				if (address != null && type != null) {
					String smtpHost = "";
					if (smtpServer == null || smtpServer.equals("")) {
						smtpHost = findSmtpServer(address);
					} else {
						smtpHost = smtpServer;
					}

					if (smtpHost != null && !smtpHost.equals("")) {
						Session session = getSession(smtpHost);
						if (session != null) {
							MimeMessage msg = null;
							try {
								msg = new MimeMessage(session);
							} catch (Exception e) {
								errors.add("Create MimeMessage - "
										+ e.toString());
								msg = null;
							}

							if (msg != null) {
								try {
									InternetAddress iAddress = new InternetAddress(
											address);
									if (iAddress != null) {
										msg.addRecipient(type, iAddress);
									}
								} catch (MessagingException e) {
									errors.add("Add Msg Recipirnt - "
											+ e.toString());
								}

								try {
									InternetAddress mesgFrom = new InternetAddress(
											messageFrom);
									if (mesgFrom != null) {
										msg.setFrom(mesgFrom);
									}
								} catch (MessagingException e) {
									errors.add("Add Msg From - " + e.toString());
								}

								try {
									if (getMessageDate() != null) {
										msg.setSentDate(getMessageDate());
									}
								} catch (MessagingException e) {
									errors.add("Add Msg Date - " + e.toString());
								}

								try {
									msg.setSubject(messageSubject);
								} catch (MessagingException e) {
									errors.add("Add Msg Subject - "
											+ e.toString());
								}
								try {
									msg.setContent(getMsgContent());
								} catch (MessagingException e) {
									errors.add("Add Msg Content - "
											+ e.toString());
								}

								if (errors == null || errors.size() == 0) {
									try {
										Transport.send(msg);
									} catch (MessagingException e) {
										errors.add("Send Msg - " + e.toString());
									}
								}
							}
						} else {
							errors.add("Unable to create new message session");
						}
					} else {
						errors.add("No smtp server found for " + address);
					}
				}
			}
		}
	}

	private MimeMultipart getMsgContent() throws MessagingException {
		MimeMultipart msgMixedParts = new MimeMultipart();
		MimeMultipart msgAltParts = new MimeMultipart("alternative");

		for (BodyPart part : bodyParts) {
			msgAltParts.addBodyPart(part);
		}
		BodyPart altBodyPart = new MimeBodyPart();
		altBodyPart.setContent(msgAltParts);
		msgMixedParts.addBodyPart(altBodyPart);

		for (BodyPart part : fileParts) {
			msgMixedParts.addBodyPart(part);
		}
		return msgMixedParts;
	}

	private String removeReturns(String input) {
		input = input.replaceAll("[\n\r]", "");
		return input;
	}

	static class SmtpAuthenticator extends Authenticator {

		private String user = null;
		private String password = null;

		public void setPassword(String password) {
			this.password = password;
		}

		public void setUser(String user) {
			this.user = user;
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			if (user != null && password != null) {
				return new PasswordAuthentication(user, password);
			}
			return null;
		}
	}
}
