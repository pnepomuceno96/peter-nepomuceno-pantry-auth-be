package net.yorksolutions.authapplication;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
@CrossOrigin
public class AppUserController {
    AppUserService service;
    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public UUID login(@RequestBody Credentials creds) {return this.service.login(creds.username, creds.password); }

    @PostMapping("/signup" )
    public AppUser signup(@RequestBody Credentials creds){
        return this.service.signup(creds.username, creds.password);
    }
    @GetMapping("/logout")
    public void logout(@RequestParam UUID token){
        this.service.logout(token);
    }
    @GetMapping("/checkAuth")
    public void checkAuth(@RequestParam UUID token){
        this.service.checkAuth(token);
    }
}
