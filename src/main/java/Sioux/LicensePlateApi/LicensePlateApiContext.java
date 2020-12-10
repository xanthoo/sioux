package Sioux.LicensePlateApi;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.File;
import java.net.URI;

public class LicensePlateApiContext implements LicensePlateApi{
    @Override
    public String getLicensePlate(String imageFilepath) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api-eu.anpr-cloud.com/free");
        URI uri = builder.build().encode().toUri();

        File image = null;
        try{
            //only works if file is inside project
//            URL url = getClass().getResource("/bmwLowRes.png");
            image = new File(imageFilepath);
        }
        catch (Exception ex){
            System.out.println("No image can be found at:  "+imageFilepath);
            System.exit(1);
        }

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("image", new FileSystemResource(image));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key","03516249f87fcbe6a770172d6c23f6c22a99ad2b");
        headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

        String result = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
            result = responseEntity.toString();
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }
}
