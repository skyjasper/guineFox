$(function(){
    /**start**/
        //监听右键事件，创建右键菜单
    $('#tt').tabs({
        onContextMenu:function(e, title,index){
            e.preventDefault();
            if(index>0){
                $('#jmm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                })
            }
        }
    });

    //刷新
    $("#m-refresh").click(function(){
        //获取选中的标签项
        var currTab = $('#tt').tabs('getSelected');
        var options =  currTab.panel('options');
        var currTitle = currTab.panel('options').title;
        var navTabce = options.content;
        if(navTabce){
            //外部页面
            var url = $(navTabce).attr('src');
            /**重新设置该标签**/
            $('#tt').tabs('update',{
                tab:currTab,
                options:{
                    href:url
                }
            })
        }else{//系统页面
        	//json 打印
//         var obj_json = $.toJSON(options);
//         console.info("obj_json-"+obj_json);
            var uhref = options.href;            
            currTab.panel('refresh', uhref);
        }
    });

    //关闭所有
    $("#m-closeall").click(function(){
        $(".tabs li").each(function(i, n){
            if(i>0){
                var title = $(n).text();
                $('#tt').tabs('close',title);
            }
        });
    });

    //除当前之外关闭所有
    $("#m-closeother").click(function(){
        var currTab = $('#tt').tabs('getSelected');
        var currTitle = currTab.panel('options').title;
        $(".tabs li").each(function(i, n){
            if(i>0){
                var title = $(n).text();
                if(currTitle != title){
                    $('#tt').tabs('close',title);
                }
            }
        });
    });

    //关闭当前
    $("#m-close").click(function(){
        closeCurr();
    });

    /**
     在中间添加tab,添加tab
     **/
    $('#tt').tabs({
        onLoad:function(panel){
            var plugin = panel.panel('options').title;
            panel.find('textarea[name="code-'+plugin+'"]').each(function(){
                var data = $(this).val();
                data = data.replace(/(\r\n|\r|\n)/g, '\n');
                if (data.indexOf('\t') == 0){
                    data = data.replace(/^\t/, '');
                    data = data.replace(/\n\t/g, '\n');
                }
                data = data.replace(/\t/g, '    ');
                var pre = $('<pre name="code" class="prettyprint linenums"></pre>').insertAfter(this);
                pre.text(data);
                $(this).remove();
            });
        }
    });
    /**end**/
});

/**
 打开标签页no public web *.html
 **/
function openNav(plugin){
    if ($('#tt').tabs('exists',plugin)){
        $('#tt').tabs('select', plugin);
    } else {
        $('#tt').tabs('add',{
            title:plugin,
            href:plugin,
            closable:true
        });
    }
}

function openNav(plugin,ntitle){
    if(null!=ntitle&&ntitle!=""){
        if ($('#tt').tabs('exists',ntitle)){
            $('#tt').tabs('select', ntitle);
        }else{
            $('#tt').tabs('add',{
                title:ntitle,
                href:plugin,
                closable:true
            });
        }
    }else{
        if($('#tt').tabs('exists',plugin)){
            $('#tt').tabs('select', plugin);
        }else{
            $('#tt').tabs('add',{
                title:plugin,
                href:plugin,
                closable:true
            });
        }
    }

}

/**
 打开标签页all
 **/
function OpenTab(title, url, icon){
    /**
     如果这个标题的标签存在，则选择该标签
     否则添加一个标签到标签组
     */
    if($("#tt").tabs('exists', title)){
        $("#tt").tabs('select', title);
    }else{
        $("#tt").tabs('add',{
            title: title,
            content: createTabContent(url),
            closable: true,
            icon: icon
        });
    }
}

/** 生成标签内容 **/
function createTabContent(url){
    return '<iframe style="width:100%;height:98%;" scrolling="auto" frameborder="0" src="' + url + '"></iframe>';
}

function closeCurr(){
    var currTab = $('#tt').tabs('getSelected');
    var currTitle = currTab.panel('options').title;
    $('#tt').tabs('close', currTitle);
}