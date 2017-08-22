<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>中医医药信息平台</title>
</head>
<body>

</body>
<script>
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    window.location.href = '/' + webName + '/sys/login';
</script>
</html>