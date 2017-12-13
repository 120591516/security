(function() {
    'use strict';
    angular
        .module('glxssSecurityApp')
        .factory('Person', Person);

    Person.$inject = ['$resource'];

    function Person ($resource) {
        var resourceUrl =  'api/persons/:id';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET'
            }
        });
    }
})();
