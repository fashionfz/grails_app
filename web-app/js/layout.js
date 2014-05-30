function list(idstr) {
	var name1 = "subtree" + idstr;
	var name2 = "img" + idstr;
	var namebg = "bgimg" + idstr;
	var objectobj = document.all(name1);
	var imgobj = document.all(name2);
	var imgobj2 = document.all(namebg);

	if (objectobj.style.display == "none") {
		for (i = 1; i <= 6; i++) {
			var name3 = "img" + i;
			var name = "subtree" + i;
			var name4 = "bgimg" + i;
			var o = document.all(name);
			if (o != undefined) {
				o.style.display = "none";
				var image = document.all(name3);
				var imagebg = document.all(name4);
				//alert(image);
				image.src = "/monitor-webapp/static/images/hide.png";
				imagebg.src = "/monitor-webapp/static/images/left-show.png";
			}
		}
		objectobj.style.display = "block";
		imgobj.src = "/monitor-webapp/static/images/show.png";
		imgobj2.src = "/monitor-webapp/static/images/left-bg.png";
	} else {
		objectobj.style.display = "none";
		imgobj.src = "/monitor-webapp/static/images/hide.png";
		imgobj2.src = "/monitor-webapp/static/images/left-show.png";
	}
}
function allHeight() {
    var divl = document.getElementById("left-body");//获取左侧div的id
    var divr = document.getElementById("right-body");//获取右侧div的id

        if(divr.scrollHeight<=624)//如果右侧高度＜左侧最小高度

        {
        divl.style.backgroundColor='#555';
        divl.style.height=679+"px";//设置左侧高度为固定值

         }

      else{
    divl.style.backgroundColor='#555';//给左侧的div设置背景
    divl.style.height=divr.scrollHeight+"px";//给左侧的div的高度复值，使左侧div的高度等于右侧div的动态的高度；

            }
    //alert(divl.scrollHeight + "px");//做测试用的
    //alert(divr.scrollHeight + "px");//做测试用的
}
window.onload =allHeight;//不能写成allHeight();