(function() {
    'use strict';
    angular
        .module('glxssSecurityApp')
        .factory('RecognitionRecord', RecognitionRecord);

    RecognitionRecord.$inject = ['$resource', 'DateUtils'];

    function RecognitionRecord ($resource, DateUtils) {
        var resourceUrl =  'api/recognition-records/:id';

        return $resource(resourceUrl, {}, {
            'query': {
                url: 'api/work-records/:workRecordId/recognition-records',
                method: 'GET',
                isArray: true
            },
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
