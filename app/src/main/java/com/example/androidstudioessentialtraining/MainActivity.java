package com.example.androidstudioessentialtraining;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.example.androidstudioessentialtraining.R.id.listView;

public class MainActivity extends AppCompatActivity {

    public static final String PRODUCT_ID ="PRODUCT_ID" ;

    private static final int DETAIL_REQUEST = 1111;
    public static final String RETURN_MESSAGE ="RETURN_MESSAGE" ;
    private CoordinatorLayout coordinatorLayout;
    public static String webUrl = "https://www.arsenal.com/";
    public static String email = "info@arsenal.com/";

    private List<Product> products = DataProvider.productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = (EditText) findViewById(R.id.editText);
                String entry = et.getText().toString();
                Snackbar.make(view, "You entered: " + entry, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //send email
                String[] addresses =  {email};
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Information request");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Please Send Some information!");
                if(emailIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(emailIntent);
                }
            }
        });

//        ImageView iv= (ImageView) findViewById(R.id.photo);
//       // iv.setImageResource(R.drawable.jacket101);
//
//        //using assets folder ... removed image from drawable to assess folder
//        String imageName = "jacket101";
//        try {
//            InputStream stream = getAssets().open(imageName + ".png");
//            Drawable d = Drawable.createFromStream(stream,null);
//            iv.setImageDrawable(d);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String[] items = getResources().getStringArray(R.array.clothing);
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
//                        android.R.id.text1, items);

        ProductListAdapter adapter = new ProductListAdapter(this, R.layout.list_item,products);
        ListView lv = (ListView) findViewById(listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);

                Product product = products.get(position);
                intent.putExtra(PRODUCT_ID, product.getProductId());

                startActivityForResult(intent, DETAIL_REQUEST);
            }
        });
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

        switch (id){
            case R.id.action_settings:
                //   Toast.makeText(MainActivity.this,"You selected the settings menu",Toast.LENGTH_SHORT).show();
//            return true;
                Snackbar.make(coordinatorLayout, "You selected the settings menu ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;

            case R.id.action_about:
//                Snackbar.make(coordinatorLayout, "You selected About ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_cart:
                Snackbar.make(coordinatorLayout, "You selected Shopping Cart ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;

            case R.id.action_web:
                //Go to website
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                if(webIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(webIntent);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClickHandler(View view) {
        EditText et = (EditText) findViewById(R.id.editText);
        String name = et.getText().toString();
        Snackbar.make(coordinatorLayout, "Your name is: " + name, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(MainActivity.this,"Your orientation is portrait",Toast.LENGTH_SHORT).show();
        }
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(MainActivity.this,"Your orientation is landscape",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == DETAIL_REQUEST){
            if(resultCode == RESULT_OK){
                String message = data.getStringExtra(RETURN_MESSAGE);
                Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_LONG)
                        .setAction("Go to cart", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Going to cart",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        }
    }
}
