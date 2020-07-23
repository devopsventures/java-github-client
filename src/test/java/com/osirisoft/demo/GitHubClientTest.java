package com.osirisoft.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GitHubClientTest {

    private GitHub client;

    @BeforeEach
    void init() {
        String token = System.getenv("GITHUB_TEST_TOKEN");
        assumeTrue(token != null);

        client = new GitHub(token);
    }

    @Test
    @DisplayName("GitHub - getUser()")
    void getUser() {
        String user = client.getUser();
        assertNotNull(user,  "Token should have a username associated with it");
    }

    @Test
    @DisplayName("GitHub - getUserRepositories()")
    void getUserRepositories() {
        Collection<Repository> repos = client.getUserRepositories();
        assertNotNull(repos, "should get some repositories for user");
        assertTrue(repos.size() > 0, "the user should be able to access some repositories");
    }
}