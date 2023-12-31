package com.example.demo.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class BillService {
	
	public void generatePdfActivationBill(String emailAddress,String username,String phonenumber,String planname,String price,String validity,String transactionid) throws DocumentException, FileNotFoundException {

		 
	 	System.out.println(username);
	 	System.out.println(phonenumber);
	 
    	Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RechargeInvoice.pdf"));

        writer.setPdfVersion(PdfWriter.VERSION_1_7);

 

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.DARK_GRAY);

 

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);

 

        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

 

        BaseColor accentColor = new BaseColor(0, 102, 204);

 

        document.open();

 

        Paragraph title = new Paragraph("Activation Successful", titleFont);

 

        title.setAlignment(Element.ALIGN_CENTER);

 

        document.add(title);

 

 

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        String invoiceDate = dateFormat.format(new Date());

        Paragraph invoiceDetails = new Paragraph("Invoice Date: " + invoiceDate);

 

        invoiceDetails.setAlignment(Element.ALIGN_RIGHT);

 

        document.add(invoiceDetails);

 

        Paragraph customerInfo = new Paragraph("Customer Information:", headerFont);

 

        customerInfo.add(new Chunk("\nName: "+username+"\nEmail: "+emailAddress, normalFont));

 

        document.add(customerInfo);

        Paragraph rechargeInfo = new Paragraph("Recharge Details:", headerFont);

 

        rechargeInfo.add(new Chunk("\nTransaction ID: "+transactionid+"\nPlan Name : "+planname+"\nRecharge Amount: "+price+"\nValidity"+validity+"\nMobile Number: "+phonenumber, normalFont));

 

        document.add(rechargeInfo);

      

        Paragraph total = new Paragraph("Total Amount: "+price, headerFont);

        total.setAlignment(Element.ALIGN_RIGHT);

        document.add(total);

 

        document.close();

 

 

 

        System.out.println("recharge invoice generated successfully!");

    

		

	}
	
	public String sendActivationBillMail(String emailAddress, String userName, String phonenumber,String planname,String price,String validity,String transactionid) throws FileNotFoundException, DocumentException, MessagingException {

        System.out.println("Outlook Email Start");

 

        String smtpHostServer = "smtp.office365.com";

        final String emailID = "priya.jp0210@outlook.com";

        final String password = "Welcome@123";

        String toEmail = emailAddress;

 

        generatePdfBill(emailAddress,userName,phonenumber,planname,price,validity,transactionid);

        String pdfFilePath = "RechargeInvoice.pdf";

        String subject = "Hi, " + userName + " - Recharge Done Succesfully";

 

       

        String cssStyles = "<style>" +

                "body { font-family: Arial, sans-serif; background-color: #f2f2f2; }" +

                "h2 { font-size: 18px; color: #333; }" +

                "#container { background-color: #fff; border-radius: 5px; padding: 20px; margin: 20px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); }" +

                ".greeting { font-size: 24px; color: #FF0000; }" +

                "</style>";

 

       

        String greetingMessage = "Hooray! Your SIM and Mobile Number Activation is Complete!";

        String activationInstructions = "<p>Your SIM card and mobile number are now active and ready for action. Here are a few things you can do:</p>" +
            "<ul>" +
            "<li>Start making calls, sending texts, and using mobile data.</li>" +
            "<li>Explore our exciting plans and features to get the most out of your mobile experience.</li>" +
            "<li>If you have any questions or need assistance, our friendly customer support team is just a call or email away.</li>" +
            "</ul>";

 

       

        String messageBody = "<!DOCTYPE html>\r\n"

                + "<html>\r\n"

                + "<head>\r\n"

                + cssStyles

                + "</head>\r\n"

                + "<body>\r\n"

                + "  <div id=\"container\">\r\n"

                + "    <p class=\"greeting\">" + greetingMessage + "</p>\r\n"

              //  + activationInstructions

              //  + "    <p>Here are your new SIM card details:</p>\r\n"

                

                + "</body>\r\n"

                + "</html>\r\n";

        Properties props = new Properties();

        props.put("mail.smtp.host", smtpHostServer);

        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.starttls.enable", "true");

 

        Session session = Session.getInstance(props, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(emailID, password);

            }

        });

 

        MimeMessage msg = new MimeMessage(session);

 

     

        MimeMultipart multipart = new MimeMultipart();

 

      

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(messageBody, "text/html; charset=utf-8");

        multipart.addBodyPart(messageBodyPart);

 

       

        MimeBodyPart attachmentPart = new MimeBodyPart();

        FileDataSource source = new FileDataSource(pdfFilePath);

        attachmentPart.setDataHandler(new DataHandler(source));

        attachmentPart.setFileName(pdfFilePath);

        multipart.addBodyPart(attachmentPart);

 

        

        msg.setContent(multipart);

 

      

        msg.setFrom(new InternetAddress(emailID));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

        msg.setSubject(subject, "UTF-8");

        msg.setSentDate(new Date());

 

       

        Transport.send(msg);

        System.out.println("Email Sent Successfully!!");

    

    return "Email sent successfully";

}
	
	public void generatePdfBill(String emailAddress,String username,String phonenumber,String planname,String price,String validity,String transactionid) throws DocumentException, FileNotFoundException {

		 
	 	System.out.println(username);
	 	System.out.println(phonenumber);
	 
    	Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("RechargeInvoice.pdf"));

        writer.setPdfVersion(PdfWriter.VERSION_1_7);

 

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.DARK_GRAY);

 

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);

 

        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

 

        BaseColor accentColor = new BaseColor(0, 102, 204);

 

        document.open();

 

        Paragraph title = new Paragraph("Recharge Invoice", titleFont);

 

        title.setAlignment(Element.ALIGN_CENTER);

 

        document.add(title);

 

 

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

        String invoiceDate = dateFormat.format(new Date());

        Paragraph invoiceDetails = new Paragraph("Invoice Date: " + invoiceDate);

 

        invoiceDetails.setAlignment(Element.ALIGN_RIGHT);

 

        document.add(invoiceDetails);

 

        Paragraph customerInfo = new Paragraph("Customer Information:", headerFont);

 

        customerInfo.add(new Chunk("\nName: "+username+"\nEmail: "+emailAddress, normalFont));

 

        document.add(customerInfo);

        Paragraph rechargeInfo = new Paragraph("Recharge Details:", headerFont);

 

        rechargeInfo.add(new Chunk("\nTransaction ID: "+transactionid+"\nPlan Name : "+planname+"\nRecharge Amount: "+price+"\nValidity"+validity+"\nMobile Number: "+phonenumber, normalFont));

 

        document.add(rechargeInfo);

      

        Paragraph total = new Paragraph("Total Amount: "+price, headerFont);

        total.setAlignment(Element.ALIGN_RIGHT);

        document.add(total);

 

        document.close();

 

 

 

        System.out.println("recharge invoice generated successfully!");

    

		

	}

    public String sendMail(String emailAddress, String userName, String phonenumber,String planname,String price,String validity,String transactionid) throws FileNotFoundException, DocumentException, MessagingException {

        System.out.println("Outlook Email Start");

 

        String smtpHostServer = "smtp.office365.com";

        final String emailID = "priya.jp0210@outlook.com";

        final String password = "Welcome@123";

        String toEmail = emailAddress;

 

        generatePdfBill(emailAddress,userName,phonenumber,planname,price,validity,transactionid);

        String pdfFilePath = "RechargeInvoice.pdf";

        String subject = "Hi, " + userName + " - Recharge Done Succesfully";

 

       

        String cssStyles = "<style>" +

                "body { font-family: Arial, sans-serif; background-color: #f2f2f2; }" +

                "h2 { font-size: 18px; color: #333; }" +

                "#container { background-color: #fff; border-radius: 5px; padding: 20px; margin: 20px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); }" +

                ".greeting { font-size: 24px; color: #FF0000; }" +

                "</style>";

 

       

        String greetingMessage = " Your recharge details.";

 

       

//        String activationInstructions = "<p>To activate your new SIM card, please follow these steps:</p>" +
//
//                "<ol>" +
//
//                "<li>Insert the SIM card into your mobile device.</li>" +
//
//                "<li>Power on your device and follow the on-screen instructions for activation.</li>" +
//
//                "<li>If you encounter any issues, please contact our customer support.</li>" +
//
//                "</ol>";

 

       

        String messageBody = "<!DOCTYPE html>\r\n"

                + "<html>\r\n"

                + "<head>\r\n"

                + cssStyles

                + "</head>\r\n"

                + "<body>\r\n"

                + "  <div id=\"container\">\r\n"

                + "    <p class=\"greeting\">" + greetingMessage + "</p>\r\n"

              //  + activationInstructions

              //  + "    <p>Here are your new SIM card details:</p>\r\n"

                

                + "</body>\r\n"

                + "</html>\r\n";

        Properties props = new Properties();

        props.put("mail.smtp.host", smtpHostServer);

        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.starttls.enable", "true");

 

        Session session = Session.getInstance(props, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(emailID, password);

            }

        });

 

        MimeMessage msg = new MimeMessage(session);

 

     

        MimeMultipart multipart = new MimeMultipart();

 

      

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(messageBody, "text/html; charset=utf-8");

        multipart.addBodyPart(messageBodyPart);

 

       

        MimeBodyPart attachmentPart = new MimeBodyPart();

        FileDataSource source = new FileDataSource(pdfFilePath);

        attachmentPart.setDataHandler(new DataHandler(source));

        attachmentPart.setFileName(pdfFilePath);

        multipart.addBodyPart(attachmentPart);

 

        

        msg.setContent(multipart);

 

      

        msg.setFrom(new InternetAddress(emailID));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

        msg.setSubject(subject, "UTF-8");

        msg.setSentDate(new Date());

 

       

        Transport.send(msg);

        System.out.println("Email Sent Successfully!!");

    

    return "Email sent successfully";

}


}
