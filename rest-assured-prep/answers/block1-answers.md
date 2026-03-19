# ✅ Ответы: Основы REST Assured

## Задачи 1.1 - 1.5

### 1.1
```java
@Test
public void testGetUsers() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 1.2
```java
@Test
public void testGetUserById() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users/1")
    .then()
        .statusCode(200)
        .body("id", equalTo(1));
}
```

### 1.3
```java
@Test
public void testGetUsersWithPage() {
    given()
        .baseUri("https://api.example.com")
        .queryParam("page", 1)
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 1.4
```java
@Test
public void testUsersNotEmpty() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users")
    .then()
        .statusCode(200)
        .body("", hasSize(greaterThan(0)));
}
```

### 1.5
```java
@Test
public void testContentType() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users")
    .then()
        .statusCode(200)
        .contentType(ContentType.JSON);
}
```

---

## Задачи 1.6 - 1.10

### 1.6
```java
@Test
public void testCreateUser() {
    Map<String, String> body = new HashMap<>();
    body.put("name", "John");
    body.put("email", "john@example.com");
    
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(body)
    .when()
        .post("/users")
    .then()
        .statusCode(201);
}
```

### 1.7
```java
@Test
public void testCreateUserStatusCode() {
    Map<String, String> body = new HashMap<>();
    body.put("name", "John");
    
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(body)
    .when()
        .post("/users")
    .then()
        .statusCode(201);
}
```

### 1.8
```java
@Test
public void testCreateUserId() {
    Map<String, String> body = new HashMap<>();
    body.put("name", "John");
    
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(body)
    .when()
        .post("/users")
    .then()
        .statusCode(201)
        .body("id", notNullValue());
}
```

### 1.9
```java
@Test
public void testCreateUserWithJsonString() {
    String jsonBody = "{\"name\": \"John\", \"email\": \"john@example.com\"}";
    
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(jsonBody)
    .when()
        .post("/users")
    .then()
        .statusCode(201);
}
```

### 1.10
```java
@Test
public void testCreateUserNameMatch() {
    Map<String, String> body = new HashMap<>();
    body.put("name", "John");
    
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(body)
    .when()
        .post("/users")
    .then()
        .statusCode(201)
        .body("name", equalTo("John"));
}
```

---

## Задачи 1.11 - 1.15

### 1.11
```java
@Test
public void testUpdateUser() {
    Map<String, String> body = new HashMap<>();
    body.put("name", "John Updated");
    
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(body)
    .when()
        .put("/users/1")
    .then()
        .statusCode(200);
}
```

### 1.12
```java
@Test
public void testPatchUser() {
    Map<String, String> body = new HashMap<>();
    body.put("name", "Updated");
    
    given()
        .baseUri("https://api.example.com")
        .contentType(ContentType.JSON)
        .body(body)
    .when()
        .patch("/users/1")
    .then()
        .statusCode(200);
}
```

### 1.13
```java
@Test
public void testDeleteUser() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .delete("/users/1")
    .then()
        .statusCode(204);
}
```

### 1.14
```java
@Test
public void testGetUserWithPathParam() {
    given()
        .baseUri("https://api.example.com")
        .pathParam("id", 1)
    .when()
        .get("/users/{id}")
    .then()
        .statusCode(200);
}
```

### 1.15
```java
@Test
public void testMultipleFields() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users/1")
    .then()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("name", equalTo("John"))
        .body("email", containsString("@"));
}
```

---

## Задачи 1.16 - 1.20

### 1.16
```java
@Test
public void testWithAuthHeader() {
    given()
        .baseUri("https://api.example.com")
        .header("Authorization", "Bearer token123")
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 1.17
```java
@Test
public void testWithCookie() {
    given()
        .baseUri("https://api.example.com")
        .cookie("sessionId", "abc123")
    .when()
        .get("/profile")
    .then()
        .statusCode(200);
}
```

### 1.18
```java
@Test
public void testResponseHeader() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users")
    .then()
        .statusCode(200)
        .header("Content-Type", "application/json");
}
```

### 1.19
```java
@Test
public void testMultipleHeaders() {
    given()
        .baseUri("https://api.example.com")
        .header("Authorization", "Bearer token")
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
    .when()
        .get("/users")
    .then()
        .statusCode(200);
}
```

### 1.20
```java
@Test
public void testWithLogging() {
    given()
        .baseUri("https://api.example.com")
        .log().all()
    .when()
        .get("/users")
    .then()
        .log().all()
        .statusCode(200);
}
```

---

## Задачи 1.21 - 1.25

### 1.21
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

### 1.22
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

### 1.23
```java
@Test
public void testExtractData() {
    Response response = given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users/1")
    .then()
        .statusCode(200)
        .extract()
        .response();
    
    int id = response.path("id");
    String name = response.path("name");
}
```

### 1.24
```java
@Test
public void testArrayInResponse() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users")
    .then()
        .statusCode(200)
        .body("id", hasItem(1))
        .body("name", hasItem("John"));
}
```

### 1.25
```java
@Test
public void testNestedObjects() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users/1")
    .then()
        .statusCode(200)
        .body("address.city", equalTo("Moscow"))
        .body("address.street", notNullValue());
}
```
