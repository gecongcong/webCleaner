
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
    
    /* Form */
	$('.registration-form fieldset:first-child').fadeIn('slow');
	
    // previous step
    $('.registration-form .btn-primary.previous').on('click', function() {
//    	$(this).parents('fieldset').fadeOut(300, function() {
//    		$(this).prev().prev().fadeIn();
//    	});
    });
    
    // submit
    $('.registration-form .btn-success.submit').on('click', function(e) {
    	
    });
    
    
});
