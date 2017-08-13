var upump = angular.module("UPump", ["ngRoute", "ui.bootstrap"]);
upump.directive('modalDialog', function () {
    return {
        restrict: 'E',
        scope: {
            show: '='
        },
        replace: true, // Замените на шаблон
        transclude: true, // мы хотим вставлять пользовательский контент внутри директивы
        link: function (scope, element, attrs) {
            scope.dialogStyle = {};

            if (attrs.width) {
                scope.dialogStyle.width = attrs.width;
            }

            if (attrs.height) {
                scope.dialogStyle.height = attrs.height;
            }

            scope.hideModal = function () {
                scope.show = false;
            };
        },
        template: '...' // Смотрите ниже
    };
});

upump.controller("MainCTRL", function ($scope, $http) {
    $scope.userStorage = [];
    $scope.createOrEdit = function (user) {

        $scope.editUser = user ? angular.copy(user) : {};
        $scope.show = true;
    };

    $scope.createPhone = function (user) {
        alert(user.id);
        $scope.editPhone ={
            "parentId":user.id
        };
        $scope.showPhone = true;
        
    };


    $scope.editPhone = function (phone) {

        $scope.editPhone = phone ? angular.copy(phone) : {};
        // alert(editPhone.number);
        $scope.showPhone = true;
    };

    $scope.saveOrUpdatePhone = function (editPhone) {
        if (angular.isDefined(editPhone.id)) {
            alert(editPhone.id);
            alert(editPhone.parentId);
            updatePhone(phone)
        } else {
            alert(editPhone.number);
            createPhone(editPhone);

        }
    };


    $scope.deleteUser = function (user) {
        var req = {
                method: 'DELETE',
                url: '/api/user/' + user.id,
                headers: {
                    'Content-Type': 'application/json'
                }
            }
            ;
        $http(req)
            .then(function () {
                $scope.userStorage.splice($scope.userStorage.indexOf(user), 1);
            }, function () {
            });
    };

    $scope.saveOrUpdate = function (user) {
        if (angular.isDefined(user.id)) {
            alert(user.id);
            updateUser(user)
        } else {
            createUser(user);

        }
    };

    function createUser(user) {
        var req = {
                method: 'POST',
                url: '/api/user/',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: {"name": user.name}
            }
            ;
        $http(req)
            .then(function (respons) {
                var data = respons.data;
                var user = {
                    "id": data.id,
                    "name": data.name,
                    "expand": false,
                    "listPhones": []
                };
                $scope.userStorage.push(user);

                $scope.show = false;
            }, function () {
            });


    }

    function updateUser(user) {
        var req = {
                method: 'PUT',
                url: '/api/user/' + user.id,
                headers: {
                    'Content-Type': 'application/json'
                },
                data: {"name": user.name}
            }
            ;
        $http(req)
            .then(function () {
                for (var i = 0; i < $scope.userStorage.length; i++) {
                    if ($scope.userStorage[i].id == user.id) {
                        $scope.userStorage[i].name = user.name;
                        break;
                    }
                }
                $scope.show = false;

            }, function () {
            });
    }


    $scope.cancelEdit = function () {
        $scope.show = false;
        $scope.showPhone = false;
    };
    var init = function () {
        var req = {
            method: 'GET',
            url: '/api/user/'
        };
        $http(req)
            .then(function (response) {
                for (var i = 0; response.data.length; i++) {
                    var user = {
                        "id": response.data[i].id,
                        "name": response.data[i].name,
                        "expand": false,
                        "listPhones": []
                    };
                    $scope.userStorage.push(user);
                }

            }, function () {

            });
    };
    init();
    $scope.openNode = function (user) {
        if (user.expand) {
            for (var i = 0; i < $scope.userStorage.length; i++) {
                if ($scope.userStorage[i].id == user.id) {
                    $scope.userStorage[i].listPhones = [];
                    $scope.userStorage[i].expand = false;
                }
            }
        } else {

            var req = {
                method: 'GET',
                url: '/api/user/' + user.id + '/phone',
                headers: {
                    'Content-Type': 'application/json'
                }
            };
            $http(req)
                .then(function (response) {
                    var node = response.data;
                    for (var i = 0; i < $scope.userStorage.length; i++) {
                        if ($scope.userStorage[i].id == user.id) {
                            for (var k = 0; k < node.listPhones.length; k++) {
                                var phone = {
                                    "id": node.listPhones[k].id,
                                    "number": node.listPhones[k].number,
                                    "parentId": user.id
                                };
                                $scope.userStorage[i].listPhones.push(phone)

                            }
                            $scope.userStorage[i].expand = true;

                        }
                    }

                }, function () {
                });

        }
    }


})
;
