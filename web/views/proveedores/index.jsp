
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.sofinventory.modelos.Proveedor" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Proveedores — SofInventory</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>

<nav class="navbar">
    <span class="navbar-marca">SofInventory</span>
    <div class="navbar-links">
        <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
        <a href="${pageContext.request.contextPath}/proveedores" class="activo">Proveedores</a>
        <span class="navbar-usuario">
            <%= session.getAttribute("nombreUsuario") %> |
            <a href="${pageContext.request.contextPath}/logout">Salir</a>
        </span>
    </div>
</nav>

    <div class="contenedor">

    <% String msg = request.getParameter("mensaje"); %>
    <% if ("creado".equals(msg))     { %><div class="alerta alerta-ok">Proveedor creado correctamente.</div><% } %>
    <% if ("actualizado".equals(msg)){ %><div class="alerta alerta-ok">Proveedor actualizado correctamente.</div><% } %>
    <% if ("eliminado".equals(msg))  { %><div class="alerta alerta-ok">Proveedor eliminado.</div><% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alerta alerta-error"><%= request.getAttribute("error") %></div>
    <% } %>

    <% String modo = (String) request.getAttribute("modo");
       Proveedor editando = (Proveedor) request.getAttribute("proveedor"); %>

    <div class="card">
        <h2><%= "editar".equals(modo) ? "Editar proveedor" : "Nuevo proveedor" %></h2>

        <form action="${pageContext.request.contextPath}/proveedores" method="post">
            <input type="hidden" name="accion" value="<%= "editar".equals(modo) ? "actualizar" : "insertar" %>">
            <% if ("editar".equals(modo) && editando != null) { %>
                <input type="hidden" name="id" value="<%= editando.getId() %>">
            <% } %>

            <div class="form-grid">

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

                <div class="campo">
                    <label>Número documento</label>
                    <input type="text" name="numeroDocumento" required maxlength="20"
                           value="<%= editando != null ? editando.getNumeroDocumento() : "" %>">
                </div>

                <div class="campo campo-ancho">
                    <label>Razón social</label>
                    <input type="text" name="razonSocial" required maxlength="150"
                           value="<%= editando != null ? editando.getRazonSocial() : "" %>">
                </div>

                <div class="campo">
                    <label>Nombre contacto</label>
                    <input type="text" name="nombreContacto" required maxlength="100"
                           value="<%= editando != null ? editando.getNombreContacto() : "" %>">
                </div>

                <div class="campo">
                    <label>Cargo contacto</label>
                    <input type="text" name="cargoContacto" maxlength="100"
                           value="<%= editando != null && editando.getCargoContacto() != null ? editando.getCargoContacto() : "" %>">
                </div>

                <div class="campo">
                    <label>Email</label>
                    <input type="email" name="email" required
                           value="<%= editando != null ? editando.getEmail() : "" %>">
                </div>

                <div class="campo">
                    <label>Teléfono</label>
                    <input type="text" name="telefono" required maxlength="20"
                           value="<%= editando != null ? editando.getTelefono() : "" %>">
                </div>

                <div class="campo campo-ancho">
                    <label>Dirección</label>
                    <input type="text" name="direccion" required maxlength="200"
                           value="<%= editando != null ? editando.getDireccion() : "" %>">
                </div>

                <div class="campo">
                    <label>País</label>
                    <input type="text" name="pais" required maxlength="100"
                           value="<%= editando != null ? editando.getPais() : "Colombia" %>">
                </div>

                <div class="campo">
                    <label>Departamento</label>
                    <input type="text" name="departamento" required maxlength="100"
                           value="<%= editando != null ? editando.getDepartamento() : "" %>">
                </div>

                <div class="campo">
                    <label>Ciudad</label>
                    <input type="text" name="ciudad" required maxlength="100"
                           value="<%= editando != null ? editando.getCiudad() : "" %>">
                </div>

                <div class="campo">
                    <label>Tipo proveedor</label>
                    <select name="tipoProveedor" required>
                        <option value="">-- Seleccionar --</option>
                        <option value="Bienes"    <%= (editando != null && "Bienes".equals(editando.getTipoProveedor()))    ? "selected" : "" %>>Bienes (Productos)</option>
                        <option value="Servicios" <%= (editando != null && "Servicios".equals(editando.getTipoProveedor())) ? "selected" : "" %>>Servicios</option>
                        <option value="Mixto"     <%= (editando != null && "Mixto".equals(editando.getTipoProveedor()))     ? "selected" : "" %>>Mixto</option>
                    </select>
                </div>

                <div class="campo">
                    <label>Estado</label>
                    <select name="estado" required>
                        <option value="Activo"   <%= (editando == null || "Activo".equals(editando.getEstado()))   ? "selected" : "" %>>Activo</option>
                        <option value="Inactivo" <%= (editando != null && "Inactivo".equals(editando.getEstado())) ? "selected" : "" %>>Inactivo</option>
                    </select>
                </div>

                <div class="campo">
                    <label>Creado por (usuario)</label>
                    <select name="creadoPorId" required>
                        <option value="">-- Seleccionar usuario --</option>
                        <% List<String[]> usuariosLista = (List<String[]>) request.getAttribute("usuarios");
                           if (usuariosLista != null) for (String[] u : usuariosLista) { %>
                            <option value="<%= u[0] %>"
                                <%= (editando != null && String.valueOf(editando.getCreadoPorId()).equals(u[0])) ? "selected" : "" %>>
                                <%= u[1] %>
                            </option>
                        <% } %>
                    </select>
                </div>

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
                    <a href="${pageContext.request.contextPath}/proveedores" class="btn btn-secundario">Cancelar</a>
                <% } %>
            </div>
        </form>
    </div>

    <div class="card">
        <h2>Proveedores registrados</h2>
        <div class="tabla-responsive">
            <table class="tabla">
                <thead>
                    <tr>
                        <th>Razón social</th>
                        <th>Documento</th>
                        <th>Contacto</th>
                        <th>Email</th>
                        <th>Ciudad</th>
                        <th>Tipo</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% List<Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores");
                       if (proveedores != null) for (Proveedor p : proveedores) { %>
                        <tr>
                            <td><%= p.getRazonSocial() %></td>
                            <td><%= p.getTipoDocumentoNombre() %> <%= p.getNumeroDocumento() %></td>
                            <td><%= p.getNombreContacto() %></td>
                            <td><%= p.getEmail() %></td>
                            <td><%= p.getCiudad() %></td>
                            <td><span class="badge"><%= p.getTipoProveedor() %></span></td>
                            <td><span class="badge badge-<%= p.getEstado().toLowerCase() %>"><%= p.getEstado() %></span></td>
                            <td class="acciones">
                                <a href="${pageContext.request.contextPath}/proveedores?accion=editar&id=<%= p.getId() %>"
                                   class="btn btn-sm btn-editar">Editar</a>
                                <a href="${pageContext.request.contextPath}/proveedores?accion=eliminar&id=<%= p.getId() %>"
                                   class="btn btn-sm btn-eliminar"
                                   onclick="return confirm('¿Eliminar este proveedor?')">Eliminar</a>
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
