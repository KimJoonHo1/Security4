package com.example.security2.oauth;

import com.example.security2.auth.PrincipalDetails;
import com.example.security2.model.User;
import com.example.security2.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());

        Oauth2UserInfo oauth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oauth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            oauth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oauth2UserInfo = new NaveruserInfo((Map) oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("구글, 페이스북, 네이버만 지원합니다.");
        }

        String provider = oauth2UserInfo.getProvider();
        String providerId = oauth2UserInfo.getProviderId();
        String email = oauth2UserInfo.getEmail();
        String username = provider + "_" + providerId;

        UUID uuid = UUID.randomUUID();
        String password = bCryptPasswordEncoder.encode(uuid.toString());

        String role = "ROLE_USER";

        User user = repository.login(username);

        if(user == null) {
            System.out.println("OAuth 로그인이 최초입니다.");

            user = User.builder().username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            repository.save(user);
        } else {
            System.out.println("로그인을 이미 한 적이 있습니다.");
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
