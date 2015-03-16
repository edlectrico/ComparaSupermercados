package supermarket.magiasoft.eu.comparasupermercados;

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
    private static Document mainDoc;
    private static Document thirdLevelDoc;
    private static List<String> secondLevelCategoriesLinks;

    static public List<String> listMainCategories(final String url) {
        List<String> mainCategories = null;
        try {
            // need http protocol
            mainDoc = Jsoup.connect(url).get();
            navmenu = mainDoc.getElementsByClass("navmenu").get(0);

            // get the main categories
            doblelinea = navmenu.getElementsByClass("doblelinea");

            mainCategories = new ArrayList<String>();

            for (int i = 0; i < doblelinea.size(); i++) {
                final String mainCategory = mainDoc.select("a[class=doblelinea]").get(i).attr("title");
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
        final List<String> thirdLevel = new ArrayList<String>();

        Thread t = new Thread() {
            public void run() {
                try {
                    thirdLevelDoc = Jsoup.connect(url).get();
                    Element productlist_filters = thirdLevelDoc.getElementsByClass("productlist_filters").get(0);
                    final Elements thirdLevelCategories = productlist_filters.getElementsByAttributeValueContaining("for", "chk");

                    for (Element e : thirdLevelCategories){
                        final String level = e.toString().substring(e.toString().indexOf(">") + 1, e.toString().lastIndexOf("<"));
                        thirdLevel.add(level);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();

        try {
            // Wait for the thread to finish filling thirdLevel
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return thirdLevel;
    }

}