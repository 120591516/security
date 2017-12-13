(function() {
    'use strict';
    angular
        .module('glxssSecurityApp')
        .factory('CarPlate', CarPlate);

    CarPlate.$inject = ['$resource'];

    function CarPlate ($resource) {
        var resourceUrl =  'api/car-plates/:id';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET'
            }
        });
    }
})();
