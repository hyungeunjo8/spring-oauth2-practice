package com.weverse.springoauth2practice.authorization;

import com.weverse.springoauth2practice.security.UserAuthority;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    @Secured(UserAuthority.ROLES.ADMIN)
    @GetMapping("/authorization/admin")
    String checkAdminRole() {
        return "Success Admin Role Check";
    }

    @Secured(UserAuthority.ROLES.USER)
    @GetMapping("/authorization/user")
    String checkUserRole() {
        return "Success User Role Check";
    }

    @Secured(UserAuthority.ROLES.MANAGER)
    @GetMapping("/authorization/manager")
    String checkManagerRole() {
        return "Success Manager Role Check";
    }

    @GetMapping("/authorization/multi-by-configure")
    String checkMultiAuthorizationByConfigure() {
        return "Success Configure Authorization";
    }
}
