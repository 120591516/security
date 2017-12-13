(function() {
    'use strict';
    angular
        .module('glxssSecurityApp')
        .factory('WorkRecordStats', WorkRecordStats);

    WorkRecordStats.$inject = ['$translate', '$q', 'WorkRecord'];

    function WorkRecordStats ($translate, $q, WorkRecord) {
        var vm = {};

        // typeStr: carPlate, or face
        vm.getStatsOption = function(type, typeStr, days) {
            var defer = $q.defer();

            WorkRecord.stats({type: type, days: days}).$promise
                .then(function(data) {
                    var translateIdArray = [
                        'home.' + typeStr + '.stats.title',
                        'home.stats.legend',
                        'home.stats.total',
                        'home.stats.attention',
                        'home.stats.report'
                    ];

                    $translate(translateIdArray, { days: days })
                        .then(function(translations) {
                            defer.resolve({
                                title: {
                                    text: translations['home.' + typeStr + '.stats.title']
                                },
                                tooltip: {},
                                legend: {
                                    data:
                                        [
                                            translations['home.stats.total'],
                                            translations['home.stats.attention'],
                                            translations['home.stats.report'],
                                        ]
                                },
                                xAxis: {
                                    data: data.map(function(item) {
                                        return new Date(item.date).toLocaleDateString();
                                    }),
                                    type: 'category',
                                    axisLabel: {
                                        rotate: 45,
                                    }
                                },
                                yAxis: {},
                                series: [
                                    {
                                        name: translations['home.stats.total'],
                                        type: 'bar',
                                        data: data.map(function(item) { return item.numWorkRecords;})
                                    },
                                    {
                                        name: translations['home.stats.attention'],
                                        type: 'bar',
                                        data: data.map(function(item) { return item.numAttention;})
                                    },
                                    {
                                        name: translations['home.stats.report'],
                                        type: 'bar',
                                        data: data.map(function(item) { return item.numReport;})
                                    }
                                ]
                            });
                        }, function() {
                            defer.reject();
                        });
            });
            return defer;
        }  // End of vm.getStatsOption

        // typeStr: carPlate, or face
        vm.getUserStatsOption = function(type, typeStr, days, size) {
            var defer = $q.defer();

            WorkRecord.userStats({type: type, days: days, size: size, page: 0}).$promise
                .then(function(data) {
                    data = data.content.reverse();  // Top to bottom

                    var translateIdArray = [
                        'home.' + typeStr + '.userStats.title',
                        'home.stats.legend',
                        'home.stats.total',
                        'home.stats.attention',
                        'home.stats.report'
                    ];

                    $translate(translateIdArray, { days: days, size: size })
                        .then(function(translations) {
                            defer.resolve({
                                title: {
                                    text: translations['home.' + typeStr + '.userStats.title']
                                },
                                tooltip: {},
                                legend: {
                                    data:
                                        [
                                            translations['home.stats.total'],
                                            translations['home.stats.attention'],
                                            translations['home.stats.report'],
                                        ]
                                },
                                xAxis: {
                                    type: 'value'
                                },
                                yAxis: {
                                    type: 'category',
                                    data: data.map(function(item) {
                                        return item.user.lastName + item.user.firstName + '\n(' + item.user.login + ')';
                                    }),
                                    axisLabel: {
                                        rotate: 45,
                                    }
                                },
                                grid: {
                                    left: 100
                                },
                                series: [
                                    {
                                        name: translations['home.stats.total'],
                                        type: 'bar',
                                        data: data.map(function(item) { return item.numWorkRecords;})
                                    },
                                    {
                                        name: translations['home.stats.attention'],
                                        type: 'bar',
                                        data: data.map(function(item) { return item.numAttention;})
                                    },
                                    {
                                        name: translations['home.stats.report'],
                                        type: 'bar',
                                        data: data.map(function(item) { return item.numReport;})
                                    }
                                ]
                            });
                        }, function() {
                            defer.reject();
                        });
            });
            return defer;
        }  // End of getUserStatsOption

        return vm;
    }
})();
