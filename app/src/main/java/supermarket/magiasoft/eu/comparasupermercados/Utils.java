package supermarket.magiasoft.eu.comparasupermercados;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                    //Log.d("Element", element.toString());

                    // get the main categories
                    final Elements elements = element.getElementsByClass("doblelinea");
                    //Log.d("Elements", elements.toString());

                    List<String> mainCategories = new ArrayList<String>();
                    List<String> secondLevelCategories = new ArrayList<String>();

                    boolean secondLevelFilled = false;

                    for (int i = 0; i < elements.size(); i++){
                        final String mainCategory = doc.select("a[class=doblelinea]").get(i).attr("title");
                        mainCategories.add(mainCategory);

                        if (!secondLevelFilled){
                            Elements secondLevelElements = element.getElementsByAttributeValueContaining("href", "/supermercado/2059698-Alimentos-Frescos/");

                            for (int j = 1; j < secondLevelElements.size(); j++){
                                final String secondLevelElement = secondLevelElements.get(j).attr("title");
                                secondLevelCategories.add(secondLevelElement);
                                Log.d("secondLevelElement", secondLevelElement);

                                // TODO:
                                // Third level categories have to be fetched from URL_EROSKI_BASE +
                                // + Category's code + "/" Element's code + "/", for example
                                // www.compraonline.grupoeroski.com/supermercado/2059698-Alimentos-Frescos/2059699-Frutas/
                            }
                        }





//                        Log.d("secondLevelTitles", secondLevelElements.toString());
//                        for (Element e : secondLevelElements){
//                            Elements secondLevelTitles = e.getElementsByAttribute("title");
//                            Log.d("secondLevelTitles", secondLevelTitles.toString());
//                        }
                    }

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
