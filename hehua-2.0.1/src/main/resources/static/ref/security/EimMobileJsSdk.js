function _classCallCheck(e,i){if(!(e instanceof i))throw new TypeError("Cannot call a class as a function")}var EimMobileJsSdk=function(){function e(){_classCallCheck(this,e)}return e.prototype.firstUpperCase=function(e){return e.toLowerCase().replace(/( |^)[a-z]/g,function(e){return e.toUpperCase()})},e.prototype.randomId=function(e){e=e||10;for(var i="ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678",o=i.length,s="",t=0;t<e;t++)s+=i.charAt(Math.floor(Math.random()*o));return s},e.prototype.judge=function(){return window.navigator.userAgent.indexOf("eimAndroid")>-1?"eimAndroid":"eimios"},e.prototype.selectPhoto=function(e){var i=Object.assign({},this.opts,e),o=i.count?i.count:1,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkSelectPhoto"+s+"Success"]=i.success,window["eimMobileJsSdkSelectPhoto"+s+"Fail"]=i.fail,window.location.href="eim://selectPhoto_eimMobileJsSdkSelectPhoto"+s+"Success_eimMobileJsSdkSelectPhoto"+s+"Fail/"+o},e.prototype.imcrop=function(e){var i=Object.assign({},this.opts,e),o=i.scale?i.scale:1,s=i.level?i.level:0,t=i.shotonly?i.shotonly:0,n=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkImcrop"+n+"Success"]=i.success,window["eimMobileJsSdkImcrop"+n+"Fail"]=i.fail,window.location.href="eim://imcrop_eimMobileJsSdkImcrop"+n+"Success_eimMobileJsSdkImcrop"+n+"Fail/"+o+"_"+s+"_"+t},e.prototype.selectUser=function(e){var i=Object.assign({},this.opts,e),o=i.count,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6)),t="eim://selectUser_eimMobileJsSdkSelectUser"+s+"Success_eimMobileJsSdkSelect"+s+"UserFail",n=i.isSelectUser&&0!==i.isSelectUser.length?i.isSelectUser.join("_"):"";window["eimMobileJsSdkSelectUser"+s+"Success"]=i.success,window["eimMobileJsSdkSelectUser"+s+"Fail"]=i.fail,o&&(t+="_"+o),""!==n&&(t+="/"+n),i.ignoreself&&(t+="/ignoreself"),window.location.href=t},e.prototype.selectDept=function(e){var i=Object.assign({},this.opts,e),o=i.count,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6)),t="eim://selectDept_eimMobileJsSdkSelectDept"+s+"Success_eimMobileJsSdkSelectDept"+s+"Fail",n=i.isSelectDept&&0!==i.isSelectDept.length?i.isSelectDept.join("_"):"";window["eimMobileJsSdkSelectDept"+s+"Success"]=i.success,window["eimMobileJsSdkSelectDept"+s+"Fail"]=i.fail,o&&(t+="_"+o),""!==n&&(t+="/"+n),i.ignoreself&&(t+="/ignoreself"),window.location.href=t},e.prototype.screenRotation=function(e){var i=Object.assign({},this.opts,e),o=i.param?i.param:"vertical",s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkScreenRotation"+s+"Success"]=i.success,window["eimMobileJsSdkScreenRotation"+s+"Fail"]=i.fail,window.location.href="eim://screenRotation_eimMobileJsSdkScreenRotation"+s+"Success_eimMobileJsSdkScreenRotation"+s+"Fail/"+o},e.prototype.phoneCall=function(e){var i=Object.assign({},this.opts,e),o=i.phoneNumber;window.location.href="eim://phoneCall/"+o},e.prototype.openQRCode=function(e){var i=Object.assign({},this.opts,e),o=i.senderId?i.senderId:1,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkOpenQRCode"+s+"Success"]=i.success,window["eimMobileJsSdkOpenQRCode"+s+"Fail"]=i.fail,window.location.href="eim://openQRCode_eimMobileJsSdkOpenQRCode"+s+"Success_eimMobileJsSdkOpenQRCode"+s+"Fail/"+o},e.prototype.scanQRCode=function(e){var i=Object.assign({},this.opts,e),o=i.needResult?i.needResult:1,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkScanQRCode"+s+"Success"]=i.success,window["eimMobileJsSdkScanQRCode"+s+"Fail"]=i.fail,window.location.href="eim://scanQRCode_eimMobileJsSdkScanQRCode"+s+"Success_eimMobileJsSdkScanQRCode"+s+"Fail/"+o},e.prototype.openEditText=function(e){var i=Object.assign({},this.opts,e),o=i.text?i.text:"发送",s=i.type?i.type:"number",t=i.content,n=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6)),c="eim://openEditText_eimMobileJsSdkOpenEditText"+n+"Success_eimMobileJsSdkOpenEditText"+n+"Fail/"+o+"_"+s;window["eimMobileJsSdkOpenEditText"+n+"Success"]=i.success,window["eimMobileJsSdkOpenEditText"+n+"Fail"]=i.fail,t&&(c+="_"+t),window.location.href=c},e.prototype.openFile=function(e){var i=Object.assign({},this.opts,e),o=i.fileStr;window["eimMobileJsSdkOpenFile"+s+"Success"]=i.success,window["eimMobileJsSdkOpenFile"+s+"Fail"]=i.fail;var s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window.location.href="eim://openFile_eimMobileJsSdkOpenFile"+s+"Success_eimMobileJsSdkOpenFile"+s+"Fail/"+o},e.prototype.menuList=function(e){var i=Object.assign({},this.opts,e),o=i.isSelectMenu&&0!==i.isSelectMenu.length?i.isSelectMenu.join("_"):"",s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6)),t="eim://menuList_eimMobileJsSdkMenuList"+s+"Success_eimMobileJsSdkMenuList"+s+"Fail";window["eimMobileJsSdkMenuList"+s+"Success"]=i.success,window["eimMobileJsSdkMenuList"+s+"Fail"]=i.fail,o&&(t+="/"+o),window.location.href=t},e.prototype.onMenu=function(e){var i=Object.assign({},this.opts,e),o=i.type,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6)),t="eim://onMenu_eimMobileJsSdkOnMenu"+s+"Success_eimMobileJsSdkOnMenu"+s+"Fail/"+o;window["eimMobileJsSdkOnMenu"+s+"Success"]=i.success,window["eimMobileJsSdkOnMenu"+s+"Fail"]=i.fail,window.location.href=t},e.prototype.textInputDidFinish=function(){window.location.href="eim://textInputDidFinish"},e.prototype.removePopView=function(){window.location.href="eim://removePopView"},e.prototype.logout=function(){window.location.href="eim://logout"},e.prototype.closeWindow=function(){"eimAndroid"===this.judge()?window.location.href="eim://closecurrentwindow":window.location.href="eim://closeWindow"},e.prototype.banPull=function(){window.location.href="eim://banPull"},e.prototype.hideBottomToolView=function(){window.location.href="eim://hideBottomToolView"},e.prototype.showBottomToolView=function(){window.location.href="eim://showBottomToolView"},e.prototype.goBack=function(){"eimAndroid"===this.judge()?window.location.href="eim://closeWindow":window.location.href="eim://goBack"},e.prototype.hiddenNavigationBar=function(){window.location.href="eim://hiddenNavigationBar"},e.prototype.closeShouldAutorotate=function(){window.location.href="eim://closeShouldAutorotate"},e.prototype.refreshApp=function(e){var i=Object.assign({},this.opts,e),o=i.type,s="eim://refreshapp/"+o;window.location.href=s},e.prototype.clipBoard=function(e){var i=Object.assign({},this.opts,e),o=i.text,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkClipBoard"+s+"Success"]=i.success,window["eimMobileJsSdkClipBoard"+s+"Fail"]=i.fail;var t="eim://clipBoard_eimMobileJsSdkClipBoard"+s+"Success_eimMobileJsSdkClipBoard"+s+"Fail/"+o;window.location.href=t},e.prototype.setScrollViewNoBounces=function(){window.location.href="eim://setScrollViewNoBounces"},e.prototype.showPhotos=function(e){var i=Object.assign({},this.opts,e),o=i.param,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkShowPhotos"+s+"Success"]=i.success,window["eimMobileJsSdkShowPhotos"+s+"Fail"]=i.fail;var t="eim://showPhotos_eimMobileJsSdkShowPhotos"+s+"Success_eimMobileJsSdkShowPhotos"+s+"Fail/"+o;window.location.href=t},e.prototype.startRecord=function(){window.location.href="eim://startRecord"},e.prototype.refreshPubService=function(e){var i=Object.assign({},this.opts,e),o=i.type,s="eim://refreshpubservice/"+o;window.location.href=s},e.prototype.setEarpieceMode=function(e){var i=Object.assign({},this.opts,e),o=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkSetEarpieceMode"+o+"Success"]=i.success,window["eimMobileJsSdkSetEarpieceMode"+o+"Fail"]=i.fail;var s="eim://setEarpieceMode_eimMobileJsSdkSetEarpieceMode"+o+"Success_eimMobileJsSdkSetEarpieceMode"+o+"Fail";window.location.href=s},e.prototype.isInstalled=function(e){var i=Object.assign({},this.opts,e),o=i.packageName,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkIsInstalled"+s+"Success"]=i.success,window["eimMobileJsSdkIsInstalled"+s+"Fail"]=i.fail;var t="eim://isInstalled_eimMobileJsSdkIsInstalled"+s+"Success_eimMobileJsSdkIsInstalled"+s+"Fail/"+o;window.location.href=t},e.prototype.choosePhoto=function(e){var i=Object.assign({},this.opts,e),o=i.max,s=i.id?this.firstUpperCase(i.id):this.firstUpperCase(this.randomId(6));window["eimMobileJsSdkChoosePhoto"+s+"Success"]=i.success,window["eimMobileJsSdkChoosePhoto"+s+"Fail"]=i.fail;var t="eim://choosePhoto_eimMobileJsSdkChoosePhoto"+s+"Success_eimMobileJsSdkChoosePhoto"+s+"Fail/"+o;window.location.href=t},e.prototype.setTitleVisibility=function(e){var i=Object.assign({},this.opts,e),o=i.isShow||void 0===i.isShow?"visible":"gone",s="eim://setTitleVisibility/"+o;window.location.href=s},e}();!function(e,i){"undefined"!=typeof exports?module.exports=i:"function"==typeof define&&define.amd?define([],function(){return i}):e.EimMobileJsSdk=i}(this,EimMobileJsSdk);