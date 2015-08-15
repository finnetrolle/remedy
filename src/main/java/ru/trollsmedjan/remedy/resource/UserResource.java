package ru.trollsmedjan.remedy.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.UserDTO;
import ru.trollsmedjan.remedy.exception.RemedyAuthException;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.AccessRights;
import ru.trollsmedjan.remedy.model.entity.User;
import ru.trollsmedjan.remedy.services.SessionService;
import ru.trollsmedjan.remedy.services.UserService;


/**
 * Created by finnetrolle on 15.08.2015.
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    @ResponseBody
    public UserDTO updateUser(@PathVariable String username, @RequestBody AccessRights accessRights, @RequestHeader("authToken") String token) throws RemedyServiceLayerException, RemedyDataLayerException, RemedyAuthException {
        if (!sessionService.canWriteAccessRights(token)) {
            throw new RemedyAuthException("This user can't edit access rights");
        }

        User user = userService.updateUser(username, accessRights);

        UserDTO dto = new UserDTO();
        dto.setAccessRights(user.getAccessRights());
        dto.setUsername(user.getUsername());
        return dto;
    }

    public static class CreateUserDTO {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    @RequestMapping(value = "/absolute", method = RequestMethod.POST)
    @ResponseBody
    public UserDTO createAbsoluteUser(@RequestBody CreateUserDTO data) throws RemedyServiceLayerException {
        AccessRights accessRights = new AccessRights(true);
        User user = userService.createUser(data.getUsername(), accessRights);

        return new UserDTO(user.getUsername(), user.getAccessRights());
    }


}
