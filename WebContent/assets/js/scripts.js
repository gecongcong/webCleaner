
jQuery(document).ready(function() {
	
    /*
        Fullscreen background
    */
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
    
		$('.registration-form input[type="text"], .registration-form input[type="password"], .registration-form textarea').on('focus', function() {
			$(this).removeClass('input-error');
		});
	}
    
    
    // next step
    $('.registration-form .btn-primary.next').on('click', function() {
    	var parent_fieldset = $(this).parents('fieldset');
    	var next_step = true;
    	
    	parent_fieldset.find('input[id="file-dataset"]').each(function() {
    		if( $(this).val() == "" ) {
    			var parent = document.getElementById('loadFileForm');
    			var before = document.getElementById('file-dataset');
    			var div = document.createElement('div');
    			//设置div的属性
    			div.className = "alert alert-danger alert-dismissable";
    			div.innerHTML = '<button type="button" class="close" data-dismiss="alert" aria-hidden="true"> &times; </button>Error: No dataset input.';
//    			var bo = document.body;//获取body对象.
    			//动态插入到body中
    			parent.insertBefore(div,parent.childNodes[1]);
    			next_step = false;
    		}
    	});
    	
    	parent_fieldset.find('input[id="file-rules"]').each(function() {
    		if( $(this).val() == "" ) {
    			var parent = document.getElementById('loadFileForm');
    			var before = document.getElementById('file-rules');
    			var div = document.createElement('div');
    			//设置div的属性
    			div.className = "alert alert-danger alert-dismissable";
    			div.innerHTML = '<button type="button" class="close" data-dismiss="alert" aria-hidden="true"> &times; </button>Error: No rules input.';
//    			var bo = document.body;//获取body对象.
    			//动态插入到body中
    			parent.insertBefore(div,parent.childNodes[1]);
    			next_step = false;
    		}
    	});
    	if(next_step){
    		parent_fieldset.fadeOut(300, function() {
	    		$(this).next().fadeIn();
	    	});
    		$("#uploadForm").submit();
    	}
    });
    
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
    
    
});
