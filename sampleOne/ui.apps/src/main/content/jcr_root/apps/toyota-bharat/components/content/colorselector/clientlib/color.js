(function($) {
	 var mb = $('#path').text();
	 $( "#palette > li" ).each(function(i) {
		 if(i==0)
			 {
			 var imgPath =$( "#palette > li:first-child").find('img').attr('src');
			 var tempPath = imgPath.split('/');
				var file = tempPath[tempPath.length-1];
				var data = file.split('.')[0];
				$("#car_colors").find('img').attr('src',mb+'/'+data+'.jpg');
			 }
     $(this).on('click' , function()	{
			var src = $(this).find('img').attr('src');
			var tarr = src.split('/');
			var file = tarr[tarr.length-1];
			var data = file.split('.')[0];
			$("#car_colors").find('img').attr('src',mb+'/'+data+'.jpg');
        });
    });
})(jQuery);
