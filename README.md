# whatson


### Build executable jar file

```
mvn package
```

### Build executable and skip tests

```
mvn package  -Dmaven.test.skip=true
```

### Run spring boot webapp

```
java -jar target/whatson-0.0.1.jar
```

### ALl in one command

```
mvn package  -Dmaven.test.skip=true && java -jar target/whatson-0.0.1.jar
```