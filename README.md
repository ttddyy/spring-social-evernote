### NOTE: This project is no longer actively maintained.
----

# spring-social-evernote

Spring Social provider module for Evernote API.

Spring Social Evernote provides `EvernoteConnectionFactory`, a connection factory implementation for Evernote. This enables 'Connect Framework' from Spring Social to handle OAuth authentication process (and connection persistence) with Evernote.  
Not only the connect framework support, Spring Social Evernote provides rich programming model founded by Spring Framework on top of the [evernote-sdk-java](https://github.com/evernote/evernote-sdk-java/), a thrift based Java SDK provided by Evernote. 


# key features

- Service provider implementation for Evernote OAuth

- More Java friendly programming model

 - Interface based programming for store clients

 - Unchecked exceptions for endpoint operations

 - Null safe collection for thrift based domain object


Please refer to the [documentation](https://github.com/ttddyy/spring-social-evernote/wiki/About) and implementation for details.


## documentation

- [Spring Social Evernote Reference](https://github.com/ttddyy/spring-social-evernote/wiki/About)
- [Javadoc](https://github.com/ttddyy/spring-social-evernote/wiki/Javadoc)

## library versions

| spring-social-evernote | spring-social | evernote-sdk-java |                     note |
| ----------------------:| -------------:| -----------------:| ------------------------ | 
|           1.0.0, 1.0.1 |      1.1.0.M4 |            1.25.1 | *works fine with spring4 |
|           1.0.2, 1.0.3 | 1.1.0.RELEASE |            1.25.1 |                          |
|               SNAPSHOT | 1.1.0.RELEASE |            1.25.1 |                          |

## how to get


```xml
  <dependency>
      <groupId>net.ttddyy</groupId>
      <artifactId>spring-social-evernote</artifactId>
      <version>1.0.3</version>
  </dependency>
```

### for version 1.0.0 and 1.0.1

Both versions have dependency to `spring-social-core-1.1.0.M4`.   
By maven's transitive dependency mechanism, the jar file is downloaded from spring's milestone repository since `pom.xml` in `spring-social-evernote` specifies milestone repository.
However, if you want `mvn dependency:sources` to download all sources.jar or javadoc.jar, your project pom file needs to specify the milestone repository explicitly.

```xml
    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>http://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
```

* Since version 1.0.2 uses release version of spring-social, this milestone configuration is not necessary. 

# development

## continuous integration

- [BuildHive Jenkins](https://buildhive.cloudbees.com/job/ttddyy/job/spring-social-evernote/)
[![Build Status](https://buildhive.cloudbees.com/job/ttddyy/job/spring-social-evernote/badge/icon)](https://buildhive.cloudbees.com/job/ttddyy/job/spring-social-evernote/)


