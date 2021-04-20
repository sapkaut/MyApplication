package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class NewsActivity extends AppCompatActivity {

    //String KEY_API="9975e7f7c1614471a0fdead656cd7e27";
    String News_Source = "https://lenta.ru/rss/news";
    //ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
    ArrayList<String> titles;
    ArrayList<String> links;
    ListView listNews;
    ProgressBar loader;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        listNews = (ListView)findViewById(R.id.listNews);
        loader = (ProgressBar)findViewById(R.id.progressBar2);
        listNews.setEmptyView(loader);
        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        new DownloadNews().execute();


    }

    public InputStream getInputStream(URL url){
        try{
           return url.openConnection().getInputStream();
        }catch(IOException e){
            return null;
        }
    }



 public class DownloadNews extends AsyncTask<Integer,Void,Exception> {
    @Override
    protected void onPreExecute(){super.onPreExecute();}

    Exception exception = null;

     @Override
     protected Exception doInBackground(Integer... args){
        try{
            URL url = new URL("https://lenta.ru/rss/news");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(getInputStream(url),"UTF_8");

            boolean insideItem = false;
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName().equalsIgnoreCase("item")){
                        insideItem = true;
                    }else if(xpp.getName().equalsIgnoreCase("title")){
                        if(insideItem){
                            titles.add(xpp.nextText());
                        }
                    }else if(xpp.getName().equalsIgnoreCase("link")){
                        if(insideItem){
                            links.add(xpp.nextText());
                        }
                    }
                }else if(eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item") ){
                    insideItem = false;
                }
                 eventType = xpp.next();
            }


        }catch(MalformedURLException e){
            exception=e;
         }
        catch(XmlPullParserException e){
            exception=e;
        }
        catch(IOException e){
            exception=e;
        }

        return exception;
     }

     @Override
     protected void onPostExecute(Exception s){
        super.onPostExecute(s);

         ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewsActivity.this, android.R.layout.simple_list_item_1, titles);
         listNews.setAdapter(adapter);
     }
 }

}