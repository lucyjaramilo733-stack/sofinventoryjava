package com.sofinventory.servlets;

import com.sofinventory.dao.UsuarioDAO;
import com.sofinventory.modelos.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();

    // ── GET: listar o cargar formulario de edición ────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");

        try {
            // Cargar listas para los <select> del formulario
            req.setAttribute("tiposDocumento", dao.listarCatalogoCompleto("tipos_documento"));
            req.setAttribute("roles",          dao.listarCatalogoCompleto("roles"));

            if ("editar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("usuario", dao.buscarPorId(id));
                req.setAttribute("modo", "editar");
            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("id"));
                dao.eliminar(id);
                res.sendRedirect("usuarios?mensaje=eliminado");
                return;
            } else {
                req.setAttribute("modo", "nuevo");
            }

            req.setAttribute("usuarios", dao.listar());
            req.getRequestDispatcher("/views/usuarios/index.jsp").forward(req, res);

        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("/views/usuarios/index.jsp").forward(req, res);
        }
    }

    // ── POST: insertar o actualizar ───────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        try {
            Usuario u = new Usuario();
            u.setTipoDocumentoId (Integer.parseInt(req.getParameter("tipoDocumentoId")));
            u.setNumeroDocumento (req.getParameter("numeroDocumento"));
            u.setNombreCompleto  (req.getParameter("nombreCompleto"));
            u.setEmail           (req.getParameter("email"));
            u.setUsername        (req.getParameter("username"));
            u.setPassword        (req.getParameter("password"));
            u.setRolId           (Integer.parseInt(req.getParameter("rolId")));
            u.setEstado          (req.getParameter("estado"));
            u.setObservaciones   (req.getParameter("observaciones"));
            u.setFechaCreacion   (LocalDate.now());

            if ("actualizar".equals(accion)) {
                u.setId(Integer.parseInt(req.getParameter("id")));
                dao.actualizar(u);
                res.sendRedirect("usuarios?mensaje=actualizado");
            } else {
                dao.insertar(u);
                res.sendRedirect("usuarios?mensaje=creado");
            }

        } catch (Exception e) {
            req.setAttribute("error", "Error al guardar: " + e.getMessage());
            try {
                req.setAttribute("usuarios", dao.listar());
                req.setAttribute("tiposDocumento", dao.listarCatalogoCompleto("tipos_documento"));
                req.setAttribute("roles",          dao.listarCatalogoCompleto("roles"));
            } catch (Exception ex) { /* ignorar */ }
            req.getRequestDispatcher("/views/usuarios/index.jsp").forward(req, res);
        }
    }
}
