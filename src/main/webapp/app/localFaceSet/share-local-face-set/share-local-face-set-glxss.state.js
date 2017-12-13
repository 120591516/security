(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('share-local-face-set-glxss', {
            parent: 'localFaceSet',
            url: '/share-local-face-set-glxss?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'glxssSecurityApp.shareLocalFaceSet.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/localFaceSet/share-local-face-set/share-local-face-setsglxss.html',
                    controller: 'ShareLocalFaceSetGlxssController',
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
                    $translatePartialLoader.addPart('shareLocalFaceSet');
                    $translatePartialLoader.addPart('localFaceSet');
                    $translatePartialLoader.addPart('share');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('share-local-face-set-glxss.delete', {
            parent: 'share-local-face-set-glxss',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/localFaceSet/share-local-face-set/share-local-face-set-glxss-delete-dialog.html',
                    controller: 'ShareLocalFaceSetGlxssDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LocalFaceSet', function(LocalFaceSet) {
                            return LocalFaceSet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('share-local-face-set-glxss', null, { reload: 'share-local-face-set-glxss' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
    }

})();
