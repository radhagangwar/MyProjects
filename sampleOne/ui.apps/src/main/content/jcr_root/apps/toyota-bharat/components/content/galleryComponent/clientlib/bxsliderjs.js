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
