(function() {
    'use strict';
    angular
        .module('glxssSecurityApp')
        .factory('LogRecord', LogRecord);

    LogRecord.$inject = ['$resource', 'DateUtils'];

    function LogRecord ($resource, DateUtils) {
        var resourceUrl =  'api/log-records/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
