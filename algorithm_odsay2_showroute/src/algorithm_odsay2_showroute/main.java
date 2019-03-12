package algorithm_odsay2_showroute;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class main {
	public main() throws Exception {
		db database = new db();
		int usercount = database.userCount();

		user[] user = new user[usercount];
		for (int i = 0; i < usercount; i++) {
			user[i] = new user();
		}

		for (int i = 1; i <= usercount; i++) {
			int dep = database.departure(i);
			System.out.println(dep);
			int des = database.destination(i);
			System.out.println(des);
			int prefer = database.prefer(i);
			System.out.println(prefer);

			JSONParser jsonparser = new JSONParser();
			JSONObject jsonobject = (JSONObject) jsonparser.parse(readUrl(dep, des, prefer));
			JSONObject json = (JSONObject) jsonobject.get("result");
			JSONObject json2 = (JSONObject) json.get("stationSet");
			JSONArray array = (JSONArray) json2.get("stations");

			for (int j = 0; j < array.size(); j++) {
				JSONObject entity = (JSONObject) array.get(j);

				String sname = (String) entity.get("startName");
				if(checkduplicate(sname, user[i - 1]) == 0) {
					user[i-1].routestore.add(sname);
				}
				

				String ename = (String) entity.get("endName");
				if(checkduplicate(ename, user[i - 1]) == 0) {
					user[i-1].routestore.add(ename);
				}

			}

			for (int k = 0; k < user[i - 1].routestore.size(); k++) {
				System.out.print(user[i - 1].routestore.get(k));
			}
			System.out.println();
		}
		database.deleteuserinfocopy();
	}

	private String readUrl(int departure, int destination, int prefer) throws Exception {
		BufferedReader reader = null;

		try {
			// https://api.odsay.com/v1/api/subwayPath?lang=0&CID=1000&SID=201&EID=222

			URL url = new URL("https://api.odsay.com/v1/api/subwayPath?lang=0&CID=1000&" + "SID=" + departure + "&EID="
					+ destination + "&Sopt=" + prefer + "&apiKey=9loymI1RM20ytIKmWKFe0x8arsNpYKoPSgHLoGhzANE");
			// WcVpRfZ6U%2BAuKf8AgOTZapx9edixkIvmJLWnT9KgiaE-하이드아웃
			// 15XH4EhsIQGTKIwZAjii5dwtmXtv%2BdVulD4QWniB%2Bjg-히수집
			// 9loymI1RM20ytIKmWKFe0x8arsNpYKoPSgHLoGhzANE-은비집
			// FKNgHXbbPDpB2qoqgvkmA3DAKApfxjOfbp%2Fz%2F0gWnOU-학교

			reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

			StringBuffer buffer = new StringBuffer();

			String str;

			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public int checkduplicate(String route, user user) {
		int check = 0;

		for (int j = 0; j < user.routestore.size(); j++) {
			if (user.routestore.get(j).contains(route)) {
				check++;
			}
		}

		return check;
	}

	public static void main(String[] args) {
		try {
			new main();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
