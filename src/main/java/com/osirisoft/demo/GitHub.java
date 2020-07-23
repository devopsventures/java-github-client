package com.osirisoft.demo;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.*;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class GitHub {

    private GitHubClient github;

    public GitHub(String token) {
        github = new GitHubClient();
        github.setOAuth2Token(token);
    }

    public Collection<Repository> getUserRepositories() {
        RepositoryService service = new RepositoryService(github);

        try {
            List<org.eclipse.egit.github.core.Repository> repos = service.getRepositories();
            Collection<Repository> results = new ArrayList<Repository>(repos.size());

            repos.forEach(repo -> results.add(new RepositoryImpl(repo)));

            return results;
        } catch (IOException e) {
            return null;
        }
    }

    public String getUser() {
        UserService service = new UserService(github);
        try {
            User currentUser = service.getUser();
            return currentUser.getLogin();
        } catch (IOException e) {
            return null;
        }
    }
}

class RepositoryImpl implements Repository {

    private long id;
    private String name;
    private String owner;

    RepositoryImpl(org.eclipse.egit.github.core.Repository repo) {
        this.id = repo.getId();
        this.name = repo.getName();
        this.owner = repo.getOwner().getLogin();
    }

    public String getName() {
        return this.name;
    }

    public long getId() {
        return this.id;
    }

    public String getOwner() {
        return this.owner;
    }
}