'use strict';

var pathName = document.location.pathname.substring(1);
var webRoot = pathName == '' ? '' :'/' + pathName.substring(0, pathName.indexOf('/'));
angular.module('app', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngTouch',
    'ngStorage',
    'ui.router',
    'ui.bootstrap',
    'ui.load',
    'ui.jq',
    'ui.validate',
    'oc.lazyLoad',
    'pascalprecht.translate',
    'toaster',
    'ngDialog',
    'tm.pagination'
]);
