package supermarket.magiasoft.eu.comparasupermercados.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import supermarket.magiasoft.eu.comparasupermercados.MainActivity;
import supermarket.magiasoft.eu.comparasupermercados.R;
import supermarket.magiasoft.eu.comparasupermercados.Utils;

/**
 * Created by edlectrico on 11/03/15.
 */
public class SecondLevelCategories extends ActionBarActivity {

    private ListView secondLevelCategories;
    private Intent thirdLevelCategories;

    // This dialog assures a second thread to fetch the corresponding
    // third level categories from the required web page
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        secondLevelCategories = (ListView) findViewById(R.id.list_categories);

        Intent intent = getIntent();
        final String[] secondCategories = intent.getExtras().getStringArray("secondlevelcategories");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, secondCategories);


        secondLevelCategories.setAdapter(adapter);
        secondLevelCategories.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog();
                new ThirdLevelCategoriesFetcher().execute(String.valueOf(position));
            }
        });
    }

    private void showDialog() {
        dialog = ProgressDialog.show(SecondLevelCategories.this, "", getResources().getString(R.string.loading_message_dialog), true);
        dialog.show();
    }

    class ThirdLevelCategoriesFetcher extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... position) {
            final String category = (String) secondLevelCategories.getItemAtPosition(Integer.valueOf(position[0])); // e.g. "Frutas"

            final List<String> categoriesUrl = Utils.getSecondLevelCategoriesLinks();

            int pos = -1;
            for (int i = 0; i < categoriesUrl.size(); i++) {
                if (categoriesUrl.get(i).contains(category)) {
                    pos = i;
                    break;
                }
            }

            if (pos != -1) {
                final String url = categoriesUrl.get(pos);
                final List<String> thirldLevelCategories = Utils.listThirdLevelCategories(MainActivity.URL_EROSKI_BASE + url);

                String[] categories = new String[thirldLevelCategories.size()];

                for (int i = 0; i < thirldLevelCategories.size(); i++) {
                    categories[i] = thirldLevelCategories.get(i);
                }

                thirdLevelCategories = new Intent(SecondLevelCategories.this, ThirdLevelCategories.class);
                thirdLevelCategories.putExtra("thirdlevelcategories", categories);
            }

            return "Executed";
        }

        protected void onPostExecute(String string) {
            dialog.dismiss();
            startActivity(thirdLevelCategories);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second_level_categories, menu);
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
