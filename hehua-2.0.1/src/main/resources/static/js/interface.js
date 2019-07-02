$(function(){
	var url = $('#headUrl').text();
	
	$('#ewm').bind('click', function(){
		var pzx = $('#pzx').val();
		window.location.href = url + '/zq_device/qrCode.do?number=' + pzx;
	});
	
	$.getJSON(url + '/interface/list/mz.do', null, function(json) {
		var con = $('#con');

		var jZtree = new Array();
		$.each(json, function(i, n) {
			var z = {};
			z.param = n.param;
			z.id = n.controllerName + n.methodName;
			z.cname = n.controllerName;
			z.mname = n.methodName;
			z.name = n.description;
			jZtree.push(z);
		});
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick /*树节点点击事件*/
			}
		};
		var zTree = $.fn.zTree.init($('#scTree'), setting, jZtree);
		var node = zTree.getNodeByParam('id', jZtree[0].id);//获取id为第一个节点的Id  
        zTree.selectNode(node);//选择点  
        zTree.setting.callback.onClick(null, zTree.setting.treeId, node);

		function onClick(event, treeId, treeNode, clickFlag) {
			$('#url').val('');
			$('#result').val('');
			getMethod(treeNode.id);
		}

		function getMethod(nz) {
			$.each(json, function(i, n) {
				var id = n.controllerName + n.methodName;
				if(id != nz)
					return;
				var btnUrl = url;
				con.empty();
				var title = '<div class="row hd"><p>' + n.description + '</p></div>'
				con.append(title);
				var content = $('<form class="form-horizontal"><div class="row search-tr"></div></form>');
				$.each(n.param, function(j, x) {
					if(x.name != undefined && x.name != "" && x.name != null) {
						if(x.parameter) {
							var g = $('<div class="form-group" style="margin-bottom: 0px;"><label class="col-sm-5 col-lg-5 col-md-5 control-label" for="way"><span style="color:red">*&nbsp;&nbsp;</span>' + x.nameCN + '(' + x.name + ')' + '：</label><div class="col-sm-7 col-lg-7 col-md-7"></div></div>');
							g.find('.col-sm-7').append('<input id="' + x.name + '" name="' + x.name + '" type="text" class="form-control" />');
						} else {
							var g = $('<div class="form-group" style="margin-bottom: 0px;"><label class="col-sm-5 col-lg-5 col-md-5 control-label" for="way"><span style="color:red">*&nbsp;&nbsp;</span>' + x.name + '：</label><div class="col-sm-7 col-lg-7 col-md-7"></div></div>');
							g.find('.col-sm-7').append('<select id="' + x.name + '" name="' + x.name + '" type="text" class="form-control col-sm-7 col-lg-7 col-md-7"></select>');
							for(var s = 0; s < x.dynamic.length; s++) {
								g.find('select').append('<option value="' + (x.dynamic)[s] + '">' + (x.dynamic)[s] + '</option>');
							}
						}
						$(g).appendTo(content);
						var desc = $('<div class="form-group"><div class="col-sm-5 col-lg-5 col-md-5"></div><lable style="color: red;" class="col-sm-7 col-lg-7 col-md-7">描述：'+ x.desc +'</lable></form>');
						$(desc).appendTo(content);
					}
				});
				content.append('<button id=' + i + ' type="button" class="btn btn-primary pull-right">测试</button>');
				content.find('button').bind('click', function() {
					$('#url').val('');
					$('#result').val('');
					var tj = content.find('input').serialize();
					tj = decodeURIComponent(tj, true);
					var tjs = content.find('select').val();
					var u = "";
					if(tjs != undefined && tjs != null && tjs != "") {
						tjs = tjs.replace("&", "/");
						var index = n.methodName.indexOf('{');
						var pathv = n.methodName.substring(index, n.methodName.length);
						u = url + n.controllerName + n.methodName.replace(pathv, tjs) + ".do?" + tj;
					} else {
						u = url + n.controllerName + n.methodName + ".do?" + tj;
					}
					$('#url').val(u);
					if(n.requestMethod != undefined && n.requestMethod != "" && n.requestMethod != null) {
						$.ajax({
							type: n.requestMethod,
							dataType: 'json',
							contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
							url: u,
							success: function(json) {
								$('#result').val(JSON.stringify(json, null, 2));
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								$('#result').val("服务调用失败！");
							}
						});
					} else {
						alert("没有method = RequestMethod.GET方法");
					}
				});
				con.append(content);
			});
		}
	});
});