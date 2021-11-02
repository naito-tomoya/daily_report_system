<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actFol" value="${ForwardConst.ACT_FOL.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commFollow" value="${ForwardConst.CMD_FOLLOW.getValue()}" />
<c:set var="commDelete" value="${ForwardConst.CMD_DELETE.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員 一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                    <th>フォロー</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <c:set var="flag" value="${flagList[status.count - 1]}" />
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td><c:choose>
                                <c:when
                                    test="${employee.deleteFlag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a
                                        href="<c:url value='?action=${actEmp}&command=${commShow}&id=${employee.id}' />"><button>詳細を見る</button></a>
                                </c:otherwise>
                            </c:choose></td>
                        <td><c:choose>
                                <c:when test="${employee.id == login_employee.id}">
                                    <p></p>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when
                                            test="${employee.deleteFlag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">
                                    （削除済み）
                                </c:when>
                                        <c:when test="${flag == 0}">
                                            <a
                                                href="<c:url value='?action=${actFol}&command=${commFollow}&followId=${employee.id}' />"><button>フォローする</button></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a
                                                href="<c:url value='?action=${actFol}&command=${commDelete}&followId=${employee.id}' />"><button>フォロー解除</button></a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${employees_count} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((employees_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a
                            href="<c:url value='?action=${actEmp}&command=${commIdx}&page=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p>
            <a href="<c:url value='?action=${actEmp}&command=${commNew}' />">新規従業員の登録</a>
        </p>

    </c:param>
</c:import>