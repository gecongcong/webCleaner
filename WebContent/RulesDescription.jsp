<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Rules Description</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 引入 Bootstrap -->
	<link href="bootstrap-3.3.7/css/bootstrap.css" rel="stylesheet">
	<script src="jquery-3.2.1/jquery-3.2.1.js"></script>
	<script src="bootstrap-3.3.7/js/bootstrap.min.js"></script>
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
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="jumbotron">
					<h2>
						Rules Description
					</h2>
					<p style="font-size:18px;color:#3d3f42cc;line-height:25px;">
						Before Cleaning, Users should provide data quality rules, which incorporate their knowledge of data set.<br>
						The format is as follow: <br>
					</p>
					<p style="font-size:20px;line-height:32px;">
						ZIPCode(valueZIPCode) => State(valueState)<br>
						PhoneNumber(valuePhoneNumber) => ZIPCode(valueZIPCode)<br>
						City(valueCity) , PhoneNumber(valuePhoneNumber) => ProviderID(valueProviderID)<br>
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>