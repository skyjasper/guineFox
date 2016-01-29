/****
 * 验证手机号码(验证是11位数字)
 * @param param
 * @returns {boolean}
 */
function mobile_v(param) {
    return /^\d{11}$/.test(param);
}

/***
 * 验证邮箱
 * */
function email_v(param) {
    return /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test(param);
}