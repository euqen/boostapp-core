<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="en" ng-app="app">
<head>
    <meta charset="utf-8">
    <title>Boost App - Best crowdfunding platform ever!</title>

    <s:url var="ctxUrl" forceAddSchemeHostAndPort="true" includeContext="true" value="/" namespace="/" ></s:url>
    <base href="<s:property value="ctxUrl"/>">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
    <link rel="stylesheet" href="<s:url value="css/custom.css" />" />
</head>
<body>

<div ng-controller="boostapp" class="container">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <a href="/" class="navbar-brand">Home</a> - <a class="navbar-brand" href="/projects">Projects</a> - <a class="navbar-brand" ng-if="context" href="/companies">My Companies</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <a type="submit" class="btn btn-success" href="/signin" style="float: right; margin-top:7px;" ng-if="!context.email">Sign in</a>
                <span style="float: right" ng-if="context">
                    <a href="/user/{{context.id}}" class="navbar-brand">{{context.email}}</a>
                    <a ng-click="signout()" class="btn btn-success" href="#" style="float: right;margin-top:7px;">Logout</a>
                </span>
            </div>
        </div>
    </nav>
    <br>
    <div ng-view></div>
</div>

<footer class="footer">
    <div class="container">
        <p class="text-muted" lingdex="2">(C) Angular + Struts 2 Test APP For SPP.</p>
    </div>
</footer>

<script src="<s:url value="js/lib/angular/angular.min.js" />"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
<script src="<s:url value="js/lib/angular/angular-route.min.js" />"></script>
<script src="<s:url value="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-cookies.js" />"></script>
<script src="<s:url value="modules/app.js" />"></script>
<script src="<s:url value="shared/context.js" />"></script>
<script src="<s:url value="internal/service.js" />"></script>
<script src="<s:url value="modules/home/home.controller.js" />"></script>
<script src="<s:url value="modules/signup/signup.controller.js" />"></script>
<script src="<s:url value="modules/signin/signin.controller.js" />"></script>
<script src="<s:url value="modules/projects/projects.controller.js" />"></script>
<script src="<s:url value="modules/projects/details/projects.details.controller.js" />"></script>
<script src="<s:url value="modules/projects/page/projects.page.controller.js" />"></script>
<script src="<s:url value="shared/user.service.js" />"></script>
<script src="<s:url value="modules/home/home.service.js" />"></script>
<script src="<s:url value="modules/projects/projects.service.js" />"></script>
<script src="<s:url value="modules/companies/company.service.js" />"></script>
<script src="<s:url value="modules/companies/details/company.details.controller.js" />"></script>
<script src="<s:url value="modules/companies/page/company.page.controller.js" />"></script>
<script src="<s:url value="modules/users/page/user.page.controller.js" />"></script>
<script src="<s:url value="shared/invite.service.js" />"></script>
<script src="<s:url value="shared/payment.service.js" />"></script>
<script src="<s:url value="modules/companies/company.controller.js" />"></script>
<script src="<s:url value="modules/404/404.controller.js" />"></script>
<script src="<s:url value="modules/block/blocked.controller.js" />"></script>
<script src="<s:url value="modules/admin/admin.controller.js" />"></script>
</body>
</html>
