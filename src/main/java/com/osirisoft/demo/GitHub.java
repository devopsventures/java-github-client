package com.osirisoft.demo;

import org.eclipse.egit.github.core.client.*;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.*;

class GitHub {

    private GitHubClient github;

    public GitHub(String token) {
        github = new GitHubClient();
        github.setOAuth2Token(token);
    }

    public List<Repository> getUserRepositories() {
        RepositoryService service = new RepositoryService(github);

        try {
            return service.getRepositories();
        } catch (IOException e) {
            return null;
        }
    }

}