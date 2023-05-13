package learn.socialLogin.service.user_info;

import learn.socialLogin.SocialLoginApplication;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
@Service
public class KakaoUserInfo {
    public JSONObject getUserInfo(String ac) throws ParseException {
        MultiValueMap<String, Boolean> params = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Conetent-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer " + ac);

        HttpEntity<MultiValueMap<String, Boolean>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();

        //token으로 유저 정보를 가져오는 카카오 서버에 콜을 다시 날림
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                entity,
                String.class
        );

        System.out.println(response.getBody());
        JSONParser jsonParser = new JSONParser();

        return (JSONObject) jsonParser.parse(response.getBody());
    }
}
