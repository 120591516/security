(function() {
    'use strict';
    angular
        .module('glxssSecurityApp')
        .factory('WorkRecord', WorkRecord);

    WorkRecord.$inject = ['$resource', 'DateUtils'];

    function WorkRecord ($resource, DateUtils) {
        var resourceUrl =  'api/work-records/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastRecognizedDate = DateUtils.convertDateTimeFromServer(data.lastRecognizedDate);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'stats': {
                method: 'GET',
                isArray: true,
                url: 'api/work-records/stats'
            },
            'userStats': {
                method: 'GET',
                url: 'api/work-records/user-stats'
            },
            'update': { method:'PUT' }
        });
    }
})();
