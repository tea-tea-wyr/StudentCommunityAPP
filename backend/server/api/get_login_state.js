/*
* function:获取登录状态
*/
'use strict'
const express = require('express');
const return_obj = require('../tool/return_obj.js');
const router = express.Router();


router.get("/", function (req, res, next) {
    if (req.session.is_login==true) {
        res.send(return_obj.success({
            msg: "用户已登录",
            is_login: true
        }))
    } else {
        res.send(return_obj.success({
            msg: "用户未登录",
            is_login: false
        }));
    }
})

module.exports = router;