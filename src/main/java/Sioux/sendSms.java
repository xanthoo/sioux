package Sioux.SMS;

import Sioux.parkingspot.IParkingSpotRepository;
import Sioux.parkingspot.ParkingSpotMemoryRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class sendSms {
    public static final String ACCOUNT_SID = "AC53f8696ca356d3f050e9befdb203d4ec";
    public static final String AUTH_TOKEN = "c5208c4526efbd9587592aad3c46e6ee";
    private static  final IParkingSpotRepository parkingSpotRepository = new ParkingSpotMemoryRepository();

    public static void main(String[] args){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new com.twilio.type.PhoneNumber("+31 6 41 63 4198"),
                new com.twilio.type.PhoneNumber("+13343447824"), parkingSpotRepository.getAvParkingspot().toString()).create();

        System.out.println(message.getSid());
//
    }
}
