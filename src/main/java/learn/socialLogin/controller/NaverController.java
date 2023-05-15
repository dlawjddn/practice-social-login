package learn.socialLogin.controller;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NaverController {
    @GetMapping("/naver-login/callback")
    public String authorization(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "error_description", required = false) String error_description
    ) throws ParseException {
        System.out.println(code + ",  " +  state);
        return "plz..";
    }
}
