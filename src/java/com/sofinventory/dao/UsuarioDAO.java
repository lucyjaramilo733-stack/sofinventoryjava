package com.sofinventory.dao;

import com.sofinventory.modelos.Usuario;
import com.sofinventory.utils.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // ── LISTAR TODOS ──────────────────────────────────────────────────────────
    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
            SELECT u.id, u.numero_documento, u.nombre_completo, u.email,
                   u.username, u.estado, u.fecha_creacion, u.observaciones,
                   t.id AS tipo_doc_id, t.nombre AS tipo_doc_nombre,
                   r.id AS rol_id, r.nombre AS rol_nombre
            FROM usuarios u
            JOIN tipos_documento t ON u.tipo_documento_id = t.id
            JOIN roles r ON u.rol_id = r.id
            ORDER BY u.nombre_completo
            """;
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    // ── BUSCAR POR ID ─────────────────────────────────────────────────────────
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT u.id, u.numero_documento, u.nombre_completo, u.email,
                   u.username, u.estado, u.fecha_creacion, u.observaciones,
                   t.id AS tipo_doc_id, t.nombre AS tipo_doc_nombre,
                   r.id AS rol_id, r.nombre AS rol_nombre
            FROM usuarios u
            JOIN tipos_documento t ON u.tipo_documento_id = t.id
            JOIN roles r ON u.rol_id = r.id
            WHERE u.id = ?
            """;
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    // ── INSERTAR ──────────────────────────────────────────────────────────────
    public boolean insertar(Usuario u) throws SQLException {
        String sql = """
            INSERT INTO usuarios
              (tipo_documento_id, numero_documento, nombre_completo, email,
               username, password, rol_id, estado, fecha_creacion, observaciones)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, u.getTipoDocumentoId());
            ps.setString(2, u.getNumeroDocumento());
            ps.setString(3, u.getNombreCompleto());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getUsername());
            // Contraseña en texto plano para el proyecto académico
            ps.setString(6, u.getPassword());
            ps.setInt(7, u.getRolId());
            ps.setString(8, u.getEstado());
            ps.setDate(9, Date.valueOf(u.getFechaCreacion()));
            ps.setString(10, u.getObservaciones());
            return ps.executeUpdate() > 0;
        }
    }

    // ── ACTUALIZAR ────────────────────────────────────────────────────────────
    public boolean actualizar(Usuario u) throws SQLException {
        String sql = """
            UPDATE usuarios SET
              tipo_documento_id = ?, numero_documento = ?, nombre_completo = ?,
              email = ?, username = ?, rol_id = ?, estado = ?, observaciones = ?
            WHERE id = ?
            """;
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, u.getTipoDocumentoId());
            ps.setString(2, u.getNumeroDocumento());
            ps.setString(3, u.getNombreCompleto());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getUsername());
            ps.setInt(6, u.getRolId());
            ps.setString(7, u.getEstado());
            ps.setString(8, u.getObservaciones());
            ps.setInt(9, u.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // ── ELIMINAR ──────────────────────────────────────────────────────────────
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── LOGIN ─────────────────────────────────────────────────────────────────
    public Usuario login(String username, String password) throws SQLException {
        String sql = """
        SELECT u.id, u.numero_documento, u.nombre_completo, u.email,
               u.username, u.estado, u.fecha_creacion, u.observaciones,
               t.id AS tipo_doc_id, t.nombre AS tipo_doc_nombre,
               r.id AS rol_id, r.nombre AS rol_nombre
        FROM usuarios u
        JOIN tipos_documento t ON u.tipo_documento_id = t.id
        JOIN roles r ON u.rol_id = r.id
        WHERE u.username = ? AND u.password = ? AND u.estado = 'activo'
        """;
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    // ── LISTAS AUXILIARES ─────────────────────────────────────────────────────
    public List<int[]> listarTiposDocumento() throws SQLException {
        // Retorna pares [id, nombre] como Object[]
        return listarCatalogo("SELECT id, nombre FROM tipos_documento ORDER BY nombre");
    }

    public List<int[]> listarRoles() throws SQLException {
        return listarCatalogo("SELECT id, nombre FROM roles ORDER BY nombre");
    }

    private List<int[]> listarCatalogo(String sql) throws SQLException {
        List<int[]> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new int[]{rs.getInt(1)});
            }
        }
        return lista;
    }

    // Versión que retorna id + nombre como String[][]
    public List<String[]> listarCatalogoCompleto(String tabla) throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String columna = tabla.equals("usuarios") ? "nombre_completo" : "nombre";
        String sql = "SELECT id, " + columna + " FROM " + tabla + " ORDER BY " + columna;
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString(columna)
                });
            }
        }
        return lista;
    }

    // ── MAPEAR ResultSet → Usuario ────────────────────────────────────────────
    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNumeroDocumento(rs.getString("numero_documento"));
        u.setNombreCompleto(rs.getString("nombre_completo"));
        u.setEmail(rs.getString("email"));
        u.setUsername(rs.getString("username"));
        u.setEstado(rs.getString("estado"));
        u.setObservaciones(rs.getString("observaciones"));
        u.setTipoDocumentoId(rs.getInt("tipo_doc_id"));
        u.setTipoDocumentoNombre(rs.getString("tipo_doc_nombre"));
        u.setRolId(rs.getInt("rol_id"));
        u.setRolNombre(rs.getString("rol_nombre"));
        if (rs.getDate("fecha_creacion") != null) {
            u.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
        }
        return u;
    }
}
