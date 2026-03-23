package com.sofinventory.servlets;

import com.sofinventory.dao.UsuarioDAO;
import com.sofinventory.modelos.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();

    // ── GET: mostrar formulario de login ──────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // Si ya hay sesión activa, redirigir al dashboard
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            res.sendRedirect("usuarios");
            return;
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, res);
    }

    // ── POST: validar credenciales ────────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Usuario usuario = dao.login(username, password);

            if (usuario != null) {
                // Crear sesión
                HttpSession session = req.getSession();
                session.setAttribute("usuarioLogueado", usuario);
                session.setAttribute("nombreUsuario",   usuario.getNombreCompleto());
                session.setAttribute("rolUsuario",      usuario.getRolNombre());
                res.sendRedirect("usuarios");
            } else {
                req.setAttribute("error", "Usuario o contraseña incorrectos");
                req.getRequestDispatcher("/views/login.jsp").forward(req, res);
            }

        } catch (Exception e) {
            req.setAttribute("error", "Error del sistema: " + e.getMessage());
            req.getRequestDispatcher("/views/login.jsp").forward(req, res);
        }
    }
}
    