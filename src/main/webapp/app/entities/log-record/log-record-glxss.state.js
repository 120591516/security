(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('work-record-glxss.recognition-record-glxss.log-record-glxss', {
            parent: 'work-record-glxss.recognition-record-glxss',
            url: '/log-record-glxss',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.logRecord.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/log-record/log-recordsglxss.html',
                    controller: 'LogRecordGlxssController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('logRecord');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }],
                workRecordEntity: ['$stateParams', 'WorkRecord', function($stateParams, WorkRecord) {
                    return WorkRecord.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        // name: $state.current.name || 'work-record-glxss.recognition-record-glxss',
                        name: 'work-record-glxss.recognition-record-glxss',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-record-glxss.recognition-record-glxss.log-record-glxss-detail', {
            parent: 'work-record-glxss.recognition-record-glxss.log-record-glxss',
            url: '/log-record-glxss/{rid}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.logRecord.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/log-record/log-record-glxss-detail.html',
                    controller: 'LogRecordGlxssDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('logRecord');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LogRecord', function($stateParams, LogRecord) {
                    console.log($stateParams.id);
                    return LogRecord.get({id : $stateParams.rid}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'work-record-glxss.recognition-record-glxss.log-record-glxss',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-record-glxss.recognition-record-glxss.log-record-glxss-detail.edit', {
            parent: 'work-record-glxss.recognition-record-glxss.log-record-glxss-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-record/log-record-glxss-dialog.html',
                    controller: 'LogRecordGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LogRecord', function(LogRecord) {
                            return LogRecord.get({id : $stateParams.rid}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-record-glxss.recognition-record-glxss.log-record-glxss.new', {
            parent: 'work-record-glxss.recognition-record-glxss.log-record-glxss',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-record/log-record-glxss-dialog.html',
                    controller: 'LogRecordGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                text: null,
                                textContentType: null,
                                picId: null,
                                deleted: null,
                                createdDate: null,
                                id: null
                            };
                        },
                        workRecordEntity: ['$stateParams', 'WorkRecord', function($stateParams, WorkRecord) {
                            return WorkRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-record-glxss.recognition-record-glxss.log-record-glxss', null, { reload: 'work-record-glxss.recognition-record-glxss.log-record-glxss' });
                }, function() {
                    $state.go('work-record-glxss.recognition-record-glxss.log-record-glxss');
                });
            }]
        })
        .state('work-record-glxss.recognition-record-glxss.log-record-glxss.edit', {
            parent: 'work-record-glxss.recognition-record-glxss.log-record-glxss',
            url: '/{rid}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-record/log-record-glxss-dialog.html',
                    controller: 'LogRecordGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        workRecordEntity: ['$stateParams', 'WorkRecord', function($stateParams, WorkRecord) {
                            return WorkRecord.get({id : $stateParams.id}).$promise;
                        }],
                        entity: ['LogRecord', function(LogRecord) {
                            return LogRecord.get({id : $stateParams.rid}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-record-glxss.recognition-record-glxss.log-record-glxss', null, { reload: 'work-record-glxss.recognition-record-glxss.log-record-glxss' });
                }, function() {
                    $state.go('work-record-glxss.recognition-record-glxss.log-record-glxss');
                });
            }]
        })
        .state('work-record-glxss.recognition-record-glxss.log-record-glxss.delete', {
            parent: 'work-record-glxss.recognition-record-glxss.log-record-glxss',
            url: '/{rid}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/log-record/log-record-glxss-delete-dialog.html',
                    controller: 'LogRecordGlxssDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LogRecord', function(LogRecord) {
                            return LogRecord.get({id : $stateParams.rid}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-record-glxss.recognition-record-glxss.log-record-glxss', null, { reload: 'work-record-glxss.recognition-record-glxss.log-record-glxss' });
                }, function() {
                    $state.go('work-record-glxss.recognition-record-glxss.log-record-glxss');
                });
            }]
        });
    }

})();
