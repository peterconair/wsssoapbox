<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Result.</title>
        <style type="text/css">
            caption{
                color: #FFFFFF;
                background:#4682B4;
                font-size:140%;
                border:1px solid #000;
                border-bottom:none;
                padding:5px;
                text-align:left;
            }
            table {
                border:1px solid #000;
                border-collapse:collapse;
                font-family:arial,sans-serif;
                font-size:80%;
            }
            td,th{
                border:1px solid #000;
                border-collapse:collapse;
                padding:5px;
            }
            thead th{
                color: #FFFFFF;
                background:#3A5FCD;
                text-align:center;

            }
            tbody th{
                text-align:center;
                background:#69c;
            }
            tfoot td{
                text-align:right;
                font-weight:bold;
                background:#7AC5CD;
            }
            tbody td{
                background:#D6D6D6;
                text-align:center;
            }
            tbody tr.odd td{
                background:#F2F2F2;
            }

        </style>
    </head>
    <body>

        <table>
            <caption>Payment Transaction</caption>
            <thead>
                <tr>
                    <th width="30">No.</th>
                    <th width="100">Payment ID</th>
                    <th width="120">Payment Date</th>
                    <th width="120">Credit No</th>
                    <th width="180">Name on Card</th>
                    <th width="100">Amount (THB)</th>
                    <th width="75">Exp. Date</th>
                    <th width="50">CVV</th>
                    <th width="100">Statue</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach items="${payments}" var="payment" varStatus="row">

                    <c:if test="${row.count % 2 != 0}">
                        <tr>
                        </c:if>   
                        <c:if test="${row.count % 2 == 0}">
                        <tr class="odd">
                        </c:if>                

                        <td><c:out value="${row.count}" /></td>
                        <td><c:out value="${payment.paymentID}" /></td>
                        <td><c:out value="${payment.paymentDate}" /></td>
                        <td><c:out value="${payment.cardNumber}" /></td>
                        <td><c:out value="${payment.nameOnCard}" /></td>
                        <td><c:out value="${payment.amount}" /></td>
                        <td><c:out value="${payment.expirationDate}" /></td>
                        <td><c:out value="${payment.securityCode}" /></td>
                        <td><c:out value="${payment.paymentStatus}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="9">Total: ${total} Baht</td>
                </tr>
            </tfoot>
        </table>
    </body>
</html>