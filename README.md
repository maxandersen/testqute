# JBang w/Java

JBang lets Students, Educators and Professional Developers create, edit and run self-contained source-only Java programs with unprecedented ease.

## It is just Java

You can write any kind of java. JBang just wraps `javac` and `java` to make it super easy to run. JBang scans for special markers to setup dependencies, repositories and java settings.

Below is a cheatsheet for common things you can do with JBang on replit, see more in [JBang Documentation](https://jbang.dev/documentation).

## Add dependency

To use a Maven dependency use `//DEPS groupid:artifactid:version` syntax.

i.e. to use Apache Commons lang you do:

```java
//DEPS org.apache.commons:commons-lang3:3.12.0
```

You can use any dependency available Maven Central by default, use [search.maven.org](https://search.maven.org) to search for a library.

## Add a repository

If you want to use another Maven repository in addition to Maven central, i.e. [jitpack.io](https://search.maven.org) do:

```java
//DEPS jitpack,mavencentral
```
 
## `javac` and `java` options

If you need to set up some flags to javac or java when running, like memory, garbage collection or like the example below to enable preview features use `JAVA_OPTIONS` and `JAVA_OPTIONS`

``java
//JAVAC_OPTIONS --enable-preview -source 14 
//JAVA_OPTIONS --enable-preview
```

## Specific version of Java

JBang runs with the default Java installed but if you want to use a older or newer Java use `//JAVA version+`. i.e. to run with exactly Java 8 you use:

```java
//JAVA 8
```

If 17 or higher use:

```java
//JAVA 17+
```

## Examples of things to do in JBang 

Check out [Example repository](https://github.com/jbangdev/jbang-examples/tree/main/examples) and the [JBang AppStore](https://jbang.dev/appstore) for examples of what others done with JBang. 