package JMT.lymdingdong.security.controller;

import JMT.lymdingdong.security.dto.MemberLoginRequestDto;
import JMT.lymdingdong.security.dto.TokenInfo;
import JMT.lymdingdong.security.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto){
        log.info("Wow"); //이게 찍히면 컨트롤러는 간다는 뜻
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId,password);
        return tokenInfo;
    }
}
