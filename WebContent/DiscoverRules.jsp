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
<input type="hidden" value="<%=session.getAttribute("rulesURL")%>" id="rule"/>

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
				                    	<div class="form-group" id="loadFileForm" style="background:#fff;padding:25px;border-radius: 6px;">
				                    		<p>DataSet</p>
				                    		<input id="file-dataset" class="file" type="file" name="dataset">
				                    		<br>
				                    		<button type="button" class="btn btn-primary next">Start</button>
				                    		<button type="button" class="btn btn-default next" onClick="window.location.href='loadFile.jsp'">Back</button>
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
<div class="modal fade" id="myModal" style="margin-top:200px" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					Result
				</h4>
			</div>
			<div class="modal-body">
				The data quality rules is generated.<br>
				You could download this file to see more details.
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close
				</button>
				<button type="button" class="btn btn-success" onClick="window.location.href='DownloadRules'">
					Download
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
        	uploadUrl: '#', // you must set a valid URL here else you will get an error
        	'allowedFileExtensions' : ['db','data', 'csv','txt'],
	        overwriteInitial: false,
	        maxFileSize: 4096,
	        maxFilesNum: 1,
	        slugCallback: function(filename) {
	            return filename.replace('(', '_').replace(']', '_');
	        }
	    });
	   
        $("#file-3").fileinput({
			showUpload: false,
			showCaption: false,
			browseClass: "btn btn-primary btn-lg",
			fileType: "any",
	        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
		}); 
        
        $("#file-1").fileinput({
	        uploadUrl: '#', // you must set a valid URL here else you will get an error
	        allowedFileExtensions : ['jpg', 'png','gif'],
	        overwriteInitial: false,
	        maxFileSize: 1000,
	        maxFilesNum: 10,
	        //allowedFileTypes: ['image', 'video', 'flash'],
	        slugCallback: function(filename) {
	            return filename.replace('(', '_').replace(']', '_');
	        }
		});
	            
		$("#file-3").fileinput({
			showUpload: false,
			showCaption: false,
			browseClass: "btn btn-primary btn-lg",
			fileType: "any",
	        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
		});
		$("#file-4").fileinput({
			uploadExtraData: {kvId: '10'}
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