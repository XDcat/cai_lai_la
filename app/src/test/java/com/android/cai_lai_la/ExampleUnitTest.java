package com.android.cai_lai_la;

import com.alibaba.fastjson.JSON;
import com.android.cai_lai_la.controller.CartController;
import com.android.cai_lai_la.controller.ProductClassController;
import com.android.cai_lai_la.controller.ProductPicController;
import com.android.cai_lai_la.controller.UserController;
import com.android.cai_lai_la.model.Cart;
import com.android.cai_lai_la.model.Product;
import com.android.cai_lai_la.model.ProductClass;
import com.android.cai_lai_la.model.ProductPic;
import com.android.cai_lai_la.model.User;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1() {
        System.out.println("获取商品分类");
        List<ProductClass> list = ProductClassController.list();
        for (ProductClass p :
                list) {
            System.out.println(p);
        }
    }
    @Test
    public void test2(){
        System.out.println("获取购物车信息");
        List<Product> productList = CartController.list(1);
        for(Product p:productList){
            System.out.println(p);
        }
    }
    @Test
    public void testProductPicList() {
        System.out.println("获取商品图片");
        System.out.println("获取所有图片");
        List<ProductPic> list = ProductPicController.list();
        System.out.println(JSON.toJSON(list).toString());
        System.out.println("获取pid=1的商品的图片");
        List<ProductPic> list1 = ProductPicController.list(3);
        System.out.println(list1);
        System.out.println(JSON.toJSON(list1).toString());
    }

    @Test
    public void testCartController(){
        System.out.println("获取的用户购物车");
        List<Cart> cartList1 = CartController.getCartByUid(-1);
        for (Cart cart :
                cartList1) {
            System.out.println(cart);
        }

        System.out.println("获取id为1的用户购物车");
        List<Cart> cartList = CartController.getCartByUid(1);
        for (Cart cart :
                cartList) {
            System.out.println(cart);
        }

        if (cartList.size() > 0){
            Cart cart = cartList.get(0);
            System.out.printf("更新uid = %d， pid=%d, num=%d, 设置num++\n", cart.getUid(), cart.getPid(), cart.getNum());
            cart.setNum(cart.getNum() + 1);
            System.out.println("更新后的cart" + cart);
            CartController.update(cart);
        }
    }

    @Test
    public void testUserController(){
        System.out.println("获取所有用户信息");
        List<User> list = UserController.list();
        for (User user :
                list) {
            System.out.println(user);
        }
    }
}