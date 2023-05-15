package learn.socialLogin.service.token;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
@Service
public class KakaoAccessToken {
    //ID값을 기준으로 access_token을 가져오는 부분
    public String getAccessToken(String code) throws ParseException {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "576378062e928e8231bd946cc7d65013");
        params.add("client_secret", "Lj4ZwM3Sk2kynWrfS7I7xyTwJBDy5HFF");
        params.add("code", code);
        params.add("redirect_uri", "http://localhost:8080/oauth2/kakao/callback/");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Conetent-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();

        //token을 가져오는 카카오 서버에 콜을 다시 날림
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                entity,
                String.class
        );

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());

        System.out.println(response.getBody());

        return jsonObject.get("access_token").toString();
    }
}
