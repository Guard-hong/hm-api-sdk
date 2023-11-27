<p align="center">
    <img src=https://img.qimuu.icu/typory/logo.gif width=188/>
</p>

<h1 align="center">Hm-API-SDK</h1>
<p align="center"><strong>Hm-API 接口开放平台开发者工具包</strong></p>

<div align="center">
    <img alt="Maven" src="https://raster.shields.io/badge/Maven-3.8.1-red.svg"/>
   <img alt="SpringBoot" src="https://raster.shields.io/badge/SpringBoot-2.7+-green.svg"/>
  <a href="https://github.com/qimu666/qi-api-sdk" target="_blank"><img src='https://img.shields.io/github/forks/qimu666/qi-api-sdk' alt='GitHub forks' class="no-zoom"></a>
  <a href="https://github.com/qimu666/qi-api-sdk" target="_blank"><img src='https://img.shields.io/github/stars/qimu666/qi-api-sdk' alt='GitHub stars' class="no-zoom"></a>
</div>

### 导航 🧭

- **[Hm-API 接口开放平台 🔗](http://124.222.245.207/)**

###  快速开始 🚀

**要开始使用 Hm-API-SDK，您需要按照以下简单进行操作:**

#### 1. 引入依赖坐标

```xml
<dependency>
   <groupId>com.hcj</groupId>
   <artifactId>hm-api-sdk</artifactId>
   <version>0.0.3</version>
</dependency>   
```


#### 2. 前往[Hm-API 接口开放平台](http://124.222.245.207/) 获取开发者密钥对

#### 3. 初始化客户端QiApiClient对象

- 方法 1 ：主动实例化客户端

  ```java
  String accessKey = "你的 accessKey";
  String secretKey = "你的 secretKey";
  HmApiClient client = new HmApiClient(accessKey, secretKey);
  ```

- 方法 2 ：通过配置文件注入对象

  - yml

    ```yml
    # Hm-API 配置
    hm:
      api:
        client:
          access-key: 你的 accessKey
          secret-key: 你的 secretKey
          # 本地网关地址，可修改为自己的网关，用于本地测试，线上网关地址等，不配置默认平台的网关
         # host: http://localhost:8090/api
    ```
    
  - properties
  
    ```properties
    qi.api.client.access-key=你的 accessKey
    qi.api.client.secret-key=你的 secretKey
    ```

#### 4. 使用API服务

   ```java
    @Resource
    private HmApiService hmApiService;
   ```

#### 5. 发起请求示例

示例：随机毒鸡汤

- 示例一 ：**通过配置文件 推荐👍**

```java
try {
    PoisonousChickenSoupResponse poisonousChickenSoup = hmApiService.getPoisonousChickenSoup();
    System.out.println("poisonousChickenSoup = " + poisonousChickenSoup);
} catch (ApiException e) {
  log.error(e.getMessage());
}
```

- 示例二 ：主动注入
```java
try {
        HmApiService hmApiService = new HmApiService("你的 accessKey", "你的 secretKey");
    PoisonousChickenSoupResponse poisonousChickenSoup = HmApiService.getPoisonousChickenSoup(qiApiClient);
    System.out.println("poisonousChickenSoup = " + poisonousChickenSoup);
} catch (ApiException e) {
    log.error(e.getMessage());
}
```

响应：

```json
{
  "text": "不知道你混什么圈，反正我的是混黑眼圈。"
}
```


```java
@GetMapping("/getPoisonousChickenSoup")
public BaseResponse<PoisonousChickenSoupResponse> getPoisonousChickenSoup() {
        PoisonousChickenSoupResponse poisonousChickenSoup = null;
        try {
        poisonousChickenSoup = mmApiService.getPoisonousChickenSoup();
        } catch (ApiException e) {
        throw new ApiException(e.getCode(), e.getMessage());
        }
        return ResultUtils.success(poisonousChickenSoup);
        }
```

响应示例：

```json
{
  "code": 0,
  "data": {
    "text": "人类三大错觉—手机响了，有人敲门，他（她）喜欢我。"
  },
  "message": "ok"
}
```
- **更多接口详见：[Hm-API-DOC 开发者文档](http://124.222.245.207/)**

### 贡献 🤝

如果您想为 **[Hm-API 接口开放平台](http://124.222.245.207/)**  做出贡献，请随时提交拉取请求。我们始终在寻找方法来改进项目，使其对像您这样的开发者更有用。

### 联系我们 📩

如果您对 **[Hm-API 接口开放平台](http://124.222.245.207/)**  平台有任何问题或建议，请随时联系我们:📩邮箱：3341073630@qq.com。

感谢您使用 **[Hm-API 接口开放平台](http://124.222.245.207/)**  ！ 😊
