$(document).ready(function () {
		$(".signUp").on("click", function () {
			$("#SignUPModal").css("display", "block");
			$("#ModalBG").css("background-color", "rgba(0,0,0,0.5)");
			$("#ModalBG").css("width", "100%");
			$("#ModalBG").css("height", "100%");
		});
		
		
		
		$(".ModalClose").on("click" , function () {
			$("#SignInModal").css("display", "none");
			$("#ModalBG").css("display", "none");
		})
		
	});



window.onload = function(){
    document.getElementById("address_kakao").addEventListener("click", function(){
        new daum.Postcode({
            oncomplete: function(data) { 
                document.getElementById("address_kakao").value = data.address; 
                document.querySelector("input[name=address_detail]").focus(); 
            }
        }).open();
    });
};


function email_check( email ) {    
    var regex=/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return (email != '' && email != 'undefined' && regex.test(email)); 
};

$("input[type=email]").blur(function(){
	  var email = $(this).val();
	  if( email == '' || email == 'undefined') return;
	  if(! email_check(email) ) {
	  	$(".result-email").text('이메일 형식으로 적어주세요');
	    $(this).focus();
	    return false;
	  }else {
		$(".result-email").text('');
	  }
	});