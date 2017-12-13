(function() {
    'use strict';
    angular
        .module('glxssSecurityApp')
        .factory('ShareLocalFaceSet', ShareLocalFaceSet);

    ShareLocalFaceSet.$inject = ['$resource', 'DateUtils'];

    function ShareLocalFaceSet ($resource, DateUtils) {
        var resourceUrl =  'api/local-face-sets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
