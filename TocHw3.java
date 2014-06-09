import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.*;

public class TocHw3 {

	public static void main(String[] args)
	{
		try {
			URL url = new URL(args[0]);
			Reader myReader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
			JSONTokener myTokener = new JSONTokener(myReader);
			JSONArray myArray = new JSONArray(myTokener);
			JSONObject myObject;
			
			Pattern[] myPattern = new Pattern[2];
			myPattern[0] = Pattern.compile(args[1]);
			myPattern[1] = Pattern.compile(".*"+args[2]+".*");
			Matcher[] myMatcher = new Matcher[2];
			
			int count=0;
			long sum=0;
			
			for (int i = 0; i < myArray.length(); i++) {
				myObject = myArray.getJSONObject(i);
				myMatcher[0] = myPattern[0].matcher(myObject.getString("鄉鎮市區").toString());
				if(!myMatcher[0].find()) continue;
				myMatcher[1] = myPattern[1].matcher(myObject.getString("土地區段位置或建物區門牌").toString());
				if(!myMatcher[1].find()) continue;
				if(myObject.getInt(("交易年月"))<Integer.valueOf(args[3])*100) continue;
				
				/*System.out.println(
						myObject.getString("鄉鎮市區")+"\t"+
						myObject.getString("土地區段位置或建物區門牌")+"\t"+
						myObject.getInt(("交易年月"))+"\t"+
						myObject.getInt("總價元"));*/
				count++;
				sum+=myObject.getInt("總價元");
            }
			
			double avg_price=(sum/count);
			
			System.out.println((int)avg_price);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
