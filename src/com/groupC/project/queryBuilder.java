package com.groupC.project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

//todo change the name of the methods 
//make the mainActivity take only one object and that object is the query builder taking json data via jsonParser 


public class queryBuilder implements OnItemSelectedListener{
    public static String infoParsed;
    public static String part1;
    public static String countryName;
    public static String part3;
    public static String indicatorName;
    public static String part5;   
    public static JSONArray dataArray = new JSONArray();
    public static JSONObject dataObject = new JSONObject();
public queryBuilder(){
	JsonAndJdaughter();
	
	
	MainActivity.countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	MainActivity.countryListView.setAdapter(MainActivity.countryAdapter);
	MainActivity.countryListView.setOnItemSelectedListener(this);
    
	
	MainActivity.indicatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	MainActivity.indicatorListView.setAdapter(MainActivity.indicatorAdapter);
	MainActivity.indicatorListView.setOnItemSelectedListener(this);
    
}

public void JsonAndJdaughter(){
    //that class is passing the Json data from jsonParser.java
    //also in oirder to make the parser working i need a sdk permission:
    /*
     
     <uses-sdk
android:minSdkVersion="9"
android:targetSdkVersion="17" />

<uses-permission android:name="android.permission.INTERNET"></uses-permission>
      
     */
    
    //i am also using a textView1 to display the source Json
    //the think is that son.readData(String) reads only about that string and that string should be changed 
    jsonParser son = new jsonParser();
    
    
    
    //Jcat();
    
    infoParsed = son.readData(Jcat());
            
            
    
    MainActivity.textView1.setMovementMethod(new ScrollingMovementMethod());
    MainActivity.textView1.setText(infoParsed);

    Jdog();

}

public void Jdog(){
    //the Jdog takes the Jstring that the Json and Jdaugther throw and return them a Json Array (As every good dog does)
    

String displayInfo = "";

try { 

JSONArray jsonMainArr = new JSONArray(infoParsed); 
JSONArray countries = jsonMainArr.getJSONArray(1);
for(int i =0;i<countries.length();i++){
JSONObject country = (JSONObject) countries.get(i);
JSONObject indicator = country.getJSONObject("country");
String id = indicator.getString("id"); 
String value = indicator.getString("value"); 
displayInfo +=id+value;
}       
} catch (JSONException e) { 
    e.printStackTrace();
Log.e("MainActivity","data did not parse"); 
} 

MainActivity.textView1.setText(displayInfo);
    
    
    
    
} 
public String Jcat(){
    //Jcat will construct api calls 
    //http://api.worldbank.org/countries/ABW/indicators/1.1_TOTAL.FINAL.ENERGY.CONSUM?per_page=50&date=1960:2013&format=json
    //http://api.worldbank.org/countries/BGR/indicators/1.1_ACCESS.ELECTRICITY.TOT?per_page=10&date=1960:2013&format=json
    //the only 2 changing parts of the api calling string are the ones between http://api.worldbank.org/countries/ & /indicators/ and /indicators/ & per_page=50&date=1960:2013&format=json
    // in other words you can just remove http://api.worldbank.org/countries/ ; /indicators/ ; per_page=50&date=1960:2013&format=json
    //Jcat will get the string and will replace the 2 important parts with its own strings who will be arrays
    //http://api.worldbank.org/countries/{country_id}/indicators/{indicator_id}?date={from_year}:{to_year} this is the full string type bujt we will leave {fromyear},{toyear} to 1960 and 2013
    
    
    
    part1 = "http://api.worldbank.org/countries/";
    
    part3 = "/indicators/";
    
    part5 = "?per_page=10&date=1960:2013&format=json";
                    
    //user changes different countryName and indicatorName from a list and get's the query for them
    
    return part1 + countryName + part3 + indicatorName + part5;
    
    
    
    
    
}

@Override
public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
// TODO Auto-generated method stub
Log.v("something", "something");
countryName = MainActivity.countryListView.getSelectedItem().toString();
indicatorName = MainActivity.indicatorListView.getSelectedItem().toString();
JsonAndJdaughter();			
}

@Override
public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
Log.v("something", "something");
countryName = "ABW";
indicatorName = "1.1_ACCESS.ELECTRICITY.TOT";
}

}
