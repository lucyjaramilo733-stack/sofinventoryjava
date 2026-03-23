package com.sofinventory.dao;

import com.sofinventory.modelos.Proveedor;
import com.sofinventory.utils.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    // ── LISTAR TODOS ──────────────────────────────────────────────────────────
    public List<Proveedor> listar() throws SQLException {
        List<Proveedor> lista = new ArrayList<>();
        String sql = """
            SELECT p.id, p.numero_documento, p.razon_social, p.nombre_contacto,
                   p.cargo_contacto, p.email, p.telefono, p.direccion,
                   p.pais, p.departamento, p.ciudad, p.tipo_proveedor,
                   p.estado, p.observaciones, p.fecha_registro,
                   t.id AS tipo_doc_id, t.nombre AS tipo_doc_nombre,
                   u.id AS creado_por_id, u.nombre_completo AS creado_por_nombre
            FROM proveedores p
            JOIN tipos_documento t ON p.tipo_documento_id = t.id
            JOIN usuarios u ON p.creado_por_id = u.id
            ORDER BY p.razon_social
            """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ── BUSCAR POR ID ─────────────────────────────────────────────────────────
    public Proveedor buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT p.id, p.numero_documento, p.razon_social, p.nombre_contacto,
                   p.cargo_contacto, p.email, p.telefono, p.direccion,
                   p.pais, p.departamento, p.ciudad, p.tipo_proveedor,
                   p.estado, p.observaciones, p.fecha_registro,
                   t.id AS tipo_doc_id, t.nombre AS tipo_doc_nombre,
                   u.id AS creado_por_id, u.nombre_completo AS creado_por_nombre
            FROM proveedores p
            JOIN tipos_documento t ON p.tipo_documento_id = t.id
            JOIN usuarios u ON p.creado_por_id = u.id
            WHERE p.id = ?
            """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    // ── INSERTAR ──────────────────────────────────────────────────────────────
    public boolean insertar(Proveedor p) throws SQLException {
        String sql = """
            INSERT INTO proveedores
              (tipo_documento_id, numero_documento, razon_social, nombre_contacto,
               cargo_contacto, email, telefono, direccion, pais, departamento,
               ciudad, tipo_proveedor, estado, observaciones, creado_por_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1,  p.getTipoDocumentoId());
            ps.setString(2,  p.getNumeroDocumento());
            ps.setString(3,  p.getRazonSocial());
            ps.setString(4,  p.getNombreContacto());
            ps.setString(5,  p.getCargoContacto());
            ps.setString(6,  p.getEmail());
            ps.setString(7,  p.getTelefono());
            ps.setString(8,  p.getDireccion());
            ps.setString(9,  p.getPais());
            ps.setString(10, p.getDepartamento());
            ps.setString(11, p.getCiudad());
            ps.setString(12, p.getTipoProveedor());
            ps.setString(13, p.getEstado());
            ps.setString(14, p.getObservaciones());
            ps.setInt   (15, p.getCreadoPorId());
            return ps.executeUpdate() > 0;
        }
    }

    // ── ACTUALIZAR ────────────────────────────────────────────────────────────
    public boolean actualizar(Proveedor p) throws SQLException {
        String sql = """
            UPDATE proveedores SET
              tipo_documento_id = ?, numero_documento = ?, razon_social = ?,
              nombre_contacto = ?, cargo_contacto = ?, email = ?, telefono = ?,
              direccion = ?, pais = ?, departamento = ?, ciudad = ?,
              tipo_proveedor = ?, estado = ?, observaciones = ?
            WHERE id = ?
            """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt   (1,  p.getTipoDocumentoId());
            ps.setString(2,  p.getNumeroDocumento());
            ps.setString(3,  p.getRazonSocial());
            ps.setString(4,  p.getNombreContacto());
            ps.setString(5,  p.getCargoContacto());
            ps.setString(6,  p.getEmail());
            ps.setString(7,  p.getTelefono());
            ps.setString(8,  p.getDireccion());
            ps.setString(9,  p.getPais());
            ps.setString(10, p.getDepartamento());
            ps.setString(11, p.getCiudad());
            ps.setString(12, p.getTipoProveedor());
            ps.setString(13, p.getEstado());
            ps.setString(14, p.getObservaciones());
            ps.setInt   (15, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // ── ELIMINAR ──────────────────────────────────────────────────────────────
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM proveedores WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── MAPEAR ResultSet → Proveedor ──────────────────────────────────────────
    private Proveedor mapear(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor();
        p.setId               (rs.getInt("id"));
        p.setNumeroDocumento  (rs.getString("numero_documento"));
        p.setRazonSocial      (rs.getString("razon_social"));
        p.setNombreContacto   (rs.getString("nombre_contacto"));
        p.setCargoContacto    (rs.getString("cargo_contacto"));
        p.setEmail            (rs.getString("email"));
        p.setTelefono         (rs.getString("telefono"));
        p.setDireccion        (rs.getString("direccion"));
        p.setPais             (rs.getString("pais"));
        p.setDepartamento     (rs.getString("departamento"));
        p.setCiudad           (rs.getString("ciudad"));
        p.setTipoProveedor    (rs.getString("tipo_proveedor"));
        p.setEstado           (rs.getString("estado"));
        p.setObservaciones    (rs.getString("observaciones"));
        p.setTipoDocumentoId  (rs.getInt("tipo_doc_id"));
        p.setTipoDocumentoNombre(rs.getString("tipo_doc_nombre"));
        p.setCreadoPorId      (rs.getInt("creado_por_id"));
        p.setCreadoPorNombre  (rs.getString("creado_por_nombre"));
        if (rs.getTimestamp("fecha_registro") != null)
            p.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        return p;
    }
}
