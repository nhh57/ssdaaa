package poly.com.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;
import poly.com.DAO.UserDAO;
import poly.com.Entity.User;

import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserDAO userDAO;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws org.springframework.security.oauth2.core.OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String avatar = (String) attributes.get("picture");

        User existingUser = userDAO.findUserByEmail(email);
        if (existingUser == null) {
           
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setHoten(name);
            newUser.setAvatar(avatar);
            newUser.setIs_google_login(true);
            userDAO.save(newUser);
        } else {
            
            existingUser.setAvatar(avatar);
            existingUser.setIs_google_login(true);
            userDAO.save(existingUser);
        }

        return oauth2User;  
    }
}
