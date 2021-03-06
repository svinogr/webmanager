var upump = angular.module("UPump", ["ngRoute", "ui.bootstrap"]);
upump.factory("mails", function ($http, $q) {
    var getById = function (user) {
        var canceller = $q.defer();
        var cancel = function (reason) {
            canceller.resolve(reason);
        };
        var promise =
            $http.get("/api/user/" + user.id + "/mail", {timeout: canceller.promise})
                .then(function (response) {
                    return response.data.listMails;
                });
        return {
            promise: promise,
            cancel: cancel
        };
    };
    return {
        getById: getById
    };
});

upump.controller("MainCTRL", function ($scope, $http, $timeout, mails) {
    $scope.userStorage = [];

    $scope.createOrEdit = function (user) {
        $scope.editUser = user ? angular.copy(user) : {};
        $scope.show = true;
    };

    $scope.createMail = function (user) {
        $scope.editMail = {
            "parentId": user.id
        };
        $scope.showMail = true;
    };

    $scope.editMailFunction = function (mail) {
        $scope.editMail = mail ? angular.copy(mail) : {};
        $scope.showMail = true;
    };

    $scope.saveOrUpdateMail = function (editMail) {
        if (angular.isDefined(editMail.id)) {

            updateMail(editMail);
            $scope.showMail = false;
        } else {
            addMail(editMail);
            $scope.showMail = false;
        }
    };

    function addMail(editMail) {
        var req = {
                method: 'POST',
                url: '/api/user/' + editMail.parentId + '/mail/',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: {"mail": editMail.mail}
            }
            ;
        $http(req)
            .then(function (response) {
                    var data = response.data;

                    for (var i = 0; i < $scope.userStorage.length; i++) {

                        if ($scope.userStorage[i].id == editMail.parentId) {
                            var mail = {
                                "parentId": editMail.parentId,
                                "id": data.id,
                                "mail": data.mail
                            };
                            $scope.userStorage[i].listMails.push(mail);
                            break;
                        }
                    }
                }
                , function () {
                });
    }

    function updateMail(editMail) {
        var req = {
                method: 'PUT',
                url: '/api/user/mail/' + editMail.id,
                headers: {
                    'Content-Type': 'application/json'
                },
                data: {"mail": editMail.mail}
            }
            ;
        $http(req)
            .then(function () {
                for (var i = 0; i < $scope.userStorage.length; i++) {
                    if ($scope.userStorage[i].id == editMail.parentId) {
                        for (var k = 0; k < $scope.userStorage[i].listMails.length; k++) {
                            if ($scope.userStorage[i].listMails[k].id == editMail.id) {
                                $scope.userStorage[i].listMails[k].mail = editMail.mail;
                                break;
                            }
                        }

                    }
                }
            }, function () {
            });
    }

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

    $scope.deleteMail = function (mail) {
        var req = {
            method: 'DELETE',
            url: '/api/user/mail/' + mail.id,
            headers: {
                'Content-Type': 'application/json'
            }
        };
        $http(req)
            .then(function () {
                for (var i = 0; i < $scope.userStorage.length; i++) {
                    for (var k = 0; k < $scope.userStorage[i].listMails.length; k++) {
                        if ($scope.userStorage[i].listMails[k].id == mail.id) {
                            $scope.userStorage[i].listMails.splice($scope.userStorage[i].listMails.indexOf(mail), 1);
                            break;
                        }
                    }
                }
            });
    };

    $scope.saveOrUpdate = function (user) {
        if (angular.isDefined(user.id)) {
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
            .then(function (response) {
                var data = response.data;
                var user = {
                    "id": data.id,
                    "name": data.name,
                    "expand": false,
                    "listMails": []
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
        $scope.showMail = false;
    };

    var init = function () {
        var req = {
            method: 'GET',
            url: '/api/user/',
            'Accept': 'application/json'
        };
        $http(req)
            .then(function (response) {
                for (var i = 0; response.data.length; i++) {
                    var user = {
                        "id": response.data[i].id,
                        "name": response.data[i].name,
                        "expand": false,
                        "progress": false,
                        "listMails": []
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
                    $scope.userStorage[i].listMails = [];
                    $scope.userStorage[i].expand = false;
                }
            }
        } else {
            var request = mails.getById(user);

            request.promise.then(function (list) {
                var j;
                for (var i = 0; i < $scope.userStorage.length; i++) {
                    if ($scope.userStorage[i].id == user.id) {
                        $scope.userStorage[i].progress = true;
                        for (var k = 0; k < list.length; k++) {

                            var mail = {
                                "id": list[k].id,
                                "mail": list[k].mail,
                                "parentId": user.id
                            };
                            $scope.userStorage[i].listMails.push(mail);
                        }
                        j = i;

                        break;
                    }
                }
                $scope.userStorage[j].progress = true;
                var countUp = function () {
                    $scope.userStorage[j].expand = true;
                    $scope.userStorage[j].progress = false;
                };
                $timeout(countUp, 1000);
            }, function (reason) {
            });
        }
    }
})
;
