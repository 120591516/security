(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('WorkRecordGlxssController', WorkRecordGlxssController);

    WorkRecordGlxssController.$inject = ['$state', 'WorkRecord', 'ParseLinks', 'AlertService', 'paginationConstants',
        'pagingParams', 'CarPlate', 'Person'];

    function WorkRecordGlxssController($state, WorkRecord, ParseLinks, AlertService, paginationConstants,
            pagingParams, CarPlate, Person) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        // workRecordsType是工作记录类型type
        vm.workRecordsType = parseInt(pagingParams.type);

        vm.getTemplateUrl = function() {
            switch (vm.workRecordsType) {
                case 1: // 车牌识别
                    return "app/entities/work-record/work-records-car-plateglxss.html";
                case 2: // 人脸识别
                    return "app/entities/work-record/work-records-faceglxss.html";
                default:
                    return "app/entities/work-record/work-recordsglxss.html";
            }
        };

        loadAll();

        function loadAll () {
            WorkRecord.query({
                type: vm.workRecordsType,
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.workRecords = data;
                vm.page = pagingParams.page;
                angular.forEach(vm.workRecords, function(workRecord) {
                    if (workRecord.type == 1) {
                        // 车牌信息
                        workRecord.carPlate = CarPlate.get({id: workRecord.targetId});
                    } else if (workRecord.type == 2 && workRecord.targetId != null) {
                        // 人像信息
                        workRecord.person = Person.get({id: workRecord.targetId});
                    }
                });
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }


        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                type: vm.workRecordsType,
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

    }
})();
