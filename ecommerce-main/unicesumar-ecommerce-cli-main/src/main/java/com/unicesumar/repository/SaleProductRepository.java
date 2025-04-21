package com.unicesumar.repository;

import com.unicesumar.entities.SaleProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaleProductRepository {
    private final Connection conn;

    public SaleProductRepository(Connection conn) {
        this.conn = conn;
    }

    public void save(SaleProduct sp) {
        String sql = "INSERT INTO sale_products (sale_id, product_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sp.getSaleId().toString());
            stmt.setString(2, sp.getProductId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar produto da venda: " + e.getMessage());
        }
    }
}

