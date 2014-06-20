/* Theory of Computation HW3
 * 程式名稱： TocHw3.java
 * 程式作者： 資訊三乙 F74006161 劉彥良
 * 程式輸入： java -jar TocHw3.jar URL 鄉鎮市區 路名 交易年份
 * 程式輸出： 該路段交易總價元之總平均(integer)
 * 程式目的： 藉由爬取DataGarage之實價登錄資料，計算所查詢之路段總平均
 * 程式概念： 透過reader將資料從URL上爬取下來，再經由搜尋JSON格式的KEY取得相對應的VALUE，
 * 判斷VALUE是否符合所查詢，符合者將其總價元加入sum，最後取總平均即為所求。
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class TocHw4 {

	public static void main(String[] args)
	{
		try {
			URL url = new URL(args[0]);
			Reader myReader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
			JSONTokener myTokener = new JSONTokener(myReader);
			JSONArray myArray = new JSONArray(myTokener);
			JSONObject myObject;

			Pattern myPattern = Pattern.compile(".*(大道|路|街)");
			Pattern myPattern2 = Pattern.compile(".*([0-9]+)巷");
			Pattern myPattern3 = Pattern.compile(".*巷");
			Matcher myMatcher;
			
			HashMap<String, Integer> roadCount = new HashMap<String, Integer>();
			HashMap<String, Integer> roadMonth = new HashMap<String, Integer>();
			int[][] monthExist = new int[2000][50];
			int[] monthExistTop = new int[2000];
			
			String tempName="";
			
			int tempCount,monthCount=0,ansCount=0;
			boolean exist=false;
			
			for (int i = 0; i < myArray.length(); i++) {
				exist=false;
				
				//搜尋大道、路、街
				myObject = myArray.getJSONObject(i);
				myMatcher = myPattern.matcher(myObject.getString("土地區段位置或建物區門牌").toString());

				//若找不到大道、路、街，則找非數字巷
				if(!myMatcher.find())
				{
					myMatcher = myPattern2.matcher(myObject.getString("土地區段位置或建物區門牌").toString());
					
					//找到非數字巷後排除掉，剩再找巷，即取得國字巷
					if(myMatcher.find()) continue;
					else
					{
						myMatcher = myPattern3.matcher(myObject.getString("土地區段位置或建物區門牌").toString());
						if(!myMatcher.find()) continue;
					}
				}

				tempName = myMatcher.group();
				
				if(roadMonth.get(tempName)==null) roadMonth.put(tempName, monthCount++);
				
				for(int j=0;j<monthExistTop[roadMonth.get(tempName)];j++)
					if(monthExist[roadMonth.get(tempName)][j]==myObject.getInt(("交易年月"))) exist=true;
				if(exist) continue;
				
				monthExist[roadMonth.get(tempName)][monthExistTop[roadMonth.get(tempName)]++] = myObject.getInt(("交易年月"));
				
				if(roadCount.get(tempName)==null) 
					roadCount.put(tempName, 1);
				else
				{
					tempCount = roadCount.remove(tempName);
					roadCount.put(tempName, tempCount+1);
				}
				//System.out.println(tempName+"="+roadCount.get(tempName)+" "+myObject.getInt(("交易年月"))+" "+roadMonth.get(tempName));
			}
			
			//找出不同月份的MAX值
			int maxDistinctMonth=0;
			for (Object key : roadMonth.keySet())
				if(monthExistTop[roadMonth.get(key)]>maxDistinctMonth) maxDistinctMonth = monthExistTop[roadMonth.get(key)];
			
			//根據MAX值找到對應的結果
			int tempPrice,highestPrice,lowestPrice;
			for (Object key : roadMonth.keySet())
				if(monthExistTop[roadMonth.get(key)]==maxDistinctMonth)
				{
					highestPrice=0;
					lowestPrice=999999999;
					tempName = key.toString();
					
					for (int i = 0; i < myArray.length(); i++)
					{
						myObject = myArray.getJSONObject(i);
						myMatcher = myPattern.matcher(myObject.getString("土地區段位置或建物區門牌").toString());
						
						if(!myMatcher.find()||!myMatcher.group().equals(tempName)) continue;

						tempPrice = myObject.getInt("總價元");
						if(tempPrice>highestPrice) highestPrice = tempPrice;
						if(tempPrice<lowestPrice) lowestPrice = tempPrice;
					}
					System.out.println(tempName+", 最高成交價:"+highestPrice+", 最低成交價:"+lowestPrice);
				}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
