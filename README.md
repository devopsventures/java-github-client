# Java Example Library Project

This is an example Java library project that is built using Maven and has GitHub Actions Workflows that showcase how to interact with an Artifactory instance
for proxying and hosting our internal artifacts.


## Java Library
This is an extremely simple library that provides a very basic wrapper around a public library for interacting with GitHub over the v3 REST API.

The project is using Java 11.


### pom.xml
The Maven POM for the project is extremely lightweight providing only the basic settings to be valid and provide best practice control features, like locking maven plugin versions for consistent builds.


### settings.xml
The project contains a `settings.xml` which is configured to provide the necessary settings for Maven so as to redirect dependency and plugin requests to an Artifactory instance instead of going via the internet.

* `mirror`: A mirror for all `*` sources is configured as the Artifactory instance (a virtual repository for the project that groups a number of Maven sources in Artifactory)
* `artifactory` profile: Sets the `repositories` and `pluginRepositories` to generic URLs that are not valid, but will be redirected to the Artifactory `mirror`.
* `server` authentication details: The usernames and passwords for accessing the Artifactory server URLs, these use properties so that they can be injected using `-Dxxx=yyy` properties on the command line when running `mvn` commands.
* `localRepository`: A localRepoistory is hard coded to `.m2/repository` which will result in the dependencies and maven plugins being stored local to the project to ensure isolation (this is also used for caching in the GitHub Actions workflows)


### GitHub Actions Workflows

#### build_and_test_docker.yml
The [`build_and_test_docker.yml`](.github/workflows/build_and_test_docker.yml) workflow showcases using an offical Docker Hub image for Maven (which includes a JDK) to build and test the project.

There is a use of the `actions/cache` action so as to cache the resolved dependencies and plugins and speed up builds.


#### build_and_test_local.yml
The [`build_and_test_local.yml`](.github/workflows/build_and_test_local.yml) workflow showcases using the `actions/setup-java` action and using the version of Maven that is present in the provided runner.

There is a use of the `actions/cache` action so as to cache the resolved dependencies and plugins and speed up builds.


#### publish_to_repository.yml
The [`publish_to_repository.yml`](.github/workflows/publish_to_repository.yml) workflow showcases a deployment to a hosted Artifcatory instance, catering for both release and snapshots depending upon the version present in the POM file.

This is a manually triggered workflow but could be modified to activate from any automation events that are relevant to your desired SDLC workflows.
