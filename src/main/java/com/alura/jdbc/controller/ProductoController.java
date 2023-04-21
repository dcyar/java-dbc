package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {
	public int modificar(Integer id, String nombre, String descripcion, Integer cantidad) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();

		try(con) {
			final PreparedStatement statement = con.prepareStatement("UPDATE producto SET nombre=?, descripcion=?, cantidad=? WHERE id=?");

			try(statement) {
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);

				statement.execute();

				return statement.getUpdateCount();
			}
		}
	}

	public int eliminar(Integer id) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();

		try(con) {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE id = ?");

			try(statement) {
				statement.setInt(1, id);

				statement.execute();

				return statement.getUpdateCount();
			}
		}
	}

	public List<Map<String, String>> listar() throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();

		try(con) {
			final Statement statement = con.createStatement();

			try(statement) {
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

				return resultados;
			}
		}
	}

    public void guardar(Producto producto) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();

		try(con) {
			con.setAutoCommit(false);

			final PreparedStatement statement = con.prepareStatement("INSERT INTO producto(nombre, descripcion, cantidad) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			try(statement) {
				ejecutarRegistro(producto, statement);
				con.commit();
			} catch (Exception e) {
				con.rollback();
			}
		}
	}

	private static void ejecutarRegistro(Producto producto, PreparedStatement statement) throws SQLException {
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, producto.getCantidad());

		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();

		try(resultSet) {
			while(resultSet.next()) {
				producto.setId(resultSet.getInt(1));

				System.out.printf("Fue insertado el producto de %s", producto);
			}
		}
	}

}
