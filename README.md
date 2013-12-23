# spring-social-evernote

Spring Social provider module for Evernote API.

Spring Social Evernote provides `EvernoteConnectionFactory`, a connection factory implementation for Evernote. This enables 'Connect Framework' from Spring Social to handle OAuth authentication process (and connection persistence) with Evernote.  
Not only the connect framework support, Spring Social Evernote provides rich programming model founded by spring framework on top of the [evernote-sdk-java](https://github.com/evernote/evernote-sdk-java/), a thrift based java sdk provided by evernote. 


# key features

- Service provider implementation for Evernote OAuth

- More  java friendly programming model

 - Interface based programming for store clients

 - Unchecked exceptions for endpoint operations

 - Null safe collection for thrift based domain object


Please reference the [documentation](https://github.com/ttddyy/spring-social-evernote/wiki/About) and implementation for details.


## documentation

https://github.com/ttddyy/spring-social-evernote/wiki/About

## library versions

- spring-social: 1.1.0.BUILD-SNAPSHOT
- evernote-sdk-java: 1.25.1

## how to get

Once major version is released, it will be available in maven central.

For now:

```
$ git clone https://github.com/ttddyy/spring-social-evernote.git
$ cd spring-social-evernote
$ mvn install
```


# development

## status

working on implementation.

## continuous integration

- [BuildHive Jenkins](https://buildhive.cloudbees.com/job/ttddyy/job/spring-social-evernote/)
[![Build Status](https://buildhive.cloudbees.com/job/ttddyy/job/spring-social-evernote/badge/icon)](https://buildhive.cloudbees.com/job/ttddyy/job/spring-social-evernote/)


