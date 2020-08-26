/**
 * 按类别获取全部资料的列表
 * @type {Pool}
 */
"use strict"

const DBHelp = require('../config/DBHelp.js');
const express = require("express");
//const crypto = require("crypto");
const verify_login = require('../middleware/verify_login.js')
const verify_no_login = require('../middleware/verify_no_login.js');
const return_obj = require('../tool/return_obj.js');
const { success } = require("../tool/return_obj");
const router = express.Router();

//进行参数检查
router.get("/",function(req,res,next){
    if("op" in req.query && "page" in req.query) ;
    else {
        res.send(return_obj.fail("100", "缺少必要的参数"));
        return ;
    }
    //var option;
    
    //正则式
    var pcount_reg = new RegExp("^\\d+$");
    var page_reg = new RegExp("^\\d+$");
    if ("pcount" in req.query) {
        if (!pcount_reg.test(req.query.pcount)) {
            if (req.query.pcount< 1) {
                res.send(return_obj.fail("101", "传入参数格式有误"));
                return ;
            }
        }
    }
    if ("page" in req.query) {
        if (!page_reg.test(req.query.page)) {
            if (req.query.page < 1) {
                res.send(return_obj.fail("101", "传入参数格式有误"));
                return ;
            }
        }
    }
    next();
})

router.get("/", function (req, res,next) {
    let page; //第几页
    let pcount; //每页数据
    let option;
        if(req.query.op=="article"){
            //获取文章列表
            option="if_article = 1 and if_video =0 and if_audio =0" ;
        }else if(req.query.op=="video"){
            //获取视频列表
            option="if_article = 0 and if_video =1 and if_audio =0" ;
        }else if(req.query.op=="audio"){
            //获取音频列表
            option="if_article = 0 and if_video =0 and if_audio =1" ;
        }else {
            res.send(return_obj.fail("112", "资料类型输入有误"));
            return;
        }
        // SQL 语句
        var SQL = `
        select  
            dataid,
            title,
            introduction,
            picture,
            time
        from Data
        where ${option}
        order by time desc
        limit ?,?`;
        let param_list = [];
        //默认十条
        if("pcount" in req.query) pcount=req.query.pcount*1;
        else pcount=10;

        page=req.query.page*1;
        param_list.push((page-1)*pcount);
        //param_list.push(page*pcount-1);
        param_list.push(pcount);
        DBHelp(SQL,param_list,(error, result, fields) =>{
            let res_list;
            if(error){
                //resultData.error = error.message
                console.error(error);
                res.send(return_obj.fail("200", "调用数据库接口错误"));
                return;
            }
            if(result.length==0){
                res.send(return_obj.fail("125", "数据库无该类型的资料"));
                return;
            }
            
            res.send(return_obj.success({
                "msg": "获取数据成功",
                "data_info": result
            }));
            return ;
        })
    
})

module.exports = router;