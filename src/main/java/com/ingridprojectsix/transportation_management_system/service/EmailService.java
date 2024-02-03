package com.ingridprojectsix.transportation_management_system.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public SimpleMailMessage sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
        return simpleMailMessage;
    }

    public void sendSimpleMessage(String to, String subject, String text, List<String> cc) {
        SimpleMailMessage simpleMailMessage = sendSimpleMessage(to, subject, text);

        if(cc.size() > 0) {
            simpleMailMessage.setCc(getCcArray(cc));
        }

        javaMailSender.send(simpleMailMessage);
    }


    private String[] getCcArray(List<String> ccList) {
        String[] cc = new String[ccList.size()];
        for(int i = 0; i < ccList.size(); i++) {
            cc[i] = ccList.get(i);
        }
        return cc;
    }

    public void sendRideRequestEmail(String to, String driverName, String passengerName,
                                     String startLocation, String endLocation, String requestTime,
                                     String supportContact) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setTo(to);
        helper.setSubject("New Ride Request - Action Required");

        // Compose the HTML content for the email
        String htmlContent = "<html>"
                + "<body>"
                + "<p>Dear " + driverName + ",</p>"
                + "<p>I hope this message finds you well. We have received a new ride request " +
                "that matches your availability and proximity to the passenger." +
                " Details of the ride request are as follows:</p>"
                + "<ul>"
                + "<li><strong>Passenger Name:</strong> " + passengerName + "</li>"
                + "<li><strong>Start Location:</strong> " + startLocation + "</li>"
                + "<li><strong>End Location:</strong> " + endLocation + "</li>"
                + "<li><strong>Request Time:</strong> " + requestTime + "</li>"
                + "</ul>"
                + "<p><strong>Action Required:</strong><br/>Please review the ride details and " +
                "confirm your availability to accept this ride. The passenger is eagerly awaiting your response. " +
                "If you are unable to accept the ride, kindly reject it promptly.</p>"
                + "<p><strong>To Confirm:</strong><br/>"
                + "1. Log in to your driver account.<br/>"
                + "2. Navigate to the \"Pending Requests\" section.<br/>"
                + "3. Accept or Reject the ride request.</p>"
                + "<p><strong>Note:</strong> If you do not respond within 10 minutes, the system will automatically consider it as a rejection, and the ride will be reassigned to another available driver.</p>"
                + "<p>Thank you for your prompt attention to this matter. If you have any questions or encounter any issues, feel free to contact our support team at " + supportContact + ".</p>"
                + "<p>Best regards,<br/>Your Company Name<br/>Your Contact Information</p>"
                + "</body>"
                + "</html>";

            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            System.out.println("Email sent successfully!");

    }

    public void sendRideRequestPendingEmail(String to, String passengerName,
                                            String startLocation, String endLocation,
                                            String requestTime, String supportContact) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");


        helper.setTo(to);
        helper.setSubject("Your Ride Request is Pending");

        // Compose the HTML content for the email
        String htmlContent = "<html>"
                + "<body>"
                + "<p>Dear " + passengerName + ",</p>"
                + "<p>We have received your ride request, and our team is working to assign a driver for your journey. Here are the details of your ride request:</p>"
                + "<ul>"
                + "<li><strong>Start Location:</strong> " + startLocation + "</li>"
                + "<li><strong>End Location:</strong> " + endLocation + "</li>"
                + "<li><strong>Request Time:</strong> " + requestTime + "</li>"
                + "</ul>"
                + "<p><strong>Status:</strong> Pending - We are actively searching for an available driver in your area. Please be patient, and we will notify you once your ride is confirmed.</p>"
                + "<p>If you have any urgent inquiries or need assistance, feel free to contact our support team at " + supportContact + ".</p>"
                + "<p>Thank you for choosing our service. We appreciate your patience!</p>"
                + "<p>Best regards,<br/>Ingryd<br/>+2346578647856</p>"
                + "</body>"
                + "</html>";

        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendRideAssignedEmail(String to, String passengerName,
                                      String startLocation, String endLocation,
                                      String driverName, String driverContact,
                                      String rideTime, String supportContact) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setTo(to);
        helper.setSubject("Your Ride Has Been Assigned");

        // Compose the HTML content for the email
        String htmlContent = "<html>"
                + "<body>"
                + "<p>Dear " + passengerName + ",</p>"
                + "<p>Great news! Your ride request has been successfully assigned. Here are the details of your ride:</p>"
                + "<ul>"
                + "<li><strong>Start Location:</strong> " + startLocation + "</li>"
                + "<li><strong>End Location:</strong> " + endLocation + "</li>"
                + "<li><strong>Driver Name:</strong> " + driverName + "</li>"
                + "<li><strong>Driver Contact:</strong> " + driverContact + "</li>"
                + "<li><strong>Ride Time:</strong> " + rideTime + "</li>"
                + "</ul>"
                + "<p><strong>Status:</strong> In Progress - Your driver is on the way to pick you up. Please be ready at the specified location.</p>"
                + "<p>If you have any questions or need assistance during the ride, you can contact your driver directly. For any other inquiries, reach out to our support team at " + supportContact + ".</p>"
                + "<p>Thank you for choosing our service. We wish you a pleasant journey!</p>"
                + "<p>Best regards,<br/>Your Company Name<br/>Your Contact Information</p>"
                + "</body>"
                + "</html>";

        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
        System.out.println("Email sent successfully!");
    }

    public void sendNoRideAvailableEmail(String to, String passengerName,
                                         String startLocation, String endLocation,
                                         String supportContact) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setTo(to);
        helper.setSubject("No Available Drivers for Your Ride Request");

        // Compose the HTML content for the email
        String htmlContent = "<html>"
                + "<body>"
                + "<p>Dear " + passengerName + ",</p>"
                + "<p>We regret to inform you that there are currently no available drivers for your ride request. Here are the details of your request:</p>"
                + "<ul>"
                + "<li><strong>Start Location:</strong> " + startLocation + "</li>"
                + "<li><strong>End Location:</strong> " + endLocation + "</li>"
                + "</ul>"
                + "<p>Please be assured that our team is actively working to find a suitable driver for your journey. We appreciate your patience and understanding.</p>"
                + "<p>If you have any urgent inquiries or need assistance, feel free to contact our support team at " + supportContact + ".</p>"
                + "<p>Thank you for choosing our service. We apologize for any inconvenience caused and appreciate your understanding.</p>"
                + "<p>Best regards,<br/>Your Company Name<br/>Your Contact Information</p>"
                + "</body>"
                + "</html>";

            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
    }
}
