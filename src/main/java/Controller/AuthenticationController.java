package Controller;

import Repositories.UserRepository;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * Created by ShubU on 3/13/2017.
 */

@Controller
@RequestMapping("/hello")
public class AuthenticationController {

    private UserRepository userRepository;

    @Autowired
    public AuthenticationController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String testHello(){
        return "Hello";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String loginHandler(@RequestBody String username){
        return "test login success";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public int registerHandler(@RequestBody User user){
        System.out.println("accept request");
        userRepository.add(user);
        return user.getId();
    }
}
