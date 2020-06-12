package com.android.cai_lai_la.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.cai_lai_la.R;
import com.android.cai_lai_la.adapter.OrderConfirmAdapter;
import com.android.cai_lai_la.controller.AddressController;
import com.android.cai_lai_la.controller.UserController;
import com.android.cai_lai_la.model.Address;
import com.android.cai_lai_la.model.Product;
import com.android.cai_lai_la.model.User;
import com.android.cai_lai_la.model.ui.CartInfo;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderConfirmActivity extends AppCompatActivity {
    public static final String INTENT_PRODUCT = "productlist";
    public static final String INTENT_CARTINFO = "cartinfolist";
    int uid;
    @BindView(R.id.order_list)
    ListView listView;
    @BindView(R.id.order_num)
    TextView OrderNum;
    @BindView(R.id.order_money)
    TextView OrderMoney;
    @BindView(R.id.order_address)
    ImageView orderaddresspic;
    @BindView(R.id.order_username)
    TextView orderusername;
    @BindView(R.id.usertel)
    TextView usertel;
    @BindView(R.id.user_address)
    TextView useraddress;
    private List<CartInfo> cartInfos;
    private List<Product> list;
    private String price;
    private String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        //仅去掉标题栏，系统状态栏还是会显示
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        list = (List<Product>) intent.getSerializableExtra(INTENT_PRODUCT);
        cartInfos = (List<CartInfo>) intent.getSerializableExtra(INTENT_CARTINFO);
        num = intent.getStringExtra("num");
        price = intent.getStringExtra("price");
        uid = intent.getIntExtra("uid", 1);
    }

    private void initView() {
        orderaddresspic.setImageResource(R.drawable.address);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                User user = UserController.loadUser(OrderConfirmActivity.this);

                String name = user.getNickname();
                String tel = user.getUsertel();
                List<Address> addresses = AddressController.list(uid);
                OrderConfirmActivity.this.runOnUiThread(() -> {
                    orderusername.setText(name);
                    usertel.setText(tel);
                    useraddress.setText(addresses.get(0).getSheng() + addresses.get(0).getShi() + addresses.get(0).getQu() + addresses.get(0).getDetailaddr());
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        OrderNum.setText("共" + num + "件商品");
        OrderMoney.setText("¥ " + price);
        for (int i = 0; i < list.size(); i++) {
            CartInfo c1 = new CartInfo();
            c1.setCurrentprice(list.get(i).getCurrentprice());
            cartInfos.add(c1);
        }
        OrderConfirmAdapter adapter = new OrderConfirmAdapter(this, list, cartInfos, this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.confirm_order_toolbar_back)
    public void onclick() {
        this.finish();
    }

}