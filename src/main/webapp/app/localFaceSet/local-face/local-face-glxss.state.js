(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('local-face-glxss', {
            parent: 'local-face-set-glxss',
            url: '/{faceSetId}/local-face-glxss?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.localFace.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/localFaceSet/local-face/local-facesglxss.html',
                    controller: 'LocalFaceGlxssController',
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
                faceSet: ['$stateParams', 'LocalFaceSet', function($stateParams, LocalFaceSet) {
                    return LocalFaceSet.get({id : $stateParams.faceSetId}).$promise;
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('localFace');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
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
        .state('local-face-glxss-detail', {
            parent: 'local-face-glxss',
            url: '/local-face-glxss/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.localFace.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/localFaceSet/local-face/local-face-glxss-detail.html',
                    controller: 'LocalFaceGlxssDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('localFace');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LocalFace', function($stateParams, LocalFace) {
                    return LocalFace.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'local-face-glxss',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('local-face-glxss-detail.edit', {
            parent: 'local-face-glxss-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face/local-face-glxss-dialog.html',
                    controller: 'LocalFaceGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocalFace', function(LocalFace) {
                            return LocalFace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('local-face-glxss.new', {
            parent: 'local-face-glxss',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face/local-face-glxss-dialog.html',
                    controller: 'LocalFaceGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                personId: null,
                                gender: null,
                                birthday: null,
                                address: null,
                                attention: null,
                                danger: null,
                                image: null,
                                model: null,
                                hash: null,
                                faceSetId: $stateParams.faceSetId,
                                createdDate: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('local-face-glxss', null, { reload: 'local-face-glxss' });
                }, function() {
                    $state.go('local-face-glxss');
                });
            }]
        })
        .state('local-face-glxss.edit', {
            parent: 'local-face-glxss',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face/local-face-glxss-dialog.html',
                    controller: 'LocalFaceGlxssDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocalFace', function(LocalFace) {
                            return LocalFace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('local-face-glxss', null, { reload: 'local-face-glxss' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('local-face-glxss.delete', {
            parent: 'local-face-glxss',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/local-face/local-face-glxss-delete-dialog.html',
                    controller: 'LocalFaceGlxssDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LocalFace', function(LocalFace) {
                            return LocalFace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('local-face-glxss', null, { reload: 'local-face-glxss' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
