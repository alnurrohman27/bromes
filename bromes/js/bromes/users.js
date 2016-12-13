function datatables_users($scope,DTOptionsBuilder,$http,SweetAlert,$uibModal){
    $scope.dtOptions = DTOptionsBuilder.newOptions()
        .withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {
                extend: 'copy',                 
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                } 
            },
            {
                extend: 'csv',
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                } 
            },
            {
                extend: 'excel', 
                title: 'Daftar Akun ',
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                }
            },
            {
                extend: 'pdf', 
                title: 'Daftar Akun ',
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                }
            },
            {
                extend: 'print',
                exportOptions: {
                    columns: [ 0, 1, 2 ]
                },
                customize: function (win){
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]);

    var data;

    var config = {
        headers : {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
        }
    }

    $scope.refresh_table = function(){
        data = $.param({
            table:"user"
        });
        $http.post("php/search.php", data, config).
            success(function(data, status, headers, config) {
                $scope.users = data;
            });
    }
    $scope.refresh_table();

    $scope.insert_user = function(){
        data = $.param({
            table:"user",
            username:$scope.username,
            password:$scope.password,
            name:$scope.name,
        });
        $http.post("php/insert.php", data, config).
            success(function(data, status, headers, config) {
                console.log(data);
            });
        setTimeout(function(){  $scope.refresh_table(); }, 200);
    }

    $scope.delete = function (username) {
        SweetAlert.swal({
                title: "Apakah anda yakin?",
                text: "Anda tidak akan bisa mengembalikan data yang sudah terhapus!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Ya, silahkan!",
                cancelButtonText: "Tidak, jangan lakukan!",
                closeOnConfirm: false,
                closeOnCancel: false },
            function (isConfirm) {
                if (isConfirm) {
                    data = $.param({
                        table:"user",
                        key:$scope.username
                    });
                    $http.post("php/delete.php", data, config).
                        success(function(data, status, headers, config) {
                            console.log(data);
                            if(response.data == 1){
                                SweetAlert.swal("Terhapus!", "Data berhasil dihapus.", "success");
                            }
                            else{
                                SweetAlert.swal("Gagal", "Data akun gagal dihapus.", "error");
                            }
                        });
                    setTimeout(function(){  $scope.refresh_table(); }, 200);
                } else {
                    SweetAlert.swal("Dibatalkan", "Data akun aman :)", "error");
                }
            });
    }

    $scope.reset_form = function (){
        $scope.username = null;
        $scope.password = null;
        $scope.name = null;
    }

    $scope.open_modal = function (username,password,name){
        $scope.username = username;
        $scope.password = password;
        $scope.name = name;
        var modalInstance = $uibModal.open({
            templateUrl: 'views/user-management/m_users.html',
            scope: $scope,
            controller: modal_users
        });
    }
}

function modal_users($scope,$http,$uibModalInstance){
    $scope.update_user = function () {
        var data = $.param({
            table:"user", username:$scope.username, password:$scope.password, name:$scope.name
        });
    
        var config = {
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        }

        $http.post("php/update.php", data, config).
            success(function(data, status, headers, config) {
                if(data==0){
                    $scope.message = "Gagal update user";
                }
                else{
                    console.log(data);
                    $scope.refresh_table();
                    $uibModalInstance.close();
                }
            }).
            error(function(data, status, headers, config) {
                $scope.message = "Gagal update user";
            });
    };

    $scope.cancel = function () {
        $scope.reset_form();
        $uibModalInstance.dismiss('cancel');
    };
}

angular
    .module('dyanis')
    .controller('datatables_users', datatables_users)
    .controller('modal_users', modal_users);