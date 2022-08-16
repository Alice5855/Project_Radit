/* 댓글 동작을 확인하는 용도로 사용
 * 게시물의 조회페이지에서 사용하기 위해서 views/board/get.jsp에 import
 */
console.log("Reply Module ===== activation check");
// devtools(F12) console 확인

/* Module 구성 : 교재 Page 401
 * Module pattern은 Java의 class처럼 method를 가지는 객체
 * JavaScript의 즉시 실행 함수와 '{}'를 이용하여 객체 구성
 * JS에서 즉시 실행 함수는 ()안에 function을 선언하고 바깥에서 실행함. 함수의 실행 결과가
 * 바깥쪽에서 선언된 변수에 할당됨
 */
var replyService = (function () {
	function add(reply, callback){
		console.log("Reply.js inner function running");
		// page 403 ajax를 이용하여 post method로 댓글 호출
		
		$.ajax({
			type: 'post',
			url: '/replies/new',
			data: JSON.stringify(reply),
			contentType: "application/json; charset=utf-8",
			success: function (result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error: function (xhr, status, e) {
				if (error) {
					error(e);
				}
			}
		});
	};
	// replyService.add(object, callback)로 호출
	
	function getList(param, callback, error) {
		var bno = param.bno;
		var page = param.page || 1;
		
		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
			function(data){
				if (callback) {
					// callback(data);
					// 댓글 목록만 가져옴. paging 처리 이전 source
					callback(data.replyCnt, data.list);
					// 댓글의 갯수와 목록(값)을 가져옴
				}
			}).fail(function(xhr, status, err){
				if(error) {
					error();
				}
			});
		// $.getJSON(url [, data] [, success])
		// data는 url에서 넘어온 data의 내용물을 뜻한다
	};
	
	// page407. 댓글이 소속된 게시글 bno와 댓글의 페이지를 param으로 받아 json data를
	// 추출하여 callback을 처리하는 함수. getJSON의 parameter인 URL은
	// ReplyController를 참고
	
	
	// function remove(rno, callback, error) {
	function remove(rno, replyer, callback, error) {
		$.ajax({
			type: 'delete',
			url: '/replies/' + rno,
			// page731 replyer 검증과정 추가. devTools의 network tab에서
			// (rno).json file이 생성됨을 확인
			data: JSON.stringify({rno: rno, replyer: replyer}),
			contentType: "application/json; charset=utf-8",
			
			success: function(deleteResult, status, xhr) {
				if(callback){
					callback(deleteResult);
				}
			},
			error: function(xhr, status, er) {
				if(error) {
					error(er);
				}
			}
		});
		// jQuery.ajax(url [, settings])
	}
	// page 408. pretty self explanatory remove feature includes
	// callback feature
	// type: 'delete' / get, post, put, patch, delete method의 delete
	
	function update(reply, callback, error) {
		console.log("rno : " + reply.rno);
		
		$.ajax({
			type: 'put',
			url: '/replies/' + reply.rno,
			data: JSON.stringify(reply),
			contentType: "application/json; charset=utf-8",
			success: function(result, status, xhr){
				if (callback) {
					callback(result);
				}
			},
			error: function(status, xhr, er) {
				if (error) {
					error(er);
				}
			}
		});
	}
	// page 410
	
	function get(rno, callback, error) {
		$.get("/replies/" + rno + ".json", function(result) {
			if(callback) {
				callback(result);
			}
		}).fail(function(xhr, status, err) {
			if(error) {
				error();
			}
		});
	}
	// page 412
	
	function displayTime(timeValue) {
		var today = new Date();
		var gap = today.getTime() - timeValue;
		
		var dateObj = new Date(timeValue);
		var str = "";
		// get.jsp의 js에서 변수로 사용됨
		
		// if (현재 날짜와 등록 날짜의 gap이 24시간 이상이라면)
		if (gap < (1000 * 60 * 60 * 24)) {
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			
			return [ (hh > 9 ? '' : '0') + hh, ":", (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss ].join('');
			// if(true) : hour:minute:second 형태로 출력
		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1;
			var dd = dateObj.getDate();
			
			return [yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd].join('');
			// if (false) : year/month/date 형태로 출력
		}
		// return문의 삼항 연산자의 경우 두자리가 아닌 숫자들의 앞에 0을 출력해주는 역할
	}
	// page 417
	// XML or JSON 형태로 data를 받을 때에 숫자로 표현되는 시간값이 출력됨. 이를 변환하기
	// 위해서 사용
	/* 최근 web trend에서는 해당 일의 경우 시/분/초를, 해당일자가 아닌 경우 년/월/일을
	 * 출력하는 경우가 많음. 예제에서도 이러한 형태를 보여줌
	 * displayTime()은 get.jsp의 js에서 ajax로 data를 html로 출력할 때에 사용됨
	 * (getList()의 for문 내부)
	 */
	
	return {
		add: add, 
		getList: getList,
		remove: remove,
		update: update,
		get: get,
		displayTime: displayTime
	};
})();