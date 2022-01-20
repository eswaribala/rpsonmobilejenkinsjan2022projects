$(document).ready(function(){
	 alert("document ready....");
	 
	$("#login-form").on('submit', function(e){
		 e.preventDefault();
		alert("form submitted...");
		alert($("#userName").val());
		var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
	    var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);

	    var aesUtil = new AesUtil(128, 1000);
	    var ciphertext = aesUtil.encrypt(salt, iv, $('#key').val(), $('#password').val());

	    var aesPassword = (iv + "::" + salt + "::" + ciphertext);
	    var password = btoa(aesPassword);
	    var data = {
	        userName: $("#userName").val(),
	        password: password
	    }
	    console.log(data);   
	    $.ajax({
	    	  url:'http://localhost:6062/login',
	    	  type:"POST",
	    	  data:JSON.stringify(data),
	    	  contentType:"application/json",
	    	  dataType:"json",
	    	  success: function(response){
	    		  if(response.data.messageBean.statuscode === 200){
	  	            localStorage.token = response.headers('Authorization');
	  				if(response.data.result.role === 'ADMIN'){
	  	                localStorage.isAdmin = true;
	  	                localStorage.isSuperUser = false;
	  				} else if(response.data.result.role === 'SU'){
	  					localStorage.isSuperUser = true;
	  	                localStorage.isAdmin = false;
	  				}else {
	  	                localStorage.isSuperUser = false;
	  	                localStorage.isAdmin = false;
	  				}
	  	            localStorage.authenticated = true;
	  				if(!localStorage.isSuperUser) {
	  	                window.location.href='/sendsms';
	  	            }else {
	  	            	window.location.href='/manager';
	  	            }
	    	  }
	    	}
	    } );   
	    
	    
	   
		
	});
    
});
  
      

	