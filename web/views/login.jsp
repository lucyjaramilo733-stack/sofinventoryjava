<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SofInventory — Iniciar sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body class="login-page">

<div class="login-container">
    <div class="login-logo">
        <h1>SofInventory</h1>
        <p>Sistema de Inventarios y Ventas</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alerta alerta-error">
            <%= request.getAttribute("error") %>
        </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">
        <div class="campo">
            <label for="username">Usuario</label>
            <input type="text" id="username" name="username"
                   placeholder="Ingresa tu usuario" required autofocus>
        </div>
        <div class="campo">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password"
                   placeholder="Ingresa tu contraseña" required>
        </div>
        <button type="submit" class="btn btn-primario btn-bloque">
            Iniciar sesión
        </button>
    </form>
</div>

</body>
</html>
