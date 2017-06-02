	
	var websocket = new WebSocket("ws://localhost/Cleaner-web/webSocket");
	

jQuery(document).ready(function() {
    /*
        Fullscreen background
    */
	setTimeout( function(){},1000);
	
    $.backstretch("images/background.jpg");
    
    $('#top-navbar-1').on('shown.bs.collapse', function(){
    	$.backstretch("resize");
    });
    $('#top-navbar-1').on('hidden.bs.collapse', function(){
    	$.backstretch("resize");
    });
    
    var form = $('.registration-form fieldset:first-child');
    
    var fieldset = $('.registration-form .btn-primary.next').parents('fieldset');
    var cleanResult = $("#cleanResult").val();
    
	if(cleanResult=="true") {
		fieldset.fadeOut(300, function() {
			form.next().next().fadeIn();
    	});
	}else{
		/* Form */
		$('.registration-form fieldset:first-child').fadeIn('slow');
	}
    
    // previous step
    $('.registration-form .btn-primary.previous').on('click', function() {
    	$(this).parents('fieldset').fadeOut(300, function() {
    		$(this).prev().prev().fadeIn();
    	});
    });
    
    // submit
    $('.registration-form .btn-success.submit').on('click', function(e) {
    	
    	$(this).find('input[type="text"], input[type="password"], textarea').each(function() {
    		if( $(this).val() == "" ) {
    			e.preventDefault();
    			$(this).addClass('input-error');
    		}
    		else {
    			$(this).removeClass('input-error');
    		}
    	});
    	
    });
	
    //判断当前浏览器是否支持WebSocket
//    if ('WebSocket' in window) {
//        webSocket = new WebSocket("ws://localhost/Cleaner-web/webSocket");
//    }
//    else {
//        alert('当前浏览器 Not support websocket')
//    }
	
  //连接发生错误的回调方法
	websocket.onerror = function () {
		setMessageInnerHTML("WebSocket连接发生错误");
	};
	
	//连接成功建立的回调方法
	websocket.onopen = function () {
		setMessageInnerHTML("This is RDBSCleaner V1.0");
	};
	
	//接收到消息的回调方法
	websocket.onmessage = function (event) {
		setMessageInnerHTML(event.data);
		if(event.data=="Finish."){
			closeWebSocket();
		}
	};
	
	//连接关闭的回调方法
	websocket.onclose = function () {
		
	};
	
	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function () {
		closeWebSocket();
	};
	
	//将消息显示在网页上
	function setMessageInnerHTML(innerHTML) {
		document.getElementById('message').innerHTML += innerHTML + '<br/>';
	};
	
	//关闭WebSocket连接
	function closeWebSocket() {
		websocket.close();
		$('#viewResult').show();
	};
    
	//发送消息
	function send() {
		var message = document.getElementById('rulesURL').value+","+document.getElementById('datasetURL').value;
		if(websocket!=null){
			websocket.send(message);
		}else{
			setTimeout( function(){},1000);
			websocket.send(message);
		}
		
	};
	
	send();
});

