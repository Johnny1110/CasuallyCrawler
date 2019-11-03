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

*   [casualcrawl-parent](./casualcrawlerparent) 

    整個專案的聚合與繼承模組
    
<br>

*   [casualcrawl-requests](./casualcrawlerparent/casualcrawler-requests) 

    因為要爬很多網站，所以我會把 HttpClient 進行進一步封裝，
    並達到通用性。source code 都放在這裡。
    
<br>

*   [casualcrawl-pttcrawler](./casualcrawlerparent/casualcrawler-pttcrawler) 

    針對 ptt gossiping 網站的文章資料僅行分類爬取整理。最後統整入 SQLite 資料庫。