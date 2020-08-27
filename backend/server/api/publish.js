/*
* function:用户发布资料
*/
'use strict'
const express = require('express');
const return_obj = require('../tool/return_obj.js');
const verify_login = require('../middleware/verify_login.js')
const router = express.Router();
const DBHelp = require('../config/DBHelp.js');
const async = require('async');
const moment = require('moment');
const multer = require('multer');
const multer_conf = require("../config/multer_conf.js");
//var req.body.,Files;

//创建mupload
const upload = multer(multer_conf.picture).single('photo');
//const path_picture="/home/ubuntu/Data/Picture"

//使用登录状态验证中间件
router.post("/", verify_login);

//upload错误处理部分

router.post("/",function(req,res){
    async.waterfall([
        function (done) {
            upload(req, res, function (err) {
                //
                if (err) {
                    console.log(err)
                    if (err.message == "upload file mimetype error") {
                        return res.send(return_obj.fail("115", "上传文件类型出错"));
                    }
                    if (err.code == "LIMIT_FILE_COUNT") {
                        return res.send(return_obj.fail("116", "上传文件超出数量限制"));
                    }
                    
                    return res.send(return_obj.fail("500", "出乎意料的错误"));
                }
                done(null)
            })},
        function(done){
            console.log('fields',req.body);
            if(req.body.title == undefined || req.body.introduction == undefined || req.body.content == undefined )
            {
                res.send(return_obj.fail("100", "缺少必要的参数"));
                
                return ;
            }
            /*if(req.body.if_video== '1' && req.body.video==undefined ||req.body.if_audio== '1' &&req.body.audio==undefined)
            {
                res.send(return_obj.fail("100", "缺少必要的参数"));
                return ;
            }*/
            done(null)
        },
        function(done){
            //sql语句
        let sql=`
            insert into Data(
                dataid,
                if_article,
                if_video,
                if_audio,
                title,
                introduction,
                content,
                picture,
                time
            ) value(?,?,?,?,?,?,?,?,?)
        `
        let timeStamp=moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        
        let picture_realpath="http://81.70.27.208:8088/picture/"+req.file.filename;
        //let video=""+
        let dataid=Date.now();
        let data_info=[
            dataid,
            '1',
            '0',
            '0',
            req.body.title,
            req.body.introduction,
            req.body.content,
            picture_realpath,
            timeStamp
        ]
        DBHelp(sql,data_info,(err,result,field)=>{
            if(err){
                console.log(err);
                res.send(return_obj.fail("200","调用数据库接口错误"))
                return ;
            }
            
            done(null,data_info[0]);
        })
        },
        function(dataid,done){
            let sql=`
                insert into Publish(
                    userid,
                    dataid
                ) value(?,?)
            `
            let publish_info=[
                req.session.userid,
                dataid
            ]
            DBHelp(sql,publish_info,(err,result,field)=>{
                if(err){
                    console.log(err);
                    res.send(return_obj.fail("200","调用数据库接口错误"))
                    return ;
                }
                done(null,result)
            })
        }
    ],
        function(err,result){
            if(err){
                console.log(err);
                return ;
            }
            res.send(return_obj.success({
                msg: "发布资料成功"
            }));
        }
    )
    
     
    //next();
})

module.exports = router;