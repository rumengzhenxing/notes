# HTTP 缓存
---
缓存对于移动端是非常重要的存在。
  - 减少请求次数，减小服务器压力.
  - 本地数据读取速度更快，让页面不会空白几百毫秒。
  - 在无网络的情况下提供数据。

缓存一般由服务器控制(通过某些方式可以本地控制缓存，比如向过滤器添加缓存控制信息)。通过在请求头添加下面几个字端：

### Request

  请求头字段 | 意义
  ---- | ---
  If-Modified-Since: Sun, 03 Jan 2016 03:47:16 GMT | 缓存文件的最后修改时间
  If-None-Match: "3415g77s19tc3:0" |  缓存文件的Etag(Hash)值
  Cache-Control: no-cache | 不使用缓存
  Pragma: no-cache | 不使用缓存

### Response

  请求头字段 | 意义
  ---- | ---
  Cache-Control: public | 响应被共有缓存，移动端无用
  Cache-Control: private | 响应被私有缓存，移动端无用
  Cache-Control:no-cache | 不缓存
  Cache-Control:no-store | 不缓存
  Cache-Control: max-age=60 | 60秒之后缓存过期（相对时间）
  Date: Sun, 03 Jan 2016 04:07:01 GMT | 当前response发送的时间
  Expires: Sun, 03 Jan 2016 07:07:01 GMT | 缓存过期的时间（绝对时间）
  Last-Modified: Sun, 03 Jan 2016 04:07:01 GMT | 服务器端文件的最后修改时间
  ETag: "3415g77s19tc3:0" | 服务器端文件的Etag[Hash]值

### 客户端发起请求步骤

  ![](/img/http_c.png)

---
### OkHttp缓存机制

  OkHttp缓存主要是在CacheStrategy 和 HttpEngine，其中 CacheStrategy 包含缓存机制的所有逻辑，HttpEngine 是 CacheStrategy 被调用的地方。
  - CacheStrategy.Factory 理解缓存候选响应的报头并将其转换为类成员的构造器
  - CacheStrategy.getCandidate() - 检查缓存候选响应，如果需要的话讲修改原始请求的报头

#### 什么是缓存候选响应？

  第一次进行 HTTP 请求时不会存在任何已缓存内容，相关 API 只在有缓存内容的时候才会被调用。

  一旦响应被存储我们就能尝试将它用到后续的调用中，当然了，我们不可能将所有响应码对应的响应都存储下来。我们只存储以下响应码对应的响应：200, 203, 204, 300, 301, 404, 405, 410, 414, 501, 308。除此以外还有 302, 307，但存储它们对应的响应时必须满足以下条件之一：
    - 包含 Expires header OR
    - CacheControl 包含 max-age OR
    - CacheControl 包含 public OR
    - CacheControl 包含 private
    - 包含 Expires 报头
    - CacheControl 包含 max-age
    - CacheControl 包含 public
    - CacheControl 包含 private

  需要注意：OkHttp 的缓存机制不支持缓存部分报文内容。
  当我们重复某个请求，OkHttp 会判断是否已经有已缓存的响应

#### CacheStrategy 是什么？

  CacheStrategy 需要一个新的报文和一个缓存候选响应，评估这两个 HTTP 报头是否有效并比较它们。

  首先，存放在缓存候选响应报头中的部分成员是：
      - Date
      - Expires
      - Last-Modified
      - ETag
      - Age

  下面是需要检查的条件的汇总列表：
    1. 判断缓存候选响应是否存在。
    2. 如果接收的是 HTTPS 请求，如果需要的话，判断缓存候选响应是否已进行握手。
    3. 判断缓存候选响应是否已缓存；这和 OkHttp 存储响应时完成的工作是相同的。
    4. 如果没有缓存，在请求报头的 Cache-Control 中检查对应内容，如果该标记为 true，缓存候选响应将不会被使用，后面的检查也会跳过。
    5. 在请求报头中查找 If-Modified-Since 或 If-None-Match，如果找到其中之一，缓存候选响应将不会被使用，后面的检查也会跳过。
    6. 进行一些计算以得到诸如缓存响应缓存响应存活时间，缓存存活时间，缓存最大失活时间。我不希望在此解释所有对应实现的细节，因为这会让博文变得冗长，你只需要知道以上提到的报文内容（例如：Date, Expires 等等…）还有请求 Cache-Control 中的 max-age, min-fresh, max-stale 这部分相关的计算耗时是毫秒级的。完成检查最简单的办法是写这样的伪代码：if ("cache candidate's no-cache" && "cache candidate's age" + "request's min-fresh" < "cache candidate's fresh lifetime" + "request's max-stale").
    7. 在需要进行网络操作时，下一次检查会判断它是否为“条件请求”。条件请求指的是：发送报文请求服务器响应，而服务器可能会也可能不会返回新的内容，或让我们使用已缓存的报文。(如果上述条件被满足，那么已缓存的响应报文将会被使用，此条的检查将跳过。)


---
### 参考
  - [剖析OkHttp缓存机制](https://github.com/hehonghui/android-tech-frontier/blob/master/issue-42/%E5%89%96%E6%9E%90okhttp%E7%BC%93%E5%AD%98%E6%9C%BA%E5%88%B6.md)
  - [Android网络请求心路历程](http://www.jianshu.com/p/3141d4e46240)
