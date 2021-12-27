let express = require('express');
var bodyParser = require('body-parser');


let app = express();
    app.use(bodyParser.urlencoded({ extended: false }))
    app.use(bodyParser.json())
    let port = process.env.PORT || 3000;
    let con = require('./db.js');
    app.use(bodyParser.json({limit: "50mb"}));
    app.use(bodyParser.urlencoded({limit: "50mb", extended: true, parameterLimit:50000}));


    //===KNN_Không gian===
    app.route('/khonggian')
    .get((req,res)=>{
    con.query("SELECT * FROM knn_khonggian", function (err, result, fields) {
        if (err) throw err;
        var lichsu = result; 
        res.json(lichsu);
        });
    })
    .post((req, res) => {
        var {tentiem, soluong, khoangcach} = req.body;
        con.query(`INSERT INTO knn_khonggian (tentiem, soluong, khoangcach) 
        VALUES (${con.escape(tentiem)},${con.escape(soluong)}, ${con.escape(khoangcach)})`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
    })
    .delete((req, res) => {
            con.query("DELETE FROM knn_khonggian ", function (err, result) {
                if (err) throw err;
                res.json({status: 200})
            });
         })
    //===KNN_Dịch vụ===
    app.route('/dichvu')
    .get((req,res)=>{
    con.query("SELECT * FROM knn_dvanuong", function (err, result, fields) {
        if (err) throw err;
        var lichsu = result; 
        res.json(lichsu);
        });
    })
    .post((req, res) => {
        var {tentiem, soluong, khoangcach} = req.body;
        con.query(`INSERT INTO knn_dvanuong (tentiem, soluong, khoangcach) 
        VALUES (${con.escape(tentiem)},${con.escape(soluong)}, ${con.escape(khoangcach)})`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
    })
    .delete((req, res) => {
            con.query("DELETE FROM knn_dvanuong ", function (err, result) {
                if (err) throw err;
                res.json({status: 200})
            });
         })

    //===KNN_Số lượng người===
    app.route('/slnguoi')
    .get((req,res)=>{
    con.query("SELECT * FROM knn_soluongnguoi", function (err, result, fields) {
        if (err) throw err;
        var lichsu = result; 
        res.json(lichsu);
        });
    })
    .post((req, res) => {
        var {tentiem, soluong, khoangcach} = req.body;
        con.query(`INSERT INTO knn_soluongnguoi (tentiem, soluong, khoangcach) 
        VALUES (${con.escape(tentiem)},${con.escape(soluong)}, ${con.escape(khoangcach)})`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
    })
    .delete((req, res) => {
            con.query("DELETE FROM knn_soluongnguoi ", function (err, result) {
                if (err) throw err;
                res.json({status: 200})
            });
         })

    //===KNN_Bãi giữ xe===
    app.route('/baigiuxe')
    .get((req,res)=>{
    con.query("SELECT * FROM knn_baigiuxe", function (err, result, fields) {
        if (err) throw err;
        var lichsu = result; 
        res.json(lichsu);
        });
    })
    .post((req, res) => {
        var {tentiem, soluong, khoangcach} = req.body;
        con.query(`INSERT INTO knn_baigiuxe (tentiem, soluong, khoangcach) 
        VALUES (${con.escape(tentiem)},${con.escape(soluong)}, ${con.escape(khoangcach)})`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
    })
    .delete((req, res) => {
            con.query("DELETE FROM knn_baigiuxe ", function (err, result) {
                if (err) throw err;
                res.json({status: 200})
            });
         })


    //===Lịch sử quản trị viên===
    app.route('/lichsu_qtv')
    .get((req,res)=>{
    con.query("SELECT * FROM lichsu_qtv", function (err, result, fields) {
        if (err) throw err;
        var lichsu = result; 
        res.json(lichsu);
        });
    }).post((req, res) => {
        var {noidung, thoigian} = req.body;
        con.query(`INSERT INTO lichsu_qtv (noidung, thoigian) 
        VALUES (${con.escape(noidung)},  ${con.escape(thoigian)})`, function (err, result) {
            if (err) throw err;
            console.log("1 lich su duoc them");
            res.json({status: 200})
        });
    }).delete((req, res) => {
        con.query("DELETE FROM lichsu_qtv ", function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
     //===Lịch sử chủ tiệm===
     app.route('/lichsu_ct/:idchutiem')
     .get((req,res)=>{
     let idchutiem = req.params.idchutiem;
     con.query(`SELECT * FROM lichsu_ct WHERE idchutiem = ${con.escape(idchutiem)}`, function (err, result, fields) {
         if (err) throw err;
         var lichsu = result; 
         res.json(lichsu);
         });
     }).post((req, res) => {
         let idchutiem = req.params.idchutiem;
         var {noidung,thoigian} = req.body;
         con.query(`INSERT INTO lichsu_ct (idchutiem,noidung, thoigian) 
         VALUES (${con.escape(idchutiem)},${con.escape(noidung)},  ${con.escape(thoigian)}) `, function (err, result) {
             if (err) throw err;
             console.log("1 lich su duoc them");
             res.json({status: 200})
         });
     }).delete((req, res) => {
         let idchutiem = req.params.idchutiem;
         con.query(`DELETE FROM lichsu_ct WHERE idchutiem = ${con.escape(idchutiem)}`, function (err, result) {
             if (err) throw err;
             res.json({status: 200})
         });
      })
     //===Lịch sử nhân viên===
     app.route('/lichsu_nv/:idnhanvien')
    .get((req,res)=>{
    let idnhanvien = req.params.idnhanvien;
    con.query(`SELECT * FROM lichsu_nv WHERE idnhanvien = ${con.escape(idnhanvien)}`, function (err, result, fields) {
        if (err) throw err;
        var lichsu = result; 
        res.json(lichsu);
        });
    }).post((req, res) => {
        let idnhanvien = req.params.idnhanvien;
        var {noidung,thoigian} = req.body;
        con.query(`INSERT INTO lichsu_nv (idnhanvien,noidung, thoigian) 
        VALUES (${con.escape(idnhanvien)},${con.escape(noidung)},  ${con.escape(thoigian)}) `, function (err, result) {
            if (err) throw err;
            console.log("1 lich su duoc them");
            res.json({status: 200})
        });
    }).delete((req, res) => {
        let idnhanvien = req.params.idnhanvien;
        con.query(`DELETE FROM lichsu_nv WHERE idnhanvien = ${con.escape(idnhanvien)}`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
     //===Lịch sử khách hàng===
     app.route('/lichsu_kh/:idkhachhang')
    .get((req,res)=>{
    let idkhachhang = req.params.idkhachhang;
    con.query(`SELECT * FROM lichsu_kh WHERE idkhachhang = ${con.escape(idkhachhang)}`, function (err, result, fields) {
        if (err) throw err;
        var lichsu = result; 
        res.json(lichsu);
        });
    }).post((req, res) => {
        let idkhachhang = req.params.idkhachhang;
        var {noidung, thoigian} = req.body;
        con.query(`INSERT INTO lichsu_kh (idkhachhang,noidung, thoigian) 
        VALUES (${con.escape(idkhachhang)},${con.escape(noidung)},  ${con.escape(thoigian)}) `, function (err, result) {
            if (err) throw err;
            console.log("1 lich su duoc them");
            res.json({status: 200})
        });
    }).delete((req, res) => {
        let idkhachhang = req.params.idkhachhang;
        con.query(`DELETE FROM lichsu_kh WHERE idkhachhang = ${con.escape(idkhachhang)}`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
    //===Tài khoản===

    //taikhoan (GET(Lấy danh sách tài khoản)-POST)
    app.route('/taikhoan')
    .get((req,res)=>{
    con.query("SELECT * FROM taikhoan", function (err, result, fields) {
        if (err) throw err;
        var taikhoan = result; 
        res.json(taikhoan);
        });
    }).post((req, res) => {
        var {sdt , matkhau, hoten,hinhanh, diachi, quyen} = req.body;
        con.query(`INSERT INTO taikhoan (matkhau, hoten, hinhanh, diachi, sdt, quyen, sohuutiem) 
        VALUES (${con.escape(matkhau)},  ${con.escape(hoten)},${con.escape(hinhanh)},${con.escape(diachi)},${con.escape(sdt)},  ${con.escape(quyen)}, "0")`, function (err, result) {
            if (err) throw err;
            console.log("1 tai khoan duoc them");
            res.json({status: 200})
        });
    })
    //taikhoan (GET(Lấy 1 tài khoản)-PUT-DELETE)
    app.route('/taikhoan/:id')
    .get((req,res)=>{
        let id = req.params.id;
        con.query(`SELECT * FROM taikhoan WHERE id = ${con.escape(id)}`, function (err, result, fields) {
            if (err) throw err;
            var [taikhoan] = result; 
            res.json(taikhoan);
        });
    })
    .put((req,res)=>{
        let id = req.params.id;
        var {sdt, matkhau, hoten,hinhanh, diachi, quyen} = req.body;
        con.query(`UPDATE taikhoan SET sdt = ${con.escape(sdt)} , matkhau = ${con.escape(matkhau)} , hoten = ${con.escape(hoten)} ,hinhanh=${con.escape(hinhanh)}, diachi = ${con.escape(diachi)} , quyen = ${con.escape(quyen)} WHERE id = ${con.escape(id)}`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    }).delete((req, res) => {
        let id = req.params.id;
        con.query(`DELETE FROM taikhoan WHERE id = ${con.escape(id)}`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     }).patch((req,res)=>{
        let id = req.params.id;
        con.query(`UPDATE taikhoan SET sohuutiem = "1" WHERE id = ${con.escape(id)}`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    })
     //===Địa điểm===

    //diadiem (GET(Lấy danh sách địa điểm-POST)
    app.route('/diadiem')
    .get((req,res)=>{
    con.query("SELECT * FROM diadiem", function (err, result, fields) {
        if (err) throw err;
        var diadiem = result; 
        res.json(diadiem);
        });
    }).post((req, res) => {
        var {ten, diachi, kinhdo, vido,trangthai, idchu, hotenchu, ghichu, baigiuxe} = req.body;
        con.query(`INSERT INTO diadiem (ten, diachi, kinhdo, vido,trangthai, idchu, hotenchu, ghichu, baigiuxe) 
        VALUES (${con.escape(ten)}, ${con.escape(diachi)}, ${con.escape(kinhdo)}, ${con.escape(vido)},${con.escape(trangthai)}, ${con.escape(idchu)}, ${con.escape(hotenchu)},${con.escape(ghichu)},${con.escape(baigiuxe)})`, function (err, result) {
            if (err) throw err;
            console.log("1 dia diem duoc them");
            res.json({status: 200})
        });
    })
    //diadiem (GET(Lấy 1 địa điểm)-PUT-DELETE)
    app.route('/diadiem/:id')
    .get((req,res)=>{
        let id = req.params.id;
        con.query(`SELECT * FROM diadiem WHERE id = ${con.escape(id)}`, function (err, result, fields) {
            if (err) throw err;
            var [diadiem] = result; 
            res.json(diadiem);
        });
    })
    .put((req,res)=>{
        let id = req.params.id;
        var {ten, diachi, kinhdo, vido, trangthai, ghichu, baigiuxe} = req.body;
        con.query(`UPDATE diadiem SET ten = ${con.escape(ten)} , diachi = ${con.escape(diachi)} , kinhdo = ${con.escape(kinhdo)} , vido = ${con.escape(vido)} , trangthai = ${con.escape(trangthai)} , ghichu = ${con.escape(ghichu)}, baigiuxe = ${con.escape(baigiuxe)} WHERE id = ${con.escape(id)}`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    }).delete((req, res) => {
        let id = req.params.id;
        con.query(`DELETE FROM diadiem WHERE id = ${con.escape(id)}`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
     //===Nhân viên===

     //nhanvien (GET(Lấy danh sách nhân viên theo id của địa điểm)-POST)
    app.route('/nhanvien')
    .post((req, res) => {
        var {hoten,hinhanh, diachi, gioitinh,sdt, iddiadiem} = req.body;
        con.query(`INSERT INTO nhanvien (hoten,hinhanh, diachi, gioitinh, sdt, iddiadiem) 
        VALUES (${con.escape(hoten)},${con.escape(hinhanh)}, ${con.escape(diachi)}, ${con.escape(gioitinh)},${con.escape(sdt)}, ${con.escape(iddiadiem)})`, function (err, result) {
            if (err) throw err;
            console.log("1 nhan vien duoc them");
            res.json({status: 200})
        });
    }).get((req,res)=>{
        con.query("SELECT * FROM nhanvien ", function (err, result, fields) {
            if (err) throw err;
            var nhanvien = result; 
            res.json(nhanvien);
            });
        })
    app.route('/nhanvien/:iddiadiem')
    .get((req,res)=>{
    let iddiadiem = req.params.iddiadiem;
    con.query(`SELECT * FROM nhanvien WHERE iddiadiem = ${con.escape(iddiadiem)}`, function (err, result, fields) {
        if (err) throw err;
        var nhanvien = result; 
        res.json(nhanvien);
        });
    })
    //nhanvien (GET(Lấy 1 nhân viên theo id của địa điểm)-PUT-DELETE)
    app.route('/nhanvien/:iddiadiem/:id')
    .get((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`SELECT * FROM nhanvien WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)}) `, function (err, result, fields) {
            if (err) throw err;
            var [nhanvien] = result; 
            res.json(nhanvien);
        });
    })
    .put((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        var {hoten,hinhanh, diachi, gioitinh,sdt } = req.body;
        con.query(`UPDATE nhanvien SET hoten = ${con.escape(hoten)} ,hinhanh = ${con.escape(hinhanh)}, diachi = ${con.escape(diachi)} , gioitinh = ${con.escape(gioitinh)} ,sdt = ${con.escape(sdt)} WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    }).delete((req, res) => {
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`DELETE FROM nhanvien WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
     //===Kho===

    //kho (GET(Lấy danh sách vật phẩm theo id của địa điểm)-POST)
    app.route('/kho')
    .post((req, res) => {
        var {tenvatpham, soluong, dongia, hinhanh, iddiadiem} = req.body;
        con.query(`INSERT INTO kho (tenvatpham, soluong, dongia, hinhanh, iddiadiem) 
        VALUES (${con.escape(tenvatpham)}, ${con.escape(soluong)}, ${con.escape(dongia)}, ${con.escape(hinhanh)}, ${con.escape(iddiadiem)})`, function (err, result) {
            if (err) throw err;
            console.log("1 vat pham duoc them");
            res.json({status: 200})
        });
    })
    app.route('/kho/:iddiadiem')
    .get((req,res)=>{
    let iddiadiem = req.params.iddiadiem;
    con.query(`SELECT * FROM kho WHERE iddiadiem = ${con.escape(iddiadiem)}`, function (err, result, fields) {
        if (err) throw err;
        var kho = result; 
        res.json(kho);
        });
    })
    //kho (PUT-DELETE)
    app.route('/kho/:iddiadiem/:id')
    .get((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`SELECT * FROM kho WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)}) `, function (err, result, fields) {
            if (err) throw err;
            var [vatpham] = result; 
            res.json(vatpham);
        });
    })
    .put((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        var {tenvatpham, soluong, dongia, hinhanh} = req.body;
        con.query(`UPDATE kho SET tenvatpham = ${con.escape(tenvatpham)} , soluong = ${con.escape(soluong)} , dongia = ${con.escape(dongia)} , hinhanh = ${con.escape(hinhanh)}  WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    }).delete((req, res) => {
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`DELETE FROM kho WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
     //===Bàn bida===

    //banbida (GET(Lấy danh sách bàn theo id của địa điểm)-POST)

    app.route('/ban/:id')
    .get((req,res)=>{
    let id = req.params.id;
    con.query(`SELECT * FROM banbida WHERE id = ${con.escape(id)}`, function (err, result, fields) {
        if (err) throw err;
        var [banbida] = result; 
        res.json(banbida);
        });
    })
    .put((req,res)=>{
        let id = req.params.id;
        var {tenban, gia,soluong, hinhanh} = req.body;
        con.query(`UPDATE banbida SET tenban = ${con.escape(tenban)} , gia = ${con.escape(gia)} ,soluong=${con.escape(soluong)}, hinhanh = ${con.escape(hinhanh)}  WHERE id = ${con.escape(id)}`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    })

    app.route('/banbida')
    .post((req, res) => {
        var {tenban, gia,soluong, hinhanh, iddiadiem} = req.body;
        con.query(`INSERT INTO banbida (tenban, gia ,soluong, hinhanh, iddiadiem) 
        VALUES (${con.escape(tenban)}, ${con.escape(gia)},${con.escape(soluong)}, ${con.escape(hinhanh)}, ${con.escape(iddiadiem)})`, function (err, result) {
            if (err) throw err;
            console.log("1 ban bida duoc them");
            res.json({status: 200})
        });
    })
    app.route('/banbida/:iddiadiem')
    .get((req,res)=>{
    let iddiadiem = req.params.iddiadiem;
    con.query(`SELECT * FROM banbida WHERE iddiadiem = ${con.escape(iddiadiem)}`, function (err, result, fields) {
        if (err) throw err;
        var banbida = result; 
        res.json(banbida);
        });
    })
    //banbida (GET(Lấy 1 bàn bida theo id của địa điểm)-PUT-DELETE)
    app.route('/banbida/:iddiadiem/:id')
    .get((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`SELECT * FROM banbida WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)}) `, function (err, result, fields) {
            if (err) throw err;
            var [banbida] = result; 
            res.json(banbida);
        });
    })
    .put((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        var {tenban, gia,soluong, hinhanh} = req.body;
        con.query(`UPDATE banbida SET tenban = ${con.escape(tenban)} , gia = ${con.escape(gia)} ,soluong=${con.escape(soluong)}, hinhanh = ${con.escape(hinhanh)}  WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    }).delete((req, res) => {
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`DELETE FROM banbida WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
    //===Phiếu nhập===

    //phieunhap (GET(Lấy danh sách phiếu nhập theo id của địa điểm)-POST)
    app.route('/phieunhap')
    .post((req, res) => {
        var {ngay, idnhanvien, tennhanvien, iddiadiem, tienthanhtoan, trangthai} = req.body;
        con.query(`INSERT INTO phieunhap (ngay, idnhanvien, tennhanvien, iddiadiem, tienthanhtoan, trangthai) 
        VALUES (${con.escape(ngay)}, ${con.escape(idnhanvien)}, ${con.escape(tennhanvien)}, ${con.escape(iddiadiem)}, ${con.escape(tienthanhtoan)}, ${con.escape(trangthai)})`, function (err, result) {
            if (err) throw err;
            console.log("1 phieu nhap duoc them");
            res.json({status: 200})
        });
    })
    app.route('/phieunhap/:iddiadiem')
    .get((req,res)=>{
    let iddiadiem = req.params.iddiadiem;
    con.query(`SELECT * FROM phieunhap WHERE iddiadiem = ${con.escape(iddiadiem)}`, function (err, result, fields) {
        if (err) throw err;
        var phieunhap = result; 
        res.json(phieunhap);
        });
    })
    //phieunhap (GET(Lấy 1 phiếu nhập theo id của địa điểm)-PUT-DELETE)
    app.route('/phieunhap/:iddiadiem/:id')
    .get((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`SELECT * FROM phieunhap WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)}) `, function (err, result, fields) {
            if (err) throw err;
            var [phieunhap] = result; 
            res.json(phieunhap);
        });
    })
    .put((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        var {ngay, idnhanvien, tennhanvien, tienthanhtoan, trangthai} = req.body;
        con.query(`UPDATE phieunhap SET ngay = ${con.escape(ngay)} , idnhanvien = ${con.escape(idnhanvien)} ,tennhanvien=${con.escape(tennhanvien)}, iddiadiem = ${con.escape(iddiadiem)} ,tienthanhtoan = ${con.escape(tienthanhtoan)},trangthai = ${con.escape(trangthai)}  WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    })
    .delete((req, res) => {
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`DELETE FROM phieunhap WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
     //===Chi tiết phiếu nhập===

    //ctpn (GET-POST-DELETE)
    app.route('/ctphieunhap/:idphieunhap')
    .get((req,res)=>{
    let idphieunhap = req.params.idphieunhap;
    con.query(`SELECT * FROM ctpn WHERE idphieunhap = ${con.escape(idphieunhap)}`, function (err, result, fields) {
        if (err) throw err;
        var ctpn = result; 
        res.json(ctpn);
        });
    }).post((req, res) => {
        var {idphieunhap, tenvatpham,hinhanh, soluong, dongia} = req.body;
        con.query(`INSERT INTO ctpn (idphieunhap, tenvatpham,hinhanh, soluong, dongia) 
        VALUES (${con.escape(idphieunhap)}, ${con.escape(tenvatpham)},${con.escape(hinhanh)}, ${con.escape(soluong)}, ${con.escape(dongia)})`, function (err, result) {
            if (err) throw err;
            console.log("1 ctpn duoc them");
            res.json({status: 200})
        });
    })
    app.route('/ctphieunhap/:id')
    .delete((req, res) => {
        let id = req.params.id;
        con.query(`DELETE FROM ctpn WHERE id = ${con.escape(id)}`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
    //===Booking===

    //booking (GET(Lấy danh sách booking theo id của địa điểm)-POST)
    app.route('/booking')
    .post((req, res) => {
        var {idkhachhang, tenkhachhang, sdt, ngay,gio,trangthai,idnhanvien,tennhanvien, tienthanhtoan, iddiadiem,qrcode, slnguoi, idban, tenban, giaban,ngaychoi, giochoi, giochoithat } = req.body;
        con.query(`INSERT INTO booking (idkhachhang, tenkhachhang, sdt, ngay,gio,trangthai,idnhanvien,tennhanvien, tienthanhtoan, iddiadiem,qrcode, slnguoi, idban, tenban, giaban, ngaychoi, giochoi, giochoithat ) 
        VALUES (${con.escape(idkhachhang)}, ${con.escape(tenkhachhang)}, ${con.escape(sdt)}, ${con.escape(ngay)}, ${con.escape(gio)}, ${con.escape(trangthai)}, ${con.escape(idnhanvien)},  ${con.escape(tennhanvien)} , ${con.escape(tienthanhtoan)} , ${con.escape(iddiadiem)} ,"", ${con.escape(slnguoi)}, ${con.escape(idban)}, ${con.escape(tenban)}, ${con.escape(giaban)}, ${con.escape(ngaychoi)}, ${con.escape(giochoi)},"")`, function (err, result) {
            if (err) throw err;
            console.log("1 booking duoc them");
            res.json({status: 200})
        });
    }).get((req,res)=>{
    con.query(`SELECT * FROM booking `, function (err, result, fields) {
        if (err) throw err;
        var booking = result; 
        res.json(booking);
        });
    })
    app.route('/booking/:iddiadiem')
    .get((req,res)=>{
    let iddiadiem = req.params.iddiadiem;
    con.query(`SELECT * FROM booking WHERE iddiadiem = ${con.escape(iddiadiem)}`, function (err, result, fields) {
        if (err) throw err;
        var booking = result; 
        res.json(booking);
        });
    })

    //sua Booking theo id
    app.route('/booking/:id')
    .put((req,res)=>{
        let id = req.params.id;
        var {idkhachhang, tenkhachhang, sdt, ngay,gio,trangthai,idnhanvien,tennhanvien, tienthanhtoan, qrcode, slnguoi, idban, tenban, giaban, ngaychoi, giochoi, giochoithat} = req.body;
        con.query(`UPDATE booking SET idkhachhang = ${con.escape(idkhachhang)} , tenkhachhang = ${con.escape(tenkhachhang)} , sdt = ${con.escape(sdt)} , ngay = ${con.escape(ngay)}, gio = ${con.escape(gio)} , trangthai = ${con.escape(trangthai)} , idnhanvien = ${con.escape(idnhanvien)}, tennhanvien = ${con.escape(tennhanvien)} , tienthanhtoan = ${con.escape(tienthanhtoan)} , qrcode = ${con.escape(qrcode)} , slnguoi = ${con.escape(slnguoi)}, idban = ${con.escape(idban)}, tenban = ${con.escape(tenban)}, giaban = ${con.escape(giaban)}, ngaychoi =${con.escape(ngaychoi)}, giochoi =${con.escape(giochoi)}, giochoithat =${con.escape(giochoithat)}   WHERE id = ${con.escape(id)}`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    })
    //booking (GET(Lấy 1 booking theo id của địa điểm)-PUT-DELETE)
    app.route('/booking/:iddiadiem/:id')
    .get((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`SELECT * FROM booking WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)}) `, function (err, result, fields) {
            if (err) throw err;
            var [booking] = result; 
            res.json(booking);
        });
    })
    .put((req,res)=>{
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        var {idkhachhang, tenkhachhang, sdt, ngay,gio,trangthai,idnhanvien,tennhanvien, tienthanhtoan, qrcode, slnguoi, idban, tenban, giaban, ngaychoi, giochoi, giochoithat} = req.body;
        con.query(`UPDATE booking SET idkhachhang = ${con.escape(idkhachhang)} , tenkhachhang = ${con.escape(tenkhachhang)} , sdt = ${con.escape(sdt)} , ngay = ${con.escape(ngay)}, gio = ${con.escape(gio)} , trangthai = ${con.escape(trangthai)} , idnhanvien = ${con.escape(idnhanvien)}, tennhanvien = ${con.escape(tennhanvien)} , tienthanhtoan = ${con.escape(tienthanhtoan)} , qrcode = ${con.escape(qrcode)} , slnguoi = ${con.escape(slnguoi)}, idban = ${con.escape(idban)}, tenban = ${con.escape(tenban)}, giaban = ${con.escape(giaban)}, ngaychoi =${con.escape(ngaychoi)}, giochoi =${con.escape(giochoi)}, giochoithat =${con.escape(giochoithat)}   WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result, fields) {
            if (err) throw err;
            res.json({status: 200})
        });
    }).delete((req, res) => {
        let iddiadiem = req.params.iddiadiem;
        let id = req.params.id;
        con.query(`DELETE FROM booking WHERE (iddiadiem = ${con.escape(iddiadiem)} AND id = ${con.escape(id)})`, function (err, result) {
            if (err) throw err;
            res.json({status: 200})
        });
     })
     //===Chi tiết booking===
     app.route('ctbooking')
     .get((req,res)=>{
        con.query(`SELECT * FROM ctbooking`, function (err, result, fields) {
            if (err) throw err;
            var ctbooking = result; 
            res.json(ctbooking);
            });
        })
     //ctbooking (GET-POST-PUT-DELETE)
     app.route('/ctbooking/:idbooking')
     .get((req,res)=>{
     let idbooking = req.params.idbooking;
     con.query(`SELECT * FROM ctbooking WHERE idbooking = ${con.escape(idbooking)}`, function (err, result, fields) {
         if (err) throw err;
         var ctbooking = result; 
         res.json(ctbooking);
         });
     }).post((req, res) => {
         var {idbooking,  idvatpham, tenvatpham, soluong, dongia, hinhanh} = req.body;
         con.query(`INSERT INTO ctbooking (idbooking, idvatpham, tenvatpham, soluong, dongia, hinhanh) 
         VALUES (${con.escape(idbooking)} , ${con.escape(idvatpham)}, ${con.escape(tenvatpham)}, ${con.escape(soluong)}, ${con.escape(dongia)},${con.escape(hinhanh)})`, function (err, result) {
             if (err) throw err;
             console.log("1 ctbooking duoc them");
             res.json({status: 200})
         });
     })
     .put((req,res)=>{
        let idbooking = req.params.idbooking;
         var {idvatpham, tenvatpham, soluong, dongia, hinhanh} = req.body;
         con.query(`UPDATE ctbooking SET idbooking = ${con.escape(idbooking)}, idvatpham = ${con.escape(idvatpham)} , tenvatpham = ${con.escape(tenvatpham)} , soluong = ${con.escape(soluong)} , dongia = ${con.escape(dongia)}, hinhanh = ${con.escape(hinhanh)} WHERE idbooking = ${con.escape(idbooking)}`, function (err, result, fields) {
             if (err) throw err;
             res.json({status: 200})
         });
     }).delete((req, res) => {
        let idbooking = req.params.idbooking;
         con.query(`DELETE FROM ctbooking WHERE id = ${con.escape(idbooking)}`, function (err, result) {
             if (err) throw err;
             res.json({status: 200})
         });
      })
app.listen(port, () => {
        console.log(`Example app listening at http://localhost:${port}`)
        }) 