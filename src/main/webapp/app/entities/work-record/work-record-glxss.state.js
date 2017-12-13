(function () {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('work-record-glxss', {
                parent: 'entity',
                url: '/work-record-glxss?page&sort&search&type',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'glxssSecurityApp.workRecord.home.title'
                },
                views: {
                    'content@': {
                        template: '<div ng-include="vm.getTemplateUrl()"></div>',
                        controller: 'WorkRecordGlxssController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,desc',
                        squash: true
                    },
                    type: null,
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            type: $stateParams.type,
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workRecord');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('work-record-glxss-detail', {
                parent: 'work-record-glxss',
                url: '/work-record-glxss/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'glxssSecurityApp.workRecord.detail.title'
                },
                views: {
                    'content@': {
                        template: '<div ng-include="vm.getTemplateUrl()"></div>',
                        controller: 'WorkRecordGlxssDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workRecord');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'WorkRecord', function ($stateParams, WorkRecord) {
                        return WorkRecord.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'work-record-glxss',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('work-record-glxss.recognition-record-glxss.edit', {
                parent: 'work-record-glxss.recognition-record-glxss',
                url: '/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        template: '<div ng-include="vm.getTemplateUrl()"></div>',
                        controller: 'WorkRecordGlxssDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['WorkRecord', function (WorkRecord) {
                                return WorkRecord.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('work-record-glxss.new', {
                parent: 'work-record-glxss',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/work-record/work-record-glxss-dialog.html',
                        controller: 'WorkRecordGlxssDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    targetId: null,
                                    targetPicId: null,
                                    sourcePicId: null,
                                    similarity: null,
                                    attention: null,
                                    report: null,
                                    reportDelay: null,
                                    latitude: null,
                                    longitude: null,
                                    danger1: null,
                                    danger2: null,
                                    danger3: null,
                                    danger4: null,
                                    danger5: null,
                                    deleted: null,
                                    lastRecognizedDate: null,
                                    createdDate: null,
                                    lastModifiedDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('work-record-glxss', null, {reload: 'work-record-glxss'});
                    }, function () {
                        $state.go('work-record-glxss');
                    });
                }]
            })
            .state('work-record-glxss.edit', {
                parent: 'work-record-glxss',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        template: '<div ng-include="vm.getTemplateUrl()"></div>',
                        controller: 'WorkRecordGlxssDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['WorkRecord', function (WorkRecord) {
                                return WorkRecord.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('work-record-glxss', null, {reload: 'work-record-glxss'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('work-record-glxss.delete', {
                parent: 'work-record-glxss',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/work-record/work-record-glxss-delete-dialog.html',
                        controller: 'WorkRecordGlxssDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['WorkRecord', function (WorkRecord) {
                                return WorkRecord.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('work-record-glxss', null, {reload: 'work-record-glxss'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('work-record-glxss-car-info', {
                parent: 'work-record-glxss',
                url: '/car-plates/{cid}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'glxssSecurityApp.workRecordCarPlate.carInfo'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/plugin/plugin-glxss-info-car.html',
                        controller: 'PluginGlxssCarInfoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workRecord');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CarPlate', function ($stateParams, CarPlate) {
                        return CarPlate.get({id: $stateParams.cid}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || '^', //在车辆中不会有不确定的返回情况
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('work-record-glxss-car-person', {
                parent: 'work-record-glxss',
                url: '/persons/{pid}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'glxssSecurityApp.workRecordCarPlate.carInfo'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/plugin/plugin-glxss-info-person.html',
                        controller: 'PluginGlxssPersonInfoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workRecord');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Person', function ($stateParams, Person) {
                        return Person.get({id: $stateParams.pid}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'work-record-glxss',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            // 实时人脸识别
            .state('work-record-glxss-real-time', {
                parent: 'work-record-glxss',
                url: '/work-record-glxss-real-time',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'glxssSecurityApp.workRecord.detail.title'
                },
                views: {
                    'content@': {
                        template: '<div ng-include="vm.getTemplateUrl()"></div>',
                        controller: 'WorkRecordGlxssRealTimeController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,desc',
                        squash: true
                    },
                    type: null,
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            type: $stateParams.type,
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workRecord');
                        return $translate.refresh();
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'work-record-glxss',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            });
    }

})();
