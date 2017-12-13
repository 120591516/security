(function() {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'WorkRecordStats'];

    function HomeController ($scope, Principal, LoginService, $state, WorkRecordStats) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = Principal.isAuthenticated();
        vm.login = LoginService.open;
        vm.register = register;
        vm.statsDays = 7;
        vm.userStatsSize = 5;

        $scope.$on('authenticationSuccess', function() {
            console.log("authenticationSuccess");
            getAccount();
            loadStats();
        });

        if(!vm.isAuthenticated){
            login();
        } else {
            loadStats();
        }

        function loadStats() {
            WorkRecordStats.getStatsOption(1, 'carPlate', vm.statsDays).promise
                .then(function(result) {
                    vm.carPlateStatsOption = result;
                });
            WorkRecordStats.getStatsOption(2, 'face', vm.statsDays).promise
                .then(function(result) {
                    vm.faceStatsOption = result;
                });
            WorkRecordStats.getUserStatsOption(1, 'carPlate', vm.statsDays, vm.userStatsSize).promise
                .then(function(result) {
                    vm.carPlateUserStatsOption = result;
                });
            WorkRecordStats.getUserStatsOption(2, 'face', vm.statsDays, vm.userStatsSize).promise
                .then(function(result) {
                    vm.faceUserStatsOption = result;
                });
        }

        function login() {
            collapseNavbar();
            LoginService.open();
        }
        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
