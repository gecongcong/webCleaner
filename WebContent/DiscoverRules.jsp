<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Index</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 引入 Bootstrap -->
	<link rel="stylesheet" href="bootstrap-3.3.7/css/bootstrap.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="bootstrap-3.3.7/css/bootstrap-table.min.css">
    <!-- <link rel="stylesheet" href="assets/css/form-elements.css"> -->
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="bootstrap-fileinput-master/css/fileinput.min.css">
    
</head>
<body style="font-family: 'Roboto';" onload="load()">
<%
	String rulesURL = (String)request.getAttribute("rulesURL");
	String datasetURL = (String)request.getAttribute("datasetURL");
%>
<input type="hidden" value="<%=request.getAttribute("rulesURL")%>" id="rule"/>

<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse">
				<span class="sr-only">切换导航</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">RDBSCleaner</a>
		</div>
		
		<div class="collapse navbar-collapse" id="navbar-collapse" style="font-size: 16px;font-weight: 300;">
	        <ul class="nav navbar-nav">
	            <li class="dropdown">
	                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
	                    	Input File Description<b class="caret"></b>
	                </a>
	                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
						<li role="presentation">
							<a role="menuitem" tabindex="-1" href="#">Rules</a>
						</li>
						<li role="presentation">
							<a role="menuitem" tabindex="-1" href="#">DataSet</a>
						</li>
						<li role="presentation" class="divider"></li>
						<li role="presentation">
							<a role="menuitem" tabindex="-1" href="#">Example</a>
						</li>
					</ul>
	            </li>
	            <li><a href="#">Help</a></li>
	            <li><a href="#">About</a></li>
	        </ul>
	    </div>
	</div>
</nav>

<!-- Top content -->
        <div class="top-content text-center">
        	
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1><strong>RDBSCleaner</strong> cleans structured database</h1>
                            <div class="description">
                            	<p>
	                            	Please follow the steps below to complete the data cleaning process.
                            	</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 form-box">
                        	
                        	<form action="UploadFileForDR" id="uploadForm" role="form" method="post" enctype="multipart/form-data" class="registration-form" style="font-weight: 300;">
                        		
                        		<fieldset id="fieldset">
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>Discover Rules</h3>
		                            		<p>Please load dirty dataSet</p>
		                        		</div>
		                        		<div class="form-top-right">
		                        			<i class="fa fa-search"></i>
		                        		</div>
		                            </div>
		                            <div class="form-bottom">
				                    	<div class="form-group" id="loadFileForm">
				                    		<p>DataSet</p>
				                    		<input id="file-dataset" class="file" type="file" name="dataset">
				                    		<br>
				                    		<button type="button" class="btn btn-primary next">Start</button>
				                    	</div>
				                    </div>
			                    </fieldset>
		                    </form>
                        </div>
                    </div>
                </div>
</div>
<!-- 按钮触发模态框 -->
<button style="display:none" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" id="rulesResult"></button>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					模态框（Modal）标题
				</h4>
			</div>
			<div class="modal-body">
				在这里添加一些文本
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

        <!-- Javascript -->
        <script src="jquery-3.2.1/jquery-3.2.1.js"></script>
		<script src="bootstrap-3.3.7/js/bootstrap.min.js"></script>
		<script src="bootstrap-3.3.7/js/bootstrap-table.js"></script>
        <script src="assets/js/jquery.backstretch.min.js"></script>
        <script src="assets/js/scripts.js"></script>
        <!--FileInput组件-->
        <script src="bootstrap-fileinput-master/js/fileinput.js"></script>
		<script src="bootstrap-fileinput-master/js/locales/zh.js"></script>
        <script src="bootstrap-3.3.7/js/tableExport.js"></script>
        <script src="bootstrap-3.3.7/js/jquery.base64.js"></script>
		<script src="bootstrap-3.3.7/js/bootstrap-table.js"></script>
		<script src="bootstrap-3.3.7/js/bootstrap-table-export.js"></script>
        <script>
        $("#file-dataset").fileinput({
	        'allowedFileExtensions' : ['db','data', 'csv','txt'],
	        uploadUrl: uploadUrl, //上传的地址
	        maxFilesNum: 1,
	    });
        $("#file-rules").fileinput({
	        'allowedFileExtensions' : ['db','data', 'csv','txt'],
	        maxFilesNum: 1,
	    });
	   
        function load(){
        	  //下面两种方法效果是一样的
        	  var a = document.getElementById('rule').value;
        	  if(a!="null"){
        		  $('#myModal').modal();
        	  }
        	}
        </script>
</div>
</body>
</html>