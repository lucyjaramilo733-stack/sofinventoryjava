package com.sofinventory.servlets;

import com.sofinventory.dao.ProveedorDAO;
import com.sofinventory.dao.UsuarioDAO;
import com.sofinventory.modelos.Proveedor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/proveedores")
public class ProveedorServlet extends HttpServlet {

    private final ProveedorDAO dao        = new ProveedorDAO();
    private final UsuarioDAO   usuarioDao = new UsuarioDAO();

    // ── GET: listar o cargar formulario de edición ────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");

        try {
            // Cargar listas para los <select>
            req.setAttribute("tiposDocumento", usuarioDao.listarCatalogoCompleto("tipos_documento"));
            req.setAttribute("usuarios",       usuarioDao.listarCatalogoCompleto("usuarios"));

            if ("editar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("proveedor", dao.buscarPorId(id));
                req.setAttribute("modo", "editar");
            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("id"));
                dao.eliminar(id);
                res.sendRedirect("proveedores?mensaje=eliminado");
                return;
            } else {
                req.setAttribute("modo", "nuevo");
            }

            req.setAttribute("proveedores", dao.listar());
            req.getRequestDispatcher("/views/proveedores/index.jsp").forward(req, res);

        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("/views/proveedores/index.jsp").forward(req, res);
        }
    }

    // ── POST: insertar o actualizar ───────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        try {
            Proveedor p = new Proveedor();
            p.setTipoDocumentoId (Integer.parseInt(req.getParameter("tipoDocumentoId")));
            p.setNumeroDocumento (req.getParameter("numeroDocumento"));
            p.setRazonSocial     (req.getParameter("razonSocial"));
            p.setNombreContacto  (req.getParameter("nombreContacto"));
            p.setCargoContacto   (req.getParameter("cargoContacto"));
            p.setEmail           (req.getParameter("email"));
            p.setTelefono        (req.getParameter("telefono"));
            p.setDireccion       (req.getParameter("direccion"));
            p.setPais            (req.getParameter("pais"));
            p.setDepartamento    (req.getParameter("departamento"));
            p.setCiudad          (req.getParameter("ciudad"));
            p.setTipoProveedor   (req.getParameter("tipoProveedor"));
            p.setEstado          (req.getParameter("estado"));
            p.setObservaciones   (req.getParameter("observaciones"));
            p.setCreadoPorId     (Integer.parseInt(req.getParameter("creadoPorId")));

            if ("actualizar".equals(accion)) {
                p.setId(Integer.parseInt(req.getParameter("id")));
                dao.actualizar(p);
                res.sendRedirect("proveedores?mensaje=actualizado");
            } else {
                dao.insertar(p);
                res.sendRedirect("proveedores?mensaje=creado");
            }

        } catch (Exception e) {
            req.setAttribute("error", "Error al guardar: " + e.getMessage());
            try {
                req.setAttribute("proveedores", dao.listar());
                req.setAttribute("tiposDocumento", usuarioDao.listarCatalogoCompleto("tipos_documento"));
                req.setAttribute("usuarios",       usuarioDao.listarCatalogoCompleto("usuarios"));
            } catch (Exception ex) { /* ignorar */ }
            req.getRequestDispatcher("/views/proveedores/index.jsp").forward(req, res);
        }
    }
}
