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
<body style="font-family: 'Roboto';">
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
                        	
                        	<form action="UploadServlet" id="uploadForm" role="form" method="post" enctype="multipart/form-data" class="registration-form" style="font-weight: 300;">
                        		
                        		<fieldset id="fieldset">
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>Step 1 / 3</h3>
		                            		<p>Load dirty dataSet and some constraint rules</p>
		                        		</div>
		                        		<div class="form-top-right">
		                        			<i class="fa fa-cloud-upload"></i>
		                        		</div>
		                            </div>
		                            <div class="form-bottom">
				                    	<div class="form-group" id="loadFileForm">
				                    		<p>Dirty dataSet</p>
				                    		<input id="file-dataset" class="file" type="file" name="dataset">
				                    		<br>
				                    		<p>Rules</p>
				                    		<input id="file-rules" class="file" type="file" name="rules">
				                    		<br>
				                    		<button type="button" class="btn btn-primary next">Next</button>
				                    	</div>
				                    </div>
			                    </fieldset>
		                    </form>
		                    
                        </div>
                    </div>
                </div>
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
	   
        </script>

</div>
</body>
</html>