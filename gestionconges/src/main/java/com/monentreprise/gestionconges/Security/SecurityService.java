package com.monentreprise.gestionconges.Security;

import com.monentreprise.gestionconges.entity.AppUser;
import com.monentreprise.gestionconges.repository.AppUserRepository;
import com.monentreprise.gestionconges.repository.CongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    private final CongeRepository congeRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    public SecurityService(CongeRepository congeRepository) {
        this.congeRepository = congeRepository;
    }

    public boolean isCongeOwner(Long congeId, String username) {
        return congeRepository.findById(congeId)
                .map(conge -> conge.getUser().getUsername().equals(username))
                .orElse(false);
    }
    public boolean isOwner(Long userId, String username) {
        return appUserRepository.findById(userId)
                .map(user -> user.getUsername().equals(username))
                .orElse(false);
    }
    public boolean isUser(Long userId, String username) {
        if (userId == null || username == null) {
            return false;
        }

        AppUser user = appUserRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        return username.equals(user.getUsername());
    }
}

