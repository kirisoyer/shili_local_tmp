package com.example.ordermanager.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.example.ordermanager.dao.TOrderMapper;
import com.example.ordermanager.entity.TOrder;

public class MybatisTest {

    /* ========== 基础连通性测试 ========== */
    @Test
    public void testConnDB() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/test?user=root&password=Wjmysql123321&serverTimezone=GMT");
            PreparedStatement ps = conn.prepareStatement("select * from t_order limit 1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* ========== MyBatis 连通性测试 ========== */
    @Test
    public void testMybatisConn() throws Exception {
        String resource = "mybatis-config.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource);
             SqlSession session = new SqlSessionFactoryBuilder()
                     .build(inputStream)
                     .openSession()) {
            TOrderMapper mapper = session.getMapper(TOrderMapper.class);
            TOrder order = mapper.findOrderById(1L);
            System.out.println(order == null ? "null" : order.getName());
        }
    }

    /* ========== 分页查询测试 ========== */
    @Test
    public void testMybatisPagination() throws Exception {
        String resource = "mybatis-config.xml";
        try (InputStream is = Resources.getResourceAsStream(resource);
             SqlSession session = new SqlSessionFactoryBuilder().build(is).openSession()) {
            TOrderMapper mapper = session.getMapper(TOrderMapper.class);
            List<TOrder> list = mapper.find(0, 5, "",0);
            System.out.println("size = " + list.size());
            if (!list.isEmpty()) {
                System.out.println("first order name = " + list.get(0).getName());
            }
        }
    }

    /* ========== 条件统计测试 ========== */
    @Test
    public void testCountOrderByCondition() throws IOException {
        try (InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
             SqlSession session = new SqlSessionFactoryBuilder().build(in).openSession()) {
            TOrderMapper mapper = session.getMapper(TOrderMapper.class);
            int total = mapper.count(null,0);
            System.out.println("total orders = " + total);
        }
    }

    /* ========== 删除测试 ========== */
    @Test
    public void testDeleteOrder() throws IOException {
        try (InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
             SqlSession session = new SqlSessionFactoryBuilder().build(in).openSession()) {
            TOrderMapper mapper = session.getMapper(TOrderMapper.class);
            int rows = mapper.deleteOrder(1L);
            session.commit();
            assertEquals(1, rows);
        }
    }

    /* ========== 插入测试 ========== */
    @Test
    public void testInsertOrder() throws IOException {
        try (InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
             SqlSession session = new SqlSessionFactoryBuilder().build(in).openSession()) {
            TOrderMapper mapper = session.getMapper(TOrderMapper.class);

            TOrder order = new TOrder();
            order.setName("测试订单");
            order.setPrice(99.99);
            order.setTime(new Date());
            order.setType(0);

            int rows = mapper.insertOrder(order);
            session.commit();
            assertEquals(1, rows);
        }
    }

    /* ========== 更新测试 ========== */
    @Test
    public void testUpdateOrder() throws IOException {
        try (InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
             SqlSession session = new SqlSessionFactoryBuilder().build(in).openSession()) {
            TOrderMapper mapper = session.getMapper(TOrderMapper.class);

            TOrder order = mapper.findOrderById(2L);
            if (order == null) {
                System.out.println("未找到 ID=2 的订单，跳过更新测试");
                return;
            }
            order.setName("更新后的订单名");
            order.setPrice(199.99);
            order.setTime(new Date());

            int rows = mapper.updateOrder(order);
            session.commit();
            assertEquals(1, rows);
        }
    }
}