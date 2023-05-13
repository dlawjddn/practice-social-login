package learn.socialLogin.controller;

import learn.socialLogin.service.KakaoLoginService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoController {
    // click here라는 버튼을 누르면서 첫 request 요청
    // 처음 시도하는 거라면 동의를 진행한 후, 카카오 서버에서 확인, 확인 후 ID값 전송
    private final KakaoLoginService kakaoLoginService;
    public KakaoController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }
    @GetMapping("/oauth2/kakao/callback/")
    public String authorization(
            @RequestParam("code") String code,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false) String error_description
    ) throws ParseException {
        return kakaoLoginService.getUserInfo(code);
        /*
        System.out.println(code + "," + error + "," + error_description);
        String accessToken = getAccessToken(code);
        JSONObject userInfo = getUserInfo(accessToken);
        return userInfo.toJSONString();
         */
//		return "aa";
    }
}
