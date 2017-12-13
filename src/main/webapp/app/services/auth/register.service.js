(function () {
    'use strict';

    angular
        .module('glxssSecurityApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
