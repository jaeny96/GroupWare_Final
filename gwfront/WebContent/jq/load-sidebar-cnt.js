$(function () {
  //a링크 구성요소 받아오는 변수
  var allCntObject = document.getElementById("apAllLink");
  var waitCntObject = document.getElementById("apWaitLink");
  var okCntObject = document.getElementById("apOkLink");
  var noCntObject = document.getElementById("apNoLink");
  //div 구성요소 받아오는 변수 - 이곳에 마크 값 넣을 예정
  var allCnt = document.getElementById("apAllCntMark");
  var waitCnt = document.getElementById("apWaitCntMark");
  var okCnt = document.getElementById("apOkCntMark");
  var noCnt = document.getElementById("apNoCntMark");

  //배열로 받아오기 위한 변수
  var cnt = new Array();

  //사이드바의 마크에 받아온 데이터로 채우는 함수
  function createApCntElement() {
    if (allCntObject) {
      //전체 - 0번 index
      if (typeof cnt[0] == "undefined" || cnt[0] == "" || cnt[0] == null) {
        allCnt.innerHTML = 0;
      } else {
        allCnt.innerHTML = cnt[0];
      }
    }
    if (waitCntObject) {
      //대기 - 1번 index
      if (typeof cnt[1] == "undefined" || cnt[1] == "" || cnt[1] == null) {
        waitCnt.innerHTML = 0;
      } else {
        waitCnt.innerHTML = cnt[1];
      }
    }
    if (okCntObject) {
      //승인 - 2번 index
      if (typeof cnt[2] == "undefined" || cnt[2] == "" || cnt[2] == null) {
        okCnt.innerHTML = 0;
      } else {
        okCnt.innerHTML = cnt[2];
      }
    }
    if (noCntObject) {
      //반려 - 3번 index
      if (typeof cnt[3] == "undefined" || cnt[3] == "" || cnt[3] == null) {
        noCnt.innerHTML = 0;
      } else {
        noCnt.innerHTML = cnt[3];
      }
    }
  }

  callSidebar();
  //사이드바 관련 ajax
  function callSidebar() {
    $.ajax({
      method: "GET",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      url: "/gwback/approval/sidebar",
      headers: {
        Accept: "application/json, text/plain, */*",
      },
      data: "",
      timeout: {},
      success: function (responseData) {
        $(responseData).each(function (i, e) {
          cnt[i] = e;
        });
        createApCntElement();
      },
    });
  }
});
