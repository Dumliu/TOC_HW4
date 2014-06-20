Theory of Computation HW4
=======
 * 程式名稱： TocHw4.java
 * 程式作者： 資訊三乙 F74006161 劉彥良
 * 程式輸入： java -jar TocHw4.jar URL
 * 程式輸出： 該URL中最多不同月份交易筆數的路名，及其最大最小交易價(String,Integer,Integer)
 * 程式目的： 藉由爬取DataGarage之實價登錄資料，計算最多不同月份交易筆數，再算出其最大最小交易價
 * 程式概念： 透過reader將資料從URL上爬取下來，再經由搜尋JSON格式的KEY取得相對應的VALUE，
 * 判斷VALUE是否符合所查詢，並記錄其在不同月分出現的次數；取得最大不同月份出現次數的資料，
 * 再計算其最大及最小的交易價格，印出結果即為所求。
