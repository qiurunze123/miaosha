/** 
 * @preserve jQuery flipcountdown plugin v3.0.5
 * @homepage http://xdsoft.net/jqplugins/flipcountdown/
 * (c) 2013, Chupurnov Valeriy.
 */
(function($){
jQuery.fn.flipCountDown = jQuery.fn.flipcountdown = function( _options ){
	var default_options = {
			showHour	:true,
			showMinute	:true,
			showSecond	:true,
			am			:false,

			tzoneOffset	:0,
			speedFlip	:60,
			period		:1000,
			tick		:function(){
							return new Date();
						},
			autoUpdate	:true,
			size		:'md',
			
			beforeDateTime:false,
			
			prettyPrint :function( chars ){
				return (chars instanceof Array)?chars.join(' '):chars;
			}
		},
		
		digitsCount = 66,
		
		sizes = {
			lg:77,
			md:52,
			sm:35,
			xs:24
		},
		
		createFlipCountDown = function( $box ){
			var $flipcountdown 	= $('<div class="xdsoft_flipcountdown"></div>'),
				$clearex 		= $('<div class="xdsoft_clearex"></div>'),	 
				
				options = $.extend({},default_options),
				
				timer = 0,
				
				_animateRange = function( box,a,b ){
					if( !isNaN(a) ){
						_animateOne( box,a,((a>b && !(a==9&&b==0) )||(a==0&&b==9))?-1:1,!((a==9&&b==0)||(a==0&&b==9))?Math.abs(a-b):1 );
					}else{
						box.css('background-position','0px -'+((b+1)*6*sizes[options.size]+1)+'px' );
					}
				},
				
				_animateOne = function( box,a,arrow,range ){
					if( range<1 )
						return;
	
					_setMargin(box,-((a+1)*6*sizes[options.size]+1),1,arrow,function(){
						_animateOne(box,a+arrow,arrow,range-1);
					},range);
				},
				
				_setMargin = function( box, marginTop, rec, arrow,callback,range){
					if( marginTop<=-sizes[options.size]*digitsCount )
						marginTop = -(6*sizes[options.size]+1);
					box.css('background-position','0px '+marginTop+'px' );
					if( rec<=6 ){
						setTimeout(function(){
							_setMargin(box, marginTop-arrow*sizes[options.size], ++rec, arrow, callback,range);	
						},parseInt(options.speedFlip/range));
					}else
						callback();
				},
				
				blocks = [],
				
				_typeCompare	= 	function ( a,b ){
					return 	a&&b&&(
								(a==b)||
								(/^[0-9]+$/.test(a+''+b))||
								(/^[:.\s]+$/.test(a+''+b))
							);
				},
				
				_generate = function( chars ){
					if( !(chars instanceof Array) || !chars.length )
						return false;
					for( var i = 0, n = chars.length;i<n;i++ ){
						if( !blocks[i] ){
							blocks[i] = $('<div class="xdsoft_digit"></div>');
							$clearex.before(blocks[i]);
						}
						if( blocks[i].data('value')!=chars[i] ){
							if( !_typeCompare(blocks[i].data('value'),chars[i]) ){
								blocks[i]
									.removeClass('xdsoft_separator')
									.removeClass('xdsoft_dot');
								switch( chars[i] ){
									case ':':blocks[i].addClass('xdsoft_separator');break;
									case '.':blocks[i].addClass('xdsoft_dot');break; 
									case ' ':blocks[i].addClass('xdsoft_space');break; 
								}
							}
							if( !isNaN(chars[i]) ){
								var old = parseInt(blocks[i].data('value')), 
									ii = parseInt(blocks[i].data('i')),
									crnt = parseInt(chars[i]);
								_animateRange(blocks[i],old,crnt);
							}
							blocks[i].data('value',chars[i]);
							blocks[i].data('i',i);
						}
					}
					if( blocks.length>chars.length ){
						for(;i<blocks.length;i++ ){
							blocks[i][0].parentNode.removeChild(blocks[i][0]);
							delete blocks[i];
						}
						blocks.splice(chars.length);
					}
					
				},
				
				counter = 0,
				
				_calcMoment = function(){
					var value = '1',chars = [];
					if(options.tick)
						value = options.prettyPrint.call($box,(options.tick instanceof Function)?options.tick.call($box,counter):options.tick);
					
					if( typeof value!=='undefined' ){
						switch( value.constructor ){
							case Date:
								var h = (value.getHours()+options.tzoneOffset)%(options.am?12:24);
		
								if( options.showHour ){
									chars.push(parseInt(h/10));
									chars.push(h%10);
								}
										
								if( options.showHour && (options.showMinute || options.showSecond) )
									chars.push(':');
								
								if( options.showMinute ){
									chars.push(parseInt(value.getMinutes()/10));
									chars.push(value.getMinutes() % 10);
								}
								
								if( options.showMinute && options.showSecond )
									chars.push(':');
								
								if( options.showSecond ){
									chars.push(parseInt(value.getSeconds()/10));
									chars.push(value.getSeconds() % 10);
								}
							break;
							case String:
								chars = value.replace(/[^0-9\:\.\s]/g,'').split('');
							break;
							case Number:
								chars = value.toString().split('');
							break;
						}
						_generate(chars);
					}
				};
			
			
			
			$flipcountdown
				.append($clearex)
				.on('xdinit.xdsoft',function(){
					clearInterval(timer);
					if( options.autoUpdate )
						timer = setInterval( _calcMoment,options.period );
					_calcMoment();
				});
				
			$box.data('setOptions',function( _options ){
				options = $.extend(true,{},options,_options);
				if( !sizes[options.size] )
					options.size = defaulOptions.size;
					
				if( options.beforeDateTime && !_options.tick ){
					if( typeof(options.beforeDateTime) == 'string' )
						options.beforeDateTime = Math.round((new Date(options.beforeDateTime)).getTime()/1000);
					else{
						if ( Object.prototype.toString.call(options.beforeDateTime) !== "[object Date]" )
							options.beforeDateTime = Math.round((new Date()).getTime()/1000)+365*24*60;
					}
					var nol = function(h){
						return h>9?h:'0'+h;
					}
					
					options.tick = function(){
						var	range  	=  Math.max(0,options.beforeDateTime-Math.round((new Date()).getTime()/1000)),
							secday = 86400, sechour = 3600,
							days 	= parseInt(range/secday),
							hours	= parseInt((range%secday)/sechour),
							min		= parseInt(((range%secday)%sechour)/60),
							sec		= ((range%secday)%sechour)%60;
						return [nol(days),nol(hours),nol(min),nol(sec)];
					}
				}
				
				$flipcountdown
					.addClass('xdsoft_size_'+options.size)
					.trigger('xdinit.xdsoft');
			});
			$box.append($flipcountdown);
		};	
	return this.each(function(){
		var $box = $(this);
		if( !$box.data('setOptions') ){
			$box.addClass('xdsoft')
			createFlipCountDown($box);
		}
		$box.data('setOptions')&&
			$.isFunction($box.data('setOptions'))&&
				$box.data('setOptions')(_options);
	});
}
})(jQuery);