package JMT.lymdingdong.security.service;

import JMT.lymdingdong.security.config.JwtTokenProvider;
import JMT.lymdingdong.security.dto.TokenInfo;
import JMT.lymdingdong.security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(String memberId,String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId,password);
        //Login Id와 password를 기반으로 Authentication 객체 생성
        // 이떄 authentication은 인증 여부를 확인하는 값이 false

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 실제 관리자의 비밀번호 체크가 이뤄지는 부분으로 authenticate 메서드가 실행될 떄 CustomUserDetailsService에서 만든
        // loadUserByUsername 메서드 실행

        TokenInfo tokenInfo = jwtTokenProvider.createToken(authentication);

        return tokenInfo;
    }
}
