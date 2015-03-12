package supermarket.magiasoft.eu.comparasupermercados;

import android.text.Html;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by edlectrico on 11/03/15.
 */
public class Utils {

    static public void parseURL(final String url){

        new Thread(){
            public void run(){
                Document doc;
                try {

                    // need http protocol
                    doc = Jsoup.connect(url).get();

                    // get page title
                    //String title = doc.title();
                    //System.out.println("title : " + title);

                    final Element element = doc.getElementsByClass("navmenu").get(0);
                    Log.d("Element", element.toString());

                    // get the main categories
                    final Elements elements = element.getElementsByClass("doblelinea");
                    Log.d("Elements", elements.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }


    private void buildCategoriesHierarchy(final String htmlStringContent){
        final String INITIAL_TAG = "Alimentos Frescos";  //Tag from which we will create the hierarchy


    }
}
