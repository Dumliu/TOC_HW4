/* Theory of Computation HW4
 * �{���W�١G TocHw4.java
 * �{���@�̡G ��T�T�A F74006161 �B�ۨ}
 * �{����J�G java -jar TocHw4.jar URL
 * �{����X�G ��URL���̦h���P���������ƪ����W�A�Ψ�̤j�̤p�����(String,Integer,Integer)
 * �{���ت��G �ǥѪ���DataGarage������n����ơA�p��̦h���P���������ơA�A��X��̤j�̤p�����
 * �{�������G �z�Lreader�N��ƱqURL�W�����U�ӡA�A�g�ѷj�MJSON�榡��KEY���o�۹�����VALUE�A
 * �P�_VALUE�O�_�ŦX�Ҭd�ߡA�ðO����b���P����X�{�����ơF���o�̤j���P����X�{���ƪ���ơA
 * �A�p���̤j�γ̤p���������A�L�X���G�Y���ҨD�C
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

			Pattern myPattern = Pattern.compile(".*(�j�D|��|��)");
			Pattern myPattern2 = Pattern.compile(".*([0-9]+)��");
			Pattern myPattern3 = Pattern.compile(".*��");
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
				
				//�j�M�j�D�B���B��
				myObject = myArray.getJSONObject(i);
				myMatcher = myPattern.matcher(myObject.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").toString());

				//�Y�䤣��j�D�B���B��A�h��D�Ʀr��
				if(!myMatcher.find())
				{
					myMatcher = myPattern2.matcher(myObject.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").toString());
					
					//���D�Ʀr�ѫ�ư����A�ѦA��ѡA�Y���o��r��
					if(myMatcher.find()) continue;
					else
					{
						myMatcher = myPattern3.matcher(myObject.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").toString());
						if(!myMatcher.find()) continue;
					}
				}

				tempName = myMatcher.group();
				
				if(roadMonth.get(tempName)==null) roadMonth.put(tempName, monthCount++);
				
				for(int j=0;j<monthExistTop[roadMonth.get(tempName)];j++)
					if(monthExist[roadMonth.get(tempName)][j]==myObject.getInt(("����~��"))) exist=true;
				if(exist) continue;
				
				monthExist[roadMonth.get(tempName)][monthExistTop[roadMonth.get(tempName)]++] = myObject.getInt(("����~��"));
				
				if(roadCount.get(tempName)==null) 
					roadCount.put(tempName, 1);
				else
				{
					tempCount = roadCount.remove(tempName);
					roadCount.put(tempName, tempCount+1);
				}
				//System.out.println(tempName+"="+roadCount.get(tempName)+" "+myObject.getInt(("����~��"))+" "+roadMonth.get(tempName));
			}
			
			//��X���P�����MAX��
			int maxDistinctMonth=0;
			for (Object key : roadMonth.keySet())
				if(monthExistTop[roadMonth.get(key)]>maxDistinctMonth) maxDistinctMonth = monthExistTop[roadMonth.get(key)];
			
			//�ھ�MAX�ȧ����������G
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
						myMatcher = myPattern.matcher(myObject.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").toString());
						
						if(!myMatcher.find()||!myMatcher.group().equals(tempName)) continue;

						tempPrice = myObject.getInt("�`����");
						if(tempPrice>highestPrice) highestPrice = tempPrice;
						if(tempPrice<lowestPrice) lowestPrice = tempPrice;
					}
					System.out.println(tempName+", �̰������:"+highestPrice+", �̧C�����:"+lowestPrice);
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
