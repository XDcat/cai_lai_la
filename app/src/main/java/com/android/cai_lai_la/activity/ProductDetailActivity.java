package com.android.cai_lai_la.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.cai_lai_la.R;
import com.android.cai_lai_la.adapter.ProductDetailRecyclerAdapter;
import com.android.cai_lai_la.controller.CartController;
import com.android.cai_lai_la.controller.HistoryBrowserController;
import com.android.cai_lai_la.controller.ProductPicController;
import com.android.cai_lai_la.controller.UserController;
import com.android.cai_lai_la.model.Cart;
import com.android.cai_lai_la.model.HistoryBrowser;
import com.android.cai_lai_la.model.Product;
import com.android.cai_lai_la.model.ProductPic;
import com.android.cai_lai_la.model.User;
import com.android.cai_lai_la.utils.LoadImageUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String TAG = "product detail activity";
    public static final String INTENT_PRODUCT = "product";
    @BindView(R.id.toolbar)
    Toolbar toolbar;  // 工具条
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;  // 工具条外部布局
    @BindView(R.id.fab)
    FloatingActionButton fab;  // 浮动按钮
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.xbanner)
    ImageView bannerImage;
    @BindView(R.id.product_detail_recycler)
    RecyclerView recyclerView;
    // 具体的信息
    @BindView(R.id.current_price)
    TextView tv_current_price;
    @BindView(R.id.old_price)
    TextView tv_old_price;
    @BindView(R.id.product_keep)
    TextView tv_keep;
    @BindView(R.id.product_title)
    TextView tv_title;
    @BindView(R.id.product_spci)
    TextView tv_spci;
    @BindView(R.id.product_life_time)
    TextView tv_life_time;
    @BindView(R.id.product_birth_date)
    TextView tv_birth_date;

    Product product;  // 商品
    List<ProductPic> productPicList;  // 商品图片列表

    private CollapsingToolbarLayoutState state;  // 状态标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                initData();
                ProductDetailActivity.this.runOnUiThread(() -> {
                    initView();
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @SuppressLint("DefaultLocale")
    private void initView() {
        // 设置工具栏
        setSupportActionBar(toolbar);
        // 设置顶部图片
        LoadImageUtils.load(this, this, productPicList, bannerImage);

        // 设置标题
        collapsingToolbarLayout.setTitle("商品详情");

        // 设置加入购物车按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart cart = new Cart();
                // 只有在登陆的的时候添加购物车信息
                if (UserController.isLog(ProductDetailActivity.this)) {
                    new Thread(() -> {
                        User user = UserController.loadUser(ProductDetailActivity.this);
                        List<Product> list = CartController.list(user.getUid());
                        // 判断用户是否有该商品，如果有则在基础上添加，如果没有则是创建新的
                        boolean isInCart = false;
                        for (Product p :
                                list) {
                            if (p.getPid().equals(product.getPid())) {
                                isInCart = true;
                            }
                        }
                        if (!isInCart) {
                            cart.setNum(1);
                            cart.setPid(product.getPid());
                            cart.setUid(user.getUid());
                            CartController.add(cart);
                            Snackbar.make(view, "已经添加到购物车", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else{
                            Snackbar.make(view, "该商品已经在购物车中", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }).start();
                } else {
                    Snackbar.make(view, "您尚未登录，请登陆后再尝试哟", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        // 设置图片 recycler
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ProductDetailRecyclerAdapter(this, this, productPicList));
//        recyclerView.setNestedScrollingEnabled(false);
        // 设置其他商品信息
        tv_current_price.setText(String.format("%.2f", product.getCurrentprice()));
        tv_old_price.setText(String.format("%.2f", product.getOriginalprice()));
        tv_old_price.setPaintFlags(tv_old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);  // 设置删除线
        tv_title.setText(String.format("%s，%s", product.getTitle(), product.getSubtitle()));
        tv_keep.setText(String.format("剩余%d件", product.getStorenum()));
        tv_life_time.setText(String.format("保质期%.0f天", product.getLifetime()));
        // 如果没有时间，默认加载今天
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        if (product.getManudate() != null) {
            tv_birth_date.setText(String.format(
                    "生产日期：%s", dateFormat.format(product.getManudate())
            ));
        } else {
            tv_birth_date.setText(String.format(
                    "生产日期：%s", dateFormat.format(new Date())
            ));
        }

    }

    private void initData() {
        // 获取出入的pid，如果不存在则直接退出
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra(ProductDetailActivity.INTENT_PRODUCT);
        if (product == null) {
            Log.i(TAG, "initData: 为获取到商品对象，直接退出");
            finish();
        }
        // 获取图片
        productPicList = ProductPicController.list(product.getPid());
        // 查看商品信息，增加浏览记录
        boolean isLog = UserController.isLog(this);
        if (isLog) {
            Log.i(TAG, "initData: 添加历史浏览记录");
            User user = UserController.loadUser(this);
            HistoryBrowser historyBrowser = new HistoryBrowser();
            historyBrowser.setPid(product.getPid());
            historyBrowser.setUid(user.getUid());
            HistoryBrowserController.add(historyBrowser);
        }
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED,  // 展开
        COLLAPSED,  // 闭合
        INTERNEDIATE,  // 过程中
    }
}