package com.unicesumar.repository;

import com.unicesumar.entities.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaleRepository {
    private final Connection conn;

    public SaleRepository(Connection conn) {
        this.conn = conn;
    }

    public void save(Sale sale) {
        String sql = "INSERT INTO sales (id, user_id, payment_method) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sale.getId().toString());
            stmt.setString(2, sale.getUserId().toString());
            stmt.setString(3, sale.getPaymentMethod().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar venda: " + e.getMessage());
        }
    }
}

