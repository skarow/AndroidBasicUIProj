package com.example.adefault.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
        displayPrice(calculatePrice() );
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String orderText = makeOrderString( calculatePrice() );
        displayOrder( orderText );
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        quantity++;
        display(quantity);
        displayPrice(calculatePrice() );
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if ( quantity <= 0 )
            return;
        quantity--;
        display(quantity);
        displayPrice(calculatePrice() );
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    /**
     * This method displays the final order on the screen.
     * @param orderText is the text to be displayed
     */
    private void displayOrder(String orderText) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(orderText);
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "k.pejgorski@abv.bg", null));
        email.putExtra(Intent.EXTRA_SUBJECT, "New Order");
        email.putExtra(Intent.EXTRA_TEXT, orderText);
        startActivity(email);
//        if (email.resolveActivity(getPackageManager()) != null) {
//            startActivity(email);
//        }
    }

    /**
     * This method creates the string to be shown as client orders
     * @param totalPrice is what he has to pay
     * @return final order text
     */
    private String makeOrderString(int totalPrice) {
        String result;
        CheckBox checkBox = (CheckBox) findViewById(R.id.ch_b_whipped_cream);
        EditText editText = (EditText) findViewById(R.id.edit_name_field);
        if (totalPrice == 0)
            return "No order made";
        result = "Name: ";
        result += editText.getText();
        result +="\nQuantity:" + quantity;
        if (checkBox.isChecked()) {
            result += "\nwith wiped cream";
        }
        result += "\nTotal: $" + totalPrice;
        result += "\nThank you!";
        return result;
    }

    /**
     * Calculates the price of the order based on the current quantity.
     * @return the price
     */
    private int calculatePrice() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.ch_b_whipped_cream);
        int price = quantity * 5;
        if (checkBox.isChecked()) {
            price += quantity;
        }
        return price;
    }
}





