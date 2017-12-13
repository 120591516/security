(function () {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('WorkRecordGlxssRealTimeController', WorkRecordGlxssRealTimeController);

    WorkRecordGlxssRealTimeController.$inject = ['$state', 'WorkRecord', 'ParseLinks', 'AlertService', 'paginationConstants',
        'pagingParams', 'CarPlate', 'Person', '$rootScope', '$window', '$q', '$localStorage', 'Account', '$stateParams', '$timeout', 'Principal'];

    function WorkRecordGlxssRealTimeController($state, WorkRecord, ParseLinks, AlertService, paginationConstants,
                                               pagingParams, CarPlate, Person, $rootScope, $window, $q, $localStorage, Account, $stateParams, $timeout, Principal) {

        var vm = this;
        var maxWorkRecords = 40;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        // workRecordsType是工作记录类型type
        vm.workRecordsType = parseInt(pagingParams.type);

        vm.getTemplateUrl = function () {
            switch (vm.workRecordsType) {
                case 1: // 车牌识别
                    return "app/entities/work-record/work-records-real-time-car-plateglxss.html";
                case 2: // 人脸识别
                    return "app/entities/work-record/work-records-real-time-faceglxss.html";
                default:
                    return "app/entities/work-record/work-records-real-time-faceglxss.html";
            }
        };
        /// 获取实时数据 需要webSocket技术

        vm.workRecords = [];
        var stompClient = null;
        var subscriber = null;
        var listener = $q.defer();
        var connected = $q.defer();

        var alreadyConnectedOnce = false;
        var user = {};

        function connect() {
            //building absolute path so that websocket doesn't fail when deploying with a context path
            var loc = $window.location;
            // var url = '//' + loc.host + loc.pathname + 'websocket/realTime';
            var url = '//' + loc.host  + '/websocket/realTime';

            /*jshint camelcase: false */
            var authToken = angular.fromJson($localStorage.authenticationToken).access_token;
            url += '?access_token=' + authToken;
            var socket = new SockJS(url);
            stompClient = Stomp.over(socket);
            var stateChangeStart;
            var headers = {};
            stompClient.connect(headers, function () {
                connected.resolve('success');
                sendActivity();
                if (!alreadyConnectedOnce) {
                    stateChangeStart = $rootScope.$on('$stateChangeStart', function () {
                        sendActivity();
                    });
                    alreadyConnectedOnce = true;
                }
            });
            $rootScope.$on('$destroy', function () {
                if (angular.isDefined(stateChangeStart) && stateChangeStart !== null) {
                    stateChangeStart();
                }
            });
        }

        function sendActivity() {
            if (stompClient !== null && stompClient.connected) {
                stompClient
                    .send('/topic/realTimeFace',
                        {},
                        angular.toJson({'page': $rootScope.toState.name}));
            }
        }

        function subscribeCar() {
            // connect();
            if (!connected) {
                connected.reject("STOMP client not created");
            } else {
                connected.promise.then(
                    function () {
                        subscriber = stompClient.subscribe('/user/queue/car', function (data) {
                            listener.notify(angular.fromJson(data.body));
                        });
                    }, null, null);
            }
        }

        function subscribeFace() {
            connected.promise.then(function () {
                subscriber = stompClient.subscribe('/user/queue/face', function (data) {
                    listener.notify(angular.fromJson(data.body));
                });
            });
        }

        function subscribeRealTime() {
            connected.promise.then(function () {
                subscriber = stompClient.subscribe('/user/queue/realTime', function (data) {
                    listener.notify(angular.fromJson(data.body));
                });
            });
        }

        function disconnect() {
            if (stompClient !== null) {
                stompClient.disconnect();
                stompClient = null;
            }
        }

        function receive() {
            return listener.promise;
        }

        function unsubscribe() {
            if (subscriber !== null) {
                subscriber.unsubscribe();
            }
            listener = $q.defer();
        }

        connect();
        subscribeFace();
        subscribeCar();
        subscribeRealTime();

        receive().then(null, null, function (workRecord) {
            if(vm.workRecordsType == workRecord.type){
                showGlxss(workRecord);
            }
        });
        function showGlxss(workRecord) {
            var existingWorkRecord = false;
            if (!existingWorkRecord && (workRecord.page !== 'logout')) {

                for(var i = 0; i < vm.workRecords.length ; i++){
                    if(vm.workRecords[i].id == workRecord.workRecord.id){
                        vm.workRecords.splice(i,1);
                        break;
                    }
                }

                if ( workRecord.workRecord.type == 1) {
                    workRecord.workRecord.carPlate = CarPlate.get({id:  workRecord.workRecord.targetId});
                } else if ( workRecord.workRecord.type == 2 &&  workRecord.workRecord.targetId != null) {
                    workRecord.workRecord.person = Person.get({id:  workRecord.workRecord.targetId});
                }
                vm.workRecords.unshift(workRecord.workRecord);
                if(vm.workRecords.length > maxWorkRecords ){
                    vm.workRecords.pop();
                }
            }
        }

        //获取数据库数据
        loadAll();
        function loadAll() {
            WorkRecord.query({
                type: vm.workRecordsType,
                // page: pagingParams.page - 1,
                // size: vm.itemsPerPage,
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
                angular.forEach(vm.workRecords, function (workRecord) {
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
