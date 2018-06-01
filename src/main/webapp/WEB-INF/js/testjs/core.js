var core = {
		/**
	     * 获取指定参数
	     * @param name 参数名
	     * @returns {null}
	     */
	    getQueryString: function (name) {
	        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	        var r = window.location.search.substr(1).match(reg);
	        if (r != null) return decodeURIComponent(r[2]);
	        return null;
	    },
	    /**
	     * 页面跳转
	     * @param url
	     */
	    winJump: function (url) {
	        window.location.href = url;
	    },
}