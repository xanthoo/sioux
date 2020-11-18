package Sioux.SMS;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class sendSms {
    public static final String ACCOUNT_SID = "AC53f8696ca356d3f050e9befdb203d4ec";
    public static final String AUTH_TOKEN = "c5208c456efbd9587592aad3c46e6ee";

    public static void main(String[] args){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new com.twilio.type.PhoneNumber("+359 89 534 7218"),
                new com.twilio.type.PhoneNumber("+13343447824"), "This is a test message app").create();

        System.out.println(message.getSid());

    }
}
