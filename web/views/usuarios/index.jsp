<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.sofinventory.modelos.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Usuarios — SofInventory</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<%-- Navbar --%>
<nav class="navbar">
    <span class="navbar-marca">SofInventory</span>
    <div class="navbar-links">
        <a href="${pageContext.request.contextPath}/usuarios" class="activo">Usuarios</a>
        <a href="${pageContext.request.contextPath}/proveedores">Proveedores</a>
        <span class="navbar-usuario">
            <%= session.getAttribute("nombreUsuario") %> |
            <a href="${pageContext.request.contextPath}/logout">Salir</a>
        </span>
    </div>
</nav>

<div class="contenedor">

    <%-- Mensajes --%>
    <% String msg = request.getParameter("mensaje"); %>
    <% if ("creado".equals(msg))     { %><div class="alerta alerta-ok">Usuario creado correctamente.</div><% } %>
    <% if ("actualizado".equals(msg)){ %><div class="alerta alerta-ok">Usuario actualizado correctamente.</div><% } %>
    <% if ("eliminado".equals(msg))  { %><div class="alerta alerta-ok">Usuario eliminado.</div><% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alerta alerta-error"><%= request.getAttribute("error") %></div>
    <% } %>

    <%-- Formulario nuevo / editar --%>
    <% String modo = (String) request.getAttribute("modo");
       Usuario editando = (Usuario) request.getAttribute("usuario"); %>

    <div class="card">
        <h2><%= "editar".equals(modo) ? "Editar usuario" : "Nuevo usuario" %></h2>

        <form action="${pageContext.request.contextPath}/usuarios" method="post">
            <input type="hidden" name="accion" value="<%= "editar".equals(modo) ? "actualizar" : "insertar" %>">
            <% if ("editar".equals(modo) && editando != null) { %>
                <input type="hidden" name="id" value="<%= editando.getId() %>">
            <% } %>

            <div class="form-grid">
                <%-- Tipo documento --%>
                <div class="campo">
                    <label>Tipo documento</label>
                    <select name="tipoDocumentoId" required>
                        <option value="">-- Seleccionar --</option>
                        <% List<String[]> tipos = (List<String[]>) request.getAttribute("tiposDocumento");
                           if (tipos != null) for (String[] td : tipos) { %>
                            <option value="<%= td[0] %>"
                                <%= (editando != null && String.valueOf(editando.getTipoDocumentoId()).equals(td[0])) ? "selected" : "" %>>
                                <%= td[1] %>
                            </option>
                        <% } %>
                    </select>
                </div>

                <%-- Número documento --%>
                <div class="campo">
                    <label>Número documento</label>
                    <input type="text" name="numeroDocumento" required maxlength="20"
                           value="<%= editando != null ? editando.getNumeroDocumento() : "" %>">
                </div>

                <%-- Nombre completo --%>
                <div class="campo campo-ancho">
                    <label>Nombre completo</label>
                    <input type="text" name="nombreCompleto" required maxlength="150"
                           value="<%= editando != null ? editando.getNombreCompleto() : "" %>">
                </div>

                <%-- Email --%>
                <div class="campo">
                    <label>Email</label>
                    <input type="email" name="email" required
                           value="<%= editando != null ? editando.getEmail() : "" %>">
                </div>

                <%-- Username --%>
                <div class="campo">
                    <label>Username</label>
                    <input type="text" name="username" required maxlength="50"
                           value="<%= editando != null ? editando.getUsername() : "" %>">
                </div>

                <%-- Contraseña --%>
                <div class="campo">
                    <label>Contraseña</label>
                    <input type="password" name="password"
                           <%= "editar".equals(modo) ? "" : "required" %>
                           placeholder="<%= "editar".equals(modo) ? "Dejar vacío para no cambiar" : "" %>">
                </div>

                <%-- Rol --%>
                <div class="campo">
                    <label>Rol</label>
                    <select name="rolId" required>
                        <option value="">-- Seleccionar --</option>
                        <% List<String[]> roles = (List<String[]>) request.getAttribute("roles");
                           if (roles != null) for (String[] r : roles) { %>
                            <option value="<%= r[0] %>"
                                <%= (editando != null && String.valueOf(editando.getRolId()).equals(r[0])) ? "selected" : "" %>>
                                <%= r[1] %>
                            </option>
                        <% } %>
                    </select>
                </div>

                <%-- Estado --%>
                <div class="campo">
                    <label>Estado</label>
                    <select name="estado" required>
                        <option value="activo"   <%= (editando == null || "activo".equals(editando.getEstado()))   ? "selected" : "" %>>Activo</option>
                        <option value="inactivo" <%= (editando != null && "inactivo".equals(editando.getEstado())) ? "selected" : "" %>>Inactivo</option>
                    </select>
                </div>

                <%-- Observaciones --%>
                <div class="campo campo-ancho">
                    <label>Observaciones</label>
                    <textarea name="observaciones" rows="2"><%= editando != null && editando.getObservaciones() != null ? editando.getObservaciones() : "" %></textarea>
                </div>
            </div>

            <div class="form-acciones">
                <button type="submit" class="btn btn-primario">
                    <%= "editar".equals(modo) ? "Actualizar" : "Guardar" %>
                </button>
                <% if ("editar".equals(modo)) { %>
                    <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-secundario">Cancelar</a>
                <% } %>
            </div>
        </form>
    </div>

    <%-- Tabla de usuarios --%>
    <div class="card">
        <h2>Usuarios registrados</h2>
        <div class="tabla-responsive">
            <table class="tabla">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Username</th>
                        <th>Documento</th>
                        <th>Email</th>
                        <th>Rol</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
                       if (usuarios != null) for (Usuario u : usuarios) { %>
                        <tr>
                            <td><%= u.getNombreCompleto() %></td>
                            <td><%= u.getUsername() %></td>
                            <td><%= u.getTipoDocumentoNombre() %> <%= u.getNumeroDocumento() %></td>
                            <td><%= u.getEmail() %></td>
                            <td><span class="badge"><%= u.getRolNombre() %></span></td>
                            <td><span class="badge badge-<%= u.getEstado() %>"><%= u.getEstado() %></span></td>
                            <td class="acciones">
                                <a href="${pageContext.request.contextPath}/usuarios?accion=editar&id=<%= u.getId() %>"
                                   class="btn btn-sm btn-editar">Editar</a>
                                <a href="${pageContext.request.contextPath}/usuarios?accion=eliminar&id=<%= u.getId() %>"
                                   class="btn btn-sm btn-eliminar"
                                   onclick="return confirm('¿Eliminar este usuario?')">Eliminar</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
