package com.example.ptit_cntt2_it211_ss20_ex2;

import com.example.ptit_cntt2_it211_ss20_ex2.domain.Account;
import com.example.ptit_cntt2_it211_ss20_ex2.domain.Artwork;
import com.example.ptit_cntt2_it211_ss20_ex2.domain.Role;
import com.example.ptit_cntt2_it211_ss20_ex2.repository.AccountRepository;
import com.example.ptit_cntt2_it211_ss20_ex2.repository.ArtworkRepository;
import com.example.ptit_cntt2_it211_ss20_ex2.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final ArtworkRepository artworkRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setRoleName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role artistRole = new Role();
        artistRole.setRoleName("ROLE_ARTIST");
        roleRepository.save(artistRole);

        Account adminAccount = new Account();
        adminAccount.setUsername("admin");
        adminAccount.setPassword(passwordEncoder.encode("password"));
        adminAccount.setRoles(Set.of(adminRole));
        accountRepository.save(adminAccount);

        Account artistAccount = new Account();
        artistAccount.setUsername("artist");
        artistAccount.setPassword(passwordEncoder.encode("password"));
        artistAccount.setRoles(Set.of(artistRole));
        accountRepository.save(artistAccount);
        
        Account artistAccount2 = new Account();
        artistAccount2.setUsername("artist2");
        artistAccount2.setPassword(passwordEncoder.encode("password"));
        artistAccount2.setRoles(Set.of(artistRole));
        accountRepository.save(artistAccount2);

        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Mona Lisa");
        artwork1.setDescription("A famous painting by Leonardo da Vinci.");
        artwork1.setPublished(true);
        artwork1.setOwner(artistAccount);
        artworkRepository.save(artwork1);

        Artwork artwork2 = new Artwork();
        artwork2.setTitle("The Starry Night");
        artwork2.setDescription("A famous painting by Vincent van Gogh.");
        artwork2.setPublished(true);
        artwork2.setOwner(artistAccount2);
        artworkRepository.save(artwork2);

        Artwork artwork3 = new Artwork();
        artwork3.setTitle("Unpublished Work");
        artwork3.setDescription("A private work by artist.");
        artwork3.setPublished(false);
        artwork3.setOwner(artistAccount);
        artworkRepository.save(artwork3);
    }
}
