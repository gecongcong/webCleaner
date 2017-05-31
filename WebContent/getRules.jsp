<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Rules</title>
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
				<a class="navbar-brand" href="index.jsp">RDBSCleaner</a>
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
	<!-- 内容主题 -->
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
                        	
                        	<form action="#" id="cleanForm" role="form" method="post" class="registration-form" style="font-weight: 300;">
                        		
			                    <fieldset>
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>Step 2 / 3</h3>
		                            		<p>Start Cleaning...</p>
		                        		</div>
		                        		<div class="form-top-right">
		                        			<!-- <i class="fa fa-spinner"></i> -->
		                        			<div id="loading">
											<div id="loading-center">
											<div id="loading-center-absolute">
											<div class="object" id="object_four"></div>
											<div class="object" id="object_three"></div>
											<div class="object" id="object_two"></div>
											<div class="object" id="object_one"></div>
											</div>
											</div>
											</div>
		                        		</div>
		                            </div>
		                            <div class="form-bottom">
		                            	<div class="panel panel-default">
		                            		<div id="message" style="margin:30px;height:500px;overflow:auto"></div>
		                            		<button type="button" id="viewResult" style="display:none;margin-top:15px" onClick="window.location.href='clean2.jsp'" class="btn btn-success pull-right">View Result</button>
		                            	</div>
		                            	<br>
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
        <!--FileInput组件-->
        <script src="bootstrap-fileinput-master/js/fileinput.js"></script>
		<script src="bootstrap-fileinput-master/js/locales/zh.js"></script>
        <script src="bootstrap-3.3.7/js/tableExport.js"></script>
        <script src="bootstrap-3.3.7/js/jquery.base64.js"></script>
		<script src="bootstrap-3.3.7/js/bootstrap-table.js"></script>
		<script src="bootstrap-3.3.7/js/bootstrap-table-export.js"></script>

</div>
</body>
</html>