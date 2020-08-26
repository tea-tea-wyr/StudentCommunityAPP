/*
 * function:路由配置
 */
"use strict"

const express = require("express");

const router = express.Router();

//路由列表
router.use("/login", require("./login.js"));
router.use("/getlogin", require("./get_login_state.js"));
router.use("/get_user_info", require("./get_user_info.js"));
router.use("/get_user_publish", require("./get_user_publish.js"));
router.use("/get_part_data", require("./get_part_data.js"));
router.use("/get_data", require("./get_data.js"));
router.use("/get_comment", require("./get_comment.js"));
router.use("/get_my_follow", require("./get_my_follow.js"));
router.use("/send_comment", require("./send_comment.js"));
router.use("/loginout", require("./loginout.js"));
router.use("/get_classtable", require("./get_classtable.js"));
router.use("/publish", require("./publish.js"));
router.use("/publish_audio", require("./publish_audio.js"));
router.use("/publish_video", require("./publish_video.js"));
router.use("/set_classtable", require("./set_classtable.js"));
router.use("/get_detail", require("./get_detail.js"));
module.exports = router;