package learn.socialLogin.service;

import learn.socialLogin.service.token.NaverAccessToken;
import learn.socialLogin.service.user_info.NaverUserInfo;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class NaverLoginService {
    private final NaverAccessToken naverAccessToken;
    private final NaverUserInfo naverUserInfo;

    public NaverLoginService(NaverAccessToken naverAccessToken, NaverUserInfo naverUserInfo) {
        this.naverAccessToken = naverAccessToken;
        this.naverUserInfo = naverUserInfo;
    }
    public String getUserInfo(String code, String state) throws ParseException {
        return naverUserInfo.getUserInfo(naverAccessToken.getAccessToken(code, state)).toJSONString();
    }
}
