(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('system-config-glxss', {
            parent: 'entity',
            url: '/system-config-glxss',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.systemConfig.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/system-config/system-configsglxss.html',
                    controller: 'SystemConfigGlxssController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('systemConfig');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('system-config-glxss-detail', {
            parent: 'system-config-glxss',
            url: '/system-config-glxss/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.systemConfig.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/system-config/system-config-glxss-detail.html',
                    controller: 'SystemConfigGlxssDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('systemConfig');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SystemConfig', function($stateParams, SystemConfig) {
                    return SystemConfig.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'system-config-glxss',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('system-config-glxss-detail.edit', {
            parent: 'system-config-glxss-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system-config/system-config-glxss-dialog.html',
                    controller: 'SystemConfigGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SystemConfig', function(SystemConfig) {
                            return SystemConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('system-config-glxss.new', {
            parent: 'system-config-glxss',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system-config/system-config-glxss-dialog.html',
                    controller: 'SystemConfigGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                key: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('system-config-glxss', null, { reload: 'system-config-glxss' });
                }, function() {
                    $state.go('system-config-glxss');
                });
            }]
        })
        .state('system-config-glxss.edit', {
            parent: 'system-config-glxss',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system-config/system-config-glxss-dialog.html',
                    controller: 'SystemConfigGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SystemConfig', function(SystemConfig) {
                            return SystemConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('system-config-glxss', null, { reload: 'system-config-glxss' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('system-config-glxss.delete', {
            parent: 'system-config-glxss',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system-config/system-config-glxss-delete-dialog.html',
                    controller: 'SystemConfigGlxssDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SystemConfig', function(SystemConfig) {
                            return SystemConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('system-config-glxss', null, { reload: 'system-config-glxss' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
