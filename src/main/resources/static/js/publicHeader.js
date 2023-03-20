console.log("--------publicHeaderApp--------")

let publicHeaderApp = new Vue({
    el: "#publicHeaderApp",
    data: {
        bookTypeList: [],
        currentUserAccount:'',
        editStatus:0//true是修改,false是刪除
    },
    methods: {
        initBookTypeList() {

            let vueObj = this;
             this.editStatus = 1
            $.get("/booktype/selectAll", function (data) {
                console.log(data)
                vueObj.bookTypeList = data;
            })

        }
        ,
        toAdd(){

        },
        toUpdate(bookTypeId){
            this.editStatus = 2
            console.log("当前typeId "+bookTypeId)

            $.post("/booktype/update",{id:bookTypeId},function (data) {

                if (data == "ok") {
                    alert("修改成功")
                } else {
                    alert("当前typeId "+data)
                    console.log("当前typeId "+data)
                }
            })
        },
        toDelete(bookTypeId){
            this.editStatus = 3
            console.log("当前typeId "+bookTypeId)

            $.post("/booktype/delete",{id:bookTypeId},function (data) {
                if (data == "ok") {
                    alert("刪除成功")
                } else {
                    alert("当前typeId "+data)
                    console.log("当前typeId "+data)
                }
            })
        },
        seeBooksOfType(bookTypeId){
            sessionStorage.setItem("seeBooksOfType",bookTypeId)
            location.href = "/booksOfType.html"
        }
        ,
        initCurrentUserAccount(){
            let vueObj = this;
            $.post("/user/getCurrentUserAccount",function (data) {
                console.log("当前用户名 "+data)
                vueObj.currentUserAccount = data;
            })
        }
        ,
        toLogout(){
            $.post("/user/logout",function (data) {
                location.href="/"
            })
        }
    },
    created() {
        this.initBookTypeList();

    }
})



