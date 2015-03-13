package supermarket.magiasoft.eu.comparasupermercados;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import supermarket.magiasoft.eu.comparasupermercados.activities.MainCategories;

/**
 * Created by edlectrico on 11/03/15.
 */

public class MainActivity extends ActionBarActivity {

    private static final String URL_EROSKI_BASE = "http://www.compraonline.grupoeroski.com/supermercado/";
    private static final String URL_EROSKI_MAIN = "home.jsp";

    private ImageButton buttonEroski;
    private ProgressDialog dialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, MainCategories.class);

        buttonEroski = (ImageButton) findViewById(R.id.eroskiButton);
        buttonEroski.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                new ProductCategoriesFetcher().execute(URL_EROSKI_BASE + URL_EROSKI_MAIN);
            }
        });
    }

    class ProductCategoriesFetcher extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            final List<String> mainCategories = Utils.parseURL(urls[0]);
            String[] categories = new String[mainCategories.size()];

            for (int i = 0; i < mainCategories.size(); i++){
                categories[i] = mainCategories.get(i);
            }

            intent.putExtra("maincategories", categories);

            return "Executed";
        }

        protected void onPostExecute(String string) {
            dialog.dismiss();
            startActivity(intent);
        }
    }

    private void showDialog() {
        dialog = ProgressDialog.show(MainActivity.this, "", getResources().getString(R.string.loading_message_dialog), true);
        dialog.show();
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

}
