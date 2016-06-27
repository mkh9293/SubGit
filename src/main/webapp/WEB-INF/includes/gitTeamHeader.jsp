<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@include file="/WEB-INF/includes/teamSrc.jsp" %>

<title>SUBGIT</title>
<!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                 <a class="navbar-brand" href="${pageContext.request.contextPath}/">Subgit</a>
            </div>
            
            <!-- Sidebar Menu Items -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li>
                        <a href="${pageContext.request.contextPath}/git/teampage/teamEnter?teamURL=${gitURL }&leaderNum_fk=${leaderNum_fk}&teamName=${teamName}&intro=${intro }"><i class="fa fa-fw fa-dashboard"></i> Team</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/git/teampage/fileBrowse?st=${plist.stName }&teamURL=${gitURL }&leaderNum_fk=${leaderNum_fk}&teamRepo=${uri}&teamName=${teamName}&intro=${intro }"><i class="fa fa-fw fa-desktop"></i>FileBrowse</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#list"><i class="glyphicon glyphicon-user"></i> Member <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="list" class="collapse">
                        <c:forEach var="plist" items="${p_list }" varStatus="i">
                            <li>
                                <a href="${pageContext.request.contextPath}/git/teampage/team/gitMember?st=${plist.stName }&teamRepo=${uri}&teamURL=${gitURL }&leaderNum_fk=${leaderNum_fk}&teamName=${teamName}&intro=${intro }">${plist.stName } (${plist.stNum})</a>
                            </li>
                         </c:forEach>
                        </ul>
                    </li>
                    
                    <li>
                    <a href="${pageContext.request.contextPath}/git/teampage/chart?st=${plist.stName }&teamURL=${gitURL }&leaderNum_fk=${leaderNum_fk}&teamRepo=${uri}&teamName=${teamName}&intro=${intro }"><i class="fa fa-bar-chart" aria-hidden="true"></i> chart</a>
                    
                   
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>