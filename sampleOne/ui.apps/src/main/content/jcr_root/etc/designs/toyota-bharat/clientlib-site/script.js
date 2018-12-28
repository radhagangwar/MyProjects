$(window).load(function(){
    var $container = $('.row2');
    var $container1 = $('.row');

    $container.isotope({
        filter: '*',
        animationOptions: {
            duration: 750,
            easing: 'linear',
            queue: false
        }
    });


    $container1.isotope({
        filter: '*',
        animationOptions: {
            duration: 750,
            easing: 'linear',
            queue: false
        }
    });

    var main_visual_height = $('#main_visual').height()+'px';
    $('#page_navi_top').css('position','absolute').css('width','100%').css('top',main_visual_height).css('z-index','10').css('left','0px');


    var elementPosition = $('#page_navi_top').offset();
    $(window).scroll(function(){
        if($(window).scrollTop() > elementPosition.top){
            $('#page_navi_top').css('position','fixed').css('width','100%').css('top','0').css('z-index','10').css('left','178px');
        } else {
            $('#page_navi_top').css('position','absolute').css('width','100%').css('top', main_visual_height).css('z-index','10').css('left','0px');
        }    
    });

    $('.selector a').click(function(){
        $('.selector .current').removeClass('active current');
        $(this).addClass('active current');
 
        var selector = $(this).attr('data-filter');
        $container.isotope({
            filter: selector,
            animationOptions: {
                duration: 750,
                easing: 'linear',
                queue: false
            }
         });
         return false;
    }); 


    $('.open_toyota_models li').hover(function(){
        $('.open_toyota_models .current').removeClass('active current');
        $(this).addClass('active current');
 
        var selector = $(this).attr('data-filter');
        $container1.isotope({
            filter: selector,
            animationOptions: {
                duration: 750,
                easing: 'linear',
                queue: false
            }
         });
         return false;
    }); 
});


$('.bxCar-slider').bxSlider({
  pagerCustom: '#bxCar-pager',
  speed: 500
});

$("#bxCar-pager > a").each(function( i ) {
	if(i == 0){
		var imgPath = $("#bxCar-pager > a:first-child").find('img').attr('src');
    	$("#btn_download_media > a").attr('href',imgPath);
	}
    $(this).on('click',function(){
    	var imgPath = $(this).find('img').attr('src');
    	$("#btn_download_media > a").attr('href',imgPath);
    });
  });


$('#nav').onePageNav({
	currentClass: 'current',
	changeHash: false,
	scrollSpeed: 750,
	scrollThreshold: 0.5,
	filter: '',
	easing: 'swing',
	begin: function() {
		//I get fired when the animation is starting
	},
	end: function() {
		//I get fired when the animation is ending
	},
	scrollChange: function($currentListItem) {
		//I get fired when you enter a section and I pass the list item of the section
	}
});