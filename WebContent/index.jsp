<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Index</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
							<a role="menuitem" tabindex="-1" href="RulesDescription.jsp">Rules</a>
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
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="jumbotron">
		    	<h1 class="text-center"><img src="images/icon-120.png" alt="RDBSCleaner Icon"> 
		          RDBSCleaner</h1>
		    	<p class="lead text-center">
		          RDBSCleaner cleans your structured database requiring soft domain knowledge, <br>a prior clean database, or hiring a dedicated data quality team.
		    	</p>
		    	<p class="text-center">
		    		<a class="btn btn-lg btn-primary" href="loadFile.jsp" role="button">Start Cleaning</a>
		    	</p>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-4 column">
			<h2>
				Sample Data
			</h2>
			<p>
				You are welcome to try out the software with your own datasets. Just use a comma-separated file. You can also <a href="DownloadSamples">get some sample data</a> to operate on.
			</p>
		</div>
		<div class="col-md-4 column">
			<h2>
				Advanced Algorithms
			</h2>
			<p>
				RDBSCleaner is based on Markov Logic Network(MLN), a first-order knowledge base with a weight attached to each formula (or clause), and an advanced cleaning architecture that accurately predicts real-life errors.
			</p>
		</div>
		<div class="col-md-4 column">
			<h2>
				Completely Automated
			</h2>
			<p>
				RDBSCleaner does not need any time consuming manual cleaning operation. Just provide the database, a soft data quality rules and you're done!
			</p>
		</div>
	</div>
</div>
</body>
</html>