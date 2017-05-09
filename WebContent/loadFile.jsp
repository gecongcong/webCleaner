<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import=
	"java.util.HashMap,
	java.util.Iterator,
	java.util.List,
	java.util.Map,
	java.util.Map.Entry" %>
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
<%
	boolean cleanResult = false;
	if(request.getAttribute( "cleanResult")!=null){
		cleanResult = (boolean)request.getAttribute( "cleanResult");
	}
%>
<input type="text" id="cleanResult" style="display:none" value="<%=cleanResult%>"/>
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
                        	
                        	<form action="DataCleanServlet" id="uploadForm" role="form" method="post" enctype="multipart/form-data" class="registration-form" style="font-weight: 300;">
                        		
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
			                    
			                    <fieldset>
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>Step 2 / 3</h3>
		                            		<p>Start Cleaning...</p>
		                        		</div>
		                        		<div class="form-top-right">
		                        			<i class="fa fa-key"></i>
		                        		</div>
		                            </div>
		                            <div class="form-bottom">
		                            	<div id="message"></div>
				                    </div>
			                    </fieldset>
			                    
			                    <fieldset>
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>Step 3 / 3</h3>
		                            		<p>Cleaning finished!</p>
		                        		</div>
		                        		<div class="form-top-right">
		                        			<!-- <i class="fa fa-key"></i> -->
		                        		</div>
		                        		<div>
			                        		<table id="table"
										           data-toggle="table"
										           data-show-columns="true"
										           data-search="true"
										           data-show-refresh="true"
										           data-show-toggle="true"
										           data-pagination="true"
										           data-height="500"
										           style="background:#fff;">
												<thead>
													<tr style="background:#fff;">
													<%	String[] header = (String[])request.getAttribute("header");
														if(null!=header)
														for(int i=0;i<header.length;i++){%>
															<th><%=header[i]%></th>
														<%}%>
												    </tr>
												</thead>
											  	<tbody>
											  	<%
											  	HashMap<Integer,String[]> dataSet = (HashMap<Integer,String[]>)request.getAttribute("dataSet");
												if(null!=dataSet){
													Iterator<Entry<Integer,String[]>> iter = dataSet.entrySet().iterator();%>
													<%while(iter.hasNext()){
														Entry<Integer,String[]> entry = iter.next();
														String[] value = entry.getValue();%>
														<tr>
														<%for(int i=0;i<value.length;i++){%>
															<td><%=value[i]%></td>
														<%}%>
														</tr>
													<%}
												}
											  	%>
											    </tbody>
											</table>
		                        		</div>
		                            </div>
		                            
		                            <div class="form-bottom">
				                        <button type="button" class="btn btn-primary previous">Restart</button>
				                        <button type="button" class="btn btn-success submit">Download</button>
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
	    /* $("#file-rules").fileinput({
	        uploadUrl: '#', // you must set a valid URL here else you will get an error
	        allowedFileExtensions : ['jpeg', 'jpg', 'png','gif'],
	        overwriteInitial: false,
	        maxFileSize: 1000,
	        maxFilesNum: 10,
	        //allowedFileTypes: ['image', 'video', 'flash'],
	        slugCallback: function(filename) {
	            return filename.replace('(', '_').replace(']', '_');
	        }
		}); */
		
	    $(document).ready(function() {
	        $("#test-upload").fileinput({
	            'showPreview' : false,
	            'allowedFileExtensions' : ['jpg', 'png','gif'],
	            'elErrorContainer': '#errorBlock'
	        });
	        /*
	        $("#test-upload").on('fileloaded', function(event, file, previewId, index) {
	            alert('i = ' + index + ', id = ' + previewId + ', file = ' + file.name);
	        });
	        */
	    });
        </script>

		<!-- websocket发送消息 -->
		<script type="text/javascript">
	    var webSocket = new WebSocket('ws://localhost/Cleaner-web/DataCleanServlet');
	    webSocket.onerror = function(event) {
	      onError(event)
	    };
	 
	    webSocket.onopen = function(event) {
	      onOpen(event)
	    };
	 
	    webSocket.onmessage = function(event) {
	      onMessage(event)
	    };
	 
	    function onMessage(event) {
	      document.getElementById('messages').innerHTML += '<br />' + event.data;
	    }
	 
	    function onOpen(event) {
	      document.getElementById('messages').innerHTML = 'Connection established';
	    }
	 
	    function onError(event) {
	      alert(event.data);
	    }
	 
	    function start() {
	      webSocket.send('hello');
	      return false;
	    }
	  	</script>


<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        webSocket = new WebSocket("ws://localhost/Cleaner-web/DataCleanServlet");
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
</script>

</div>
</body>
</html>