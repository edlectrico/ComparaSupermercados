package supermarket.magiasoft.eu.comparasupermercados.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import supermarket.magiasoft.eu.comparasupermercados.R;
import supermarket.magiasoft.eu.comparasupermercados.Utils;

/**
 * Created by edlectrico on 11/03/15.
 */

public class MainCategories extends ActionBarActivity {

    private ListView mainCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_categories);

        mainCategories = (ListView) findViewById(R.id.list_main_categories);

        Intent intent = getIntent();
        final String[] categories = intent.getExtras().getStringArray("maincategories");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, categories);

        mainCategories.setAdapter(adapter);
        mainCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                final String category = (String) mainCategories.getItemAtPosition(position); // e.g. "Alimentos Frescos"

                if (category.startsWith("Alimentos")){
                    final List<String> secondCategories = Utils.listSecondLevelCategories();
                    String[] categories = new String[secondCategories.size()];

                    for (int i = 0; i < secondCategories.size(); i++){
                        categories[i] = secondCategories.get(i);
                    }

                    Intent secondLevelCategories = new Intent(MainCategories.this, SecondLevelCategories.class);
                    secondLevelCategories.putExtra("secondlevelcategories", categories);

                    startActivity(secondLevelCategories);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_categories, menu);
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