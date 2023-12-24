package dev.vikash.UserService.Service;

import dev.vikash.UserService.DTO.SignUpRequestDto;
import dev.vikash.UserService.DTO.UserDto;
import dev.vikash.UserService.Exception.InvalidCredential;
import dev.vikash.UserService.Exception.InvalidTokenException;
import dev.vikash.UserService.Exception.UserNotFoundException;
import dev.vikash.UserService.Mapper.UserEntityDTOMapper;
import dev.vikash.UserService.Model.Session;
import dev.vikash.UserService.Model.SessionStatus;
import dev.vikash.UserService.Model.User;
import dev.vikash.UserService.Repository.SessionRepository;
import dev.vikash.UserService.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<List<Session>> getAllSession()
    {
        List<Session> sessions=sessionRepository.findAll();
        return ResponseEntity.ok(sessions);
    }
    public ResponseEntity<List<User>> getAllUsers()
    {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<UserDto> login(String email,String password)
    {
        //Get user detail detail from db
        Optional<User> userOptional=userRepository.findByEmail(email);
        if (userOptional.isEmpty())
        {
            throw new UserNotFoundException("User not found with given email");
        }
        User user=userOptional.get();
        //verify User password
       // if(!user.getPassword().equals(password))
        if(!bCryptPasswordEncoder.matches(password,user.getPassword()))
        {
            throw new InvalidCredential("Invalid Credential");
        }

            //Token Generation
           // String token= RandomStringUtils.randomAlphanumeric(30);
        MacAlgorithm alg= Jwts.SIG.HS256;
        SecretKey key=alg.key().build();
        Map<String,Object> jsonForJWT =new HashMap<>();
        jsonForJWT.put("email",user.getEmail());
        jsonForJWT.put("roles",user.getRoles());
        jsonForJWT.put("createdAt",new Date());
        jsonForJWT.put("expiresAt",new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String token=Jwts.builder().claims(jsonForJWT).signWith(key,alg).compact();


        //Session Control
        Session session =new Session();
        session.setUser(user);
        session.setLoginAt(new Date());
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        sessionRepository.save(session);

        //Generating Response

        UserDto userDto = UserEntityDTOMapper.getUserDtoFromUserEntity(user);
        //Setting up Header
        MultiValueMapAdapter<String,String> headers=new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE,"auth_token_"+token);
        return new ResponseEntity<>(userDto,headers, HttpStatus.OK);


    }


    public UserDto signUp(String email,String password)
    {
       User user=new User();
       user.setEmail(email);
      // user.setPassword(password);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public ResponseEntity<Void> logout(String token,Long userId)
    {
        Optional<Session> sessionOptional=sessionRepository.findByTokenAndUser_id(token,userId);
        if(sessionOptional.isEmpty())
        {
            return null;
        }

        Session session=sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }

    public SessionStatus validate(String token,Long UserId)
    {
        Optional<Session> sessionOptional =sessionRepository.findByTokenAndUser_id(token,UserId);
        if(sessionOptional.isEmpty() ||sessionOptional.get().getSessionStatus().equals(SessionStatus.ENDED))
        {
            throw new InvalidTokenException("Invalid Token");
        }

        return SessionStatus.ACTIVE;
    }
}
