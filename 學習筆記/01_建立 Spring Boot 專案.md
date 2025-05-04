# 建立 Spring Boot 專案

## Spring Initalizr - Spring Boot 專案初始化工具

1. 說明
   Spring Initializr 是 Spring 官方提供的 Spring 初始化工具，可以快速建立 Spring Boot 專案。

2. 連結
   
   https://start.spring.io/

3. 設定 Spring 官方依賴
   
   - `Spring Web`：Spring Boot 的 Web服務模組。
   
   - `Spring Data JPA`：Spring Boot 的 數據操作模組 (內含 `hibernate`)，可簡化 資料庫 CRUD 操作。
   
   - `H2 Database`：簡易的 DB 數據庫 。
   
   - `Spring Reactive Web`：Spring Boot 的 非阻塞Web框架，主要負責 API 請求，主要會使用裡面的 `WebClient` 進行 API 呼叫。
   
   ![](image\springInitializr.png)

## maven 設定

為了配合 `公司倉庫` 的版本，我們需要對 專案的 `pom.xml` 進行調整。

調整前，請先使用 InteliJ 開啟專案，並開啟 `pom.xml`。

1. 調降 Spring Boot 的版本
   
   - 將 `spring-boot-starter-parent` 版本調降為 `2.7.18`。
   
   - 將 `Java` 調降為 `11`。
   
   ![](image\pom.png)

2. 安裝 `swagger doc`
   
   1. 於 `pom.xml` 的 `<dependencies>` 裡面填入 swagger 的相關設定
   
   ```xml
           <!--  Swagger Doc -->        
           <dependency>
               <groupId>org.springdoc</groupId>
               <artifactId>springdoc-openapi-ui</artifactId>
               <version>1.8.0</version>
           </dependency>
           <dependency>
               <groupId>org.webjars</groupId>
               <artifactId>swagger-ui</artifactId>
               <version>5.11.10</version>
           </dependency>
   ```
   
   2. 開啟 `src/main/resources` 的 `application.properties`，並填入下面設定
      
      ```properties
      spring.port=9010
      
      springdoc.api-docs.enabled=true
      springdoc.api-docs.path=/api-docs
      springdoc.swagger-ui.version=5.11.10
      ```
   
   3. 設定 Swagger Doc 的 標題文字
      
      於 `src/main/java/com/example/api/` 新增資料夾 `config`，並新增檔案 `SwaggerDocConfig.java` 並設定下面的內容。
      
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

3. 安裝 報表相關 依賴
   
   - `Jxls`：產生 Excel 的相關依賴
   
   - `poi-tl`：產生 Word 的相關依賴
   
   - `fr.opensagres.poi.xwpf.converter.pdf`：Word 轉 PDF 的相關依賴
   
   - `itextpdf`：PDF 合併的相關依賴 (使用 最後一個免費版)
   
   ```xml
           <!-- Jxls -->
           <dependency>
               <groupId>org.jxls</groupId>
               <artifactId>jxls</artifactId>
               <version>2.12.0</version>
           </dependency>
           <dependency>
               <groupId>org.jxls</groupId>
               <artifactId>jxls-poi</artifactId>
               <version>2.12.0</version>
           </dependency>
           <!-- poi-tl -->
           <dependency>
               <groupId>com.deepoove</groupId>
               <artifactId>poi-tl</artifactId>
               <version>1.12.1</version>
           </dependency>
           <!-- fr.opensagres.poi.xwpf.converter.pdf -->
           <dependency>
               <groupId>fr.opensagres.xdocreport</groupId>
               <artifactId>fr.opensagres.poi.xwpf.converter.pdf</artifactId>
               <version>2.0.3</version>
           </dependency>
           <!-- itextpdf -->
           <dependency>
               <groupId>com.itextpdf</groupId>
               <artifactId>itextpdf</artifactId>
               <version>5.5.13.2</version>
           </dependency>
   ```

## 啟動專案

完成上述設定後，我們可以是著來啟動專案

- 前往 `src/main/java/com/example/api/` 並運行 `ApiDemoApplication.java`，這個文件就是 `Spring Boot 的啟動檔案`。

- `@SpringBootApplication` 這個註解 代表 該檔案為 Spring Boot 的啟動檔案`。

```java
@SpringBootApplication
public class ApiDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiDemoApplication.class, args);
    }

}
```

運行後，於瀏覽器中 前往 http://localhost:9010/swagger-ui/index.html 即可開啟 本專案的 Swagger UI 頁面。

- 下方顯示 `No operations defined in spec` 是因為目前我們沒有設定任何服務。

- 頁面上方 會顯示 我們剛剛於 `SwaggerDocConfig` 設定的相關內容。

![swagger.png](image\swagger.png)
