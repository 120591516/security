(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('LocalFaceSetShareGlxssDeleteController',LocalFaceSetShareGlxssDeleteController);

    LocalFaceSetShareGlxssDeleteController.$inject = ['$scope', '$uibModalInstance', 'entity', 'LocalFaceSet', '$stateParams'];

    function LocalFaceSetShareGlxssDeleteController($scope, $uibModalInstance, entity, LocalFaceSet, $stateParams) {
        var vm = this;

        vm.user = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete () {
            LocalFaceSet.shareDelete({id : $stateParams.id, userId: vm.user.id}
            ,onSaveSuccess, onSaveError);
        }

        function onSaveError () {
            $uibModalInstance.close(true);
        }

        function onSaveSuccess (result) {
            $scope.$emit('glxssSecurityApp:localFaceSetUpdate', result);
            $uibModalInstance.close(result);
        }
    }
})();
