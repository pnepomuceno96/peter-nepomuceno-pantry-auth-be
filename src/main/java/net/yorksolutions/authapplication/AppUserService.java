package net.yorksolutions.authapplication;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.*;

@Service
public class AppUserService {
    private final AppUserRepo repo;
    HashMap<UUID, UUID> token_map;
    public AppUserService(AppUserRepo repo) {
        this.repo = repo;
        this.token_map = new HashMap<>();
    }

    public UUID login(String username, String password) {
        Optional<AppUser> appUserOptional = this.repo.findByUsername(username);
        if(appUserOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        var user = appUserOptional.get();
        if(!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        var token = UUID.randomUUID();
        token_map.put(token, user.getId());
        System.out.println(token_map);
        return token;
    }

    public AppUser signup(String username, String password) {
        Optional<AppUser> appUserOptional = repo.findByUsername(username);
        if (appUserOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = new AppUser();

        Pattern pattern = Pattern.compile("^[\\w.@-]*$");
        Matcher matcher = pattern.matcher(username);
        boolean validUsername = matcher.find();
        if(!validUsername) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        appUser.setUsername(username);
        appUser.setPassword(password);
        repo.save(appUser);
        //System.out.println(appUser.getUsername()+ ", " + appUser.getPassword());
        return appUser;

    }

    public void logout(UUID token) {
        this.token_map.remove(token);
    }

    public void checkAuth(UUID token){
        if(!this.token_map.containsKey(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
