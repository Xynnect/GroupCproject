package logicClasses;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jjoe64.graphview.GraphView.GraphViewData;
import android.util.Log;

public class QueryBuilder {
	public static String infoParsed;
	public static String displayInfo;

	public static JSONArray dataArray = new JSONArray();
	public static JSONObject dataObject = new JSONObject();
	public static JSONArray jsonMainArr;
	public static JSONObject jsonInfo;

	public static JSONObject indicatorInfo;
	public static JSONObject countryInfo;

	public static JSONObject regionInfo;
	public static JSONObject adminRegionInfo;
	public static JSONObject incomeLevelInfo;
	public static JSONObject lendingTypeInfo;

	static String idIndicator;
	public static String valueIndicator;
	static String idCountry;
	public static String valueCountry;
	public static String valueInfoStr;
	static String decimalInfoStr;
	static String dateInfoStr;

	private static String idRegion;
	private static String valueRegion;
	private static String idIncomeLevel;
	private static String valueIncomeLevel;
	private static String idAdminRegion;
	private static String valueAdminRegion;
	private static String idLendingType;
	private static String valueLendingType;
	private static String idInfo;
	private static String iso2CodeInfo;
	public static String nameInfo;
	private static String capitalCityInfo;
	private static String longitudeInfo;
	private static String latitudeInfo;

	public static String p1ApiAddress = "http://api.worldbank.org/countries/";
	public static String p2CountryName = "";
	public static String p3Indicators = "/indicators/";
	public static String p4IndicatorName = "";
	public static String p5BeginningOfIdentifiers = "?";
	public static String p6ItemsPerPage = "per_page=51&";
	public static String p7Date = "date=1960:2013&";
	public static String p8Format = "format=json";
	public static String p2Country2Name = "";

	public static int[] years = new int[2000];
	public static double[] values = new double[2000];
	public static int arrayNumber = 0;

	private static String nameOftheClassCallingThisClass;

	public static boolean called = false;
	public static int num = 50;
	public static GraphViewData[] data = new GraphViewData[num];
	public static GraphViewData[] data2 = new GraphViewData[2 * num];

	public static String thereIsNoInforamtionForTheFollowingYears = "";

	static double arrayMaxLength = 0;

	public static int debuggingIntincreasingOnEvery102elements = 0;
	static boolean debugginAllResultsAreCorrect = false;

	public static ArrayList<String> arrayWithValuesForCountry = new ArrayList<String>();
	public static ArrayList<String> arrayWithDescrptionsForCountry = new ArrayList<String>();

	public static ArrayList<String> arrayWithValuesAndYearsForIndicators = new ArrayList<String>();
	public static ArrayList<String> arrayWithValuesAndYearsForComparison = new ArrayList<String>();
	public static ArrayList<String> arrayListForComparisonTitle = new ArrayList<String>();
	public static ArrayList<String> arrayWithYearsForComparison = new ArrayList<String>();
	public static ArrayList<String> arrayWithValuesForComparison = new ArrayList<String>();

	public static String[] arrayWithValuesForComparisonForKeepingTheNullValues = new String[2000];

	public QueryBuilder(String urlparser) {
		jsonParserReader(urlparser);
	}

	public static void jsonParserReader(String url) {
		infoParsed = JsonParser.readData(url);
		jsonStringIntoJsonArrayTransformer();
	}

	public static void setNameOfClassCallingQueryBuilder(String className) {
		nameOftheClassCallingThisClass = className;
	}

	public static void jsonStringIntoJsonArrayTransformer() {
		displayInfo = "";
		arrayWithValuesForCountry.clear();
		arrayWithValuesAndYearsForIndicators.clear();
		arrayWithValuesAndYearsForComparison.clear();
		arrayWithYearsForComparison.clear();
		arrayWithValuesForComparison.clear();

		try {
			jsonMainArr = new JSONArray(infoParsed);
			JSONArray countries = jsonMainArr.getJSONArray(1);

			for (int i = 0; i < countries.length(); i++) {

				if (nameOftheClassCallingThisClass
						.equals("searchActivities.IndicatorSearchActivity")) {
					jsonInfo = (JSONObject) countries.get(50 - i);
					jsonObjectExtractorForCountryAndIndicator();
				}
				if (nameOftheClassCallingThisClass
						.equals("searchActivities.CountrySearchActivity")) {
					jsonInfo = (JSONObject) countries.get(i);
					jsonObjectExtractorForCountry();
				}
				if (nameOftheClassCallingThisClass
						.equals("searchActivities.ComparisonSearchActivity")) {
					jsonInfo = (JSONObject) countries.get(50 - i);
					jsonObjectExtractorForCountryAndIndicator();
				}

			}
			restartTheValuesOfAttributes();

		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("QueryBuilder", "data did not parse");
		}

	}

	public static void jsonObjectExtractorForCountryAndIndicator() {
		try {
			indicatorInfo = jsonInfo.getJSONObject("indicator");
			countryInfo = jsonInfo.getJSONObject("country");

			idIndicator = indicatorInfo.getString("id");
			valueIndicator = indicatorInfo.getString("value");

			idCountry = countryInfo.getString("id");
			valueCountry = countryInfo.getString("value");

			valueInfoStr = jsonInfo.getString("value");
			decimalInfoStr = jsonInfo.getString("decimal");
			dateInfoStr = jsonInfo.getString("date");

			arrayWithValuesForComparisonForKeepingTheNullValues[arrayNumber] = valueInfoStr;

			years[arrayNumber] = Integer.parseInt(dateInfoStr);
			if (valueInfoStr == "null") {
				values[arrayNumber] = 0.0;
				thereIsNoInforamtionForTheFollowingYears += Integer
						.toString(years[arrayNumber]) + " ";
				debugginAllResultsAreCorrect = false;
			}

			else {
				values[arrayNumber] = Double.parseDouble(valueInfoStr);

			}

			arrayWithValuesAndYearsForIndicators.add(dateInfoStr);
			arrayWithValuesAndYearsForIndicators.add(valueInfoStr);
			if (nameOftheClassCallingThisClass
					.equals("searchActivities.ComparisonSearchActivity")) {
				arrayWithValuesAndYearsForComparison
						.add(QueryBuilder.valueInfoStr);
				arrayWithYearsForComparison.add(dateInfoStr);
			}

			displayInfo += dateInfoStr + ": " + " " + valueInfoStr + "\n";

			arrayNumber++;

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void jsonObjectExtractorForCountry() {
		try {
			regionInfo = jsonInfo.getJSONObject("region");
			adminRegionInfo = jsonInfo.getJSONObject("adminregion");
			incomeLevelInfo = jsonInfo.getJSONObject("incomeLevel");
			lendingTypeInfo = jsonInfo.getJSONObject("lendingType");

			idRegion = regionInfo.getString("id");
			valueRegion = regionInfo.getString("value");

			idAdminRegion = adminRegionInfo.getString("id");
			valueAdminRegion = adminRegionInfo.getString("value");

			idIncomeLevel = incomeLevelInfo.getString("id");
			valueIncomeLevel = incomeLevelInfo.getString("value");

			idLendingType = lendingTypeInfo.getString("id");
			valueLendingType = lendingTypeInfo.getString("value");

			idInfo = jsonInfo.getString("id");
			iso2CodeInfo = jsonInfo.getString("iso2Code");
			nameInfo = jsonInfo.getString("name");
			capitalCityInfo = jsonInfo.getString("capitalCity");
			longitudeInfo = jsonInfo.getString("longitude");
			latitudeInfo = jsonInfo.getString("latitude");

			idRegion = " " + idRegion;
			valueRegion = " " + valueRegion;

			idAdminRegion = " " + idAdminRegion;
			valueAdminRegion = " " + valueAdminRegion;

			idIncomeLevel = " " + idIncomeLevel;
			valueIncomeLevel = " " + valueIncomeLevel;

			idLendingType = " " + idLendingType;
			valueLendingType = " " + valueLendingType;

			idInfo = " " + idLendingType;
			iso2CodeInfo = " " + iso2CodeInfo;
			nameInfo = " " + nameInfo;
			capitalCityInfo = " " + capitalCityInfo;
			longitudeInfo = " " + longitudeInfo;
			latitudeInfo = " " + latitudeInfo;

			arrayWithValuesForCountry.add(idInfo);
			arrayWithValuesForCountry.add(iso2CodeInfo);
			arrayWithValuesForCountry.add(nameInfo);
			arrayWithValuesForCountry.add(capitalCityInfo);
			arrayWithValuesForCountry.add(longitudeInfo);
			arrayWithValuesForCountry.add(latitudeInfo);
			arrayWithValuesForCountry.add(idRegion);
			arrayWithValuesForCountry.add(valueRegion);

			if (!idAdminRegion.equals(" ")) {
				arrayWithValuesForCountry.add(idAdminRegion);
			} else {
				arrayWithValuesForCountry.add(" No information");
			}
			if (!valueAdminRegion.equals(" ")) {
				arrayWithValuesForCountry.add(valueAdminRegion);
			} else {
				arrayWithValuesForCountry
						.add(" No                                    information");
			}

			arrayWithValuesForCountry.add(idIncomeLevel);
			arrayWithValuesForCountry.add(valueIncomeLevel);
			arrayWithValuesForCountry.add(idLendingType);
			arrayWithValuesForCountry.add(valueLendingType);

			arrayWithDescrptionsForCountry.add(" Country id: ");
			arrayWithDescrptionsForCountry.add(" Iso code: ");
			arrayWithDescrptionsForCountry.add(" Country name: ");
			arrayWithDescrptionsForCountry.add(" Capital city: ");
			arrayWithDescrptionsForCountry.add(" Longitude: ");
			arrayWithDescrptionsForCountry.add(" Latitude: ");
			arrayWithDescrptionsForCountry.add(" Region id: ");
			arrayWithDescrptionsForCountry
					.add(" Region                      location: ");
			arrayWithDescrptionsForCountry.add(" Admin location id: ");
			arrayWithDescrptionsForCountry
					.add(" Administration              location: ");
			arrayWithDescrptionsForCountry.add(" Income level id: ");
			arrayWithDescrptionsForCountry.add(" Income level: ");
			arrayWithDescrptionsForCountry.add(" Lending type id: ");
			arrayWithDescrptionsForCountry.add(" Lending type: ");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static String missingInformation() {
		String debuggingNoInfo = thereIsNoInforamtionForTheFollowingYears;
		if (thereIsNoInforamtionForTheFollowingYears.isEmpty()) {
			return "";
		} else {
			thereIsNoInforamtionForTheFollowingYears = "";
			return "We are sorry but there was no information for the following years: "
					+ debuggingNoInfo + "\n";
		}

	}

	public static double maxLength(double thisValueLength) {
		int castIntForThisValueLength = (int) thisValueLength;
		int castIntForArrayMaxLength = (int) arrayMaxLength;
		if (castIntForThisValueLength > castIntForArrayMaxLength) {
			arrayMaxLength = thisValueLength;
		}
		return arrayMaxLength;
	}

	private static void restartTheValuesOfAttributes() {

		if (nameOftheClassCallingThisClass
				.equals("searchActivities.IndicatorSearchActivity")
				|| nameOftheClassCallingThisClass
						.equals("searchActivities.CountrySearchActivity")) {
			p2CountryName = "";
			p4IndicatorName = "";
			p2Country2Name = "";
		}
		arrayMaxLength = 0;

	}

}
