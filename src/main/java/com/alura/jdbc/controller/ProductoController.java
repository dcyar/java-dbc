package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
	}

	public void eliminar(Integer id) {
		// TODO
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

		Statement statement = con.createStatement();

		statement.execute(
				"INSERT INTO producto(nombre, descripcion, cantidad) VALUES('" + producto.get("NOMBRE") + "', '" + producto.get("DESCRIPCION") + "', " + producto.get("CANTIDAD") + ")",
				Statement.RETURN_GENERATED_KEYS
		);

		ResultSet resultSet = statement.getGeneratedKeys();

		while(resultSet.next()) {
			System.out.printf("Fue insertado el producto de ID %d", resultSet.getInt(1));

		}

		con.close();
	}

}
