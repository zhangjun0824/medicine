'use strict';

/* Controllers */

angular.module('app')
  .controller('AppCtrl', ['$scope', '$translate', '$localStorage', '$window', 'toaster', 
    function(              $scope,   $translate,   $localStorage,   $window , toaster) {
      // add 'ie' classes to html
      var isIE = !!navigator.userAgent.match(/MSIE/i);
      isIE && angular.element($window.document.body).addClass('ie');
      isSmartDevice( $window ) && angular.element($window.document.body).addClass('smart');

      var menus;
      var user;
  	  $.ajax({
		url : webRoot + '/sys/queryMenus.json',
		type : 'post',
		async : false,
		success:function(data){ 
			menus=data.menus;
		}
  	  });
  	  $.ajax({
  		  url : webRoot + '/sys/getCurrentUser.json',
  		  type : 'post',
  		  async : false,
  		  success:function(data){ 
  			  user=data.user;
  		  }
  	  });
  	  
      // config
      $scope.app = {
        name: 'Angulr',
        version: '1.3.3',
        menus:menus,
        user:user,
        webRoot:webRoot,
        color: {
          primary: '#7266ba',
          info:    '#23b7e5',
          success: '#27c24c',
          warning: '#fad733',
          danger:  '#f05050',
          light:   '#e8eff0',
          dark:    '#3a3f51',
          black:   '#1c2b36'
        },
        settings: {
          themeID: 1,
          navbarHeaderColor: 'bg-black',
          navbarCollapseColor: 'bg-white-only',
          asideColor: 'bg-black',
          headerFixed: true,
          asideFixed: false,
          asideFolded: false,
          asideDock: false,
          container: false
        }
      }

      // save settings to local storage
      if ( angular.isDefined($localStorage.settings) ) {
        $scope.app.settings = $localStorage.settings;
      } else {
        $localStorage.settings = $scope.app.settings;
      }
      $scope.$watch('app.settings', function(){
        if( $scope.app.settings.asideDock  &&  $scope.app.settings.asideFixed ){
          // aside dock and fixed must set the header fixed.
          $scope.app.settings.headerFixed = true;
        }
        // save to local storage
        $localStorage.settings = $scope.app.settings;
      }, true);

      // angular translate
      $scope.lang = { isopen: false };
      $scope.langs = {en:'English', de_DE:'German', it_IT:'Italian'};
      $scope.selectLang = $scope.langs[$translate.proposedLanguage()] || "English";
      $scope.setLang = function(langKey, $event) {
        // set the current lang
        $scope.selectLang = $scope.langs[langKey];
        // You can change the language during runtime
        $translate.use(langKey);
        $scope.lang.isopen = !$scope.lang.isopen;
      };

      function isSmartDevice( $window )
      {
          // Adapted from http://www.detectmobilebrowsers.com
          var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
          // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
          return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
      }
      $scope.GoBack=function(){
    	  window.history.go(-1);
      }
      $scope.resetPwd=function(obj){
    	  var user={
			  id:obj.id,
			  username:obj.username
    	  }
      	  $.ajax({
      		  url : webRoot + '/user/resetPwd.json',
      		  type : 'post',
      		  data:user,
      		  async : false,
      		  success:function(data){
      			toaster.pop("success","提示","重置成功.密码与账户名相同.");
      		  }
      	  });
      }
  }]);