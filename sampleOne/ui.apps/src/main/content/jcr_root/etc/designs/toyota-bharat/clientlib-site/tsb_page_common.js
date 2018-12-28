
var $$ = jQuery.noConflict();
(function($){
	
	get_commons();
	
	function get_commons(){
		$.get('/tsb_xml/tsb_page_common.xml', function(xml){
			var json = $.xml2json(xml);
			make_logo(json.logo);
			make_navi(json.navi.item);
			make_navi2(json.navi2.item);
			make_social(json.social);
			make_sub_navi(json.sub_navi.item);
			make_footer(json.footer);
			
			set_search_label(json.search_label);
		});
		
		function make_logo (data) {
			var obj = $('.header_box');
			
			var html = '';
			html += '<a href="' +data.url+ '" class="logo">';
			html += '<img class="logo__img" src="' +data.image.pc+ '" alt="' +data.summary+ '">';
			html += '<img class="splogo__img" src="' +data.image.sp+ '" alt="' +data.summary+ '">';
			html += '</a> ';
			
			obj.prepend(html);
		}
		
		function make_navi(data) {
			var html = '';
			data = object_to_array(data);
			var n = data.length;
			for(var i=0; i<n; i++){
				html += make_navi_html(data[i]);
			}
			
			$('.header_box .nav-wrapper ul.nav').append(html);
			$('.nav__sub__modeles').parent().parent().parent().parent().attr("id","open_nav_sub_modeles");
			add_navi_event();
		}
		
		function make_navi2(data) {
			var html = '';
			data = object_to_array(data);
			var n = data.length;
			for(var i=0; i<n; i++){
				var row = data[i];
				html += '<li><a href="' +row.url+ '" onclick="ga(\'send\',\'event\',\'' +row.class_name+ '\', \'click\', \'header\');" class="' +row.class_name+ '">' +row.label + '</a></li>';
			}
			
			$('.header_box .nav-wrapper ul.nav2').append(html);
			
			add_navi2_event();
		}
		
		function make_social(data) {
			var html_list = '';
			data.item = object_to_array(data.item);
			var n = data.item.length;
			for(var i=0; i<n; i++){
				var row = data.item[i];
				html_list += '<li><a href="' +row.url+ '" class="social__link" target="_blank"><img src="' +row.image+ '" alt="' +row.label+ '" class="social__img" /></a></li>';
			}
			
			//navi
			var html_navi = '';
			html_navi += '<span class="subnav__copy">' +data.label+ '</span>';
			html_navi += '<ul class="cf">';
			html_navi += html_list;
			html_navi += '</ul>';
			$('.header_box .nav-wrapper .social').append(html_navi);
			
			//footer
			var html_footer = '';
			html_footer += '<p>' +data.label+'</p>';
			html_footer += '<ul class="footer_social_icon cf">';
			html_footer += html_list;
			html_footer += '</ul>';
			$('#follow_toyota').append(html_footer);
		}
		
		function make_sub_navi(data) {
			var html = '';
			
			html += '<li>';
			html += '<a href="' +data.url+ '" class="subnav__link subnav__link--region" target="_blank">';
			html += '<span class="icon icon--region subnav__icon"></span>';
			html += '<span class="subnav__copy">' +data.label+ '</span>';
			html += '</a>';
			html += '</li>';
			
			$('.header_box .nav-wrapper .subnav').append(html);
		}
		
		function make_navi_html(data) {
			var html = '';
			if (data.active  == 'showroom') {
				html += '<li id="open_toyota_models">';
			} else {
				html += '<li>';
			}
// -+-+			html += '<a href="#" class="nav__link js-nav-link" data-active="' +data.active+ '" aria-haspopup="true">' +data.label+ '</a>';
			if (data.label  == 'Contact Us') {
			html += '<a href="#"  class="nav__link" data-active="' +data.active+ '">Contact us</a>';
			}else{
			html += '<a href="#" class="nav__link js-nav-link" data-active="' +data.active+ '" aria-haspopup="true">' +data.label+ '</a>';
			}

			html += '<span class="icon icon--sub-close"></span>';
			
			if (data.active  == 'showroom') {
				html += '<div class="nav__sub open_toyota_models">';
				html += '<ul class="nav__sub__list">';
				html += '</ul>';
				html += '<ul class="nav__sub__list others">';
				html += '</ul>';
				html += '<div class="toyota_models" id="nav__modeles">';
				html += '<div id="scroll_box">';
				html += '<div class="row row-ie"></div>';
				html += '</div>';
				html += '<span class="sub-close"></span> </div>';
				html += '</div>';
			}
			
			else if (data.sub_navi) {
				html += '<div class="nav__sub">';
				html += '<ul class="nav__sub__list">';
				
				var sub_navi = object_to_array(data.sub_navi.item);
				var n = sub_navi.length;
				for(var i=0; i<n; i++){
					var row = sub_navi[i];
					html += '<li> <a href="' +row.url+ '"';
					if (typeof row.target != "undefined") {
						html += ' target="' +row.target+ '"';
					}
					html += ' class="nav__sub__link">' +row.label+ '</a>';
					if (row.sub_navi) html += make_sub_sub_navi(row.sub_navi);
					html += '</li>';
				}
				
				html += '</ul>';
				html += '</div>';
			}
			html += '</li>';
			
			return html;
		}
		function make_sub_sub_navi(data) {
			var html = '';
			
			html += '<span class="sub-close"></span>';
			html += '<div class="nav__sub__modeles">';
			html += '<ul>';
			data.item = object_to_array(data.item);
			var n = data.item.length;
			for(var i=0; i<n; i++){
				var row = data.item[i];
				html += '<li><a href="' +row.url+ '"';
				if (typeof row.target != "undefined") {
					html += ' target="' +row.target+ '"';
				}
				html += '>' +row.label+ '</a></li>';
			}
			html += '</ul>';
			html += '</div>';
			
			return html;
		}
		
		
		function make_footer (data) {
			//footer_nav
			var html_footer_nav = '';
			data.footer_nav.item = object_to_array(data.footer_nav.item);
			var n = data.footer_nav.item.length;
			for(var i=0; i<n; i++){
				var row = data.footer_nav.item[i];
				html_footer_nav += '<li> <a href="' +row.url+ '" class="footer__nav__link">' +row.label+ '</a> </li>';
			}
			$('.footer__nav').append(html_footer_nav);
			
			//footer copy
			$('.footer__copyright').append(data.footer_copyright);
		}
		
		function set_search_label (label) {
			$('.js-search .search__label').append(label);
		}
	}
	
	function add_navi_event () {
		
		$('.js-mobile-nav-but').nav({
			body: '.js-search-container',
			nav: '.js-nav-wrapper',
			className: 'is-nav-expanded',
			subNav: '.js-nav-link',
			subNavClass: 'is-nav-active',
			subNavParentClass: 'is-subnav-active',
			subNavParent: 'li',
			subNavGrandfather: '.nav',
			//search
			searchOverlay: '.js-search',
			searchInput: '.js-search-input',
			searchHandle: '.js-search-handle',
			searchHandleClass: 'is-search-active',
			searchOverlayClass: 'is-search-expanded',
			searchClose: '.js-search-close'
		});
		
		$('#open_nav_sub_modeles').on(
			eventname_go,function(e){
				if(isSmartDevice()){return;}
			}
		).on(
			eventname_in,function(e){
				if(isSmartDevice()){return;}
				$('.nav__sub__modeles').hide();
			}
		).on(
			eventname_out,function(e){
				if(isSmartDevice()){return;}
				$('.nav__sub__modeles').hide();
			}
		);
		$('#open_nav_sub_modeles .nav__sub__list .nav__sub__link').on(
			eventname_go,function(e){
				if(isSmartPhone()){
					if ($(this).parent().attr("class") == "is-sub-nav-active") {
						$(this).parent().removeClass('is-sub-nav-active');
					} else {
						$(this).parent().addClass('is-sub-nav-active');
					}
				}else if(isSmartDevice()){
					$('.nav__sub__modeles').hide();
					$(this).parent().children('.nav__sub__modeles').show();
				}
			}
		).on(
			eventname_in,function(e){
				if(isSmartDevice()){return;}
				$('.nav__sub__modeles').hide();
				$(this).parent().children('.nav__sub__modeles').show();
			}
		).on(
			eventname_out,function(e){
				if(isSmartDevice()){return;}
			}
		);
	}
	
	function add_navi2_event () {
		//testdrive -----------
		//open
/*		-+-+-+ Remove Event

		$('#nav2 .testdrive').click(function(e) {
			$('#nav2 li a').removeClass('on');
			if( !$(this).hasClass('on') ){
				$(this).addClass('on');
				overlay_window_open('testdrive');
			}
			return false;
		});
		
		// close
		$('#testdrive .close, #testdrive .overlay').click(function(e) {
			$('#nav2 .testdrive').removeClass('on');
			$('#nav2 li a').removeClass('on');
			$('#testdrive').hide();
		});
		
		//quote -----------
		//open
		$('#nav2 .quote').click(function(e) {
			$('#nav2 li a').removeClass('on');
			if( !$(this).hasClass('on') ){
				$(this).addClass('on');
				overlay_window_open('quote');
			}
			return false;
		});
		
		// close
		$('#quote .close, #quote .overlay').click(function(e) {
			$('#nav2 .quote').removeClass('on');
			$('#nav2 li a').removeClass('on');
			$('#quote').hide();
		});
		
		//bookaservice -----------
		//open
		$('#nav2 .bookaservice').click(function(e) {
			$('#nav2 li a').removeClass('on');
			if( !$(this).hasClass('on') ){
				$(this).addClass('on');
				overlay_window_open('bookaservice');
			}
			return false;
		});
		
		// close
		$('#bookaservice .close, #bookaservice .overlay').click(function(e) {
			$('#nav2 .bookaservice').removeClass('on');
			$('#nav2 li a').removeClass('on');
			$('#bookaservice').hide();
		});
		
		
		//brochure -----------
		//open
		$('#nav2 .brochure').click(function(e) {
			
			//reset
			$('#brochure #brochure_pickup').remove();
			$('#brochure #brochure_thum dl').show();
			
			$('#nav2 li a').removeClass('on');
			if( !$(this).hasClass('on') ){
				$(this).addClass('on');
				overlay_window_open('brochure');
			}
			return false;
		});
		
		// close
		$('#brochure .close, #brochure .overlay').click(function(e) {
			$('#nav2 .brochure').removeClass('on');
			$('#nav2 li a').removeClass('on');
			
			//reset
			$('#brochure #brochure_pickup').remove();
			$('#brochure #brochure_thum dl').show();
			$('#brochure').hide();
		});
		*/
	}
})($$);

