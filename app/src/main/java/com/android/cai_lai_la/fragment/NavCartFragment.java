package com.android.cai_lai_la.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cai_lai_la.R;
import com.android.cai_lai_la.activity.OrderConfirmActivity;
import com.android.cai_lai_la.adapter.CartListAdapter;
import com.android.cai_lai_la.callback.OnClickAddCloseListenter;
import com.android.cai_lai_la.callback.OnClickListenterModel;
import com.android.cai_lai_la.controller.CartController;
import com.android.cai_lai_la.controller.UserController;
import com.android.cai_lai_la.model.Product;
import com.android.cai_lai_la.model.User;
import com.android.cai_lai_la.model.ui.CartInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class NavCartFragment extends Fragment {
    CartInfo cartInfo;  //购物车信息
    double price;
    int num;
    @BindView(R.id.cart_listView)
    ListView listView;
    @BindView(R.id.cart_num)
    TextView cartNum;
    @BindView(R.id.cart_money)
    TextView cartMoney;
    @BindView(R.id.cart_shopp_moular)
    Button cartShoppMoular;
    private boolean isGetData = false;
    private int uid;  //用户编号
    private Context mContext;
    private boolean isLog;
    private String money;
    private List<Product> productList = new ArrayList();
    private List<CartInfo> cartInfos = new ArrayList();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                ArrayList arrayList = msg.getData().getParcelableArrayList("data");
                List<Product> data = (List<Product>) arrayList.get(0);


                for (int i = 0; i < data.size(); i++) {
                    CartInfo c1 = new CartInfo();
                    c1.setCurrentprice(data.get(i).getCurrentprice());
                    cartInfos.add(c1);
                }
                if (data.size() == 0) {
                    Toast.makeText(mContext, "商品不存在", Toast.LENGTH_SHORT).show();
                } else {
                    CartListAdapter adapter = new CartListAdapter(mContext, data, cartInfos, getActivity());
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    /**
                     * 单选
                     */
                    adapter.setOnClickListenterModel(new OnClickListenterModel() {
                        @Override
                        public void onItemClick(boolean isFlang, View view, int position) {
                            cartInfos.get(position).setIscheck(isFlang);
                            showCommodityCalculation(cartInfos);
                        }
                    });


                    /**
                     * 数量增加和减少
                     */
                    adapter.setOnClickAddCloseListenter(new OnClickAddCloseListenter() {
                        @Override
                        public void onItemClick(View view, int index, int position, int num) {
                            if (index == 1) {
                                if (num > 1) {
                                    cartInfos.get(position).setNum(num - 1);
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                cartInfos.get(position).setNum(num + 1);
                                adapter.notifyDataSetChanged();
                            }
                            showCommodityCalculation(cartInfos);
                        }
                    });
                    showCommodityCalculation(cartInfos);
                }
            }
        }
    };

    public NavCartFragment() {
    }

    public static NavCartFragment newInstance() {
        NavCartFragment fragment = new NavCartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.mContext = getActivity();
            this.isLog = UserController.isLog(mContext);
            setUid(isLog);
            initData();
            isGetData = true;
            //相当于Fragment的onResume，为true时，Fragment已经可见
        } else {
            isGetData = false;
            // 相当于Fragment的onPause，为false时，Fragment不可见
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_cart, container, false);
        ButterKnife.bind(this, view);  // 自动绑定
        setUid(isLog);
        initData();
        return view;
    }

    private void initData() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                productList = CartController.list(uid);

                ArrayList arrayList = new ArrayList();//用于传递list的集合
                arrayList.add(productList);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", arrayList);
                Message msg = new Message();
                msg.what = 0;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    private void showCommodityCalculation(List<CartInfo> cartInfos) {
        price = 0;
        num = 0;
        for (int i = 0; i < cartInfos.size(); i++) {
            if (cartInfos.get(i).ischeck()) {
                price += Double.valueOf((cartInfos.get(i).getNum() * Double.valueOf(cartInfos.get(i).getCurrentprice())));
                num++;
            }
        }
        if (price == 0.0) {
            cartNum.setText("共0件商品");
            cartMoney.setText("¥ 0.0");
            return;
        }
        try {
            money = String.valueOf(price);
            cartNum.setText("共" + num + "件商品");
            if (money.substring(money.indexOf(".")).length() > 2) {
                cartMoney.setText("¥ " + money.substring(0, (money.indexOf(".") + 3)));
                return;
            }
            cartMoney.setText("¥ " + money.substring(0, (money.indexOf(".") + 2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.cart_shopp_moular)
    public void onClick() {
        List<Product> productList_selected = new ArrayList();
        List<CartInfo> cartInfos_selected = new ArrayList();
        for (int i = 0; i < cartInfos.size(); i++) {
            if (cartInfos.get(i).ischeck()) {
                productList_selected.add(productList.get(i));
                cartInfos_selected.add(cartInfos.get(i));
            }
        }
        if (cartInfos_selected.size() == 0) {
            Toast.makeText(mContext, "您还没有选择宝贝哦", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(mContext, OrderConfirmActivity.class);
            intent.putExtra(OrderConfirmActivity.INTENT_PRODUCT, (Serializable) productList_selected);
            intent.putExtra(OrderConfirmActivity.INTENT_CARTINFO, (Serializable) cartInfos_selected);
            intent.putExtra("num", String.valueOf(num));
            intent.putExtra("price", money.substring(0, (money.indexOf(".") + 2)));
            intent.putExtra("uid", uid);
            getActivity().startActivity(intent);
        }
    }

    public void setUid(boolean isLog) {
        if (isLog) {
            User user = UserController.loadUser(mContext);
            uid = user.getUid();
        } else {
            uid = 0;
            Toast.makeText(mContext, "您还没有登录哦", Toast.LENGTH_SHORT).show();
        }
    }
}
