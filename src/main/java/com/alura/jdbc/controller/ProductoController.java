package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {
	public int modificar(Integer id, String nombre, String descripcion, Integer cantidad) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

		PreparedStatement statement = con.prepareStatement("UPDATE producto SET nombre=?, descripcion=?, cantidad=? WHERE id=?");
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.setInt(4, id);

		statement.execute();

		int updatedCount = statement.getUpdateCount();

		con.close();

		return updatedCount;
	}

	public int eliminar(Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

		PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE id = ?");
		statement.setInt(1, id);

		statement.execute();

		int updateCount = statement.getUpdateCount();

		con.close();

		return updateCount;
	}

	public List<Map<String, String>> listar() throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

		Statement statement = con.createStatement();

		statement.execute("SELECT id, nombre, descripcion, cantidad FROM producto");

		ResultSet resultSet = statement.getResultSet();

		List<Map<String, String>> resultados = new ArrayList<>();

		while (resultSet.next()) {
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt("id")));
			fila.put("NOMBRE", resultSet.getString("nombre"));
			fila.put("DESCRIPCION", resultSet.getString("descripcion"));
			fila.put("CANTIDAD", String.valueOf(resultSet.getInt("cantidad")));

			resultados.add(fila);
		}

		con.close();

		return resultados;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

		PreparedStatement statement = con.prepareStatement("INSERT INTO producto(nombre, descripcion, cantidad) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, producto.get("NOMBRE"));
		statement.setString(2, producto.get("DESCRIPCION"));
		statement.setString(3, producto.get("CANTIDAD"));

		statement.execute();

		ResultSet resultSet = statement.getGeneratedKeys();

		while(resultSet.next()) {
			System.out.printf("Fue insertado el producto de ID %d", resultSet.getInt(1));
		}

		con.close();
	}

}
