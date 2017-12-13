(function() {
    'use strict';
    angular
        .module('glxssSecurityApp')
        .factory('LocalFaceSet', LocalFaceSet);

    LocalFaceSet.$inject = ['$resource', 'DateUtils'];

    function LocalFaceSet ($resource, DateUtils) {
        var resourceUrl =  'api/local-face-sets/:id';

        return $resource(resourceUrl,{id : '@id', userLogin : '@userLogin'}, {
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
            'update': {
                method:'PUT',
                url: 'api/local-face-sets'
            },
            'shareSave' : {
                url: 'api/local-face-set/:id/users/:userLogin',
                method: 'POST'
            },
            'shareQuery': {
                url: 'api/local-face-set/activated-share',
                method: 'GET',
                isArray: true
            },
            'shareDelete': {
                url: 'api/local-face-set/:id/users/:userId',
                method: 'DELETE'
            }
        });
    }
})();
