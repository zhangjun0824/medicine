'use strict';

/**
 * Config for the router
 */
angular.module('app')
  .run(
    [          '$rootScope', '$state', '$stateParams',
      function ($rootScope,   $state,   $stateParams) {
          $rootScope.$state = $state;
          $rootScope.$stateParams = $stateParams;        
      }
    ]
  )
  .config(
    [          '$stateProvider', '$urlRouterProvider',
      function ($stateProvider,   $urlRouterProvider) {
          
          $urlRouterProvider.otherwise('/app/main');
          $stateProvider
              .state('app', {
                  abstract: true,
                  url: '/app',
                  templateUrl: 'tpl/app.html'
              })
              .state('app.main', {
            	  url: '/main',
            	  templateUrl: 'tpl/main.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
            					return $ocLazyLoad.load('js/main.js');
                      }]
                    }
              })
              .state('app.userList', {
                  url: '/user/list',
                  templateUrl: 'tpl/user/list.html',
                  resolve: {
                    deps: ['$ocLazyLoad',
                      function( $ocLazyLoad ){
          					return $ocLazyLoad.load('js/source/user/list.js');
                    }]
                  }
              })
              .state('app.userAdd', {
            	  url: '/user/add',
            	  templateUrl: 'tpl/user/add.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
              					return $ocLazyLoad.load('js/source/user/add.js');
            		  }]
            	  }
              })
              .state('app.userEdit', {
            	  url: '/user/edit/:user',
            	  templateUrl: 'tpl/user/edit.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
            			  return $ocLazyLoad.load('js/source/user/edit.js');
            		  }]
            	  }
              })
              .state('app.userEditPwd', {
            	  url: '/user/editPwd/:user',
            	  templateUrl: 'tpl/user/editPwd.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
            			  return $ocLazyLoad.load('js/source/user/editPwd.js');
            		  }]
            	  }
              })
              .state('app.userRole', {
            	  url: '/user/userRole/:user',
            	  templateUrl: 'tpl/user/userRole.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
            			  return $ocLazyLoad.load('js/source/user/userRole.js');
            		  }]
            	  }
              })
              .state('app.roleList', {
            	  url: '/role/list',
            	  templateUrl: 'tpl/role/list.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
		                  		return $ocLazyLoad.load('js/source/role/list.js');
            		  }]
            	  }
              })
              .state('app.roleAdd', {
            	  url: '/role/add',
            	  templateUrl: 'tpl/role/add.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
            					return $ocLazyLoad.load('js/source/role/add.js');
            		  }]
            	  }
              })
              .state('app.roleEdit', {
            	  url: '/role/edit/:role',
            	  templateUrl: 'tpl/role/edit.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
            			  		return $ocLazyLoad.load('js/source/role/edit.js');
            		  }]
            	  }
              })
              .state('app.rolePrivilege', {
            	  url: '/role/rolePrivilege/:role',
            	  templateUrl: 'tpl/role/rolePrivilege.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
                    			return $ocLazyLoad.load('angularBootstrapNavTree').then(
                      				function(){
                      					return $ocLazyLoad.load('js/source/role/rolePrivilege.js');
                      				}
                      		);
            		  }]
            	  }
              })
              .state('app.privilegeList', {
            	  url: '/privilege/list',
            	  templateUrl: 'tpl/privilege/list.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
	                    		return $ocLazyLoad.load('angularBootstrapNavTree').then(
                      				function(){
                      					return $ocLazyLoad.load('js/source/privilege/list.js');
                      				}
	                      		);
            		  }]
            	  }
              })
              .state('app.dictList', {
            	  url: '/dict/list',
            	  templateUrl: 'tpl/dict/list.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
		                  		return $ocLazyLoad.load('angularBootstrapNavTree').then(
	                  				function(){
	                  					return $ocLazyLoad.load('js/source/dict/list.js');
	                  				}
	                      		);
            		  }]
            	  }
              })
              .state('app.tableConfig', {
            	  url: '/table/list',
            	  templateUrl: 'tpl/table/list.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
            			  return $ocLazyLoad.load('angularBootstrapNavTree').then(
            					  function(){
            						  return $ocLazyLoad.load('js/source/table/list.js');
            					  }
            			  );
            		  }]
            	  }
              })
              .state('app.info', {
            	  url: '/info/main',
            	  templateUrl: 'tpl/info/main.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
        						 return $ocLazyLoad.load('js/source/info/main.js');
            		  }]
            	  }
              })
              .state('app.infoEdit', {
            	  url: '/info/edit:table',
            	  templateUrl: 'tpl/info/edit.html',
            	  resolve: {
            		  deps: ['$ocLazyLoad',
            		         function( $ocLazyLoad ){
            			  return $ocLazyLoad.load('js/source/info/edit.js');
            		  }]
            	  }
              })
      }
    ]
  );