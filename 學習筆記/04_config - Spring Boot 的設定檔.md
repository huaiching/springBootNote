# config - Spring Boot 的設定檔

`config` 資料夾，我們會在裡面擺放 `Spring Boot 啟動時` 要執行的 `配置類` 程式。

可以理解為 啟動時要自動執行的 `初始化檔案`。

要讓 Spring Boot 可以認識 這個檔案是 `配置類文件`，我們必須為其加上 `註解`。

- `@Configuration`：標示 檔案是 配置類文件。
- `@Bean`：告訴 Spring，這個方法返回的對象，幫我放進容器內進行管理。
  - 方法名稱 就是 容器中對象的名稱。
  - 通常用來進行 `依賴包的初始化設定`。

例如：我們剛才添加的 `SwaggerDocConfig` 就是一個 `配置類文件`。

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
