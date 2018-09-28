# codekata.com kata09

## Manual

The presented solution of kata #9 of _codekata.com_ (http://codekata.com/kata/kata09-back-to-the-checkout/) uses the following technologies for its implementation:

* Gradle
* Java 8
* Spring Boot 2

Start the service:

```
$ ./gradlew bootRun
```

Manual testing of the endpoint via curl:

```
$ curl -X POST -H "Content-Type: application/json" --data '{"items":[{"sku":"A","price":30.0},{"sku":"C","price":50.0},{"sku":"D","price":25.0},{"sku":"A","price":30.0},{"sku":"B","price":40.0},{"sku":"A","price":30.0},{"sku":"D","price":25.0},{"sku":"A","price":30.0},{"sku":"B","price":40.0},{"sku":"B","price":40.0},{"sku":"A","price":30.0},{"sku":"C","price":50.0},{"sku":"B","price":40.0},{"sku":"B","price":40.0},{"sku":"C","price":50.0},{"sku":"A","price":30.0},{"sku":"B","price":40.0},{"sku":"D","price":25.0},{"sku":"A","price":30.0},{"sku":"C","price":50.0}]}' "http://localhost:10000/checkout/"
```

The response of the request above should look like this:

```
{"success":true,"message":"Operation succeeded","total":575.0}
```