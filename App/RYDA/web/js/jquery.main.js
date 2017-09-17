// page init
jQuery(function(){
	initCycleCarousel();
	initSlideShow();
	initMobileNav();
	initRetinaCover();
	initCustomHover();
	initScrollSection();
	initInVieport();
	initCustomForms();
	jQuery('input, textarea').placeholder();
	initFitVids();
	initAddClasses();
	initInputs();
	initCheckEnd();
});

jQuery(window).load(function(){
	initSameHeight();
});
function initCheckEnd() {
	var animateEndClass = 'animate-finish';

	jQuery('.team-list li').each(function(){
		var item = jQuery(this);

		item.one('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function(e) {
			item.addClass(animateEndClass);
		});
	});
}

// initialize custom form elements
function initCustomForms() {
	jcf.setOptions('Select', {
		wrapNative: false,
		wrapNativeOnMobile: false
	});
	jcf.replaceAll();
}

// clear inputs on focus
function initInputs() {
	PlaceholderInput.replaceByOptions({
		// filter options
		clearInputs: true,
		clearTextareas: true,
		clearPasswords: true,
		skipClass: 'default',

		// input options
		wrapWithElement: false,
		showUntilTyping: false,
		getParentByClass: false,
		placeholderAttr: 'value'
	});
}

// load more init
function initInVieport() {
	jQuery('.matters-block').itemInViewport({
		activeClass: 'in-viewport',
		once: true
	});
}

// add class on click
function initAddClasses() {
	jQuery('.bottom-arrow').clickClass({
		classAdd: 'arrow-active'
	});
}

// handle flexible video size
function initFitVids() {
	jQuery('.video-frame').fitVids();
}

var isTouchDevice = /Windows Phone/.test(navigator.userAgent) || ('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch;

function initScrollSection() {
	jQuery('.scroll-area').scrollPage({
		slide: '.section-holder',
		wrap: '#wrapper',
		paging: '.showcase-pagination',
		pagingItem: 'li',
		pagingLink: 'a',
		btnScroll: '.bottom-arrow',
		activeClass: 'active',
		wrapClass: 'scroll-hidden',
		slideHideClass: 'slide-hidden',
		visibleClass: 'visible-section',
		touchDeviceClass: 'touch-device',
		scrollSpeed: 500,
		animSpeed: 700
	});
}

// cycle scroll gallery init
function initCycleCarousel() {
	jQuery('.carousel').scrollAbsoluteGallery({
		mask: '.mask',
		slider: '.slideset',
		slides: '.slide',
		btnPrev: 'a.btn-prev',
		btnNext: 'a.btn-next',
		generatePagination: '.pagination',
		stretchSlideToMask: true,
		pauseOnHover: true,
		maskAutoSize: true,
		autoRotation: false,
		switchTime: 3000,
		animSpeed: 500
	});
}

// fade gallery init
function initSlideShow() {
	jQuery('.carousel').fadeGallery({
		slides: '.fade-slide',
		btnPrev: 'a.btn-prev',
		btnNext: 'a.btn-next',
		event: 'click',
		useSwipe: true,
		autoRotation: false,
		autoHeight: true,
		switchTime: 3000,
		animSpeed: 500
	});
}

// mobile menu init
function initMobileNav() {
	jQuery('body').mobileNav({
		hideOnClickOutside: true,
		menuActiveClass: 'nav-active',
		menuOpener: '.nav-opener',
		menuDrop: '.nav-drop'
	});

	jQuery('body').mobileNav({
		hideOnClickOutside: true,
		menuActiveClass: 'modal-active',
		menuOpener: '.modal-opener,  .modal-close',
		menuDrop: '.modal-wrapper',
		onHide: function(self) {
			var iframe = self.drop.find('iframe');

			if (iframe.length !== 0) {
				iframe.attr('src', iframe.attr('src'));
			}
		}
	});
}

// align blocks height
function initSameHeight() {
	jQuery('.matters-holder').sameHeight({
		elements: '.holder',
		flexible: true,
		multiLine: true
	});
	jQuery('.blocks-area').sameHeight({
		elements: '.block',
		flexible: true,
		multiLine: true
	});
	jQuery('.team-holder').sameHeight({
		elements: '.description-area, .image-block',
		flexible: true,
		multiLine: true,
		biggestHeight: true
	});
}

function initRetinaCover() {
	jQuery('.bg-stretch').retinaCover();
}

// add classes on hover/touch
function initCustomHover() {
	jQuery('.team-list li, .team-blocks-area .image-block, .cities-list .image-holder').touchHover();}

/*
 * jQuery ScrollPage plugin
 */
;(function($) {
	function ScrollPage(options) {
		this.options = $.extend({
			holder: null,
			animSpeed: 500
		}, options);

		this.init();
	}
	ScrollPage.prototype = {
		init: function() {
			this.findElements();
			this.makeCallback('onInit', this);
			this.attachEvents();
			this.refreshClass();
		},
		findElements: function() {
			this.holder = jQuery(this.options.holder);
			this.slides = this.holder.find(this.options.slide);

			this.btnScroll = this.holder.find(this.options.btnScroll);

			this.paging = this.holder.find(this.options.paging);
			this.pagingItems = this.paging.find(this.options.pagingItem);
			this.pagingLinks = this.paging.find(this.options.pagingLink);

			this.wrap = jQuery(this.options.wrap);

			this.win = jQuery(window);
			this.isBusy = false;
			this.curInd = 0;
			this.prevInd = 0;
		},
		prepareSlides: function() {
			var self = this;

			this.slides.each(function(index) {
				var slide = jQuery(this);

				slide.css({
					zIndex: self.slides.length - index
				});
			});

			this.wrap.addClass(this.options.wrapClass);
			this.curInd = 0;
			this.refreshClass();
		},
		attachEvents: function(){
			var self = this;

			this.mouseWheelHandle = function(ev) {
				if (!self.isBusy) {

					self.isBusy = true;
					if (ev.originalEvent.detail > 0 || ev.originalEvent.wheelDelta < 0) {
						self.curInd ++;
						self.slideDown();
					} else {
						self.curInd --;
						self.slideUp();
					}
				}

				return false;
			};

			this.clickHandler = function(ev) {
				ev.preventDefault();

				self.prevInd = self.curInd;
				self.curInd = self.pagingItems.index(ev.currentTarget);

				if (self.prevInd < self.curInd) {
					self.slideDown();
				} else if (self.prevInd > self.curInd) {
					self.slideUp();
				}

				self.refreshClass(true);
			};

			this.scrollDownHandler = function(ev) {
				ev.preventDefault();

				self.curInd ++;
				self.slideDown();

				self.refreshClass(true);
			};

			this.clickLinksHandler = function(ev) {
				ev.preventDefault();

				self.curInd = self.pagingLinks.index(event.currentTarget);
				var activeLink = self.pagingLinks.eq(self.curInd);
				self.initSmooth(jQuery(activeLink.attr('href')).offset().top);

				self.refreshClass(true);
			};

			this.scrollHandler = function(ev) {
				ev.preventDefault();

				self.filterSlide();
			};

			this.downHandler = function(ev) {
				ev.preventDefault();

				var activeSlide = jQuery(self.btnScroll.attr('href'));

				if (activeSlide.length !== 0) {
					self.initSmooth(activeSlide.offset().top);
				}
			};

			if(!isTouchDevice) {
				ResponsiveHelper.addRange({
					'..767': {
						on: function() {
							clearTimeout(self.timer);
							self.timer = setTimeout(function() {
								self.win.off('DOMMouseScroll mousewheel', self.mouseWheelHandle);
								self.pagingItems.off('click', self.clickHandler);
								self.btnScroll.off('click', self.scrollDownHandler);

								self.wrap.removeClass(self.options.wrapClass);
								self.slides.removeClass(self.options.slideHideClass);

								self.pagingLinks.on('click', self.clickLinksHandler);
								self.win.on('scroll resize orientationchange', self.scrollHandler);
								self.btnScroll.on('click', self.downHandler);
							}, 100);
						}
					},
					'768..': {
						on: function() {
							clearTimeout(self.timer);
							self.timer = setTimeout(function() {
								self.pagingLinks.off('click', self.clickLinksHandler);
								self.win.off('scroll resize orientationchange', self.scrollHandler);
								self.btnScroll.off('click', self.downHandler);

								self.win[0].scrollTo(0, 0);
								self.prepareSlides();

								self.win.on('DOMMouseScroll mousewheel', self.mouseWheelHandle);
								self.pagingItems.on('click', self.clickHandler);
								self.btnScroll.on('click', self.scrollDownHandler);
							}, 100);
						}
					}
				});
			} else {
				jQuery('body').addClass(this.options.touchDeviceClass);
				this.pagingLinks.on('click', this.clickLinksHandler);
				this.win.on('scroll resize orientationchange', this.scrollHandler);
				this.btnScroll.on('click', this.downHandler);
			}
		},
		filterSlide: function() {
			var self = this;

			var activeSlide = this.slides.filter(function() {
				return self.win.scrollTop()< jQuery(this).offset().top && self.win.scrollTop() + self.win.height() < jQuery(this).offset().top + jQuery(this).outerHeight();
			}).first();

			var activeBtnSlide = this.slides.filter(function() {
				return self.btnScroll.offset().top > jQuery(this).offset().top;
			}).last();

			this.curInd = this.slides.index(activeSlide) - 1;

			if (this.curInd < 0) {
				this.curInd = this.slides.length - 1;
			}

			this.btnScroll.attr('href', '#' + activeSlide.attr('id'));
			this.btnScroll.attr('id', activeBtnSlide.data('arrow'));

			this.refreshClass(false);
		},
		slideDown: function() {
			var self = this;

			if (this.curInd < this.slides.length) {

				var activeSlide = this.slides.eq(this.curInd);
				var prevSlides = activeSlide.prevAll();
				prevSlides.addClass(this.options.slideHideClass);

				setTimeout(function() {
					self.isBusy = false;
				}, this.options.animSpeed);
			} else {
				this.isBusy = false;
				this.curInd = this.slides.length - 1;
			}

			this.refreshClass(true);
		},
		slideUp: function() {
			var self = this;

			if (this.curInd !== -1) {

				var activeSlide = this.slides.eq(this.curInd);
				var nextSlides = activeSlide.nextAll();
				activeSlide.add(nextSlides).removeClass(this.options.slideHideClass);

				setTimeout(function() {
					self.isBusy = false;
				}, this.options.animSpeed);
			} else {
				this.isBusy = false;
				this.curInd = 0;
			}

			this.refreshClass(true);
		},
		refreshClass: function(flag) {
			var activeSection = this.slides.eq(this.curInd);

			this.pagingItems.removeClass(this.options.activeClass);
			this.pagingItems.eq(this.curInd).addClass(this.options.activeClass);

			if (flag) {
				this.btnScroll.attr('id', activeSection.data('arrow'));
			}

			activeSection.addClass(this.options.visibleClass);
			this.holder.attr('id', 'color-' + activeSection.attr('id'));
		},
		initSmooth: function (offset, callback) {
			jQuery('html, body').stop().animate({
				scrollTop: offset
			}, {
				duration: this.options.scrollSpeed,
				complete: function() {
					if (typeof callback === 'function') {
						callback(true);
					}
				}
			});
		},
		makeCallback: function(name) {
			if (typeof this.options[name] === 'function') {
				var args = Array.prototype.slice.call(arguments);
				args.shift();
				this.options[name].apply(this, args);
			}
		}
	};

	$.fn.scrollPage = function(options) {
		return this.each(function() {
			$(this).data('ScrollPage', new ScrollPage($.extend(options, { holder:this })));
		});
	};
})(jQuery);

/*
 * Responsive Layout helper
 */
ResponsiveHelper = (function($){
	// init variables
	var handlers = [],
		prevWinWidth,
		win = $(window),
		nativeMatchMedia = false;

	// detect match media support
	if(window.matchMedia) {
		if(window.Window && window.matchMedia === Window.prototype.matchMedia) {
			nativeMatchMedia = true;
		} else if(window.matchMedia.toString().indexOf('native') > -1) {
			nativeMatchMedia = true;
		}
	}

	// prepare resize handler
	function resizeHandler() {
		var winWidth = win.width();
		if(winWidth !== prevWinWidth) {
			prevWinWidth = winWidth;

			// loop through range groups
			$.each(handlers, function(index, rangeObject){
				// disable current active area if needed
				$.each(rangeObject.data, function(property, item) {
					if(item.currentActive && !matchRange(item.range[0], item.range[1])) {
						item.currentActive = false;
						if(typeof item.disableCallback === 'function') {
							item.disableCallback();
						}
					}
				});

				// enable areas that match current width
				$.each(rangeObject.data, function(property, item) {
					if(!item.currentActive && matchRange(item.range[0], item.range[1])) {
						// make callback
						item.currentActive = true;
						if(typeof item.enableCallback === 'function') {
							item.enableCallback();
						}
					}
				});
			});
		}
	}
	win.bind('load resize orientationchange', resizeHandler);

	// test range
	function matchRange(r1, r2) {
		var mediaQueryString = '';
		if(r1 > 0) {
			mediaQueryString += '(min-width: ' + r1 + 'px)';
		}
		if(r2 < Infinity) {
			mediaQueryString += (mediaQueryString ? ' and ' : '') + '(max-width: ' + r2 + 'px)';
		}
		return matchQuery(mediaQueryString, r1, r2);
	}

	// media query function
	function matchQuery(query, r1, r2) {
		if(window.matchMedia && nativeMatchMedia) {
			return matchMedia(query).matches;
		} else if(window.styleMedia) {
			return styleMedia.matchMedium(query);
		} else if(window.media) {
			return media.matchMedium(query);
		} else {
			return prevWinWidth >= r1 && prevWinWidth <= r2;
		}
	}

	// range parser
	function parseRange(rangeStr) {
		var rangeData = rangeStr.split('..');
		var x1 = parseInt(rangeData[0], 10) || -Infinity;
		var x2 = parseInt(rangeData[1], 10) || Infinity;
		return [x1, x2].sort(function(a, b){
			return a - b;
		});
	}

	// export public functions
	return {
		addRange: function(ranges) {
			// parse data and add items to collection
			var result = {data:{}};
			$.each(ranges, function(property, data){
				result.data[property] = {
					range: parseRange(property),
					enableCallback: data.on,
					disableCallback: data.off
				};
			});
			handlers.push(result);

			// call resizeHandler to recalculate all events
			prevWinWidth = null;
			resizeHandler();
		}
	};
}(jQuery));
/*
 * jQuery SlideShow plugin
 */
;(function($){
	function FadeGallery(options) {
		this.options = $.extend({
			slides: 'ul.slideset > li',
			activeClass:'active',
			disabledClass:'disabled',
			btnPrev: 'a.btn-prev',
			btnNext: 'a.btn-next',
			generatePagination: false,
			pagerList: '<ul>',
			pagerListItem: '<li><a href="#"></a></li>',
			pagerListItemText: 'a',
			pagerLinks: '.pagination li',
			currentNumber: 'span.current-num',
			totalNumber: 'span.total-num',
			btnPlay: '.btn-play',
			btnPause: '.btn-pause',
			btnPlayPause: '.btn-play-pause',
			galleryReadyClass: 'gallery-js-ready',
			autorotationActiveClass: 'autorotation-active',
			autorotationDisabledClass: 'autorotation-disabled',
			autorotationStopAfterClick: false,
			circularRotation: true,
			switchSimultaneously: true,
			disableWhileAnimating: false,
			disableFadeIE: false,
			autoRotation: false,
			pauseOnHover: true,
			autoHeight: false,
			useSwipe: false,
			swipeThreshold: 15,
			switchTime: 4000,
			animSpeed: 600,
			event:'click'
		}, options);
		this.init();
	}
	FadeGallery.prototype = {
		init: function() {
			if(this.options.holder) {
				this.findElements();
				this.attachEvents();
				this.refreshState(true);
				this.autoRotate();
				this.makeCallback('onInit', this);
			}
		},
		findElements: function() {
			// control elements
			this.gallery = $(this.options.holder).addClass(this.options.galleryReadyClass);
			this.slides = this.gallery.find(this.options.slides);
			this.slidesHolder = this.slides.eq(0).parent();
			this.stepsCount = this.slides.length;
			this.btnPrev = this.gallery.find(this.options.btnPrev);
			this.btnNext = this.gallery.find(this.options.btnNext);
			this.currentIndex = 0;

			// disable fade effect in old IE
			if(this.options.disableFadeIE && !$.support.opacity) {
				this.options.animSpeed = 0;
			}

			// create gallery pagination
			if(typeof this.options.generatePagination === 'string') {
				this.pagerHolder = this.gallery.find(this.options.generatePagination).empty();
				this.pagerList = $(this.options.pagerList).appendTo(this.pagerHolder);
				for(var i = 0; i < this.stepsCount; i++) {
					$(this.options.pagerListItem).appendTo(this.pagerList).find(this.options.pagerListItemText).text(i+1);
				}
				this.pagerLinks = this.pagerList.children();
			} else {
				this.pagerLinks = this.gallery.find(this.options.pagerLinks);
			}

			// get start index
			var activeSlide = this.slides.filter('.'+this.options.activeClass);
			if(activeSlide.length) {
				this.currentIndex = this.slides.index(activeSlide);
			}
			this.prevIndex = this.currentIndex;

			// autorotation control buttons
			this.btnPlay = this.gallery.find(this.options.btnPlay);
			this.btnPause = this.gallery.find(this.options.btnPause);
			this.btnPlayPause = this.gallery.find(this.options.btnPlayPause);

			// misc elements
			this.curNum = this.gallery.find(this.options.currentNumber);
			this.allNum = this.gallery.find(this.options.totalNumber);

			// handle flexible layout
			this.slides.css({display:'block',opacity:0}).eq(this.currentIndex).css({
				opacity:''
			});
		},
		attachEvents: function() {
			var self = this;

			// flexible layout handler
			this.resizeHandler = function() {
				self.onWindowResize();
			};
			$(window).bind('load resize orientationchange', this.resizeHandler);

			if(this.btnPrev.length) {
				this.btnPrevHandler = function(e){
					e.preventDefault();
					self.prevSlide();
					if(self.options.autorotationStopAfterClick) {
						self.stopRotation();
					}
				};
				this.btnPrev.bind(this.options.event, this.btnPrevHandler);
			}
			if(this.btnNext.length) {
				this.btnNextHandler = function(e) {
					e.preventDefault();
					self.nextSlide();
					if(self.options.autorotationStopAfterClick) {
						self.stopRotation();
					}
				};
				this.btnNext.bind(this.options.event, this.btnNextHandler);
			}
			if(this.pagerLinks.length) {
				this.pagerLinksHandler = function(e) {
					e.preventDefault();
					self.numSlide(self.pagerLinks.index(e.currentTarget));
					if(self.options.autorotationStopAfterClick) {
						self.stopRotation();
					}
				};
				this.pagerLinks.bind(self.options.event, this.pagerLinksHandler);
			}

			// autorotation buttons handler
			if(this.btnPlay.length) {
				this.btnPlayHandler = function(e) {
					e.preventDefault();
					self.startRotation();
				};
				this.btnPlay.bind(this.options.event, this.btnPlayHandler);
			}
			if(this.btnPause.length) {
				this.btnPauseHandler = function(e) {
					e.preventDefault();
					self.stopRotation();
				};
				this.btnPause.bind(this.options.event, this.btnPauseHandler);
			}
			if(this.btnPlayPause.length) {
				this.btnPlayPauseHandler = function(e){
					e.preventDefault();
					if(!self.gallery.hasClass(self.options.autorotationActiveClass)) {
						self.startRotation();
					} else {
						self.stopRotation();
					}
				};
				this.btnPlayPause.bind(this.options.event, this.btnPlayPauseHandler);
			}

			// swipe gestures handler
			if(this.options.useSwipe && window.Hammer && isTouchDevice) {
				this.swipeHandler = new Hammer.Manager(this.gallery[0]);
				this.swipeHandler.add(new Hammer.Swipe({
					direction: Hammer.DIRECTION_HORIZONTAL,
					threshold: self.options.swipeThreshold
				}));
				this.swipeHandler.on('swipeleft', function() {
					self.nextSlide();
				}).on('swiperight', function() {
					self.prevSlide();
				});
			}

			// pause on hover handling
			if(this.options.pauseOnHover) {
				this.hoverHandler = function() {
					if(self.options.autoRotation) {
						self.galleryHover = true;
						self.pauseRotation();
					}
				};
				this.leaveHandler = function() {
					if(self.options.autoRotation) {
						self.galleryHover = false;
						self.resumeRotation();
					}
				};
				this.gallery.bind({mouseenter: this.hoverHandler, mouseleave: this.leaveHandler});
			}
		},
		onWindowResize: function(){
			if(this.options.autoHeight) {
				this.slidesHolder.css({height: this.slides.eq(this.currentIndex).outerHeight(true) });
			}
		},
		prevSlide: function() {
			if(!(this.options.disableWhileAnimating && this.galleryAnimating)) {
				this.prevIndex = this.currentIndex;
				if(this.currentIndex > 0) {
					this.currentIndex--;
					this.switchSlide();
				} else if(this.options.circularRotation) {
					this.currentIndex = this.stepsCount - 1;
					this.switchSlide();
				}
			}
		},
		nextSlide: function(fromAutoRotation) {
			if(!(this.options.disableWhileAnimating && this.galleryAnimating)) {
				this.prevIndex = this.currentIndex;
				if(this.currentIndex < this.stepsCount - 1) {
					this.currentIndex++;
					this.switchSlide();
				} else if(this.options.circularRotation || fromAutoRotation === true) {
					this.currentIndex = 0;
					this.switchSlide();
				}
			}
		},
		numSlide: function(c) {
			if(this.currentIndex != c) {
				this.prevIndex = this.currentIndex;
				this.currentIndex = c;
				this.switchSlide();
			}
		},
		switchSlide: function() {
			var self = this;
			if(this.slides.length > 1) {
				this.galleryAnimating = true;
				if(!this.options.animSpeed) {
					this.slides.eq(this.prevIndex).css({opacity:0});
				} else {
					this.slides.eq(this.prevIndex).stop().animate({opacity:0},{duration: this.options.animSpeed});
				}

				this.switchNext = function() {
					if(!self.options.animSpeed) {
						self.slides.eq(self.currentIndex).css({opacity:''});
					} else {
						self.slides.eq(self.currentIndex).stop().animate({opacity:1},{duration: self.options.animSpeed});
					}
					clearTimeout(this.nextTimer);
					this.nextTimer = setTimeout(function() {
						self.slides.eq(self.currentIndex).css({opacity:''});
						self.galleryAnimating = false;
						self.autoRotate();

						// onchange callback
						self.makeCallback('onChange', self);
					}, self.options.animSpeed);
				};

				if(this.options.switchSimultaneously) {
					self.switchNext();
				} else {
					clearTimeout(this.switchTimer);
					this.switchTimer = setTimeout(function(){
						self.switchNext();
					}, this.options.animSpeed);
				}
				this.refreshState();

				// onchange callback
				this.makeCallback('onBeforeChange', this);
			}
		},
		refreshState: function(initial) {
			this.slides.removeClass(this.options.activeClass).eq(this.currentIndex).addClass(this.options.activeClass);
			this.pagerLinks.removeClass(this.options.activeClass).eq(this.currentIndex).addClass(this.options.activeClass);
			this.curNum.html(this.currentIndex+1);
			this.allNum.html(this.stepsCount);

			// initial refresh
			if(this.options.autoHeight) {
				if(initial) {
					this.slidesHolder.css({height: this.slides.eq(this.currentIndex).outerHeight(true) });
				} else {
					this.slidesHolder.stop().animate({height: this.slides.eq(this.currentIndex).outerHeight(true)}, {duration: this.options.animSpeed});
				}
			}

			// disabled state
			if(!this.options.circularRotation) {
				this.btnPrev.add(this.btnNext).removeClass(this.options.disabledClass);
				if(this.currentIndex === 0) this.btnPrev.addClass(this.options.disabledClass);
				if(this.currentIndex === this.stepsCount - 1) this.btnNext.addClass(this.options.disabledClass);
			}

			// add class if not enough slides
			this.gallery.toggleClass('not-enough-slides', this.stepsCount === 1);
		},
		startRotation: function() {
			this.options.autoRotation = true;
			this.galleryHover = false;
			this.autoRotationStopped = false;
			this.resumeRotation();
		},
		stopRotation: function() {
			this.galleryHover = true;
			this.autoRotationStopped = true;
			this.pauseRotation();
		},
		pauseRotation: function() {
			this.gallery.addClass(this.options.autorotationDisabledClass);
			this.gallery.removeClass(this.options.autorotationActiveClass);
			clearTimeout(this.timer);
		},
		resumeRotation: function() {
			if(!this.autoRotationStopped) {
				this.gallery.addClass(this.options.autorotationActiveClass);
				this.gallery.removeClass(this.options.autorotationDisabledClass);
				this.autoRotate();
			}
		},
		autoRotate: function() {
			var self = this;
			clearTimeout(this.timer);
			if(this.options.autoRotation && !this.galleryHover && !this.autoRotationStopped) {
				this.gallery.addClass(this.options.autorotationActiveClass);
				this.timer = setTimeout(function(){
					self.nextSlide(true);
				}, this.options.switchTime);
			} else {
				this.pauseRotation();
			}
		},
		makeCallback: function(name) {
			if(typeof this.options[name] === 'function') {
				var args = Array.prototype.slice.call(arguments);
				args.shift();
				this.options[name].apply(this, args);
			}
		},
		destroy: function() {
			// navigation buttons handler
			this.btnPrev.unbind(this.options.event, this.btnPrevHandler);
			this.btnNext.unbind(this.options.event, this.btnNextHandler);
			this.pagerLinks.unbind(this.options.event, this.pagerLinksHandler);
			$(window).unbind('load resize orientationchange', this.resizeHandler);

			// remove autorotation handlers
			this.stopRotation();
			this.btnPlay.unbind(this.options.event, this.btnPlayHandler);
			this.btnPause.unbind(this.options.event, this.btnPauseHandler);
			this.btnPlayPause.unbind(this.options.event, this.btnPlayPauseHandler);
			this.gallery.unbind('mouseenter', this.hoverHandler);
			this.gallery.unbind('mouseleave', this.leaveHandler);

			// remove swipe handler if used
			if(this.swipeHandler) {
				this.swipeHandler.destroy();
			}
			if(typeof this.options.generatePagination === 'string') {
				this.pagerHolder.empty();
			}

			// remove unneeded classes and styles
			var unneededClasses = [this.options.galleryReadyClass, this.options.autorotationActiveClass, this.options.autorotationDisabledClass];
			this.gallery.removeClass(unneededClasses.join(' '));
			this.slidesHolder.add(this.slides).removeAttr('style');
		}
	};

	// detect device type
	var isTouchDevice = /Windows Phone/.test(navigator.userAgent) || ('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch;

	// jquery plugin
	$.fn.fadeGallery = function(opt){
		return this.each(function(){
			$(this).data('FadeGallery', new FadeGallery($.extend(opt,{holder:this})));
		});
	};
}(jQuery));

/*
 * Simple Mobile Navigation
 */
;(function($) {
	function MobileNav(options) {
		this.options = $.extend({
			container: null,
			hideOnClickOutside: false,
			menuActiveClass: 'nav-active',
			menuOpener: '.nav-opener',
			menuDrop: '.nav-drop',
			toggleEvent: 'click',
			outsideClickEvent: 'click touchstart pointerdown MSPointerDown'
		}, options);
		this.initStructure();
		this.attachEvents();
	}
	MobileNav.prototype = {
		initStructure: function() {
			this.page = $('html');
			this.container = $(this.options.container);
			this.opener = this.container.find(this.options.menuOpener);
			this.drop = this.container.find(this.options.menuDrop);
		},
		attachEvents: function() {
			var self = this;

			if(activateResizeHandler) {
				activateResizeHandler();
				activateResizeHandler = null;
			}

			this.outsideClickHandler = function(e) {
				if(self.isOpened()) {
					var target = $(e.target);
					if(!target.closest(self.opener).length && !target.closest(self.drop).length) {
						self.hide();
					}
				}
			};

			this.openerClickHandler = function(e) {
				e.preventDefault();
				self.toggle();
			};

			this.opener.on(this.options.toggleEvent, this.openerClickHandler);
		},
		isOpened: function() {
			return this.container.hasClass(this.options.menuActiveClass);
		},
		show: function() {
			this.container.addClass(this.options.menuActiveClass);
			if(this.options.hideOnClickOutside) {
				this.page.on(this.options.outsideClickEvent, this.outsideClickHandler);
			}
			this.makeCallback('onShow', this);
		},
		hide: function() {
			this.container.removeClass(this.options.menuActiveClass);
			if(this.options.hideOnClickOutside) {
				this.page.off(this.options.outsideClickEvent, this.outsideClickHandler);
			}

			this.makeCallback('onHide', this);
		},
		toggle: function() {
			if(this.isOpened()) {
				this.hide();
			} else {
				this.show();
			}
		},
		makeCallback: function(name) {
			if (typeof this.options[name] === 'function') {
				var args = Array.prototype.slice.call(arguments);
				args.shift();
				this.options[name].apply(this, args);
			}
		},
		destroy: function() {
			this.container.removeClass(this.options.menuActiveClass);
			this.opener.off(this.options.toggleEvent, this.clickHandler);
			this.page.off(this.options.outsideClickEvent, this.outsideClickHandler);
		}
	};

	var activateResizeHandler = function() {
		var win = $(window),
			doc = $('html'),
			resizeClass = 'resize-active',
			flag, timer;
		var removeClassHandler = function() {
			flag = false;
			doc.removeClass(resizeClass);
		};
		var resizeHandler = function() {
			if(!flag) {
				flag = true;
				doc.addClass(resizeClass);
			}
			clearTimeout(timer);
			timer = setTimeout(removeClassHandler, 500);
		};
		win.on('resize orientationchange', resizeHandler);
	};

	$.fn.mobileNav = function(options) {
		return this.each(function() {
			var params = $.extend({}, options, {container: this}),
				instance = new MobileNav(params);
			$.data(this, 'MobileNav', instance);
		});
	};
}(jQuery));

/*
 * jQuery SameHeight plugin
 */
;(function($){
	$.fn.sameHeight = function(opt) {
		var options = $.extend({
			skipClass: 'same-height-ignore',
			leftEdgeClass: 'same-height-left',
			rightEdgeClass: 'same-height-right',
			elements: '>*',
			flexible: false,
			multiLine: false,
			useMinHeight: false,
			biggestHeight: false
		},opt);
		return this.each(function(){
			var holder = $(this), postResizeTimer, ignoreResize;
			var elements = holder.find(options.elements).not('.' + options.skipClass);
			if(!elements.length) return;

			// resize handler
			function doResize() {
				elements.css(options.useMinHeight && supportMinHeight ? 'minHeight' : 'height', '');
				if(options.multiLine) {
					// resize elements row by row
					resizeElementsByRows(elements, options);
				} else {
					// resize elements by holder
					resizeElements(elements, holder, options);
				}
			}
			doResize();

			// handle flexible layout / font resize
			var delayedResizeHandler = function() {
				if(!ignoreResize) {
					ignoreResize = true;
					doResize();
					clearTimeout(postResizeTimer);
					postResizeTimer = setTimeout(function() {
						doResize();
						setTimeout(function(){
							ignoreResize = false;
						}, 10);
					}, 100);
				}
			};

			// handle flexible/responsive layout
			if(options.flexible) {
				$(window).bind('resize orientationchange fontresize', delayedResizeHandler);
			}

			// handle complete page load including images and fonts
			$(window).bind('load', delayedResizeHandler);
		});
	};

	// detect css min-height support
	var supportMinHeight = typeof document.documentElement.style.maxHeight !== 'undefined';

	// get elements by rows
	function resizeElementsByRows(boxes, options) {
		var currentRow = $(), maxHeight, maxCalcHeight = 0, firstOffset = boxes.eq(0).offset().top;
		boxes.each(function(ind){
			var curItem = $(this);
			if(curItem.offset().top === firstOffset) {
				currentRow = currentRow.add(this);
			} else {
				maxHeight = getMaxHeight(currentRow);
				maxCalcHeight = Math.max(maxCalcHeight, resizeElements(currentRow, maxHeight, options));
				currentRow = curItem;
				firstOffset = curItem.offset().top;
			}
		});
		if(currentRow.length) {
			maxHeight = getMaxHeight(currentRow);
			maxCalcHeight = Math.max(maxCalcHeight, resizeElements(currentRow, maxHeight, options));
		}
		if(options.biggestHeight) {
			boxes.css(options.useMinHeight && supportMinHeight ? 'minHeight' : 'height', maxCalcHeight);
		}
	}

	// calculate max element height
	function getMaxHeight(boxes) {
		var maxHeight = 0;
		boxes.each(function(){
			maxHeight = Math.max(maxHeight, $(this).outerHeight());
		});
		return maxHeight;
	}

	// resize helper function
	function resizeElements(boxes, parent, options) {
		var calcHeight;
		var parentHeight = typeof parent === 'number' ? parent : parent.height();
		boxes.removeClass(options.leftEdgeClass).removeClass(options.rightEdgeClass).each(function(i){
			var element = $(this);
			var depthDiffHeight = 0;
			var isBorderBox = element.css('boxSizing') === 'border-box' || element.css('-moz-box-sizing') === 'border-box' || element.css('-webkit-box-sizing') === 'border-box';

			if(typeof parent !== 'number') {
				element.parents().each(function(){
					var tmpParent = $(this);
					if(parent.is(this)) {
						return false;
					} else {
						depthDiffHeight += tmpParent.outerHeight() - tmpParent.height();
					}
				});
			}
			calcHeight = parentHeight - depthDiffHeight;
			calcHeight -= isBorderBox ? 0 : element.outerHeight() - element.height();

			if(calcHeight > 0) {
				element.css(options.useMinHeight && supportMinHeight ? 'minHeight' : 'height', calcHeight);
			}
		});
		boxes.filter(':first').addClass(options.leftEdgeClass);
		boxes.filter(':last').addClass(options.rightEdgeClass);
		return calcHeight;
	}
}(jQuery));

/*
 * jQuery retina cover plugin
 */
 ;(function($) {
 	'use strict';

 	var styleRules = {};
 	var templates = {
 		'2x': [
 			'(-webkit-min-device-pixel-ratio: 1.5)',
 			'(min-resolution: 192dpi)',
 			'(min-device-pixel-ratio: 1.5)',
 			'(min-resolution: 1.5dppx)'
 		],
 		'3x': [
 			'(-webkit-min-device-pixel-ratio: 3)',
 			'(min-resolution: 384dpi)',
 			'(min-device-pixel-ratio: 3)',
 			'(min-resolution: 3dppx)'
 		]
 	};

 	function addSimple(imageSrc, media, id) {
 		var style = buildRule(id, imageSrc);

 		addRule(media, style);
 	}

 	function addRetina(imageData, media, id) {
 		var currentRules = templates[imageData[1]].slice();
 		var patchedRules = currentRules;
 		var style = buildRule(id, imageData[0]);

 		if (media !== 'default') {
 			patchedRules = $.map(currentRules, function(ele, i) {
 				return ele + ' and ' + media;
 			});
 		}

 		media = patchedRules.join(',');

 		addRule(media, style);
 	}

 	function buildRule(id, src) {
 		return '#' + id + '{background-image: url("' + src + '");}';
 	}

 	function addRule(media, rule) {
 		var $styleTag = styleRules[media];
 		var styleTagData;
 		var rules = '';

 		if (media === 'default') {
 			rules = rule + ' ';
 		} else {
 			rules = '@media ' + media + '{' + rule + '}';
 		}

 		if (!$styleTag) {
 			styleRules[media] = $('<style>').text(rules).appendTo('head');
 		} else {
 			styleTagData = $styleTag.text();
 			styleTagData = styleTagData.substring(0, styleTagData.length - 2) + ' }' + rule + '}';
 			$styleTag.text(styleTagData);
 		}
 	}

 	$.fn.retinaCover = function() {
 		return this.each(function() {
 			var $block = $(this);
 			var $items = $block.children('[data-srcset]');
 			var id = 'bg-stretch' + Date.now() + (Math.random() * 1000).toFixed(0);

 			if ($items.length) {
 				$block.attr('id', id);

 				$items.each(function() {
 					var $item = $(this);
 					var data = $item.data('srcset').split(', ');
 					var media = $item.data('media') || 'default';
 					var dataLength = data.length;
 					var itemData;
 					var i;

 					for (i = 0; i < dataLength; i++) {
 						itemData = data[i].split(' ');

 						if (itemData.length === 1) {
 							addSimple(itemData[0], media, id);
 						} else {
 							addRetina(itemData, media, id);
 						}
 					}
 				});
 			}

 			$items.detach();
 		});
 	};
 }(jQuery));

/*
 * Mobile hover plugin
 */
;(function($){

	// detect device type
	var isTouchDevice = ('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch,
		isWinPhoneDevice = /Windows Phone/.test(navigator.userAgent);

	// define events
	var eventOn = (isTouchDevice && 'touchstart') || (isWinPhoneDevice && navigator.pointerEnabled && 'pointerdown') || (isWinPhoneDevice && navigator.msPointerEnabled && 'MSPointerDown') || 'mouseenter',
		eventOff = (isTouchDevice && 'touchend') || (isWinPhoneDevice && navigator.pointerEnabled && 'pointerup') || (isWinPhoneDevice && navigator.msPointerEnabled && 'MSPointerUp') || 'mouseleave';

	// event handlers
	var toggleOn, toggleOff, preventHandler;
	if(isTouchDevice || isWinPhoneDevice) {
		// prevent click handler
		preventHandler = function(e) {
			e.preventDefault();
		};

		// touch device handlers
		toggleOn = function(e) {
			var options = e.data, element = $(this);

			var toggleOff = function(e) {
				var target = $(e.target);
				if (!target.is(element) && !target.closest(element).length) {
					element.removeClass(options.hoverClass);
					element.off('click', preventHandler);
					if(options.onLeave) options.onLeave(element);
					$(document).off(eventOn, toggleOff);
				}
			};

			if(!element.hasClass(options.hoverClass)) {
				element.addClass(options.hoverClass);
				element.one('click', preventHandler);
				$(document).on(eventOn, toggleOff);
				if(options.onHover) options.onHover(element);
			}
		};
	} else {
		// desktop browser handlers
		toggleOn = function(e) {
			var options = e.data, element = $(this);
			element.addClass(options.hoverClass);
			$(options.context).on(eventOff, options.selector, options, toggleOff);
			if(options.onHover) options.onHover(element);
		};
		toggleOff = function(e) {
			var options = e.data, element = $(this);
			element.removeClass(options.hoverClass);
			$(options.context).off(eventOff, options.selector, toggleOff);
			if(options.onLeave) options.onLeave(element);
		};
	}

	// jQuery plugin
	$.fn.touchHover = function(opt) {
		var options = $.extend({
			context: this.context,
			selector: this.selector,
			hoverClass: 'hover'
		}, opt);

		$(this.context).on(eventOn, this.selector, options, toggleOn);
		return this;
	};
}(jQuery));

/*
 * jQuery FontResize Event
 */
jQuery.onFontResize = (function($) {
	$(function() {
		var randomID = 'font-resize-frame-' + Math.floor(Math.random() * 1000);
		var resizeFrame = $('<iframe>').attr('id', randomID).addClass('font-resize-helper');

		// required styles
		resizeFrame.css({
			width: '100em',
			height: '10px',
			position: 'absolute',
			borderWidth: 0,
			top: '-9999px',
			left: '-9999px'
		}).appendTo('body');

		// use native IE resize event if possible
		if (window.attachEvent && !window.addEventListener) {
			resizeFrame.bind('resize', function () {
				$.onFontResize.trigger(resizeFrame[0].offsetWidth / 100);
			});
		}
		// use script inside the iframe to detect resize for other browsers
		else {
			var doc = resizeFrame[0].contentWindow.document;
			doc.open();
			doc.write('<scri' + 'pt>window.onload = function(){var em = parent.jQuery("#' + randomID + '")[0];window.onresize = function(){if(parent.jQuery.onFontResize){parent.jQuery.onFontResize.trigger(em.offsetWidth / 100);}}};</scri' + 'pt>');
			doc.close();
		}
		jQuery.onFontResize.initialSize = resizeFrame[0].offsetWidth / 100;
	});
	return {
		// public method, so it can be called from within the iframe
		trigger: function (em) {
			$(window).trigger("fontresize", [em]);
		}
	};
}(jQuery));

/*
 * jQuery In Viewport plugin
 */
;(function($, $win) {
	'use strict';

	var ScrollDetector = (function() {
		var data = {};

		return {
			init: function() {
				var self = this;

				this.addHolder('win', $win);

				$win.on('load.blockInViewport resize.blockInViewport orientationchange.blockInViewport', function() {
					$.each(data, function(holderKey, holderData) {
						self.calcHolderSize(holderData);

						$.each(holderData.items, function(itemKey, itemData) {
							self.calcItemSize(itemKey, itemData);
						});
					});
				});
			},

			addHolder: function(holderKey, $holder) {
				var self = this;
				var holderData =  {
					holder: $holder,
					items: {},
					props: {
						height: 0,
						scroll: 0
					}
				};

				data[holderKey] = holderData;

				$holder.on('scroll.blockInViewport', function() {
					self.calcHolderScroll(holderData);

					$.each(holderData.items, function(itemKey, itemData) {
						self.calcItemScroll(itemKey, itemData);
					});
				});

				this.calcHolderSize(data[holderKey]);
			},

			calcHolderSize: function(holderData) {
				var holderOffset = holderData.holder.offset();

				holderData.props.height = holderData.holder.get(0) === window ? (window.innerHeight || document.documentElement.clientHeight) : holderData.holder.outerHeight();
				holderData.props.offset = holderOffset ? holderOffset.top : 0;

				this.calcHolderScroll(holderData);
			},

			calcItemSize: function(itemKey, itemData) {
				itemData.offset = itemData.$el.offset().top - itemData.holderProps.props.offset;
				itemData.height = itemData.$el.outerHeight();

				this.calcItemScroll(itemKey, itemData);
			},

			calcHolderScroll: function(holderData) {
				holderData.props.scroll = holderData.holder.scrollTop();
			},

			calcItemScroll: function(itemKey, itemData) {
				var itemInViewPortFromUp;
				var itemInViewPortFromDown;
				var itemOutViewPort;
				var holderProps = itemData.holderProps.props;

				switch (itemData.options.visibleMode) {
					case 1:
						itemInViewPortFromDown = itemData.offset < holderProps.scroll + holderProps.height / 2 || itemData.offset + itemData.height < holderProps.scroll + holderProps.height;
						itemInViewPortFromUp   = itemData.offset > holderProps.scroll || itemData.offset + itemData.height > holderProps.scroll + holderProps.height / 2;
						break;

					case 2:
						itemInViewPortFromDown = itemInViewPortFromDown || (itemData.offset < holderProps.scroll + holderProps.height / 2 || itemData.offset + itemData.height / 2 < holderProps.scroll + holderProps.height);
						itemInViewPortFromUp   = itemInViewPortFromUp || (itemData.offset + itemData.height / 2 > holderProps.scroll || itemData.offset + itemData.height > holderProps.scroll + holderProps.height / 2);
						break;

					case 3:
						itemInViewPortFromDown = itemInViewPortFromDown || (itemData.offset < holderProps.scroll + holderProps.height / 2 || itemData.offset < holderProps.scroll + holderProps.height);
						itemInViewPortFromUp   = itemInViewPortFromUp || (itemData.offset + itemData.height > holderProps.scroll || itemData.offset + itemData.height > holderProps.scroll + holderProps.height / 2);
						break;

					default:
						itemInViewPortFromDown = itemInViewPortFromDown || (itemData.offset < holderProps.scroll + holderProps.height / 2 || itemData.offset + Math.min(itemData.options.visibleMode, itemData.height) < holderProps.scroll + holderProps.height);
						itemInViewPortFromUp   = itemInViewPortFromUp || (itemData.offset + itemData.height - Math.min(itemData.options.visibleMode, itemData.height) > holderProps.scroll || itemData.offset + itemData.height > holderProps.scroll + holderProps.height / 2);
						break;
				}


				if (itemInViewPortFromUp && itemInViewPortFromDown) {
					if (!itemData.state) {
						itemData.state = true;
						itemData.$el.addClass(itemData.options.activeClass)
									.trigger('in-viewport', true);

						if (itemData.options.once || ($.isFunction(itemData.options.onShow) && itemData.options.onShow(itemData))) {
							delete itemData.holderProps.items[itemKey];
						}
					}
				} else {
					itemOutViewPort = itemData.offset < holderProps.scroll + holderProps.height && itemData.offset + itemData.height > holderProps.scroll;

					if ((itemData.state || isNaN(itemData.state)) && !itemOutViewPort) {
						itemData.state = false;
						itemData.$el.removeClass(itemData.options.activeClass)
									.trigger('in-viewport', false);
					}
				}
			},

			addItem: function(el, options) {
				var itemKey = 'item' + this.getRandomValue();
				var newItem = {
					$el: $(el),
					options: options
				};
				var holderKeyDataName = 'in-viewport-holder';

				var $holder = newItem.$el.closest(options.holder);
				var holderKey = $holder.data(holderKeyDataName);

				if (!$holder.length) {
					holderKey = 'win';
				} else if (!holderKey) {
					holderKey = 'holder' + this.getRandomValue();
					$holder.data(holderKeyDataName, holderKey);

					this.addHolder(holderKey, $holder);
				}

				newItem.holderProps = data[holderKey];

				data[holderKey].items[itemKey] = newItem;

				this.calcItemSize(itemKey, newItem);
			},

			getRandomValue: function() {
				return (Math.random() * 100000).toFixed(0);
			},

			destroy: function() {
				$win.off('.blockInViewport');

				$.each(data, function(key, value) {
					value.holder.off('.blockInViewport');

					$.each(value.items, function(key, value) {
						value.$el.removeClass(value.options.activeClass);
						value.$el.get(0).itemInViewportAdded = null;
					});
				});

				data = {};
			}
		};
	}());

	ScrollDetector.init();

	$.fn.itemInViewport = function(options) {
		options = $.extend({
			activeClass: 'in-viewport',
			once: true,
			holder: '',
			visibleMode: 1 // 1 - full block, 2 - half block, 3 - immediate, 4... - custom
		}, options);

		return this.each(function() {
			if (this.itemInViewportAdded) {
				return;
			}

			this.itemInViewportAdded = true;

			ScrollDetector.addItem(this, options);
		});
	};
}(jQuery, jQuery(window)));

/*!
 * JavaScript Custom Forms
 *
 * Copyright 2014-2015 PSD2HTML - http://psd2html.com/jcf
 * Released under the MIT license (LICENSE.txt)
 *
 * Version: 1.1.3
 */
;(function(root, factory) {
	'use strict';
	if (typeof define === 'function' && define.amd) {
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		module.exports = factory(require('jquery'));
	} else {
		root.jcf = factory(jQuery);
	}
}(this, function($) {
	'use strict';

	// define version
	var version = '1.1.3';

	// private variables
	var customInstances = [];

	// default global options
	var commonOptions = {
		optionsKey: 'jcf',
		dataKey: 'jcf-instance',
		rtlClass: 'jcf-rtl',
		focusClass: 'jcf-focus',
		pressedClass: 'jcf-pressed',
		disabledClass: 'jcf-disabled',
		hiddenClass: 'jcf-hidden',
		resetAppearanceClass: 'jcf-reset-appearance',
		unselectableClass: 'jcf-unselectable'
	};

	// detect device type
	var isTouchDevice = ('ontouchstart' in window) || window.DocumentTouch && document instanceof window.DocumentTouch,
		isWinPhoneDevice = /Windows Phone/.test(navigator.userAgent);
	commonOptions.isMobileDevice = !!(isTouchDevice || isWinPhoneDevice);

	// create global stylesheet if custom forms are used
	var createStyleSheet = function() {
		var styleTag = $('<style>').appendTo('head'),
			styleSheet = styleTag.prop('sheet') || styleTag.prop('styleSheet');

		// crossbrowser style handling
		var addCSSRule = function(selector, rules, index) {
			if (styleSheet.insertRule) {
				styleSheet.insertRule(selector + '{' + rules + '}', index);
			} else {
				styleSheet.addRule(selector, rules, index);
			}
		};

		// add special rules
		addCSSRule('.' + commonOptions.hiddenClass, 'position:absolute !important;left:-9999px !important;height:1px !important;width:1px !important;margin:0 !important;border-width:0 !important;-webkit-appearance:none;-moz-appearance:none;appearance:none');
		addCSSRule('.' + commonOptions.rtlClass + ' .' + commonOptions.hiddenClass, 'right:-9999px !important; left: auto !important');
		addCSSRule('.' + commonOptions.unselectableClass, '-webkit-user-select: none; -moz-user-select: none; -ms-user-select: none; user-select: none; -webkit-tap-highlight-color: rgba(0,0,0,0);');
		addCSSRule('.' + commonOptions.resetAppearanceClass, 'background: none; border: none; -webkit-appearance: none; appearance: none; opacity: 0; filter: alpha(opacity=0);');

		// detect rtl pages
		var html = $('html'), body = $('body');
		if (html.css('direction') === 'rtl' || body.css('direction') === 'rtl') {
			html.addClass(commonOptions.rtlClass);
		}

		// handle form reset event
		html.on('reset', function() {
			setTimeout(function() {
				api.refreshAll();
			}, 0);
		});

		// mark stylesheet as created
		commonOptions.styleSheetCreated = true;
	};

	// simplified pointer events handler
	(function() {
		var pointerEventsSupported = navigator.pointerEnabled || navigator.msPointerEnabled,
			touchEventsSupported = ('ontouchstart' in window) || window.DocumentTouch && document instanceof window.DocumentTouch,
			eventList, eventMap = {}, eventPrefix = 'jcf-';

		// detect events to attach
		if (pointerEventsSupported) {
			eventList = {
				pointerover: navigator.pointerEnabled ? 'pointerover' : 'MSPointerOver',
				pointerdown: navigator.pointerEnabled ? 'pointerdown' : 'MSPointerDown',
				pointermove: navigator.pointerEnabled ? 'pointermove' : 'MSPointerMove',
				pointerup: navigator.pointerEnabled ? 'pointerup' : 'MSPointerUp'
			};
		} else {
			eventList = {
				pointerover: 'mouseover',
				pointerdown: 'mousedown' + (touchEventsSupported ? ' touchstart' : ''),
				pointermove: 'mousemove' + (touchEventsSupported ? ' touchmove' : ''),
				pointerup: 'mouseup' + (touchEventsSupported ? ' touchend' : '')
			};
		}

		// create event map
		$.each(eventList, function(targetEventName, fakeEventList) {
			$.each(fakeEventList.split(' '), function(index, fakeEventName) {
				eventMap[fakeEventName] = targetEventName;
			});
		});

		// jQuery event hooks
		$.each(eventList, function(eventName, eventHandlers) {
			eventHandlers = eventHandlers.split(' ');
			$.event.special[eventPrefix + eventName] = {
				setup: function() {
					var self = this;
					$.each(eventHandlers, function(index, fallbackEvent) {
						if (self.addEventListener) self.addEventListener(fallbackEvent, fixEvent, false);
						else self['on' + fallbackEvent] = fixEvent;
					});
				},
				teardown: function() {
					var self = this;
					$.each(eventHandlers, function(index, fallbackEvent) {
						if (self.addEventListener) self.removeEventListener(fallbackEvent, fixEvent, false);
						else self['on' + fallbackEvent] = null;
					});
				}
			};
		});

		// check that mouse event are not simulated by mobile browsers
		var lastTouch = null;
		var mouseEventSimulated = function(e) {
			var dx = Math.abs(e.pageX - lastTouch.x),
				dy = Math.abs(e.pageY - lastTouch.y),
				rangeDistance = 25;

			if (dx <= rangeDistance && dy <= rangeDistance) {
				return true;
			}
		};

		// normalize event
		var fixEvent = function(e) {
			var origEvent = e || window.event,
				touchEventData = null,
				targetEventName = eventMap[origEvent.type];

			e = $.event.fix(origEvent);
			e.type = eventPrefix + targetEventName;

			if (origEvent.pointerType) {
				switch (origEvent.pointerType) {
					case 2: e.pointerType = 'touch'; break;
					case 3: e.pointerType = 'pen'; break;
					case 4: e.pointerType = 'mouse'; break;
					default: e.pointerType = origEvent.pointerType;
				}
			} else {
				e.pointerType = origEvent.type.substr(0, 5); // "mouse" or "touch" word length
			}

			if (!e.pageX && !e.pageY) {
				touchEventData = origEvent.changedTouches ? origEvent.changedTouches[0] : origEvent;
				e.pageX = touchEventData.pageX;
				e.pageY = touchEventData.pageY;
			}

			if (origEvent.type === 'touchend') {
				lastTouch = { x: e.pageX, y: e.pageY };
			}
			if (e.pointerType === 'mouse' && lastTouch && mouseEventSimulated(e)) {
				return;
			} else {
				return ($.event.dispatch || $.event.handle).call(this, e);
			}
		};
	}());

	// custom mousewheel/trackpad handler
	(function() {
		var wheelEvents = ('onwheel' in document || document.documentMode >= 9 ? 'wheel' : 'mousewheel DOMMouseScroll').split(' '),
			shimEventName = 'jcf-mousewheel';

		$.event.special[shimEventName] = {
			setup: function() {
				var self = this;
				$.each(wheelEvents, function(index, fallbackEvent) {
					if (self.addEventListener) self.addEventListener(fallbackEvent, fixEvent, false);
					else self['on' + fallbackEvent] = fixEvent;
				});
			},
			teardown: function() {
				var self = this;
				$.each(wheelEvents, function(index, fallbackEvent) {
					if (self.addEventListener) self.removeEventListener(fallbackEvent, fixEvent, false);
					else self['on' + fallbackEvent] = null;
				});
			}
		};

		var fixEvent = function(e) {
			var origEvent = e || window.event;
			e = $.event.fix(origEvent);
			e.type = shimEventName;

			// old wheel events handler
			if ('detail'      in origEvent) { e.deltaY = -origEvent.detail;      }
			if ('wheelDelta'  in origEvent) { e.deltaY = -origEvent.wheelDelta;  }
			if ('wheelDeltaY' in origEvent) { e.deltaY = -origEvent.wheelDeltaY; }
			if ('wheelDeltaX' in origEvent) { e.deltaX = -origEvent.wheelDeltaX; }

			// modern wheel event handler
			if ('deltaY' in origEvent) {
				e.deltaY = origEvent.deltaY;
			}
			if ('deltaX' in origEvent) {
				e.deltaX = origEvent.deltaX;
			}

			// handle deltaMode for mouse wheel
			e.delta = e.deltaY || e.deltaX;
			if (origEvent.deltaMode === 1) {
				var lineHeight = 16;
				e.delta *= lineHeight;
				e.deltaY *= lineHeight;
				e.deltaX *= lineHeight;
			}

			return ($.event.dispatch || $.event.handle).call(this, e);
		};
	}());

	// extra module methods
	var moduleMixin = {
		// provide function for firing native events
		fireNativeEvent: function(elements, eventName) {
			$(elements).each(function() {
				var element = this, eventObject;
				if (element.dispatchEvent) {
					eventObject = document.createEvent('HTMLEvents');
					eventObject.initEvent(eventName, true, true);
					element.dispatchEvent(eventObject);
				} else if (document.createEventObject) {
					eventObject = document.createEventObject();
					eventObject.target = element;
					element.fireEvent('on' + eventName, eventObject);
				}
			});
		},
		// bind event handlers for module instance (functions beggining with "on")
		bindHandlers: function() {
			var self = this;
			$.each(self, function(propName, propValue) {
				if (propName.indexOf('on') === 0 && $.isFunction(propValue)) {
					// dont use $.proxy here because it doesn't create unique handler
					self[propName] = function() {
						return propValue.apply(self, arguments);
					};
				}
			});
		}
	};

	// public API
	var api = {
		version: version,
		modules: {},
		getOptions: function() {
			return $.extend({}, commonOptions);
		},
		setOptions: function(moduleName, moduleOptions) {
			if (arguments.length > 1) {
				// set module options
				if (this.modules[moduleName]) {
					$.extend(this.modules[moduleName].prototype.options, moduleOptions);
				}
			} else {
				// set common options
				$.extend(commonOptions, moduleName);
			}
		},
		addModule: function(proto) {
			// add module to list
			var Module = function(options) {
				// save instance to collection
				if (!options.element.data(commonOptions.dataKey)) {
					options.element.data(commonOptions.dataKey, this);
				}
				customInstances.push(this);

				// save options
				this.options = $.extend({}, commonOptions, this.options, getInlineOptions(options.element), options);

				// bind event handlers to instance
				this.bindHandlers();

				// call constructor
				this.init.apply(this, arguments);
			};

			// parse options from HTML attribute
			var getInlineOptions = function(element) {
				var dataOptions = element.data(commonOptions.optionsKey),
					attrOptions = element.attr(commonOptions.optionsKey);

				if (dataOptions) {
					return dataOptions;
				} else if (attrOptions) {
					try {
						return $.parseJSON(attrOptions);
					} catch (e) {
						// ignore invalid attributes
					}
				}
			};

			// set proto as prototype for new module
			Module.prototype = proto;

			// add mixin methods to module proto
			$.extend(proto, moduleMixin);
			if (proto.plugins) {
				$.each(proto.plugins, function(pluginName, plugin) {
					$.extend(plugin.prototype, moduleMixin);
				});
			}

			// override destroy method
			var originalDestroy = Module.prototype.destroy;
			Module.prototype.destroy = function() {
				this.options.element.removeData(this.options.dataKey);

				for (var i = customInstances.length - 1; i >= 0; i--) {
					if (customInstances[i] === this) {
						customInstances.splice(i, 1);
						break;
					}
				}

				if (originalDestroy) {
					originalDestroy.apply(this, arguments);
				}
			};

			// save module to list
			this.modules[proto.name] = Module;
		},
		getInstance: function(element) {
			return $(element).data(commonOptions.dataKey);
		},
		replace: function(elements, moduleName, customOptions) {
			var self = this,
				instance;

			if (!commonOptions.styleSheetCreated) {
				createStyleSheet();
			}

			$(elements).each(function() {
				var moduleOptions,
					element = $(this);

				instance = element.data(commonOptions.dataKey);
				if (instance) {
					instance.refresh();
				} else {
					if (!moduleName) {
						$.each(self.modules, function(currentModuleName, module) {
							if (module.prototype.matchElement.call(module.prototype, element)) {
								moduleName = currentModuleName;
								return false;
							}
						});
					}
					if (moduleName) {
						moduleOptions = $.extend({ element: element }, customOptions);
						instance = new self.modules[moduleName](moduleOptions);
					}
				}
			});
			return instance;
		},
		refresh: function(elements) {
			$(elements).each(function() {
				var instance = $(this).data(commonOptions.dataKey);
				if (instance) {
					instance.refresh();
				}
			});
		},
		destroy: function(elements) {
			$(elements).each(function() {
				var instance = $(this).data(commonOptions.dataKey);
				if (instance) {
					instance.destroy();
				}
			});
		},
		replaceAll: function(context) {
			var self = this;
			$.each(this.modules, function(moduleName, module) {
				$(module.prototype.selector, context).each(function() {
					if (this.className.indexOf('jcf-ignore') < 0) {
						self.replace(this, moduleName);
					}
				});
			});
		},
		refreshAll: function(context) {
			if (context) {
				$.each(this.modules, function(moduleName, module) {
					$(module.prototype.selector, context).each(function() {
						var instance = $(this).data(commonOptions.dataKey);
						if (instance) {
							instance.refresh();
						}
					});
				});
			} else {
				for (var i = customInstances.length - 1; i >= 0; i--) {
					customInstances[i].refresh();
				}
			}
		},
		destroyAll: function(context) {
			if (context) {
				$.each(this.modules, function(moduleName, module) {
					$(module.prototype.selector, context).each(function(index, element) {
						var instance = $(element).data(commonOptions.dataKey);
						if (instance) {
							instance.destroy();
						}
					});
				});
			} else {
				while (customInstances.length) {
					customInstances[0].destroy();
				}
			}
		}
	};

	// always export API to the global window object
	window.jcf = api;

	return api;
}));

/*!
 * JavaScript Custom Forms : Select Module
 *
 * Copyright 2014-2015 PSD2HTML - http://psd2html.com/jcf
 * Released under the MIT license (LICENSE.txt)
 *
 * Version: 1.1.3
 */
;(function($, window) {
	'use strict';

	jcf.addModule({
		name: 'Select',
		selector: 'select',
		options: {
			element: null,
			multipleCompactStyle: false
		},
		plugins: {
			ListBox: ListBox,
			ComboBox: ComboBox,
			SelectList: SelectList
		},
		matchElement: function(element) {
			return element.is('select');
		},
		init: function() {
			this.element = $(this.options.element);
			this.createInstance();
		},
		isListBox: function() {
			return this.element.is('[size]:not([jcf-size]), [multiple]');
		},
		createInstance: function() {
			if (this.instance) {
				this.instance.destroy();
			}
			if (this.isListBox() && !this.options.multipleCompactStyle) {
				this.instance = new ListBox(this.options);
			} else {
				this.instance = new ComboBox(this.options);
			}
		},
		refresh: function() {
			var typeMismatch = (this.isListBox() && this.instance instanceof ComboBox) ||
								(!this.isListBox() && this.instance instanceof ListBox);

			if (typeMismatch) {
				this.createInstance();
			} else {
				this.instance.refresh();
			}
		},
		destroy: function() {
			this.instance.destroy();
		}
	});

	// combobox module
	function ComboBox(options) {
		this.options = $.extend({
			wrapNative: true,
			wrapNativeOnMobile: true,
			fakeDropInBody: true,
			useCustomScroll: true,
			flipDropToFit: true,
			maxVisibleItems: 10,
			fakeAreaStructure: '<span class="jcf-select"><span class="jcf-select-text"></span><span class="jcf-select-opener"></span></span>',
			fakeDropStructure: '<div class="jcf-select-drop"><div class="jcf-select-drop-content"></div></div>',
			optionClassPrefix: 'jcf-option-',
			selectClassPrefix: 'jcf-select-',
			dropContentSelector: '.jcf-select-drop-content',
			selectTextSelector: '.jcf-select-text',
			dropActiveClass: 'jcf-drop-active',
			flipDropClass: 'jcf-drop-flipped'
		}, options);
		this.init();
	}
	$.extend(ComboBox.prototype, {
		init: function() {
			this.initStructure();
			this.bindHandlers();
			this.attachEvents();
			this.refresh();
		},
		initStructure: function() {
			// prepare structure
			this.win = $(window);
			this.doc = $(document);
			this.realElement = $(this.options.element);
			this.fakeElement = $(this.options.fakeAreaStructure).insertAfter(this.realElement);
			this.selectTextContainer = this.fakeElement.find(this.options.selectTextSelector);
			this.selectText = $('<span></span>').appendTo(this.selectTextContainer);
			makeUnselectable(this.fakeElement);

			// copy classes from original select
			this.fakeElement.addClass(getPrefixedClasses(this.realElement.prop('className'), this.options.selectClassPrefix));

			// handle compact multiple style
			if (this.realElement.prop('multiple')) {
				this.fakeElement.addClass('jcf-compact-multiple');
			}

			// detect device type and dropdown behavior
			if (this.options.isMobileDevice && this.options.wrapNativeOnMobile && !this.options.wrapNative) {
				this.options.wrapNative = true;
			}

			if (this.options.wrapNative) {
				// wrap native select inside fake block
				this.realElement.prependTo(this.fakeElement).css({
					position: 'absolute',
					height: '100%',
					width: '100%'
				}).addClass(this.options.resetAppearanceClass);
			} else {
				// just hide native select
				this.realElement.addClass(this.options.hiddenClass);
				this.fakeElement.attr('title', this.realElement.attr('title'));
				this.fakeDropTarget = this.options.fakeDropInBody ? $('body') : this.fakeElement;
			}
		},
		attachEvents: function() {
			// delayed refresh handler
			var self = this;
			this.delayedRefresh = function() {
				setTimeout(function() {
					self.refresh();
					if (self.list) {
						self.list.refresh();
						self.list.scrollToActiveOption();
					}
				}, 1);
			};

			// native dropdown event handlers
			if (this.options.wrapNative) {
				this.realElement.on({
					focus: this.onFocus,
					change: this.onChange,
					click: this.onChange,
					keydown: this.onChange
				});
			} else {
				// custom dropdown event handlers
				this.realElement.on({
					focus: this.onFocus,
					change: this.onChange,
					keydown: this.onKeyDown
				});
				this.fakeElement.on({
					'jcf-pointerdown': this.onSelectAreaPress
				});
			}
		},
		onKeyDown: function(e) {
			if (e.which === 13) {
				this.toggleDropdown();
			} else if (this.dropActive) {
				this.delayedRefresh();
			}
		},
		onChange: function() {
			this.refresh();
		},
		onFocus: function() {
			if (!this.pressedFlag || !this.focusedFlag) {
				this.fakeElement.addClass(this.options.focusClass);
				this.realElement.on('blur', this.onBlur);
				this.toggleListMode(true);
				this.focusedFlag = true;
			}
		},
		onBlur: function() {
			if (!this.pressedFlag) {
				this.fakeElement.removeClass(this.options.focusClass);
				this.realElement.off('blur', this.onBlur);
				this.toggleListMode(false);
				this.focusedFlag = false;
			}
		},
		onResize: function() {
			if (this.dropActive) {
				this.hideDropdown();
			}
		},
		onSelectDropPress: function() {
			this.pressedFlag = true;
		},
		onSelectDropRelease: function(e, pointerEvent) {
			this.pressedFlag = false;
			if (pointerEvent.pointerType === 'mouse') {
				this.realElement.focus();
			}
		},
		onSelectAreaPress: function(e) {
			// skip click if drop inside fake element or real select is disabled
			var dropClickedInsideFakeElement = !this.options.fakeDropInBody && $(e.target).closest(this.dropdown).length;
			if (dropClickedInsideFakeElement || e.button > 1 || this.realElement.is(':disabled')) {
				return;
			}

			// toggle dropdown visibility
			this.selectOpenedByEvent = e.pointerType;
			this.toggleDropdown();

			// misc handlers
			if (!this.focusedFlag) {
				if (e.pointerType === 'mouse') {
					this.realElement.focus();
				} else {
					this.onFocus(e);
				}
			}
			this.pressedFlag = true;
			this.fakeElement.addClass(this.options.pressedClass);
			this.doc.on('jcf-pointerup', this.onSelectAreaRelease);
		},
		onSelectAreaRelease: function(e) {
			if (this.focusedFlag && e.pointerType === 'mouse') {
				this.realElement.focus();
			}
			this.pressedFlag = false;
			this.fakeElement.removeClass(this.options.pressedClass);
			this.doc.off('jcf-pointerup', this.onSelectAreaRelease);
		},
		onOutsideClick: function(e) {
			var target = $(e.target),
				clickedInsideSelect = target.closest(this.fakeElement).length || target.closest(this.dropdown).length;

			if (!clickedInsideSelect) {
				this.hideDropdown();
			}
		},
		onSelect: function() {
			this.refresh();

			if (this.realElement.prop('multiple')) {
				this.repositionDropdown();
			} else {
				this.hideDropdown();
			}

			this.fireNativeEvent(this.realElement, 'change');
		},
		toggleListMode: function(state) {
			if (!this.options.wrapNative) {
				if (state) {
					// temporary change select to list to avoid appearing of native dropdown
					this.realElement.attr({
						size: 4,
						'jcf-size': ''
					});
				} else {
					// restore select from list mode to dropdown select
					if (!this.options.wrapNative) {
						this.realElement.removeAttr('size jcf-size');
					}
				}
			}
		},
		createDropdown: function() {
			// destroy previous dropdown if needed
			if (this.dropdown) {
				this.list.destroy();
				this.dropdown.remove();
			}

			// create new drop container
			this.dropdown = $(this.options.fakeDropStructure).appendTo(this.fakeDropTarget);
			this.dropdown.addClass(getPrefixedClasses(this.realElement.prop('className'), this.options.selectClassPrefix));
			makeUnselectable(this.dropdown);

			// handle compact multiple style
			if (this.realElement.prop('multiple')) {
				this.dropdown.addClass('jcf-compact-multiple');
			}

			// set initial styles for dropdown in body
			if (this.options.fakeDropInBody) {
				this.dropdown.css({
					position: 'absolute',
					top: -9999
				});
			}

			// create new select list instance
			this.list = new SelectList({
				useHoverClass: true,
				handleResize: false,
				alwaysPreventMouseWheel: true,
				maxVisibleItems: this.options.maxVisibleItems,
				useCustomScroll: this.options.useCustomScroll,
				holder: this.dropdown.find(this.options.dropContentSelector),
				multipleSelectWithoutKey: this.realElement.prop('multiple'),
				element: this.realElement
			});
			$(this.list).on({
				select: this.onSelect,
				press: this.onSelectDropPress,
				release: this.onSelectDropRelease
			});
		},
		repositionDropdown: function() {
			var selectOffset = this.fakeElement.offset(),
				selectWidth = this.fakeElement.outerWidth(),
				selectHeight = this.fakeElement.outerHeight(),
				dropHeight = this.dropdown.css('width', selectWidth).outerHeight(),
				winScrollTop = this.win.scrollTop(),
				winHeight = this.win.height(),
				calcTop, calcLeft, bodyOffset, needFlipDrop = false;

			// check flip drop position
			if (selectOffset.top + selectHeight + dropHeight > winScrollTop + winHeight && selectOffset.top - dropHeight > winScrollTop) {
				needFlipDrop = true;
			}

			if (this.options.fakeDropInBody) {
				bodyOffset = this.fakeDropTarget.css('position') !== 'static' ? this.fakeDropTarget.offset().top : 0;
				if (this.options.flipDropToFit && needFlipDrop) {
					// calculate flipped dropdown position
					calcLeft = selectOffset.left;
					calcTop = selectOffset.top - dropHeight - bodyOffset;
				} else {
					// calculate default drop position
					calcLeft = selectOffset.left;
					calcTop = selectOffset.top + selectHeight - bodyOffset;
				}

				// update drop styles
				this.dropdown.css({
					width: selectWidth,
					left: calcLeft,
					top: calcTop
				});
			}

			// refresh flipped class
			this.dropdown.add(this.fakeElement).toggleClass(this.options.flipDropClass, this.options.flipDropToFit && needFlipDrop);
		},
		showDropdown: function() {
			// do not show empty custom dropdown
			if (!this.realElement.prop('options').length) {
				return;
			}

			// create options list if not created
			if (!this.dropdown) {
				this.createDropdown();
			}

			// show dropdown
			this.dropActive = true;
			this.dropdown.appendTo(this.fakeDropTarget);
			this.fakeElement.addClass(this.options.dropActiveClass);
			this.refreshSelectedText();
			this.repositionDropdown();
			this.list.setScrollTop(this.savedScrollTop);
			this.list.refresh();

			// add temporary event handlers
			this.win.on('resize', this.onResize);
			this.doc.on('jcf-pointerdown', this.onOutsideClick);
		},
		hideDropdown: function() {
			if (this.dropdown) {
				this.savedScrollTop = this.list.getScrollTop();
				this.fakeElement.removeClass(this.options.dropActiveClass + ' ' + this.options.flipDropClass);
				this.dropdown.removeClass(this.options.flipDropClass).detach();
				this.doc.off('jcf-pointerdown', this.onOutsideClick);
				this.win.off('resize', this.onResize);
				this.dropActive = false;
				if (this.selectOpenedByEvent === 'touch') {
					this.onBlur();
				}
			}
		},
		toggleDropdown: function() {
			if (this.dropActive) {
				this.hideDropdown();
			} else {
				this.showDropdown();
			}
		},
		refreshSelectedText: function() {
			// redraw selected area
			var selectedIndex = this.realElement.prop('selectedIndex'),
				selectedOption = this.realElement.prop('options')[selectedIndex],
				selectedOptionImage = selectedOption ? selectedOption.getAttribute('data-image') : null,
				selectedOptionText = '',
				selectedOptionClasses,
				self = this;

			if (this.realElement.prop('multiple')) {
				$.each(this.realElement.prop('options'), function(index, option) {
					if (option.selected) {
						selectedOptionText += (selectedOptionText ? ', ' : '') + option.innerHTML;
					}
				});
				if (!selectedOptionText) {
					selectedOptionText = self.realElement.attr('placeholder') || '';
				}
				this.selectText.removeAttr('class').html(selectedOptionText);
			} else if (!selectedOption) {
				if (this.selectImage) {
					this.selectImage.hide();
				}
				this.selectText.removeAttr('class').empty();
			} else if (this.currentSelectedText !== selectedOption.innerHTML || this.currentSelectedImage !== selectedOptionImage) {
				selectedOptionClasses = getPrefixedClasses(selectedOption.className, this.options.optionClassPrefix);
				this.selectText.attr('class', selectedOptionClasses).html(selectedOption.innerHTML);

				if (selectedOptionImage) {
					if (!this.selectImage) {
						this.selectImage = $('<img>').prependTo(this.selectTextContainer).hide();
					}
					this.selectImage.attr('src', selectedOptionImage).show();
				} else if (this.selectImage) {
					this.selectImage.hide();
				}

				this.currentSelectedText = selectedOption.innerHTML;
				this.currentSelectedImage = selectedOptionImage;
			}
		},
		refresh: function() {
			// refresh fake select visibility
			if (this.realElement.prop('style').display === 'none') {
				this.fakeElement.hide();
			} else {
				this.fakeElement.show();
			}

			// refresh selected text
			this.refreshSelectedText();

			// handle disabled state
			this.fakeElement.toggleClass(this.options.disabledClass, this.realElement.is(':disabled'));
		},
		destroy: function() {
			// restore structure
			if (this.options.wrapNative) {
				this.realElement.insertBefore(this.fakeElement).css({
					position: '',
					height: '',
					width: ''
				}).removeClass(this.options.resetAppearanceClass);
			} else {
				this.realElement.removeClass(this.options.hiddenClass);
				if (this.realElement.is('[jcf-size]')) {
					this.realElement.removeAttr('size jcf-size');
				}
			}

			// removing element will also remove its event handlers
			this.fakeElement.remove();

			// remove other event handlers
			this.doc.off('jcf-pointerup', this.onSelectAreaRelease);
			this.realElement.off({
				focus: this.onFocus
			});
		}
	});

	// listbox module
	function ListBox(options) {
		this.options = $.extend({
			wrapNative: true,
			useCustomScroll: true,
			fakeStructure: '<span class="jcf-list-box"><span class="jcf-list-wrapper"></span></span>',
			selectClassPrefix: 'jcf-select-',
			listHolder: '.jcf-list-wrapper'
		}, options);
		this.init();
	}
	$.extend(ListBox.prototype, {
		init: function() {
			this.bindHandlers();
			this.initStructure();
			this.attachEvents();
		},
		initStructure: function() {
			this.realElement = $(this.options.element);
			this.fakeElement = $(this.options.fakeStructure).insertAfter(this.realElement);
			this.listHolder = this.fakeElement.find(this.options.listHolder);
			makeUnselectable(this.fakeElement);

			// copy classes from original select
			this.fakeElement.addClass(getPrefixedClasses(this.realElement.prop('className'), this.options.selectClassPrefix));
			this.realElement.addClass(this.options.hiddenClass);

			this.list = new SelectList({
				useCustomScroll: this.options.useCustomScroll,
				holder: this.listHolder,
				selectOnClick: false,
				element: this.realElement
			});
		},
		attachEvents: function() {
			// delayed refresh handler
			var self = this;
			this.delayedRefresh = function(e) {
				if (e && e.which === 16) {
					// ignore SHIFT key
					return;
				} else {
					clearTimeout(self.refreshTimer);
					self.refreshTimer = setTimeout(function() {
						self.refresh();
						self.list.scrollToActiveOption();
					}, 1);
				}
			};

			// other event handlers
			this.realElement.on({
				focus: this.onFocus,
				click: this.delayedRefresh,
				keydown: this.delayedRefresh
			});

			// select list event handlers
			$(this.list).on({
				select: this.onSelect,
				press: this.onFakeOptionsPress,
				release: this.onFakeOptionsRelease
			});
		},
		onFakeOptionsPress: function(e, pointerEvent) {
			this.pressedFlag = true;
			if (pointerEvent.pointerType === 'mouse') {
				this.realElement.focus();
			}
		},
		onFakeOptionsRelease: function(e, pointerEvent) {
			this.pressedFlag = false;
			if (pointerEvent.pointerType === 'mouse') {
				this.realElement.focus();
			}
		},
		onSelect: function() {
			this.fireNativeEvent(this.realElement, 'change');
			this.fireNativeEvent(this.realElement, 'click');
		},
		onFocus: function() {
			if (!this.pressedFlag || !this.focusedFlag) {
				this.fakeElement.addClass(this.options.focusClass);
				this.realElement.on('blur', this.onBlur);
				this.focusedFlag = true;
			}
		},
		onBlur: function() {
			if (!this.pressedFlag) {
				this.fakeElement.removeClass(this.options.focusClass);
				this.realElement.off('blur', this.onBlur);
				this.focusedFlag = false;
			}
		},
		refresh: function() {
			this.fakeElement.toggleClass(this.options.disabledClass, this.realElement.is(':disabled'));
			this.list.refresh();
		},
		destroy: function() {
			this.list.destroy();
			this.realElement.insertBefore(this.fakeElement).removeClass(this.options.hiddenClass);
			this.fakeElement.remove();
		}
	});

	// options list module
	function SelectList(options) {
		this.options = $.extend({
			holder: null,
			maxVisibleItems: 10,
			selectOnClick: true,
			useHoverClass: false,
			useCustomScroll: false,
			handleResize: true,
			multipleSelectWithoutKey: false,
			alwaysPreventMouseWheel: false,
			indexAttribute: 'data-index',
			cloneClassPrefix: 'jcf-option-',
			containerStructure: '<span class="jcf-list"><span class="jcf-list-content"></span></span>',
			containerSelector: '.jcf-list-content',
			captionClass: 'jcf-optgroup-caption',
			disabledClass: 'jcf-disabled',
			optionClass: 'jcf-option',
			groupClass: 'jcf-optgroup',
			hoverClass: 'jcf-hover',
			selectedClass: 'jcf-selected',
			scrollClass: 'jcf-scroll-active'
		}, options);
		this.init();
	}
	$.extend(SelectList.prototype, {
		init: function() {
			this.initStructure();
			this.refreshSelectedClass();
			this.attachEvents();
		},
		initStructure: function() {
			this.element = $(this.options.element);
			this.indexSelector = '[' + this.options.indexAttribute + ']';
			this.container = $(this.options.containerStructure).appendTo(this.options.holder);
			this.listHolder = this.container.find(this.options.containerSelector);
			this.lastClickedIndex = this.element.prop('selectedIndex');
			this.rebuildList();
		},
		attachEvents: function() {
			this.bindHandlers();
			this.listHolder.on('jcf-pointerdown', this.indexSelector, this.onItemPress);
			this.listHolder.on('jcf-pointerdown', this.onPress);

			if (this.options.useHoverClass) {
				this.listHolder.on('jcf-pointerover', this.indexSelector, this.onHoverItem);
			}
		},
		onPress: function(e) {
			$(this).trigger('press', e);
			this.listHolder.on('jcf-pointerup', this.onRelease);
		},
		onRelease: function(e) {
			$(this).trigger('release', e);
			this.listHolder.off('jcf-pointerup', this.onRelease);
		},
		onHoverItem: function(e) {
			var hoverIndex = parseFloat(e.currentTarget.getAttribute(this.options.indexAttribute));
			this.fakeOptions.removeClass(this.options.hoverClass).eq(hoverIndex).addClass(this.options.hoverClass);
		},
		onItemPress: function(e) {
			if (e.pointerType === 'touch' || this.options.selectOnClick) {
				// select option after "click"
				this.tmpListOffsetTop = this.list.offset().top;
				this.listHolder.on('jcf-pointerup', this.indexSelector, this.onItemRelease);
			} else {
				// select option immediately
				this.onSelectItem(e);
			}
		},
		onItemRelease: function(e) {
			// remove event handlers and temporary data
			this.listHolder.off('jcf-pointerup', this.indexSelector, this.onItemRelease);

			// simulate item selection
			if (this.tmpListOffsetTop === this.list.offset().top) {
				this.listHolder.on('click', this.indexSelector, { savedPointerType: e.pointerType }, this.onSelectItem);
			}
			delete this.tmpListOffsetTop;
		},
		onSelectItem: function(e) {
			var clickedIndex = parseFloat(e.currentTarget.getAttribute(this.options.indexAttribute)),
				pointerType = e.data && e.data.savedPointerType || e.pointerType || 'mouse',
				range;

			// remove click event handler
			this.listHolder.off('click', this.indexSelector, this.onSelectItem);

			// ignore clicks on disabled options
			if (e.button > 1 || this.realOptions[clickedIndex].disabled) {
				return;
			}

			if (this.element.prop('multiple')) {
				if (e.metaKey || e.ctrlKey || pointerType === 'touch' || this.options.multipleSelectWithoutKey) {
					// if CTRL/CMD pressed or touch devices - toggle selected option
					this.realOptions[clickedIndex].selected = !this.realOptions[clickedIndex].selected;
				} else if (e.shiftKey) {
					// if SHIFT pressed - update selection
					range = [this.lastClickedIndex, clickedIndex].sort(function(a, b) {
						return a - b;
					});
					this.realOptions.each(function(index, option) {
						option.selected = (index >= range[0] && index <= range[1]);
					});
				} else {
					// set single selected index
					this.element.prop('selectedIndex', clickedIndex);
				}
			} else {
				this.element.prop('selectedIndex', clickedIndex);
			}

			// save last clicked option
			if (!e.shiftKey) {
				this.lastClickedIndex = clickedIndex;
			}

			// refresh classes
			this.refreshSelectedClass();

			// scroll to active item in desktop browsers
			if (pointerType === 'mouse') {
				this.scrollToActiveOption();
			}

			// make callback when item selected
			$(this).trigger('select');
		},
		rebuildList: function() {
			// rebuild options
			var self = this,
				rootElement = this.element[0];

			// recursively create fake options
			this.storedSelectHTML = rootElement.innerHTML;
			this.optionIndex = 0;
			this.list = $(this.createOptionsList(rootElement));
			this.listHolder.empty().append(this.list);
			this.realOptions = this.element.find('option');
			this.fakeOptions = this.list.find(this.indexSelector);
			this.fakeListItems = this.list.find('.' + this.options.captionClass + ',' + this.indexSelector);
			delete this.optionIndex;

			// detect max visible items
			var maxCount = this.options.maxVisibleItems,
				sizeValue = this.element.prop('size');
			if (sizeValue > 1 && !this.element.is('[jcf-size]')) {
				maxCount = sizeValue;
			}

			// handle scrollbar
			var needScrollBar = this.fakeOptions.length > maxCount;

			this.container.toggleClass(this.options.scrollClass, needScrollBar);
			if (needScrollBar) {
				// change max-height
				this.listHolder.css({
					maxHeight: this.getOverflowHeight(maxCount),
					overflow: 'auto'
				});

				if (this.options.useCustomScroll && jcf.modules.Scrollable) {
					// add custom scrollbar if specified in options
					jcf.replace(this.listHolder, 'Scrollable', {
						handleResize: this.options.handleResize,
						alwaysPreventMouseWheel: this.options.alwaysPreventMouseWheel
					});
					return;
				}
			}

			// disable edge wheel scrolling
			if (this.options.alwaysPreventMouseWheel) {
				this.preventWheelHandler = function(e) {
					var currentScrollTop = self.listHolder.scrollTop(),
						maxScrollTop = self.listHolder.prop('scrollHeight') - self.listHolder.innerHeight();

					// check edge cases
					if ((currentScrollTop <= 0 && e.deltaY < 0) || (currentScrollTop >= maxScrollTop && e.deltaY > 0)) {
						e.preventDefault();
					}
				};
				this.listHolder.on('jcf-mousewheel', this.preventWheelHandler);
			}
		},
		refreshSelectedClass: function() {
			var self = this,
				selectedItem,
				isMultiple = this.element.prop('multiple'),
				selectedIndex = this.element.prop('selectedIndex');

			if (isMultiple) {
				this.realOptions.each(function(index, option) {
					self.fakeOptions.eq(index).toggleClass(self.options.selectedClass, !!option.selected);
				});
			} else {
				this.fakeOptions.removeClass(this.options.selectedClass + ' ' + this.options.hoverClass);
				selectedItem = this.fakeOptions.eq(selectedIndex).addClass(this.options.selectedClass);
				if (this.options.useHoverClass) {
					selectedItem.addClass(this.options.hoverClass);
				}
			}
		},
		scrollToActiveOption: function() {
			// scroll to target option
			var targetOffset = this.getActiveOptionOffset();
			if (typeof targetOffset === 'number') {
				this.listHolder.prop('scrollTop', targetOffset);
			}
		},
		getSelectedIndexRange: function() {
			var firstSelected = -1, lastSelected = -1;
			this.realOptions.each(function(index, option) {
				if (option.selected) {
					if (firstSelected < 0) {
						firstSelected = index;
					}
					lastSelected = index;
				}
			});
			return [firstSelected, lastSelected];
		},
		getChangedSelectedIndex: function() {
			var selectedIndex = this.element.prop('selectedIndex'),
				targetIndex;

			if (this.element.prop('multiple')) {
				// multiple selects handling
				if (!this.previousRange) {
					this.previousRange = [selectedIndex, selectedIndex];
				}
				this.currentRange = this.getSelectedIndexRange();
				targetIndex = this.currentRange[this.currentRange[0] !== this.previousRange[0] ? 0 : 1];
				this.previousRange = this.currentRange;
				return targetIndex;
			} else {
				// single choice selects handling
				return selectedIndex;
			}
		},
		getActiveOptionOffset: function() {
			// calc values
			var dropHeight = this.listHolder.height(),
				dropScrollTop = this.listHolder.prop('scrollTop'),
				currentIndex = this.getChangedSelectedIndex(),
				fakeOption = this.fakeOptions.eq(currentIndex),
				fakeOptionOffset = fakeOption.offset().top - this.list.offset().top,
				fakeOptionHeight = fakeOption.innerHeight();

			// scroll list
			if (fakeOptionOffset + fakeOptionHeight >= dropScrollTop + dropHeight) {
				// scroll down (always scroll to option)
				return fakeOptionOffset - dropHeight + fakeOptionHeight;
			} else if (fakeOptionOffset < dropScrollTop) {
				// scroll up to option
				return fakeOptionOffset;
			}
		},
		getOverflowHeight: function(sizeValue) {
			var item = this.fakeListItems.eq(sizeValue - 1),
				listOffset = this.list.offset().top,
				itemOffset = item.offset().top,
				itemHeight = item.innerHeight();

			return itemOffset + itemHeight - listOffset;
		},
		getScrollTop: function() {
			return this.listHolder.scrollTop();
		},
		setScrollTop: function(value) {
			this.listHolder.scrollTop(value);
		},
		createOption: function(option) {
			var newOption = document.createElement('span');
			newOption.className = this.options.optionClass;
			newOption.innerHTML = option.innerHTML;
			newOption.setAttribute(this.options.indexAttribute, this.optionIndex++);

			var optionImage, optionImageSrc = option.getAttribute('data-image');
			if (optionImageSrc) {
				optionImage = document.createElement('img');
				optionImage.src = optionImageSrc;
				newOption.insertBefore(optionImage, newOption.childNodes[0]);
			}
			if (option.disabled) {
				newOption.className += ' ' + this.options.disabledClass;
			}
			if (option.className) {
				newOption.className += ' ' + getPrefixedClasses(option.className, this.options.cloneClassPrefix);
			}
			return newOption;
		},
		createOptGroup: function(optgroup) {
			var optGroupContainer = document.createElement('span'),
				optGroupName = optgroup.getAttribute('label'),
				optGroupCaption, optGroupList;

			// create caption
			optGroupCaption = document.createElement('span');
			optGroupCaption.className = this.options.captionClass;
			optGroupCaption.innerHTML = optGroupName;
			optGroupContainer.appendChild(optGroupCaption);

			// create list of options
			if (optgroup.children.length) {
				optGroupList = this.createOptionsList(optgroup);
				optGroupContainer.appendChild(optGroupList);
			}

			optGroupContainer.className = this.options.groupClass;
			return optGroupContainer;
		},
		createOptionContainer: function() {
			var optionContainer = document.createElement('li');
			return optionContainer;
		},
		createOptionsList: function(container) {
			var self = this,
				list = document.createElement('ul');

			$.each(container.children, function(index, currentNode) {
				var item = self.createOptionContainer(currentNode),
					newNode;

				switch (currentNode.tagName.toLowerCase()) {
					case 'option': newNode = self.createOption(currentNode); break;
					case 'optgroup': newNode = self.createOptGroup(currentNode); break;
				}
				list.appendChild(item).appendChild(newNode);
			});
			return list;
		},
		refresh: function() {
			// check for select innerHTML changes
			if (this.storedSelectHTML !== this.element.prop('innerHTML')) {
				this.rebuildList();
			}

			// refresh custom scrollbar
			var scrollInstance = jcf.getInstance(this.listHolder);
			if (scrollInstance) {
				scrollInstance.refresh();
			}

			// refresh selectes classes
			this.refreshSelectedClass();
		},
		destroy: function() {
			this.listHolder.off('jcf-mousewheel', this.preventWheelHandler);
			this.listHolder.off('jcf-pointerdown', this.indexSelector, this.onSelectItem);
			this.listHolder.off('jcf-pointerover', this.indexSelector, this.onHoverItem);
			this.listHolder.off('jcf-pointerdown', this.onPress);
		}
	});

	// helper functions
	var getPrefixedClasses = function(className, prefixToAdd) {
		return className ? className.replace(/[\s]*([\S]+)+[\s]*/gi, prefixToAdd + '$1 ') : '';
	};
	var makeUnselectable = (function() {
		var unselectableClass = jcf.getOptions().unselectableClass;
		function preventHandler(e) {
			e.preventDefault();
		}
		return function(node) {
			node.addClass(unselectableClass).on('selectstart', preventHandler);
		};
	}());

}(jQuery, this));

/*!
 * JavaScript Custom Forms : File Module
 *
 * Copyright 2014-2015 PSD2HTML - http://psd2html.com/jcf
 * Released under the MIT license (LICENSE.txt)
 *
 * Version: 1.1.3
 */
;(function($) {
	'use strict';

	jcf.addModule({
		name: 'File',
		selector: 'input[type="file"]',
		options: {
			fakeStructure: '<span class="jcf-file"><span class="jcf-fake-input"></span><span class="jcf-upload-button"><span class="jcf-button-content"></span></span></span>',
			buttonText: 'Choose file',
			placeholderText: 'No file chosen',
			realElementClass: 'jcf-real-element',
			extensionPrefixClass: 'jcf-extension-',
			selectedFileBlock: '.jcf-fake-input',
			buttonTextBlock: '.jcf-button-content'
		},
		matchElement: function(element) {
			return element.is('input[type="file"]');
		},
		init: function() {
			this.initStructure();
			this.attachEvents();
			this.refresh();
		},
		initStructure: function() {
			this.doc = $(document);
			this.realElement = $(this.options.element).addClass(this.options.realElementClass);
			this.fakeElement = $(this.options.fakeStructure).insertBefore(this.realElement);
			this.fileNameBlock = this.fakeElement.find(this.options.selectedFileBlock);
			this.buttonTextBlock = this.fakeElement.find(this.options.buttonTextBlock).text(this.options.buttonText);

			this.realElement.appendTo(this.fakeElement).css({
				position: 'absolute',
				opacity: 0
			});
		},
		attachEvents: function() {
			this.realElement.on({
				'jcf-pointerdown': this.onPress,
				change: this.onChange,
				focus: this.onFocus
			});
		},
		onChange: function() {
			this.refresh();
		},
		onFocus: function() {
			this.fakeElement.addClass(this.options.focusClass);
			this.realElement.on('blur', this.onBlur);
		},
		onBlur: function() {
			this.fakeElement.removeClass(this.options.focusClass);
			this.realElement.off('blur', this.onBlur);
		},
		onPress: function() {
			this.fakeElement.addClass(this.options.pressedClass);
			this.doc.on('jcf-pointerup', this.onRelease);
		},
		onRelease: function() {
			this.fakeElement.removeClass(this.options.pressedClass);
			this.doc.off('jcf-pointerup', this.onRelease);
		},
		getFileName: function() {
			var resultFileName = '',
				files = this.realElement.prop('files');

			if (files && files.length) {
				$.each(files, function(index, file) {
					resultFileName += (index > 0 ? ', ' : '') + file.name;
				});
			} else {
				resultFileName = this.realElement.val().replace(/^[\s\S]*(?:\\|\/)([\s\S^\\\/]*)$/g, '$1');
			}

			return resultFileName;
		},
		getFileExtension: function() {
			var fileName = this.realElement.val();
			return fileName.lastIndexOf('.') < 0 ? '' : fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
		},
		updateExtensionClass: function() {
			var currentExtension = this.getFileExtension(),
				currentClassList = this.fakeElement.prop('className'),
				cleanedClassList = currentClassList.replace(new RegExp('(\\s|^)' + this.options.extensionPrefixClass + '[^ ]+','gi'), '');

			this.fakeElement.prop('className', cleanedClassList);
			if (currentExtension) {
				this.fakeElement.addClass(this.options.extensionPrefixClass + currentExtension);
			}
		},
		refresh: function() {
			var selectedFileName = this.getFileName() || this.options.placeholderText;
			this.fakeElement.toggleClass(this.options.disabledClass, this.realElement.is(':disabled'));
			this.fileNameBlock.text(selectedFileName);
			this.updateExtensionClass();
		},
		destroy: function() {
			// reset styles and restore element position
			this.realElement.insertBefore(this.fakeElement).removeClass(this.options.realElementClass).css({
				position: '',
				opacity: ''
			});
			this.fakeElement.remove();

			// remove event handlers
			this.realElement.off({
				'jcf-pointerdown': this.onPress,
				change: this.onChange,
				focus: this.onFocus,
				blur: this.onBlur
			});
			this.doc.off('jcf-pointerup', this.onRelease);
		}
	});

}(jQuery));

/*! http://mths.be/placeholder v2.0.7 by @mathias */
;(function(window, document, $) {

	// Opera Mini v7 doesn’t support placeholder although its DOM seems to indicate so
	var isOperaMini = Object.prototype.toString.call(window.operamini) == '[object OperaMini]';
	var isInputSupported = 'placeholder' in document.createElement('input') && !isOperaMini;
	var isTextareaSupported = 'placeholder' in document.createElement('textarea') && !isOperaMini;
	var prototype = $.fn;
	var valHooks = $.valHooks;
	var propHooks = $.propHooks;
	var hooks;
	var placeholder;

	if (isInputSupported && isTextareaSupported) {

		placeholder = prototype.placeholder = function() {
			return this;
		};

		placeholder.input = placeholder.textarea = true;

	} else {

		placeholder = prototype.placeholder = function() {
			var $this = this;
			$this
				.filter((isInputSupported ? 'textarea' : ':input') + '[placeholder]')
				.not('.placeholder')
				.bind({
					'focus.placeholder': clearPlaceholder,
					'blur.placeholder': setPlaceholder
				})
				.data('placeholder-enabled', true)
				.trigger('blur.placeholder');
			return $this;
		};

		placeholder.input = isInputSupported;
		placeholder.textarea = isTextareaSupported;

		hooks = {
			'get': function(element) {
				var $element = $(element);

				var $passwordInput = $element.data('placeholder-password');
				if ($passwordInput) {
					return $passwordInput[0].value;
				}

				return $element.data('placeholder-enabled') && $element.hasClass('placeholder') ? '' : element.value;
			},
			'set': function(element, value) {
				var $element = $(element);

				var $passwordInput = $element.data('placeholder-password');
				if ($passwordInput) {
					return $passwordInput[0].value = value;
				}

				if (!$element.data('placeholder-enabled')) {
					return element.value = value;
				}
				if (value == '') {
					element.value = value;
					// Issue #56: Setting the placeholder causes problems if the element continues to have focus.
					if (element != safeActiveElement()) {
						// We can't use `triggerHandler` here because of dummy text/password inputs :(
						setPlaceholder.call(element);
					}
				} else if ($element.hasClass('placeholder')) {
					clearPlaceholder.call(element, true, value) || (element.value = value);
				} else {
					element.value = value;
				}
				// `set` can not return `undefined`; see http://jsapi.info/jquery/1.7.1/val#L2363
				return $element;
			}
		};

		if (!isInputSupported) {
			valHooks.input = hooks;
			propHooks.value = hooks;
		}
		if (!isTextareaSupported) {
			valHooks.textarea = hooks;
			propHooks.value = hooks;
		}

		$(function() {
			// Look for forms
			$(document).delegate('form', 'submit.placeholder', function() {
				// Clear the placeholder values so they don't get submitted
				var $inputs = $('.placeholder', this).each(clearPlaceholder);
				setTimeout(function() {
					$inputs.each(setPlaceholder);
				}, 10);
			});
		});

		// Clear placeholder values upon page reload
		$(window).bind('beforeunload.placeholder', function() {
			$('.placeholder').each(function() {
				this.value = '';
			});
		});

	}

	function args(elem) {
		// Return an object of element attributes
		var newAttrs = {};
		var rinlinejQuery = /^jQuery\d+$/;
		$.each(elem.attributes, function(i, attr) {
			if (attr.specified && !rinlinejQuery.test(attr.name)) {
				newAttrs[attr.name] = attr.value;
			}
		});
		return newAttrs;
	}

	function clearPlaceholder(event, value) {
		var input = this;
		var $input = $(input);
		if (input.value == $input.attr('placeholder') && $input.hasClass('placeholder')) {
			if ($input.data('placeholder-password')) {
				$input = $input.hide().next().show().attr('id', $input.removeAttr('id').data('placeholder-id'));
				// If `clearPlaceholder` was called from `$.valHooks.input.set`
				if (event === true) {
					return $input[0].value = value;
				}
				$input.focus();
			} else {
				input.value = '';
				$input.removeClass('placeholder');
				input == safeActiveElement() && input.select();
			}
		}
	}

	function setPlaceholder() {
		var $replacement;
		var input = this;
		var $input = $(input);
		var id = this.id;
		if (input.value == '') {
			if (input.type == 'password') {
				if (!$input.data('placeholder-textinput')) {
					try {
						$replacement = $input.clone().attr({ 'type': 'text' });
					} catch(e) {
						$replacement = $('<input>').attr($.extend(args(this), { 'type': 'text' }));
					}
					$replacement
						.removeAttr('name')
						.data({
							'placeholder-password': $input,
							'placeholder-id': id
						})
						.bind('focus.placeholder', clearPlaceholder);
					$input
						.data({
							'placeholder-textinput': $replacement,
							'placeholder-id': id
						})
						.before($replacement);
				}
				$input = $input.removeAttr('id').hide().prev().attr('id', id).show();
				// Note: `$input[0] != input` now!
			}
			$input.addClass('placeholder');
			$input[0].value = $input.attr('placeholder');
		} else {
			$input.removeClass('placeholder');
		}
	}

	function safeActiveElement() {
		// Avoid IE9 `document.activeElement` of death
		// https://github.com/mathiasbynens/jquery-placeholder/pull/99
		try {
			return document.activeElement;
		} catch (err) {}
	}

}(this, document, jQuery));

/*
 * Add class plugin
 */
jQuery.fn.clickClass = function(opt) {
	var options = jQuery.extend({
		classAdd: 'add-class',
		addToParent: false,
		event: 'click'
	}, opt);

	return this.each(function() {
		var classItem = jQuery(this);
		if(options.addToParent) {
			if(typeof options.addToParent === 'boolean') {
				classItem = classItem.parent();
			} else {
				classItem = classItem.parents('.' + options.addToParent);
			}
		}
		jQuery(this).bind(options.event, function(e) {
			classItem.toggleClass(options.classAdd);
			e.preventDefault();
		});
	});
};

// placeholder class
;(function(){
	var placeholderCollection = [];
	PlaceholderInput = function() {
		this.options = {
			element:null,
			showUntilTyping:false,
			wrapWithElement:false,
			getParentByClass:false,
			showPasswordBullets:false,
			placeholderAttr:'value',
			inputFocusClass:'focus',
			inputActiveClass:'text-active',
			parentFocusClass:'parent-focus',
			parentActiveClass:'parent-active',
			labelFocusClass:'label-focus',
			labelActiveClass:'label-active',
			fakeElementClass:'input-placeholder-text'
		};
		placeholderCollection.push(this);
		this.init.apply(this,arguments);
	};
	PlaceholderInput.refreshAllInputs = function(except) {
		for(var i = 0; i < placeholderCollection.length; i++) {
			if(except !== placeholderCollection[i]) {
				placeholderCollection[i].refreshState();
			}
		}
	};
	PlaceholderInput.replaceByOptions = function(opt) {
		var inputs = [].concat(
			convertToArray(document.getElementsByTagName('input')),
			convertToArray(document.getElementsByTagName('textarea'))
		);
		for(var i = 0; i < inputs.length; i++) {
			if(inputs[i].className.indexOf(opt.skipClass) < 0) {
				var inputType = getInputType(inputs[i]);
				var placeholderValue = inputs[i].getAttribute('placeholder');
				if(opt.focusOnly || (opt.clearInputs && (inputType === 'text' || inputType === 'email' || placeholderValue)) ||
					(opt.clearTextareas && inputType === 'textarea') ||
					(opt.clearPasswords && inputType === 'password')
				) {
					new PlaceholderInput({
						element:inputs[i],
						focusOnly: opt.focusOnly,
						wrapWithElement:opt.wrapWithElement,
						showUntilTyping:opt.showUntilTyping,
						getParentByClass:opt.getParentByClass,
						showPasswordBullets:opt.showPasswordBullets,
						placeholderAttr: placeholderValue ? 'placeholder' : opt.placeholderAttr
					});
				}
			}
		}
	};
	PlaceholderInput.prototype = {
		init: function(opt) {
			this.setOptions(opt);
			if(this.element && this.element.PlaceholderInst) {
				this.element.PlaceholderInst.refreshClasses();
			} else {
				this.element.PlaceholderInst = this;
				if(this.elementType !== 'radio' || this.elementType !== 'checkbox' || this.elementType !== 'file') {
					this.initElements();
					this.attachEvents();
					this.refreshClasses();
				}
			}
		},
		setOptions: function(opt) {
			for(var p in opt) {
				if(opt.hasOwnProperty(p)) {
					this.options[p] = opt[p];
				}
			}
			if(this.options.element) {
				this.element = this.options.element;
				this.elementType = getInputType(this.element);
				if(this.options.focusOnly) {
					this.wrapWithElement = false;
				} else {
					if(this.elementType === 'password' && this.options.showPasswordBullets && !this.options.showUntilTyping) {
						this.wrapWithElement = false;
					} else {
						this.wrapWithElement = this.elementType === 'password' || this.options.showUntilTyping ? true : this.options.wrapWithElement;
					}
				}
				this.setPlaceholderValue(this.options.placeholderAttr);
			}
		},
		setPlaceholderValue: function(attr) {
			this.origValue = (attr === 'value' ? this.element.defaultValue : (this.element.getAttribute(attr) || ''));
			if(this.options.placeholderAttr !== 'value') {
				this.element.removeAttribute(this.options.placeholderAttr);
			}
		},
		initElements: function() {
			// create fake element if needed
			if(this.wrapWithElement) {
				this.fakeElement = document.createElement('span');
				this.fakeElement.className = this.options.fakeElementClass;
				this.fakeElement.innerHTML += this.origValue;
				this.fakeElement.style.color = getStyle(this.element, 'color');
				this.fakeElement.style.position = 'absolute';
				this.element.parentNode.insertBefore(this.fakeElement, this.element);

				if(this.element.value === this.origValue || !this.element.value) {
					this.element.value = '';
					this.togglePlaceholderText(true);
				} else {
					this.togglePlaceholderText(false);
				}
			} else if(!this.element.value && this.origValue.length) {
				this.element.value = this.origValue;
			}
			// get input label
			if(this.element.id) {
				this.labels = document.getElementsByTagName('label');
				for(var i = 0; i < this.labels.length; i++) {
					if(this.labels[i].htmlFor === this.element.id) {
						this.labelFor = this.labels[i];
						break;
					}
				}
			}
			// get parent node (or parentNode by className)
			this.elementParent = this.element.parentNode;
			if(typeof this.options.getParentByClass === 'string') {
				var el = this.element;
				while(el.parentNode) {
					if(hasClass(el.parentNode, this.options.getParentByClass)) {
						this.elementParent = el.parentNode;
						break;
					} else {
						el = el.parentNode;
					}
				}
			}
		},
		attachEvents: function() {
			this.element.onfocus = bindScope(this.focusHandler, this);
			this.element.onblur = bindScope(this.blurHandler, this);
			if(this.options.showUntilTyping) {
				this.element.onkeydown = bindScope(this.typingHandler, this);
				this.element.onpaste = bindScope(this.typingHandler, this);
			}
			if(this.wrapWithElement) this.fakeElement.onclick = bindScope(this.focusSetter, this);
		},
		togglePlaceholderText: function(state) {
			if(!this.element.readOnly && !this.options.focusOnly) {
				if(this.wrapWithElement) {
					this.fakeElement.style.display = state ? '' : 'none';
				} else {
					this.element.value = state ? this.origValue : '';
				}
			}
		},
		focusSetter: function() {
			this.element.focus();
		},
		focusHandler: function() {
			clearInterval(this.checkerInterval);
			this.checkerInterval = setInterval(bindScope(this.intervalHandler,this), 1);
			this.focused = true;
			if(!this.element.value.length || this.element.value === this.origValue) {
				if(!this.options.showUntilTyping) {
					this.togglePlaceholderText(false);
				}
			}
			this.refreshClasses();
		},
		blurHandler: function() {
			clearInterval(this.checkerInterval);
			this.focused = false;
			if(!this.element.value.length || this.element.value === this.origValue) {
				this.togglePlaceholderText(true);
			}
			this.refreshClasses();
			PlaceholderInput.refreshAllInputs(this);
		},
		typingHandler: function() {
			setTimeout(bindScope(function(){
				if(this.element.value.length) {
					this.togglePlaceholderText(false);
					this.refreshClasses();
				}
			},this), 10);
		},
		intervalHandler: function() {
			if(typeof this.tmpValue === 'undefined') {
				this.tmpValue = this.element.value;
			}
			if(this.tmpValue != this.element.value) {
				PlaceholderInput.refreshAllInputs(this);
			}
		},
		refreshState: function() {
			if(this.wrapWithElement) {
				if(this.element.value.length && this.element.value !== this.origValue) {
					this.togglePlaceholderText(false);
				} else if(!this.element.value.length) {
					this.togglePlaceholderText(true);
				}
			}
			this.refreshClasses();
		},
		refreshClasses: function() {
			this.textActive = this.focused || (this.element.value.length && this.element.value !== this.origValue);
			this.setStateClass(this.element, this.options.inputFocusClass,this.focused);
			this.setStateClass(this.elementParent, this.options.parentFocusClass,this.focused);
			this.setStateClass(this.labelFor, this.options.labelFocusClass,this.focused);
			this.setStateClass(this.element, this.options.inputActiveClass, this.textActive);
			this.setStateClass(this.elementParent, this.options.parentActiveClass, this.textActive);
			this.setStateClass(this.labelFor, this.options.labelActiveClass, this.textActive);
		},
		setStateClass: function(el,cls,state) {
			if(!el) return; else if(state) addClass(el,cls); else removeClass(el,cls);
		}
	};

	// utility functions
	function convertToArray(collection) {
		var arr = [];
		for (var i = 0, ref = arr.length = collection.length; i < ref; i++) {
			arr[i] = collection[i];
		}
		return arr;
	}
	function getInputType(input) {
		return (input.type ? input.type : input.tagName).toLowerCase();
	}
	function hasClass(el,cls) {
		return el.className ? el.className.match(new RegExp('(\\s|^)'+cls+'(\\s|$)')) : false;
	}
	function addClass(el,cls) {
		if (!hasClass(el,cls)) el.className += " "+cls;
	}
	function removeClass(el,cls) {
		if (hasClass(el,cls)) {el.className=el.className.replace(new RegExp('(\\s|^)'+cls+'(\\s|$)'),' ');}
	}
	function bindScope(f, scope) {
		return function() {return f.apply(scope, arguments);};
	}
	function getStyle(el, prop) {
		if (document.defaultView && document.defaultView.getComputedStyle) {
			return document.defaultView.getComputedStyle(el, null)[prop];
		} else if (el.currentStyle) {
			return el.currentStyle[prop];
		} else {
			return el.style[prop];
		}
	}
}());

/*
 * jQuery Cycle Carousel plugin
 */
;(function($){
	function ScrollAbsoluteGallery(options) {
		this.options = $.extend({
			activeClass: 'active',
			mask: 'div.slides-mask',
			slider: '>ul',
			slides: '>li',
			btnPrev: '.btn-prev',
			btnNext: '.btn-next',
			pagerLinks: 'ul.pager > li',
			generatePagination: false,
			pagerList: '<ul>',
			pagerListItem: '<li><a href="#"></a></li>',
			pagerListItemText: 'a',
			galleryReadyClass: 'gallery-js-ready',
			currentNumber: 'span.current-num',
			totalNumber: 'span.total-num',
			maskAutoSize: false,
			autoRotation: false,
			pauseOnHover: false,
			stretchSlideToMask: false,
			switchTime: 3000,
			animSpeed: 500,
			handleTouch: true,
			swipeThreshold: 15,
			vertical: false
		}, options);
		this.init();
	}
	ScrollAbsoluteGallery.prototype = {
		init: function() {
			if(this.options.holder) {
				this.findElements();
				this.attachEvents();
				this.makeCallback('onInit', this);
			}
		},
		findElements: function() {
			// find structure elements
			this.holder = $(this.options.holder).addClass(this.options.galleryReadyClass);
			this.mask = this.holder.find(this.options.mask);
			this.slider = this.mask.find(this.options.slider);
			this.slides = this.slider.find(this.options.slides);
			this.btnPrev = this.holder.find(this.options.btnPrev);
			this.btnNext = this.holder.find(this.options.btnNext);

			// slide count display
			this.currentNumber = this.holder.find(this.options.currentNumber);
			this.totalNumber = this.holder.find(this.options.totalNumber);

			// create gallery pagination
			if(typeof this.options.generatePagination === 'string') {
				this.pagerLinks = this.buildPagination();
			} else {
				this.pagerLinks = this.holder.find(this.options.pagerLinks);
			}

			// define index variables
			this.sizeProperty = this.options.vertical ? 'height' : 'width';
			this.positionProperty = this.options.vertical ? 'top' : 'left';
			this.animProperty = this.options.vertical ? 'marginTop' : 'marginLeft';

			this.slideSize = this.slides[this.sizeProperty]();
			this.currentIndex = 0;
			this.prevIndex = 0;

			// reposition elements
			this.options.maskAutoSize = this.options.vertical ? false : this.options.maskAutoSize;
			if(this.options.vertical) {
				this.mask.css({
					height: this.slides.innerHeight()
				});
			}
			if(this.options.maskAutoSize){
				this.mask.css({
					height: this.slider.height()
				});
			}
			this.slider.css({
				position: 'relative',
				height: this.options.vertical ? this.slideSize * this.slides.length : '100%'
			});
			this.slides.css({
				position: 'absolute'
			}).css(this.positionProperty, -9999).eq(this.currentIndex).css(this.positionProperty, 0);
			this.refreshState();
		},
		buildPagination: function() {
			var pagerLinks = $();
			if(!this.pagerHolder) {
				this.pagerHolder = this.holder.find(this.options.generatePagination);
			}
			if(this.pagerHolder.length) {
				this.pagerHolder.empty();
				this.pagerList = $(this.options.pagerList).appendTo(this.pagerHolder);
				for(var i = 0; i < this.slides.length; i++) {
					$(this.options.pagerListItem).appendTo(this.pagerList).find(this.options.pagerListItemText).text(i+1);
				}
				pagerLinks = this.pagerList.children();
			}
			return pagerLinks;
		},
		attachEvents: function() {
			// attach handlers
			var self = this;
			if(this.btnPrev.length) {
				this.btnPrevHandler = function(e) {
					e.preventDefault();
					self.prevSlide();
				};
				this.btnPrev.click(this.btnPrevHandler);
			}
			if(this.btnNext.length) {
				this.btnNextHandler = function(e) {
					e.preventDefault();
					self.nextSlide();
				};
				this.btnNext.click(this.btnNextHandler);
			}
			if(this.pagerLinks.length) {
				this.pagerLinksHandler = function(e) {
					e.preventDefault();
					self.numSlide(self.pagerLinks.index(e.currentTarget));
				};
				this.pagerLinks.click(this.pagerLinksHandler);
			}

			// handle autorotation pause on hover
			if(this.options.pauseOnHover) {
				this.hoverHandler = function() {
					clearTimeout(self.timer);
				};
				this.leaveHandler = function() {
					self.autoRotate();
				};
				this.holder.bind({mouseenter: this.hoverHandler, mouseleave: this.leaveHandler});
			}

			// handle holder and slides dimensions
			this.resizeHandler = function() {
				if(!self.animating) {
					if(self.options.stretchSlideToMask) {
						self.resizeSlides();
					}
					self.resizeHolder();
					self.setSlidesPosition(self.currentIndex);
				}
			};
			$(window).bind('load resize orientationchange', this.resizeHandler);
			if(self.options.stretchSlideToMask) {
				self.resizeSlides();
			}

			// handle swipe on mobile devices
			if(this.options.handleTouch && window.Hammer && this.mask.length && this.slides.length > 1 && isTouchDevice) {
				this.swipeHandler = new Hammer.Manager(this.mask[0]);
				this.swipeHandler.add(new Hammer.Pan({
					direction: self.options.vertical ? Hammer.DIRECTION_VERTICAL : Hammer.DIRECTION_HORIZONTAL,
					threshold: self.options.swipeThreshold
				}));

				this.swipeHandler.on('panstart', function() {
					if(self.animating) {
						self.swipeHandler.stop();
					} else {
						clearTimeout(self.timer);
					}
				}).on('panmove', function(e) {
					self.swipeOffset = -self.slideSize + e[self.options.vertical ? 'deltaY' : 'deltaX'];
					self.slider.css(self.animProperty, self.swipeOffset);
					clearTimeout(self.timer);
				}).on('panend', function(e) {
					if(e.distance > self.options.swipeThreshold) {
						if(e.offsetDirection === Hammer.DIRECTION_RIGHT || e.offsetDirection === Hammer.DIRECTION_DOWN) {
							self.nextSlide();
						} else {
							self.prevSlide();
						}
					} else {
						var tmpObj = {};
						tmpObj[self.animProperty] = -self.slideSize;
						self.slider.animate(tmpObj, {duration: self.options.animSpeed});
						self.autoRotate();
					}
					self.swipeOffset = 0;
				});
			}

			// start autorotation
			this.autoRotate();
			this.resizeHolder();
			this.setSlidesPosition(this.currentIndex);
		},
		resizeSlides: function() {
			this.slideSize = this.mask[this.options.vertical ? 'height' : 'width']();
			this.slides.css(this.sizeProperty, this.slideSize);
		},
		resizeHolder: function() {
			if(this.options.maskAutoSize) {
				this.mask.css({
					height: this.slides.eq(this.currentIndex).outerHeight(true)
				});
			}
		},
		prevSlide: function() {
			if(!this.animating && this.slides.length > 1) {
				this.direction = -1;
				this.prevIndex = this.currentIndex;
				if(this.currentIndex > 0) this.currentIndex--;
				else this.currentIndex = this.slides.length - 1;
				this.switchSlide();
			}
		},
		nextSlide: function(fromAutoRotation) {
			if(!this.animating && this.slides.length > 1) {
				this.direction = 1;
				this.prevIndex = this.currentIndex;
				if(this.currentIndex < this.slides.length - 1) this.currentIndex++;
				else this.currentIndex = 0;
				this.switchSlide();
			}
		},
		numSlide: function(c) {
			if(!this.animating && this.currentIndex !== c && this.slides.length > 1) {
				this.direction = c > this.currentIndex ? 1 : -1;
				this.prevIndex = this.currentIndex;
				this.currentIndex = c;
				this.switchSlide();
			}
		},
		preparePosition: function() {
			// prepare slides position before animation
			this.setSlidesPosition(this.prevIndex, this.direction < 0 ? this.currentIndex : null, this.direction > 0 ? this.currentIndex : null, this.direction);
		},
		setSlidesPosition: function(index, slideLeft, slideRight, direction) {
			// reposition holder and nearest slides
			if(this.slides.length > 1) {
				var prevIndex = (typeof slideLeft === 'number' ? slideLeft : index > 0 ? index - 1 : this.slides.length - 1);
				var nextIndex = (typeof slideRight === 'number' ? slideRight : index < this.slides.length - 1 ? index + 1 : 0);

				this.slider.css(this.animProperty, this.swipeOffset ? this.swipeOffset : -this.slideSize);
				this.slides.css(this.positionProperty, -9999).eq(index).css(this.positionProperty, this.slideSize);
				if(prevIndex === nextIndex && typeof direction === 'number') {
					var calcOffset = direction > 0 ? this.slideSize*2 : 0;
					this.slides.eq(nextIndex).css(this.positionProperty, calcOffset);
				} else {
					this.slides.eq(prevIndex).css(this.positionProperty, 0);
					this.slides.eq(nextIndex).css(this.positionProperty, this.slideSize*2);
				}
			}
		},
		switchSlide: function() {
			// prepare positions and calculate offset
			var self = this;
			var oldSlide = this.slides.eq(this.prevIndex);
			var newSlide = this.slides.eq(this.currentIndex);
			this.animating = true;

			// resize mask to fit slide
			if(this.options.maskAutoSize) {
				this.mask.animate({
					height: newSlide.outerHeight(true)
				}, {
					duration: this.options.animSpeed
				});
			}

			// start animation
			var animProps = {};
			animProps[this.animProperty] = this.direction > 0 ? -this.slideSize*2 : 0;
			this.preparePosition();
			this.slider.animate(animProps,{duration:this.options.animSpeed, complete:function() {
				self.setSlidesPosition(self.currentIndex);

				// start autorotation
				self.animating = false;
				self.autoRotate();

				// onchange callback
				self.makeCallback('onChange', self);
			}});

			// refresh classes
			this.refreshState();

			// onchange callback
			this.makeCallback('onBeforeChange', this);
		},
		refreshState: function(initial) {
			// slide change function
			this.slides.removeClass(this.options.activeClass).eq(this.currentIndex).addClass(this.options.activeClass);
			this.pagerLinks.removeClass(this.options.activeClass).eq(this.currentIndex).addClass(this.options.activeClass);

			// display current slide number
			this.currentNumber.html(this.currentIndex + 1);
			this.totalNumber.html(this.slides.length);

			// add class if not enough slides
			this.holder.toggleClass('not-enough-slides', this.slides.length === 1);
		},
		autoRotate: function() {
			var self = this;
			clearTimeout(this.timer);
			if(this.options.autoRotation) {
				this.timer = setTimeout(function() {
					self.nextSlide();
				}, this.options.switchTime);
			}
		},
		makeCallback: function(name) {
			if(typeof this.options[name] === 'function') {
				var args = Array.prototype.slice.call(arguments);
				args.shift();
				this.options[name].apply(this, args);
			}
		},
		destroy: function() {
			// destroy handler
			this.btnPrev.unbind('click', this.btnPrevHandler);
			this.btnNext.unbind('click', this.btnNextHandler);
			this.pagerLinks.unbind('click', this.pagerLinksHandler);
			this.holder.unbind('mouseenter', this.hoverHandler);
			this.holder.unbind('mouseleave', this.leaveHandler);
			$(window).unbind('load resize orientationchange', this.resizeHandler);
			clearTimeout(this.timer);

			// destroy swipe handler
			if(this.swipeHandler) {
				this.swipeHandler.destroy();
			}

			// remove inline styles, classes and pagination
			this.holder.removeClass(this.options.galleryReadyClass);
			this.slider.add(this.slides).removeAttr('style');
			if(typeof this.options.generatePagination === 'string') {
				this.pagerHolder.empty();
			}
		}
	};

	// detect device type
	var isTouchDevice = /Windows Phone/.test(navigator.userAgent) || ('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch;

	// jquery plugin
	$.fn.scrollAbsoluteGallery = function(opt){
		return this.each(function(){
			$(this).data('ScrollAbsoluteGallery', new ScrollAbsoluteGallery($.extend(opt,{holder:this})));
		});
	};
}(jQuery));


/*!
* FitVids 1.0.3
*
* Copyright 2013, Chris Coyier - http://css-tricks.com + Dave Rupert - http://daverupert.com
* Credit to Thierry Koblentz - http://www.alistapart.com/articles/creating-intrinsic-ratios-for-video/
* Released under the WTFPL license - http://sam.zoy.org/wtfpl/
*
* Date: Thu Sept 01 18:00:00 2011 -0500
*/
;(function(a){a.fn.fitVids=function(b){var c={customSelector:null};if(!document.getElementById("fit-vids-style")){var f=document.createElement("div"),d=document.getElementsByTagName("base")[0]||document.getElementsByTagName("script")[0],e="&shy;<style>.fluid-width-video-wrapper{width:100%;position:relative;padding:0;}.fluid-width-video-wrapper iframe,.fluid-width-video-wrapper object,.fluid-width-video-wrapper embed {position:absolute;top:0;left:0;width:100%;height:100%;}</style>";f.className="fit-vids-style";f.id="fit-vids-style";f.style.display="none";f.innerHTML=e;d.parentNode.insertBefore(f,d)}if(b){a.extend(c,b)}return this.each(function(){var g=["iframe[src*='player.vimeo.com']","iframe[src*='youtube.com']","iframe[src*='youtube-nocookie.com']","iframe[src*='kickstarter.com'][src*='video.html']","object","embed"];if(c.customSelector){g.push(c.customSelector)}var h=a(this).find(g.join(","));h=h.not("object object");h.each(function(){var m=a(this);if(this.tagName.toLowerCase()==="embed"&&m.parent("object").length||m.parent(".fluid-width-video-wrapper").length){return}var i=(this.tagName.toLowerCase()==="object"||(m.attr("height")&&!isNaN(parseInt(m.attr("height"),10))))?parseInt(m.attr("height"),10):m.height(),j=!isNaN(parseInt(m.attr("width"),10))?parseInt(m.attr("width"),10):m.width(),k=i/j;if(!m.attr("id")){var l="fitvid"+Math.floor(Math.random()*999999);m.attr("id",l)}m.wrap('<div class="fluid-width-video-wrapper"></div>').parent(".fluid-width-video-wrapper").css("padding-top",(k*100)+"%");m.removeAttr("height").removeAttr("width")})})}})(window.jQuery||window.Zepto);


/*! Hammer.JS - v2.0.4 - 2014-09-28
 * http://hammerjs.github.io/
 *
 * Copyright (c) 2014 Jorik Tangelder;
 * Licensed under the MIT license */
if(Object.create){!function(a,b,c,d){"use strict";function e(a,b,c){return setTimeout(k(a,c),b)}function f(a,b,c){return Array.isArray(a)?(g(a,c[b],c),!0):!1}function g(a,b,c){var e;if(a)if(a.forEach)a.forEach(b,c);else if(a.length!==d)for(e=0;e<a.length;)b.call(c,a[e],e,a),e++;else for(e in a)a.hasOwnProperty(e)&&b.call(c,a[e],e,a)}function h(a,b,c){for(var e=Object.keys(b),f=0;f<e.length;)(!c||c&&a[e[f]]===d)&&(a[e[f]]=b[e[f]]),f++;return a}function i(a,b){return h(a,b,!0)}function j(a,b,c){var d,e=b.prototype;d=a.prototype=Object.create(e),d.constructor=a,d._super=e,c&&h(d,c)}function k(a,b){return function(){return a.apply(b,arguments)}}function l(a,b){return typeof a==kb?a.apply(b?b[0]||d:d,b):a}function m(a,b){return a===d?b:a}function n(a,b,c){g(r(b),function(b){a.addEventListener(b,c,!1)})}function o(a,b,c){g(r(b),function(b){a.removeEventListener(b,c,!1)})}function p(a,b){for(;a;){if(a==b)return!0;a=a.parentNode}return!1}function q(a,b){return a.indexOf(b)>-1}function r(a){return a.trim().split(/\s+/g)}function s(a,b,c){if(a.indexOf&&!c)return a.indexOf(b);for(var d=0;d<a.length;){if(c&&a[d][c]==b||!c&&a[d]===b)return d;d++}return-1}function t(a){return Array.prototype.slice.call(a,0)}function u(a,b,c){for(var d=[],e=[],f=0;f<a.length;){var g=b?a[f][b]:a[f];s(e,g)<0&&d.push(a[f]),e[f]=g,f++}return c&&(d=b?d.sort(function(a,c){return a[b]>c[b]}):d.sort()),d}function v(a,b){for(var c,e,f=b[0].toUpperCase()+b.slice(1),g=0;g<ib.length;){if(c=ib[g],e=c?c+f:b,e in a)return e;g++}return d}function w(){return ob++}function x(a){var b=a.ownerDocument;return b.defaultView||b.parentWindow}function y(a,b){var c=this;this.manager=a,this.callback=b,this.element=a.element,this.target=a.options.inputTarget,this.domHandler=function(b){l(a.options.enable,[a])&&c.handler(b)},this.init()}function z(a){var b,c=a.options.inputClass;return new(b=c?c:rb?N:sb?Q:qb?S:M)(a,A)}function A(a,b,c){var d=c.pointers.length,e=c.changedPointers.length,f=b&yb&&d-e===0,g=b&(Ab|Bb)&&d-e===0;c.isFirst=!!f,c.isFinal=!!g,f&&(a.session={}),c.eventType=b,B(a,c),a.emit("hammer.input",c),a.recognize(c),a.session.prevInput=c}function B(a,b){var c=a.session,d=b.pointers,e=d.length;c.firstInput||(c.firstInput=E(b)),e>1&&!c.firstMultiple?c.firstMultiple=E(b):1===e&&(c.firstMultiple=!1);var f=c.firstInput,g=c.firstMultiple,h=g?g.center:f.center,i=b.center=F(d);b.timeStamp=nb(),b.deltaTime=b.timeStamp-f.timeStamp,b.angle=J(h,i),b.distance=I(h,i),C(c,b),b.offsetDirection=H(b.deltaX,b.deltaY),b.scale=g?L(g.pointers,d):1,b.rotation=g?K(g.pointers,d):0,D(c,b);var j=a.element;p(b.srcEvent.target,j)&&(j=b.srcEvent.target),b.target=j}function C(a,b){var c=b.center,d=a.offsetDelta||{},e=a.prevDelta||{},f=a.prevInput||{};(b.eventType===yb||f.eventType===Ab)&&(e=a.prevDelta={x:f.deltaX||0,y:f.deltaY||0},d=a.offsetDelta={x:c.x,y:c.y}),b.deltaX=e.x+(c.x-d.x),b.deltaY=e.y+(c.y-d.y)}function D(a,b){var c,e,f,g,h=a.lastInterval||b,i=b.timeStamp-h.timeStamp;if(b.eventType!=Bb&&(i>xb||h.velocity===d)){var j=h.deltaX-b.deltaX,k=h.deltaY-b.deltaY,l=G(i,j,k);e=l.x,f=l.y,c=mb(l.x)>mb(l.y)?l.x:l.y,g=H(j,k),a.lastInterval=b}else c=h.velocity,e=h.velocityX,f=h.velocityY,g=h.direction;b.velocity=c,b.velocityX=e,b.velocityY=f,b.direction=g}function E(a){for(var b=[],c=0;c<a.pointers.length;)b[c]={clientX:lb(a.pointers[c].clientX),clientY:lb(a.pointers[c].clientY)},c++;return{timeStamp:nb(),pointers:b,center:F(b),deltaX:a.deltaX,deltaY:a.deltaY}}function F(a){var b=a.length;if(1===b)return{x:lb(a[0].clientX),y:lb(a[0].clientY)};for(var c=0,d=0,e=0;b>e;)c+=a[e].clientX,d+=a[e].clientY,e++;return{x:lb(c/b),y:lb(d/b)}}function G(a,b,c){return{x:b/a||0,y:c/a||0}}function H(a,b){return a===b?Cb:mb(a)>=mb(b)?a>0?Db:Eb:b>0?Fb:Gb}function I(a,b,c){c||(c=Kb);var d=b[c[0]]-a[c[0]],e=b[c[1]]-a[c[1]];return Math.sqrt(d*d+e*e)}function J(a,b,c){c||(c=Kb);var d=b[c[0]]-a[c[0]],e=b[c[1]]-a[c[1]];return 180*Math.atan2(e,d)/Math.PI}function K(a,b){return J(b[1],b[0],Lb)-J(a[1],a[0],Lb)}function L(a,b){return I(b[0],b[1],Lb)/I(a[0],a[1],Lb)}function M(){this.evEl=Nb,this.evWin=Ob,this.allow=!0,this.pressed=!1,y.apply(this,arguments)}function N(){this.evEl=Rb,this.evWin=Sb,y.apply(this,arguments),this.store=this.manager.session.pointerEvents=[]}function O(){this.evTarget=Ub,this.evWin=Vb,this.started=!1,y.apply(this,arguments)}function P(a,b){var c=t(a.touches),d=t(a.changedTouches);return b&(Ab|Bb)&&(c=u(c.concat(d),"identifier",!0)),[c,d]}function Q(){this.evTarget=Xb,this.targetIds={},y.apply(this,arguments)}function R(a,b){var c=t(a.touches),d=this.targetIds;if(b&(yb|zb)&&1===c.length)return d[c[0].identifier]=!0,[c,c];var e,f,g=t(a.changedTouches),h=[],i=this.target;if(f=c.filter(function(a){return p(a.target,i)}),b===yb)for(e=0;e<f.length;)d[f[e].identifier]=!0,e++;for(e=0;e<g.length;)d[g[e].identifier]&&h.push(g[e]),b&(Ab|Bb)&&delete d[g[e].identifier],e++;return h.length?[u(f.concat(h),"identifier",!0),h]:void 0}function S(){y.apply(this,arguments);var a=k(this.handler,this);this.touch=new Q(this.manager,a),this.mouse=new M(this.manager,a)}function T(a,b){this.manager=a,this.set(b)}function U(a){if(q(a,bc))return bc;var b=q(a,cc),c=q(a,dc);return b&&c?cc+" "+dc:b||c?b?cc:dc:q(a,ac)?ac:_b}function V(a){this.id=w(),this.manager=null,this.options=i(a||{},this.defaults),this.options.enable=m(this.options.enable,!0),this.state=ec,this.simultaneous={},this.requireFail=[]}function W(a){return a&jc?"cancel":a&hc?"end":a&gc?"move":a&fc?"start":""}function X(a){return a==Gb?"down":a==Fb?"up":a==Db?"left":a==Eb?"right":""}function Y(a,b){var c=b.manager;return c?c.get(a):a}function Z(){V.apply(this,arguments)}function $(){Z.apply(this,arguments),this.pX=null,this.pY=null}function _(){Z.apply(this,arguments)}function ab(){V.apply(this,arguments),this._timer=null,this._input=null}function bb(){Z.apply(this,arguments)}function cb(){Z.apply(this,arguments)}function db(){V.apply(this,arguments),this.pTime=!1,this.pCenter=!1,this._timer=null,this._input=null,this.count=0}function eb(a,b){return b=b||{},b.recognizers=m(b.recognizers,eb.defaults.preset),new fb(a,b)}function fb(a,b){b=b||{},this.options=i(b,eb.defaults),this.options.inputTarget=this.options.inputTarget||a,this.handlers={},this.session={},this.recognizers=[],this.element=a,this.input=z(this),this.touchAction=new T(this,this.options.touchAction),gb(this,!0),g(b.recognizers,function(a){var b=this.add(new a[0](a[1]));a[2]&&b.recognizeWith(a[2]),a[3]&&b.requireFailure(a[3])},this)}function gb(a,b){var c=a.element;g(a.options.cssProps,function(a,d){c.style[v(c.style,d)]=b?a:""})}function hb(a,c){var d=b.createEvent("Event");d.initEvent(a,!0,!0),d.gesture=c,c.target.dispatchEvent(d)}var ib=["","webkit","moz","MS","ms","o"],jb=b.createElement("div"),kb="function",lb=Math.round,mb=Math.abs,nb=Date.now,ob=1,pb=/mobile|tablet|ip(ad|hone|od)|android/i,qb="ontouchstart"in a,rb=v(a,"PointerEvent")!==d,sb=qb&&pb.test(navigator.userAgent),tb="touch",ub="pen",vb="mouse",wb="kinect",xb=25,yb=1,zb=2,Ab=4,Bb=8,Cb=1,Db=2,Eb=4,Fb=8,Gb=16,Hb=Db|Eb,Ib=Fb|Gb,Jb=Hb|Ib,Kb=["x","y"],Lb=["clientX","clientY"];y.prototype={handler:function(){},init:function(){this.evEl&&n(this.element,this.evEl,this.domHandler),this.evTarget&&n(this.target,this.evTarget,this.domHandler),this.evWin&&n(x(this.element),this.evWin,this.domHandler)},destroy:function(){this.evEl&&o(this.element,this.evEl,this.domHandler),this.evTarget&&o(this.target,this.evTarget,this.domHandler),this.evWin&&o(x(this.element),this.evWin,this.domHandler)}};var Mb={mousedown:yb,mousemove:zb,mouseup:Ab},Nb="mousedown",Ob="mousemove mouseup";j(M,y,{handler:function(a){var b=Mb[a.type];b&yb&&0===a.button&&(this.pressed=!0),b&zb&&1!==a.which&&(b=Ab),this.pressed&&this.allow&&(b&Ab&&(this.pressed=!1),this.callback(this.manager,b,{pointers:[a],changedPointers:[a],pointerType:vb,srcEvent:a}))}});var Pb={pointerdown:yb,pointermove:zb,pointerup:Ab,pointercancel:Bb,pointerout:Bb},Qb={2:tb,3:ub,4:vb,5:wb},Rb="pointerdown",Sb="pointermove pointerup pointercancel";a.MSPointerEvent&&(Rb="MSPointerDown",Sb="MSPointerMove MSPointerUp MSPointerCancel"),j(N,y,{handler:function(a){var b=this.store,c=!1,d=a.type.toLowerCase().replace("ms",""),e=Pb[d],f=Qb[a.pointerType]||a.pointerType,g=f==tb,h=s(b,a.pointerId,"pointerId");e&yb&&(0===a.button||g)?0>h&&(b.push(a),h=b.length-1):e&(Ab|Bb)&&(c=!0),0>h||(b[h]=a,this.callback(this.manager,e,{pointers:b,changedPointers:[a],pointerType:f,srcEvent:a}),c&&b.splice(h,1))}});var Tb={touchstart:yb,touchmove:zb,touchend:Ab,touchcancel:Bb},Ub="touchstart",Vb="touchstart touchmove touchend touchcancel";j(O,y,{handler:function(a){var b=Tb[a.type];if(b===yb&&(this.started=!0),this.started){var c=P.call(this,a,b);b&(Ab|Bb)&&c[0].length-c[1].length===0&&(this.started=!1),this.callback(this.manager,b,{pointers:c[0],changedPointers:c[1],pointerType:tb,srcEvent:a})}}});var Wb={touchstart:yb,touchmove:zb,touchend:Ab,touchcancel:Bb},Xb="touchstart touchmove touchend touchcancel";j(Q,y,{handler:function(a){var b=Wb[a.type],c=R.call(this,a,b);c&&this.callback(this.manager,b,{pointers:c[0],changedPointers:c[1],pointerType:tb,srcEvent:a})}}),j(S,y,{handler:function(a,b,c){var d=c.pointerType==tb,e=c.pointerType==vb;if(d)this.mouse.allow=!1;else if(e&&!this.mouse.allow)return;b&(Ab|Bb)&&(this.mouse.allow=!0),this.callback(a,b,c)},destroy:function(){this.touch.destroy(),this.mouse.destroy()}});var Yb=v(jb.style,"touchAction"),Zb=Yb!==d,$b="compute",_b="auto",ac="manipulation",bc="none",cc="pan-x",dc="pan-y";T.prototype={set:function(a){a==$b&&(a=this.compute()),Zb&&(this.manager.element.style[Yb]=a),this.actions=a.toLowerCase().trim()},update:function(){this.set(this.manager.options.touchAction)},compute:function(){var a=[];return g(this.manager.recognizers,function(b){l(b.options.enable,[b])&&(a=a.concat(b.getTouchAction()))}),U(a.join(" "))},preventDefaults:function(a){if(!Zb){var b=a.srcEvent,c=a.offsetDirection;if(this.manager.session.prevented)return void b.preventDefault();var d=this.actions,e=q(d,bc),f=q(d,dc),g=q(d,cc);return e||f&&c&Hb||g&&c&Ib?this.preventSrc(b):void 0}},preventSrc:function(a){this.manager.session.prevented=!0,a.preventDefault()}};var ec=1,fc=2,gc=4,hc=8,ic=hc,jc=16,kc=32;V.prototype={defaults:{},set:function(a){return h(this.options,a),this.manager&&this.manager.touchAction.update(),this},recognizeWith:function(a){if(f(a,"recognizeWith",this))return this;var b=this.simultaneous;return a=Y(a,this),b[a.id]||(b[a.id]=a,a.recognizeWith(this)),this},dropRecognizeWith:function(a){return f(a,"dropRecognizeWith",this)?this:(a=Y(a,this),delete this.simultaneous[a.id],this)},requireFailure:function(a){if(f(a,"requireFailure",this))return this;var b=this.requireFail;return a=Y(a,this),-1===s(b,a)&&(b.push(a),a.requireFailure(this)),this},dropRequireFailure:function(a){if(f(a,"dropRequireFailure",this))return this;a=Y(a,this);var b=s(this.requireFail,a);return b>-1&&this.requireFail.splice(b,1),this},hasRequireFailures:function(){return this.requireFail.length>0},canRecognizeWith:function(a){return!!this.simultaneous[a.id]},emit:function(a){function b(b){c.manager.emit(c.options.event+(b?W(d):""),a)}var c=this,d=this.state;hc>d&&b(!0),b(),d>=hc&&b(!0)},tryEmit:function(a){return this.canEmit()?this.emit(a):void(this.state=kc)},canEmit:function(){for(var a=0;a<this.requireFail.length;){if(!(this.requireFail[a].state&(kc|ec)))return!1;a++}return!0},recognize:function(a){var b=h({},a);return l(this.options.enable,[this,b])?(this.state&(ic|jc|kc)&&(this.state=ec),this.state=this.process(b),void(this.state&(fc|gc|hc|jc)&&this.tryEmit(b))):(this.reset(),void(this.state=kc))},process:function(){},getTouchAction:function(){},reset:function(){}},j(Z,V,{defaults:{pointers:1},attrTest:function(a){var b=this.options.pointers;return 0===b||a.pointers.length===b},process:function(a){var b=this.state,c=a.eventType,d=b&(fc|gc),e=this.attrTest(a);return d&&(c&Bb||!e)?b|jc:d||e?c&Ab?b|hc:b&fc?b|gc:fc:kc}}),j($,Z,{defaults:{event:"pan",threshold:10,pointers:1,direction:Jb},getTouchAction:function(){var a=this.options.direction,b=[];return a&Hb&&b.push(dc),a&Ib&&b.push(cc),b},directionTest:function(a){var b=this.options,c=!0,d=a.distance,e=a.direction,f=a.deltaX,g=a.deltaY;return e&b.direction||(b.direction&Hb?(e=0===f?Cb:0>f?Db:Eb,c=f!=this.pX,d=Math.abs(a.deltaX)):(e=0===g?Cb:0>g?Fb:Gb,c=g!=this.pY,d=Math.abs(a.deltaY))),a.direction=e,c&&d>b.threshold&&e&b.direction},attrTest:function(a){return Z.prototype.attrTest.call(this,a)&&(this.state&fc||!(this.state&fc)&&this.directionTest(a))},emit:function(a){this.pX=a.deltaX,this.pY=a.deltaY;var b=X(a.direction);b&&this.manager.emit(this.options.event+b,a),this._super.emit.call(this,a)}}),j(_,Z,{defaults:{event:"pinch",threshold:0,pointers:2},getTouchAction:function(){return[bc]},attrTest:function(a){return this._super.attrTest.call(this,a)&&(Math.abs(a.scale-1)>this.options.threshold||this.state&fc)},emit:function(a){if(this._super.emit.call(this,a),1!==a.scale){var b=a.scale<1?"in":"out";this.manager.emit(this.options.event+b,a)}}}),j(ab,V,{defaults:{event:"press",pointers:1,time:500,threshold:5},getTouchAction:function(){return[_b]},process:function(a){var b=this.options,c=a.pointers.length===b.pointers,d=a.distance<b.threshold,f=a.deltaTime>b.time;if(this._input=a,!d||!c||a.eventType&(Ab|Bb)&&!f)this.reset();else if(a.eventType&yb)this.reset(),this._timer=e(function(){this.state=ic,this.tryEmit()},b.time,this);else if(a.eventType&Ab)return ic;return kc},reset:function(){clearTimeout(this._timer)},emit:function(a){this.state===ic&&(a&&a.eventType&Ab?this.manager.emit(this.options.event+"up",a):(this._input.timeStamp=nb(),this.manager.emit(this.options.event,this._input)))}}),j(bb,Z,{defaults:{event:"rotate",threshold:0,pointers:2},getTouchAction:function(){return[bc]},attrTest:function(a){return this._super.attrTest.call(this,a)&&(Math.abs(a.rotation)>this.options.threshold||this.state&fc)}}),j(cb,Z,{defaults:{event:"swipe",threshold:10,velocity:.65,direction:Hb|Ib,pointers:1},getTouchAction:function(){return $.prototype.getTouchAction.call(this)},attrTest:function(a){var b,c=this.options.direction;return c&(Hb|Ib)?b=a.velocity:c&Hb?b=a.velocityX:c&Ib&&(b=a.velocityY),this._super.attrTest.call(this,a)&&c&a.direction&&a.distance>this.options.threshold&&mb(b)>this.options.velocity&&a.eventType&Ab},emit:function(a){var b=X(a.direction);b&&this.manager.emit(this.options.event+b,a),this.manager.emit(this.options.event,a)}}),j(db,V,{defaults:{event:"tap",pointers:1,taps:1,interval:300,time:250,threshold:2,posThreshold:10},getTouchAction:function(){return[ac]},process:function(a){var b=this.options,c=a.pointers.length===b.pointers,d=a.distance<b.threshold,f=a.deltaTime<b.time;if(this.reset(),a.eventType&yb&&0===this.count)return this.failTimeout();if(d&&f&&c){if(a.eventType!=Ab)return this.failTimeout();var g=this.pTime?a.timeStamp-this.pTime<b.interval:!0,h=!this.pCenter||I(this.pCenter,a.center)<b.posThreshold;this.pTime=a.timeStamp,this.pCenter=a.center,h&&g?this.count+=1:this.count=1,this._input=a;var i=this.count%b.taps;if(0===i)return this.hasRequireFailures()?(this._timer=e(function(){this.state=ic,this.tryEmit()},b.interval,this),fc):ic}return kc},failTimeout:function(){return this._timer=e(function(){this.state=kc},this.options.interval,this),kc},reset:function(){clearTimeout(this._timer)},emit:function(){this.state==ic&&(this._input.tapCount=this.count,this.manager.emit(this.options.event,this._input))}}),eb.VERSION="2.0.4",eb.defaults={domEvents:!1,touchAction:$b,enable:!0,inputTarget:null,inputClass:null,preset:[[bb,{enable:!1}],[_,{enable:!1},["rotate"]],[cb,{direction:Hb}],[$,{direction:Hb},["swipe"]],[db],[db,{event:"doubletap",taps:2},["tap"]],[ab]],cssProps:{userSelect:"none",touchSelect:"none",touchCallout:"none",contentZooming:"none",userDrag:"none",tapHighlightColor:"rgba(0,0,0,0)"}};var lc=1,mc=2;fb.prototype={set:function(a){return h(this.options,a),a.touchAction&&this.touchAction.update(),a.inputTarget&&(this.input.destroy(),this.input.target=a.inputTarget,this.input.init()),this},stop:function(a){this.session.stopped=a?mc:lc},recognize:function(a){var b=this.session;if(!b.stopped){this.touchAction.preventDefaults(a);var c,d=this.recognizers,e=b.curRecognizer;(!e||e&&e.state&ic)&&(e=b.curRecognizer=null);for(var f=0;f<d.length;)c=d[f],b.stopped===mc||e&&c!=e&&!c.canRecognizeWith(e)?c.reset():c.recognize(a),!e&&c.state&(fc|gc|hc)&&(e=b.curRecognizer=c),f++}},get:function(a){if(a instanceof V)return a;for(var b=this.recognizers,c=0;c<b.length;c++)if(b[c].options.event==a)return b[c];return null},add:function(a){if(f(a,"add",this))return this;var b=this.get(a.options.event);return b&&this.remove(b),this.recognizers.push(a),a.manager=this,this.touchAction.update(),a},remove:function(a){if(f(a,"remove",this))return this;var b=this.recognizers;return a=this.get(a),b.splice(s(b,a),1),this.touchAction.update(),this},on:function(a,b){var c=this.handlers;return g(r(a),function(a){c[a]=c[a]||[],c[a].push(b)}),this},off:function(a,b){var c=this.handlers;return g(r(a),function(a){b?c[a].splice(s(c[a],b),1):delete c[a]}),this},emit:function(a,b){this.options.domEvents&&hb(a,b);var c=this.handlers[a]&&this.handlers[a].slice();if(c&&c.length){b.type=a,b.preventDefault=function(){b.srcEvent.preventDefault()};for(var d=0;d<c.length;)c[d](b),d++}},destroy:function(){this.element&&gb(this,!1),this.handlers={},this.session={},this.input.destroy(),this.element=null}},h(eb,{INPUT_START:yb,INPUT_MOVE:zb,INPUT_END:Ab,INPUT_CANCEL:Bb,STATE_POSSIBLE:ec,STATE_BEGAN:fc,STATE_CHANGED:gc,STATE_ENDED:hc,STATE_RECOGNIZED:ic,STATE_CANCELLED:jc,STATE_FAILED:kc,DIRECTION_NONE:Cb,DIRECTION_LEFT:Db,DIRECTION_RIGHT:Eb,DIRECTION_UP:Fb,DIRECTION_DOWN:Gb,DIRECTION_HORIZONTAL:Hb,DIRECTION_VERTICAL:Ib,DIRECTION_ALL:Jb,Manager:fb,Input:y,TouchAction:T,TouchInput:Q,MouseInput:M,PointerEventInput:N,TouchMouseInput:S,SingleTouchInput:O,Recognizer:V,AttrRecognizer:Z,Tap:db,Pan:$,Swipe:cb,Pinch:_,Rotate:bb,Press:ab,on:n,off:o,each:g,merge:i,extend:h,inherit:j,bindFn:k,prefixed:v}),typeof define==kb&&define.amd?define(function(){return eb}):"undefined"!=typeof module&&module.exports?module.exports=eb:a[c]=eb}(window,document,"Hammer");}

/*! matchMedia() polyfill - Test a CSS media type/query in JS. Authors & copyright (c) 2012: Scott Jehl, Paul Irish, Nicholas Zakas. Dual MIT/BSD license */
;window.matchMedia=window.matchMedia||(function(e,f){var c,a=e.documentElement,b=a.firstElementChild||a.firstChild,d=e.createElement("body"),g=e.createElement("div");g.id="mq-test-1";g.style.cssText="position:absolute;top:-100em";d.appendChild(g);return function(h){g.innerHTML='&shy;<style media="'+h+'"> #mq-test-1 { width: 42px; }</style>';a.insertBefore(d,b);c=g.offsetWidth==42;a.removeChild(d);return{matches:c,media:h}}})(document);

/*! Picturefill - Responsive Images that work today. (and mimic the proposed Picture element with span elements). Author: Scott Jehl, Filament Group, 2012 | License: MIT/GPLv2 */
;(function(a){a.picturefill=function(){var b=a.document.getElementsByTagName("span");for(var f=0,l=b.length;f<l;f++){if(b[f].getAttribute("data-picture")!==null){var c=b[f].getElementsByTagName("span"),h=[];for(var e=0,g=c.length;e<g;e++){var d=c[e].getAttribute("data-media");if(!d||(a.matchMedia&&a.matchMedia(d).matches)){h.push(c[e])}}var m=b[f].getElementsByTagName("img")[0];if(h.length){var k=h.pop();if(!m||m.parentNode.nodeName==="NOSCRIPT"){m=a.document.createElement("img");m.alt=b[f].getAttribute("data-alt")}if(k.getAttribute("data-width")){m.setAttribute("width",k.getAttribute("data-width"))}else{m.removeAttribute("width")}if(k.getAttribute("data-height")){m.setAttribute("height",k.getAttribute("data-height"))}else{m.removeAttribute("height")}m.src=k.getAttribute("data-src");k.appendChild(m)}else{if(m){m.parentNode.removeChild(m)}}}}};if(a.addEventListener){a.addEventListener("resize",a.picturefill,false);a.addEventListener("DOMContentLoaded",function(){a.picturefill();a.removeEventListener("load",a.picturefill,false)},false);a.addEventListener("load",a.picturefill,false)}else{if(a.attachEvent){a.attachEvent("onload",a.picturefill)}}}(this));
