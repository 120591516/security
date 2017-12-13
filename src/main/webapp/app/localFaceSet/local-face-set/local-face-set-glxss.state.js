(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('local-face-set-glxss', {
            parent: 'localFaceSet',
            url: '/local-face-set-glxss?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.localFaceSet.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/localFaceSet/local-face-set/local-face-setsglxss.html',
                    controller: 'LocalFaceSetGlxssController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('localFaceSet');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('local-face-set-glxss-detail', {
            parent: 'local-face-set-glxss',
            url: '/local-face-set-glxss/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.localFaceSet.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/localFaceSet/local-face-set/local-face-set-glxss-detail.html',
                    controller: 'LocalFaceSetGlxssDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('localFaceSet');
                    $translatePartialLoader.addPart('share');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LocalFaceSet', function($stateParams, LocalFaceSet) {
                    return LocalFaceSet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'local-face-set-glxss',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('local-face-set-glxss-detail.edit', {
            parent: 'local-face-set-glxss-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face-set/local-face-set-glxss-dialog.html',
                    controller: 'LocalFaceSetGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocalFaceSet', function(LocalFaceSet) {
                            return LocalFaceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('local-face-set-glxss.new', {
            parent: 'local-face-set-glxss',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face-set/local-face-set-glxss-dialog.html',
                    controller: 'LocalFaceSetGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                deleted: null,
                                activated: true,
                                createdDate: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('local-face-set-glxss', null, { reload: 'local-face-set-glxss' });
                }, function() {
                    $state.go('local-face-set-glxss');
                });
            }]
        })
        .state('local-face-set-glxss.edit', {
            parent: 'local-face-set-glxss',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face-set/local-face-set-glxss-dialog.html',
                    controller: 'LocalFaceSetGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocalFaceSet', function(LocalFaceSet) {
                            return LocalFaceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('local-face-set-glxss', null, { reload: 'local-face-set-glxss' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('local-face-set-glxss.delete', {
            parent: 'local-face-set-glxss',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face-set/local-face-set-glxss-delete-dialog.html',
                    controller: 'LocalFaceSetGlxssDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LocalFaceSet', function(LocalFaceSet) {
                            return LocalFaceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('local-face-set-glxss', null, { reload: 'local-face-set-glxss' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('local-face-set-glxss-detail.share', {
            parent: 'local-face-set-glxss-detail',
            url: '/share',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face-set/local-face-set-share-glxss-dialog.html',
                    controller: 'LocalFaceSetShareGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg'
                }).result.then(function () {
                    $state.go('^', null, {reload: false});
                }, function () {
                    $state.go('^');
                });
            }]
        })
        .state('local-face-set-glxss-detail.shareDelete', {
            parent: 'local-face-set-glxss-detail',
            url: '/{login}/shareDelete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face-set/local-face-set-share-glxss-delete-dialog.html',
                    controller: 'LocalFaceSetShareGlxssDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['User', function(User) {
                            return User.get({login : $stateParams.login});
                        }]
                    }
                }).result.then(function () {
                    $state.go('^', null, {reload: false });
                }, function () {
                    $state.go('^');
                });
            }]
        })
    }

})();
