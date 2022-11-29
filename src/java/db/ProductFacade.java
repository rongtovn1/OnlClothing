/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class ProductFacade {

    public List<Product> selectAll() throws SQLException {
        List<Product> list = null;
        Connection con = DBContext.getConnection();

        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from product");
        list = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product();
            product.setpId(rs.getString("pId"));
            product.setpName(rs.getString("pName"));
            product.setPrice(rs.getInt("price"));
            product.setImage(rs.getString("image"));
            product.setCaId(rs.getInt("CaId"));
            list.add(product);
        }
        con.close();

        return list;
    }

    public Product find(String id) throws SQLException {
        Connection con = DBContext.getConnection();
        Product product = null;
        String sql = "select p.*, c.CaName as category "
                + "from product p join category c on p.CaId=c.CaId where p.pId= ?";
        PreparedStatement stm = con.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            product = new Product();
            product.setpId(rs.getString("pId"));
            product.setpName(rs.getString("pName"));
            product.setPrice(rs.getInt("price"));
            product.setImage(rs.getString("image"));
            product.setCategory(rs.getString("category"));
        }
        con.close();

        return product;
    }
}
