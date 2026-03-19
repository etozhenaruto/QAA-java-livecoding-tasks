# ✅ Ответы: Аутентификация и фильтры

## Задачи 3.1 - 3.5

### 3.1
```java
@Test
public void testBasicAuth() {
    given()
        .baseUri("https://api.example.com")
        .auth()
        .basic("username", "password")
    .when()
        .get("/protected")
    .then()
        .statusCode(200);
}
```

### 3.2
```java
@Test
public void testBearerToken() {
    given()
        .baseUri("https://api.example.com")
        .auth()
        .oauth2("your-bearer-token")
    .when()
        .get("/protected")
    .then()
        .statusCode(200);
}
```

### 3.3
```java
@Test
public void testApiKeyInHeader() {
    given()
        .baseUri("https://api.example.com")
        .header("X-API-Key", "your-api-key")
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 3.4
```java
@Test
public void testApiKeyInQuery() {
    given()
        .baseUri("https://api.example.com")
        .queryParam("api_key", "your-api-key")
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 3.5
```java
@Test
public void testPreemptiveBasicAuth() {
    given()
        .baseUri("https://api.example.com")
        .auth()
        .preemptive()
        .basic("username", "password")
    .when()
        .get("/protected")
    .then()
        .statusCode(200);
}
```

---

## Задачи 3.6 - 3.10

### 3.6
```java
public class TimestampFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        requestSpec.header("X-Timestamp", String.valueOf(System.currentTimeMillis()));
        return ctx.next(requestSpec, responseSpec);
    }
}
```

### 3.7
```java
public class TimingFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        long start = System.currentTimeMillis();
        Response response = ctx.next(requestSpec, responseSpec);
        long end = System.currentTimeMillis();
        System.out.println("Request took: " + (end - start) + "ms");
        return response;
    }
}
```

### 3.8
```java
@Test
public void testLogOnValidationFail() {
    given()
        .baseUri("https://api.example.com")
        .filter(logRequestAndResponseIfValidationFails())
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 3.9
```java
public class StatusCodeFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        if (response.getStatusCode() >= 500) {
            throw new AssertionError("Server error: " + response.getStatusCode());
        }
        return response;
    }
}
```

### 3.10
```java
@Test
public void testMultipleFilters() {
    given()
        .baseUri("https://api.example.com")
        .filter(new TimingFilter())
        .filter(new HeaderFilter())
        .filter(new AllureRestAssured())
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

---

## Задачи 3.11 - 3.15

### 3.11
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

### 3.12
```java
@Test
@Feature("User Management")
@Story("Get Users")
@Severity(SeverityLevel.CRITICAL)
public void testWithAllureAnnotations() {
    given()
        .baseUri("https://api.example.com")
        .filter(new AllureRestAssured())
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 3.13
```java
@DataProvider(name = "userData")
public Object[][] userData() {
    return new Object[][] {
        {"John", "john@example.com"},
        {"Jane", "jane@example.com"},
        {"Bob", "bob@example.com"}
    };
}
```

### 3.14
```java
@Test(dataProvider = "userData")
public void testCreateUser(String name, String email) {
    Map<String, String> body = new HashMap<>();
    body.put("name", name);
    body.put("email", email);
    
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(body)
    .when()
        .post("/users")
    .then()
        .statusCode(201)
        .body("name", equalTo(name));
}
```

### 3.15
```java
@Test
public void testSetup() {
    // Setup данные
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body("{\"name\": \"Test\"}")
    .when()
        .post("/users")
    .then()
        .statusCode(201);
}

@Test(dependsOnMethods = "testSetup")
public void testMain() {
    // Основной тест который зависит от setup
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

---

## Задачи 3.16 - 3.20

### 3.16
```java
public class OrdersApiPage {
    private static final String ORDERS_ENDPOINT = "/orders";
    
    public Response getAllOrders() {
        return given()
            .when()
            .get(ORDERS_ENDPOINT)
            .then()
            .extract()
            .response();
    }
    
    public Response getOrderById(int id) {
        return given()
            .pathParam("id", id)
            .when()
            .get(ORDERS_ENDPOINT + "/{id}")
            .then()
            .extract()
            .response();
    }
    
    public Response createOrder(Map<String, Object> orderData) {
        return given()
            .contentType(ContentType.JSON)
            .body(orderData)
            .when()
            .post(ORDERS_ENDPOINT)
            .then()
            .extract()
            .response();
    }
    
    public Response deleteOrder(int id) {
        return given()
            .pathParam("id", id)
            .when()
            .delete(ORDERS_ENDPOINT + "/{id}")
            .then()
            .extract()
            .response();
    }
}
```

### 3.17
```java
public Response getAllOrders() {
    return given()
        .baseUri("https://api.example.com")
    .when()
        .get("/orders")
    .then()
        .extract()
        .response();
}
```

### 3.18
```java
public Response createOrder(Map<String, Object> orderData) {
    return given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(orderData)
    .when()
        .post("/orders")
    .then()
        .extract()
        .response();
}
```

### 3.19
```java
@Test
public void testCreateOrder() {
    OrdersApiPage ordersApi = new OrdersApiPage();
    
    Map<String, Object> orderData = new HashMap<>();
    orderData.put("productId", 1);
    orderData.put("quantity", 2);
    
    Response response = ordersApi.createOrder(orderData);
    response.then()
        .statusCode(201)
        .body("id", notNullValue());
}
```

### 3.20
```java
public class BaseTest {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = System.getProperty("base.uri", "https://api.example.com");
        RestAssured.basePath = "/api/v1";
        
        requestSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .addHeader("Authorization", "Bearer " + getToken())
            .addFilter(new AllureRestAssured())
            .build();
        
        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();
    }
    
    private static String getToken() {
        // Логика получения токена
        return "token123";
    }
}
```
