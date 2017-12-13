(function () {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('PluginGlxssCarInfoController', PluginGlxssCarInfoController);

    PluginGlxssCarInfoController.$inject = ['previousState', 'entity'];

    function PluginGlxssCarInfoController(previousState, entity) {
        var vm = this;
        vm.ownerPerson = entity;
        vm.carInfo = entity;
        vm.previousState = previousState.name;
        vm.workRecordsType = entity.type;
        vm.workRecordId = previousState.params.id;
    }
})();
