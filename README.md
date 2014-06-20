TOC_HW3
=======
/* Theory of Computation HW3
 * 程式名稱： TocHw3.java
 * 程式作者： 資訊三乙 F74006161 劉彥良
 * 程式輸入： java -jar TocHw3.jar URL 鄉鎮市區 路名 交易年份
 * 程式輸出： 該路段交易總價元之總平均(integer)
 * 程式目的： 藉由爬取DataGarage之實價登錄資料，計算所查詢之路段總平均
 * 程式概念： 透過reader將資料從URL上爬取下來，再經由搜尋JSON格式的KEY取得相對應的VALUE，
 * 判斷VALUE是否符合所查詢，符合者將其總價元加入sum，最後取總平均即為所求。
 */
