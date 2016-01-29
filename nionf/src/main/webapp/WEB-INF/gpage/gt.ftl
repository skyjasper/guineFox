<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${path}/script/js/jquery-2.2.0.min.js"></script>
</head>
<body>
<div>
    直接显示字符串<br />
    String:${strValue}<br/>
    <hr/>
</div>

<div>
    显示日期
    date:${myDate?date}<br/>
    <hr/>
    显示时间
    time:${myDate?time}<br/>
    <hr/>
    显示时间戳
    datetime:${myDate?datetime}<br/>
    <hr/>
</div>

<div>
    访问对象数组
    ObjectArray:
${objectArray[0]},${objectArray[1]},${objectArray[2]}
    <br />
    <hr/>
</div>

<div>
    遍历Set中的元素
    SetData:<br />
<#list setData as value>
${value}
</#list>
    <hr/>
</div>

<div>
    遍历List中的元素
    strList:<br/>
<#list strList as strValue >
    <#if strValue_index == 0>
    ${strValue} is first element
    <#elseif !strValue_has_next>
    ${strValue} is last element
    <#else>
    ${strValue}
    </#if>
    <br />
</#list>
    <hr/>
</div>

<div>
    获取map中的值
    <br/>
    map:
    key1=${map.key1},key2=${map.key2},key3=${map.key3}
    <hr/>
</div>
<#include "font.html">

<script type="text/javascript">
    $(function(){
        console.info("=====");
    });
</script>
</body>
</html>