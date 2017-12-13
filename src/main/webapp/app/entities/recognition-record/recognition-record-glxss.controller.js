(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('RecognitionRecordGlxssController', RecognitionRecordGlxssController);

    RecognitionRecordGlxssController.$inject = [
        '$scope', '$rootScope', '$stateParams', 'previousState', 'workRecordEntity', 'WorkRecord',
        'DataUtils', 'RecognitionRecord', 'ParseLinks', 'AlertService', 'paginationConstants', 'Person', 'CarPlate'];

    function RecognitionRecordGlxssController(
        $scope, $rootScope, $stateParams, previousState, workRecordEntity, WorkRecord,
        DataUtils, RecognitionRecord, ParseLinks, AlertService, paginationConstants, Person, CarPlate) {

        var vm = this;

        vm.workRecord = workRecordEntity;
        vm.previousState = previousState.name;
        vm.workRecordType = workRecordEntity.type;

        vm.recognitionRecords = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = false;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        if (vm.workRecordType == 2 && vm.workRecord.targetId != null) {
            vm.workRecord.person = Person.get({id: vm.workRecord.targetId});
        }
        if (vm.workRecordType == 1 && vm.workRecord.targetId != null) {
            vm.workRecord.carPlate = CarPlate.get({id: vm.workRecord.targetId});
        }
        loadAll();

        vm.getTemplateUrl = function() {
            switch (vm.workRecordType) {
                case 1: // 车牌识别
                    return "app/entities/recognition-record/recognition-records-car-plateglxss.html";
                case 2: // 人脸识别
                    return "app/entities/recognition-record/recognition-records-faceglxss.html";
                default:
                    return "app/entities/recognition-record/recognition-recordsglxss.html";
            }
        };

        var unsubscribe = $rootScope.$on('glxssSecurityApp:workRecordUpdate', function(event, result) {
            vm.workRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function loadAll () {
            RecognitionRecord.query({
                workRecordId: vm.workRecord.id,
                page: vm.page,
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
                for (var i = 0; i < data.length; i++) {
                    vm.recognitionRecords.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.recognitionRecords = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
