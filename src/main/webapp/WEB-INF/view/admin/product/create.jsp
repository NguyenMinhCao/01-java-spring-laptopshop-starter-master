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
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>
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
                                        <a href="/admin" style="text-decoration: none;">Dashboard</a> / Create
                                    </li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create product</h3>
                                            <hr>
                                            <form:form class="row g-3" method="post"
                                                action="/admin/product/create-product" modelAttribute="newProduct"
                                                enctype="multipart/form-data">
                                                <c:set var="errorName">
                                                    <form:errors path="name" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorPrice">
                                                    <form:errors path="price" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorDetailDesc">
                                                    <form:errors path="detailDesc" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorShortDesc">
                                                    <form:errors path="shortDesc" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorQuantity">
                                                    <form:errors path="quantity" cssClass="invalid-feedback" />
                                                </c:set>
                                                <div class="col-md-6">
                                                    <label class="form-label">Name:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorName ? 'is-invalid':''}"
                                                        path="name" />
                                                    ${errorName}
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Price:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorPrice ? 'is-invalid':''}"
                                                        path="price" />
                                                    ${errorPrice}
                                                </div>
                                                <div class="col-md-12">
                                                    <label class="form-label">Detail Desc:</label>
                                                    <form:input type="textarea"
                                                        class="form-control ${not empty errorDetailDesc ? 'is-invalid':''}"
                                                        path="detailDesc" />
                                                    ${errorDetailDesc}
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Short Desc:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorShortDesc ? 'is-invalid':''}"
                                                        path="shortDesc" />
                                                    ${errorShortDesc}
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Quantity:</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty errorQuantity ? 'is-invalid':''}"
                                                        path="quantity" />
                                                    ${errorQuantity}
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Factory:</label>
                                                    <form:select class="form-select" aria-label="Default select example"
                                                        path="factory">
                                                        <form:option value="Apple">Apple(Macbook)</form:option>
                                                        <form:option value="Asus">Asus</form:option>
                                                        <form:option value="Lenovo">Lenovo</form:option>
                                                        <form:option value="Dell">Dell</form:option>
                                                        <form:option value="LG">LG</form:option>
                                                        <form:option value="Acer">Acer</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Target:</label>
                                                    <form:select class="form-select" aria-label="Default select example"
                                                        path="target">
                                                        <form:option value="Gaming">Gaming</form:option>
                                                        <form:option value="SVVP">Sinh Viên - Văn Phòng</form:option>
                                                        <form:option value="TKDoHoa">Thiết kế đồ họa</form:option>
                                                        <form:option value="MongNhe">Mỏng nhẹ</form:option>
                                                        <form:option value="DoanhNhan">Doanh Nhân</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col-md-12">
                                                    <label for="formFile" class="form-label">Avatar:</label>
                                                    <input class="form-control" type="file" id="avatarFile"
                                                        accept=".png , .jpg, .jpeg" name="getImgFile">
                                                </div>
                                                <div class="col-12 mb-3">
                                                    <img style="max-height: 250px; display: none;" alt="avatar preview"
                                                        id="avatarPreview">
                                                </div>
                                                <div class="col-md-12">
                                                    <button type="submit" class="btn btn-primary">Create</button>
                                                    <a style="margin-left: 10px;" class="btn btn-warning"
                                                        href="/admin/product">Back</a>
                                                </div>
                                            </form:form>
                                        </div>
                                    </div>
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