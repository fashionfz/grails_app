if (typeof jQuery !== 'undefined') {
	(function($) {
		$("#side_switch").click(function(){
            $(".left").hide();
            $('#region_west').panel('resize',{
            	width:5
            });
            $('#region_center').panel('resize',{
            	left:5,
            	width: $('#region_center').width() + 200
            });
           // $(".right_body").css('margin-left',0);
            $(this).hide();
            $("#side_switchl").show();
        });

        $("#side_switchl").click(function(){
            $(".left").show();
            $(this).hide();
            $('#region_west').panel('resize',{
            	width:205
            });
            $('#region_center').panel('resize',{
            	left:205,
            	width: $('#region_center').width() - 200
            });
            //$(".right_body").css('margin-left',200);
            $("#side_switch").show();
        });

	})(jQuery);
}
