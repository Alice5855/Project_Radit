package org.zerock.controller;

public class Math_ceil_Page304 {

	public static void main(String[] args) {
		System.out.println(Math.ceil(0.1)*10); // 10
		System.out.println(Math.ceil(1)*10); // 10
		System.out.println(Math.ceil(1.1)*10); // 20
		// ceil = 올림
		
		/*
		 * 페이지 번호를 표시하고 사용자가 페이지 번호를 클릭할 수 있는 코드의 흐름
		 * 1] 브라우저 주소창에서 페이지 번호를 전달하여 결과를 확인
		 * 2] JSP에서 페이지 번호를 출력
		 * 3] 각 페이지 번호에 클릭 이벤트 처리
		 * 4] 전체 데이터 개수를 반영하여 페이지 번호를 조절
		 * 
		 * 페이징에 필요한 정보
		 * 1] 현재 페이지 번호 = page
		 * 2] 이전(prev), 다음(next)으로 이동 가능한 링크 표시 여부
		 * 3] 화면에서 보여지는 페이지의 시작번호(startPage)와 끝 번호(endPage)
		 * 
		 * 페이징을 할때에 고려해야 할 점
		 * 화면에 10개씩 페이지 번호를 출력한다고 가정할 때, 사용자가 5페이지를 볼 때에
		 * 화면의 페이지 번호는 1부터 시작하지만 사용자가 19페이지를 볼 때에는 11부터
		 * 시작해야 함.
		 * 끝번호를 먼저 계산하는 것이 시작번호를 계산하는 것보다 수월함.
		 * 
		 * 페이지 번호가 10일 때의 endPage 계산
		 * this.endPage = (int)(Math.ceil(pageNum/10.0))*10;
		 * 
		 * startPage계산
		 * this.startPage = this.endPage - 9;
		 * 
		 * endPage는 total값에도 영향을 받는다. total값이 80, 10개씩 출력할 경우
		 * endPage는 8이 된다. endPage와 amount[entries per page]의 곱이
		 * total보다 클 경우 endPage가 하나 더 필요해 진다.
		 * 
		 * endPage계산
		 * realEnd = (int) (Math.ceil((total*1.0)/amount));
		 * if (realEnd < this.endPage) {
		 * 		this.endPage = realEnd;
		 * }
		 */
	}

}
