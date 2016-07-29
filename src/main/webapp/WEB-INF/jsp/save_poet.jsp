<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>诗句编辑</title>
    <script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
</head>
<body>
  <form method="post" action="/poet/save" style="text-align:center;">
    <table>
      <tr>
        <td>标题</td>
        <td><input type="text" name="title" /> </td>
      </tr>
      <tr>
        <td>内容</td>
        <td><textarea name="content" rows="30" cols="70"></textarea> </td>
      </tr>
      <tr>
        <td>作者</td>
        <td><input type="text" name="author" /> </td>
      </tr>
      <tr>
        <td>创作时间 </td>
        <td><input type="text" name="writeTime" /> </td>
      </tr>
      <tr>
        <td><input type="submit"> </td>
        <td><input type="reset"> </td>
      </tr>
    </table>
  </form>
</body>
</html>
