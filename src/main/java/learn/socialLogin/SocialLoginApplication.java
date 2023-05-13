package learn.socialLogin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
@SpringBootApplication
public class SocialLoginApplication{
	// click here라는 버튼을 누르면서 첫 request 요청
	// 처음 시도하는 거라면 동의를 진행한 후, 카카오 서버에서 확인, 확인 후 ID값 전송
	@GetMapping("/oauth2/kakao/callback/")
	public String authorization(
			@RequestParam("code") String code,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "error_description", required = false) String error_description
	) throws ParseException {
		System.out.println(code + "," + error + "," + error_description);
		String accessToken = getAccessToken(code);
		JSONObject userInfo = getUserInfo(accessToken);
		return userInfo.toJSONString();
//		return "aa";
	}

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
	public static void main(String[] args) {
		SpringApplication.run(SocialLoginApplication.class, args);
	}
}