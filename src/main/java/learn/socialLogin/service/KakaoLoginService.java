package learn.socialLogin.service;

import learn.socialLogin.service.token.KakaoAccessToken;
import learn.socialLogin.service.user_info.KakaoUserInfo;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class KakaoLoginService {
    private final KakaoAccessToken kakaoAccessToken;
    private final KakaoUserInfo kakaoUserInfo;
    public KakaoLoginService(KakaoAccessToken kakaoAccessToken, KakaoUserInfo kakaoUserInfo) {
        this.kakaoAccessToken = kakaoAccessToken;
        this.kakaoUserInfo = kakaoUserInfo;
    }
    public String getUserInfo(String code) throws ParseException {
        return kakaoUserInfo.getUserInfo(kakaoAccessToken.getAccessToken(code)).toJSONString();
    }
}
