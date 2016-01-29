<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Zoo</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${path}/script/css/amazeui.css"/>
    <style>
        @media only screen and (min-width: 1200px) {
            .blog-g-fixed {
                max-width: 1200px;
            }
        }

        @media only screen and (min-width: 641px) {
            .blog-sidebar {
                font-size: 1.4rem;
            }
        }

        .blog-main {
            padding: 20px 0;
        }

        .blog-title {
            margin: 10px 0 20px 0;
        }

        .blog-meta {
            font-size: 14px;
            margin: 10px 0 20px 0;
            color: #222;
        }

        .blog-meta a {
            color: #27ae60;
        }

        .blog-pagination a {
            font-size: 1.4rem;
        }

        .blog-team li {
            padding: 4px;
        }

        .blog-team img {
            margin-bottom: 0;
        }

        .blog-content img,
        .blog-team img {
            max-width: 100%;
            height: auto;
        }

        .blog-footer {
            padding: 10px 0;
            text-align: center;
        }
    </style>
</head>
<body>
<header class="am-topbar">
    <h1 class="am-topbar-brand">
        <a href="#">Codding Zoo</a>
    </h1>

    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
            data-am-collapse="{target: '#doc-topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span
            class="am-icon-bars"></span></button>

    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav">
            <li class="am-active"><a href="#">首页</a></li>
            <li><a href="#">项目</a></li>
            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    菜单 <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <li class="am-dropdown-header">标题</li>
                    <li><a href="#">关于我们</a></li>
                    <li><a href="#">关于字体</a></li>
                    <li><a href="#">TIPS</a></li>
                </ul>
            </li>
        </ul>

        <form class="am-topbar-form am-topbar-left am-form-inline am-topbar-right" role="search">
            <div class="am-form-group">
                <input type="text" class="am-form-field am-input-sm" placeholder="搜索文章">
            </div>
            <button type="submit" class="am-btn am-btn-default am-btn-sm">搜索</button>
        </form>

    </div>
</header>

<div class="am-g am-g-fixed blog-g-fixed">
    <div class="am-u-md-8">
        <article class="blog-main">
            <h3 class="am-article-title blog-title">
                <a href="#">Oculus开发了一款VR绘画工具 但只限内部使用</a>
            </h3>


            <div class="am-g blog-content">
                <div class="am-u-lg-7">
                    <p>迪斯尼招牌动画师Glen Keane利用HTC Vive的控制器，通过被誉为杀手级应用的VR作画工具Tilt Brush，在空中流畅地“画出”美人鱼Ariel和其他迪斯尼动画人物形象。这件事情引起了人们的关注，至少这意味着，一名资深动画师正在适应新的作画媒介——VR，以及一种新的艺术诞生。事实上，在VR艺术方向上有不少人在探索，包括VR巨头Oculus。</p>
                </div>
                <div class="am-u-lg-5">
                    <p><img src="${path}/script/image/vr.png"></p>
                </div>
            </div>
            <div class="am-g">
                <div class="am-u-sm-12">
                    <p>根据外媒的报道，Oculus旗下的工作室Oculus Story Studio研发出一款与Tilt Brush相似的工具Quill。遗憾的是，这款绘画应用并不准备向大众开放，而是为内部团队服务。但这至少说明，Oculus并不永远专注于游戏。</p>

                    <p>具体来说，通过Oculus Rift的控制器Touch以及动作跟踪系统，用户能选择不同的画刷、颜色等在空中作画，与此同时作品能够实时通过3D画布渲染出来。对此，该工作室的视觉效果总监Inigo Quilez表示，这款应用的开发是“无心插柳”的结果，一开始只是为了制作最新的VR短片《Dear Angelica》事实上，在VR艺术创作这件事情上，Oculus很上心。在去年的 Oculus Connect 2大会上，Oculus发布了 Oculus Medium雕刻应用。这是一款多用户的VR应用，通过Oculus Touch ，人们可以“雕刻”不同的物体。对此，Oculus 的CEO Brendan Iribe 说：“每一个伟大的平台都有自己的画图程序，而Medium就是这样一款应用。”</p>
                </div>
            </div>
        </article>

        <hr class="am-article-divider blog-hr">

        <article class="blog-main">
            <h3 class="am-article-title">
                <a href="#">发力SUV阵营 大众T-Cross日内瓦车展亮相</a>
            </h3>

            </h4>

            <div class="am-g blog-content">
                <div class="am-u-lg-7">
                    <p>·T-Cross定位于小型SUV，基于Polo的尺寸打造而来，整车长度4米左右；·外观设计语言将借鉴此前亮相的T-ROC概念车的设计；
                    </p>

                    <p>在目前大众品牌的产品序列中，SUV是一个巨大的短板，为此，大众也制定了大量的SUV研发计划。随着这些计划的逐渐落实，大众也将迎来SUV产品的爆发之年。未来，Taigun、T-Cross、T-Roc将与Tiguan（国产车型为途观）和Touraeg（途锐）将共同构成大众SUV产品阵营，在命名上也保持了一致性。</p>
                </div>
                <div class="am-u-lg-5">
                    <p><img src="${path}/script/image/car.png"></p>
                </div>
            </div>
            <div class="am-g">
                <div class="am-u-sm-12">
                    <p>·此外，在北美以及中国市场还将推出基于CrossBlue概念车的量产版，一款融合跑车风格的跨界SUV。</p>

                    <p>大众推出的这款全新入门级跨界SUV，或将命名为T-Cross，采用POLO两厢车型的设计理念，T-Cross的定位略低于大众于2014年推出的基于高尔夫车型研发的T-Roc</p>

                    <p>T-Cross和T-Roc都将基于高度灵活的MQB模块化生产平台生产，采用多种汽油发动机、柴油发动机或插电式混合动力系统，并提供前驱或四驱车型。据悉，大众还将推出一款更小的基于up！车型打造的SUV，有可能是2012年圣保罗车展上推出的大众Taigun概念车的量产版。未来，Taigun、T-Cross、T-Roc将与Tiguan（国产车型为途观）和Touraeg（途锐）将共同构成大众SUV产品阵营，在命名上也保持了一致性。</p>
                </div>
                <div class="am-u-lg-5">
                    <p><img src="${path}/script/image/car2.png"></p>
                </div>
            </div>
        </article>

        <hr class="am-article-divider blog-hr">

    </div>

    <div class="am-u-md-4 blog-sidebar">
        <div class="am-panel-group">
            <section class="am-panel am-panel-default">
                <div class="am-panel-hd">关于我</div>
                <div class="am-panel-bd">
                    <p>你的，我的，我们的 GuineaFox</p>
                    <a class="am-btn am-btn-success am-btn-sm" href="#">查看更多 →</a>
                </div>
            </section>
            <section class="am-panel am-panel-default">
                <div class="am-panel-hd">文章目录</div>
                <ul class="am-list blog-list">
                    <li><a href="#">Google fonts 的字體（sans-serif 篇）</a></li>
                    <li><a href="#">[but]服貿最前線？－再訪桃園機場</a></li>
                    <li><a href="#">到日星鑄字行學字型</a></li>
                    <li><a href="#">glyph font vs. 漢字（上）</a></li>
                    <li><a href="#">我的小鱼你醒了，还认识早晨吗</a></li>
                    <li><a href="#">[極短篇] Android v.s iOS，誰的字體好讀？</a></li>
                </ul>
            </section>

            <section class="am-panel am-panel-default">
                <div class="am-panel-hd">团队成员</div>
                <div class="am-panel-bd">
                    <ul class="am-avg-sm-4 blog-team">
                        <li>博博<img class="am-thumbnail"
                                   src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg" alt=""/>
                        </li>
                        <li>岩岩<img class="am-thumbnail"
                                   src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg" alt=""/>
                        </li>
                        <li>鹏鹏<img class="am-thumbnail"
                                   src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg" alt=""/>
                        </li>
                        <li>元元<img class="am-thumbnail"
                                   src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg" alt=""/>
                        </li>
                        <li><img class="am-thumbnail"
                                 src="http://img4.duitang.com/uploads/blog/201406/15/20140615230159_kjTmC.thumb.224_0.jpeg" alt=""/>
                        </li>
                        <li><img class="am-thumbnail"
                                 src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg" alt=""/>
                        </li>
                        <li><img class="am-thumbnail"
                                 src="http://img4.duitang.com/uploads/blog/201406/15/20140615230220_F5LiM.thumb.224_0.jpeg" alt=""/>
                        </li>
                        <li><img class="am-thumbnail"
                                 src="http://img4.duitang.com/uploads/blog/201406/15/20140615230159_kjTmC.thumb.224_0.jpeg" alt=""/>
                        </li>
                    </ul>
                </div>
            </section>
        </div>
    </div>

</div>

<footer class="blog-footer">
    <p>codding template<br/>
        <small>© Copyright jasper.guineaFox.</small>
    </p>
</footer>

<script src="${path}/script/js/jquery-2.2.0.min.js"></script>

<script src="${path}/script/js/amazeui.min.js"></script>

</body>
</html>
