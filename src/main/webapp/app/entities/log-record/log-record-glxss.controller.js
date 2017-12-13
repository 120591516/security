(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LogRecordGlxssController', LogRecordGlxssController);

    LogRecordGlxssController.$inject = ['DataUtils', 'LogRecord', 'ParseLinks', 'previousState', 'AlertService', 'pagingParams', 'workRecordEntity', 'Base64', 'paginationConstants'];

    function LogRecordGlxssController(DataUtils, LogRecord, ParseLinks, previousState, AlertService, pagingParams, workRecordEntity, Base64, paginationConstants) {

        var vm = this;

        vm.logRecords = [];
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
        vm.previousState = previousState.name;
        vm.workRecordId = workRecordEntity.id;


        loadAll();

        function loadAll () {
            LogRecord.query({
                workRecordId: vm.workRecordId,
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
                    vm.logRecords.push(data[i]);
                }
                angular.forEach(vm.logRecords, function(logRecord) {
                    if(!!logRecord.text){
                        if(logRecord.text.trim().toString()){
                            if(window.decodeURIComponent(Base64.decode(logRecord.text.trim().toString())).length > 60){
                                logRecord.text = window.decodeURIComponent(Base64.decode(logRecord.text.trim().toString())).substr(0,59) + "...";
                            }else {
                                logRecord.text = window.decodeURIComponent(Base64.decode(logRecord.text.trim().toString()));
                            }
                        }
                    }
                });
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.logRecords = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

    }
})();
