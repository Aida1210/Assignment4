<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 15.10.2020
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>controllers.Main</title>
</head>
<body>
<jsp:include page="blocks/header.jsp"/>


<div class="container text-center">
    <div class="row">
        <div class="col-3"><a class="btn btn-info" style="margin-left: 15px;"
                              href='<%=request.getContextPath()+"/main?sort_by=price"%>'>Sort by Price</a>
        </div>
        <div class="col-3">
            <button id="Smartphone" onclick="action(this.id)" class="btn btn-success">Smartphone</button>
        </div>
        <div class="col-3">
            <button id="House" onclick="action(this.id)" class="btn btn-warning">House</button>
        </div>
        <div class="col-3">
            <button id="Game" onclick="action(this.id)" class="btn btn-primary">Game</button>
        </div>
    </div>
    <form action="<%=request.getContextPath()%>/confirm" method="post">
        <div id="row" class="row">
            <c:forEach items="${books}" var="book">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-xl-3 text-center shadow rounded pt-5 my-5 ${product.getCategory()}">
                    <img src="${book.getImage()}" alt="${book.getName()}"
                         style="height: 200px;max-width: 200px;">
                    <h2>${book.getName()}</h2>
                    <p>${book.getAuthor()}</p>
                    <p><b>In Stock</b></p>
                    <h4>${book.getStock()}</h4>

                    <div>
                        <input type="checkbox" id="${book.getName()}" name="products" value="${book.getName()}">
                        <label for="${book.getName()}">Add to Favorite</label>
                        <c:if test="${cookie.role.value=='ADMIN'}">
                            <button id="${book.getIsbn()}" class="btn btn-danger" onclick="deleteProduct(this.id)">
                                Remove
                            </button>

                        </c:if>
                    </div>


                        <%--                <p><a class="btn btn-secondary" th:href="'/'+${food.getCategory()}+'/'+${food.getName()}" role="button">View--%>
                        <%--                    details »</a></p>--%>

                        <%--                <div class="row text-center">--%>
                        <%--                    <a class="btn btn-success mb-4 mx-4" th:href="'/food/edit/'+${food.getID()}"--%>
                        <%--                        role="button">Edit--%>
                        <%--                        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-tools" fill="currentColor"--%>
                        <%--                             xmlns="http://www.w3.org/2000/svg">--%>
                        <%--                            <path fill-rule="evenodd"--%>
                        <%--                                  d="M0 1l1-1 3.081 2.2a1 1 0 0 1 .419.815v.07a1 1 0 0 0 .293.708L10.5 9.5l.914-.305a1 1 0 0 1 1.023.242l3.356 3.356a1 1 0 0 1 0 1.414l-1.586 1.586a1 1 0 0 1-1.414 0l-3.356-3.356a1 1 0 0 1-.242-1.023L9.5 10.5 3.793 4.793a1 1 0 0 0-.707-.293h-.071a1 1 0 0 1-.814-.419L0 1zm11.354 9.646a.5.5 0 0 0-.708.708l3 3a.5.5 0 0 0 .708-.708l-3-3z"/>--%>
                        <%--                            <path fill-rule="evenodd"--%>
                        <%--                                  d="M15.898 2.223a3.003 3.003 0 0 1-3.679 3.674L5.878 12.15a3 3 0 1 1-2.027-2.027l6.252-6.341A3 3 0 0 1 13.778.1l-2.142 2.142L12 4l1.757.364 2.141-2.141zm-13.37 9.019L3.001 11l.471.242.529.026.287.445.445.287.026.529L5 13l-.242.471-.026.529-.445.287-.287.445-.529.026L3 15l-.471-.242L2 14.732l-.287-.445L1.268 14l-.026-.529L1 13l.242-.471.026-.529.445-.287.287-.445.529-.026z"/>--%>
                        <%--                        </svg>--%>
                        <%--                    </a>--%>
                        <%--                    <form th:action="'/food/remove/'+${food.getID()}"--%>
                        <%--                          method="post">--%>
                        <%--                        <input type="submit" value="Remove" class="btn btn-danger">--%>
                        <%--                    </form>--%>
                </div>

            </c:forEach>
        </div>
        <br>
        <div id="addtocart" class="text-center"><input type="submit" class="btn btn-outline-primary"
                                                       value="Add to Cart"></div>
    </form>

</div>
<jsp:include page="blocks/footer.jsp"/>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    function action(classname) {
        $("." + classname).removeClass("d-none");
        $('#row > *').not($('.' + classname)).addClass('d-none');
        $('#addtocart').removeClass('d-none');
    }

    function deleteProduct(idd) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/assignment3_war/main",
            data: {
                reqValue: idd
            },
            cache: false,
            timeout: 600000,
            success: function (data) {
                alert("Success")
            },
            error: function (response, error, errorThrown) {
                alert("Error")
            }
        });
    }
</script>
</html>
