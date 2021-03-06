(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('localFaceSet', {
            abstract: true,
            parent: 'app'
        });
    }
})();
