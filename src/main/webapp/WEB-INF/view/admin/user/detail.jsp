<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Dashboard - SB Admin</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <!-- Latest compiled JavaScript -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <!-- <link rel="stylesheet" href="/css/demo.css"> -->
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />

                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Dashboard</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active">
                                        <a href="/admin" style="text-decoration: none;">Dashboard</a> / Detail
                                    </li>
                                </ol>
                                <div class="container mt-5">
                                    <section class="w-100 px-4 py-5"
                                        style="background-color: #9de2ff; border-radius: .5rem .5rem 0 0;">
                                        <div class="row d-flex justify-content-center">
                                            <div class="col col-md-9 col-lg-7 col-xl-6">
                                                <div class="card" style="border-radius: 15px;">
                                                    <div class="card-body p-4">
                                                        <div class="d-flex">
                                                            <div class="flex-shrink-0">
                                                                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-profiles/avatar-1.webp"
                                                                    alt="Generic placeholder image" class="img-fluid"
                                                                    style="width: 180px; border-radius: 10px;">
                                                            </div>
                                                            <div class="flex-grow-1 ms-3">
                                                                <h5 class="mb-1">${userName}</h5>
                                                                <p class="mb-2 pb-1">${userEmail}</p>
                                                                <div
                                                                    class="d-flex justify-content-start rounded-3 p-2 mb-2 bg-body-tertiary">
                                                                    <div>
                                                                        <p class="small text-muted mb-1">Address</p>
                                                                        <p class="mb-0">${userAddress}</p>
                                                                    </div>
                                                                    <div class="px-3">
                                                                        <p class="small text-muted mb-1">Phone</p>
                                                                        <p class="mb-0">${userPhone}</p>
                                                                    </div>
                                                                    <div>
                                                                        <p class="small text-muted mb-1">PassWord</p>
                                                                        <p class="mb-0">${userPass}</p>
                                                                    </div>
                                                                </div>
                                                                <div class="d-flex pt-1">
                                                                    <button type="button" data-mdb-button-init
                                                                        data-mdb-ripple-init
                                                                        class="btn btn-outline-primary me-1 flex-grow-1">Chat</button>
                                                                    <a style="margin-left: 10px;"
                                                                        class="btn btn-primary flex-grow-1"
                                                                        href="/admin/user">Back</a>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
            </body>

            </html>