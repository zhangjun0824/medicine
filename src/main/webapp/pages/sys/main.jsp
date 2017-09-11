<%@ page contentType="text/html; charset=utf-8" language="java"  %>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN" data-ng-app="app">
<head>
  <meta charset="utf-8" />
  <title>中医医药信息平台</title>
  <meta name="description" content="app, web app, responsive, responsive layout, admin, admin panel, admin dashboard, flat, flat ui, ui kit, AngularJS, ui route, charts, widgets, components" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="${ctx}/sys/css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="${ctx}/sys/css/animate.css" type="text/css" />
  <link rel="stylesheet" href="${ctx}/sys/css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="${ctx}/sys/css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="${ctx}/sys/css/font.css" type="text/css" />
  <link rel="stylesheet" href="${ctx}/sys/css/app.css" type="text/css" />
  <link rel="stylesheet" href="${ctx}/sys/vendor/modules/angularjs-toaster/toaster.css" type="text/css" />
  <link rel="stylesheet" href="${ctx}/sys/vendor/modules/ngDialog-master/css/ngDialog.min.css" type="text/css" />
  <link rel="stylesheet" href="${ctx}/sys/vendor/modules/ngDialog-master/css/ngDialog-theme-default.min.css" type="text/css" />
</head>
<body ng-controller="AppCtrl">
  <div class="app" id="app" ng-class="{'app-header-fixed':app.settings.headerFixed, 'app-aside-fixed':app.settings.asideFixed, 'app-aside-folded':app.settings.asideFolded, 'app-aside-dock':app.settings.asideDock, 'container':app.settings.container}" ui-view></div>


  <!-- jQuery -->
  <script src="${ctx}/sys/vendor/jquery/jquery.min.js"></script>

  <!-- Angular -->
  <script src="${ctx}/sys/vendor/angular/angular.js"></script>
  
  <script src="${ctx}/sys/vendor/angular/angular-animate/angular-animate.js"></script>
  <script src="${ctx}/sys/vendor/angular/angular-cookies/angular-cookies.js"></script>
  <script src="${ctx}/sys/vendor/angular/angular-resource/angular-resource.js"></script>
  <script src="${ctx}/sys/vendor/angular/angular-sanitize/angular-sanitize.js"></script>
  <script src="${ctx}/sys/vendor/angular/angular-touch/angular-touch.js"></script>
  <!-- Vendor -->
  <script src="${ctx}/sys/vendor/angular/angular-ui-router/angular-ui-router.js"></script> 
  <script src="${ctx}/sys/vendor/angular/ngstorage/ngStorage.js"></script>
  <script src="${ctx}/sys/vendor/modules/angularjs-toaster/toaster.js"></script>
  <script src="${ctx}/sys/vendor/modules/ngDialog-master/js/ngDialog.min.js"></script>

  <!-- bootstrap -->
  <script src="${ctx}/sys/vendor/angular/angular-bootstrap/ui-bootstrap-tpls.js"></script>
  <!-- lazyload -->
  <script src="${ctx}/sys/vendor/angular/oclazyload/ocLazyLoad.js"></script>
  <!-- translate -->
  <script src="${ctx}/sys/vendor/angular/angular-translate/angular-translate.js"></script>
  <script src="${ctx}/sys/vendor/angular/angular-translate/loader-static-files.js"></script>
  <script src="${ctx}/sys/vendor/angular/angular-translate/storage-cookie.js"></script>
  <script src="${ctx}/sys/vendor/angular/angular-translate/storage-local.js"></script>

  <!-- App -->
  <script src="${ctx}/sys/js/app.js"></script>
  <script src="${ctx}/sys/js/config.js"></script>
  <script src="${ctx}/sys/js/config.lazyload.js"></script>
  <script src="${ctx}/sys/js/config.router.js"></script>
  <script src="${ctx}/sys/js/main.js"></script>
  <script src="${ctx}/sys/js/services/ui-load.js"></script>
  <script src="${ctx}/sys/js/filters/fromNow.js"></script>
  <script src="${ctx}/sys/js/directives/setnganimate.js"></script>
  <script src="${ctx}/sys/js/directives/ui-butterbar.js"></script>
  <script src="${ctx}/sys/js/directives/ui-focus.js"></script>
  <script src="${ctx}/sys/js/directives/ui-fullscreen.js"></script>
  <script src="${ctx}/sys/js/directives/ui-jq.js"></script>
  <script src="${ctx}/sys/js/directives/ui-module.js"></script>
  <script src="${ctx}/sys/js/directives/ui-nav.js"></script>
  <script src="${ctx}/sys/js/directives/ui-scroll.js"></script>
  <script src="${ctx}/sys/js/directives/ui-shift.js"></script>
  <script src="${ctx}/sys/js/directives/ui-toggleclass.js"></script>
  <script src="${ctx}/sys/js/directives/ui-validate.js"></script>
  <script src="${ctx}/sys/js/controllers/bootstrap.js"></script>


  <script src="${ctx}/sys/js/app/pageinfo/tm.pagination.js"></script>
</body>
</html>