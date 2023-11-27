package JMT.lymdingdong.security.config;


import JMT.lymdingdong.security.dto.TokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
@Component
public class JwtTokenProvider {
    private  Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey){
        byte[] keybyte = Decoders.BASE64.decode(secretKey);
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // 인코딩된 문자열을 디코딩 해서 배열에 저장
        //그리고 hmaShaKeyFor를 사용해 HmacSha 키로 변환
        // Hmac은 메세지 인증 코드를 위한 해시함수로 무결성을 보호함
        // 따라서 hmacshakey는 HMAC를 생성하기 위한 비밀키
    }

    //토큰 생성 메소드
    public TokenInfo createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        //authentication.getAuthorities()를 통해서 권한 목록 가져옴
        //stream()은 그 목록을 stream으로 변환 이때 stream은 컬렉션 요소를 함수형 스타일로 처리하는 기능을 제공
        // 따라서 스트림의 각 요소를 ,표로 구분한걸 collectors.joining을 통해 하나의 문자열로 결합하면
        // 권한을 하나의 문자열로 합칠 수 있음

        long now = (new Date().getTime());
        // Token의 유효기간,만료시간 등을 계산하는데 사용됨

        Date accessTokenTime = new Date(now+3600000);
        // 60*60*1000 = 1시간을 밀리초로 표현한 것으로 토큰의 유효기간이 1시간

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                /*Jwt의 Sub클레임(클레임은 토큰에 담기는 데이터 조각)에 사용자 이름 설정.
                즉, 현재 인증된 사용자 정보를 포함하고 사용자의 이름을 반환*/

                .claim("auth",authorities)
                //Jwt에 클래임을 추가적으로 설정. auth 클레임을 추가

                .setExpiration(accessTokenTime)
                // Jwt 토큰을 아까 계산한 변수를 사용해 만료 시간 설정

                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
                // Jwt를 문자열로 변환해 반환하고 그 문자열이 accessToken이 됨

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now+86400000))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer") //OAuth에서 사용되는 인증 토큰 유형으로 Http 요청 헤더에 포함되어 전달
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한이 없는 토큰입니다");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);

    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("잘못된 토큰입니다",e);
        }catch (ExpiredJwtException e){
            log.info("만료된 토큰입니다",e);
        }catch(UnsupportedJwtException e){
            log.info("지원하지 않는 토큰입니다",e);
        }catch(IllegalArgumentException e){
            log.info("Jwt claims이 비어있습니다",e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
