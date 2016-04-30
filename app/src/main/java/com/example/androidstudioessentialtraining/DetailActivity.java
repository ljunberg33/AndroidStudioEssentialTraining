package com.example.androidstudioessentialtraining;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String productID = getIntent().getStringExtra(MainActivity.PRODUCT_ID);
        final Product product = DataProvider.productMap.get(productID);

        TextView tv = (TextView) findViewById(R.id.nameText);
        tv.setText(product.getName());

        TextView descView = (TextView) findViewById(R.id.descriptionText);
        descView.setText(product.getDescription());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String price = formatter.format(product.getPrice());
        TextView priceText = (TextView) findViewById(R.id.priceText);
        priceText.setText(price);

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap = getBitMapFromAsset(product.getProductId());
        iv.setImageBitmap(bitmap);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra(MainActivity.RETURN_MESSAGE, product.getName() + " added to shpping cart");
                setResult(RESULT_OK, data);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Bitmap getBitMapFromAsset(String productID){
        AssetManager assetManaget = getAssets();
        InputStream stream = null;

        try {
            stream = assetManaget.open(productID + ".png");
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
