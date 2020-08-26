/**
 * 获取首页中所需要显示的资料列表（部分）
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
router.get("/", function (req, res,next) {
    if("op" in req.query){
        let option;
        let max; 
        //正则式
        let max_reg = new RegExp("^\\d+$");
        
        if ("max" in req.query) {
            if (!max_reg.test(req.query.max)) {
                if (req.query.max < 1) {
                    res.send(return_obj.fail("101", "传入参数格式有误"));
                    return ;
                }
            }
        }
        if(req.query.op=="article"){
            //获取文章列表
            option="if_article = 1" ;
        }else if(req.query.op=="video"){
            //获取视频列表
            option="if_video =1" ;
        }else if(req.query.op=="audio"){
            //获取音频列表
            option="if_audio =1" ;
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
        limit 0,?`;
        if("max"in req.query) max=req.query.max*1;
        else max=6;
        DBHelp(SQL,[max],(error, result, fields) =>{
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
    }else {
        res.send(return_obj.fail("100", "缺少必要的参数"));
        return;
    }
})

module.exports = router;