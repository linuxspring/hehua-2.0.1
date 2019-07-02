<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>接口文档</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" />
		<link rel="stylesheet" href="css/component.css" />
		<link rel="stylesheet" href="css/normalize.css" />
		<link rel="stylesheet" href="css/zTreeStyle.css" />
		<script type="text/javascript" src="js/jquery-3.2.0.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/jquery.ztree.core-3.5.min.js"></script>
		<script type="text/javascript" src="js/jquery.ztree.excheck-3.5.min.js"></script>
		<script type="text/javascript" src="js/interface.js" ></script>
	</head>
	<style>
		.container {
			width: 99%;
			padding-right: 5px;
			padding-left: 5px;
		}
	</style>

	<body>
		<div id="headUrl" style="display: none;">http://localhost:8080/wx</div>
		<div class="container">
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-2" style="margin-top: 50px;height:100%;">
						<div style="overflow-y:scroll; overflow-x:scroll;">
							<div class="zTreeDemoBackground left">
								<ul id="scTree" class="ztree"></ul>
							</div>
						</div>
						<div>
							<input id="pzx" class="form-control"/>
							<button id="ewm" class="btn btn-info">生成配置项二维码</button>
						</div>
					</div>
					<div class="col-md-6">
						<div id="con"></div>
					</div>
					<div class="col-md-4">
						<h1>URL：</h1>
						<textarea id="url" class="form-control" rows="3"></textarea>
						<h1>结果：</h1>
						<textarea id="result" class="form-control" rows="20" cols="10"></textarea>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>