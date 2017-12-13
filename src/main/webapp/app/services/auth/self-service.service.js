(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .factory('selfService', selfService);

    selfService.$inject = ['$resource'];

    function selfService ($resource) {
        var resourceUrl =  'api/selfService';
        return $resource(resourceUrl, {}, {
            'get': {method: 'GET'}
        });
    }
})();
