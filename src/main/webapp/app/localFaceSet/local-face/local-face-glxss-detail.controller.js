(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LocalFaceGlxssDetailController', LocalFaceGlxssDetailController);

    LocalFaceGlxssDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LocalFace', 'LocalFaceSet'];

    function LocalFaceGlxssDetailController($scope, $rootScope, $stateParams, previousState, entity, LocalFace, LocalFaceSet) {
        var vm = this;

        vm.localFace = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('glxssSecurityApp:localFaceUpdate', function(event, result) {
            vm.localFace = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
