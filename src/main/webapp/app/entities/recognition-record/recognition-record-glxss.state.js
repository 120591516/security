(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('work-record-glxss.recognition-record-glxss', {
            parent: 'work-record-glxss',
            url: '/{id}/recognition-record-glxss',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.recognitionRecord.home.title'
            },
            views: {
                'content@': {
                    template: '<div ng-include="vm.getTemplateUrl()"></div>',
                    controller: 'RecognitionRecordGlxssController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('recognitionRecord');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }],
                workRecordEntity: ['$stateParams', 'WorkRecord', function($stateParams, WorkRecord) {
                    return WorkRecord.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: 'work-record-glxss', //TODO:受下层车牌信息影响返回按钮地址预先写死,以后研究.
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-record-glxss.recognition-record-glxss-detail', {
            parent: 'work-record-glxss.recognition-record-glxss',
            url: '/{rid}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.recognitionRecord.detail.title'
            },
            views: {
                'content@': {
                    template: '<div ng-include="vm.getTemplateUrl()"></div>',
                    controller: 'RecognitionRecordGlxssDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('recognitionRecord');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RecognitionRecord', function($stateParams, RecognitionRecord) {
                    return RecognitionRecord.get({id : $stateParams.rid}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: 'work-record-glxss.recognition-record-glxss',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-record-glxss.recognition-record-glxss-detail.edit', {
            parent: 'work-record-glxss.recognition-record-glxss-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/recognition-record/recognition-record-glxss-dialog.html',
                    controller: 'RecognitionRecordGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RecognitionRecord', function(RecognitionRecord) {
                            return RecognitionRecord.get({id : $stateParams.rid}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-record-glxss.recognition-record-glxss.new', {
            parent: 'work-record-glxss.recognition-record-glxss',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/recognition-record/recognition-record-glxss-dialog.html',
                    controller: 'RecognitionRecordGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                sourcePicId: null,
                                similarity: null,
                                attention: null,
                                latitude: null,
                                longitude: null,
                                info: null,
                                infoContentType: null,
                                deleted: null,
                                createdDate: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('work-record-glxss.recognition-record-glxss', null, { reload: 'work-record-glxss.recognition-record-glxss' });
                }, function() {
                    $state.go('work-record-glxss.recognition-record-glxss');
                });
            }]
        })
        .state('work-record-glxss.recognition-record-glxss.edit-not-used', {  // not used
            parent: 'work-record-glxss.recognition-record-glxss',
            url: '/{rid}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/recognition-record/recognition-record-glxss-dialog.html',
                    controller: 'RecognitionRecordGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RecognitionRecord', function(RecognitionRecord) {
                            return RecognitionRecord.get({id : $stateParams.rid}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-record-glxss.recognition-record-glxss', null, { reload: 'work-record-glxss.recognition-record-glxss' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-record-glxss.recognition-record-glxss.delete', {
            parent: 'work-record-glxss.recognition-record-glxss',
            url: '/{rid}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/recognition-record/recognition-record-glxss-delete-dialog.html',
                    controller: 'RecognitionRecordGlxssDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RecognitionRecord', function(RecognitionRecord) {
                            return RecognitionRecord.get({id : $stateParams.rid}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-record-glxss.recognition-record-glxss', null, { reload: 'work-record-glxss.recognition-record-glxss' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
