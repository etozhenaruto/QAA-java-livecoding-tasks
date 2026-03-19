# ✅ Ответы: Продвинутые темы REST Assured

## Задачи 2.1 - 2.5

### 2.1
```java
@Test
public void testExtractId() {
    Response response = given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users/1")
    .then()
        .extract()
        .response();
    
    int id = response.path("id");
}
```

### 2.2
```java
@Test
public void testExtractNamesList() {
    List<String> names = given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users")
    .then()
        .extract()
        .path("data.name");
}
```

### 2.3
```java
@Test
public void testFilterByPrice() {
    List<Map<String, Object>> expensive = given()
        .baseUri("https://api.example.com")
    .when()
        .get("/products")
    .then()
        .extract()
        .path("products.findAll { it.price > 100 }");
}
```

### 2.4
```java
@Test
public void testSumPrices() {
    Double total = given()
        .baseUri("https://api.example.com")
    .when()
        .get("/products")
    .then()
        .extract()
        .path("products.price.sum()");
}
```

### 2.5
```java
@Test
public void testFindFirstElectronics() {
    Map<String, Object> electronics = given()
        .baseUri("https://api.example.com")
    .when()
        .get("/products")
    .then()
        .extract()
        .path("products.find { it.category == 'electronics' }");
}
```

---

## Задачи 2.6 - 2.10

### 2.6
```java
public class User {
    private int id;
    private String name;
    private String email;
    
    // Конструкторы, геттеры, сеттеры
    public User() {}
    
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    // getters and setters...
}
```

### 2.7
```java
@Test
public void testPostWithPojo() {
    User user = new User(0, "John", "john@example.com");
    
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(user)
    .when()
        .post("/users")
    .then()
        .statusCode(201);
}
```

### 2.8
```java
@Test
public void testGetAsPojo() {
    User user = given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users/1")
    .then()
        .statusCode(200)
        .extract()
        .as(User.class);
}
```

### 2.9
```java
public class Address {
    private String city;
    private String street;
    private String zip;
    // getters, setters...
}

public class UserWithAddress {
    private int id;
    private String name;
    private Address address;
    // getters, setters...
}
```

### 2.10
```java
public class User {
    private int id;
    private String name;
    
    @JsonIgnore
    private String password;
    
    // getters, setters...
}
```

---

## Задачи 2.11 - 2.15

### 2.11
```java
RequestSpecification requestSpec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .setBasePath("/api/v1")
    .setContentType(ContentType.JSON)
    .build();
```

### 2.12
```java
ResponseSpecification responseSpec = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .expectContentType(ContentType.JSON)
    .build();
```

### 2.13
```java
@Test
public void testWithRequestSpec() {
    RequestSpecification spec = new RequestSpecBuilder()
        .setBaseUri("https://api.example.com")
        .setContentType(ContentType.JSON)
        .build();
    
    given()
        .spec(spec)
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 2.14
```java
@Test
public void testWithResponseSpec() {
    ResponseSpecification spec = new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectContentType(ContentType.JSON)
        .build();
    
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users")
    .then()
        .spec(spec);
}
```

### 2.15
```java
public class BaseTest {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://api.example.com";
        
        requestSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .build();
        
        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
    }
}
```

---

## Задачи 2.16 - 2.20

### 2.16
```java
public class HeaderFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        requestSpec.header("X-Custom-Header", "value");
        return ctx.next(requestSpec, responseSpec);
    }
}
```

### 2.17
```java
@Test
public void testLogOnError() {
    given()
        .baseUri("https://api.example.com")
        .filter(logRequestAndResponseIfValidationFails())
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 2.18
```java
@Test
public void testWithTimeout() {
    given()
        .baseUri("https://api.example.com")
        .timeout(10000)
    .when()
        .get("/slow-endpoint")
    .then()
        .statusCode(200);
}
```

### 2.19
```java
@Test
public void testWithRetry() {
    RetryFilter retryFilter = new RetryFilter(
        3,  // попытки
        1000,  // задержка
        (req, resp, ctx) -> resp.getStatusCode() >= 500
    );
    
    given()
        .baseUri("https://api.example.com")
        .filter(retryFilter)
    .when()
        .get("/flaky-endpoint")
    .then()
        .statusCode(200);
}
```

### 2.20
```java
@Test
public void testWithAllure() {
    given()
        .baseUri("https://api.example.com")
        .filter(new AllureRestAssured())
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

---

## Задачи 2.21 - 2.25

### 2.21
```java
@Test
public void testFormData() {
    given()
        .baseUri("https://api.example.com")
        .formParam("name", "John")
        .formParam("email", "john@example.com")
    .when()
        .post("/users")
    .then()
        .statusCode(201);
}
```

### 2.22
```java
@Test
public void testMultipartFile() {
    given()
        .baseUri("https://api.example.com")
        .multiPart("file", new File("test.txt"))
        .multiPart("description", "Test file")
    .when()
        .post("/upload")
    .then()
        .statusCode(200);
}
```

### 2.23
```java
@Test
public void testMultipleFiles() {
    given()
        .baseUri("https://api.example.com")
        .multiPart("file1", new File("test1.txt"))
        .multiPart("file2", new File("test2.txt"))
    .when()
        .post("/upload")
    .then()
        .statusCode(200);
}
```

### 2.24
```java
@Test
public void testFormDataWithMap() {
    Map<String, String> formData = new HashMap<>();
    formData.put("name", "John");
    formData.put("email", "john@example.com");
    
    given()
        .baseUri("https://api.example.com")
        .formParams(formData)
    .when()
        .post("/users")
    .then()
        .statusCode(201);
}
```

### 2.25
```java
@Test
public void testMultipartJson() {
    String jsonContent = "{\"name\": \"test\"}";
    
    given()
        .baseUri("https://api.example.com")
        .multiPart("data", "data.json", jsonContent, "application/json")
    .when()
        .post("/upload")
    .then()
        .statusCode(200);
}
```
