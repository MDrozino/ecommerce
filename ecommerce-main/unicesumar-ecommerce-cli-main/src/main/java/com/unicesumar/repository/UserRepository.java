package com.unicesumar.repository;

import com.unicesumar.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository implements EntityRepository<User> {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(User entity) {
        String query = "INSERT INTO users VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, entity.getUuid().toString());
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getPassword());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        String query = "SELECT * FROM users WHERE uuid = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT uuid, name, email, password FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            UUID.fromString(rs.getString("uuid")),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
            // Consider logging the exception properly instead of just printing to console
        }
        return Optional.empty();
    }


    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();

        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                users.add(new User(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void deleteById(UUID id) {
        String query = "DELETE FROM users WHERE uuid = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
