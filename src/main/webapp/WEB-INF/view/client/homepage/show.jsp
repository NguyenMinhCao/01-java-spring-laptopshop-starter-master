<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <title>Trang chủ - Laptopshop</title>
                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link
                        href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                        rel="stylesheet">

                    <!-- Icon Font Stylesheet -->
                    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                        rel="stylesheet">

                    <!-- Libraries Stylesheet -->
                    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


                    <!-- Customized Bootstrap Stylesheet -->
                    <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                    <!-- Template Stylesheet -->
                    <link href="/client/css/style.css" rel="stylesheet">
                </head>

                <body>

                    <!-- Spinner Start -->
                    <div id="spinner"
                        class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                    <!-- Spinner End -->


                    <!-- Navbar start -->
                    <jsp:include page="../layout/header.jsp" />
                    <!-- Navbar End -->

                    <!-- Hero Start -->
                    <jsp:include page="../layout/banner.jsp" />
                    <!-- Hero End -->

                    <!-- Featurs Section Start -->
                    <jsp:include page="../layout/feature.jsp" />
                    <!-- Featurs Section End -->

                    <!-- Fruits Shop Start-->
                    <div class="container-fluid fruite py-5">
                        <div class="container py-5">
                            <div class="tab-class text-center">
                                <div class="row g-4">
                                    <div class="col-lg-4 text-start">
                                        <h1>Sản phẩm nổi bật</h1>
                                    </div>
                                    <div class="col-lg-8 text-end">
                                        <ul class="nav nav-pills d-inline-flex text-center mb-5">
                                            <li class="nav-item">
                                                <a class="d-flex m-2 py-2 bg-light rounded-pill active"
                                                    data-bs-toggle="pill" href="#tab-1">
                                                    <span class="text-dark" style="width: 130px;">All Products</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="tab-content">
                                    <div class="row g-4">
                                        <div class="col-lg-12">
                                            <div class="row g-4">
                                                <c:forEach items="${listProducts}" var="product">
                                                    <div class="col-md-6 col-lg-4 col-xl-3">
                                                        <div class="rounded position-relative fruite-item product-card">
                                                            <div class="fruite-img">
                                                                <img src="/images/product/${product.image}"
                                                                    class="img-fluid w-100 rounded-top" alt="">
                                                            </div>
                                                            <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                                style="top: 10px; left: 10px;">
                                                                Laptop
                                                            </div>
                                                            <div
                                                                class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                <a href="/product/${product.id}">
                                                                    <h4 style="font-size: 15px;">${product.name}
                                                                    </h4>
                                                                </a>
                                                                <p style="font-size: 13px;">${product.shortDesc}</p>
                                                                <div
                                                                    class="d-flex flex-lg-wrap justify-content-center flex-column">
                                                                    <p style="font-size: 15px; text-align: center; width: 100%;"
                                                                        class="text-dark fw-bold mb-3">
                                                                        <fmt:formatNumber type="number"
                                                                            value="${product.price}" /> đ
                                                                    </p>
                                                                    <form action="add-product-to-cart/${product.id}"
                                                                        method="post">
                                                                        <input type="hidden"
                                                                            name="${_csrf.parameterName}"
                                                                            value="${_csrf.token}" />
                                                                        <button
                                                                            class="mx-auto btn border border-secondary rounded-pill px-3 text-primary fa fa-shopping-bag me-2 text-primary">
                                                                            Add to cart
                                                                        </button>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                    <nav aria-label="Page navigation example" class="pagination-nav">
                                        <ul class="pagination justify-content-center" style="display: flex;">
                                            <li class="${1 eq currentPage ? 'disabled page-item' : 'page-item'}">
                                                <a class="page-link" href="/?page=${currentPage - 1}"
                                                    aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                    <span class="sr-only">Previous</span>
                                                </a>
                                            </li>
                                            <c:forEach begin="0" end="${totalPages - 1}" varStatus="loop">
                                                <li class="page-item">
                                                    <a class="${(loop.index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                                                        href="/?page=${loop.index + 1}">
                                                        ${loop.index + 1}
                                                    </a>
                                                </li>
                                            </c:forEach>
                                            <li
                                                class="${totalPages eq currentPage ? 'disabled page-item' : 'page-item'}">
                                                <a class="page-link" href="/?page=${currentPage + 1}" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                    <span class="sr-only">Next</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Fruits Shop End-->

                    <!-- Footer Start -->
                    <jsp:include page="../layout/footer.jsp" />
                    <!-- Footer End -->

                    <!-- Back to Top -->
                    <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                            class="fa fa-arrow-up"></i></a>


                    <!-- JavaScript Libraries -->
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script src="/client/lib/easing/easing.min.js"></script>
                    <script src="/client/lib/waypoints/waypoints.min.js"></script>
                    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                    <!-- Template Javascript -->
                    <script src="/client/js/main.js"></script>
                </body>

                </html>