<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <!-- Latest compiled JavaScript -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <!-- <link rel="stylesheet" href="/css/demo.css"> -->
            </head>

            <body>
                <div class="container mt-5">
                    <div class="d-flex justify-content-between">
                        <h3>Table user</h3>
                        <a href="/admin/user/create" class="btn btn-primary">Create a user</a>
                    </div>
                    <hr>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Email</th>
                                <th scope="col">Full Name</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listUser}" var="user">
                                <tr>
                                    <th scope="row">${user.id}</th>
                                    <td>${user.email}</td>
                                    <td>${user.fullName}</td>
                                    <td>
                                        <a href="" class="btn btn-success">View</a>
                                        <a href="/admin/user/view-updateId/${user.id}"
                                            class="btn btn-warning">Update</a>
                                        <a href="/admin/user/deteleId/${user.id}" class="btn btn-danger">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </body>

            </html>