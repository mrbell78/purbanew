package com.example.project531;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project531.Activity.ConfrimActivity;
import com.example.project531.Activity.Login;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Helper.ManagementCart;
import com.example.project531.Interface.ChangeNumberItemsListener;
import com.example.project531.adapter.checkoutAdaptor;
import com.example.project531.apicall.Api;
import com.example.project531.apicall.RetrofitClient;
import com.example.project531.responsedata.ResponseCustomer;
import com.example.project531.responsedata.responseProduct;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class checkout_activity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, name_cus, address, taxTxt, deliveryTxt, totalTxt, emptyTxt, CheckOut, productName, totalEachItem2, totalEachItem, feeEachItem, total,phone_number;
    private double tax;
    private EditText total3;
    ProgressDialog dialog;
    private ConstraintLayout order_place_layout;
    private ScrollView scrollView;
    Handler handler;
    String str;
    String phone="";
    int totalBalance=0;
    List<responseProduct> orderItems=new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        managementCart = new ManagementCart(this);
        initView();
        initList();
        userinfo();
        calculateCard();

        dialog= new ProgressDialog(checkout_activity.this);
        dialog.setContentView(R.layout.progressbar_dialog);
        // dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.addContentView(new ProgressBar(checkout_activity.this), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        /*Checkout */
        productName= findViewById(R.id.item);
        totalEachItem2= findViewById(R.id.totalEachItem2);
        totalEachItem= findViewById(R.id.totalEachItem);
        feeEachItem= findViewById(R.id.feeEachItem);
        total3= findViewById(R.id.totalTxt2);
        order_place_layout= findViewById(R.id.order_place_layout);

        orderItems=managementCart.getListCart();

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d(TAG, "handleMessage: inside handler........"+msg.what);
                RetrofitClient retrofitClient = new RetrofitClient();
                //  Retrofit mb = retrofitClient.getRetrofit();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://dmcbproject.com/dairy_api/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                order_place_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String>productName= new ArrayList<>();
                        ArrayList<String>quantity= new ArrayList<>();
                        ArrayList<String>proname= new ArrayList<>();
                        ArrayList<Integer> proPrice= new ArrayList<>();
                        ArrayList<Integer> subtotal= new ArrayList<>();
                        ArrayList<String> unit= new ArrayList<>();
                        ArrayList<String>productHead= new ArrayList<>();
                        for(int i=0;i<orderItems.size();i++){
                            productName.add(orderItems.get(i).getPro_name().toString());
                            proname.add(orderItems.get(i).getProductName().toString());
                            quantity.add(String.valueOf(orderItems.get(i).getNumberInCart()));
                            proPrice.add(orderItems.get(i).getSalePrice());
                            subtotal.add((orderItems.get(i).getSalePrice()*orderItems.get(i).getNumberInCart()));
                            unit.add(orderItems.get(i).getUnit().toString());
                            productHead.add(orderItems.get(i).getProductType().toString());
                        }
                        String pName = new Gson().toJson(productName);
                        String q = new Gson().toJson(quantity);
                        String proName = new Gson().toJson(proname);
                        String proPrice2 = new Gson().toJson(proPrice);
                        String subtotal2 = new Gson().toJson(subtotal);
                        String unittupe = new Gson().toJson(unit);
                        String custName = new Gson().toJson(msg.what);
                        String totalBalance2 = new Gson().toJson(totalBalance);
                        String productHead2 = new Gson().toJson(productHead);
                        Log.d(TAG, "onClick: the Pname is"+pName);
                        Log.d(TAG, "onClick: the q  is"+q);
                        Log.d(TAG, "handleMessage: inside handler........"+msg.what);
                        Log.d(TAG, "total........"+totalBalance2);

                        Api apicallingjson = retrofit.create(Api.class);
                        Call<ResponseBody> callcoment = apicallingjson.checkout(pName,q,proName,proPrice2,subtotal2,unittupe,custName,totalBalance2,productHead2);
                        dialog.show();
                        callcoment.enqueue(new retrofit2.Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code() == 200) {
                                    response.body();
                                    dialog.dismiss();
                                    Log.d(TAG, "responde body is"+response.body());
                                    totalTxt.setText("");
                                    Toast.makeText(checkout_activity.this, "Successfully Checkout", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(checkout_activity.this, ConfrimActivity.class);
                                    startActivity(intent);
                                }
                                else{

                                    Toast.makeText(checkout_activity.this, "error", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(checkout_activity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }

                });
            }
        };

    }
    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new checkoutAdaptor(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                /*calculateCard();*/
            }
        });
        recyclerViewList.setAdapter(adapter);
   /*     if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }*/
    }
    private void calculateCard() {
        int delivery = Integer.parseInt(String.valueOf(80));     //you can change this item you need price for delivery
        int total = (int) (Math.round((managementCart.getTotalFee() + delivery) * 100.0) / 100.0);
        totalBalance =total;
        int itemTotal = (int) (Math.round(managementCart.getTotalFee() * 100.0) / 100.0);
        totalTxt.setText("" + total);
    }

    private void userinfo() {

        SharedPreferences prefs =getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        phone=prefs.getString("phone","null");
        phone_number.setText(phone);
        Log.d(TAG, "Name2 "+name_cus);
        Retrofit retrofit_textview = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/dairy_api/api/Customer/"+phone+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apicallingjson = retrofit_textview.create(Api.class);
        Call<ResponseCustomer> searchapi_textview= apicallingjson.get_customer_list();

        searchapi_textview.enqueue(new Callback<ResponseCustomer>() {
            @Override
            public void onResponse(Call<ResponseCustomer> call, Response<ResponseCustomer> response) {

                ResponseCustomer customerInfo=response.body();
                name_cus.setText(customerInfo.getCustomerName());
                address.setText(customerInfo.getAddress());
                Message msg = handler.obtainMessage(Integer.parseInt(customerInfo.getCustomerId()));
                msg.sendToTarget();
                Log.d(TAG, "onResponse: .......the data is "+customerInfo.getCustomerName());

            }

            @Override
            public void onFailure(Call<ResponseCustomer> call, Throwable t) {

            }
        });

    }

    private void initView() {
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt2);
        recyclerViewList = findViewById(R.id.checkout_view);
        scrollView = findViewById(R.id.scrollView2);
        emptyTxt = findViewById(R.id.emptyTxt);
        phone_number = findViewById(R.id.phone_number);
        name_cus = findViewById(R.id.name_cus);
        address = findViewById(R.id.address);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String  loginstatus = prefs.getString("login", "failed");
        if(!loginstatus.equals("login_success")){
            Intent out =new Intent(checkout_activity.this, Login.class);
            out.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(out);

        }else {

        }

    }

}