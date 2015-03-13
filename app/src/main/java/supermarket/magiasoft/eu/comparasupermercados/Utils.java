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

    private static Element navmenu;
    private static Elements doblelinea;
    private static Document doc;
    private static List<String> secondLevelCategoriesLinks;

    static public List<String> listMainCategories(final String url) {
        List<String> mainCategories = null;
        try {
            // need http protocol
            doc = Jsoup.connect(url).get();
            navmenu = doc.getElementsByClass("navmenu").get(0);

            // get the main categories
            doblelinea = navmenu.getElementsByClass("doblelinea");

            mainCategories = new ArrayList<String>();

            for (int i = 0; i < doblelinea.size(); i++) {
                final String mainCategory = doc.select("a[class=doblelinea]").get(i).attr("title");
                mainCategories.add(mainCategory);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mainCategories;
    }

    static public List<String> listSecondLevelCategories() {
        boolean secondLevelFilled = false;
        List<String> secondLevelCategoriesTitles = new ArrayList<String>();
        secondLevelCategoriesLinks = new ArrayList<String>();

        for (int i = 0; i < doblelinea.size(); i++) {
            if (!secondLevelFilled) {
                Elements secondLevelElements = navmenu.getElementsByClass("secondlevel");
                Elements categories = secondLevelElements.get(0).getElementsByAttributeValueContaining("href", "/supermercado/2059698-Alimentos-Frescos/");
                //getElementsByAttributeValueContaining("href", "/supermercado/2059698-Alimentos-Frescos/");

                for (int j = 0; j < categories.size(); j++) {
                    final String secondLevelElementTitle = categories.get(j).attr("title");
                    final String secondLevelElementLink = categories.get(j).attr("href");
                    secondLevelCategoriesTitles.add(secondLevelElementTitle);
                    secondLevelCategoriesLinks.add(secondLevelElementLink);

                    // TODO:
                    // Third level categories have to be fetched from URL_EROSKI_BASE +
                    // + Category's code + "/" Element's code + "/", for example
                    // www.compraonline.grupoeroski.com/supermercado/2059698-Alimentos-Frescos/2059699-Frutas/
                }
                secondLevelFilled = true;
                // TODO: break?
            }
        }

        return secondLevelCategoriesTitles;
    }

    public static List<String> getSecondLevelCategoriesLinks() {
        return secondLevelCategoriesLinks;
    }

    static public List<String> listThirdLevelCategories(final String url) {
        List<String> thirdLevelCategories = null;

        new Thread() {
            public void run() {
                try {
                    doc = Jsoup.connect(url).get();
                    Element productlist_filters = doc.getElementsByClass("productlist_filters").get(0);

                    Log.d("productlist_filters", "productlist_filters");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return null;
    }

}