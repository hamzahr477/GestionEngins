package com.marsamaroc.gestionengins.configuration;

import com.marsamaroc.gestionengins.entity.Critere;
import com.marsamaroc.gestionengins.entity.Engin;
import com.marsamaroc.gestionengins.entity.Famille;
import com.marsamaroc.gestionengins.entity.Panne;
import com.marsamaroc.gestionengins.entity.Post;
import com.marsamaroc.gestionengins.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@Configuration
public class MyCoolConfiguration  {
    @Autowired
    private RepositoryRestConfiguration restConfiguration;

    @Bean
    public void init() {
        restConfiguration.exposeIdsFor(Critere.class);
        restConfiguration.exposeIdsFor(Engin.class);
        restConfiguration.exposeIdsFor(Post.class);
        restConfiguration.exposeIdsFor(User.class);
        restConfiguration.exposeIdsFor(Famille.class);
        restConfiguration.exposeIdsFor(Panne.class);


    }


}
