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




# Sample Curl commands:

curl "http://api.eventful.com/rest/events/search?category=music&date=This%20Week&location=London&include=categories&page_size=10&page_number=1&sort_order=date&sort_direction=ascending&app_key=9WmHkKmHCDvrxGLX" > src/main/test/resources/events-search-music-this-week.xml


