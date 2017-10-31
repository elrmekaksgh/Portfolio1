<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="./css/inc.css" rel="stylesheet" type="text/css">
<link href="./css/subpage.css" rel="stylesheet" type="text/css">
<script src="./js/jssor.slider-23.1.6.min.js" type="text/javascript"></script>
<script type="text/javascript">
jssor_1_slider_init = function() {
	var jssor_1_SlideshowTransitions = [
		{$Duration:1200,$Opacity:2}
	];
	var jssor_1_options = {
		$AutoPlay: 1,
		$SlideshowOptions: {
			$Class: $JssorSlideshowRunner$,
			$Transitions: jssor_1_SlideshowTransitions,
			$TransitionsOrder: 1
		},
		$ArrowNavigatorOptions: {
			$Class: $JssorArrowNavigator$
		},
		$BulletNavigatorOptions: {
			$Class: $JssorBulletNavigator$
		}
	};
	var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);
	/*responsive code begin*/
	/*remove responsive code if you don't want the slider scales while window resizing*/
	function ScaleSlider() {
		var refSize = jssor_1_slider.$Elmt.parentNode.clientWidth;
		if (refSize) {
			refSize = Math.min(refSize, 600);
			jssor_1_slider.$ScaleWidth(refSize);
		}
		else {
			window.setTimeout(ScaleSlider, 30);
		}
	}
	ScaleSlider();
	$Jssor$.$AddEvent(window, "load", ScaleSlider);
	$Jssor$.$AddEvent(window, "resize", ScaleSlider);
	$Jssor$.$AddEvent(window, "orientationchange", ScaleSlider);
	/*responsive code end*/
};
</script>
<div id="package_slide">
	<div id="jssor_1" style="position:relative;margin:0 auto;top:0px;left:0px;width:600px;height:300px;overflow:hidden;visibility:hidden;">
		<!-- Loading Screen -->
		<div data-u="loading" style="position:absolute;top:0px;left:0px;background:url('./img/loading.gif') no-repeat 50% 50%;background-color:rgba(0, 0, 0, 0.7);"></div>
		<div data-u="slides" style="cursor:default;position:relative;top:0px;left:0px;width:600px;height:300px;overflow:hidden;">
			<div>
				<img data-u="image" src="./img/군항제3.jpg" />
			</div>
			<div>
				<img data-u="image" src="./img/i.jpg" />
			</div>
			<div>
				<img data-u="image" src="./img/Jeju-bg.jpg" />
			</div>
			<div>
				<img data-u="image" src="./img/20101021182610.jpg" />
			</div>
			<a data-u="any" href="https://www.jssor.com" style="display:none">js slider</a>
		</div>
		<!-- Bullet Navigator -->
		<div data-u="navigator" class="jssorb05" style="bottom:16px;right:16px;" data-autocenter="1">
			<!-- bullet navigator item prototype -->
			<div data-u="prototype" style="width:16px;height:16px;"></div>
		</div>
		<!-- Arrow Navigator -->
		<span data-u="arrowleft" class="jssora12l" style="top:0px;left:0px;width:30px;height:46px;" data-autocenter="2"></span>
		<span data-u="arrowright" class="jssora12r" style="top:0px;right:0px;width:30px;height:46px;" data-autocenter="2"></span>
	</div>
	<script type="text/javascript">jssor_1_slider_init();</script>
</div>