package supermarket.magiasoft.eu.comparasupermercados;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONObject;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private static final String URL_EROSKI = "http://www.compraonline.grupoeroski.com/supermercado/home.jsp";

    private ImageButton buttonEroski;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEroski = (ImageButton) findViewById(R.id.eroskiButton);

        buttonEroski.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEroski(v, URL_EROSKI);
            }
        });
    }

    public void launchEroski(View v, String url){
        //final JSONObject productCategories = Utils.parseURL(URL_EROSKI);

        //return productCategories;

        Utils.parseURL(URL_EROSKI);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

    }
}
