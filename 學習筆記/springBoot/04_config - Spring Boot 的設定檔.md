# `config` - Spring Boot 的設定檔案說明

在 `config` 資料夾中，我們通常會放置 Spring Boot 在啟動時需要執行的「**配置類**（Configuration Class）」程式碼。

這些類別可以被視為「**初始化設定檔案**」，用來設定應用啟動時所需的元件、依賴或第三方工具（如 Swagger、CORS、資料來源等）。

為了讓 Spring Boot 認得某個類別是設定用途的配置類，我們需要加上特定的註解：

- `@Configuration`：標示該類別為 Spring 的配置類，啟動時會被載入並執行其中定義的設定。
- `@Bean`：用來告訴 Spring，「這個方法所回傳的物件」要註冊進 Spring 的應用程式容器（Application Context）中進行管理。
  - 方法名稱將成為該 Bean 的名稱（可自訂）。
  - 常用於初始化第三方套件或設定自定義元件。

---

### 範例：Swagger 設定檔

以下為一個使用 Swagger 產生 API 文件的配置類，`SwaggerDocConfig`：

```java
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerDocConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot API 練習專案")
                        .version("v0.0.1")
                );
    }
}
```

在這個範例中：

- `@Configuration` 告訴 Spring Boot，這是一個配置類。

- `@Bean` 宣告 `baseOpenAPI()` 方法所建立的 `OpenAPI` 實例，會被註冊到 Spring 容器中。

- `@OpenAPIDefinition` 是 Swagger 提供的註解，用來標示這是一個 OpenAPI 的設定入口。
