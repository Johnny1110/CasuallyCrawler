# CasuallyCrawler

<br>

--------------------------------------------------

<br>

## 簡介

CasuallyCrawler 是一個我用來練習 java 爬蟲的 side project。搭配使用 Maven 的聚合繼承模組化開法，
針對各種網站爬取資料。使用 Spring 開發。

爬蟲套件使用 apache 的 HttpClient，資料解析使用 JSoup。

<br>

## 模組

*   [casualcrawl-parent](./casualcrawler-parent) 

    整個專案的聚合與繼承模組
    
<br>

*   [casualcrawl-requests](./casualcrawler-parent/casualcrawler-requests) 

    因為要爬很多網站，所以我會把 HttpClient 進行進一步封裝，
    並達到通用性。source code 都放在這裡。
    接口統一使用 com.casualsource.casualcrawler.requests.Request，可以使用工廠方法取得實例 :
    
    com.casualsource.casualcrawler.requests.HttpRequests
    
<br>


*   [casualcrawl-parser](./casualcrawler-parent/casualcrawler-parser) 

    封裝 Jsoup，使用統一接口 com.casualsource.casualcrawler.parser.HtmlParser。可以使用工廠方法
    取得實例 :
    
    com.casualsource.casualcrawler.parser.HtmlParsers
    
<br>

*   [casualcrawl-datawriter](./casualcrawler-parent/casualcrawler-datawriter) 

    針對爬網所開發的 dataWriter。目前暫實只支援 jdbc writer。搭配類 JPA 的標註屬性映射 database 。達到類似 ORM 的效果，底層使用 spring jdbc template 實作。
    
    接口統一使用 com.casualsource.casualcrawler.datawriter.Datawriter。可以使用工廠方法取得實例 :
    
    com.casualsource.casualcrawler.datawriter.DataWriterImpl.Datawriters
    
<br>

*   [casualcrawl-pttcrawler](./casualcrawler-parent/casualcrawler-pttcrawler) 

    針對 ptt gossiping 網站的文章資料僅行分類爬取整理。最後統整入資料庫。由 DataKeeper 類別的 main 方法啟動。